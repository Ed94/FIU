/*
Edward R. Gonzalez 499406

This program is able to open and analyze a text file to find out various info the users specifies:
It will always print the file after making a string from the text.
User can specify on command line to get the word counter.
User can specify on command line to get the words printed in sorted order.
User can specify on command line to get the number of occurrences of a specified substring.

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

//TypeDefs
typedef unsigned long long int uInt64;   //Just a shorthand.

typedef char* string;   //This is pointer, if you want the value use a dereference.

//Declares - Static
static bool C_Flag = false;   //Word count flag.
static bool S_Flag = false;   //Sort words flag.
static bool F_Flag = false;   //Substring occurrence flag.

static FILE *FilePtr = NULL;   //Pointer to the file loaded into heap.

static string  FileName    = NULL;   //Name of the file within the same directory as executable to analyze.
static string  FileString  = NULL;   //A string of the entire text file's contents.
static string  SubString   = NULL;   //Substring or word to find occurrence of.
static string *WordData    = NULL;   //Contains all the words found by GetWords.
static string *WordsSorted = NULL;   //Contains all the words found sorted with no duplicates. (Lexicographically)

static uInt64 FC_Count            = 1U;   //File character count. (Starts and one for EOF).
static uInt64 SStringOccurCount   = 0U;   //Substring occurrence count.
static uInt64 WordCount           = 0U;   //Number of total words found.
static uInt64 WordsSortedCount    = 0U;

//Functions

//Circular dependency prevention.
bool DebugGFAV();

void ClearFlagsAndValues                                                                                                                                  ();
void GetFlagsAndValues       (const int     *_argCount      ,       char   **_arguments                                                                    );
void FileToString            (      FILE    *_ptrToFile     ,       uInt64   _characterCount, string  *_stringToStore                                      );
void PrintArgumentsAndResults(const int     *_argCount      , const char   **_arguments                                                                    );
void SortWords               (      string **_ptrToWordArray,       uInt64   _wordCount     , string **_ptrToStoreWordSorted, uInt64 *_ptrToWordSortedCount);

string GetBoolStr(bool  _boolValue);

uInt64 GetFileCharacterCount(FILE   *_ptrToFile                                              );
uInt64 WordOccurance        (string  _wordToFind, string **_ptrToWordArray, uInt64 _wordCount);


//Used for debugging the runtime of the entire program.
bool Debugger()
{
    bool passed = true;

    if (!DebugGFAV())
    {
        passed = false;
    }

    return passed;
}

//Debugs GetFlagsAndValues function. (Every possible case). Was to prevent what occurred with lab 2...
bool DebugGFAV()
{
    printf("Debugging GetFlagsAndValues...");

#define CT (C_Flag == true)
#define ST (S_Flag == true)
#define FT (F_Flag == true)

    bool passed = true;

    //If nothing passed.
    int argCount = 1;

    char **arguments;

    arguments = malloc(sizeof(char*)*2);

    arguments[0] = "Test Path";
    arguments[1] = NULL       ;

    GetFlagsAndValues( (const int *)&argCount, arguments );

    PrintArgumentsAndResults( (const int *)&argCount, (const char**)arguments );

    if (!(!CT && !ST && !FT)) { passed = false; }

    ClearFlagsAndValues();

    free(arguments);

    //-c  Wont work on gcc compiler for some reason, but it will work if a real cmd arg on msys.
    argCount = 2;

    arguments = malloc(sizeof(char*) * 3);

    arguments[0] = "Test Path";
    arguments[1] = "-c"       ;
    arguments[2] = NULL       ;

    GetFlagsAndValues((const int *)&argCount, arguments);

    PrintArgumentsAndResults((const int *)&argCount, (const char**)arguments);

    if (C_Flag == false) { passed = false; }

    ClearFlagsAndValues();

    free(arguments);

    //-s 

    argCount = 2;

    arguments = malloc(sizeof(char*) * 3);

    arguments[0] = "Test Path";
    arguments[1] = "-s"       ;
    arguments[2] = NULL       ;

    GetFlagsAndValues((const int *)&argCount, arguments);

    PrintArgumentsAndResults((const int *)&argCount, (const char**)arguments);

    if (!(!CT && ST && !FT)) { passed = false; }

    ClearFlagsAndValues();

    free(arguments);

    //-f

    argCount = 3;

    arguments = malloc(sizeof(char*) * 4);

    arguments[0] = "Test Path";
    arguments[1] = "-f"       ;
    arguments[2] = "Test"     ;
    arguments[3] = NULL       ;

    GetFlagsAndValues((const int *)&argCount, arguments);

    PrintArgumentsAndResults((const int *)&argCount, (const char**)arguments);

    if (!(!CT && !ST && FT)) { passed = false; }

    ClearFlagsAndValues();

    free(arguments);

    //-c -s 

    argCount = 3;

    arguments = malloc(sizeof(char*) * 4);

    arguments[0] = "Test Path";
    arguments[1] = "-c"       ;
    arguments[2] = "-s"       ;
    arguments[3] = NULL       ;

    GetFlagsAndValues((const int *)&argCount, arguments);

    PrintArgumentsAndResults((const int *)&argCount, (const char**)arguments);

    if (!(CT && ST && !FT)) { passed = false; }

    ClearFlagsAndValues();

    free(arguments);

    //-s -c

    argCount = 3;

    arguments = malloc(sizeof(char*) * 4);

    arguments[0] = "Test Path";
    arguments[1] = "-s"       ;
    arguments[2] = "-c"       ;
    arguments[3] = NULL       ;

    GetFlagsAndValues((const int *)&argCount, arguments);

    PrintArgumentsAndResults((const int *)&argCount, (const char**)arguments);

    if (!(CT && ST && !FT)) { passed = false; }

    ClearFlagsAndValues();

    free(arguments);

    //-c -f
    argCount = 4;

    arguments = malloc(sizeof(char*) * 5);

    arguments[0] = "Test Path";
    arguments[1] = "-c"       ;
    arguments[2] = "-f"       ;
    arguments[3] = "test"     ;
    arguments[4] = NULL       ;

    GetFlagsAndValues((const int *)&argCount, arguments);

    PrintArgumentsAndResults((const int *)&argCount, (const char**)arguments);

    if (!(CT && !ST && FT)) { passed = false; }

    ClearFlagsAndValues();

    free(arguments);

    //-f -c

    argCount = 4;

    arguments = malloc(sizeof(char*) * 5);

    arguments[0] = "Test Path";
    arguments[1] = "-f"       ;
    arguments[2] = "test"     ;
    arguments[3] = "-c"       ;
    arguments[4] = NULL       ;

    GetFlagsAndValues((const int *)&argCount, arguments);

    PrintArgumentsAndResults((const int *)&argCount, (const char**)arguments);

    if (!(CT && !ST && FT)) { passed = false; }

    ClearFlagsAndValues();

    free(arguments);

    //-s -f

    argCount = 4;

    arguments = malloc(sizeof(char*) * 5);

    arguments[0] = "Test Path";
    arguments[1] = "-s"       ;
    arguments[2] = "-f"       ;
    arguments[3] = "test"     ;
    arguments[4] = NULL       ;

    GetFlagsAndValues((const int *)&argCount, arguments);

    PrintArgumentsAndResults((const int *)&argCount, (const char**)arguments);

    if (!(!CT && ST && FT)) { passed = false; }

    ClearFlagsAndValues();

    free(arguments);

    //-f -s

    argCount = 4;

    arguments = malloc(sizeof(char*) * 5);

    arguments[0] = "Test Path";
    arguments[1] = "-f"       ;
    arguments[2] = "test"     ;
    arguments[3] = "-s"       ;
    arguments[4] = NULL       ;

    GetFlagsAndValues((const int *)&argCount, arguments);

    PrintArgumentsAndResults((const int *)&argCount, (const char**)arguments);

    if (!(!CT && ST && FT)) { passed = false; }

    ClearFlagsAndValues();

    free(arguments);

    //-c -s -f

    argCount = 5;

    arguments = malloc(sizeof(char*) * 6);

    arguments[0] = "Test Path";
    arguments[1] = "-c"       ;
    arguments[2] = "-s"       ;
    arguments[3] = "-f"       ;
    arguments[4] = "test"     ;
    arguments[5] = NULL       ;

    GetFlagsAndValues((const int *)&argCount, arguments);

    PrintArgumentsAndResults((const int *)&argCount, (const char**)arguments);

    if (!(CT && ST && FT)) { passed = false; }

    ClearFlagsAndValues();

    free(arguments);

    //-c -f -s

    argCount = 5;

    arguments = malloc(sizeof(char*) * 6);

    arguments[0] = "Test Path";
    arguments[1] = "-c"       ;
    arguments[2] = "-f"       ;
    arguments[3] = "test"     ;
    arguments[4] = "-s"       ;
    arguments[5] = NULL       ;

    GetFlagsAndValues((const int *)&argCount, arguments);

    PrintArgumentsAndResults((const int *)&argCount, (const char**)arguments);

    if (!(CT && ST && FT)) { passed = false; }

    ClearFlagsAndValues();

    free(arguments);

    //-s -c -f

    argCount = 5;

    arguments = malloc(sizeof(char*) * 6);

    arguments[0] = "Test Path";
    arguments[1] = "-s"       ;
    arguments[2] = "-c"       ;
    arguments[3] = "-f"       ;
    arguments[4] = "test"     ;
    arguments[5] = NULL       ;

    GetFlagsAndValues((const int *)&argCount, arguments);

    PrintArgumentsAndResults((const int *)&argCount, (const char**)arguments);

    if (!(CT && ST && FT)) { passed = false; }

    ClearFlagsAndValues();

    free(arguments);

    //-s -f -c

    argCount = 5;

    arguments = malloc(sizeof(char*) * 6);

    arguments[0] = "Test Path";
    arguments[1] = "-s"       ;
    arguments[2] = "-f"       ;
    arguments[3] = "test"     ;
    arguments[4] = "-c"       ;
    arguments[5] = NULL       ;

    GetFlagsAndValues((const int *)&argCount, arguments);

    PrintArgumentsAndResults((const int *)&argCount, (const char**)arguments);

    if (!(CT && ST && FT)) { passed = false; }

    ClearFlagsAndValues();

    free(arguments);

    //-f -c -s

    argCount = 5;

    arguments = malloc(sizeof(char*) * 6);

    arguments[0] = "Test Path";
    arguments[1] = "-f"       ;
    arguments[2] = "test"     ;
    arguments[3] = "-c"       ;
    arguments[4] = "-s"       ;
    arguments[5] = NULL       ;

    GetFlagsAndValues((const int *)&argCount, arguments);

    PrintArgumentsAndResults((const int *)&argCount, (const char**)arguments);

    if (!(CT && ST && FT)) { passed = false; }

    ClearFlagsAndValues();

    free(arguments);

    //-f -s -c

    argCount = 5;

    arguments = malloc(sizeof(char*) * 6);

    arguments[0] = "Test Path";
    arguments[1] = "-f"       ;
    arguments[2] = "test"     ;
    arguments[3] = "-s"       ;
    arguments[4] = "-c"       ;
    arguments[5] = NULL       ;

    GetFlagsAndValues((const int *)&argCount, arguments);

    PrintArgumentsAndResults((const int *)&argCount, (const char**)arguments);

    if (!(CT && ST && FT)) { passed = false; }

    ClearFlagsAndValues();

    free(arguments);

#undef CT
#undef ST
#undef FT

    return passed;
}

//Scans a given word array for any words that match the given word to find. Returns a boolean indicating if found.
bool FindWord(string _wordToFind, string **_ptrToWordArray, uInt64 _wordCount)
{
    for (int index = 0; index < (int)_wordCount; index++)
    {
        char *currentWord = strdup((const char *)_ptrToWordArray[0][index]);

        if ((strcmp((const char*) currentWord, _wordToFind)) == 0)
        {
            return true;
        }
    }

    return false;
}

//Resets all flags and values to false or not set.
void ClearFlagsAndValues()
{
    C_Flag = false;
    S_Flag = false;
    F_Flag = false;

    if (FileName != NULL)
    {
        free(FileName);

        FileName = NULL;
    }
    if (FileString != NULL)
    {
        free(FileString);

        FileString = NULL;
    }
    if (SubString != NULL)
    {
        free(SubString);

        SubString = NULL;
    }
    if (WordData != NULL)
    {
        free(WordData);

        WordData = NULL;
    }
    if (WordsSorted != NULL)
    {
        free(WordsSorted);

        WordsSorted = NULL;
    }
    
    FC_Count          = 0U;
    SStringOccurCount = 0U;
    WordCount         = 0U;
}

//Populates a given string with the text file's contents. Must be given the character count and assumes it includes EOF.
void FileToString(FILE *_ptrToFile, uInt64 _characterCount, string *_stringToStore)
{
    *_stringToStore = malloc(sizeof(char)*(_characterCount));

    for (uInt64 index = 0U; index < (_characterCount - 1); _stringToStore[0][index++] = fgetc(_ptrToFile));

    _stringToStore[0][_characterCount - 1] = '\0';

    rewind(_ptrToFile);
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

    optind = 1; free(optarg);   //Reset the getopt arguments to their original values in case this function was run before.

    bool error = false;

    int options = -1;

    string flags = "csf:";

    static char usage[] = "usage: %s [-cs] [-f substring] filename\n";

#define SetOptions      ( options = getopt( (int)*_argCount, _arguments, flags ) )   //Updates options every time its called.
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
        case 's':
        {
            S_Flag = true;

            break;
        }
        case 'f':
        {
            F_Flag = true;

            SubString = optarg; 

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

    if (error == true)
    {
        fprintf(stderr, usage, _arguments[0]);

        exit(1);
    }

#define MissingFilename "Filename missing in command line arguments, please provide one ex: mywords -f filename\n"

    if (optind < *_argCount)
    {
        FileName = _arguments[optind];
    }
    else
    {
        printf(MissingFilename);
    }

#undef MissingFilename
#undef NoMoreArguments
#undef SetOptions
}

//Gets all the words in a given string. Sets them to a specified pointer to a word array, and gives the count to a give pointer to a word counter.
void GetWords(string _fileString, string **_ptrToWordArray, uInt64 *_ptrToWordCount)
{
    string fileStringRef = NULL;

    fileStringRef = (string)strdup(_fileString);

    string delimiter = " ,.?:;\"!@#$%%^&*<>/()[]{}=+_`~\\~\n";

    string word = NULL;

    word = strtok(fileStringRef, delimiter);

    uInt64 index = 0;

    while (word != NULL)
    {
        if (strcmp(word, "-") != 0)
        {
            _ptrToWordCount[0]++;

            _ptrToWordArray[0] = realloc(_ptrToWordArray[0], sizeof(string) * _ptrToWordCount[0]);

            _ptrToWordArray[0][index++] = word;
        }

        word = strtok(NULL, delimiter);
    }
}

//Manages the file to open before printing and analyzing. (Closes the file after).
void HandleFile()
{
    FilePtr = fopen(FileName, "r");

    FC_Count = GetFileCharacterCount(FilePtr);

    FileToString(FilePtr, FC_Count, &FileString);

    fclose(FilePtr);
}

//Secondary MergSort function to be used with MergeSortStrArray().
void MergeStrArray(string **_ptrToStrArray, int left, int middle, int right)
{
    int indexLeft  = 0, 
        indexRight = 0, 
        indexArray = 0;

    int lenLeft  = middle - left   + 1;
    int lenRight = right  - middle    ;

    string *StrsLeft  = malloc(sizeof(string) * lenLeft );
    string *StrsRight = malloc(sizeof(string) * lenRight);

    for (indexLeft = 0; indexLeft < lenLeft; indexLeft++)
    {
        StrsLeft[indexLeft] = (string)strdup(_ptrToStrArray[0][left + indexLeft]);
    }

    for (indexRight = 0; indexRight < lenRight; indexRight++)
    {
        StrsRight[indexRight] = (string)strdup(_ptrToStrArray[0][middle + 1 + indexRight]);
    }

    indexLeft  = 0   ;
    indexRight = 0   ;
    indexArray = left;

#define StrLeftRightCmp strcmp(StrsLeft[indexLeft], StrsRight[indexRight])

    while (indexLeft < lenLeft && indexRight < lenRight)
    {
        //StrsLeft[indexLeft] <= StrsRight[indexRight] 
        if (  (StrLeftRightCmp < 0) || (StrLeftRightCmp == 0) )
        {
            _ptrToStrArray[0][indexArray] = (string)strdup(StrsLeft[indexLeft]);

            indexLeft++;
        }
        else
        {
            _ptrToStrArray[0][indexArray] = (string)strdup(StrsRight[indexRight]);

            indexRight++;
        }

        indexArray++;
    }

    while (indexLeft < lenLeft)
    {
        _ptrToStrArray[0][indexArray] = (string)strdup(StrsLeft[indexLeft]);

        indexLeft ++;
        indexArray++;
    }

    while (indexRight < lenRight)
    {
        _ptrToStrArray[0][indexArray] = (string)strdup(StrsRight[indexRight]);

        indexRight++;
        indexArray++;
    }
}

//Performs a merge sort on a given string array.
void MergSortStrArray(string **_ptrToStrArray, int left, int right)
{
    if (left < right)
    {
        int middle = left + (right - left) / 2;

        MergSortStrArray(_ptrToStrArray, left      , middle);
        MergSortStrArray(_ptrToStrArray, middle + 1, right );

        MergeStrArray(_ptrToStrArray, left, middle, right);
    }
}

void ProcessFlags()
{
    GetWords(FileString, &WordData, &WordCount);

    if (S_Flag)
    {
        SortWords(&WordData, WordCount, &WordsSorted, &WordsSortedCount);

        printf("Words Sorted Lexicographically: \n\n");

        for (uInt64 index = 0U; index < WordsSortedCount; index++)
        {
            printf("%s\n", WordsSorted[index]);
        }
    }
	if (C_Flag)
	{
		printf("\n\nWord Count: %d", (int)WordCount);
	}
    if (F_Flag)
    {
        SStringOccurCount = WordOccurance(SubString, &WordData, WordCount);

        printf("\n\nOccurrence of %s: %d", SubString, (int)SStringOccurCount);
    }
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

    printf("%d%d%d\n", C_Flag, S_Flag, F_Flag);

    printf("C         : %s\n", GetBoolStr(C_Flag));
    printf("S         : %s\n", GetBoolStr(S_Flag));
    printf("F         : %s\n", GetBoolStr(F_Flag));
    printf("Substring : %s\n", SubString         );

    printf("\n");
}

//Prints the file contents that was stored in FileString.
void PrintFile()
{
    printf("File Contents: \n\n");

    printf("%s\n\n\n", FileString);
}

//Sorts given words in lexicographic order.
void SortWords(string **_ptrToWordArray, uInt64 _wordCount, string **_ptrToStoreWordSorted, uInt64 *_ptrToWordSortedCount)
{
    *_ptrToWordSortedCount = 1;

    string *wordsNoDupes = malloc(sizeof(string));

    wordsNoDupes[0] = _ptrToWordArray[0][0];

    for (uInt64 index = 1; index < _wordCount; index++)
    {
        if (!FindWord(_ptrToWordArray[0][index], &wordsNoDupes, *_ptrToWordSortedCount))
        {
            _ptrToWordSortedCount[0]++;

            wordsNoDupes = realloc(wordsNoDupes, sizeof(string) * _ptrToWordSortedCount[0]);

            wordsNoDupes[*_ptrToWordSortedCount -1] = _ptrToWordArray[0][index];
        }
    }

    _ptrToStoreWordSorted[0] = malloc(sizeof(string) * *_ptrToWordSortedCount);

    for (uInt64 index = 0; index < *_ptrToWordSortedCount; index++)
    {
        _ptrToStoreWordSorted[0][index] = (string)strdup(wordsNoDupes[index]);
    }

    MergSortStrArray(_ptrToStoreWordSorted, 0, (int)(*_ptrToWordSortedCount - 1));
}

//Prints value of bool.
string GetBoolStr(bool _boolValue)
{
    if (_boolValue == true)
    {
        return "true";
    }
    else
    {
        return "false";
    }
}

//Gets the file character count from the given file and returns it as a uint64.
uInt64 GetFileCharacterCount(FILE *_ptrToFile)
{
    uInt64 CharCount = 1U;   //Starts at 1 to include EOF.

    if (_ptrToFile == NULL)
    {
        printf("Cannot get characters. No file open. Returning -1"); 
        
        return -1;
    }

    char character;

#define CurrentCharacter (character = fgetc(_ptrToFile))

    while (CurrentCharacter != EOF)
    {
        CharCount++;
    }

#undef CurrentCharacter

    rewind(_ptrToFile);

    return CharCount;
}

//Gets the occurrence of a given word within a specified word array.
uInt64 WordOccurance(string _wordToFind, string **_ptrToWordArray, uInt64 _wordCount)
{
    uInt64 occurenceCount = 0U;

    for (uInt64 index = 0; index < _wordCount; index++)
    {
        if ((strcmp(_ptrToWordArray[0][index], _wordToFind)) == 0)
        {
            occurenceCount++;
        }
    }

    return occurenceCount;
}

//Runtime Execution
int main(int argc, char **argv)
{
    GetFlagsAndValues(&argc, argv);   //GetOpt happens here.

    if (FileName != NULL)
    {
        HandleFile();   //Open the file, get information, and close it.

        PrintFile();   //Print the text of the file.

        ProcessFlags();   //Do any analysis of the file specified by user flags.
    }
    else   //Handle a missing file name.
    {
        printf("File reference is not set, cannot continue. exiting with code 1.\n");

        exit(1);
    }

    exit(0);
}