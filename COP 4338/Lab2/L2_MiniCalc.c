/*
Edward R. Gonzalez 499406

The following has functionality to behave as a "Mini Calculator".
It uses command line arguments to take in numbers and operations.

Supported Operations: Add, Multiply, Square Power.

Numerical Ranges: Add: 1-500, Multiply: 1-10, Value: 1-50.

Affirmation of originality:
I affirm that I wrote this
program myself without any help form any
other people or sources from the Internet.
*/

#pragma once

//Includes

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <getopt.h>

//Declares

bool a_flag = false,   //Addition       Flag
     m_flag = false,   //Multiplication Flag
     x_flag = false,   //Square Power   Flag
     v_flag = false;   //Value          Flag

int a_number = 0,   //Holds the passed value for Addition.
    m_number = 0,   //Holds the passed value for Multiplication.
    value    = 0;   //Holds the passed value to be operated on.


//Functions

//Checks to make sure the Add value given via command line args is within supported range.
bool IsAddLegal(int NumberToCheck)
{
	if (NumberToCheck >= 1 && NumberToCheck <= 500)
	{
		return true;
	}

	return false;
}

//Checks to make sure the Multiply value given via command line args is within supported range.
bool IsMultiplyLegal(int NumberToCheck)
{
	if (NumberToCheck >= 1 && NumberToCheck <= 10)
	{
		return true;
	}

	return false;
}

//Checks to make sure the Value given (to be operated on) via command line args is within support range.
bool IsValueLegal(int NumberToCheck)
{
	if (NumberToCheck >= 1 && NumberToCheck <= 50)
	{
		return true;
	}

	return false;
}

//Sets all flags to false.
void InvalidateFlags()
{
	v_flag = false;
	a_flag = false;
	m_flag = false;
	x_flag = false;
}

//Sets the flags and their values.
void PopulateFlagsAndValues(int cmdLineArgCount, char **cmdLineArguments)
{
	//https://www.gnu.org/software/libc/manual/html_node/Using-Getopt.html#Using-Getopt See for docs on getopt use. (Its the GNU docs)
	extern int   optind;
	extern char *optarg;

	int option;

#define OperativeOptions (option = getopt(cmdLineArgCount, cmdLineArguments, "a:m:x"))

#define AddError      "Add number desired is not in supported range. Please use a number between 1-500 (Inclusive)."
#define MultiplyError "Multipicative factor desired is not in supported range. Please choose a number between 1-10 (Inclusive)."

	while (OperativeOptions != -1)
	{
		switch (option)
		{
		case 'a':
		{
			a_flag   = true        ;
			a_number = atoi(optarg);

			break;
		}
		case 'm':
		{
			m_flag   = true        ;
			m_number = atoi(optarg);

			break;
		}
		case 'x':
		{
			x_flag = true;

			break;
		}
		default:
			printf("\n%s\n", "Options switch did not find a defined case.");
		}
	}

#define ValueMissingError "Could not find value in arguments to use operation on. Please enter a value."
#define ValueError        "Value number desired is not in supported range. Please use a number between 1-50 (Inclusive)."

	if (optind < cmdLineArgCount)
	{
		v_flag = true;

		value = atoi(cmdLineArguments[optind]);
	}
	else
	{
		printf("\n%s\n", ValueMissingError);
	}

	if (!IsValueLegal(value))
	{
		printf("\n%s\n", ValueError);

		InvalidateFlags();
	}
	else if (!IsAddLegal(a_number))
	{
		printf("\n%s\n", AddError);

		InvalidateFlags();
	}
	else if (!IsMultiplyLegal(m_number))
	{
		printf("\n%s\n", MultiplyError);

		InvalidateFlags();
	}
}

//Completes the supported mathematical operations.
double PerformOperations()
{
	double result = -1;

	if (v_flag == true)
	{
		result = value;

		//Order: Power, Multiplication, Addition

		if (x_flag == true)
		{
			result *= value;
		}
		if (m_flag == true)
		{
			result *= m_number;
		}
		if (a_flag == true)
		{
			result += a_number;
		}
	}

	return result;
}


//Runtime execution.
int main(int argc, char **argv)
{
	PopulateFlagsAndValues(argc, argv);   //Setup parameters and see if they are supported.

	printf("Resulting value: %.2f\n", PerformOperations());   //Indicate the result of the operation as a string in Console.

	return 0;
}