public class Problem_2 
{
	public static class MinHeap
	{
		Integer[] HeapArray;
		
		public void perlocateUp(Integer position);
		public void perlocateDown(Integer position);
		
		public void replaceKey(Integer oldKey, Integer newKey)
		{
			Integer position = 0;
			
			for (;position < HeapArray.length && !HeapArray[position].equals(oldKey); position++);
			
			if (position == (HeapArray.length - 1))
			{
				System.out.println("The key: " + oldKey + " is not within the heap.");
				
				return;
			}
			else
			{
				HeapArray[position] = newKey;
				
				if ( (position > 0) && (HeapArray[position].compareTo(HeapArray[position/2]) < 0) )
				{
					perlocateUp(position);
				}
				else
				{
					perlocateDown(position);
				}
			}
		}
	}
}