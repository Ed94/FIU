/*
Edward R. Gonzalez 499406

Program name: myshell.c

Description:

----------------Index:
Includes             : 
Macros               : 
Enums                : 
Typedefs             : 
Structs              : 
Global Declares      : 
Forward Declares     : 
Lab Related Functions: 
Runtime Execution    : 

Affirmation of originality:
I affirm that I wrote this
program myself without any help form any
other people or sources from the Internet.
*/



//----------------------------------------------------------------------------------------Includes:

//C Standard
#include <stdbool.h>

#define _GNU_SOURCE 
#include <stdio.h>

#include <stdlib.h>

//Sys
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/wait.h>

//Other
#include <fcntl.h>
#include <string.h>
#include <unistd.h>

///////////////////////////////////////////////////////////////////////////////////////////////////


//------------------------------------------------------------------------------------------Macros:

//Does a new line in standard out.
#define cpnl \
printf("\n")

///////////////////////////////////////////////////////////////////////////////////////////////////


//-------------------------------------------------------------------------------------------Enums:

enum RecognizedCmds_Def   //Defines all the recognized commands for this program.
{
	C_UnSet                ,
	C_Async                ,
	C_Exit                 ,
	C_Pipe                 ,
	C_RedirectStdIn        ,
	C_RedirectStdOut       ,
	C_AppendStdout         ,
	C_NoCmd                ,
};

enum IOBehavior_Def   //Enum used to define an IOBehavior.
{
	IO_Send   ,
	IO_Recieve,
	IO_Both   
};

enum Numerical_Constants
{
	//Process I/O
	NULLINPUT = -1,

	INPUT  = 0,
	OUTPUT = 1,

	PipeChannels = 2,

	//Command Line Parse:
	CmdLineBuffer = 1024,
	Argument_Max  = 20  ,
	ArgSet_Max    = 10  
};

enum ProcessRelation_Def   //Enum used to define a ProcessRelation.
{
	Child ,
	Parent
};

///////////////////////////////////////////////////////////////////////////////////////////////////


//----------------------------------------------------------------------------------------Typedefs:

typedef unsigned char uInt8;   //Unsigned 8-bit integer.

typedef char* CString;   //Regular CString. Must be used like a pointer.

typedef char CmdlString[CmdLineBuffer];   //A command line string.

typedef enum RecognizedCmds_Def ReconizedCmds;   //Contains all recognized commands for this program.

typedef enum IOBehavior_Def IOBehavior;   //Provides an way to interpret desired IOBehaviors.

typedef enum ProcessRelation_Def ProcessRelation;   //Used to keep track of the relationship of a process since the execution of the first instance.

typedef struct ArgSet_Def ArgSet;

typedef struct CommandFlags_Def CommandFlags;   //Command flag container.

typedef struct CommandLineData_Def CommandLineData;   //Used to store command line information.

typedef struct CProcess_Def CProcess;   //A regular c process, contains the id of the process and name.

///////////////////////////////////////////////////////////////////////////////////////////////////


//-----------------------------------------------------------------------------------------Structs:

struct ArgSet_Def
{
	CString Set[Argument_Max];   //The argument set.

	uInt8 Count;   //Number of arguments stored within this set.
	uInt8 Cmd  ;   //Command to complete with this set.
};

struct CommandFlags_Def   //Defines a command flag container.
{
	bool Async;   //Indicates whether or not to do async.
	bool Exit ;   //Indicates if a program should close.
	bool Pipe ;   //Indicates if output from one command should pipped to another.

	IOBehavior PipeStage;   //Indicates if doing piping what stage of piping is at.

	bool RedirectStdIn ;   //Indicates whether to redirect stdin for the next command to execute.
	bool RedirectStdOut;   //Indicates whether to redirect stdout for the next command to execute.

	CString RedirectName;   //Name of file to redirect stdout or stdin to.

	bool AppendStdOut;   //Indicates whether to append a file with stdout for the next command to execute.

	CString AppendName;   //Name of the file to append with stdout.
};

struct CProcess_Def   //Defines a CProcess type.
{
	CString Name;   //Name of the process.

	ProcessRelation Type;   //The process type.

	uInt8 ProcessID;   //Process ID or pid_t.
};

struct CommandLineData_Def   //Define a CommandLineData type.
{
	CmdlString CmdLine;   //Raw command line string.

	CString CmdlArgs[Argument_Max];   //CmdlArgs parsed from CmdLine.

	uInt8 ArgCount;   //Number of arguments within CmdlArgs.

	ArgSet ArgSets[ArgSet_Max];   //ArgSets parsed from CmdA.

