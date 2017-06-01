package prog2.ds;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Collection;
import java.util.ListIterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.Comparator;
import java.util.Arrays;
import java.util.RandomAccess;
import java.util.AbstractList;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;
/**
 * SuperList is a simpler implementation of ArrayList
 * For the purpose of Programming II
 * Please complete the methods that have not been implemented.
 * except for iterator().remove
 * Created by Francisco R. Ortega, Ph.D. on 5/18/17.
 * see https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html
 * for javadocs:    https://www.tutorialspoint.com/java/java_documentation.htm
 * and http://www.oracle.com/technetwork/articles/java/index-137868.html
 */

public class SuperList<E> extends AbstractList<E> implements  List<E>, Iterable<E>, Cloneable, RandomAccess, Serializable
{
    /**
     * This method is used to add two integers. This is
     * a the simplest form of a class method, just to
     * show the usage of various javadoc Tags.
     * @param numA This is the first paramter to addNum method
     * @param numB  This is the second parameter to addNum method
     * @return int This returns sum of numA and numB.
     */
    public SuperList()
    {
        //it looks strange with capacity
        //it is not part of the code
        //it is the editor
        this(256);
    }
    public SuperList(int capacity)
    {
        this.originalCapacity = this.capacity = capacity;
        this.size = 0;
        list = new Object[this.capacity];
    }


    public SuperList(Collection<? extends E> c)
    {
        this.originalCapacity = this.capacity = this.size = c.size();
        list = c.toArray();
    }

    public void trimToSize()
    {
        throw new UnsupportedOperationException();
    }

    public boolean isEmpty()
    {
        throw new UnsupportedOperationException();
    }

    public boolean contains(Object o)
    {
        throw new UnsupportedOperationException();
    }

    public boolean containsAll(Collection<?> c)
    {
        throw new UnsupportedOperationException();
    }

    public int indexOf(Object o)
    {
        throw new UnsupportedOperationException();
    }

    public int lastIndexOf(Object o)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object clone()
    {
        SuperList<E> s = new SuperList<E>(this.size());
        s.list = this.toArray();
        s.size = this.size;
        return s;
    }

    public Object[] toArray()
    {
        return list.clone();
    }


    public boolean add(E e)
    {
        if (CheckSize())
            ExpandList();
        this.list[size++] = e;
        return true;
    }

    public void add(int index,E element)
    {
        throw new UnsupportedOperationException();
    }

    public E get(int index)
    {
        checkBounds(index);
        return (E)list[index];
    }

    public E set(int index, E element)
    {
        throw new UnsupportedOperationException();
    }




    public int size()
    {
        return size;
    }

    public int getCapacity()
    {
        return capacity;
    }

    public boolean ensureCapacity(int capacity)
    {
        if (capacity < this.capacity)
            return false;

        this.originalCapacity = this.capacity = capacity;
        return true;

    }



    private void checkBounds(int index)
    {
        if (index>= size || index <0)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size " + index );
    }


    public E remove(int index)
    {
        //we still need to cast
        E val = (E) list[index];
        ArrayCopy(list,index + 1,list,index,list.length);
        size--;
        return val;
    }


    public boolean remove(Object o)
    {
        throw new UnsupportedOperationException();
    }

    public boolean removeIf(Predicate<? super E> filter)
    {
        int totalSize = this.size;
        int totalCount = 0;
        for (int i=0; i < totalSize; i++)
        {
            if (filter.test((E) list[i]))
            {
                remove(i);
                totalCount++;
            }
        }
        return (totalCount != 0 && totalSize == totalCount);
    }

    public E removeLast()
    {
        return remove(list.length -1 );
    }

