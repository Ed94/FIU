/*
Edward R. Gonzalez 499406

Program name: countprimes

Description:
Finds all the prime numbers between 1-50,000 (inclusive), using a partitioning strategy with MPI processes.
Will print out any prime number found immediately with the process that found it. The total
prime numbers found and elapsed time is printed at the end.

Index:
Includes             : 29
Enums                : 40
Typedefs             : 72
Structs              : 87
Global Declares      : 111
Forward Declares     : 133
Lab Related Functions: 144
Debug                : 787
Runtime Execution    : 1412

Affirmation of originality:
I affirm that I wrote this
program myself without any help form any
other people or sources from the Internet.
*/


//----------------------------------------------------------------------------------------Includes:

#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

#include "mpi.h"

///////////////////////////////////////////////////////////////////////////////////////////////////


//-------------------------------------------------------------------------------------------Enums:

enum MPI_SendTags
{
	TDataRecvFail = 0,   //Used to indicate that data failed to be retrieved.
	TDataRecieved = 1,   //Indicates data was retrieved.
	TDataStatus   = 2,   //Used to receive something related to the status of receiving data.

	TNumParitions = 3,   //Used to send and receive data related to then number of partitions

	T_VRange_First = 4,   //Used to send and receive data related to the first of a value range partition.
	T_VRange_Last  = 5,   //Used to send and receive data related to the last of the value range partition.
	
	T_PrimeRequest  = 6,   //Used to send and receive a request for prime count data.
	T_PrimeCount    = 7,   //Used to send and receive a prime count.

	T_TimeRequest = 8,   //Used to send and receive a request for a time taken data type.
	T_TimeTaken   = 9,   //Used to send and receive a time taken.
};

enum Numerical_Constants
{
	MPI_BufferSize = 1024,   //Currently set to 1024 bytes. Maybe more in the future...

	MPI_MasterID = 0,   //ID of the master process.

	N_value = 50000,   //Hard-coded end of range value.
};

///////////////////////////////////////////////////////////////////////////////////////////////////


//----------------------------------------------------------------------------------------TypeDefs:

typedef unsigned      int uInt  ;   //Unsigned int type.
typedef unsigned long int uInt32;   //Unsigned 32 bit int type.

typedef char* CString;   //A character array. Must be treated as a pointer. Address to start of the array.

typedef char MPI_Buffer[MPI_BufferSize];   //A fixed size buffer for MPI comms. Set to MPI_BufferSize.

typedef struct MPI_Process_Def MPI_Process;   //Contains information related to an MPI_Process.
typedef struct ValueRange_Def  ValueRange ;   //Contains a value range.

///////////////////////////////////////////////////////////////////////////////////////////////////


//-----------------------------------------------------------------------------------------Structs:

struct MPI_Process_Def   //Defines an MPI_Process.
{
	bool Initalized;   //States whether MPI for the process was initialized.

	int CommSize;   //Total number of processes in comm world.
	int Rank;   //Rank of process in comm world.

	MPI_Buffer InBuffer;   //Buffer used to receive data from comm world.
	MPI_Buffer OutBuffer;   //Buffer used to send data to through comm world.

	MPI_Status Status;   //Status of the last received data.
};

struct ValueRange_Def   //Defines a value range.
{
	uInt Start;   //Beginning of range.
	uInt End  ;   //End of range.
};

///////////////////////////////////////////////////////////////////////////////////////////////////


//---------------------------------------------------------------------------------Global Declares:

//Constants

static const ValueRange NumRange = { 1, N_value };   //Number range has been hard-coded to 1-50,000 inclusive.

//Statics

static double ElapsedTime;   //Elapsed time of a process.
static double StartTime  ;   //Time when process started tasks.
static double EndTime    ;   //Time when process completed tasks.

static MPI_Process MPI_ProcessInfo;   //Information related to the MPI process.

static uInt NumOfPartitions;   //Number of valid partitions used in this runtime.
static uInt PrimeCount     ;   //Each process keeps a tally. Master tallies total at the end.

static ValueRange RangePartition;   //Remove when the above is implemented.

///////////////////////////////////////////////////////////////////////////////////////////////////


//--------------------------------------------------------------------------------Forward Declares:

bool MPI_SendValueRange(ValueRange _valueRangeToSend, uInt slaveID);

uInt GetValidPartitionNum(uInt _desiredNumParititions, ValueRange _range);

ValueRange GetPartitionOfRange(uInt _partitionIndex, uInt _numPartitions, ValueRange _range);

///////////////////////////////////////////////////////////////////////////////////////////////////


//---------------------------------------------------------------------------Lab Related Functions:

