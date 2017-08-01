import java.util.ArrayList;
import java.util.Iterator ;
import java.util.ListIterator;
import java.util.Stack	  ;


public class Maze 
{
	//Public
	public Maze(char[][] mazeMap)
	{ this.mazeMap = mazeMap; }
	 
	public boolean escape(int x, int y)
	{	
		reset();
		
		ducktape = new Tuple<Integer, Integer>(x, y);
		
		if (recursion(x, y))
		{
			path.push(pos);
			
			return true;
		}
		else
			return false;
	}
	
	
	//Private
	boolean recursion(int x, int y)
	{
		try
		{
			if (!reachedBounds)
			{
				getNextPos(x, y);
				
				if  (checkIfBoundary()) { return true	; }
				else 					{ checkOptions(); }
				
				path.push(pos);
				
				if (history.size() < ((mazeMap.length-1)*(mazeMap[0].length-1)))
					history.push(path.peek());
				
				if (optSize > 0 && !repeat) { recursion(pos.x, pos.y); }
				else 			 		    { return false			 ; }
			}
			
			return reachedBounds;
		}
		catch (StackOverflowError t)
		{
			return false;
		}
	}
	
	boolean checkIfBoundary()
	{
		if  (pos.x == mazeMap.length || pos.y == mazeMap.length || pos.x == 0 || pos.y == 0)
			return reachedBounds = true;
		else
			return false;
	}
	
	boolean checkOptionLevel()
	{
		try
		{
			if (options.get(stackLvl).size() <= 0)
				return false;
			else
				return true;
		}
		catch (StackOverflowError t)
		{
			System.out.println("StackOverflow for: "+ t);
			
			System.out.println("Recursion has most likely overflowed due to checks to prevent repeat coords not working.");
			
			return false;
		}
	}
	
	boolean checkIfTravelled(int x, int y)
	{
		for (ListIterator<Tuple<Integer, Integer>> pathList = history.listIterator();  pathList.hasNext();)
		{
			Tuple<Integer, Integer> crnt = pathList.next();
			
			if (x == crnt.x && y == crnt.y)
				return true;
		}
		return false;
	}
	
	class Tuple<X, Y>   //Stack Overflow
	{
		public final X x;
		public final Y y;
		
		public Tuple(X x, Y y)
		{
			this.x = x;
			this.y = y;
		}
		
		public Tuple(Tuple<X, Y> passedTuple)
		{
			this.x = passedTuple.x;
			this.y = passedTuple.y;
		}
		
		@Override
		public String toString()
		{ return new String(x+ " "+ y); }
	}
	
	void checkOptions()
	{
		expandLevels();
		
		for (int y = -1; y <= 1; y++)
		{   
			for (int x = -1; x <= 1; x++)
			{
				int index_X = x + pos.x, index_Y = y + pos.y;
				
				if (index_X < mazeMap.length && index_Y < mazeMap[0].length)
				{
					if      (x ==  0 && y ==  0) {}
					else if (x == -1 && y == -1) {}
					else if (x ==  1 && y ==  1) {}
					else if (x == -1 && y ==  1) {}
					else if (x ==  1 && y == -1) {}
					else if (mazeMap[index_X][index_Y] == ' ')
					{
						Tuple<Integer, Integer> possible = new Tuple<Integer, Integer>((x + pos.x), (y + pos.y));
						
						boolean travelled = false;
						boolean inOptions = false;
						
						for (int tempLvlIndex = 0; tempLvlIndex < optSize; tempLvlIndex++)
						{
							for (int stackIndex = 0; stackIndex < options.get(tempLvlIndex).size(); stackIndex++)
							{
								if (possible.x == options.get(tempLvlIndex).get(stackIndex).x && possible.y == options.get(tempLvlIndex).get(stackIndex).y)
									inOptions = true;
								
								try
								{
									for (ListIterator<Tuple<Integer, Integer>> pathList = history.listIterator();  pathList.hasNext();)
									{
										Tuple<Integer, Integer> crnt = pathList.next();
										
										if (possible.x == crnt.x && possible.y == crnt.y)
											travelled = true;
									}
								}
								catch (StackOverflowError t)
								{
									System.out.println("StackOverflow for: "+ t);
									
									System.out.println("History stack was mostly likely at max size and somehow got passed all these checks...");
								}
								
							}
						}
						if (travelled == false && inOptions == false)
							options.get(stackLvl).push(possible);
					}
				}
			}
		}
	}

	void getNextPos(int x, int y)
	{
		try
		{
			if (options.isEmpty() && !checkIfTravelled(x, y))
				pos = new Tuple<Integer, Integer>(x, y);
			else
			{
				if (checkOptionLevel())
				{
					pos = new Tuple<Integer, Integer>(options.get(stackLvl).pop());
					
					stackLvl++;
					
					if (pos.x == ducktape.x && pos.y == ducktape.y)
						repeat = true; //My check options somehow leaks the original position if it does not find an exit =/.
				}
				else
				{
					if (stackLvl > 0)
					{
						options.remove(stackLvl);
						
						optSize--; stackLvl--;
						
						path.pop();
						
						getNextPos(x, y);
					}
				}
			}
		}
		catch (StackOverflowError t)
		{
			System.out.println("StackOverflow for: "+ t);
			
			System.out.println("Options stack was most likely empty and somehow got passed all these checks...");
		}
	}
	
	void expandLevels()
	{
		if (optSize <= stackLvl)
		{
			options.add(new Stack<Tuple<Integer, Integer>>());
			
			optSize++;
		}
	}
	
	public void printPath()
	{
		System.out.println("Path:");
		
		for (ListIterator<Tuple<Integer, Integer>> pathList = path.listIterator();  pathList.hasNext();)
		{
			Tuple<Integer, Integer> crnt = pathList.next();
			
			System.out.print("("+ crnt.x+ ", "+ crnt.y+ ") ");
		}
		
		System.out.println();
	}
	
	void reset()
	{
		stackLvl = 0; optSize = 0; reachedBounds = false;
		
		options = new ArrayList<Stack<Tuple<Integer, Integer>>>();
		
		path 	= new Stack<Tuple<Integer, Integer>>();
		history = new Stack<Tuple<Integer, Integer>>();
	}
	
	//Instance
	boolean reachedBounds = false; boolean repeat = false;
	
	char[][] mazeMap;
	
	int stackLvl = 0, optSize = 0;
	
	ArrayList<Stack<Tuple<Integer, Integer>>> options = new ArrayList<Stack<Tuple<Integer, Integer>>>();
	
	Stack<Tuple<Integer, Integer>> history = new Stack<Tuple<Integer, Integer>>();
	Stack<Tuple<Integer, Integer>> path    = new Stack<Tuple<Integer, Integer>>();
	
	Tuple<Integer, Integer> pos		;
	Tuple<Integer, Integer> ducktape;
}