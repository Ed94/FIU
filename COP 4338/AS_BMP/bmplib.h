/*
Edward R. Gonzalez 499406

Program name: bmptool

Description:
Using command-line. Allow the user to either, scale, rotate, or flip an BMP image file provided and return the result as
a separate bmp file.

Index:
Includes : 28
Enums    : 31
Typedefs : 73
Structs  : 93
Functions: 213

Affirmation of originality:
I affirm that I wrote this
program myself without any help form any
other people or sources from the Internet.
*/

//Header Guard
#ifndef BMPLIB_H
#define BMPLIB_H


#include <stdbool.h>


//-------------------------------------------------------------------------------------------Enums:

enum BitmapNumericalConstants
{
	DEFAULT_BITMAP_OFFSET = 1078,
};

enum BMP_Struct_Sizes   //In Bytes
{
	Size_uInt8  = 1,
	Size_uInt16 = 2,
	Size_uInt32 = 4,

	Size_uShort = 2,

	Size_Scanline = 8,

	Size_BitmapFileHeader        = 14,   //With padding its 16
	Size_BitmapFileHeaderPadding = 2 ,
	Size_BitmapInfoHeader        = 40,

	Size_Pixel = 3
};

enum BMP_File_Offsets
{
	Offset_FileHeader = 2   //Used when writing to a file or standard out.
};

enum BMP_FileMode_Def
{
	READ ,
	WRITE,
};

///////////////////////////////////////////////////////////////////////////////////////////////////


//----------------------------------------------------------------------------------------Typedefs:

typedef enum BMP_FileMode_Def BMP_FileMode;   //Used to specify the file mode for opening a BMP file.

typedef unsigned int   uInt  ;   //Size: 2 or 4 bytes (16-bit or 32-bit)
typedef unsigned char  uInt8 ;   //Size: 1-Byte
typedef unsigned short uShort;   //Size: 2-Bytes

typedef char * CString;   //C string, must treat like a pointer.

typedef struct BitmapFileHeader_Def BitmapFileHeader;   //Size: 16 bytes (2 padded)
typedef struct BitmapInfoHeader_Def BitmapInfoHeader;   //Size: 40 bytes
typedef struct Pixel_Def			Pixel           ;   //Size: 3 bytes (No Padding)
typedef struct Scanline_Def         Scanline        ;   //Size: Varies (Heap Allocated)
typedef struct Bitmap_Def		    Bitmap          ;   //Size: Varies (Heap Allocated)
typedef struct BMP_File_Def         BMP_File        ;   //Size: 60+ bytes (Varies)

///////////////////////////////////////////////////////////////////////////////////////////////////


//-----------------------------------------------------------------------------------------Structs:

struct BitmapFileHeader_Def   //Contains information about the type, size, and layout of a device-independent bitmap file.
{
	uShort Padding;   //Used structurally for the memory positioning... (REQUIRED)

	uShort FileType;   //The characters "BM" (The signature of the file being the correct type)

	uInt FileSize;   //The size of the file in bytes

	uShort Reserved1;   //Unused - must be zero
	uShort Reserved2;   //Unused - must be zero

	uInt PixelDataOffset;   //Offset to start of Pixel Data.
};

struct BitmapInfoHeader_Def   //Specifies the dimensions, compression type, and color format for the bitmap (DIB header)
{
	uInt Size;   //FileSize of InfoHeader

	int Width ;   //Image width in pixels
	int Height;   //Image height in pixels

	uShort Planes;   //Number of Planes

	uShort BitCount;   //Bits per pixel - 1, 4, 8, 16, 24, or 32

	uInt Compression;   //Compression type (0 = uncompressed)

	uInt SizeImage;   //Image FileSize - may be zero for uncompressed images

	uInt XPixelsPerMeter;   //Preferred resolution in pixels per meter
	uInt YPixelsPerMeter;   //Preferred resolution in pixels per meter

	uInt ClrUsed     ;   //Number Color Map entries that are actually used
	uInt ClrImportant;   //Number of significant colors
};

