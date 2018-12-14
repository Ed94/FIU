/*
Edward R. Gonzalez 499406

Program name: bmptool

Description:
Using command-line. Allow the user to either, scale, rotate, or flip an BMP image file provided and return the bitmapResult as
a separate bmp file.

Index:
Includes             : 19
Declares             : 38
Lab Related Functions: 61
Runtime Execution    : 617

Affirmation of originality:
I affirm that I wrote this
program myself without any help form any
other people or sources from the Internet.
*/

//C-Standard Library
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

//Bitmap_Def Related
#include "bmplib.h"

//GetOpt
#if defined(__GNUC__)  //Using built in standard for getopt on ocelot's red hat Linux GCC distribution.
#include <getopt.h>
#endif


//----------	------------------------------------------------------------------------------Declares:

//GetOpt Flags
static bool S_Flag  = false;
static bool R_Flag  = false;
static bool F_Flag  = false;
static bool V_Flag  = false;
static bool SI_Flag = false;
static bool SO_Flag = false;

static int ScaleToEnlarge = 0;
static int RotationAmount = 0;

static CString PathToSpecified;
static CString PathToResulting;

//BMP File Instances
static BMP_File SpecifiedBMP;
static BMP_File ResultingBMP;

///////////////////////////////////////////////////////////////////////////////////////////////////

//---------------------------------------------------------------------------Lab Related Functions:

/*
 * This method enlarges a 24-bit, uncompressed .bmp file
 * that has been read in using OpenBMP() or STDIN_BMP().
 *
 * _BMPToEnlarge - a bmp file struct containing the file 
 * information that will be destructively transformed.
 *
 * _scale    - the multiplier applied to EACH OF the rows and columns, e.g.
 *           if scale=2, then 2* rows and 2*cols
 */
int Enlarge(BMP_File *_BMPToEnlarge, int _scale)
{
	bool heightCheck = _BMPToEnlarge->InfoHeader.Height > 0;
	bool widthCheck  = _BMPToEnlarge->InfoHeader.Width  > 0;

	if (!widthCheck && !heightCheck)
	{
		return -1;
	}

	if (_scale == 1)
	{
		return 0;
	}

	BMP_File useless;

	CopyInfo_BMP(_BMPToEnlarge, &useless);

	int heightScaled = _BMPToEnlarge->InfoHeader.Height * _scale;
	int widthScaled  = _BMPToEnlarge->InfoHeader.Width  * _scale;

	int sizeScaled = heightScaled * widthScaled * Size_Pixel;

	int fileSizeScaled = sizeScaled + Size_BitmapFileHeader + Size_BitmapInfoHeader;

	Bitmap scaledBitmap;

	scaledBitmap.ScanLines = (Scanline *)malloc(heightScaled * Size_Scanline);

	for (int rowPosition = 0; rowPosition < heightScaled; rowPosition++)
	{
		scaledBitmap.ScanLines[rowPosition].PixelLine = (Pixel *)malloc(widthScaled * Size_Pixel);
	}

	for (int rowPosition = 0, rowPositionScaled = 0, rowDupe = 0; rowPositionScaled < heightScaled; rowDupe++)
	{
		if (rowDupe <= (_scale))
		{
			for (int pixelPos = 0, pixelPosScaled = 0, pixelDupe = 0; pixelPosScaled < widthScaled; pixelDupe++)
			{
				if (pixelDupe <= (_scale))
				{
					scaledBitmap.ScanLines[rowPositionScaled].PixelLine[pixelPosScaled] = useless.PixelData.ScanLines[rowPosition].PixelLine[pixelPos];

					pixelPosScaled++;
				}
				else
				{
					pixelDupe = 0;

					pixelPos++;
				}
			}

			rowPositionScaled++;
		}
		else
		{
			rowDupe = 0;

			rowPosition++;
		}
	}

	for (int row = 0; row < _BMPToEnlarge->InfoHeader.Height; row++)
	{
		if (_BMPToEnlarge->PixelData.ScanLines[row].PixelLine != NULL)
		{
			free(_BMPToEnlarge->PixelData.ScanLines[row].PixelLine);
		}
	}

	if (_BMPToEnlarge->PixelData.ScanLines != NULL)
	{
		free(_BMPToEnlarge->PixelData.ScanLines);
	}

	_BMPToEnlarge->PixelData = scaledBitmap;

	_BMPToEnlarge->InfoHeader.Height = heightScaled;
	_BMPToEnlarge->InfoHeader.Width  = widthScaled ;

	_BMPToEnlarge->FileHeader.FileSize = fileSizeScaled;

	_BMPToEnlarge->InfoHeader.SizeImage = sizeScaled;

	CloseBMP(&useless);

	return 0;
}