//Master process receives prime count and time taken and tallies the totals.
bool CalculateStatistics()
{
	for (uInt slaveID = 1; slaveID < NumOfPartitions; slaveID++)
	{
		//Prime Count

		sprintf(MPI_ProcessInfo.OutBuffer, "%u", (uInt)T_PrimeRequest);

		MPI_Send
		(
			MPI_ProcessInfo.OutBuffer,
			MPI_BufferSize           ,
			MPI_CHAR                 ,
			slaveID                  ,
			T_PrimeRequest           ,
			MPI_COMM_WORLD
		);

		MPI_Recv
		(
			MPI_ProcessInfo.InBuffer,
			MPI_BufferSize          ,
			MPI_CHAR                ,
			slaveID                 ,
			T_PrimeCount            ,
			MPI_COMM_WORLD          ,
			&MPI_ProcessInfo.Status
		);

		if (MPI_ProcessInfo.Status.MPI_ERROR > 0)
		{
			return false;
		}

		uInt sPrimeCount;

		sscanf(MPI_ProcessInfo.InBuffer, "%u", &sPrimeCount);

		PrimeCount += sPrimeCount;

		//Time Taken

		sprintf(MPI_ProcessInfo.OutBuffer, "%u", (uInt)T_TimeRequest);

		MPI_Send
		(
			MPI_ProcessInfo.OutBuffer,
			MPI_BufferSize           ,
			MPI_CHAR                 ,
			slaveID                  ,
			T_TimeRequest            ,
			MPI_COMM_WORLD
		);

		MPI_Recv
		(
			MPI_ProcessInfo.InBuffer,
			MPI_BufferSize          ,
			MPI_CHAR                ,
			slaveID                 ,
			T_TimeTaken             ,
			MPI_COMM_WORLD          ,
			&MPI_ProcessInfo.Status
		);

		double sElapsedTime;

		sscanf(MPI_ProcessInfo.InBuffer, "%lf", &sElapsedTime);

		ElapsedTime += sElapsedTime;
	}

	return true;
}

//Gets partition information from the master process.
bool GetPartitionInfo()
{
	bool result = true;

	//Number of Partitions

	MPI_Recv
	(
		MPI_ProcessInfo.InBuffer,
		MPI_BufferSize          ,
		MPI_CHAR                ,
		MPI_MasterID            ,
		TNumParitions           ,
		MPI_COMM_WORLD          ,
		&MPI_ProcessInfo.Status
	);

	bool possibleError = MPI_ProcessInfo.Status.MPI_ERROR;

	if (possibleError > 0)
	{
		result = false;

		return result;
	}

	sscanf(MPI_ProcessInfo.InBuffer, "%u", &NumOfPartitions);

	sprintf(MPI_ProcessInfo.OutBuffer, "%u", (uInt)TDataRecieved);

	MPI_Send
	(
		MPI_ProcessInfo.OutBuffer,
		MPI_BufferSize           ,
		MPI_CHAR                 ,
		MPI_MasterID             ,
		TDataStatus              ,
		MPI_COMM_WORLD
	);

	if (((uInt)MPI_ProcessInfo.Rank + 1) > NumOfPartitions)
	{
		return result;
	}

	//Ranged Partition: Start

	MPI_Recv
	(
		MPI_ProcessInfo.InBuffer,
		MPI_BufferSize          ,
		MPI_CHAR                ,
		MPI_MasterID            ,
		T_VRange_First          ,
		MPI_COMM_WORLD          ,
		&MPI_ProcessInfo.Status
	);

	possibleError = MPI_ProcessInfo.Status.MPI_ERROR;

	if (possibleError > 0)
	{
		result = false;

		return result;
	}

	sscanf(MPI_ProcessInfo.InBuffer, "%u", &RangePartition.Start);

	sprintf(MPI_ProcessInfo.OutBuffer, "%u", (uInt)TDataRecieved);

	MPI_Send
	(
		MPI_ProcessInfo.OutBuffer,
		MPI_BufferSize           ,
		MPI_CHAR                 ,
		MPI_MasterID             ,
		TDataStatus              ,
		MPI_COMM_WORLD
	);

	//Ranged Partition: End

	MPI_Recv
	(
		MPI_ProcessInfo.InBuffer,
		MPI_BufferSize          ,
		MPI_CHAR                ,
		MPI_MasterID            ,
		T_VRange_Last           ,
		MPI_COMM_WORLD          ,
		&MPI_ProcessInfo.Status
	);

	possibleError = MPI_ProcessInfo.Status.MPI_ERROR;

	if (possibleError > 0)
	{
		result = false;

		return result;
	}

	sscanf(MPI_ProcessInfo.InBuffer, "%u", &RangePartition.End);

	sprintf(MPI_ProcessInfo.OutBuffer, "%u", (uInt)TDataRecieved);

	MPI_Send
	(
		MPI_ProcessInfo.OutBuffer,
		MPI_BufferSize           ,
		MPI_CHAR                 ,
		MPI_MasterID             ,
		TDataStatus              ,
		MPI_COMM_WORLD
	);

	return result;
}