struct Pixel_Def   //Definition for an rgb pixel.
{
	uInt8 Red  ;
	uInt8 green;
	uInt8 blue ;
};

struct Scanline_Def   //Definition for a row of pixels.
{
	Pixel *PixelLine;   //Duck Type Rule: Pixel Arrays must be allocated to the heap.
};

struct Bitmap_Def   //Pixel Data [Bitmap_Def Definition] (Not the same as a color table)
{
	Scanline *ScanLines;   //Duck Type Rule: Scanline arrays must be allocated to the heap.
};

struct BMP_File_Def   //Definition for a BMP_File.
{
	bool IsOpen;   //Specifies if a file is open.

	uInt IOHandle;   //The file descriptor(i/o buffer) to read/write to.

	CString Path;   //Path to the file (if using file)

	BitmapFileHeader FileHeader;   //File Header info container.
	BitmapInfoHeader InfoHeader;   //Info Header info container.

	Bitmap PixelData;   //Bitmap container.
};

//============================================Professor's Structs==================================

typedef struct 
{
	unsigned short padding;   //Used structurally for the memory positioning... (REQUIRED)

	unsigned short bfType;   //The characters "BM" (The signature of the file being the correct type)

	unsigned int bfSize;   //The size of the file in bytes

	unsigned short bfReserved1;   //Unused - must be zero
	unsigned short bfReserved2;   //Unused - must be zero

	unsigned int bfOffBits;   //Offset to start of Pixel Data.

} BMFH_Professors;   //Contains information about the type, size, and layout of a device-independent bitmap file.

typedef struct 
{
	unsigned int biSize;   //FileSize of InfoHeader

	int biWidth;   //Image width in pixels
	int biHeight;   //Image height in pixels

	unsigned short biPlanes;   //Number of Planes

	unsigned short biBitCount;   //Bits per pixel - 1, 4, 8, 16, 24, or 32

	unsigned int biCompression;   //Compression type (0 = uncompressed)

	unsigned int biSizeImage;   //Image FileSize - may be zero for uncompressed images

	unsigned int biXPelsPerMeter;   //Preferred resolution in pixels per meter
	unsigned int biYPelsPerMeter;   //Preferred resolution in pixels per meter

	unsigned int biClrUsed     ;   //Number Color Map entries that are actually used
	unsigned int biClrImportant;   //Number of significant colors

} BMIH_Professors;   //Specifies the dimensions, compression type, and color format for the bitmap (DIB header)

typedef struct 
{
  unsigned char r;   //Red
  unsigned char g;   //Green
  unsigned char b;   //Blue

} PIXEL_Professors;   //Pixel Data (Not the same as a color table)

///////////////////////////////////////////////////////////////////////////////////////////////////


//---------------------------------------------------------------------------------------Functions:

//Allocates a bitmap's memory
int AllocateBitmap(Bitmap *_bitmapToAllocate, uInt rows, uInt columns);

//Deallocates a bitmap's memory
int DeallocateBitmap(Bitmap *_bitmapToDeAllocate, uInt _rows, uInt _columns);

//Copies a BMP_File Struct Instance.
int CopyInfo_BMP(BMP_File *_fileToCopy, BMP_File *_fileToCopyTo);

//Standard I/O

//Sets up the I/O handle for the BMP_File to standard in.
int STDIN_BMP(BMP_File *_dataToRead);

//Sets up the I/O handle for the BMP_File to standard out.
int STDOUT_BMP(BMP_File *_dataToSendOut);

//File I/O

//Deallocates a BMP_File and resets its stack values.
int CloseBMP(BMP_File *_fileToClose);

//Opens a I/O file buffer for the BMP_File and populates its headers and bitmap with the buffer's contents.
//The mode for the I/O file buffer can either be read or write. (write is read-write)
int OpenBMP(CString _path, BMP_File *_fileHolder, BMP_FileMode _modeToUse);

//Records the data of the BMP_File instance to a file or standard out.
int WriteBMP(BMP_File *_fileToWrite);

///////////////////////////////////////////////////////////////////////////////////////////////////


#endif /*BMPLIB_H*/