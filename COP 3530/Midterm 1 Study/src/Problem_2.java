import java.util.*;

public class Problem_2
{
	public static <AnyType extends Comparable<? super AnyType>> void differenceIntersection(List<AnyType> L1, List<AnyType> L2, List<AnyType> Intersect) 
	{ 
		AnyType itemL1 = null;
		AnyType itemL2 = null;
		
		ListIterator<AnyType> iterL1 = L1.listIterator(); 
		ListIterator<AnyType> iterL2 = L2.listIterator(); 
		
	    // get first item in each list 
	    if ( iterL1.hasNext() && iterL2.hasNext() ) 
	    { 
	      itemL1 = iterL1.next(); 
	      itemL2 = iterL2.next(); 
	    } 
	    
	    // YOUR CODE GOES HERE
	    boolean HaveNext = true;
	    
	    do  
    	{
    		if (itemL1.equals(itemL2))
    		{
    			Intersect.add(itemL1);
    		}
    		
    		 if ( iterL1.hasNext() && iterL2.hasNext() ) 
    		 { 
    			 itemL1 = iterL1.next(); 
    			 itemL2 = iterL2.next(); 
    		 } 
    		 else
    		 {
    			 HaveNext = false;
    		 }
    		
    	} while (HaveNext);
	 } 
}