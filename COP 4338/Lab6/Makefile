#Compling C code
CC=mpicc

#Using debugger, have most warnings, and using the c99 standard.
CFLAGS= -g -Wall -std=gnu99

LDFLAGS= 

#Source Files
SourceFiles=countprimes.c

#Object Files
ObjectFiles=countprimes.o

#Executable name
TARGET=countprimes

#The green part is the identifier of the final target. To the right are the requried objects to make this final target.
${TARGET}: ${ObjectFiles}
	${CC} -o ${TARGET} ${ObjectFiles} ${LDFLAGS}

#This defines what the objects targets are and what it needs to be made.
${ObjectFiles}: ${SourceFiles}
	${CC} ${CFLAGS} -c ${SourceFiles}

#Cleans up the object files, use rm *.o will clean all object files.
clean:
	rm -f ${ObjectFiles} ${TARGET}