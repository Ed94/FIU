/*
Edward R. Gonzalez 499406

Program name: bmptool

Description:
Using command-line. Allow the user to either, scale, rotate, or flip an BMP image file provided and return the result as
a separate bmp file.

Index:
Includes            : 24
Forward Declarations: 44
Public              : 61
Private             : 589


Affirmation of originality:
I affirm that I wrote this
program myself without any help form any
other people or sources from the Internet.
*/


//----------------------------------------------------------------------------------------Includes:

#include <stdio.h>
#include <stdlib.h>
#if defined (__GNUC__)
#include <unistd.h>
#endif
#include <fcntl.h>
#include <string.h>
#include <errno.h>

#include "bmplib.h"

///////////////////////////////////////////////////////////////////////////////////////////////////


//----------------------------------------------------------------------------Forward Declarations:

int PopulateBitmap (BMP_File *_BMPFileToPopulate                                        ); 
int PopulateBuffer (BMP_File *_BMPFileToPopulate, CString _fileBuffer, int _sizeOfBuffer);
int PopulateHeaders(BMP_File *_BMPFileToPopulate                                        );

int RecordBitmap (BMP_File *_BMPFileToRecordTo                                          );
int RecordBuffer (BMP_File *_BMPFileToRecordTo, CString _recordBuffer, int _sizeOfBuffer);
int RecordHeaders(BMP_File *_BMPFileToRecordTo                                          );

///////////////////////////////////////////////////////////////////////////////////////////////////


//------------------------------------------------------------------------------------------Public:

int AllocateBitmap(Bitmap *_bitmapToAllocate, uInt _rows, uInt _columns)
{
	if ((_rows > 0) && (_columns > 0))
	{
		printf("Could not allocate the bitmap, dimensions provided incorrect");

		return -1;
	}

	_bitmapToAllocate->ScanLines = (Scanline *)malloc(Size_Scanline * _rows);

	for (uInt rowPos = 0; rowPos < _rows; rowPos++)
	{
		_bitmapToAllocate->ScanLines[rowPos].PixelLine = (Pixel *)malloc(Size_Pixel * _columns);
	}

	return 1;
}

int DeallocateBitmap(Bitmap *_bitmapToDeAllocate, uInt _rows, uInt _columns)
{
	if (_bitmapToDeAllocate->ScanLines != NULL)
	{
		for (uInt rowPos = 0; rowPos < _rows; rowPos++)
		{
			if (_bitmapToDeAllocate->ScanLines[rowPos].PixelLine != NULL)
			{
				free(_bitmapToDeAllocate->ScanLines[rowPos].PixelLine);
			}
			else
			{
				printf("Was not able to deallocate a pixel line. Aborting the deallocation.");

				return -1;
			}
		}
	}
	else
	{
		printf("Cannot deallocate the bitmap, not an accurate scanline pointer.");

		return -1;
	}

	return 0;
}

int CopyInfo_BMP(BMP_File *_fileToCopy, BMP_File *_fileToCopyTo)
{
	_fileToCopyTo->FileHeader = _fileToCopy->FileHeader;
	_fileToCopyTo->InfoHeader = _fileToCopy->InfoHeader;

	_fileToCopyTo->IsOpen = false;

	int Size_NumberOfScanlines = _fileToCopy->InfoHeader.Height * Size_Scanline;

	_fileToCopyTo->PixelData.ScanLines = (Scanline *)malloc(Size_NumberOfScanlines);

	for (int row = 0; row < _fileToCopyTo->InfoHeader.Height; row++)
	{
		_fileToCopyTo->PixelData.ScanLines[row].PixelLine = (Pixel *)malloc(Size_Pixel * _fileToCopyTo->InfoHeader.Width);

		for (int pixelPos = 0; pixelPos < _fileToCopyTo->InfoHeader.Width; pixelPos++)
		{
			_fileToCopyTo->PixelData.ScanLines[row].PixelLine[pixelPos] = _fileToCopy->PixelData.ScanLines[row].PixelLine[pixelPos];
		}
	}

	return 0;
}

int STDIN_BMP(BMP_File *_dataToRead)
{
	_dataToRead->IOHandle = 0;

	PopulateHeaders(_dataToRead);

	PopulateBitmap(_dataToRead);

	return 0;
}

int STDOUT_BMP(BMP_File *_dataToSend)
{
	_dataToSend->IOHandle = 1;

	return WriteBMP(_dataToSend);

	return 0;
}