//Handles the generation and distribution of partitions.
bool HandlePartitioning()
{
	bool partitioningResult = true;

	NumOfPartitions = GetValidPartitionNum(MPI_ProcessInfo.CommSize, NumRange);

	ValueRange *RangePartitions = (ValueRange *)malloc(sizeof(ValueRange) * NumOfPartitions);

	for (uInt index = 0; index < NumOfPartitions; index++)
	{
		RangePartitions[index] = GetPartitionOfRange(index, NumOfPartitions, NumRange);
	}

	RangePartition.Start = RangePartitions[MPI_MasterID].Start;
	RangePartition.End   = RangePartitions[MPI_MasterID].End  ;

	ValueRange Empty = { 0, 0 };

	for (uInt index = 1; index < (uInt)MPI_ProcessInfo.CommSize; index++)
	{
		if (index <= NumOfPartitions)
		{
			partitioningResult = MPI_SendValueRange(RangePartitions[index], index);
		}
		else
		{
			partitioningResult = MPI_SendValueRange(Empty, index);
		}
	}

	free(RangePartitions);

	return partitioningResult;
}

//Determines if the given rank is the master process for MPI.
bool MPI_Master(uInt _rankToCheck)
{
	return _rankToCheck == MPI_MasterID;
}

bool MPI_SendValueRange(ValueRange _valueRangeToSend, uInt slaveID)
{
	if (!MPI_Master(MPI_ProcessInfo.Rank))
	{
		perror("Attempted to send out Value Range data from a slave.");

		exit(1);
	}

	//Number of Partitions

	sprintf(MPI_ProcessInfo.OutBuffer, "%u", NumOfPartitions);

	MPI_Send
	(
		MPI_ProcessInfo.OutBuffer,
		MPI_BufferSize           ,
		MPI_CHAR                 ,
		slaveID                  ,
		TNumParitions            ,
		MPI_COMM_WORLD
	);

	MPI_Recv
	(
		MPI_ProcessInfo.InBuffer,
		MPI_BufferSize          ,
		MPI_CHAR                ,
		slaveID                 ,
		TDataStatus             ,
		MPI_COMM_WORLD          ,
		&MPI_ProcessInfo.Status
	);

	uInt result;

	sscanf(MPI_ProcessInfo.InBuffer, "%u", &result);

	if (!result)
	{
		return result;
	}

	if ((slaveID + 1) > NumOfPartitions)
	{
		return true;
	}

	//Value Range: Start

	sprintf(MPI_ProcessInfo.OutBuffer, "%u", _valueRangeToSend.Start);

	MPI_Send
	(
		MPI_ProcessInfo.OutBuffer,
		MPI_BufferSize           ,
		MPI_CHAR                 ,
		slaveID                  ,
		T_VRange_First           ,
		MPI_COMM_WORLD
	);

	MPI_Recv
	(
		MPI_ProcessInfo.InBuffer,
		MPI_BufferSize          ,
		MPI_CHAR                ,
		slaveID                 ,
		TDataStatus             ,
		MPI_COMM_WORLD          ,
		&MPI_ProcessInfo.Status
	);

	sscanf(MPI_ProcessInfo.InBuffer, "%d", (int *)&result);

	if (!result)
	{
		return result;
	}

	//Value Range: End

	sprintf(MPI_ProcessInfo.OutBuffer, "%u", _valueRangeToSend.End);

	MPI_Send
	(
		MPI_ProcessInfo.OutBuffer,
		MPI_BufferSize           ,
		MPI_CHAR                 ,
		slaveID                  ,
		T_VRange_Last            ,
		MPI_COMM_WORLD
	);

	MPI_Recv
	(
		MPI_ProcessInfo.InBuffer,
		MPI_BufferSize          ,
		MPI_CHAR                ,
		slaveID                 ,
		TDataStatus             ,
		MPI_COMM_WORLD          ,
		&MPI_ProcessInfo.Status
	);

	sscanf(MPI_ProcessInfo.InBuffer, "%d", (int *)&result);

	if (!result)
	{
		return result;
	}

	return true;
}

//Determines if the given rank a slave process for MPI.
bool MPI_Slave(uInt _rankToCheck)
{
	return _rankToCheck > MPI_MasterID;
}

