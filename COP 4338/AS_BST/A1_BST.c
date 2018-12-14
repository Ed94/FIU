/*
Edward R. Gonzalez 499406

Program name: bstsort

The program gets text data from either a file or from standard input.
For every line found, the line is stored in a node of a binary search tree as a key.
Once the input data has been exhausted, the BST is output to a file or standard out using in-order traversal
to have the lines sorted either with case sensitivity or without.

Index of Important Functions:

All functions are ordered alphabetically by return type then by function name.

175 : LoadFile
373 : CreateNode
390 : InsertNode
436 : CompareString_CaseSensitive
485 : CompareStrings_CaseInsensitive
591 : CleanDeclares
640 : CleanFileP
659 : ClearString
673 : CloseFileP
687 : ClearNodes
768 : GetFlagsAndValues (AKA getopt generic function)
932 : OutputSortedToFile
961 : OutputSortedToStdout
1094: PopulateBSTByFile
1138: PopulateBSTByUser
1218: SetupGlobalDeclares
1268: main

Affirmation of originality:
I affirm that I wrote this
program myself without any help form any
other people or sources from the internet.
*/

//Includes
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

#if defined(__GNUC__)  //Using built in standard for getopt on ocelot's red hat Linux GCC distribution.
#include <getopt.h>
//#elif defined(__GNUC__)   //What most gnu installations use on Linux.
//#include <unistd.h>
#elif defined(_MSC_VER)   //Using https://github.com/skandhurkat/Getopt-for-Visual-Studio/blob/master/getopt.h
#include "vs_getopt.h"
#endif

//Global Macros

#define Standard_Input  "Standard Input"    //Just an easy way to put out string.
#define Standard_Output "Standard Output"

//TypeDefs

typedef          char*              CString   ;   //This a pointer, handle as such. Just a character pointer alias.
typedef unsigned int                uInt      ;   //Just an alias.
typedef          enum   FModeEnum   FileMode  ;   //File mode buffer is set to. ( Used for fopen() )
typedef          enum   StrCmprEnum StrCompare;   //Stores nodeData of a comparison between strings.
typedef          struct strStruct   string    ;   //String that holds its own length with functions that better abstract string use from regular cStrings.
typedef          struct filePStruct FileP     ;   //File Plus: A structure for maintaining information related to a file.
typedef          struct nodeStruct  Node      ;   //A node for a binary search tree.

//Enums
enum FModeEnum 
{ 
	FMode_Read      ,   //Read only mode. Can only be used if the file buffer found a file.
	FMode_Write     ,   //Write only mode, 
	FMode_Append    ,   //If the file exists, it opens the file at the end o file. Otherwise creates a file. (No reading functionality)
	FMode_ReadPlus  ,   //Open a file buffer with read and write capabilities.
	FMode_WritePlus ,   //Create a file buffer with read and write functionality. If a file exist with same name, file contents is discarded.
	FMode_AppendPlus,   //Same as write plus however, writing is always the end of file, but reading can be anywhere.
	FMode_STDIN     ,
	FMode_STDOUT    ,
	FMode_Unset
};

enum StrCmprEnum
{
	StrCmpr_Before,   //String would come before.
	StrCmpr_Same  ,   //Strings are the same.
	StrCmpr_After ,   //String would come after.
	StrCmpr_Unset
};

//Structs

//Struct used to define the string type.
struct strStruct
{
	CString cString;   //Regular c string.

	uInt length;   //Stores the length of the cString. (Number of characters) 
};

//Struct used to the define File Plus.
struct filePStruct
{
	bool IsOpen;   //States whether or not the file is loaded into the buffer.

	FILE *fileBuffer;   //Pointer to file buffer loaded into the heap.

	FileMode mode;   //Stores the operating state of the file buffer.

	string contents;   //Content of file.
	string name    ;   //Name of the file.

	uInt size;   //Size of file (in characters).
};

//Struct used to define a Node for a binary search tree.
struct nodeStruct
{
	uInt lineOccurrence;   //A count of many keys have been included up to this key.

	string key;   //Data type for this implementation of a BST. Contains a line.

	Node *left ;   //Reference to left child for this Node.
	Node *right;   //Reference to right child for this Node.
};

#define StringInit { NULL, 0 }   //Wanted to use this to initialize null string structs, never fully worked out.

//Declares

//Static
const static bool POPULATE_BY_FILE = true;   //Setup via programmer. 
                                              //States whether or not to populate BST using content string of file or by reading file directly.
                                              //Must be set to true before turning in assignment.

static bool C_Flag ;   //Denotes to sort with or without case sensitivity.
static bool O_Flag ;   //Denotes to use a user specified output file.
static bool SI_Flag;   //Denotes to use standard input.