	uInt8 ArgSetToExec;   //Next ArgSetToExec.

	uInt8 ArgSetCount;   //Number of arg sets within CmdlArgs.
};

///////////////////////////////////////////////////////////////////////////////////////////////////


//---------------------------------------------------------------------------------Global Declares:

static bool exist;   //Used to determine program termination.

static CommandFlags CmdFlags;   //Stores the flags parsed from command line arguments.

static CommandLineData CmdInfo;   //Contains command line information.

static int PipeIO[PipeChannels];   //Pipe channels for this instance of myshell.

static CProcess CurrentProcess;   //Holds information regarding the list process.

///////////////////////////////////////////////////////////////////////////////////////////////////


//--------------------------------------------------------------------------------Forward Declares:

void ParseArguments();
void SetRelation   ();

///////////////////////////////////////////////////////////////////////////////////////////////////


//---------------------------------------------------------------------------Lab Related Functions:

//Check for a command that is recognized by this program.
bool ChckForCmd(CString _argToCheck, ReconizedCmds _commandToCheckFor)
{
	switch (_commandToCheckFor)
	{
	case C_Async:
	{
		return (!strcmp(_argToCheck, "&")) ? true : false;
	}
	case C_Exit:
	{
		return (!strcmp(_argToCheck, "quit") || !strcmp(_argToCheck, "exit")) ? true : false;
	}
	case C_RedirectStdIn:
	{
		return (!strcmp(_argToCheck, "<")) ? true : false;
	}
	case C_RedirectStdOut:
	{
		return (!strcmp(_argToCheck, ">")) ? true : false;
	}
	case C_AppendStdout:
	{
		return (!strcmp(_argToCheck, ">>")) ? true : false;
	}
	case C_Pipe:
	{
		return (!strcmp(_argToCheck, "|")) ? true : false;
	}
	default:
		return false;
	}
}

//Closes the specified pipes.
//_whichPipeToClose: Used to specify the pipe.
bool ClosePipeIO(IOBehavior _whichPipeToClose)
{
	switch (_whichPipeToClose)
	{
	case IO_Send:
	{
		close(PipeIO[OUTPUT]);

		return true;

	}
	case IO_Recieve:
	{
		close(PipeIO[INPUT]);

		return true;
	}
	case IO_Both:
	{
		close(PipeIO[INPUT]);
		close(PipeIO[OUTPUT]);

		return true;
	}
	}

	return false;
}

//Determines whether the pointer is at NULL.
bool IsNull(void *_ptrToCheck)
{
	return (_ptrToCheck == NULL);
}

//Checks if a string is empty.
bool StrIsEmpty(CmdlString _strToCheck)
{
	return 
	(
		(strcmp(_strToCheck, ""  ) == 0) ||
		(strcmp(_strToCheck, " " ) == 0) ||
		(strcmp(_strToCheck, "\n") == 0) ||
		(strcmp(_strToCheck, "\t") == 0)
	);
}

//Return the command ID for the raw string specified.
uInt8 GetCmdNum(CString _cmdToParse)
{
	if (ChckForCmd(_cmdToParse, C_RedirectStdIn))
	{
		return C_RedirectStdIn;
	}
	if (ChckForCmd(_cmdToParse, C_RedirectStdOut))
	{
		return C_RedirectStdOut;
	}
	if (ChckForCmd(_cmdToParse, C_AppendStdout))
	{
		return C_AppendStdout;
	}
	if (ChckForCmd(_cmdToParse, C_Pipe))
	{
		return C_Pipe;
	}

	return 255;
}

//Resets all the declaration values.
void CleanDeclares()
{
	CmdFlags.Async = false;
	CmdFlags.Exit  = false;

	CmdFlags.Pipe = false;

	CmdFlags.AppendStdOut   = false;
	CmdFlags.RedirectStdOut = false;

	CmdInfo.ArgCount = 0 ;
	
	strcpy(CmdInfo.CmdLine, "");

	CurrentProcess.ProcessID = -1;
	CurrentProcess.Type       = 1;
	
	CmdInfo.ArgCount = 0;

	CmdInfo.ArgSetToExec = 0;

	for (uInt8 index = 0; index < CmdInfo.ArgSetCount; index++)
	{
		for (uInt8 setIndex = 0; setIndex < CmdInfo.ArgSets[index].Count; setIndex++)
		{
			strcpy(CmdInfo.ArgSets[index].Set[setIndex], "");
		}

		CmdInfo.ArgSets[index].Count = 0      ;
		CmdInfo.ArgSets[index].Cmd   = C_UnSet;
	}

	fflush(stdout);
	fflush(stdin);
}

