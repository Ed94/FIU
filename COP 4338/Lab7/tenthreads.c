/*
Edward R. Gonzalez 499406

Program name: tenthreads

Description:
Uses ten threads to do an addition operation on a shared value between the threads.
The addition operation uses the thread id of every thread to add to. The value is signed.
A shared mutex is used to prevent the threads from operating on the value at the same time.
When a thread finishes operating on the value six times in a row. It notifies via standard out it
completed.
The final value of the shared value is sent to standard out at the end when all threads have completed.

----------------Index:
Includes             : 32
Macros               : 42
Enums                : 52
Typedefs             : 71
Structs              : 88
Global Declares      : 133
Forward Declares     : 145
Lab Related Functions: 153
Runtime Execution    : 382

Affirmation of originality:
I affirm that I wrote this
program myself without any help form any
other people or sources from the Internet.
*/


//----------------------------------------------------------------------------------------Includes:

#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

#include <pthread.h>

///////////////////////////////////////////////////////////////////////////////////////////////////

//------------------------------------------------------------------------------------------Macros:

//#define __DebugMode__   //Comment this out to remove debug logs.

//Ease of use: Does a newline for console.
#define cpnl \
printf("\n")
 
///////////////////////////////////////////////////////////////////////////////////////////////////

//-------------------------------------------------------------------------------------------Enums:

enum ArgumentType_Def   //Defines the different argument types that can be derived	
{						//from a pointer to arguments.
	E_SharedVariable_t,
	E_Thread_t        ,
	E_ThreadLoopArg_t ,
};

enum Numerical_Constants   //Numerical constants used with this program.
{
	LoopLimit = 6,

	ThreadLimit = 10,
};

///////////////////////////////////////////////////////////////////////////////////////////////////


//----------------------------------------------------------------------------------------TypeDefs:

typedef enum ArgumentType_Def ArgumentType;   //Holds argument type for an Argument generic.

typedef unsigned           int Int  ;   //Unsigned integer.
typedef unsigned long long int uInt64;   //Unsigned 64-bit integer.

typedef struct Argument_Def Argument;   //Generic Argument to be used by threads.

typedef struct Thread_Def        Thread       ;   //Generic Thread struct that overheads pThread functionality.
typedef struct ThreadLoopArg_Def ThreadLoopArg;   //Arguments to be used with a thread loop call.

typedef struct Th_Int_Def Th_Int;   //A thread accessible Int.

///////////////////////////////////////////////////////////////////////////////////////////////////


//-----------------------------------------------------------------------------------------Structs:

struct Argument_Def   //Definition for a generic argument.
{
	ArgumentType Argument_T;   //The arguments type.

	void *Argument;   //Pointer to argument.
};

struct Thread_Def   //Definition for a thread.
{
	Argument Arguments;   //Arguments for the function to be completed.

	bool IsActive;   //States whether or not the thread has been created/active.

	pthread_t Handle;   //PThread handle that contains the ID and other info.

	pthread_attr_t Attributes;   //Attributes to use when activating this thread.

	pthread_mutex_t *Mutex;   //Pointer to a shared pthread mutex to sync thread execution.

	int ID;   //ID of this thread. (Derived from Handle's p value.)

	void *Function;   //Function for this thread to complete when activated.
};

struct ThreadLoopArg_Def   //A specified definition for arguments to be passed to the thread loop function.
{
	Th_Int *SharedVariableRef;   //The variable that will be incremented by the id of the thread in the loop. 

	Thread *ThreadRef;   //The reference to the thread that is tasked to complete the thread loop function.
};

struct Th_Int_Def   //Definition for a Int that is thread accessible.
{
	bool Allocated;   //Indicates if the Value has been allocated.

	bool CheckedOut;   //If this Int is checked out by a thread.

	int *Value;   //The Int instanced value. Must be allocated.
};

///////////////////////////////////////////////////////////////////////////////////////////////////


//---------------------------------------------------------------------------------Global Declares:

Th_Int SharedVariable;   //Variable to be incremented by all the threads.

pthread_mutex_t Sync_Mutex;   //Mutex used to sync the threads.

Thread        Threads   [ThreadLimit];   //Thread containers.
ThreadLoopArg ThreadArgs[ThreadLimit];   //Container for thread arguments.

///////////////////////////////////////////////////////////////////////////////////////////////////


//--------------------------------------------------------------------------------Forward Declares:

void *ThreadLoop   (void   *_ptrToArguments );
void  WaitForThread(Thread *_threadToWaitFor);

///////////////////////////////////////////////////////////////////////////////////////////////////


//---------------------------------------------------------------------------Lab Related Functions:


//Extracts from a generic argument container a ThreadLoopArg argument type reference.
//Used by the thread loop or any other function that uses a ThreadLoopArg type.
ThreadLoopArg *ExtractThreadLoopArgs(void *_argumentToExtractFrom)
{
	//Dereferences to Argument scope to get Argument_T and see if its the correct type.
	if ( (*(Argument *)_argumentToExtractFrom).Argument_T == E_ThreadLoopArg_t) 
	{
		//Cast the Argument pointer to the correct type and passes the pointer reference.
		return (ThreadLoopArg *)(*(Argument *)_argumentToExtractFrom).Argument;   
	}
	else
	{
		perror("Failed to Extract the correct argument. Type was incorrect. Or not specified.");

		exit(1);
	}
}

//Activates a thread with the information contained within _threadToActivate.
void ActivateThread(Thread *_threadToActivate)
{	
	#ifdef __DebugMode__
		cpnl; printf("Activating thread..."); cpnl;
	#endif

	bool activationResult =
		pthread_create
		(
			&_threadToActivate->Handle    ,
			&_threadToActivate->Attributes,
			 _threadToActivate->Function  ,
			&_threadToActivate->Arguments
		);

	//_threadToActivate->ID = *(int *)_threadToActivate->Handle;

	if (activationResult)
	{
		perror("Could not properly the thread.");

		exit(1);
	}

	_threadToActivate->IsActive = true;

#ifdef __DebugMode__
	printf("Thread Activated with ID: %d", _threadToActivate->ID); cpnl;
#endif
}

//Cleans the declarations that used the heap.
void CleanDeclares()
{
	if (SharedVariable.Allocated)
	{
		free(SharedVariable.Value);
	}
}

//Populates the necessary definitions to have a functional thread.
void DefineThread
(
	Thread          *_threadToDefine , 
	pthread_attr_t   _attributesToUse,
	pthread_mutex_t *_mutexToUse     ,
	void            *_ptrToFunction  , 
	Argument         _arguments
)
{
	_threadToDefine->Attributes = _attributesToUse;
	_threadToDefine->Function   = _ptrToFunction  ;
	_threadToDefine->Arguments  = _arguments      ;

	if (_mutexToUse != NULL) { _threadToDefine->Mutex = _mutexToUse; }
}

//Notifies the completion of a thread and sets the _thread's sets its active state to false.
void NotifyThreadCompletion(Thread *_thread)
{
	_thread->IsActive = false;

	cpnl; printf("Thread %d has finished.", _thread->ID); cpnl;
}

//Prepares the shared variable for use by the threads.
void PrepareSharedVariable()
{
	#ifdef __DebugMode__
		cpnl; printf("Preparing shared variable for use.");
	#endif

	SharedVariable.Value = (int *)malloc(sizeof(int));   //Allocates the memory for the value of the shared variable.

	SharedVariable.Allocated = true;

	*SharedVariable.Value = 0;
}