int CloseBMP(BMP_File *_fileToClose)
{
	for (int row = 0; row < _fileToClose->InfoHeader.Height; row++)
	{
		if (_fileToClose->PixelData.ScanLines[row].PixelLine != NULL)
		{
			free(_fileToClose->PixelData.ScanLines[row].PixelLine);
		}
	}

	if (_fileToClose->PixelData.ScanLines != NULL)
	{
		free(_fileToClose->PixelData.ScanLines);
	}

	if (_fileToClose->IsOpen)
	{
		close(_fileToClose->IOHandle);
	}
	else
	{
		_fileToClose->IsOpen   = false;
	}

	_fileToClose->IOHandle = 0   ;
	_fileToClose->Path     = NULL;

	_fileToClose->FileHeader.FileSize        = 0;
	_fileToClose->FileHeader.PixelDataOffset = 0;

	_fileToClose->InfoHeader.BitCount  = 0;
	_fileToClose->InfoHeader.Height    = 0;
	_fileToClose->InfoHeader.Width     = 0;
	_fileToClose->InfoHeader.Size      = 0;
	_fileToClose->InfoHeader.SizeImage = 0;

	return 0;
}

int OpenBMP(CString _path, BMP_File *_fileHolder, BMP_FileMode _modeToUse)
{
	if (_path != NULL)
	{
		_fileHolder->Path = _path;

		int openResult;

		switch (_modeToUse)
		{
		case READ:
		{
			openResult = open(_fileHolder->Path, O_RDONLY);

			if (openResult < 0)
			{
				perror("Could not open the BMP file to read.");

				return -1;
			}
			else
			{
				_fileHolder->IOHandle = openResult;
			}

			int populateHeadersResult = PopulateHeaders(_fileHolder);

			if (populateHeadersResult)
			{
				return populateHeadersResult;
			}

			int populateBitmapResult = PopulateBitmap(_fileHolder);

			if (populateBitmapResult)
			{
				return populateBitmapResult;
			}

			break;
		}
		case WRITE:
		{
			openResult = open(_fileHolder->Path, O_RDWR | O_TRUNC | O_CREAT, 0666);

			if (openResult < 0)
			{
				perror("Can't open bmp file to write");

				return -1;
			}
			else
			{
				_fileHolder->IOHandle = openResult;
			}

			break;
		}
		default:
		{
			perror("Somehow did not properly get a valid mode to use for opening the BMP file.");

			return -1;
		}
		}
	}
	else
	{
		perror("Path cannot be undefined.");

		return -1;
	}

	return 0;
}

int WriteBMP(BMP_File *_fileToWrite)
{
	int recordResult = RecordHeaders(_fileToWrite);

	if (recordResult)
	{
		perror("Recording headers to BMP file failed.");

		return recordResult;
	}

	int recordPixelDataResult = RecordBitmap(_fileToWrite);

	if (recordPixelDataResult)
	{
		perror("Recording bitmap to BMP file failed.");

		return recordPixelDataResult;
	}

	return 0;
}

///////////////////////////////////////////////////////////////////////////////////////////////////


//-----------------------------------------------------------------------------------------Private:

int PopulateBitmap(BMP_File *_BMPFileToPopulate)
{
	int offsetCheck = _BMPFileToPopulate->FileHeader.PixelDataOffset - (Size_BitmapFileHeader + Size_BitmapInfoHeader);

	bool heightCheck    = _BMPFileToPopulate->InfoHeader.Height > 0;
	bool widthCheck     = _BMPFileToPopulate->InfoHeader.Width  > 0;

	bool bitOffsetCheck = offsetCheck == 0;

	if (heightCheck && widthCheck && bitOffsetCheck)
	{
		int Size_NumberOfScanlines = _BMPFileToPopulate->InfoHeader.Height * Size_Scanline;

		_BMPFileToPopulate->PixelData.ScanLines = (Scanline *)malloc(Size_NumberOfScanlines);

		for (int rowPosition = 0; rowPosition < _BMPFileToPopulate->InfoHeader.Height; rowPosition++)
		{
			_BMPFileToPopulate->PixelData.ScanLines[rowPosition].PixelLine = (Pixel *)malloc(Size_Pixel * _BMPFileToPopulate->InfoHeader.Width);
		}

		/*char padding[3];

		uInt padAmount = 0;

		if ((_BMPFileToPopulate->InfoHeader.Width * Size_Pixel) % 4)
		{
			uInt sizeOfWidth = _BMPFileToPopulate->InfoHeader.Width * Size_Pixel;

			padAmount = (4 - (sizeOfWidth % 4));
		}*/

		for (int rowPosition = 0; rowPosition < _BMPFileToPopulate->InfoHeader.Height; rowPosition++)
		{
			uInt rowSize = _BMPFileToPopulate->InfoHeader.Width * Size_Pixel;

			CString rowBuffer = (CString)_BMPFileToPopulate->PixelData.ScanLines[rowPosition].PixelLine;

			int rowPopulateResult = PopulateBuffer(_BMPFileToPopulate, rowBuffer, rowSize);

			if (rowPopulateResult < 0)
			{
				perror("Can't read bitmap\n");

				return -7;
			}

			/*if (padAmount > 0)
			{
				if (PopulateBuffer(_BMPFileToPopulate, padding, padAmount) < 0)
				{
					perror("Can't read bitmap");

					return -8;
				}
			}*/
		}
	}

	return 0;
}

