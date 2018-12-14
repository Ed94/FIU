/**************************************************************
 Purpose/Description: 
 Problem 1 
 a. Where one loop is used to find the set difference between two lists.
 Assumes that both lists are sorted, only 3 operators were used when handling the lists: next(), hasNext(), and compareTo().
 b. Sorts an integer stack using an auxiliary stack with runtime complexity of O(n^2).
 
 Author’s Panther ID: 4999406
 Certification:
 I hereby certify that this work is my own and none of it is the work of
 any other person.
 **************************************************************/ 

import java.util.ArrayList   ;
import java.util.List        ;
import java.util.ListIterator;
import java.util.Stack       ;


public class Problem_1 
{
	//A
	public static <AnyType extends Comparable<? super AnyType>> void difference(List<AnyType> L1, List<AnyType> L2, List<AnyType> Difference)
	 {
		ListIterator<AnyType> iterL1 = L1.listIterator();
		ListIterator<AnyType> iterL2 = L2.listIterator();
		
		AnyType itemL1, itemL2; 
		
		itemL1 = null;
		itemL2 = null;   //Initialized item from list one and 2 to null.
		
		if ( iterL1.hasNext() && iterL2.hasNext() )
		{
			itemL1 = iterL1.next();
			itemL2 = iterL2.next();
		}
		else
		{
			System.out.println("One of the lists was empty. No difference could be interpreted.");
			
			return;
		}
		
		int currentResult = 0;   //Stores the last result of comparing the elements stored in itemL1 and itemL2.

		do   //Single loop check.
		{
			currentResult = itemL1.compareTo(itemL2);   //Sets the currentResult to the compareTo function's result between itemL1 and itemL2.
			
			if (currentResult == 0)   //If it returned 0. It means both elements are equivalent and as such should not be added to the difference.
			{
				itemL1 = iterL1.next();   //Increments both itemL1 and itemL2 for lines 40 and 41.
				itemL2 = iterL2.next();
			}
			else if (currentResult > 0)   //Element in the first list is of larger equivalence value than the element from the second list.
			{
				itemL2 = iterL2.next();   //Increments itemL2 to the next element, as there is still possible elements in the list that may be of equal value to itemL1.
			}
			else   //Element in the first list is of lesser equivalence value to the element in itemL2.
			{
				Difference.add(itemL1);   //It is safe to add the current item from the first list as there is no chance of it being in the second list.
				
				if (iterL1.hasNext())   //Makes sure the first lists has another element.
				{
					itemL1 = iterL1.next();   //Increments itemL1 to the next element, as there is a chance that an element in the first list may be of equal value to itemL2.
				}
				else
				{
					break;   //Leaves the loop if no more elements.
				}
			}
			
		} while ( itemL1 != null || itemL2 != null && iterL1.hasNext());   //Loop fails to continue if either iterators for L1 and L2 fail to have another element.
	 }
	
	//B
	//Run-time complexity: O(n^2), as in the worst case the stack would need to go through a reverse order for every
	//element in the integer stack, to put all the elements in proper order inside of the auxiliary stack.
	public static Stack<Integer> sort(Stack<Integer> _integerStack)   
	{
		if (_integerStack.isEmpty())   //Make sure the given stack is not empty, if so return an empty stack.   O(+1)
		{
			return _integerStack;   //+1 -> O(2)
		}
		
		Stack<Integer> auxiliaryStack = new Stack<Integer>();   //Create the auxiliary stack used to aid sorting.  +1 -> O(3)
		
		Integer currentElement = _integerStack.pop();   //Pop the first element into currentElement.   +1 -> O(4)
		
		do   //Loops until integer stack has been exhausted of all elements.
		{
			if (!auxiliaryStack.isEmpty())   //Makes sure the auxiliary stack is not empty.   +1 -> subO(1)
			{
				if (auxiliaryStack.peek() > currentElement)   //+1 -> subO(2)
				{
					_integerStack.push(auxiliaryStack.pop());   //+1 -> subO(3)
					
					continue;   //Skips the rest of the loop to until auxiliary finds a an element in correct place.   +3(n-1) -> subO(3n-3)
				}
			}
			
			auxiliaryStack.push(currentElement);   //Populate auxiliary with current element as it met the criteria.  *+1 -> O(3n-3)
			
			currentElement = _integerStack.pop();   //Pop the next element from the integer stack. *+1 -> O(2*(3n-3)
			
		} while (!_integerStack.isEmpty());   //End the loop when the integer stack is empty.  *n -> O(2n*(3n-3)) 
		
		auxiliaryStack.push(currentElement);   //Put the last element into auxiliary. +1 -> O(2n*(3n-3)+1) 
		
		return auxiliaryStack;   //Stack is sorted. Returning the sorted stack.  +1 -> O(2n*(3n-3)+2) -[Inf]> O(n^2)
	}
	
	//Test Function.
	public static void main(String[] args)
	{
		//A
		//Test lists. Two Integer ArrayList objects.
		List<Integer> ListIntegersOne  = new ArrayList<>();
		List<Integer> ListIntegersTwo  = new ArrayList<>();
		List<Integer> DifferenceResult = new ArrayList<>();
	
		//Populate the first list.
		ListIntegersOne.add(1 );
		ListIntegersOne.add(2 );
		ListIntegersOne.add(4 );
		ListIntegersOne.add(6 );
		ListIntegersOne.add(9 );
		ListIntegersOne.add(10);
		ListIntegersOne.add(11);
		
		//Populate the second list.
		ListIntegersTwo.add(1 );
		ListIntegersTwo.add(3 );
		ListIntegersTwo.add(4 );
		ListIntegersTwo.add(5 );
		ListIntegersTwo.add(6 );
		ListIntegersTwo.add(7 );
		ListIntegersTwo.add(8 );
		ListIntegersTwo.add(10);
		ListIntegersTwo.add(12);
		
		difference(ListIntegersOne, ListIntegersTwo, DifferenceResult);
		
		System.out.println(DifferenceResult.toString());
		
		//B
		Stack<Integer> IntegerStack = new Stack<>();
		Stack<Integer> StackResult  = new Stack<>();
		
		IntegerStack.push(10);
		IntegerStack.push(6 );
		IntegerStack.push(2 );
		IntegerStack.push(5 );
		IntegerStack.push(1 );
		IntegerStack.push(3 );
		IntegerStack.push(7 );
		IntegerStack.push(9 );
		IntegerStack.push(4 );
		IntegerStack.push(8 );
		
		StackResult = sort(IntegerStack);
		
		System.out.println(StackResult.toString());
	}
}