static FileP InputFile ;   //File to get lines to sort from.
static FileP OutputFile;   //File to store sorted lines.

static string *SortedLines;   //Array of string containing the lines from the input file sorted.

static Node *Root = NULL;   //Root of the binary search tree.

static uInt SLineCount;   //Line count for the sorted lines array.

//Functions

//***************************************Forward Declarations**********************************************************
bool LoadFilePWithName(string _fileName, FileP *_fPlusToStore, FileMode _modeToUse, bool _keepBufferOpen);

StrCompare CompareStrings_CaseInsensitive(string *_stringToCmpr, string *_stringToCmprAgainst);
StrCompare CompareString_CaseSensitive   (string *_stringToCmpr, string *_stringToCmprAgainst);

void BufferString            (      string  *_ptrToString        ,       int       _amountToBuffer                        );
void ClearFileP              (      FileP   *_filePToInit                                                                 );
void ClearString             (      string  *_stringToInit                                                                );
void ClearNodes              (      Node    *_focalNode                                                                   );
void CloseFileP              (      FileP   *_filePToClose                                                                );
void DebugGFAV																			                                 ();
void DebugMockGFAV           (      CString  _rawArgs                                                                     );
void FileToString            (      FILE    *_ptrtofile          ,       int       _charactercount, string *_stringtostore);
void GetFlagsAndValues       (const int     *_argCount           , char          **_arguments                             );
void PrintArgumentsAndResults(const int     *_argCount           , const char    **_arguments                             );
void PrintFilePContents      (      FileP   *_fileContentsToPrint                                                         );
void SetString               (      string  *_ptrToString        ,       CString   _cStringToSet                          );

uInt GetFileCharCount(FILE   *_ptrToFile);
//*********************************************************************************************************************

//Checks if a values is within a specific range;
//_value: Value to check.
//_start: Start of range.
//_end: End of range.
bool CheckIfInRange(int _value, int _start, int _end)
{
	return (_value >= _start) && (_value <= _end) ? true : false;
}

//Loads the specified file. Assumes name is within _fPlusToStore.
//_fPlusToStore: FileP to load.
//_modeToUse: File mode to use when opening the buffer.
//_keepBufferOpen: Whether or not to keep the buffer open for the file after loading.
bool LoadFileP(FileP *_fPlusToStore, FileMode _modeToUse, bool _keepBufferOpen)
{
	return LoadFilePWithName(_fPlusToStore->name, _fPlusToStore, _modeToUse, _keepBufferOpen);
}

//Loads the specified file with a given name.
//_fileName: Filename to load into _fPlusToStore
//_fPlusToStore: File to load with given name.
//_modeToUse: File mode to use when opening the buffer.
//_keepBufferOpen: Whether or not to keep the buffer open for the file after loading.
bool LoadFilePWithName(string _fileName, FileP *_fPlusToStore, FileMode _modeToUse, bool _keepBufferOpen)
{
	switch (_modeToUse)
	{
	case FMode_Read:
	{
		_fPlusToStore->fileBuffer = fopen(_fileName.cString, "r");

		break;
	}
	case FMode_Append:
	{
		_fPlusToStore->fileBuffer = fopen(_fileName.cString, "a");

		break;
	}
	case FMode_Write:
	{
		_fPlusToStore->fileBuffer = fopen(_fileName.cString, "w");

		break;
	}
	case FMode_ReadPlus:
	{
		_fPlusToStore->fileBuffer = fopen(_fileName.cString, "r+");

		break;
	}
	case FMode_WritePlus:
	{
		_fPlusToStore->fileBuffer = fopen(_fileName.cString, "w+");

		break;
	}
	case FMode_AppendPlus:
	{
		_fPlusToStore->fileBuffer = fopen(_fileName.cString, "a+");

		break;
	}
	case FMode_STDIN:
	{
		_fPlusToStore->fileBuffer = stdin;

		_fPlusToStore->IsOpen = true;

		_fPlusToStore->mode = FMode_STDIN;

		return true;

		break;
	}
	case FMode_STDOUT:
	{
		_fPlusToStore->fileBuffer = stdin;

		_fPlusToStore->IsOpen = true;

		_fPlusToStore->mode = FMode_STDOUT;

		break;
	}
	case FMode_Unset:
	{
		printf("Not a valid file load option.");

		return false;
	}
	}

	if (_fPlusToStore->fileBuffer != NULL)
	{
		_fPlusToStore->size = GetFileCharCount(_fPlusToStore->fileBuffer);

		_fPlusToStore->mode = _modeToUse;

		FileToString(_fPlusToStore->fileBuffer, _fPlusToStore->size, &_fPlusToStore->contents);

		_fPlusToStore->name = _fileName;
	}
	else
	{
		switch (_modeToUse)
		{
		case FMode_Read:
		{
			fprintf(stderr, "LoadToFilePlus: Could not open a file to load into file buffer.");

			break;
		}
		default:
		{
			fprintf(stderr, "LoadToFilePlus: Could not open or create a file to load into file buffer.");

			break;
		}
		}

		return false;
	}

	if (!_keepBufferOpen)
	{
		CloseFileP(_fPlusToStore);
	}
	else
	{
		_fPlusToStore->IsOpen = true;
	}

	return true;
}

