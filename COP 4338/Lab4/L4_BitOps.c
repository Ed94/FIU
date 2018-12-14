/*
Edward R. Gonzalez 499406

Program name: bitops

Description:
Will perform bitwise operations: set and clear, on a specified integer and bit position of stated integer.
User can specify to complete another operation on a different integer afterward.

Index:

Typedefs: 41
Structs : 53
Enums   : 68
Declares: 75

String Functions: 92

Debug: 144

Lab Related Functions: 173

Main: 386

Affirmation of originality:
I affirm that I wrote this
program myself without any help form any
other people or sources from the internet.
*/

//Includes
#include <inttypes.h>
#include <getopt.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <string.h>


//----------------------------------------------------------Typedefs:

typedef          bool  bit    ;   //A value of a single binary bit. (0 or 1)
typedef unsigned int   uInt   ;   //Alias for unsigned integers.
typedef          char* CString;   //Regular c strings (character array's).

typedef enum bitActEnum BitAction;   //Stores a bit action supported by this program.

typedef struct strStruct String;   //String implementation for better handling.

//-----------------------------------------------------------------

//-----------------------------------------------------------Structs:

//Used to define a String.
struct strStruct
{
	bool isDynamic;   //Stores whether or not this string's character array was dynamically allocated or not.

	CString cString;   //Character Array pointer for the string.

	size_t length;   //Size of the string
};

//-------------------------------------------------------------------


//--------------------------------------------------------------Enums:

enum bitActEnum { BitSet, BitClear };   //Used to store a bit action.

//--------------------------------------------------------------------


//----------------------------------------------------------Declares:

static BitAction SelectedAction;   //Bit action to complete on the integer given.

static uint8_t *BitPosition = NULL;   //Position of the integer to do bitwise operation.

static uint32_t *OriginalInteger = NULL;   //Original integer given by the user.
static uint32_t *ModifiedInteger = NULL;   //Modified integer from bit actions.

//-------------------------------------------------------------------

//--------------------------------------------------Forward Declares:

CString GetBitActionStr(BitAction _bitAction);

//-------------------------------------------------------------------

//--------------------------------------------------String Functions:

//Will dynamically allocate a string with a set amount of characters based on given
void BufferString(String *_stringToBuffer, uInt _bufferAmount, bool _shouldZero)
{
	if (!_shouldZero)
	{
		_stringToBuffer->cString = (CString)malloc(sizeof(char) * _bufferAmount);
	}
	else
	{
		_stringToBuffer->cString = (CString)calloc(_bufferAmount, sizeof(char));
	}

	_stringToBuffer->isDynamic = true;

	_stringToBuffer->length = _bufferAmount;
}

//Clears a string's components, and frees dynamically allocated memory of the CString.
void ClearString(String *_stringToClear)
{
	if (_stringToClear->isDynamic)
	{
		free((CString)_stringToClear->cString);
	}
	else
	{
		if (strcmp(_stringToClear->cString, "") != 0)
		{
			_stringToClear->cString = NULL;
		}
	}

	_stringToClear->length = 0;
}

//Sets a String to a given CString.
//_string: The string to set.
//_cString: The character array that the value of _string's cString will be set to.
// Will be dynamically allocated.
void SetString(String *_string, CString _cString)
{
	_string->cString = strdup(_cString);

	_string->isDynamic = true;

	_string->length = strlen(_cString);
}

//-------------------------------------------------------------------

//-------------------------------------------------------------Debug:

//Prints binary representation of a given integer.
void DebugPrintBinary(uint32_t *_integerToPrint)
{
	printf("Binary%-9s:", " ");

	for (uint32_t ref = 1U << 31U; ref > 0U; ref /= 2U)
	{
		(*_integerToPrint & ref) ? printf("1") : printf("0");
	}

	printf("\n");
}

//Debug variant PrintResults(). Gives more information beyond just the original and modified.
void DebugPrintResults()
{
	printf("Selected Action: %s\n", GetBitActionStr(SelectedAction));
	printf("Position       : %u\n", (int)*BitPosition              );
	printf("Original       : %u\n", (int)*OriginalInteger          ); DebugPrintBinary(OriginalInteger);
	printf("Modified       : %u\n", (int)*ModifiedInteger          ); DebugPrintBinary(ModifiedInteger);

	printf("\n\n");
}

//-------------------------------------------------------------------


//---------------------------------------------Lab Related Functions:

//Returns a boolean value indicating whether o not the user wishes to complete another operation on a
//different integer.
bool CheckIfDoingAgain()
{
	printf("\nWould you like to complete another operation on a different integer? (Enter Y or y if yes)\n");

	char response;

	getchar();

	scanf("%c", &response);

	if ((response == 'y') || (response == 'Y'))
	{
		return true;
	}

	return false;
}

//Provides a string of a bit action value (Does not need to be freed, used literal).
CString GetBitActionStr(BitAction _bitAction)
{
	if (_bitAction == BitSet)
	{
		return "Set";
	}
	else
	{
		return "Clear";
	}
}

