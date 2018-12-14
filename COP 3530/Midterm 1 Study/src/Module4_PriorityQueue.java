import java.util.*;


public class Module4_PriorityQueue 
{
	public enum Implementation 
	{ 
		Simple_LinkedList,   //Insert: O(1)      DeleteMin: O(N)
		Sorted_LinkedList,   //Insert: O(N)      DeleteMin: O(1)
		Binary_SearchTree,   //Insert: O(Log(N)) DeleteMin: O(Log(N))
		Binary_MinHeap       //Insert: O(1)-Avg  DeleteMin: O(Log(N))
	}  
	
	public static class PriorityQueue
	{
		//Child Structures
		
		class Element
		{
			public int key;
			
			public int priority;
		}
		
		class Node
		{
			public int key; 
			
			public int priority;
		}

		//Constructors
		
		public PriorityQueue(Implementation _implementationDesired)
		{
			minHeap = new ArrayList<Element>();
		}
		
		//Functions
		
		//Public:
		
		//Equivalent of enqueue.
		public void Insert(int key, int priority)
		{
			
		}

		//Finds, returns, and removes the minimum element in the priority queue. (Equivalent of dequeue)
		public void DeleteMin()
		{
			
		}
		
		//Private:
		
		int FindMin()
		{
			if (minHeap.size() > 0)
			{
				return minHeap.get(0).key;
			}
			else
			{
				return -1;
			}
		}
		
		//Declares
		ArrayList<Element> minHeap;
	}
	
	
	public static void main(String[] args)
	{
		PriorityQueue pQueue = new PriorityQueue(Implementation.Binary_MinHeap);
		
		
	}
}