//Sends slave's prime count and timer taken to the master process.
bool SendStatistics()
{
	//Prime Count.

	MPI_Recv
	(
		MPI_ProcessInfo.InBuffer,
		MPI_BufferSize          ,
		MPI_CHAR                ,
		MPI_MasterID            ,
		T_PrimeRequest          ,
		MPI_COMM_WORLD          ,
		&MPI_ProcessInfo.Status
	);

	if (MPI_ProcessInfo.Status.MPI_ERROR > 0)
	{
		return false;
	}

	sprintf(MPI_ProcessInfo.OutBuffer, "%u", PrimeCount);

	MPI_Send
	(
		MPI_ProcessInfo.OutBuffer,
		MPI_BufferSize           ,
		MPI_CHAR                 ,
		MPI_MasterID             ,
		T_PrimeCount             ,
		MPI_COMM_WORLD
	);

	//Elapsed Time

	MPI_Recv
	(
		MPI_ProcessInfo.InBuffer,
		MPI_BufferSize          ,
		MPI_CHAR                ,
		MPI_MasterID            ,
		T_TimeRequest           ,
		MPI_COMM_WORLD          ,
		&MPI_ProcessInfo.Status
	);

	if (MPI_ProcessInfo.Status.MPI_ERROR > 0)
	{
		return false;
	}

	sprintf(MPI_ProcessInfo.OutBuffer, "%lf", ElapsedTime);

	MPI_Send
	(
		MPI_ProcessInfo.OutBuffer,
		MPI_BufferSize           ,
		MPI_CHAR                 ,
		MPI_MasterID             ,
		T_TimeTaken              ,
		MPI_COMM_WORLD
	);

	return true;
}

//Gets a value range specified from a given range and number of partitions.
ValueRange GetPartitionOfRange(uInt _partitionIndex, uInt _numPartitions, ValueRange _range)
{
	uInt rangeCount = _range.End - _range.Start;

	if ((_range.Start % 2) != 0)
	{
		rangeCount = _range.End - _range.Start + 1;
	}

	bool CanPartition = (rangeCount % _numPartitions) == 0;

	if (CanPartition)
	{
		uInt increment = rangeCount / _numPartitions;

		ValueRange partition;

		partition.Start = increment * (_partitionIndex)+_range.Start;

		if ( ( (_range.Start % 2) != 0) && (_partitionIndex == 0) && (_numPartitions > 1))
		{
			partition.End = increment * (_partitionIndex + 1) + _range.Start;
		}
		else
		{
			partition.End = increment * (_partitionIndex + 1);
		}

		return partition;
	}
	else
	{
		perror("Cannot partition, partitioning is not clean with partition number specified.");

		exit(1);
	}
}

//Provides a valid number of partitions that can be distributed to processes.
uInt GetValidPartitionNum(uInt _desiredNumParititions, ValueRange _range)
{
	uInt possiblePartitionNum = _desiredNumParititions;

	uInt rangeCount = _range.End - _range.Start;

	if ((_range.Start % 2) != 0)
	{
		rangeCount = _range.End - _range.Start + 1;
	}

	bool CanPartition = false;

	while (!CanPartition)
	{
		uInt test = (rangeCount % possiblePartitionNum);

		CanPartition = (test == 0);

		if (CanPartition == false)
		{
			possiblePartitionNum = possiblePartitionNum - 1;
		}

		if (possiblePartitionNum <= 0)
		{
			perror("Cannot find a valid partition number from the possible number of partitions");

			exit(1);
		}
	}

	return possiblePartitionNum;
}

//Overhead function that manages the finalization of MPI.
void CleanMPI()
{
	MPI_Finalize();
}

//Processes a value range to identify all prime values.
void CheckRangeForPrimes(ValueRange _rangeToCheck)
{
	for (uInt index = _rangeToCheck.Start; index <= _rangeToCheck.End; index++)
	{
		if (index > 0)
		{
			bool IsPrime = true;

			for (uInt moduloCheck = 2; (moduloCheck <= (index / 2)) || (IsPrime == false); moduloCheck++)
			{
				if ((index % moduloCheck) == 0)
				{
					IsPrime = false;

					break;
				}
			}

			if (IsPrime)
			{
				if (MPI_Master(MPI_ProcessInfo.Rank))
				{
					printf("Prime Found. Process: Master, Prime Number: %d\n", index);
				}
				else
				{
					printf("Prime Found. Slave Process: %d, Prime Number: %d\n", MPI_ProcessInfo.Rank, index);
				}

				PrimeCount++;
			}
		}
	}
}

//Handles tasks for processes related to prime counting.
void PrimeCountingTask()
{
	PrimeCount = 0;

	CheckRangeForPrimes(RangePartition);

	EndTime = MPI_Wtime();   //Gets the time after completing tasks.

	ElapsedTime = EndTime - StartTime;   //Calculates the elapsed time.
}

//Handles tasks for processes related to partitioning.
void PartitioningTask()
{
	if (MPI_Master(MPI_ProcessInfo.Rank))
	{
		StartTime = MPI_Wtime();

		bool partitioningResult = HandlePartitioning();

		if (!partitioningResult)
		{
			perror("Master could not properly handle partitioning.");

			exit(1);
		}
	}
	else if (MPI_Slave(MPI_ProcessInfo.Rank))
	{
		StartTime = MPI_Wtime();   //Gets the time before doing associated tasks.

		bool partitionResult = GetPartitionInfo();

		if (!partitionResult)
		{
			perror("Could not get the partition info from the master.");

			exit(1);
		}
	}
	else
	{
		perror("Could not determine process identity.");

		exit(1);
	}
}

