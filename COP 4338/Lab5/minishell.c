/*
Edward R. Gonzalez 499406

Program name: minishell

Description:
Run two child processes. One executes "ls -al" and the other executes "grep minishell.c"
This programs for child processes to complete before terminating. The processID will be provided to stdout when completed.
The stdout of the first process will be piped to the second process.

The first process lists all the folders and files in the directory of this program,
and provides (pipes) it to the second process which do a grep command to search for the source of this program within the directory.


Index:

Includes             : 35
Enums                : 43
Typedefs             : 71
Structs              : 85
Declares             : 96
Forward Declarations : 107
Debug                : 115
Lab Related Functions: 263
Runtime Execution    : 374


Affirmation of originality:
I affirm that I wrote this
program myself without any help form any
other people or sources from the Internet.
*/

//Includes
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>


//-------------------------------------------------------------Enums:

enum   //General Numerical Constants
{
	//Process I/O
	NULLINPUT = -1,

	INPUT  = 0,
	OUTPUT = 1,

	PipeChannels = 2
};

enum ProcessRelation_Enum   //Enum used to define a ProcessRelation.
{
	Child ,
	Parent
};

enum IOBehavior_Enum   //Enum used to define an IOBehavior.
{
	SEND   ,
	RECEIVE,
	BOTH   ,
};

//-------------------------------------------------------------------

//----------------------------------------------------------Typedefs:

typedef unsigned char uInt8;   //Unsigned 8-bit integer.

typedef char* CString;   //Regular CString. Must be used like a pointer.

typedef struct CProcess_Struct CProcess;   //A regular c process, contains the id of the process and name.

typedef enum ProcessRelation_Enum ProcessRelation;   //Used to keep track of the relationship of a process since the execution of the first instance.

typedef enum IOBehavior_Enum IOBehavior;   //Provides an way to interpret desired IOBehaviors.

//-------------------------------------------------------------------

//-----------------------------------------------------------Structs:

struct CProcess_Struct   //Defines a CProcess.
{
	CString Name;   //Name of the process.

	uInt8 ProcessID;   //Process ID or pid_t.
};

//-------------------------------------------------------------------

//----------------------------------------------------------Declares:

static ProcessRelation CurrentProcess;   //Current process being executed.

static int PipeIO[PipeChannels];   //Pipe channels for this instance of minishell.

static CProcess ListProcess;   //Holds information reguarding the list process.
static CProcess GrepProcess;   //Holds information reguarding the grep process.

//-------------------------------------------------------------------
		
//----------------------------------------------Forward Declarations:

//Closes the specified pipes.
//_whichPipeToClose: Used to specify the pipe.
bool ClosePipeIO();

//-------------------------------------------------------------------

//-------------------------------------------------------------Debug:

//Debug version of RunCProcess.
ProcessRelation DebugRunCProcess(CProcess *_cProcessPtr, bool _IOBehavior, CString *_arguments)
{
	printf("\nDoing run process...\n");

	_cProcessPtr->ProcessID = fork();

	printf("\n%s ProcessID: %d\n", _cProcessPtr->Name, _cProcessPtr->ProcessID);

	if (_cProcessPtr->ProcessID == 0)
	{
		printf("Found a child process....\n");

		if (_IOBehavior)
		{
			printf("Attempting to pipe output...?");

			dup2(PipeIO[OUTPUT], STDOUT_FILENO);

			//ClosePipeIO();

			//printf("Executing the shell commands.");

			//execvp((char*)_arguments[0], (char**)_arguments);   //Execution of specified command/program.
		}
		else
		{
			printf("Attempting to pipe input given to child...");

			dup2(PipeIO[INPUT], STDIN_FILENO);

			//ClosePipeIO();

			//printf("Executing the shell commands.");

			////execl((char*)_arguments[0], (char*)_arguments[1], (char*)_arguments[2], NULL);   //Execution of specified command/program.

			//execvp((char*)_arguments[0], (char**)_arguments);   //Execution of specified command/program.
		}

		ClosePipeIO();

		printf("Executing the shell commands.");

		execvp((char*)_arguments[0], (char**)_arguments);   //Execution of specified command/program.

		return Child;
	}
	else if (_cProcessPtr->ProcessID > 0)
	{
		printf("Parent Process waiting for child...\n");

		close(PipeIO[OUTPUT]);

		uInt8 finishedChildID = wait(NULL);

		printf("\nChild Process Completed: %d\n", finishedChildID);

		return Parent;
	}
	else   //An error occurred with the forking.
	{
		perror("Attempting to create a child process failed.\n");

		exit(1);
	}
}

