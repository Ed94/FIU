import java.util.Random;

/**
   This program demonstrates the linked list insertion sort algorithm.
*/
public class InsertionSortTester
{
   public static void main(String[] args)
   {
      LinkedList list = new LinkedList();
      
      for (int i = 1; i <= 10; i++)
         list.addFirst(100 - (i - 5) * (i - 5));
      
      System.out.println("Initial list: "+ list.toString()					      );
      System.out.println("Expected    : [75, 84, 91, 96, 99, 100, 99, 96, 91, 84]");
      
      list.sort();
      
      System.out.println("After sorting: "+ list.toString()					  	   );
      System.out.println("Expected     : [75, 84, 84, 91, 91, 96, 96, 99, 99, 100]");

      list = new LinkedList();
      
      list.addFirst("frog"    );
      list.addFirst("zebra"   );
      list.addFirst("aardvark");
      list.addFirst("lion"    );
      
      System.out.println("Initial list: "+ list.toString()		  	  );
      System.out.println("Expected    : [lion, aardvark, zebra, frog]");
      
      list.sort();
      
      System.out.println("After sorting: "+ list.toString()	  		   );
      System.out.println("Expected	   : [aardvark, frog, lion, zebra]");
      
      System.out.println("\n\n");
      
      DoubleLinked list2 = new DoubleLinked();
      
      for (int i = 1; i <= 10; i++)
         list2.addFirst(100 - (i - 5) * (i - 5));
      
      System.out.println("Initial list: "+ list2.toString()					  	  );
      System.out.println("Expected    : [75, 84, 91, 96, 99, 100, 99, 96, 91, 84]");
      
      list2.sort();
      
      System.out.println("After sorting: "+ list2.toString()					   );
      System.out.println("Expected     : [75, 84, 84, 91, 91, 96, 96, 99, 99, 100]");

      list2 = new DoubleLinked();
      
      list2.addFirst("frog"    );
      list2.addFirst("zebra"   );
      list2.addFirst("aardvark");
      list2.addFirst("lion"    );
      
      System.out.println("Initial list: "+ list2.toString()			  );
      System.out.println("Expected    : [lion, aardvark, zebra, frog]");
      
      list2.sort();
      
      System.out.println("After sorting: "+ list2.toString()	  	    );
      System.out.println("Expected     : [aardvark, frog, lion, zebra]");
   }
}