//Prepares the threads for this program's tasks.
void PrepareThreads()
{
	#ifdef __DebugMode__
		cpnl;  printf("Preparing the threads for activation...");
	#endif

	Argument arguments;

	arguments.Argument_T = E_ThreadLoopArg_t;

	pthread_attr_t attributes;

	pthread_attr_init(&attributes);

	Sync_Mutex = (pthread_mutex_t)PTHREAD_MUTEX_INITIALIZER;

	for (Int index = 0; index < ThreadLimit; index++)
	{
		ThreadArgs[index].SharedVariableRef = &SharedVariable;
		ThreadArgs[index].ThreadRef         = &Threads[index];

		arguments.Argument = &ThreadArgs[index];

		Threads[index].ID = index;

		DefineThread(&Threads[index], attributes, &Sync_Mutex, ThreadLoop, arguments);
	}
}

//Prints the value of the shared variable.
void PrintResult()
{
	cpnl; cpnl;

	printf("Shared variable resulting value: %u", *SharedVariable.Value);

	cpnl;
}

//Will activate all threads to complete the ThreadLoop task.
//Will not return until all threads are complete if _should Wait is true.
void RunThreads(bool _shouldWait)
{
	#ifdef __DebugMode__
		cpnl; printf("Running threads..."); cpnl;
	#endif

	for (Int index = 0; index < ThreadLimit; index++)
	{
		ActivateThread(&Threads[index]);
	}

	if (_shouldWait)
	{
		#ifdef __DebugMode__
			cpnl; printf("Will wait for threads.."); cpnl;
		#endif

		for (Int index = 0; index < ThreadLimit; index++)
		{
			WaitForThread(&Threads[index]);
		}
	}
}

//A function designed to be used with a thread.
//_ptrToArguments: A pointer of argument type that should have been derived from
//The thread that was activated.
//The necessary type this function requires will be extracted from it.
void *ThreadLoop(void *_ptrToArguments)
{
	#ifdef __DebugMode__
		cpnl; printf("Completing thread loop."); cpnl; cpnl;
	#endif

	ThreadLoopArg *extractedArgs = ExtractThreadLoopArgs(_ptrToArguments);
	
	#ifdef __DebugMode__
		printf("With Thread ID: %d", extractedArgs->ThreadRef->ID); 
	
		cpnl; cpnl;
	#endif

	Int loopCount = 0;

	while (loopCount < 6)
	{
		#ifdef __DebugMode__
			printf("ID: %d   LoopCount: %u", extractedArgs->ThreadRef->ID, loopCount); cpnl;
		#endif

		int *valRef = extractedArgs->SharedVariableRef->Value;

		pthread_mutex_lock(extractedArgs->ThreadRef->Mutex);

		//while (extractedArgs->SharedVariableRef->CheckedOut) {}   //Wait for var to not be checked out.

		//extractedArgs->SharedVariableRef->CheckedOut = true;

		*valRef += extractedArgs->ThreadRef->ID;

		//extractedArgs->SharedVariableRef->CheckedOut = false;

		pthread_mutex_unlock(extractedArgs->ThreadRef->Mutex);

		#ifdef __DebugMode__
			cpnl; printf("ID: %d, Value After Add: %d", extractedArgs->ThreadRef->ID, *valRef); cpnl;
		#endif
		
		loopCount++;
	}

	pthread_exit(0);

	return 0; 
}

//Will wait for the thread to complete before notifying its completion.
void WaitForThread(Thread *_threadToWaitFor)
{
	#ifdef __DebugMode__
		cpnl; printf("Waiting for thread: %d", _threadToWaitFor->ID); cpnl;
	#endif

	pthread_join(_threadToWaitFor->Handle, NULL);

	NotifyThreadCompletion(_threadToWaitFor);
}

///////////////////////////////////////////////////////////////////////////////////////////////////


//-------------------------------------------------------------------------------Runtime Execution:

int main(int argc, char *argv[])
{
#ifdef __DebugMode__
	cpnl; printf("Ten Threads:"); cpnl;
#endif

	PrepareSharedVariable();

	PrepareThreads();

	RunThreads(true);

	PrintResult();

	CleanDeclares();

	exit(0);
}

///////////////////////////////////////////////////////////////////////////////////////////////////

#undef __DebugMode__