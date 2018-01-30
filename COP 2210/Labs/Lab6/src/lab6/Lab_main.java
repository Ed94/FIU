package lab6;


import java.util.Scanner;


public class Lab_main 
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);

		System.out.print("Input x coordinate of the first point: ");
	    double xcoord1 = in.nextDouble();

	    System.out.print("Input y coordinate of the first point: ");
	    double ycoord1 = in.nextDouble();

	    System.out.print("Input x coordinate of the second point: ");
	    double xcoord2 = in.nextDouble();

	    System.out.print("Input y coordinate of the second point: ");
	    double ycoord2 = in.nextDouble();

	    double slope;
	    
	    double numerator = (ycoord2 - ycoord1);
	    double denominator = (xcoord2 - xcoord1);
	    
	    if (denominator == 0)
	    {
	    	System.out.println("There is no slope.");
	    }
	    else
	    {
	    	slope = numerator / denominator; 
	    	
	    	System.out.println("The slope is: "+ slope);
	    }
	    
	    in.close();
	}
}
