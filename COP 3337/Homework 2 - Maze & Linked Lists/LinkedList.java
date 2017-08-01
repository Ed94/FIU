import java.util.NoSuchElementException;

/**
   Complete the sort method in the LinkedList class below.
   Modify the insertion sort algorithm of Special Topic 14.2
   to sort the linked list.
*/
public class LinkedList
{
   private Node first;

   /**
      Constructs an empty linked list.
   */
   public LinkedList()
   { first = null; }

   /**
      Returns the first element in the linked list.
      @return the first element in the linked list
   */
   public Object getFirst()
   {
      if (first == null) { throw new NoSuchElementException(); }
      
      return first.data;
   }

   /**
      Removes the first element in the linked list.
      @return the removed element
   */
   public Object removeFirst()
   {
      if (first == null) { throw new NoSuchElementException(); }
      
      Object element = first.data;
      
      first = first.next;
      
      return element;
   }

   /**
      Adds an element to the front of the linked list.
      @param element the element to add
   */
   public void addFirst(Object element)
   {
      Node newNode = new Node();
      
      newNode.data = element;
      newNode.next = first  ;
      
      first = newNode;
   }

   /**
      Returns an iterator for iterating through this list.
      @return an iterator for iterating through this list
   */
   public ListIterator listIterator()
   { return new LinkedListIterator(); }

   /**
      Sort the data in the list, using insertion sort.
      Use the private compare method to compare nodes.
   */
   public void sort()
	{
		int count = 1;
		
		for (LinkedListIterator iter = new LinkedListIterator(); iter.hasNext();)
		{
			Object temp    = null		;
			Object current = iter.next();
			
			if (count > 1)
			{
				int down = count;
				
				for (LinkedListIterator iterBk = new LinkedListIterator(); down > 0;)
				{
					if (iterBk.hasNext())
					{
						Object other = iterBk.next();
						
						int value = compare(current, other);
						
						if (value < 0)
						{
							temp = current;
							
							iter  .set(other);
							iterBk.set(temp	);
						}
						
						current = iter.position.data;
					}
					
					down--;
				}
				
				count++;
			}
			else
				count++;
		}
	}

   /**
      Return the contents of the list.
   */
   @Override
   public String toString()
   {
      String result = "[";
      
      ListIterator iter = listIterator();
      
      if (iter.hasNext()) { result += iter.next(); }
      
      while (iter.hasNext()) { result += ", " + iter.next(); }
      
      result += "]";
      
      return result;
   }
   
   
   //Private or Protected
   /**
    * 
    * This helper method allows you to compare two objects
    * The annotation supresses warnings because of possible 
    * "unsafe" comparisons between different object types.
    * For information on making your data structures safe, 
    * see Chapter 18 on Generics.
   */
   @SuppressWarnings("unchecked")
   protected int compare(Object a, Object b)
   {
      if (! (a instanceof Comparable && b instanceof Comparable))
         throw new RuntimeException("Cannot compare non-comparable types.");
      
      return ((Comparable) a).compareTo((Comparable) b);
   }
   
   public class Node
   {
      public Object data;
      public Node 	next;
   }

   class LinkedListIterator implements ListIterator
   {
      private Node position;
      private Node previous;
      
      protected boolean isAfterNext;

      /**
         Constructs an iterator that points to the front
         of the linked list.
      */
      public LinkedListIterator()
      {
         position = null;
         previous = null;
         
         isAfterNext = false;
      }

      /**
         Moves the iterator past the next element.
         @return the traversed element
      */
      public Object next()
      {
         if (!hasNext()) { throw new NoSuchElementException(); }
         
         previous    = position; // Remember for remove
         isAfterNext = true	   ;

         if  (position == null) { position = first	      ; }
         else  				    { position = position.next; }

         return position.data;
      }

      /**
         Tests if there is an element after the iterator position.
         @return true if there is an element after the iterator position
      */
      public boolean hasNext()
      {
         if  (position == null) { return first 		   != null; }
         else 					{ return position.next != null; }
      }

      /**
         Adds an element before the iterator position
         and moves the iterator past the inserted element.
         @param element the element to add
      */
      public void add(Object element)
      {
         if (position == null)
         {
            addFirst(element);
            
            position = first;
         }
         else
         {
            Node newNode = new Node();
            
            newNode.data = element		;
            newNode.next = position.next;
            
            position.next = newNode;
            position      = newNode;
         }

         isAfterNext = false;
      }

      /**
         Removes the last traversed element. This method may
         only be called after a call to the next() method.
      */
      public void remove()
      {
         if (!isAfterNext) { throw new IllegalStateException(); }

         if  (position == first) { removeFirst()				; }
         else				     { previous.next = position.next; }
         
         position    = previous;
         isAfterNext = false   ;
      }

      /**
         Sets the last traversed element to a different value.
         @param element the element to set
      */
      public void set(Object element)
      {
         if (!isAfterNext) { throw new IllegalStateException(); }
         
         position.data = element;
      }
   }
}