//Executes commands that were flagged to complete.
void ExecuteCommand(ArgSet *_argSetToExec)
{
	CurrentProcess.ProcessID = fork();

	SetRelation();

	switch (CurrentProcess.Type)
	{
	case Child:
	{
		switch (_argSetToExec->Cmd)
		{
		case C_RedirectStdIn:
		{
			if (!CmdFlags.RedirectStdIn)
			{
				perror("Indicated to complete a redirect stdin command, however the arg parse failed to reconize this.");

				exit(1);
			}

			int inFile =
				open
				(
					CmdInfo.ArgSets[CmdInfo.ArgSetToExec + 1].Set[0],
					O_RDONLY                                        ,
					0666
				);

			if (inFile < 0)
			{
				perror("Failed to open in file.");

				exit(1);
			}

			int dupeResult = dup2(inFile, STDIN_FILENO);

			if (dupeResult < 0)
			{
				perror("Failed to write to out file.");

				exit(1);
			}

			close(inFile);

			execvp((char *)_argSetToExec->Set[0], (char **)_argSetToExec->Set);

			perror("Exec failed");

			exit(-1);
		}
		case C_RedirectStdOut:
		{
			if (!CmdFlags.RedirectStdOut)
			{
				perror("Indicated to complete a redirect to stdout, however the arg parse did not recognize this.");

				exit(1);
			}

			int outFile =
				open
				(
					CmdInfo.ArgSets[CmdInfo.ArgSetToExec + 1].Set[0],
					O_WRONLY | O_CREAT                              ,
					0666
				);

			if (outFile < 0)
			{
				perror("Failed to open out file");

				exit(1);
			}

			int dupeResult = dup2(outFile, STDOUT_FILENO);

			if (dupeResult < 0)
			{
				perror("Failed to write to out file.");

				exit(1);
			}

			close(outFile);

			execvp((char *)_argSetToExec->Set[0], (char **)_argSetToExec->Set);

			perror("Exec failed");

			exit(-1);
		}
		case C_AppendStdout:
		{
			if (!CmdFlags.AppendStdOut)
			{
				perror("Indicated to complete an append to file from stdout. However argument parse failed to recognize this.");

				exit(-1);
			}

			int outFile =
				open
				(
					CmdInfo.ArgSets[CmdInfo.ArgSetToExec + 1].Set[0],
					O_APPEND | O_CREAT | O_WRONLY                   ,
					0777
				);

			if (outFile < 0)
			{
				perror("Failed to open out file");

				exit(1);
			}

			int dupeResult = dup2(outFile, STDOUT_FILENO);

			if (dupeResult < 0)
			{
				perror("Failed to write to out file.");

				exit(1);
			}

			close(outFile);

			execvp(_argSetToExec->Set[0], _argSetToExec->Set);

			perror("Exec failed");

			exit(-1);
		}
		case C_Pipe:
		{
			if (!CmdFlags.Pipe)
			{
				perror("Indicated to complete a pipe command, however piping. However argument parse failed to reconize valid conditions...");

				exit(-1);
			}

			switch (CmdFlags.PipeStage)
			{
			case IO_Send:
			{
				dup2(PipeIO[OUTPUT], STDOUT_FILENO);

				ClosePipeIO(IO_Both);

				execvp((char *)_argSetToExec->Set[0], (char **)_argSetToExec->Set);

				perror("Exec failed");

				exit(-1);
			}
			case IO_Recieve:
			{
				dup2(PipeIO[INPUT], STDIN_FILENO);

				ClosePipeIO(IO_Both);

				/*char arg[CmdLineBuffer];

				CString *argV;

				size_t currSize = 1024

				argV = malloc()

				while (fgets(arg, CmdLineBuffer, stdin) != NULL)
				{

				}

				execlp((char *)_argSetToExec->Set[0], )*/

				execvp((char *)_argSetToExec->Set[0], (char **)_argSetToExec->Set);

				perror("Exec failed");

				exit(-1);
			}
			default:
			{
				perror("Failed to complete the pipe between commands.");

				exit(-1);
			}
			}
		}
		default:
		{
			break;
		}
		}

		execvp((char *)_argSetToExec->Set[0], (char **)_argSetToExec->Set);

		perror("Exec failed");

		exit(-1);
	}
	case Parent:
	{
		switch (CmdInfo.ArgSets[CmdInfo.ArgSetToExec].Cmd)
		{
		case C_Pipe:
		{
			switch (CmdFlags.PipeStage)
			{
			case IO_Recieve:
			{
				ClosePipeIO(IO_Recieve);

				break;
			}
			case IO_Send:
			{
				ClosePipeIO(IO_Send);

				break;
			}
			case IO_Both:
			{
				break;
			}
			}

			break;
		}
		}

		if (!CmdFlags.Async)
		{
			wait(NULL);
		}
		else
		{
			printf("This is an async call"); cpnl;
		}

		break;
	}
	default:
	{
		perror("Fork failed");

		exit(-1);
	}
	}
}