//Loads a string representing a file from user input into a file plus contents.
//_fileToSet: File to set to user input.
bool LoadStdinToFileP(FileP *_fileToSet)
{
	printf("No file name specified. Resorting to user input.\n\n");

	CString line = malloc(sizeof(char) * 100);

	do 
	{
		printf("\nPlease enter a line to be stored as input. Leave blank to finish input. (Must be within 100 characters long)\n");

		fgets(line, sizeof(char) * 100, stdin);

		if (line[strlen(line) -1] != '\n')
		{
			while ((getchar()) != '\n');

			strcat(line, "\n");
		}

		if (_fileToSet->contents.cString == NULL)
		{
			SetString(&_fileToSet->contents, line);
		}
		else
		{
			strcat(_fileToSet->contents.cString, line);

			_fileToSet->contents.length = (uInt)strlen(_fileToSet->contents.cString);
		}

		line[strlen(line) - 1] = '\0';

	} while (strcmp(line, "\0") != 0);

	_fileToSet->contents.length = (uInt)strlen(_fileToSet->contents.cString);

	_fileToSet->size = _fileToSet->contents.length;

	_fileToSet->mode = FMode_Read;

	SetString(&_fileToSet->name, Standard_Input);

	_fileToSet->IsOpen = false;

	return true;
}

//Loads a file buffer to stdout. Note, this FileP will be open and must be closed before exiting.
bool LoadStdoutFileP(FileP *_fileToSet)
{
	_fileToSet->fileBuffer = stdout;

	if (_fileToSet->fileBuffer == NULL)
	{
		printf("Failed to load standard output to file.");

		return false;
	}

	_fileToSet->mode = FMode_Write;

	_fileToSet->size = 0U;

	SetString(&_fileToSet->name, Standard_Output);

	_fileToSet->IsOpen = true;

	return true;
}

//Writes to file buffer.
bool WriteToFileP(FileP *_fileToWriteTo, bool _keepBufferOpen)
{
	fwrite(_fileToWriteTo->contents.cString, sizeof(char), _fileToWriteTo->size - 1, _fileToWriteTo->fileBuffer);
	//fprintf(_fileToWriteTo->fileBuffer, "%s", _fileToWriteTo->contents.cString);

	if (!_keepBufferOpen)
	{
		CloseFileP(_fileToWriteTo);
	}

	return true;
}

//Creates a node with the given line as key.
Node* CreateNode(string _line)
{
	Node *newNode = (Node *)malloc(sizeof(Node));

	newNode->lineOccurrence = 1;

	SetString(&newNode->key, _line.cString);

	newNode->left  = NULL;
	newNode->right = NULL;

	return newNode;
}

//Inserts a a new node with key of line to BST, or increments the lineOccurrence value of node.
Node* InsertNode(Node *_focalNode, string _line)
{
	if (_focalNode == NULL)
	{
		return CreateNode(_line);
	}

	StrCompare focal_VS_Line = StrCmpr_Same;

	if (C_Flag)
	{
		focal_VS_Line = CompareString_CaseSensitive(&_line, &_focalNode->key);

		if (focal_VS_Line == StrCmpr_Before)
		{
			_focalNode->left = InsertNode(_focalNode->left, _line);
		}
		else if (focal_VS_Line == StrCmpr_After)
		{
			_focalNode->right = InsertNode(_focalNode->right, _line);
		}
		else
		{
			_focalNode->lineOccurrence++;
		}
	}
	else
	{
		focal_VS_Line = CompareStrings_CaseInsensitive(&_line, &_focalNode->key);

		if (focal_VS_Line == StrCmpr_Before)
		{
			_focalNode->left = InsertNode(_focalNode->left, _line);
		}
		else if (focal_VS_Line == StrCmpr_After)
		{
			_focalNode->right = InsertNode(_focalNode->right, _line);
		}
		else
		{
			_focalNode->lineOccurrence++;
		}
	}

	return _focalNode;
}


