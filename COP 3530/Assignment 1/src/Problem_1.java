/**************************************************************
 Purpose/Description: Get the stability indexes of an array in N time.
 Author’s Panther ID: 4999406
 Certification:
 I hereby certify that this work is my own and none of it is the work of
 any other person.
 **************************************************************/ 

import java.util.ArrayList;
import java.util.Random; 

public class Problem_1 
{
	public static Integer[] RandomIntArray(int _arraySize)
	{
		Integer[] randomArray = new Integer[_arraySize];
		
		Random random = new Random();
		
		for (int index = 0; index < randomArray.length; index++)
		{
			randomArray[index] = random.nextInt(100);
		}
		
		return randomArray;
	}
	
	//2n^2 complexity.
	public static Integer[] N2_StabilitsFinder(Integer[] _array)
	{
		int leftSum, rightSum;
		
		ArrayList<Integer> results = new ArrayList<>();
		
		for (int index = 0; index < _array.length; index++)
		{
			leftSum = 0; rightSum = 0;
			
			for (int leftIndex = 0; leftIndex < index; leftIndex++)
			{
				leftSum += _array[leftIndex];
			}
			
			for (int rightIndex = index + 1; rightIndex < _array.length; rightIndex++)
			{
				rightSum += _array[rightIndex];
			}
			
			if (leftSum == rightSum)
			{
				results.add( (Integer)index );
			}
		}
		
		Integer[] ArrayResults = new Integer[results.size()];
		
		results.toArray(ArrayResults);
		
		return ArrayResults;
	}
	
	//n complexity
	public static Integer[] N_StabilityFinder(Integer[] _array)
	{
		Integer leftSum = 0, rightSum = 0, totalSum = 0;
		
		ArrayList<Integer> results = new ArrayList<>();
		
		//Used to determine the sum of the right side.
		for (int index = 0; index < _array.length; index++)
		{
			totalSum += _array[index];
		}
		
		rightSum = totalSum - _array[0];
		
		if (leftSum == rightSum)
		{
			results.add(0);
		}
		
		for (int index = 1; index < _array.length; index++)
		{
			leftSum  += _array[index-1];
			rightSum -= _array[index  ];
			
			if (leftSum == rightSum)
			{
				results.add(index);
			}
		}
	
		Integer[] ArrayResults = new Integer[results.size()];
		
		results.toArray(ArrayResults);
		
		return ArrayResults;
	}
	
	public static void main(String[] args) 
	{
		Integer[] exampleArray = new Integer[] { 0 , -3, 5, -4, -2, 3, 1, 0 };   //Given in the pdf for assignment 1.
		
		Integer[] exampleResult_N2 = N2_StabilitsFinder(exampleArray);
		Integer[] exampleResult_N  =  N_StabilityFinder(exampleArray);
		
		System.out.println("Example Array:");
		
		for (int index = 0; index < exampleArray.length; index++)
		{
			System.out.print(exampleArray[index]+ " ");
		}
		
		System.out.println("");
		
		System.out.println("Example Result:");
		
		for (int index = 0; index < exampleResult_N2.length; index++)
		{
			System.out.print(exampleResult_N2[index]+ " ");
		}
		
		System.out.println("");
		
		for (int index = 0; index < exampleResult_N.length; index++)
		{
			System.out.print(exampleResult_N[index]+ " ");
		}
	}	
}
