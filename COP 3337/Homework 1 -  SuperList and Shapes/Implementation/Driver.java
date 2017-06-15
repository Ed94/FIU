import prog2.ds.SuperList ;
import shape.Shape		  ;
import shape.Shapes		  ;
import shape.AbstractShape;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List	  ;


/**
 * Created by francisco on 5/19/17.
 */
public class Driver
{
	private static void initialTest(SuperList<String> mylist)   //Originally with driver1.
	{
		System.out.println("The size is = " + mylist.size());

        for (int i =0; i < 1024; i++)
        {
            String str = "Num: "+ i+ " ";
            
            mylist.add(str);
        }																
        
        System.out.println("The size is = "+ mylist.size	  ()+ "\n");
        System.out.println("Output = "	   + mylist.toString(16)+ "\n");
        
        System.out.println("Getting index 100 [Contains =>>>] :"+ mylist.get(100));
        
        String removeItem100  = mylist.remove    (100);					
        
        System.out.println("Getting index 100 [Contains =>>>] :"+ mylist.get(100));
        
        String removeItemLast = mylist.removeLast	();
        
        System.out.println();

        System.out.println("Output = "+ mylist.toString(16)+ "\n");
        																
        System.out.println("\n");
        
        mylist.clear();													
        
        System.out.println("There is nothing now.\n");
        
        for (String s : mylist)
            System.out.println(s);   //Nothing should print out.		
        
        System.out.println("The size is = "+ mylist.size());

        mylist.add("hello");
        mylist.add("Next" );

        for (String s : mylist)
            System.out.println(s);   //Only hello should show.
        
        System.out.println("The size is = "+ mylist.size()+ "\n");
        
        System.out.println("\n*****Compelted Initial Test*****\n");
	}
	
	private static void extendedTest()   //Originally with driver2.
	{
		SuperList<String> mylist = new SuperList<String>();

        System.out.println("The size is = "     + mylist.size       ());
        System.out.println("The capacity is = " + mylist.getCapacity());

        for (int i =0;i< 1024; i++)
        {
            String str = "Num: " + i;
            
            mylist.add(str);
        }

        System.out.println("The size is = "              + mylist.size				());
        System.out.println("Ensuring the capacity is = " + mylist.ensureCapacity(1024));
        
        mylist.trimToSize();
        
        Object[] ob = mylist.toArray();

        System.out.println("Output = " + mylist.toString(16));

        System.out.println("Getting index 100 [Contains =>>>] :" + mylist.get        (100));
        System.out.println("Index for Hello:"                    + mylist.lastIndexOf(100));

        String removeItem100 = mylist.remove(100);

        System.out.println("Getting index 100 [Contains =>>>] :" + mylist.get		(100));
        System.out.println("List contains '100':" 				 + mylist.contains("100"));

        String removeItemLast = mylist.removeLast();

        System.out.println("Getting Capacity of the List = " + mylist.getCapacity());

        mylist.add(45, "45");
        mylist.set(45, "45");

        System.out.println("Getting index 45 [Contains =>>>] :" + mylist.get(45));

        System.out.println("Hash Code" + mylist.hashCode());
        
        System.out.println("\nMy List: ");
        
        for (String s : mylist)
        	System.out.print(s);
        
        System.out.println();

        SuperList arraylist = (SuperList) mylist.clone();
        
        System.out.println("\nArrayList: ");
        
        for (int i = 0; i < arraylist.size(); i++) 
        	System.out.print(arraylist.get(i));
        
        System.out.println("\n");

        System.out.println("Checking contents in mylist and arraylist :- "+ arraylist.containsAll(mylist));

        arraylist.clear();
        mylist   .clear();
        
        Collection collection = new SuperList();
        
        collection.add("One"  );
        collection.add("Two"  );
        collection.add("Three");
        
        mylist.addAll   (collection);
        mylist.retainAll(collection);
        //Had to move remove all for the sublist...

        Comparator comp = Collections.reverseOrder();
        
        mylist.sort(comp);
        
        System.out.println(mylist.toString(16));

        List<String> l = mylist.subList(1, 2); //Had to change to 1, 2 since the range was out of bounds of mylist.
        
        mylist.removeAll(collection);
        
        System.out.println("Sublist is : " + l.toString());

        System.out.println("There is nothing now");
        
        for (String s : mylist)
        	System.out.print(s);

        System.out.println();
            
        System.out.println("Is the List Empty? :" + mylist.isEmpty());
        System.out.println("The size is = "       + mylist.size   ());

        mylist.add("hello");

        for (String s : mylist)
            System.out.print(s);
        
        System.out.println();

        System.out.println("The size is = " + mylist.size());
        
        System.out.println("\n*****Complted Extended Test*****\n");
	}
	
	private static void generalTest(SuperList<String> mylist, SuperList<String> secondList)
	{
        secondList.add("Waffles" );
        secondList.add("Pancakes");
        secondList.add("Hotrod"  );

        secondList.set(0, "Manual set to index 0 from hello in second list.");
        
        secondList.add(1 , "=========="   );
        secondList.add(50, "/ / / / / /"  );
        secondList.add(3 , "/// / / / / /");

        secondList.addAll(mylist);

        mylist.add(1, "-------");
        
        secondList.addAll(0, mylist);
        secondList.addAll(3, mylist);
        
        System.out.println("Contains Waffles : "+ secondList.contains      ("Waffles"));
        System.out.println("Contains  mylist : "+ secondList.containsAll   (mylist   ));
        System.out.println("Running ensureCap: "+ secondList.ensureCapacity(16	 	 ));
        System.out.println("Empty?           : "+ secondList.isEmpty         
        		());
        
        secondList.addAll   (mylist);
        secondList.retainAll(mylist);
        secondList.removeAll(mylist);
        
        secondList.add("Wohoo!");
        secondList.add("Wallet");
        secondList.add("Wohoo!");
        
        System.out.println("Using get: "+ secondList.get(0));
        
        System.out.println("First Index of Wohoo!   : "+ secondList.indexOf    ("Wohoo!"));
        System.out.println("Last Index of Wohoo!    : "+ secondList.lastIndexOf("Wohoo!"));
        System.out.println("Capacity of SecondList: : "+ secondList.getCapacity    	   ());
        
        secondList.removeLast();

        mylist = (SuperList<String>) secondList.subList(1, 2);
        
        mylist.addAll    (secondList);
        mylist.trimToSize		   ();
        
        System.out.println("\nMyList:"		);
        System.out.println(mylist.toString());
        
        System.out.println("Capacity of MyList: : "+ mylist.getCapacity    	   ());
        
        System.out.println("\nSecondList:"      );
        System.out.println(secondList.toString());
        System.out.println					   ();
        
        System.out.println("\n*****Complted General Test*****\n");
	}
	
	private static void otherFeatures()
	{
		System.out.println("\nTesting other features ==="	  );
        
        List<AbstractShape> list2 = new SuperList();
        
        Shapes shapes = new Shapes(list2);
        
        shapes.test();
	}
	
    public static void main(String[] args)
    {
    	final long START_TIME = System.nanoTime();
    	
        SuperList<String> mylist     = new SuperList<String>      ();				
        
        initialTest(mylist);
        
        SuperList<String> secondList = new SuperList<String>(mylist);
        
        generalTest  (mylist, secondList);
        extendedTest 				   ();
        otherFeatures				   ();
        
        final long DURATION = System.nanoTime() - START_TIME;
        
        final double SECONDS = DURATION / 1000000000.0;
        
        System.out.println("\nTime completed: "+ DURATION+ " NanoSeconds,   "+ SECONDS+ " Seconds");
    }
}