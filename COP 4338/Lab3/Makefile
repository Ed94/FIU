#Compling C code
CC=gcc

#Using debugger, have most warnings, and using the c99 standard.
CFLAGS=-g -Wall -std=gnu99

LDFLAGS=

#Source Files
SourceFiles=L3_MyWords.c

#Object Files
ObjectFiles=L3_MyWords.o

#Executable name
TARGET=mywords

#The green part is the identifier of the final target. To the right are the requried objects to make this final target.
${TARGET}: ${ObjectFiles}
		${CC} -o ${TARGET} ${ObjectFiles} ${LDFLAGS}

#This defines what the objects targets are and what it needs to be made (I think).
${ObjectFiles}: ${SourceFiles}
		${CC} ${CFLAGS} -c ${SourceFiles}

#Cleans up the object files, use rm *.o will clean all object files.
clean:
	rm -f ${ObjectFiles} ${TARGET}
	