//Overhead function that manages the initialization of MPI.
void StartMPI(int *_argCountRef, CString **_argValueRef)
{
	if (MPI_Init(_argCountRef, _argValueRef) == MPI_SUCCESS)
	{
		MPI_ProcessInfo.Initalized = true;
	}
	else
	{
		MPI_ProcessInfo.Initalized = false;
	}

	MPI_Comm_size(MPI_COMM_WORLD, &MPI_ProcessInfo.CommSize);
	MPI_Comm_rank(MPI_COMM_WORLD, &MPI_ProcessInfo.Rank);
}

//Handles tasks for processes related to prime count and time taken reporting.
void StatisticsTask()
{
	if (MPI_Master(MPI_ProcessInfo.Rank))
	{
		bool statsCalcResult = CalculateStatistics();

		if (!statsCalcResult)
		{
			printf("Could not produce proper stats for end of program.");

			exit(1);
		}

		printf("\nTotal Prime Count : %u\n" , PrimeCount );
		printf("Total Elapsed Time: %lf\n\n", ElapsedTime);
	}
	else   //Its the slave.
	{
		bool sendStatsResult = SendStatistics();

		if (!sendStatsResult)
		{
			printf("Could not send results to master properly.");

			exit(1);
		}
	}
}
	
///////////////////////////////////////////////////////////////////////////////////////////////////


//-------------------------------------------------------------------------------------------Debug:

//Debug Macros

//Used during debug as a ease of use for printing a new line.
#define cpnl \
printf("\n")

//Debug Forward Declares
bool MPI_SendValueRangeDebug(ValueRange _valueRangeToSend, uInt slaveID);

void Print_MPIINFO      ();
void Print_PartitionInfo();

//Debug Functions

//Master process receives prime count and time taken and tallies the totals. (Debug Ver.)
bool CalculateStatisticsDebug()
{
	cpnl; printf("Master Prime Count: %u" , PrimeCount );
	cpnl; printf("Master Time Taken : %lf", ElapsedTime); cpnl;

	for (uInt slaveID = 1; slaveID < NumOfPartitions; slaveID++)
	{
		//Prime Count

		sprintf(MPI_ProcessInfo.OutBuffer, "%u", (uInt)T_PrimeRequest);

		MPI_Send
		(
			MPI_ProcessInfo.OutBuffer,
			MPI_BufferSize           ,
			MPI_CHAR                 ,
			slaveID                  ,
			T_PrimeRequest           ,
			MPI_COMM_WORLD
		);

		cpnl;  printf("Waiting for Slave %d's prime count...", slaveID); cpnl;

		MPI_Recv
		(
			MPI_ProcessInfo.InBuffer,
			MPI_BufferSize          ,
			MPI_CHAR                ,
			slaveID                 ,
			T_PrimeCount            ,
			MPI_COMM_WORLD          ,
			&MPI_ProcessInfo.Status
		);

		if (MPI_ProcessInfo.Status.MPI_ERROR > 0)
		{
			return false;
		}

		uInt sPrimeCount;

		sscanf(MPI_ProcessInfo.InBuffer, "%u", &sPrimeCount);

		printf("Received slave %d's prime count.", slaveID); cpnl;

		cpnl; printf("Slave: %d, Prime Count: %u", slaveID, sPrimeCount); cpnl;

		PrimeCount += sPrimeCount;

		//Time Taken

		sprintf(MPI_ProcessInfo.OutBuffer, "%u", (uInt)T_TimeRequest);

		MPI_Send
		(
			MPI_ProcessInfo.OutBuffer,
			MPI_BufferSize           ,
			MPI_CHAR                 ,
			slaveID                  ,
			T_TimeRequest            ,
			MPI_COMM_WORLD
		);

		cpnl; printf("Waiting for slave %d's time taken.", slaveID); cpnl;

		MPI_Recv
		(
			MPI_ProcessInfo.InBuffer,
			MPI_BufferSize          ,
			MPI_CHAR                ,
			slaveID                 ,
			T_TimeTaken             ,
			MPI_COMM_WORLD          ,
			&MPI_ProcessInfo.Status
		);

		double sElapsedTime;

		sscanf(MPI_ProcessInfo.InBuffer, "%lf", &sElapsedTime);

		printf("Received slave %d's time taken: %lf", slaveID, sElapsedTime); cpnl;

		ElapsedTime += sElapsedTime;
	}

	return true;
}