/*
 * This method horizontally flips a 24-bit, uncompressed bmp file
 * that has been read in using readFile().
 *
 * _BMPToFlip - a bmp file struct containing the file
 * information that will be destructively transformed.
 */
int Flip(BMP_File *_BMPToFlip)
{
	if (!SI_Flag)
	{
		bool heightCheck = _BMPToFlip->InfoHeader.Height > 0;
		bool widthCheck  = _BMPToFlip->InfoHeader.Width  > 0;

		if (!heightCheck && !widthCheck)
		{
			return -1;
		}
	}

	BMP_File useless;

	CopyInfo_BMP(_BMPToFlip, &useless);

	for (int rowPosition = 0; rowPosition < _BMPToFlip->InfoHeader.Height; rowPosition++)
	{
		for (int pixelPos = (_BMPToFlip->InfoHeader.Width - 1), opposingPixelPos = 0; pixelPos >= 0; pixelPos--, opposingPixelPos++)
		{
			_BMPToFlip->PixelData.ScanLines[rowPosition].PixelLine[pixelPos] = useless.PixelData.ScanLines[rowPosition].PixelLine[opposingPixelPos];
		}
	}

	CloseBMP(&useless);

	return 0;
}

/*
 * This method rotates a 24-bit, uncompressed .bmp file that has been read
 * in using OpenBMP() or STDIN_BMP(). The rotation is expressed in degrees and can be
 * positive, negative, or 0 -- but it must be a multiple of 90 degrees
 *
_* BMPToFlip - a bmp file struct containing the file information that
 * will be destructively transformed.
 */
int Rotate(BMP_File *_BMPToRotate, int _rotation)
{
	bool heightCheck = _BMPToRotate->InfoHeader.Height > 0;
	bool widthCheck  = _BMPToRotate->InfoHeader.Width  > 0;

	if (!widthCheck && !heightCheck)
	{
		return -1;
	}

	if ( (abs(_rotation)/360) == (0 | 1))
	{
		return 0;
	}

	BMP_File useless;

	CopyInfo_BMP(_BMPToRotate, &useless);

	if ((abs(_rotation) % 360 == 90) && (_rotation < 0))
	{
		for (int rowPosition = 0; rowPosition < _BMPToRotate->InfoHeader.Height; rowPosition++)
		{
			for (int pixelPos = 0; pixelPos < _BMPToRotate->InfoHeader.Width; pixelPos++)
			{
				_BMPToRotate->PixelData.ScanLines[rowPosition].PixelLine[pixelPos] = useless.PixelData.ScanLines[_BMPToRotate->InfoHeader.Width - 1 - pixelPos].PixelLine[_BMPToRotate->InfoHeader.Height - 1 - rowPosition];
			}
		}
	}
	else if ((abs(_rotation) % 360 == 90) && (_rotation > 0))
	{
		for (int rowPosition = 0; rowPosition < _BMPToRotate->InfoHeader.Height; rowPosition++)
		{
			for (int pixelPos = 0; pixelPos < _BMPToRotate->InfoHeader.Width; pixelPos++)
			{
				_BMPToRotate->PixelData.ScanLines[rowPosition].PixelLine[pixelPos] = useless.PixelData.ScanLines[pixelPos].PixelLine[_BMPToRotate->InfoHeader.Height - 1 - rowPosition];
			}
		}
	}
	else if (abs(_rotation) % 360 == 180)
	{
		for (int rowPosition = 0; rowPosition < _BMPToRotate->InfoHeader.Height; rowPosition++)
		{
			for (int pixelPos = 0; pixelPos < _BMPToRotate->InfoHeader.Width; pixelPos++)
			{
				_BMPToRotate->PixelData.ScanLines[rowPosition].PixelLine[pixelPos] = useless.PixelData.ScanLines[_BMPToRotate->InfoHeader.Height - 1 - rowPosition].PixelLine[_BMPToRotate->InfoHeader.Width - 1 - pixelPos];
			}
		}
	}
	else if ((abs(_rotation) % 360 == 270) && (_rotation < 0))
	{
		for (int rowPosition = 0; rowPosition < _BMPToRotate->InfoHeader.Height; rowPosition++)
		{
			for (int pixelPos = 0; pixelPos < _BMPToRotate->InfoHeader.Width; pixelPos++)
			{
				_BMPToRotate->PixelData.ScanLines[rowPosition].PixelLine[pixelPos] = useless.PixelData.ScanLines[pixelPos].PixelLine[_BMPToRotate->InfoHeader.Height - 1 - rowPosition];
			}
		}
	}
	else if ((abs(_rotation) % 360 == 270) && (_rotation > 0))
	{
		for (int rowPosition = 0; rowPosition < _BMPToRotate->InfoHeader.Height; rowPosition++)
		{
			for (int pixelPos = 0; pixelPos < _BMPToRotate->InfoHeader.Width; pixelPos++)
			{
				_BMPToRotate->PixelData.ScanLines[rowPosition].PixelLine[pixelPos] = useless.PixelData.ScanLines[_BMPToRotate->InfoHeader.Width - 1 - pixelPos].PixelLine[_BMPToRotate->InfoHeader.Height - 1 - rowPosition];
			}
		}
	}
	else
	{
		perror("Could not determine rotation transformation to complete.");

		return -1;
	}

	CloseBMP(&useless);

	return 0;
}