//Compares two strings. Value is in the perspective of _stringToCmpr. (Case-Sensitive)
StrCompare CompareString_CaseSensitive(string *_stringToCmpr, string *_stringToCmprAgainst)
{
	StrCompare result = StrCmpr_Unset;

	uInt index = 0U;

#define HaveCharacters (index < _stringToCmpr->length) && (index < _stringToCmprAgainst->length)

	while (HaveCharacters)
	{
		result = StrCmpr_Same;

		if (_stringToCmpr->cString[index] < _stringToCmprAgainst->cString[index])
		{
			result = StrCmpr_Before;   //_stringToCmpr has a character in the same position to the string against so it goes before.
		}
		else if (_stringToCmpr->cString[index] > _stringToCmprAgainst->cString[index])
		{
			result = StrCmpr_After;   //_stringToCmpr to
		}

		index++;

		if (result != StrCmpr_Same)   //this needs to be fixed or she will take off points...
		{
			break;   //Why did it not leave...
		}

		//They are the same so don't change nodeData.
	}

	if (result == StrCmpr_Same)
	{
		if (_stringToCmpr->length < _stringToCmprAgainst->length)
		{
			result = StrCmpr_Before;
		}
		else if (_stringToCmpr->length > _stringToCmprAgainst->length)
		{
			result = StrCmpr_After;
		}

		//Both lengths are the same and all characters are the same. Thus these strings match perfectly.
	}

	return result;
}

//Compares two strings. Value is in the perspective of _stringToCmpr. (Case-Insensitive)
StrCompare CompareStrings_CaseInsensitive(string *_stringToCmpr, string *_stringToCmprAgainst)
{
	char currChar        = _stringToCmpr       ->cString[0],
		 currCharAgainst = _stringToCmprAgainst->cString[0] ;

	StrCompare result = StrCmpr_Same;

	uInt index = 0U;

#define HaveCharacters (index < _stringToCmpr->length) && (index < _stringToCmprAgainst->length)

	while (HaveCharacters)
	{
		if (CheckIfInRange(currChar, 'A', 'z') && CheckIfInRange(currCharAgainst, 'A', 'z'))
		{
			currChar        < 97 ? currChar        += 32 : 0;
			currCharAgainst < 97 ? currCharAgainst += 32 : 0;

			if (currChar < currCharAgainst)
			{
				result = StrCmpr_Before;
			}
			else if (currChar > currCharAgainst)
			{
				result = StrCmpr_After;
			}

			//They are the same letter, treat as the same.
		}
		else   //Not in a letters range, so use a lexicographic comparison.
		{
			if (currChar < currCharAgainst)
			{
				result = StrCmpr_Before;   //_stringToCmpr has a character in the same position to the string against so it goes before.
			}
			else if (currChar > currCharAgainst)
			{
				result = StrCmpr_After;   //_stringToCmpr to
			}
		}

		if (result != StrCmpr_Same)   //this needs to be fixed or she will take off points...
		{
			break;   //Why did it not leave...
		}

		index++;

		currChar        = _stringToCmpr       ->cString[index];
		currCharAgainst = _stringToCmprAgainst->cString[index];

		//They are the same up to one of their max lengths so don't change nodeData.
	}

	if (result == StrCmpr_Same)
	{
		if (_stringToCmpr->length < _stringToCmprAgainst->length)
		{
			result = StrCmpr_Before;
		}
		else if (_stringToCmpr->length > _stringToCmprAgainst->length)
		{
			result = StrCmpr_After;
		}

		//Both lengths are the same and all characters are the same. Thus these strings match perfectly.
	}

	return result;
}

//Provides a string representation for bool value.
string GetBoolStr(bool _boolValue)
{
	string boolStr = StringInit;

    if (_boolValue == true)
    {
		boolStr.cString = "true";

		boolStr.length = 4U;
    }
    else
    {
		boolStr.cString = "false";

		boolStr.length = 5U;
    }

	return boolStr;
}

//Allocates a string to the specified amount.
void BufferString(string *_ptrToString, int _amountToBuffer)
{
	_ptrToString->cString = malloc(sizeof(char)*(_amountToBuffer));

	_ptrToString->length = _amountToBuffer;
}

//Clears out the declares.
void CleanDeclares()
{
	ClearFileP(&InputFile );
	ClearFileP(&OutputFile);

	if (SortedLines != NULL)
	{
		for (uInt index = 0; index < SLineCount; index++)
		{
			if (SortedLines[index].cString != NULL)
			{
				free(SortedLines[index].cString);
			}
		}

		SLineCount = 0U;

		free(SortedLines);
	}

	if (Root != NULL)
	{
		ClearNodes(Root);
	}
}

