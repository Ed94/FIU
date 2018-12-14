/**************************************************************
 Purpose/Description: Create an implementation that can search for the index
 of an element in a specified sorted and rotated array.
 Author’s Panther ID: 4999406
 Certification:
 I hereby certify that this work is my own and none of it is the work of
 any other person.
 **************************************************************/ 


public class Problem_1 
{
	/* An implementation of binary search that takes into account an array that was previously sorted via increasing
	 * order and may have been rotated.
	 * 
	 * Time Complexity: O(log(n))
	 */
	public static Integer BinarySearch(Integer[] _arrayToSearch, Integer _elementToFind, Integer _left, Integer _right)
	{
		if (_left > _right)
		{
			return -1;
		}
		
		Integer middle = (_left + _right) / 2;
		
		if (_elementToFind == _arrayToSearch[middle])
		{
			return middle;
		}
		
		if (_arrayToSearch[_left] <= _arrayToSearch[middle])
		{
			if ( (_elementToFind >= _arrayToSearch[_left]) && (_elementToFind <= _arrayToSearch[middle]) )
			{
				return BinarySearch(_arrayToSearch, _elementToFind, _left, middle - 1);
			}
			
			return BinarySearch(_arrayToSearch, _elementToFind, middle + 1, _right);
		}
		
		if ( (_elementToFind >= _arrayToSearch[middle]) && (_elementToFind <= _arrayToSearch[_right]) )   
		{
			return BinarySearch(_arrayToSearch, _elementToFind, middle + 1, _right);   
		}
		
		return BinarySearch(_arrayToSearch, _elementToFind, _left, middle - 1);
	}
	
	/* Provides a clean function call to find the element. Real implementation is a call to a binary
	 * search function after where its given its parameters plus the size of the array itself to search.
	 * 
	 * Will indicate before returning a error return (-1) if no element could be find.
	 * 
	 * Time Complexity: O(log(n)) (Determined by BinarySearch)
	 */
	public static Integer FindElement(Integer _elementToFind, Integer[] _arrayToSearch)
	{
		Integer possibleValidIndex = BinarySearch(_arrayToSearch, _elementToFind, 0, _arrayToSearch.length - 1);   //Value is found before check.
		
		if (possibleValidIndex >= 0)
		{
			return possibleValidIndex;   //Return a valid find without need to warn the user.
		}
		else
		{
			System.out.println("Unable to find element. Returning -1\n");   //Warn the user that an element was not found.
			
			return -1;   //Return the error indicating value.
		}
	}
	
	//Tests implementation.
	public static void main(String[] args)
	{
		//Initial Test
		Integer[] givenArray = { 15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14 };   //Array given within the assignment.
		
		//Printing out the given array to quick check validity of "Result for Given".
		System.out.print("Given Array: [ ");
		
		for (Integer element : givenArray)
		{
			System.out.print(element.intValue() + " ");
		}
		
		System.out.println("]\n");
		//---------------------------------------------------------------------------
		
		Integer resultGiven = FindElement(5, givenArray);   //Attempt to find a result.
		
		System.out.println("Result for Given '5': " + resultGiven);   //Print result for given array.
		System.out.println("Expected result     : 8"             );   //Give expected result for comparison.
		
		System.out.println("\n");
		
		//Rigorous Test
		Integer[] rigorousArray = 
		{ 10, 11, 12, 13, 16, 17, 18, 19, 20, 21, 22, 1, 2, 3, 4, 5, 8, 9  };   //A more complex array that can test for edge cases.
		
		//Printing out the rigorous array to quick check validity of "Result for Rigorous".
		System.out.print("Given Array: [ ");
		
		for (Integer element : rigorousArray)
		{
			System.out.print(element.intValue() + " ");
		}
		
		System.out.println("]\n");
		//---------------------------------------------------------------------------
		
		Integer resultRigorous =  FindElement(5, rigorousArray);   //Attempt to find a result.
		
		System.out.println("Result for Rigorous '5': " + resultRigorous);   //Print result for given array.
		System.out.println("Expected result        : 15"               );   //Give expected result for comparison.
		
		System.out.println();
		
		Integer resultRigorous2 = FindElement(17, rigorousArray);   //Attempt to find a result.
		
		System.out.println("Result for Rigorous '17': " + resultRigorous2);   //Print result for given array.
		System.out.println("Expected result         : 5"                 );   //Give expected result for comparison.
	}
}