/*
 * This method Vertically flips a 24-bit, uncompressed bmp file
 * that has been read in using readFile().
 *
 * _BMPToVertFlip - a bmp file struct containing the file
 * information that will be destructively transformed.
 */
int VerticalFlip(BMP_File *_BMPToVertFlip)
{
	bool heightCheck = _BMPToVertFlip->InfoHeader.Height > 0;
	bool widthCheck  = _BMPToVertFlip->InfoHeader.Width  > 0;

	BMP_File useless;

	CopyInfo_BMP(_BMPToVertFlip, &useless);

	if (heightCheck && widthCheck)
	{
		for (int rowPosition = (_BMPToVertFlip->InfoHeader.Height - 1), opposingRow = 0; rowPosition >= 0; rowPosition--, opposingRow++)
		{
			for (int pixelPos = 0; pixelPos < _BMPToVertFlip->InfoHeader.Width; pixelPos++)
			{
				_BMPToVertFlip->PixelData.ScanLines[rowPosition].PixelLine[pixelPos] = useless.PixelData.ScanLines[opposingRow].PixelLine[pixelPos];
			}
		}
	}
	else
	{
		return -1;
	}

	CloseBMP(&useless);

	return 0;
}

//Checks a transformation result.
void CheckTransformationResult(int _resultToCheck)
{
	if (_resultToCheck < 0)
	{
		perror("Transformation failed on BMP file.");

		exit(1);
	}
}

//Deallocate the BMP file instances.
void CleanDeclares()
{
	if (SpecifiedBMP.IsOpen)
	{
		CloseBMP(&SpecifiedBMP);
	}
	
	if (ResultingBMP.IsOpen)
	{
		CloseBMP(&ResultingBMP);
	}
}