//Resets all flags and values to false or not set.
void ClearFlagsAndValues()
{
    C_Flag = false;
    O_Flag = false;

	ClearFileP(&InputFile );
	ClearFileP(&OutputFile);

	if (SortedLines != NULL && SLineCount > 0U)
	{
		for (uInt index = 0U; index < SLineCount; index++)
		{
			string line = SortedLines[index];

			ClearString(&line);
		}
	}
}

//Clears a file plus of its information and resets it.
void ClearFileP(FileP *_filePToInit)
{
	_filePToInit->IsOpen = false;

	if (_filePToInit->fileBuffer != NULL)
	{
		fclose(_filePToInit->fileBuffer);
	}

	_filePToInit->mode = FMode_Unset;

	ClearString(&_filePToInit->contents);
	ClearString(&_filePToInit->name    );

	_filePToInit->size = 0U;
}

//Frees the memory allocated to nodes of the BST recursively form a chosen focal node.
//This is not a delete, this will free memory to preserving order. (Passes in-order traversal)
void ClearNodes(Node *_focalNode)
{
	if (_focalNode == NULL)
	{
		return;
	}

	ClearNodes(_focalNode->left);
	ClearNodes(_focalNode->right);

	ClearString(&_focalNode->key);

	_focalNode->lineOccurrence = 0U;

	free(_focalNode);
}

//Clears a string and resets it.
void ClearString(string *_stringToInit)
{
	if (_stringToInit != NULL)
	{
		if (_stringToInit->cString != NULL)
		{
			free(_stringToInit->cString);
		}
	}
}

//Closes a file plus.
void CloseFileP(FileP *_filePToClose)
{
	fclose(_filePToClose->fileBuffer);

	_filePToClose->mode = FMode_Unset;

	_filePToClose->size = 0U;

	_filePToClose->IsOpen = false;
}

//Intended to properly concatenate two strings. (Does not work right with fwrite don't use.
void ConcatStrings(string *_string, string *_stringToAdd)
{
	strcat(_string->cString, _stringToAdd->cString);

	_string->length = _string->length + _stringToAdd->length;
}

//Used for testing.
void Debug()
{
	DebugGFAV();
}

//Attempted to test every possible good case of the GFAV function.
void DebugGFAV()
{
	DebugMockGFAV(NULL  );   //Empty Case
	DebugMockGFAV("c"   );   //-c
	DebugMockGFAV("I"   );   //testIn.txt
	DebugMockGFAV("cI"  );   //-c testIn.txt
	DebugMockGFAV("cO"  );   //-c testOut.txt
	DebugMockGFAV("oO"  );   //-o testOut.txt
	DebugMockGFAV("oI"  );   //-o testIn.txt
	DebugMockGFAV("coO" );   //-c -o testOut.txt
	DebugMockGFAV("oOc" );   //-o testOut.txt -c
	DebugMockGFAV("oOI" );   //-o testOut.txt testIn.txt
	DebugMockGFAV("coOI");   //-c -o testOut.txt testIn.txt
	DebugMockGFAV("oOcI");   //-o testOut.txt -c testIn.txt
}

//Simulates flags and values for the GetFlagsAndValues() function.
void DebugMockGFAV(CString _rawArgs)
{
	bool mC = false,
		 mO = false, 
		 fO = false, 
		 fI = false ;

	uInt argCount = 1U;

	CString *arguments = malloc(sizeof(CString*) * argCount);

	arguments[0] = "Test Path";

	if (_rawArgs != NULL)
	{
		argCount += (uInt)strlen(_rawArgs);

		arguments = realloc(arguments, sizeof(CString*) * argCount);
		
		for (uInt index = 0U, aIndex = 1U; index < argCount - 1; index++, aIndex++)
		{
			if (_rawArgs[index] == 'c')
			{
				arguments[aIndex] = "-c";
				
				mC = true;
			}
			else if (_rawArgs[index] == 'o')
			{
				arguments[aIndex] = "-o";
				
				mO = true;
			}
			else if (_rawArgs[index] == 'O')
			{
				arguments[index + 1] = "testOut.txt"; 
				
				fO = true;
			}
			else if (_rawArgs[index] == 'I')
			{
				arguments[aIndex] = "testIn.txt";
				
				fI = true;
			}
		}
	}

	//arguments[(argCount - 1)] = NULL;

	for (uInt index = 0; index < argCount; index++)
	{
		printf("Argument %d: %s\n", index, arguments[index]);
	}

	printf("Running mock GFAV:\n");

	printf("Values setup in mock: mC: %s, mO: %s, fO: %s, fI: %s\n", GetBoolStr(mC).cString, GetBoolStr(mO).cString, GetBoolStr(fO).cString, GetBoolStr(fI).cString);

	GetFlagsAndValues((const int*)&argCount, (char**)arguments);

	PrintArgumentsAndResults((const int *)&argCount, (const char **)arguments);

	ClearFlagsAndValues();
}