//Handles the generation and distribution of partitions. (Debug Ver.)
bool HandlePartitioningDebug()
{
	printf("Beginning to handle partitioning."); cpnl;

	bool partitioningResult = true;

	NumOfPartitions = GetValidPartitionNum(MPI_ProcessInfo.CommSize, NumRange);

	ValueRange *RangePartitions = (ValueRange *)malloc(sizeof(ValueRange) * NumOfPartitions);

	for (uInt index = 0; index < NumOfPartitions; index++)
	{
		RangePartitions[index] = GetPartitionOfRange(index, NumOfPartitions, NumRange);
	}

	RangePartition.Start = RangePartitions[MPI_MasterID].Start;
	RangePartition.End   = RangePartitions[MPI_MasterID].End  ;

	ValueRange Empty = { 0, 0 };

	for (uInt index = 1; index < (uInt)MPI_ProcessInfo.CommSize; index++)
	{
		if (index <= NumOfPartitions)
		{
			partitioningResult = MPI_SendValueRangeDebug(RangePartitions[index], index);
		}
		else
		{
			partitioningResult = MPI_SendValueRangeDebug(Empty, index);
		}
	}

	free(RangePartitions);

	return partitioningResult;
}

//Sends slave's prime count and timer taken to the master process. (Debug Ver.)
bool SendStatisticsDebug()
{
	//Prime Count.

	cpnl; printf("Waiting for Master request for prime count..."); cpnl;

	MPI_Recv
	(
		MPI_ProcessInfo.InBuffer,
		MPI_BufferSize          ,
		MPI_CHAR                ,
		MPI_MasterID            ,
		T_PrimeRequest          ,
		MPI_COMM_WORLD          ,
		&MPI_ProcessInfo.Status
	);

	if (MPI_ProcessInfo.Status.MPI_ERROR > 0)
	{
		return false;
	}

	printf("Request for prime count received."); cpnl;

	sprintf(MPI_ProcessInfo.OutBuffer, "%u", PrimeCount);

	MPI_Send
	(
		MPI_ProcessInfo.OutBuffer,
		MPI_BufferSize           ,
		MPI_CHAR                 ,
		MPI_MasterID             ,
		T_PrimeCount             ,
		MPI_COMM_WORLD
	);

	printf("Sent master the prime count. (%d)", MPI_ProcessInfo.Rank); cpnl;

	//Elapsed Time

	MPI_Recv
	(
		MPI_ProcessInfo.InBuffer,
		MPI_BufferSize          ,
		MPI_CHAR                ,
		MPI_MasterID            ,
		T_TimeRequest           ,
		MPI_COMM_WORLD          ,
		&MPI_ProcessInfo.Status
	);

	if (MPI_ProcessInfo.Status.MPI_ERROR > 0)
	{
		return false;
	}

	printf("Request for elapsed time received."); cpnl;

	sprintf(MPI_ProcessInfo.OutBuffer, "%lf", ElapsedTime);

	MPI_Send
	(
		MPI_ProcessInfo.OutBuffer,
		MPI_BufferSize           ,
		MPI_CHAR                 ,
		MPI_MasterID             ,
		T_TimeTaken              ,
		MPI_COMM_WORLD
	);

	printf("Sent master the elapsed time. (%d)", MPI_ProcessInfo.Rank); cpnl;

	return true;
}

//Gets partition information from the master process. (Debug Ver.)
bool GetPartitionInfoDebug()
{
	bool result = true;

	//Number of Partitions

	MPI_Recv
	(
		MPI_ProcessInfo.InBuffer,
		MPI_BufferSize          ,
		MPI_CHAR                ,
		MPI_MasterID            ,
		TNumParitions           ,
		MPI_COMM_WORLD          ,
		&MPI_ProcessInfo.Status
	);

	bool possibleError = MPI_ProcessInfo.Status.MPI_ERROR;

	if (possibleError > 0)
	{
		result = false;

		return result;
	}

	sscanf(MPI_ProcessInfo.InBuffer, "%u", &NumOfPartitions);

	sprintf(MPI_ProcessInfo.OutBuffer, "%u", (uInt)TDataRecieved);

	MPI_Send
	(
		MPI_ProcessInfo.OutBuffer,
		MPI_BufferSize           ,
		MPI_CHAR                 ,
		MPI_MasterID             ,
		TDataStatus              ,
		MPI_COMM_WORLD
	);

	printf("Slave: %d, Sent out confirmation of number of partitions: %d", MPI_ProcessInfo.Rank, NumOfPartitions);
	
	cpnl;

	if (((uInt)MPI_ProcessInfo.Rank + 1) > NumOfPartitions)
	{
		return result;
	}

	//Ranged Partition: Start

	MPI_Recv
	(
		MPI_ProcessInfo.InBuffer,
		MPI_BufferSize          ,
		MPI_CHAR                ,
		MPI_MasterID            ,
		T_VRange_First          ,
		MPI_COMM_WORLD          ,
		&MPI_ProcessInfo.Status
	);


	possibleError = MPI_ProcessInfo.Status.MPI_ERROR;

	if (possibleError > 0)
	{
		result = false;

		return result;
	}

	sscanf(MPI_ProcessInfo.InBuffer, "%u", &RangePartition.Start);

	sprintf(MPI_ProcessInfo.OutBuffer, "%u", (uInt)TDataRecieved);

	MPI_Send
	(
		MPI_ProcessInfo.OutBuffer,
		MPI_BufferSize           ,
		MPI_CHAR                 ,
		MPI_MasterID             ,
		TDataStatus              ,
		MPI_COMM_WORLD
	);

	printf("Slave: %d, Sent out confirmation of first val range: %d", MPI_ProcessInfo.Rank, RangePartition.Start);

	cpnl;

	//Ranged Partition: End

	MPI_Recv
	(
		MPI_ProcessInfo.InBuffer,
		MPI_BufferSize          ,
		MPI_CHAR                ,
		MPI_MasterID            ,
		T_VRange_Last           ,
		MPI_COMM_WORLD          ,
		&MPI_ProcessInfo.Status
	);

	possibleError = MPI_ProcessInfo.Status.MPI_ERROR;

	if (possibleError > 0)
	{
		result = false;

		return result;
	}

	sscanf(MPI_ProcessInfo.InBuffer, "%u", &RangePartition.End);

	sprintf(MPI_ProcessInfo.OutBuffer, "%u", (uInt)TDataRecieved);

	MPI_Send
	(
		MPI_ProcessInfo.OutBuffer,
		MPI_BufferSize           ,
		MPI_CHAR                 ,
		MPI_MasterID             ,
		TDataStatus              ,
		MPI_COMM_WORLD
	);

	printf("Slave: %d, Sent out confirmation of last val range: %d", MPI_ProcessInfo.Rank, RangePartition.End);

	cpnl;

	return result;
}