//Uses GetOpt to setup flags, get the SubString if any provided, and the file to analyze.
//Parameters:
//_argCount : A pass of the amount of arguments given.
//_arguments: A pass of the arguments.                
//*/
void GetFlagsAndValues(const int *_argCount, char **_arguments)
{
    //https://www.gnu.org/software/libc/manual/html_node/Using-Getopt.html#Using-Getopt See for docs on getopt use. (Its the GNU docs)
    extern int   optind;
    extern char *optarg;

    optind = 1; free(optarg);   //Reset the getopt arguments to their original values in case this function was run before.

	bool error     = false;
	bool duplicate = false;

    int options = -1;

	CString flags = "s:r:fvo:";

    static char usage[] = "usage: ./bmptool [-s scale | -r degree | -f | -v ] [-o output_file] [input_file]\n";

#define SetOptions      ( options = getopt( (int)*_argCount, _arguments, flags ) )   //Updates options every time its called.
#define NoMoreArguments -1

    while (SetOptions != NoMoreArguments)
    {
        switch (options)
        {
        case 's':
        {
			if (S_Flag)
			{
				duplicate = true;
			}
			else
			{
				S_Flag = true;

				ScaleToEnlarge = atoi(optarg);
			}

            break;
        }
		case 'r':
		{
			if (R_Flag)
			{
				duplicate = true;
			}
			else
			{
				R_Flag = true;

				RotationAmount = atoi(optarg);
			}

			break;
		}
		case 'f':
		{
			if (F_Flag)
			{
				duplicate = true;
			}
			else
			{
				F_Flag = true;
			}

			break;
		}
		case 'v':
		{
			if (V_Flag)
			{
				duplicate = true;
			}
			else
			{
				V_Flag = true;
			}

			break;
		}
        case 'o':
        {
			if (SO_Flag)
			{
				duplicate = true;
			}
			else
			{
				SO_Flag = false;

				PathToResulting = optarg;
			}

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

	if (optind < *_argCount)   //User defined a specified input file.
	{
		SI_Flag = false;

		PathToSpecified = _arguments[optind];
	}
	else
	{
		SI_Flag = true;
	}

	if (PathToResulting == NULL)
	{
		SO_Flag = true;
	}

	if (duplicate)
	{
		perror("Duplicate flag found. Please use one type of lag per execution of bmptool.");

		error = true;
	}

    if (error == true)
    {
        fprintf(stderr, usage, _arguments[0]);

        exit(1);
    }

#undef NoMoreArguments
#undef SetOptions
}

//Records the ResultingBMP image information to either a file or standard out.
void HandleResutlingBMP()
{
	int writeResult;

	if (SO_Flag)
	{
		writeResult = STDOUT_BMP(&ResultingBMP);
	}
	else
	{
		writeResult = WriteBMP(&ResultingBMP);
	}

	if (writeResult < 0)
	{
		perror("Write to BMP file failed");

		exit(1);
	}
}

//Gets BMP image information from either a file or standard in.
void HandleSpecifedBMP()
{
	int openResult = 0;

	if (SI_Flag)
	{
		openResult = STDIN_BMP(&SpecifiedBMP);
	}
	else
	{
		openResult = OpenBMP(PathToSpecified, &SpecifiedBMP, READ);
	}

	if (openResult < 0)
	{
		perror("Failed to populate SpecifiedBMP properly.\n");

		exit(1);
	}
	else
	{
		SpecifiedBMP.IsOpen = true;
	}
}

//Performs the specified transformations and stores them in ResultingBMP.
void PerformTransformations()
{
	CopyInfo_BMP(&SpecifiedBMP, &ResultingBMP);

	int openResult = 0;

	if (!SO_Flag)
	{
		openResult = OpenBMP(PathToResulting, &ResultingBMP, WRITE);
	}
	

	if (openResult < 0)
	{
		perror("OpenBMP for writing failed");

		exit(1);
	}
	else
	{
		ResultingBMP.IsOpen = true;
	}

	int transformResult;

	if (S_Flag)
	{
		transformResult = Enlarge(&ResultingBMP, ScaleToEnlarge);

		CheckTransformationResult(transformResult);
	}
	
	if (R_Flag)
	{
		bool IsRotateLegal = (RotationAmount % 90) == 0;

		if (IsRotateLegal)
		{
			transformResult = Rotate(&ResultingBMP, RotationAmount);
		}
		else
		{
			perror("Rotation amount is not a valid value, please make sure specified amount is an increment of 90 degrees.");

			exit(1);
		}

		CheckTransformationResult(transformResult);
	}
	
	if (V_Flag)
	{
		transformResult = VerticalFlip(&ResultingBMP);

		CheckTransformationResult(transformResult);
	}
	
	if (F_Flag)
	{
		transformResult = Flip(&ResultingBMP);

		CheckTransformationResult(transformResult);
	}
}

///////////////////////////////////////////////////////////////////////////////////////////////////

//-------------------------------------------------------------------------------Runtime Execution:

int main(int argc, char** argv)
{
	GetFlagsAndValues((const int *)&argc, argv);   //Gets the flags and values.

	HandleSpecifedBMP();   

	PerformTransformations();   

	HandleResutlingBMP();   

	CleanDeclares();   

	return 0;
}

///////////////////////////////////////////////////////////////////////////////////////////////////