//Debug version of runtime statements.
void DebugRuntime()
{
	printf("Doing minishell:\n\n");

	printf("Launch ProcessID: %d\n\n", getpid());

	printf("Setting up pipes...\n\n");

	pipe(PipeIO);

	printf("Input  Pipe: %d\n", PipeIO[INPUT ]);
	printf("Output Pipe: %d\n", PipeIO[OUTPUT]);

	printf("\nAttempting to do the ls -al process...\n");

	CString listArgs[3];

	listArgs[0] = "/bin/ls";
	listArgs[1] = "-al"    ;
	listArgs[2] = NULL     ;

	ListProcess.Name = "ListProcess";

	CurrentProcess = DebugRunCProcess(&ListProcess, true, listArgs);

	if (CurrentProcess == Parent)
	{
		printf("Parent:\n");
	}
	else
	{
		printf("Child:\n");
	}

	printf("Current ProcessID After Run List: %d\n", getpid());

	if (CurrentProcess == Parent)
	{
		printf("\n\n");

		printf("Attempting to run the 'grep minishell.c'....\n");

		CString grepArgs[3];

		grepArgs[0] = "/bin/grep";
		//grepArgs[1] = "grep";
		grepArgs[1] = "minishell.c";
		grepArgs[2] = NULL;

		GrepProcess.Name = "GrepProcess";

		CurrentProcess = DebugRunCProcess(&GrepProcess, false, grepArgs);

		if (CurrentProcess == Parent)
		{
			printf("Parent:\n");
		}
		else
		{
			printf("Child:\n");
		}

		printf("Current ProcessID After Run Grep: %d\n", getpid());
	}

	printf("Current ProcessID After Check 1: %d\n", getpid());

	if (CurrentProcess == Parent)
	{
		printf("\nFinished running the processes.\n");
	}

	//exit(0);
}

//-------------------------------------------------------------------

//---------------------------------------------Lab Related Functions:

//Will execute a process referenced via a pointer with the given IOBehavior and arguments.
//_cProcessPtr: Reference to the process.
//_ioBehaviorToUse: IOBehavior to use when this process does its execution.
//_arguments: Arguments to provide for execution of the process.
ProcessRelation RunCProcess(CProcess *_cProcessPtr, IOBehavior _ioBehaviorToUse, CString *_arguments)
{
	_cProcessPtr->ProcessID = fork();

	if (_cProcessPtr->ProcessID == 0)
	{
		switch (_ioBehaviorToUse)
		{
		case SEND:
		{
			dup2(PipeIO[OUTPUT], STDOUT_FILENO);

			break;
		}
		case RECEIVE:
		{
			dup2(PipeIO[INPUT], STDIN_FILENO);

			break;
		}
		case BOTH:
		{
			dup2(PipeIO[OUTPUT], STDOUT_FILENO);
			dup2(PipeIO[INPUT ], STDIN_FILENO );

			break;
		}
		}

		ClosePipeIO(BOTH);

		execvp((char*)_arguments[0], (char**)_arguments);   //Execution of specified command/program.

		return Child;
	}
	else if (_cProcessPtr->ProcessID > 0)
	{
		switch (_ioBehaviorToUse)
		{
		case SEND:
		{
			ClosePipeIO(SEND);

			break;
		}
		case RECEIVE:
		{
			ClosePipeIO(RECEIVE);

			break;
		}
		case BOTH:
		{
			break;
		}
		}

		uInt8 finishedChildID = wait(NULL);

		printf("Child Process Completed: %s\n", _cProcessPtr->Name);
		printf("PID: %d\n"					  , finishedChildID   );

		return Parent;
	}
	else   //An error occurred with the forking.
	{
		perror("Attempting to create a child process failed.\n");

		exit(1);
	}
}

//Closes the specified pipes.
//_whichPipeToClose: Used to specify the pipe.
bool ClosePipeIO(IOBehavior _whichPipeToClose)
{
	switch (_whichPipeToClose)
	{
	case SEND:
	{
		close(PipeIO[OUTPUT]);

		return true;
		
	}
	case RECEIVE:
	{
		close(PipeIO[INPUT]);

		return true;
	}
	case BOTH:
	{
		close(PipeIO[INPUT ]);
		close(PipeIO[OUTPUT]);

		return true;
	}
	}

	return false;
}

//-------------------------------------------------------------------

//-------------------------------------------------Runtime Execution:

int main()
{
	pipe(PipeIO);   //Creates populates file descriptors for parent/child pipeplines.

	CString listArgs[3];   //Arguments for list process.

	listArgs[0] = "/bin/ls";
	listArgs[1] = "-al"    ;
	listArgs[2] = NULL     ;

	ListProcess.Name = "List All (Long Form)";

	CurrentProcess = RunCProcess(&ListProcess, SEND, listArgs);   //Send Pipe will be closed here.

	if (CurrentProcess == Parent)
	{
		CString grepArgs[3];   //Arguments for grep process.

		grepArgs[0] = "/bin/grep"  ;
		grepArgs[1] = "minishell.c";
		grepArgs[2] = NULL         ;

		GrepProcess.Name = "Grep: minishell.c";

		RunCProcess(&GrepProcess, RECEIVE, grepArgs);   //Receive Pipe will be closed here.
	}

	exit(0);
}

//-------------------------------------------------------------------