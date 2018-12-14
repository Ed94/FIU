#Compling C code
CC=gcc

#Using debugger, have most warnings, and using the c99 standard.
CFLAGS=-g -Wall -std=c99

LDFLAGS=

#Source Files
SourceFiles=L2_MiniCalc.c

#Object Files
ObjectFiles=L2_MiniCalc.o

#Executable name
TARGET=minicalc

#The green part is the identifier of the final target. To the right are the requried objects to make this final target.
${TARGET}: ${ObjectFiles}
		${CC} -o ${TARGET} ${ObjectFiles} ${LDFLAGS}

#This defines what the L1_GradeCalc.o target is and what it needs to be made (I think).
${ObjectFiles}: L2_MiniCalc.c
		${CC} ${CFLAGS} -c ${SourceFiles}

#Cleans up the object files, use rm *.o will clean all object files.
clean:
	rm -f ${ObjectFiles} ${TARGET}
	