//Gets the arguments from the command line.
void GetArguments()
{
	CmdInfo.ArgCount = 0;   //Using as the index for the argument array.

	CmdlString CmdLineCopy;  //Keeps a copy of the CmdLine to use for tokenization. Prevents destructive handling of CmdLine.

	strcpy(CmdLineCopy, CmdInfo.CmdLine);

	bool noMoreArguments = false;

	CString delimiters = "\n\t ";

	while (!noMoreArguments)
	{
		switch (CmdInfo.ArgCount)
		{
		case 0:
		{
			CmdInfo.CmdlArgs[CmdInfo.ArgCount] = strtok(CmdLineCopy, delimiters);

			if (IsNull(CmdInfo.CmdlArgs[CmdInfo.ArgCount]))
			{
				noMoreArguments = true;
			}
			else
			{
				CmdInfo.ArgCount++;
			}

			break;
		}
		default:
		{
			CmdInfo.CmdlArgs[CmdInfo.ArgCount] = strtok(NULL, delimiters);

			if (!IsNull(CmdInfo.CmdlArgs[CmdInfo.ArgCount]))
			{
				if (CmdInfo.ArgCount >= Argument_Max)
				{
					perror("To many arguments!\n");

					exit(1);
				}

				CmdInfo.ArgCount++;
			}
			else
			{
				noMoreArguments = true;
			}

			break;
		}
		}
	}
}

//Gets the commands to directly use in execution and if they use of the indicated commands for this asssingment.
void GetCommands()
{
	CmdInfo.ArgSetCount = 0;

	uInt8 argSetBegin = 0;

	uInt8 argIndex = 0;

	bool noMoreCommands = false;

	while (!noMoreCommands)
	{
		if
		(   //Delimiters: < > >> | \0
			ChckForCmd(CmdInfo.CmdlArgs[argIndex], C_RedirectStdIn ) ||
			ChckForCmd(CmdInfo.CmdlArgs[argIndex], C_RedirectStdOut) ||
			ChckForCmd(CmdInfo.CmdlArgs[argIndex], C_AppendStdout  ) ||
			ChckForCmd(CmdInfo.CmdlArgs[argIndex], C_Pipe          )
		)
		{
			for (uInt8 index = 0; argSetBegin < argIndex; argSetBegin++)
			{
				CmdInfo.ArgSets[CmdInfo.ArgSetCount].Set[index] = CmdInfo.CmdlArgs[argSetBegin];

				index++;

				CmdInfo.ArgSets[CmdInfo.ArgSetCount].Count = index;
			}

			CmdInfo.ArgSets[CmdInfo.ArgSetCount].Cmd = GetCmdNum(CmdInfo.CmdlArgs[argIndex]);

			argSetBegin = argIndex + 1;

			CmdInfo.ArgSetCount++;
		}

		argIndex++;

		if (argIndex == CmdInfo.ArgCount)
		{
			noMoreCommands = true;
		}
	}

	if (argSetBegin < argIndex)
	{
		for (uInt8 index = 0; argSetBegin < argIndex; argSetBegin++)
		{
			CmdInfo.ArgSets[CmdInfo.ArgSetCount].Set[index] = CmdInfo.CmdlArgs[argSetBegin];

			index++;

			CmdInfo.ArgSets[CmdInfo.ArgSetCount].Count = index;
		}

		if
		(
			(CmdInfo.ArgSets[CmdInfo.ArgSetCount-1].Cmd == C_AppendStdout  ) ||
			(CmdInfo.ArgSets[CmdInfo.ArgSetCount-1].Cmd == C_RedirectStdIn ) ||
			(CmdInfo.ArgSets[CmdInfo.ArgSetCount-1].Cmd == C_RedirectStdOut)
		)
		{
			CmdInfo.ArgSets[CmdInfo.ArgSetCount].Cmd = C_NoCmd;
		}

		CmdInfo.ArgSetCount++;
	}
}

