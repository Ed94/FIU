import prog2.ds.SuperList ;
import shape.Shape		  ;
import shape.Shapes		  ;
import shape.AbstractShape;
import java.util.List	  ;
/**
 * Created by francisco on 5/19/17.
 */
public class Driver
{
	private static void initialTest(SuperList<String> mylist)
	{
		System.out.println("The size is = " + mylist.size());

        for (int i =0; i < 1024; i++)
        {
            String str = "Num: "+ i+ " ";
            
            mylist.add(str);
        }																
        
        System.out.println("The size is = "+ mylist.size	()+ "\n");
        System.out.println("Output = "	   + mylist.toString()+ "\n");
        
        System.out.println("Getting index 100 [Contains =>>>] :"+ mylist.get(100));
        
        String removeItem100  = mylist.remove    (100);					
        
        System.out.println("Getting index 100 [Contains =>>>] :"+ mylist.get(100));
        
        String removeItemLast = mylist.removeLast	();
        
        System.out.println();

        System.out.println("Output = "+ mylist.toString()+ "\n");
        																
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
        System.out.println("Empty?           : "+ secondList.isEmpty                ());
        
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
        SuperList<String> mylist     = new SuperList<String>      ();				
        
        initialTest(mylist);
        
        SuperList<String> secondList = new SuperList<String>(mylist);
        
        generalTest  (mylist, secondList);
        otherFeatures				   ();
    }
}