int PopulateBuffer(BMP_File *_BMPFileToPopulate, CString _fileBuffer, int _sizeOfBuffer)
{
	int totalBytesRead = 0;   //Bytes read by the read function in total.

	while (totalBytesRead < _sizeOfBuffer)
	{
		int readBytes = read(_BMPFileToPopulate->IOHandle, &_fileBuffer[totalBytesRead], _sizeOfBuffer - totalBytesRead);

		//printf("%d", readBytes);

		if (readBytes < 0)
		{
			return readBytes;
		}
		else if (readBytes == 0)
		{
			perror("Got EOF");

			return readBytes;
		}
		else
		{
			totalBytesRead += readBytes;
		}
	}

	return _sizeOfBuffer;
}

int PopulateHeaders(BMP_File *_BMPFileToPopulate)
{
	CString fileHeaderBuffer = (CString)&_BMPFileToPopulate->FileHeader + Offset_FileHeader;
	
	int fileHeaderPopulateResult = PopulateBuffer(_BMPFileToPopulate, fileHeaderBuffer, Size_BitmapFileHeader);

	if (fileHeaderPopulateResult <= 0)
	{
		perror("Can't read BITMAPFILEHEADER");

		return -2;
	}

	CString infoHeaderBuffer = (CString)&_BMPFileToPopulate->InfoHeader;

	int infoHeaderPopulateResult = PopulateBuffer(_BMPFileToPopulate, infoHeaderBuffer, Size_BitmapInfoHeader);

	if (infoHeaderPopulateResult <= 0)
	{
		perror("Can't read BITMAPINFOHEADER");

		return -3;
	}

	if (_BMPFileToPopulate->InfoHeader.Compression != 0)
	{
		fprintf(stderr, "Can't read compressed bmp\n");

		return -4;
	}

	if (_BMPFileToPopulate->InfoHeader.BitCount != 24)
	{
		fprintf(stderr, "Can't handle bmp other than 24-bit\n");

		return -5;
	}

	return 0;
}

int RecordBitmap(BMP_File *_BMPFileToRecordTo)
{
	/*char padding[3];

	int padAmount = 0;

	if ((_BMPFileToRecordTo->InfoHeader.Width * Size_Pixel) % 4)
	{
		uInt sizeOfWidth = _BMPFileToRecordTo->InfoHeader.Width * Size_Pixel;

		padAmount = (4 - (sizeOfWidth % 4));
	}*/

	/*memset(padding, 0, 3);

	int start = _BMPFileToRecordTo->FileHeader.PixelDataOffset;

	if (start > 0)
	{
		char useless[DEFAULT_BITMAP_OFFSET];

		memset(useless, 0, start);

		if (RecordBuffer(_BMPFileToRecordTo, useless, start) < 0)
		{
			perror("Can't lseek to bitmap");

			return -6;
		}
	}*/

	for (int scanlineNum = 0; scanlineNum < _BMPFileToRecordTo->InfoHeader.Height; scanlineNum++)
	{
		CString scanlineBuffer = (CString)_BMPFileToRecordTo->PixelData.ScanLines[scanlineNum].PixelLine;

		int recordScanlineResult = RecordBuffer(_BMPFileToRecordTo, scanlineBuffer, Size_Pixel * _BMPFileToRecordTo->InfoHeader.Width);

		if (recordScanlineResult < 0)
		{
			perror("Could not write scanline to file.");

			return -7;
		}

		/*if (padAmount > 0)
		{
			if (RecordBuffer(_BMPFileToRecordTo, padding, padAmount) < 0)
			{
				perror("Can't write bitmap");

				return -8;
			}
		}*/
	}

	return 0;
}

int RecordBuffer(BMP_File *_BMPFileToRecordTo, CString _recordBuffer, int _sizeOfBuffer)
{
	int totalBytesRecorded = 0;

	while (totalBytesRecorded < _sizeOfBuffer)
	{
		int bytesRecorded = write(_BMPFileToRecordTo->IOHandle, &_recordBuffer[totalBytesRecorded], _sizeOfBuffer - totalBytesRecorded);

		if (bytesRecorded < 0)
		{
			return bytesRecorded;
		}
		else
		{
			totalBytesRecorded += bytesRecorded;
		}
	}

	return _sizeOfBuffer;
}

int RecordHeaders(BMP_File *_BMPFileToRecordTo)
{
	CString fileHeaderBuffer = (CString)&_BMPFileToRecordTo->FileHeader + Offset_FileHeader;

	int recordFileHeaderResult = RecordBuffer(_BMPFileToRecordTo, fileHeaderBuffer, Size_BitmapFileHeader);

	if (recordFileHeaderResult < 0)
	{
		perror("Could not write the file header to BMP file.");

		return -2;
	}

	CString infoHeaderBuffer = (CString)&_BMPFileToRecordTo->InfoHeader;

	int recordInfoHeaderResult = RecordBuffer(_BMPFileToRecordTo, infoHeaderBuffer, Size_BitmapInfoHeader);

	if (recordInfoHeaderResult < 0)
	{
		perror("Could not write the info header to BMP file.");

		return -3;
	}

	return 0;
}

///////////////////////////////////////////////////////////////////////////////////////////////////