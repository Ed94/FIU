/**
 * Edward R. Gonzalez   5/30/2017   COP 3337
 * 
 * Modified version of SuperList Class originally created by Francisco R. Ortega, Ph.D. on 5/18/17. Refactored and completed all unfinished functions.
 */

package prog2.ds;

import java.io.Serializable						;
import java.util.Iterator						;
import java.util.Collection						;
import java.util.ListIterator					;
import java.util.List							;
import java.util.function.Consumer				;
import java.util.function.Predicate				;
import java.util.function.UnaryOperator			;
import java.util.Comparator						;
import java.util.Arrays							;
import java.util.RandomAccess					;
import java.util.AbstractList					;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException			;
import java.util.Objects						;


/**
 * SuperList is a simpler implementation of ArrayList
 * For the purpose of Programming II, please complete the methods that have not been implemented(Except for "iterator().remove"). 
 * 
 * Created by Francisco R. Ortega, Ph.D. on 5/18/17.
 * 
 * see			 https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html
 * for javadocs: https://www.tutorialspoint.com/java/java_documentation.htm
 * and 		     http://www.oracle.com/technetwork/articles/java/index-137868.html
 */
public class SuperList<E> extends AbstractList<E> implements  Cloneable, Collection<E>, Iterable<E>, List<E>, RandomAccess, Serializable
{
    /**
     * This method is used to add two integers. This is the simplest form of a class method,
     * just to show the usage of various javadoc Tags.
     * @param numA: First  parameter to addNum method.
     * @param numB: Second parameter to addNum method.
     * @return Int: Returns sum of numA and numB.
     */
    public SuperList()
    {
        this(256);   //It looks strange with capacity. It is not part of the code nor the editor. 
    }
    public SuperList(int capacity)
    {
    	originalCapacity = this.capacity = capacity;   //Sets capacity and orginalCapacity to the passed capacity.
    	
        list = new Object[this.capacity];   //Creates a new generic list of set capacity.
        size = 					   	   0;   //Sets the size to zero since nothing was added to list yet.
    }
    public SuperList(Collection<? extends E> c)
    {
        this.originalCapacity = this.capacity = this.size = c.size();   //Sets originalCapacity, capacity, and size to the collection's passed size.
        
        list = c.toArray();   //Sets list to the array of passed collection.
    }
    
    
    //------------------------Public Functions---------------------------------
    public boolean add(E e)
    {
        if (CheckSize())
            ExpandList();
        
        this.list[size++] = e;
        
        return true;
    }
    
    public boolean addAll(Collection<? extends E> c)
    {
    	for (Iterator<? extends E> iteration = c.iterator(); iteration.hasNext();)
    	{
    		this.add(iteration.next());
    	}
    	
    	return true;
    }

    public boolean addAll(int index, Collection<? extends E> c)
    {
        for (Iterator<? extends E> iteration = c.iterator(); iteration.hasNext();)
        {
        	this.add(index++ , iteration.next());
        }
        
        return true;
    }
    
    public boolean contains(Object o)
    {
    	for (int index = 0; index < size; size++)
        {
        	if (list[index].equals(o))
        	{
        		return true;
        	}
        }
        
        return false;
    }

    public boolean containsAll(Collection<?> c)
    {
        for (Iterator<?> iteration = c.iterator(); iteration.hasNext();)
        {
        	if(!this.contains(iteration.next()))
        		return false;
        }
        
        return true;
    }
    
    public boolean ensureCapacity(int capacity)
    {
        if (capacity < this.capacity)
            return false;

        this.originalCapacity = this.capacity = capacity;
        
        return true;
    }
    
    public boolean isEmpty()
    {
    	if   (size == 0) { return true ; } 
        else 		     { return false; }
    }
    
    public boolean remove(Object o)
    {
    	for (int index = 0; index < size; index++)
        {
        	if (list[index].equals(o))
        	{
        		list[index] = null;
        	}
        }
        
        int      tempIndex = 				    0;
    	Object[] tempList  = new Object[capacity]; 
    	
    	for (int index = 0; index < size; index++)
    	{
    		if (list[index] != null)
    		{
    			tempList[tempIndex++] = list[index];
    		}
    	}
    	
    	list = tempList ;
    	size = tempIndex;
        
        return true;
    }
    