//Must be done only by the master process. (Debug Ver.)
bool MPI_SendValueRangeDebug(ValueRange _valueRangeToSend, uInt slaveID)
{
	printf("Attempting to send value range to Slave: %u", slaveID); cpnl;

	if (!MPI_Master(MPI_ProcessInfo.Rank))
	{
		perror("Attempted to send out Value Range data from a slave."); cpnl;

		exit(1);
	}

	//Number of Partitions

	sprintf(MPI_ProcessInfo.OutBuffer, "%u", NumOfPartitions);

	MPI_Send
	(
		MPI_ProcessInfo.OutBuffer,
		MPI_BufferSize           ,
		MPI_CHAR                 ,
		slaveID                  ,
		TNumParitions            ,
		MPI_COMM_WORLD
	);

	printf("Master: Sent out the value for number of partitions. To: %d", slaveID); cpnl;

	MPI_Recv
	(
		MPI_ProcessInfo.InBuffer,
		MPI_BufferSize          ,
		MPI_CHAR                ,
		slaveID                 ,
		TDataStatus             ,
		MPI_COMM_WORLD          ,
		&MPI_ProcessInfo.Status
	);

	uInt result;

	sscanf(MPI_ProcessInfo.InBuffer, "%u", &result);

	printf("Confirmation Result for NumPartitions (Master): %d", result); cpnl;

	if (!result)
	{
		return result;
	}

	if ((slaveID + 1) > NumOfPartitions)
	{
		return true;
	}

	//Value Range: Start

	sprintf(MPI_ProcessInfo.OutBuffer, "%u", _valueRangeToSend.Start);

	MPI_Send
	(
		MPI_ProcessInfo.OutBuffer,
		MPI_BufferSize           ,
		MPI_CHAR                 ,
		slaveID                  ,
		T_VRange_First           ,
		MPI_COMM_WORLD
	);

	printf("Master: Sent out 'First' var of Value Range. To: %d", slaveID); cpnl;

	MPI_Recv
	(
		MPI_ProcessInfo.InBuffer,
		MPI_BufferSize          ,
		MPI_CHAR                ,
		slaveID                 ,
		TDataStatus             ,
		MPI_COMM_WORLD          ,
		&MPI_ProcessInfo.Status
	);

	sscanf(MPI_ProcessInfo.InBuffer, "%d", (int *)&result);

	printf("Confirmation Result Value Range First (Master): %d", (int)result); cpnl;

	if (!result)
	{
		return result;
	}

	//Value Range: End

	sprintf(MPI_ProcessInfo.OutBuffer, "%u", _valueRangeToSend.End);

	MPI_Send
	(
		MPI_ProcessInfo.OutBuffer,
		MPI_BufferSize           ,
		MPI_CHAR                 ,
		slaveID                  ,
		T_VRange_Last            ,
		MPI_COMM_WORLD
	);

	printf("Master: Sent out 'Last' var of Value Range. To: %d", slaveID); cpnl;

	MPI_Recv
	(
		MPI_ProcessInfo.InBuffer,
		MPI_BufferSize          ,
		MPI_CHAR                ,
		slaveID                 ,
		TDataStatus             ,
		MPI_COMM_WORLD          ,
		&MPI_ProcessInfo.Status
	);

	sscanf(MPI_ProcessInfo.InBuffer, "%d", (int *)&result);

	printf("Confirmation Result Value Range Last (Master): %d", (int)result); cpnl;

	if (!result)
	{
		return result;
	}

	return true;
}