//Sets contents of a file buffer to a specified string.
void FileToString(FILE *_ptrtofile, int _charactercount, string *_stringtostore)
{
	BufferString(_stringtostore, _charactercount);

	for (int index = 0u; index < (_charactercount - 1); _stringtostore->cString[index++] = fgetc(_ptrtofile));

	_stringtostore->cString[_charactercount - 1] = '\0';

	rewind(_ptrtofile);
}

/*
Info on function:
Uses GetOpt to setup flags, get the SubString if any provided, and the file to analyze.
Parameters:
_argCount : A pass of the amount of arguments given.
_arguments: A pass of the arguments.                
*/
void GetFlagsAndValues(const int *_argCount, char **_arguments)
{
    //https://www.gnu.org/software/libc/manual/html_node/Using-Getopt.html#Using-Getopt See for docs on getopt use. (Its the GNU docs)
    extern int   optind;
    extern char *optarg;

	//Reset the getopt arguments to their original values in case this function was run before.

    optind = 1;

	if (optarg != NULL)
	{
		free(optarg);  
	}

    bool error = false;

    int options = -1;

	string *flags = NULL; SetString(flags, "co:");

    static char usage[] = "usage: %s [-c] [-o output_file_name] input_file_name\n";

#define SetOptions      ( options = getopt( (int)*_argCount, _arguments, (char*)flags->cString ) )   //Updates options every time its called.
#define NoMoreArguments -1

    while (SetOptions != NoMoreArguments)
    {
        switch (options)
        {
        case 'c':
        {
            C_Flag = true;

            break;
        }
        case 'o':
        {
            O_Flag = true;

			SetString(&OutputFile.name, optarg);

            break;
        }
        case '?':
        {
            printf("\n%s\n", "Options switch did not find a defined case.");

            error = true;

            break;
        }
        default:
        {
            printf("\n%s\n", "Options switch did not find a defined case.");

            error = true;

            break;
        }
        }
    }

	ClearString(flags);

    if (error == true)
    {
        fprintf(stderr, usage, _arguments[0]);

        exit(1);
    }

	if (optind < *_argCount)   //User defined a specified input file.
	{
		SetString(&InputFile.name, _arguments[optind]);
	}
	else
	{
		SI_Flag = true;
	}

	free(optarg);

#undef MissingFilename
#undef NoMoreArguments
#undef SetOptions
}

//Handles an input source if POPULATE_BY_FILE is false.
void HandleInputSource()
{
	if (SI_Flag)
	{
		if (!LoadStdinToFileP(&InputFile))
		{
			exit(1);
		}
	}
	else
	{
		if (!LoadFileP(&InputFile, FMode_Read, false))   //Get a string from the input file buffer and the close it.
		{
			exit(1);
		}
	}

	PrintFilePContents(&InputFile);
}

//Handles an input source if POPULATE_BY_FILE is false.
void HandleOutputSource()
{
	if (!O_Flag)
	{
		if (!LoadStdoutFileP(&OutputFile))
		{
			exit(1);
		}

		SetString(&OutputFile.contents, SortedLines[1].cString);

		for (uInt index = 1U; index < SLineCount; index++)
		{
			strcat(OutputFile.contents.cString, SortedLines[index].cString);

			OutputFile.contents.length = OutputFile.contents.length + SortedLines[index].length;
		}

		OutputFile.size = OutputFile.contents.length;

		printf("File Contents of Standard Out:\n\n");

		WriteToFileP(&OutputFile, true);

		CloseFileP(&OutputFile);
	}
	else
	{
		if (!LoadFileP(&OutputFile, FMode_Write, true))
		{
			exit(1);
		}

		for (uInt index = 0U; index < SLineCount; index++)
		{
			strcat(OutputFile.contents.cString, SortedLines[index].cString);

			OutputFile.contents.length = OutputFile.contents.length + SortedLines[index].length;
		}

		OutputFile.size = OutputFile.contents.length;

		WriteToFileP(&OutputFile, false);

		PrintFilePContents(&OutputFile);
	}
}

//Handles an output to a file if POPULATE_BY_FILE is true.
//Does so using in-order traversal of binary search tree.
//Every time proper node is reached, the key's contents is written to the output file.
void OutputSortedToFile(Node *_focalNode)
{
	if (!OutputFile.IsOpen && !LoadFileP(&OutputFile, FMode_Write, true))
	{
		CleanDeclares();

		exit(1);
	}

	if (_focalNode == NULL)
	{
		return;
	}

	OutputSortedToFile(_focalNode->left);

	size_t lineBuffer = sizeof(char)*(_focalNode->key.length + 16);

	CString nodeData = malloc(lineBuffer);

	sprintf(nodeData, "%d     %s", _focalNode->lineOccurrence, _focalNode->key.cString);

	fputs(nodeData, OutputFile.fileBuffer);

	free(nodeData);

	OutputSortedToFile(_focalNode->right);
}