    public boolean addAll(Collection<? extends E> c)
    {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(int index, Collection<? extends E> c)
    {
        throw new UnsupportedOperationException();
    }

    protected void removeRange(int fromIndex, int toIndex)
    {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection<?> c)
    {
        throw new UnsupportedOperationException();
    }

    public void replaceAll(UnaryOperator<E> operator)
    {
        for (Object o: this.list)
            operator.apply((E)o);
    }

    public boolean retainAll(Collection<?> c)
    {
        throw new UnsupportedOperationException();
    }

    public ListIterator<E> listIterator(int index)
    {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: "+index);
        return new ListItr(index);
    }

    public ListIterator<E> listIterator()
    {
        return new ListItr(0);
    }

    public List<E> subList(int fromIndex, int toIndex)
    {
        throw new UnsupportedOperationException();
    }

    public void sort(Comparator<? super E> c)
    {
        final int expectedModCount = modCount;
        Arrays.sort((E[]) list, 0, size, c);
        if (modCount != expectedModCount) {
                 throw new ConcurrentModificationException();
        }
        modCount++;
    }

    public void clear()
    {
        this.list = new Object[this.originalCapacity];
        this.size = 0;
        this.capacity = originalCapacity;
    }

    public void forEach(Consumer<? super E> action)
    {
        for (Object o: this.list)
            action.accept((E)o);
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


    //private functions

    //java style wants EnlargeList as opposed to enlargeList
    private void ExpandList()
    {
        Object[] temp = list.clone();
        this.capacity = INCREMENT_FACTOR * this.capacity;
        list = new Object[this.capacity];
        //System.arraycopy(temp,0,list, 0, temp.length);
        ArrayCopy(temp,0,list,0,temp.length);
    }

    //in your assigment, you must determined the difference between this implementation
    //and the native (created in C++) for System.arraycopy(...)
    private static void ArrayCopy(Object[] src, int srcidx, Object[] dest, int dstidx, int srclen)
    {
        for (int sidx=srcidx,  didx = dstidx; sidx < srclen; sidx++,didx++)
        {
            dest[didx] = src[sidx];
        }
    }


    private void checkBound(int index)
    {
        if (index>= size || index <0)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size " + index );
    }


    private boolean CheckSize()
    {
        return (this.size >= this.capacity);
    }

    @Override
    public Iterator<E> iterator()
    {
        return new Itr();
    }
    //region advanced code (some from java source code)


    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        //from java source code.
        // Estimate size of array; be prepared to see more or fewer elements
        int size = size();
        T[] r = a.length >= size ? a :
                (T[])java.lang.reflect.Array
                        .newInstance(a.getClass().getComponentType(), size);
        Iterator<E> it = iterator();

        for (int i = 0; i < r.length; i++) {
            if (! it.hasNext()) { // fewer elements than expected
                if (a == r) {
                    r[i] = null; // null-terminate
                } else if (a.length < i) {
                    return Arrays.copyOf(r, i);
                } else {
                    System.arraycopy(r, 0, a, 0, i);
                    if (a.length > i) {
                        a[i] = null;
                    }
                }
                return a;
            }
            r[i] = (T)it.next();
        }
        // more elements than expected
        return it.hasNext() ? finishToArray(r, it) : r;
    }

    /**
     * Reallocates the array being used within toArray when the iterator
     * returned more elements than expected, and finishes filling it from
     * the iterator.
     *
     * @param r the array, replete with previously stored elements
     * @param it the in-progress iterator over this collection
     * @return array containing the elements in the given array, plus any
     *         further elements returned by the iterator, trimmed to size
     */
    @SuppressWarnings("unchecked")
    private static <T> T[] finishToArray(T[] r, Iterator<?> it) {
        int i = r.length;
        while (it.hasNext()) {
            int cap = r.length;
            if (i == cap) {
                int newCap = cap + (cap >> 1) + 1;
                // overflow-conscious code
                if (newCap - MAX_ARRAY_SIZE > 0)
                    newCap = hugeCapacity(cap + 1);
                r = Arrays.copyOf(r, newCap);
            }
            r[i++] = (T)it.next();
        }
        // trim if overallocated
        return (i == r.length) ? r : Arrays.copyOf(r, i);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError
                    ("Required array size too large");
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }


    public int hashCode()
    {
        //from java source code
        //this has not been tested.
        int hashCode = 1;
        for (E e : this)
            hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());

        return hashCode;
    }

    /**
     * An optimized version of AbstractList.Itr
     */
    private class Itr implements Iterator<E> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        int expectedModCount = modCount;

        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        public E next() {
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

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                SuperList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public void forEachRemaining(Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
            final int size = SuperList.this.size;
            int i = cursor;
            if (i >= size) {
                return;
            }
            final Object[] elementData = SuperList.this.list;
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            while (i != size && modCount == expectedModCount) {
                consumer.accept((E) elementData[i++]);
            }
            // update once at end of iteration to reduce heap write traffic
            cursor = i;
            lastRet = i - 1;
            checkForComodification();
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    /**
     * An optimized version of AbstractList.ListItr
     */
    private class ListItr extends Itr implements ListIterator<E> {
        ListItr(int index) {
            super();
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        @SuppressWarnings("unchecked")
        public E previous() {
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

        public void set(E e) {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                SuperList.this.set(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(E e) {
            checkForComodification();

            try {
                int i = cursor;
                SuperList.this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    //endregion


    //instance variables

    //Because of limitations with Generics in Java
    //the recommended option is to use an array of Object
    //rememebr that in Java, Object the parent of all objects.
    private Object[] list;
    private int size;
    private int capacity;
    private int originalCapacity;

    // constants
    private static final short INCREMENT_FACTOR = 2;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;


}