//Does an fgets command to populate the CmdLine cString. Kill the program if fails.
void GetCommandLine()
{
	if (fgets(CmdInfo.CmdLine, CmdLineBuffer, stdin) == NULL)
	{
		perror("Failed to retrieve command line.");

		exit(1);
	}
}

//Handles execution of command line related functions.
void HandleCommandLine()
{
	GetCommandLine();

	if (!StrIsEmpty(CmdInfo.CmdLine))
	{
		GetArguments();
		GetCommands ();

		if (!IsNull(CmdInfo.CmdlArgs[0]))
		{
			ParseArguments();
		}
	}
	else
	{
		CmdInfo.ArgCount = 0;
	}
}

//Parses the arguments found in CmdlArgs.
void ParseArguments()
{
	#define LastArgument CmdInfo.ArgCount - 1
	#define PreviousArg  index - 1
	#define NextArg      index + 1

	//Check for Async
	if (ChckForCmd(CmdInfo.CmdlArgs[0], C_Exit))   
	{
		exit(0);
	}

	//Check for end program.
	if (ChckForCmd(CmdInfo.CmdlArgs[LastArgument], C_Async))  
	{
		CmdFlags.Async = true;

		CmdInfo.CmdlArgs[LastArgument] = NULL;
	}
	else
	{
		CmdFlags.Async = false;
	}

	//Check for Redirects, Appends, or Pipes.
	for (uInt8 index = 0; index < CmdInfo.ArgCount; index++)
	{
		if (ChckForCmd(CmdInfo.CmdlArgs[index], C_RedirectStdIn))
		{
			if ((index == 0) || IsNull(CmdInfo.CmdlArgs[PreviousArg]))
			{
				perror("Indicated to do a redirect of StrIn but no file was specified");

				exit(-1);
			}

			CmdFlags.RedirectName = CmdInfo.CmdlArgs[PreviousArg];

			CmdFlags.RedirectStdIn = true;
		}
		else if (ChckForCmd(CmdInfo.CmdlArgs[index], C_RedirectStdOut))   //Redirect StdOut Check
		{
			if (IsNull(CmdInfo.CmdlArgs[NextArg]))
			{
				perror("Indicated to do a redirect of StdOut but no file was specified.");

				exit(-1);
			}

			CmdFlags.RedirectName = CmdInfo.CmdlArgs[NextArg];

			CmdFlags.RedirectStdOut = true;
		}
		else if (ChckForCmd(CmdInfo.CmdlArgs[index], C_AppendStdout))   //Append Check
		{
			if (IsNull(CmdInfo.CmdlArgs[NextArg]))
			{
				perror("Indicated to append a file with StdOut, but no file was specified.");

				exit(1);
			}

			CmdFlags.AppendName = CmdInfo.CmdlArgs[NextArg];

			CmdFlags.AppendStdOut = true;
		}
		else if (ChckForCmd(CmdInfo.CmdlArgs[index], C_Pipe))
		{
			if (IsNull(CmdInfo.CmdlArgs[NextArg]))
			{
				perror("Indicated to do a Pipe between commands, but no arguments found after Pipe");

				exit(-1);
			}

			CmdFlags.Pipe = true;

			CmdFlags.PipeStage = IO_Send;
		}
	}

	#undef LastArgument
	#undef PreviousArg
	#undef NextArg
}

void SetRelation()
{
	switch (CurrentProcess.ProcessID)
	{
	case 0:
	{
		CurrentProcess.Type = Child;

		return;
	}
	default:
	{
		CurrentProcess.Type = Parent;

		return;
	}
	}
}

///////////////////////////////////////////////////////////////////////////////////////////////////


//-------------------------------------------------------------------------------Runtime Execution:

int main(int argc, char *argv[])
{
	exist = true;

	cpnl;

	while (exist)
	{
		printf("COP4338$ ");

		HandleCommandLine();

		if (CmdInfo.ArgCount > 0)   //Make sure there are arguments to execute.
		{
			CmdInfo.ArgSetToExec = 0;

			if (CmdFlags.Pipe)
			{
				pipe(PipeIO);
			}

			for (; CmdInfo.ArgSetToExec < CmdInfo.ArgSetCount; CmdInfo.ArgSetToExec++)
			{
				if (CmdInfo.ArgSets[CmdInfo.ArgSetToExec].Cmd != C_NoCmd)
				{
					ExecuteCommand(&(CmdInfo.ArgSets[CmdInfo.ArgSetToExec]));
				}
			}
		}

		CleanDeclares();

		cpnl; cpnl; 
	}

	return 0;
}

///////////////////////////////////////////////////////////////////////////////////////////////////

#undef _GNU_Source