//Hanldes the output to standard out if POPULATE_BY_FILE is true;
void OutputSortedToStdout(Node *_focalNode)
{
	if (_focalNode == NULL)
	{
		return;
	}

	if (_focalNode == Root)
	{
		printf("File Contents for Standard Out:\n\n");
	}

	OutputSortedToStdout(_focalNode->left);

	size_t lineBuffer = sizeof(char)*(_focalNode->key.length + 16);

	CString nodeData = malloc(lineBuffer);

	sprintf(nodeData, "%d     %s", _focalNode->lineOccurrence, _focalNode->key.cString);

	fputs(nodeData, stdout);

	free(nodeData);

	OutputSortedToStdout(_focalNode->right);
}

//Debug function to see if getopt worked properly.
void PrintArgumentsAndResults(const int *_argCount, const char **_arguments)
{
    printf("Argument count: %d\n", *_argCount);

    printf("Arguments: ");

    for (int index = 0; index < *_argCount; index++)
    {
        printf("\n%s", _arguments[index]);
    }

    printf("\n");

    printf("%d%d\n", C_Flag, O_Flag);

    printf("C              : %s\n", GetBoolStr(C_Flag).cString);
    printf("O              : %s\n", GetBoolStr(O_Flag).cString);
    printf("Output Filename: %s\n", OutputFile.name   .cString);
	printf("Input  Filename: %s\n", InputFile .name   .cString);

    printf("\n");
}

//Prints the contents of a file plus.
void PrintFilePContents(FileP *_fileContentsToPrint)
{
	if (_fileContentsToPrint->contents.cString != NULL)
	{
		if (_fileContentsToPrint->name.cString != NULL)
		{
			printf("File Contents of %s: \n\n", _fileContentsToPrint->name.cString);
		}
		else
		{
			printf("File Contents of %s: \n\n", "_UnknownFileName_");
		}

		printf("%s\n\n\n", _fileContentsToPrint->contents.cString);
	}
	else
	{
		if (_fileContentsToPrint->name.cString != NULL)
		{
			if (LoadFileP(_fileContentsToPrint, FMode_Read, false))
			{
				if (_fileContentsToPrint->name.cString != NULL)
				{					
					printf("File Contents of %s: \n\n", _fileContentsToPrint->name.cString);
				}
				else
				{
					printf("File Contents of %s: \n\n", "_UnknownFileName_");
				}
			}
			else
			{
				printf("Failed to load a file to print its contents. (There was a name)");
			}
		}
		else
		{
			printf("Print file. It is not loaded, and does not have content, nor a name.");
		}
	}
}

//Populates the binary search tree by using the content string of a File plus.
void PopulateBSTByContent()
{
	if (InputFile.contents.cString != NULL)
	{
		CString sourceRef = strdup(InputFile.contents.cString);

		CString delimiter = "\n";

		CString possibleLine = strtok(sourceRef, delimiter);
		
		string currentLine;
			
		if (possibleLine != NULL)
		{
			SetString(&currentLine, possibleLine);

			Root = InsertNode(Root, currentLine);
		}
		
		while (possibleLine != NULL)
		{
			possibleLine = strtok(NULL, delimiter);

			if (possibleLine != NULL)
			{
				SetString(&currentLine, possibleLine);

				InsertNode(Root, currentLine);
			}
		}
	}
	else
	{
		printf("Could not populate binary search tree. Input file contents was null.");

		CleanDeclares();

		exit(1);
	}
}

//Populates a binary search tree directly from the file.
void PopulateBSTByFile()
{
	if (!LoadFileP(&InputFile, FMode_Read, true))
	{
		CleanDeclares();

		exit(1);
	}

	string line; 

	do
	{
		BufferString(&line, 100);

		fgets(line.cString, sizeof(char) * 100, InputFile.fileBuffer);

		while (line.cString[0] == '\n')
		{
			fgets(line.cString, sizeof(char) * 100, InputFile.fileBuffer);
		}

		line.length = (uInt)strlen(line.cString);

		if (line.cString[line.length - 1] != '\n')
		{
			line.cString[line.length - 1] = '\n';
		}

		if (line.cString != NULL)
		{
			if (Root == NULL)
			{
				Root = InsertNode(Root, line); 
			}
			else
			{
				InsertNode(Root, line);
			}
		}

		ClearString(&line);

	} while (!feof(InputFile.fileBuffer));
}