//A rigorous version of the regular runtime used during development.
void DebugRuntime(int *argc, CString **argv)
{
	cpnl; printf("Lab 6: Prime Counter"); cpnl; cpnl;

	printf("Starting MPI..."); cpnl;
	
	StartMPI(argc, argv);
	
	cpnl; cpnl; printf("MPI Process started. (%d)", MPI_ProcessInfo.Rank); cpnl;

	Print_MPIINFO();

	if (MPI_Master(MPI_ProcessInfo.Rank))
	{
		printf("Master process identified."); cpnl; cpnl;

		StartTime = MPI_Wtime();

		bool partitioningResult = HandlePartitioningDebug();

		if (!partitioningResult)
		{
			perror("Master could not properly handle partitioning.");

			exit(1);
		}

		Print_PartitionInfo();
	}
	else if (MPI_Slave(MPI_ProcessInfo.Rank))
	{
		printf("Slave process identified."); cpnl; cpnl;

		StartTime = MPI_Wtime();   //Gets the time before doing associated tasks.

		bool partitionResult = GetPartitionInfoDebug();

		if (!partitionResult)
		{
			perror("Could not get the partition info from the master.");
			
			exit(1);
		}

		Print_PartitionInfo();
	}
	else
	{
		perror("Could not determine process identity.");

		exit(1);
	}

	if (((uInt)MPI_ProcessInfo.Rank + 1) <= NumOfPartitions)
	{
		cpnl; cpnl; printf("Beginning prime check through partition."); cpnl; cpnl;
		
		PrimeCount = 0;

		CheckRangeForPrimes(RangePartition);

		EndTime = MPI_Wtime();   //Gets the time after completing tasks.

		ElapsedTime = EndTime - StartTime;   //Calculates the elapsed time.

		cpnl; printf("Start: %lf End: %lf, %d", StartTime, EndTime, MPI_ProcessInfo.Rank); cpnl;

		if (MPI_Master(MPI_ProcessInfo.Rank))
		{
			bool statsCalcResult = CalculateStatisticsDebug();

			if (!statsCalcResult)
			{
				printf("Could not produce proper stats for end of program.");

				exit(1);
			}

			cpnl; cpnl; 
			
			printf("Total Prime Count : %u" , PrimeCount ); cpnl;
			printf("Total Elapsed Time: %lf", ElapsedTime); cpnl; cpnl;
			
			cpnl; cpnl;
		}
		else   //Its the slave.
		{
			bool sendStatsResult = SendStatisticsDebug();

			if (!sendStatsResult)
			{
				printf("Could not send results to master properly.");

				exit(1);
			}
		}
	}
	else
	{
		printf("Slave Process: %d, was not given a partition.", MPI_ProcessInfo.Rank);

		printf("This was due to not being able produce enough valid partitions for it to use.\n");

		printf("Slave Process: %d, will finalize early.\n", MPI_ProcessInfo.Rank);
	}

	CleanMPI();
	
	printf("Exiting with code: SUCCESS (%d)", MPI_ProcessInfo.Rank); cpnl; cpnl;

	exit(0);
}

//Prints MPI process information.
void Print_MPIINFO()
{
	cpnl; printf("MPI Process Information:"); cpnl; cpnl;

	printf("Initialized            : %d", MPI_ProcessInfo.Initalized); cpnl;
	printf("Communicator Group Size: %d", MPI_ProcessInfo.CommSize  ); cpnl;
	printf("Process Rank           : %d", MPI_ProcessInfo.Rank      ); cpnl;

	cpnl;
}

//Prints the partition information for the current process.
void Print_PartitionInfo()
{
	cpnl; printf("Partition Information:"); cpnl; cpnl;

	printf("Number of Partitions: %d", NumOfPartitions     ); cpnl;
	printf("Partition Index     : %d", MPI_ProcessInfo.Rank); cpnl;
	printf("Partition Start     : %d", RangePartition.Start); cpnl;
	printf("Partition End       : %d", RangePartition.End  ); cpnl;

	cpnl;
}

///////////////////////////////////////////////////////////////////////////////////////////////////


//-------------------------------------------------------------------------------Runtime Execution:

int main(int argc, char *argv[])
{
	//DebugRuntime(&argc, &argv);

	//Final Runtime:

	StartMPI(&argc, &argv);

	PartitioningTask();

	if (((uInt)MPI_ProcessInfo.Rank + 1) <= NumOfPartitions)
	{
		PrimeCountingTask();

		StatisticsTask();
	}

	CleanMPI();

	exit(0);
}

///////////////////////////////////////////////////////////////////////////////////////////////////