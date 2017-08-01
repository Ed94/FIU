import java.util.NoSuchElementException;

public class DoubleLinked extends LinkedList
{	
	DoubleNode first;
	DoubleNode last ;  
	
	/**Constructs an empty doubly linked list.*/
	public DoubleLinked()
	{ first = null; last = first; }
	
	public DoubleLinked(Object obj)
	{ addFirst(obj); }
	
	private boolean isEmpty() 
	{ return (first == null); }
	
	public void addFirst(Object obj)
	{
		DoubleNode node = new DoubleNode(obj);
		
		if (isEmpty())
			last = node;
		else
			first.previous = node;
		
		node.next = first;
		first 	  = node ;
	}
	
	public void addLast(Object obj)
	{
		DoubleNode node = new DoubleNode(obj);
		
		if (isEmpty())
			first = node;
		else
		{
			last.next     = node;
			node.previous = last;
		}

		last = node;
	}
	
	public Object removeFirst()
	{
		if (first == null) { throw new NoSuchElementException(); }
		
		Object element = first.data;
		
		first = (DoubleNode) first.next;

		return element;
	 }
	
	public ListIterator listIterator()
	   { return new DoubleLinkIterator(); }
	
	public void sort()
	{
		int count = 1;
		
		for (DoubleLinkIterator iter = new DoubleLinkIterator(); iter.hasNext();)
		{
			Object temp    = null		;
			Object current = iter.next();
			
			if (count > 1)
			{
				int down = count;
				
				for (DoubleLinkIterator iterBk = new DoubleLinkIterator(); down > 0;)
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
	
	class DoubleNode extends Node
	{
		Node previous;
		
		public DoubleNode(Object obj)
		{ data = obj; }
		
		public void setNode(Object obj, Node first, Node previous)
		{ data = obj; this.next = first; this.previous = previous; }
	}
	
	class DoubleLinkIterator extends LinkedListIterator
	{
		DoubleNode position;
		DoubleNode previous;
		
		boolean isBefore = false;
		
		public DoubleLinkIterator()
		{
			position = null;
			previous = null;
			
			super.isAfterNext = false;
		}
		
		public Object next()
		{
			if (!hasNext()) { throw new NoSuchElementException(); }
	         
	         previous    = position			; // Remember for remove
	         isAfterNext = true	   			;

	         if  (position == null) { position = first	      ; }
	         else  				    { position = (DoubleNode) position.next; }

	         return position.data;
		}

		public boolean hasNext()
		{
			if  (position == null) { return first 		   != null; }
			else 					{ return position.next != null; }
		}

		public void add(Object obj)
	    {
			if (position == null)
			{
				addFirst(obj);
	            
	            position = first;
	        }
			else
			{
				DoubleNode newNode = new DoubleNode(obj);
	            
	            newNode.next = position.next;
	            
	            position.next = newNode;
	            position      = newNode;
	        }
			
			isAfterNext = false;
	    }
		 
		public void remove()
	    {
			if (!isAfterNext) { throw new IllegalStateException(); }
			
			if  (position == first) { removeFirst()				; }
			else				     { previous.next = position.next; }
			
			position    = previous;
			isAfterNext = false   ;
	    }
		 
		public void set(Object obj)
		{
			if (!isAfterNext) { throw new IllegalStateException(); }
			
			position.data = obj;
		}
	}
}