//Duplicates a 32-bit integer that was dynamically allocated.
//_integerToDuplicate: Source of value to duplicate.
//Warning: Returns a pointer dynamically allocated value.
uint32_t *DupInt32(uint32_t **_integerToDuplicate)
{
	uint32_t *duplicate = (uint32_t *)malloc(sizeof(uint32_t));

	*duplicate = **_integerToDuplicate;

	return duplicate;
}

//Clears and deallocates all the declaratives.
void ClearDeclares()
{
	if (BitPosition != NULL)
	{
		free(BitPosition);
	}
	if (OriginalInteger != NULL)
	{
		free(OriginalInteger);
	}
	if (ModifiedInteger != NULL)
	{
		free(ModifiedInteger);
	}
}

//Gets a bit action from the user.
//_bitActionHolder: What will retain the given SelectedAction. (Will pass a literal)
//Will loop until user response is a valid string. 
void GetBitActionViaCMD(BitAction *_bitActionHolder)
{
	printf("\nPlease provide a bit action: (Type 'set' for set, and 'clear' for clear, case matters, don't include quotes)\n");

	bool isValidAction = false;

	String response;

	BufferString(&response, 6, false);

	while (!isValidAction)
	{
		scanf("%5s", response.cString);

		if (strcmp(response.cString, "set") == 0)
		{
			*_bitActionHolder = BitSet;

			isValidAction = true;
		}
		else if (strcmp(response.cString, "clear") == 0)
		{
			*_bitActionHolder = BitClear;

			isValidAction = true;
		}
		else
		{
			printf("\nResponse was not correct. Please enter a proper value. (Type set for set, and clear for clear, case matters)\n");

			getchar();
		}
	}

	ClearString(&response);

	return;
}

//Gets a valid integer to process in this program from the user.
//_integerHolder: Pointer to declarative to retain value received.
//Will loop until user provides valid integer.
//Warning: Dynamically allocated! (And not deallocated)
void GetIntegerToBitOperate(uint32_t **_integerHolder)
{
	printf("\nPlease enter an integer: (Must be a number between 1 and 1000 inclusive).\n");

	bool isValidInteger = false;

	*_integerHolder = (uint32_t *)malloc(sizeof(uint32_t));

	while (!isValidInteger)
	{
		scanf("%u", *_integerHolder);

		if ((**_integerHolder >= 1U) && (**_integerHolder <= 1000U))
		{
			isValidInteger = true;
		}
		else
		{
			printf("\nGiven integer not in valid range. Please provide an integer between 1 and 1000 inclusive.\n");

			getchar();
		}
	}

	return;
}

//Gets a position from 0 to 31 from the user.
//_positionHolder: Pointer to the uint8_t declarative to store the position received.
//Will loop until user gives a valid position.
//Warning: Dynamically allocated! (And not deallocated)
void GetIntegerPosition(uint8_t **_positionHolder)
{
	printf("\nPlease enter the bit position of the integer to complete the bit action on: (Must be within the range of 0 and 31 inclusive)\n");

	bool isValidPosition = false;

	*_positionHolder = (uint8_t *)malloc(sizeof(uint8_t) * 1);

	while (!isValidPosition)
	{
		scanf("%hhu", *_positionHolder);

		if ((**_positionHolder >= (uint8_t)0U) && (**_positionHolder <= (uint8_t)31U))
		{
			isValidPosition = true;
		}
		else
		{
			printf("\nNot a valid position. Please enter a value between 0 and 31 inclusive.\n");

			getchar();
		}
	}

	return;
}

//Performs a bit operation specified by the user using a mask on the value held by ModifiedInteger.
//Uses the global values directly. (Not generic to any program)
void PerformBitOperation()
{
	uint32_t mask = 0x000000;

	switch (SelectedAction)
	{
	case BitClear:
	{
		mask = 1 << *BitPosition;

		mask = ~mask;

		*ModifiedInteger &= mask;

		break;
	}
	case BitSet:
	{
		mask = 0x00000000;

		mask = 1 << *BitPosition;

		*ModifiedInteger |= mask;

		break;
	}
	}

	return;
}

//Prints the original integer and the resulting integer from the operation.
void PrintResults()
{
	printf("\n");

	printf("Original Integer : %u\n", (int)*OriginalInteger);
	printf("Resulting Integer: %u\n", (int)*ModifiedInteger);
}

//-------------------------------------------------------------------


//-------------------------------------------------Runtime Execution:

int main()
{
	bool exist = true;

	while (exist)
	{
		GetIntegerToBitOperate(&OriginalInteger);

		ModifiedInteger = DupInt32(&OriginalInteger);

		GetBitActionViaCMD(&SelectedAction);

		GetIntegerPosition(&BitPosition);

		PerformBitOperation();

		PrintResults();

		exist = CheckIfDoingAgain();

		ClearDeclares();
	}

	exit(0);
}

//-------------------------------------------------------------------