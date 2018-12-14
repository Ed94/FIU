/**************************************************************
 Purpose/Description: Problem 2 of assignment 4
 Author’s Panther ID: 4999407
 Certification:
 I hereby certify that this work is my own and none of it is the work of
 any other person.
 **************************************************************/ 


import java.util.ArrayList;


public class Problem2 
{
	//Part A: Completes a sort of the given binary array. 
	//Note: Destructive. Will not retain the given array with its original values.
	public static void BinaryArraySort(int[] _binaryArrayToSort)
	{
		for (int index = 0, validSwapIndex = 0; index < _binaryArrayToSort.length; index++)
		{
			if (_binaryArrayToSort[index] < 1)
			{
				int binaryHolder = _binaryArrayToSort[validSwapIndex];   //Holds the value of the element at the validSwapIndex.
				
				_binaryArrayToSort[validSwapIndex] = _binaryArrayToSort[index]; //Sets the validSwapIndex element to the one at the current index.
				
				_binaryArrayToSort[index] = binaryHolder;   //Sets the element at the index to what was previously in the validSwapIndex.
				
				validSwapIndex++;   //Increment the validSwapIndex to the next one over.
			}
		}
	}
	
	//Part B: Radix sort for a set of keys. Assumes a base 10 key system.
	//Returns a sorted key array.
	public static void KeyRadixSort(int[] _keyArrayToSort)
	{
		ArrayList<ArrayList<Integer>> buckets = new ArrayList<ArrayList<Integer>>();
		
		for (int index = 0; index < 10; index++)
		{
			buckets.add(new ArrayList<Integer>());
		}
		
		int exponent   =  1                    ;   //+1
		int largestKey = -1                    ;   //+1
		int length     = _keyArrayToSort.length;   //+1
		
		for (int key : _keyArrayToSort)   //Figures out the largest key.  //+n
		{
			if (key > largestKey)   
			{
				largestKey = key;   //+1
			}
		}

		int digit = -1;
		
		while ( (largestKey / exponent) > 0)   //While there are digit places in the largest key.
		{
			for (int index = 0; index < length; index++)   //+n
			{
				int value = _keyArrayToSort[index];
				
				digit = (value / exponent) % 10;
				
				if ( (digit % 2) != 0)   //If the digit is odd. make the digit one less in the key.
				{
					if (value / 10 > 0)
					{
						value = value - 10 * exponent;
					}
					else
					{
						value--;
					}
					
					_keyArrayToSort[index] = value;
				}
		
				buckets.get(digit).add(_keyArrayToSort[index]);   //Add the key to the bucket of its derived digit value.
			}
			
			int index = 0;
			
			for (ArrayList<Integer> bucket : buckets)
			{
				if (!bucket.isEmpty())
				{
					for (Integer key : bucket)
					{
						_keyArrayToSort[index++] = key;
					}
				}
			}
			
			exponent *= 10;   //+1 Increment the digit to check by the next place.
			
			for (ArrayList<Integer> bucket : buckets)   //Clears the buckets before the next sort. //+10
			{
				bucket.clear();
			}
		}
	}
	
	public static void main(String[] args)
	{
		//Part A:
		
		int[] binaryArray = { 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1};   //Given test array for the assignment.
		
		System.out.print("Unsorted Binary Array: { ");
		
		for (int binaryValue : binaryArray)
		{
			System.out.print(binaryValue + " ");
		}
		
		System.out.println("}");
		
		BinaryArraySort(binaryArray);   //At this point the binaryArray should be destructively sorted.
		
		System.out.print("Sorted   Binary Array: { ");
		
		for (int binaryValue : binaryArray)
		{
			System.out.print(binaryValue + " ");
		}
		
		System.out.println("}");
		
		//Part B:
		
		int[] keyArray = { 13, 4680, 24062, 51, 86, 642, 51, 426, 888 };
		
		System.out.print("Unsorted Key Array: { ");
		
		for (int key : keyArray)
		{
			System.out.print(key + " ");
		}
		
		System.out.println("}");
		
		KeyRadixSort(keyArray);
		
		System.out.print("Sorted   Key Array: { ");
		
		for (int key : keyArray)
		{
			System.out.print(key + " ");
		}
		
		System.out.println("}");
	}
}