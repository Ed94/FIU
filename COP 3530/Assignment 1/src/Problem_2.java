/**************************************************************
 Purpose/Description: See if an array has a integer value in sub-linear time.
 Author’s Panther ID: 4999406
 Certification:
 I hereby certify that this work is my own and none of it is the work of
 any other person.
 **************************************************************/ 

public class Problem_2 
{
	//Find the peak value between the ascending and descending values of the array given.
	public static int bitonicSearch(Integer[] _array, int left, int right)
	{
		int bitonic = (left + right) / 2;
		
	    if(_array[bitonic] > _array[bitonic-1] && _array[bitonic] > _array[bitonic + 1])
	    {
	        return bitonic; //Will be the peak value's index
	    }
	    else if(_array[bitonic] > _array[bitonic - 1] && _array[bitonic] < _array[bitonic + 1])
		{
	    	return bitonicSearch(_array, bitonic, right);   //Need to go to the right.
		}
	    else
 		{
 		    return bitonicSearch(_array, left, bitonic);   //Need to go to the left.
 		}
	}
	
	//Search an array organized by increasing values.
	public static boolean binearySearchIncreasing(Integer[] _array, int left, int right,  Integer _intToFind)
	{
		while (left <= right)   //Make sure left or right never goes out of bounds.
		{
			int middle = left + (right - left) / 2;   //Recalculate central index;
			
			if (_array[middle] == _intToFind)
			{
				return true;
			}
			else if (_array[middle] < _intToFind)
			{
				left = middle + 1;
			}
			else 
			{
				right = middle - 1;
			}
		}
		
		return false;
	}
	
	
	//Search for a value in an array with decreasing values.
	public static boolean binarySearchDecreasing(Integer[] _array, int left, int right,  Integer _intToFind)
	{	
		while (left <= right)   //Make sure left or right never goes out of bounds.
		{
			int middle = left + (right - left) / 2;   //Recalculate central index;
			
			if (_array[middle] == _intToFind)
			{
				return true;
			}
			else if (_array[middle] > _intToFind)
			{
				left = middle + 1;
			}
			else
			{
				right = middle -1;
			}
		}
		
		return false;
	}
	
	//See if an integer exist in a array organized bitonomically.
	public static boolean sublinearIntFinder(Integer[] _array, Integer _intToFind)
	{
		int splitRef = bitonicSearch(_array, 0, _array.length-1);
		
		System.out.println("Bitonic: "+ splitRef);

		boolean foundLeft  = binearySearchIncreasing(_array, 0, splitRef, _intToFind);
		
		if (foundLeft) { return true; }
		
		boolean foundRight = binarySearchDecreasing(_array, splitRef + 1, _array.length, _intToFind);
		
	    if (foundRight) { return true; }

		return false;
	}
	
	public static void main(String[] args) 
	{
		Integer[] givenArray = new Integer[] { 1, 3, 4, 5, 7, 14, 11, 7, -4, -8 };
		
		System.out.println(sublinearIntFinder(givenArray, 3));
	}	
}
