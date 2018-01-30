package lab6;


import java.util.Scanner;


public class StringCompare
{
	public static void main(String[] args)
	{
		
		String s1;
		String s2;
		String s3;
		
		Scanner userInput = new Scanner(System.in);
		
		System.out.println("Enter the first string: ");
		
		s1 = userInput.nextLine();
		
		System.out.println("Enter the second string: ");
		
		s2 = userInput.nextLine();
		
		System.out.println("Enter the third string: ");
		
		s3 = userInput.nextLine();
		
	/*	if (s1.compareTo(s2) > s1.compareTo(s3))
		{
			if(s1.compareTo(s2) > s2.compareTo(s3))
			{
				if (s1.compareTo(s2) > s3.compareTo(s2))
				{
					
				}
				else
				{
					
				}
				
			}
			else
			{
				
			}
		}
		else if (s1.compareTo(s3))
		{
			
		}
		else
		{
			
		}*/
		
		
		String highest = s3;
		
		if(s1.compareTo(s3) < 0)
		{
			highest = s3;
		}
		
		userInput.close();
	}
}
