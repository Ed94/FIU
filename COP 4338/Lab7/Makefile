#Compling C code
CC=gcc

#Using debugger, have most warnings, and using the c99 standard.
CFLAGS=-g -Wall -std=gnu99 -pthread

LDFLAGS= -lpthread

#Source Files
SourceFiles=tenthreads.c

#Object Files
ObjectFiles=tenthreads.o

#Executable name
TARGET=tenthreads

#The green part is the identifier of the final target. To the right are the requried objects to make this final target.
${TARGET}: ${ObjectFiles}
	${CC} -o ${TARGET} ${ObjectFiles} ${LDFLAGS}

#This defines what the objects targets are and what it needs to be made. 
	${CC} ${CFLAGS} -c ${SourceFiles}

#Cleans up the object files, use rm *.o will clean all object files.
clean:
	rm -f ${ObjectFiles} ${TARGET}
	