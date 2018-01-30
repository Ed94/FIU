package lab7;

public class Exercise2 
{

	public static void main(String[] args)
	{
		//int interval    ;
		int interval = 0;
		int num      = 0;
		int counter  = 1;
		
		/*
		for (interval =0   ;
			 interval < 100; 
			 interval    ++)
		{
			num     = num     + counter;
			counter					 ++;
			
			System.out.println(num);
		}
		*/
		
		
		do
		{
		
			num 	= num + counter;
			counter			 	 ++;
			interval             ++;
			
			System.out.println(num);
		
		} while(interval < 100);
		
		
	}
	
}
