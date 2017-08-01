import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class MazeInputDriver
{
	public static void main(String args[])
	{
		boolean printPath = true;
		boolean printMaze = true;
		
		File info = null;
		
		info = new File("Maze1.txt");
		
		char[][] map = null;
		
		int col = 0;
		int row = 0;
		
		for (String s: args)
		{
			if (s.contains("info:"	   ))
				info = new File(s.substring(6, s.length()));
				
			if (s.contains("printpath:"))
			{
				if (s.substring(9, s.length()).contains("yes"))
					printPath = true ;
				if (s.substring(9, s.length()).contains("no" ))
					printPath = false;
			}
			if (s.contains("printmaze:"))
			{
				if (s.substring(9, s.length()).contains("yes"))
					printPath = true ;
				if (s.substring(9, s.length()).contains("no" ))
					printPath = false;
			}
		}
		
		System.out.println("Path      :"+ info	   );
		System.out.println("Print Path:"+ printPath);
		System.out.println("Print Maze:"+ printMaze);
		
		Scanner scnr;
		
		try
		{
			scnr = new Scanner(info);
			
			for (String line = scnr.nextLine(); scnr.hasNextLine();)
			{
				if (line.contains("txt"))
				{
					System.out.println("\nMaze Information:");
					System.out.println(line					);
					
					col = Integer.parseInt(line.substring(10, 12));
					row = Integer.parseInt(line.substring(13, 15)+1);   //Needed a buffer for some reason...
					
					map = new char[col][row];
					
					System.out.println(col+" "+ row);
				}
				if (line.startsWith("#"))
				{
					System.out.println("Importing maze...");
					
					scnr.useDelimiter("");
					
					for (int y = 0; y < row; y++) 
					{
						for (int x = 0; x < col; x++) 
						{ 
							if (scnr.hasNext())
							{
								char c = scnr.next().charAt(0);
								
								map[x][y] = c;
							}
						}
					}
				}
				if (scnr.hasNextLine())
					line = scnr.nextLine();
			}
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (printMaze)
		{
			System.out.println("\nMap: ");
			
			for (int y = 0; y < row; y++) 
			{
				for (int x = 0; x < col; x++) 
					System.out.print(map[x][y]);
			}
		}
		
		System.out.println("\n\nAttempting Escape...");
		
		Maze   maze   = new Maze  (map);
		Random random = new Random   ();

		boolean done = false;
		
		while (done == false)
		{
			int x = random.nextInt(col), y = random.nextInt(row);
			
			if (map[x][y] == ' ' && x > 0 && x < (col-1) && y > 0 && y < (row-1))
			{
				System.out.println(x+ " "+ y+ " "+ map[x][y]);
				
				System.out.println("Escaped: "+ maze.escape(x, y));
				
				if (printPath)
					maze.printPath();
				
				done = true;
			}
		}
	}
}