    public boolean removeAll(Collection<?> c)
    {
    	for (int index = 0; index < size; index++)
	    {
			for (Iterator<?> iterator = c.iterator(); iterator.hasNext();)
			{
				if (this.get(index).equals(iterator.next()))
				{
					this.remove(index);
				}
			}
	    }
    	
		return true;
    }
    
    public boolean removeIf(Predicate<? super E> filter)
    {
        int totalSize  = this.size;
        int totalCount = 		 0;
        
        for (int i = 0; i < totalSize; i++)
        {
            if (filter.test((E) list[i]))
            {
                remove(i);
                
                totalCount++;
            }
        }
        
        return (totalCount != 0 && totalSize == totalCount);
    }
    
    public boolean retainAll(Collection<?> c)
    {
    	int position = 0;
    	
    	for (Iterator<?> iteration = c.iterator(); iteration.hasNext();)
        {
        	list[position++] = iteration.next();
        }
    	
    	if (position < size)
    	{
    		this.removeRange(position, size);
    	}
    	
    	size = position + 1;
    	
    	return true;
    }
    
    public E get(int index)
    {
        checkBounds(index);
        
        return (E)list[index];
    }
    
    public E remove(int index)
    {   //We still need to cast
        E val = (E) list[index];
        
        ArrayCopy(list, index + 1, list,index, list.length); 
        
        size--;
        
        return val;
    }
    
    public E removeLast()
    { return remove(list.length -1 ); }
    
    public E set(int index, E element)
    {
    	if (index > size)
    	{
    		System.out.println("Could not set element to specified slot. It is out of bounds of current list size.");
    	}
    	else
    	{
    		list[index] = element;
    	}
    	
        return (E) list[index];
    }

    public int indexOf(Object o)
    {
    	for (int index = 0; index < size; size++)
        {
        	if (list[index].equals(o))
        	{
        		return index;
        	}
        }
    	
    	System.out.println("Object not found.");
        
    	return -1;
    }
    
    public int getCapacity()
    { return capacity; }

    public int lastIndexOf(Object o)
    {
    	for (int index = (size - 1); index > 0; index--)
        {
        	if (list[index].equals(o))
        	{
        		return index;
        	}
        }	
    	
    	System.out.println("Object not found.");
    	
        return -1;
    }
    
    public int size()
    { return size; }
    
    @Override
    public Iterator<E> iterator()
    { return new Itr(); }
    
    public List<E> subList(int fromIndex, int toIndex)
    {
    	List<E> subList = new SuperList<E>();
    	
        for (int index = fromIndex; index < toIndex; index++)
        {
        	subList.add((E) list[index]);
        }
        
        return subList;
    }

    public ListIterator<E> listIterator(int index)
    {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: "+ index);
        
        return new ListItr(index);
    }

    public ListIterator<E> listIterator()
    { return new ListItr(0); }

    @Override
    public Object   clone()
    {
        SuperList<E> s = new SuperList<E>(this.size());
        
        s.list = this.toArray();
        s.size = this.size	   ;
        
        return s;
    }

    public Object[] toArray()
    { return list.clone(); }
    
    public void add(int index, E element)
    {
    	int tmp = size + 1;
    	
        Object[] tempList = new Object[tmp];
        
        for (int position = 0; position < tmp; position++)
        {
        	if (position == index)
        	{
        		tempList[position] = element;
        	}
        	else
        	{
        		tempList[position] = list[position];
        	}
        }
        
        list = tempList;
    }
    
    public void clear()
    {
        this.list 	  = new Object[this.originalCapacity];
        this.size 	  = 								0;
        this.capacity = originalCapacity				 ;
    }
    
    public void forEach(Consumer<? super E> action)
    {
        for (Object o: this.list)
        	action.accept((E) o);
    }
    
    protected void removeRange(int fromIndex, int toIndex)
    {
    	for (int position = toIndex; position >= fromIndex; position--)
    	{
    		this.list[position] = null;
    	}
    	
    	int      tempIndex = 				    0;
    	Object[] tempList  = new Object[capacity]; 
    	
    	for (int index = 0; index < size; index++)
    	{
    		if (list[index] != null)
    		{
    			tempList[tempIndex++] = list[index];
    		}
    	}
    	
    	list = tempList ;
    	size = tempIndex;
    }
    