//Populates a binary search tree using standard out.
void PopulateBSTByUser()
{
	if (!LoadFileP(&InputFile, FMode_STDIN, true))
	{
		CleanDeclares();

		exit(1);
	}

	printf("No file name specified. Resorting to user input.\n\n");

	string *line = NULL, *ref = NULL;

	do
	{
		ClearString(ref);
		
		BufferString(line, 100);

		printf("\nPlease enter a line to be stored as input. Leave blank to finish input. (Must be within 100 characters long)\n");

		fgets(line->cString, sizeof(char) * 100, stdin);

		line->length = (uInt)strlen(line->cString);

		if (line->cString[line->length - 1] != '\n')
		{
			while ((getchar()) != '\n');

			strcat(line->cString, "\n");
		}

		if (line->cString[0] != '\n')
		{
			if (Root == NULL)
			{
				Root = InsertNode(Root, *line);
			}
			else
			{
				InsertNode(Root, *line);
			}
		}

		ref->cString = strdup(line->cString);

		ref->length  = line->length;

		ClearString(line);

	} while (strcmp(ref->cString, "\n") != 0);

	ClearString(ref);
}

//Populates the sorted lines string array using the binary search tree. (Uses in-order traversal recursive)
void PopulateSortedLines(Node *_focalNode, uInt *_ptrToCounter)
{
	if (_focalNode == NULL)
	{
		return;
	}

	PopulateSortedLines(_focalNode->left, _ptrToCounter);

	*_ptrToCounter += 1;

	SortedLines = realloc(SortedLines, sizeof(string) * *_ptrToCounter);

	size_t lineBuffer = sizeof(char)*(_focalNode->key.length + 16);   //Determines a buffer for snprintf by taking into account key length,
	                                                                  //and allowing for a 16 character buffer for the line occurrence.

	SortedLines[*_ptrToCounter - 1].cString = malloc(lineBuffer);

	sprintf(SortedLines[*_ptrToCounter - 1].cString, "%d     %s\n", _focalNode->lineOccurrence, _focalNode->key.cString);

	SortedLines[*_ptrToCounter - 1].length = (uInt)strlen(SortedLines[*_ptrToCounter - 1].cString);

	PopulateSortedLines(_focalNode->right, _ptrToCounter);
}

//Used with string type. Automatically sets up the string type with the given character array.
//_ptrToString: String to set.
//_cStringToSet: CString to set the string to.
void SetString(string *_ptrToString, CString _cStringToSet)
{
		_ptrToString->cString =       strdup((      char *)_cStringToSet);
		_ptrToString->length  = (uInt)strlen((const char *)_cStringToSet);
}

//Initializes all the global declares.
void SetupGlobalDeclares()
{
	C_Flag = false;
	O_Flag = false;

	ClearFileP(&InputFile );   //Clear file also sets various enum's and bools to proper values.
	ClearFileP(&OutputFile);

	SortedLines = malloc(sizeof(string)); 
	
	SLineCount = 0U;
}

//Not implemented.  TODO: Either finish this later, or get rid of it.
uInt GetCharcterCountToTerminator(CString _string, char _terminator)   //I just realized this was so i could make the string...
{
	uInt charCount = 0;

	return charCount;
}

//Gets the character count of a file.
uInt GetFileCharCount(FILE *_ptrToFile)
{
	uInt charCount = 1U;   //Starts at 1 to include EOF.

	if (_ptrToFile == NULL)
	{
		printf("Cannot get characters. No file open. Returning -1");

		return -1;
	}

	char character;

#define CurrentCharacter (character = fgetc(_ptrToFile))

	while (CurrentCharacter != EOF)
	{
		charCount++;
	}

#undef CurrentCharacter

	rewind(_ptrToFile);

	return charCount;
}


//Runtime Execution
int main(int argc, char** argv)
{
	SetupGlobalDeclares();

	GetFlagsAndValues((const int *)&argc, argv);   //Using getopt in here.

	if (POPULATE_BY_FILE)   //Properly sets the data from either the in file or stdin to BST then out file or stdout.
	{
		SI_Flag ? PopulateBSTByUser     () : PopulateBSTByFile       ();
		O_Flag  ? OutputSortedToFile(Root) : OutputSortedToStdout(Root);

		OutputFile.IsOpen ? CloseFileP(&OutputFile) : 0;
	}
	else   //No longer using this, was using a string middleman between the file and the BST.
	{
		HandleInputSource();

		PopulateBSTByContent();

		PopulateSortedLines(Root, &SLineCount);

		HandleOutputSource();
	}

 	CleanDeclares();

	exit(0);
}

#undef Standard_Input
#undef Standard_Output