    public void replaceAll(UnaryOperator<E> operator)
    {
        for (Object o: this.list)
            operator.apply((E) o);
    }
    
    public void sort(Comparator<? super E> c)
    {
        final int expectedModCount = modCount;
        
        Arrays.sort((E[]) list, 0, size, c);
        
        if (modCount != expectedModCount)
        	throw new ConcurrentModificationException();
        
        modCount++;
    }
    
    public void trimToSize()
    {
    	Object[] tempList = new Object[size];
    	
    	for (int index = 0; index < size; index++)
    	{
    		tempList[index] = list[index];
    	}
    	
    	list     = tempList;
    	capacity = size	   ;
    }
    
    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof SuperList<?>))
            return false;

        if (other == this)
            return true;

        //implements equals
        return false; //change this once implemented.
    }
    
    @Override
    public String toString()
    {
        String str = "[ ";
        for (int i =0; i < size; i++)
        {
            str += list[i].toString() + " ";
        }
        str += " ]";

        return str;
    }
    
    
    //------------------------Private Functions---------------------------------
    private boolean CheckSize()
    { return (this.size >= this.capacity); }
    
    private void checkBound(int index)
    {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size " + index );
    }
    
    private void checkBounds(int index)   //Found this intermingled with the public functions...
    {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size " + index );
    }
    
    private void ExpandList()   //Java style wants EnlargeList as opposed to enlargeList
    {
        Object[] temp = list.clone();
        
        this.capacity = INCREMENT_FACTOR * this.capacity;
        
        list = new Object[this.capacity];
        //System.arraycopy(temp,0,list, 0, temp.length);
        ArrayCopy(temp,0,list,0,temp.length);
    }

    //In your assignment, you must determine the difference between this implementation
    //and the native (created in C++) for System.arraycopy(...)
    private static void ArrayCopy(Object[] src, int srcidx, Object[] dest, int dstidx, int srclen)   //Need to do assignment response...
    {
        for (int sidx=srcidx,  didx = dstidx; sidx < srclen; sidx++,didx++)
        	dest[didx] = src[sidx];
    }

    
    //---------------Region for advanced code (Some from Java source code)--------------
    /**
     * Reallocates the array being used within toArray when the iterator
     * returned more elements than expected, and finishes filling it from
     * the iterator.
     *
     * @param "r" the array, replete with previously stored elements.
     * @param It the in-progress iterator over this collection.
     * @return Array containing the elements in the given array, plus any
     *         further elements returned by the iterator, trimmed to size.
     */
    @SuppressWarnings("unchecked")
    private static <T> T[] finishToArray(T[] r, Iterator<?> it) 
    {
        int i = r.length;
        
        while (it.hasNext()) 
        {
            int cap = r.length;
            
            if (i == cap) 
            {
                int newCap = cap + (cap >> 1) + 1;
                
                //Overflow-conscious code.
                if (newCap - MAX_ARRAY_SIZE > 0)
                    newCap = hugeCapacity(cap + 1);
                
                r = Arrays.copyOf(r, newCap);
            }
            
            r[i++] = (T)it.next();
        }
        
        //Trim if over allocated.
        return (i == r.length) ? r : Arrays.copyOf(r, i);
    }

    public int hashCode() //From java source code; Has not been tested.
    {
        int hashCode = 1;
        
        for (E e : this)
            hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());

        return hashCode;
    }
    
    private static int hugeCapacity(int minCapacity) 
    {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError("Required array size too large");
        
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }
    
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) 
    {   //From Java source code.
        //Estimate size of array; be prepared to see more or fewer elements.
        int size = size();
        
        T[] r = a.length >= size ? a 
        		: (T[]) java.lang.reflect.
        					      Array.newInstance(a.getClass().getComponentType(), size);
        
        Iterator<E> it = iterator();

        for (int i = 0; i < r.length; i++) 
        {
            if (! it.hasNext()) 
            {   //Fewer elements than expected
                if (a == r) 
                {
                    r[i] = null;   //Null-terminate
                } 
                else if (a.length < i) 
                {
                    return Arrays.copyOf(r, i);
                } 
                else 
                {
                    System.arraycopy(r, 0, a, 0, i);
                    
                    if (a.length > i) 
                        a[i] = null;
                }
                return a;
            }
            r[i] = (T)it.next();
        }
        //More elements than expected.
        return it.hasNext() ? finishToArray(r, it) : r;
    }
    
    
    /**
     * An optimized version of AbstractList.Itr
     */
    private class Itr implements Iterator<E> 
    {
        int cursor					   ;   //Index of next element to return.
        int lastRet 		 = 		 -1;   //Index of last element returned. Its -1 if never returned.
        int expectedModCount = modCount;

        public boolean hasNext() 
        { return cursor != size; }
        
        final void checkForComodification() 
        {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }

        @Override
        @SuppressWarnings("unchecked")
        public void forEachRemaining(Consumer<? super E> consumer) 
        {
            Objects.requireNonNull(consumer);
            
            final int size = SuperList.this.size;
            
            int i = cursor;
            
            if (i >= size) 
            	return;
            
            final Object[] elementData = SuperList.this.list;
            
            if (i >= elementData.length)
            {
                throw new ConcurrentModificationException();
            }
            
            while (i != size && modCount == expectedModCount) 
            {
                consumer.accept((E) elementData[i++]);
            }
            
            //Update once at end of iteration to reduce heap write traffic
            cursor  = i;
            
            lastRet = i - 1;
            
            checkForComodification();
        }
        
        @SuppressWarnings("unchecked")
        public E next() 
        {
            checkForComodification();
            
            int i = cursor;
            
            if (i >= size)
                throw new NoSuchElementException();
            
            Object[] elementData = SuperList.this.list;
            
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            
            cursor = i + 1;
            
            return (E) elementData[lastRet = i];
        }

        public void remove() 
        {
            if (lastRet < 0)
                throw new IllegalStateException();
            
            checkForComodification();

            try 
            {
                SuperList.this.remove(lastRet);
                
                cursor  = lastRet;
                lastRet = 	   -1;
                
                expectedModCount = modCount;
            } 
            catch (IndexOutOfBoundsException ex) 
            {
                throw new ConcurrentModificationException();
            }
        }
    }

    /**
     * An optimized version of AbstractList.ListItr
     */
    private class ListItr extends Itr implements ListIterator<E> 
    {
        ListItr(int index)
        {
            super();
            
            cursor = index;
        }
        
        
        public boolean hasPrevious() 
        { return cursor != 0; }

        public int nextIndex() 
        { return cursor; }

        public int previousIndex()
        { return cursor - 1; }

        @SuppressWarnings("unchecked")
        public E previous() 
        {
            checkForComodification();
            
            int i = cursor - 1;
            
            if (i < 0)
                throw new NoSuchElementException();
            
            Object[] elementData = SuperList.this.list;
            
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            
            cursor = i;
            
            return (E) elementData[lastRet = i];
        }

        public void add(E e) 
        {
            checkForComodification();

            try 
            {
                int i = cursor;
                
                SuperList.this.add(i, e);
                
                cursor  = i + 1;
                lastRet =    -1;
                
                expectedModCount = modCount;
                
            } 
            catch (IndexOutOfBoundsException ex) 
            {
                throw new ConcurrentModificationException();
            }
        }
        
        public void set(E e) 
        {
            if (lastRet < 0)
                throw new IllegalStateException();
            
            checkForComodification();

            try 
            {
                SuperList.this.set(lastRet, e);
            } 
            catch (IndexOutOfBoundsException ex) 
            {
                throw new ConcurrentModificationException();
            }
        }
    }
    //endregion

    //------------------------------------Instance variables--------------------------------------------
    //Because of limitations with Generics in Java, the recommended option is to use an array of Object.
    //Remember that in Java, an Object is the parent of all objects.
    private Object[] list;
    
    private int size			;
    private int capacity		;
    private int originalCapacity;

    // constants
    private static final short INCREMENT_FACTOR = 					  2;
    private static final int   MAX_ARRAY_SIZE   = Integer.MAX_VALUE - 8;
}