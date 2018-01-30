package ok;

public class Exercises 
{
	public static void main(String[] args)
	{
		//Exercise 1:
		int sum       = 0;
		int value     = 1;
		
		int numbers[] = new int[100];
		
		for (int n = 0; n <= numbers.length; n++)
		{
			numbers[n] = value;
			value++;
			
			if (numbers[n]%1 == 0)
			{
				sum = sum + numbers[n];
			}
		}
		
		System.out.println(sum+ "\n");
		
		//Exercise 2
		value = 0;
		
		double powers[] = new double[20];
		
		for (int n = 0; n <= 19; n++)
		{
			powers[n] = Math.pow(2, value);
			value++;
			
			System.out.println(powers[n]);
		}
		
		
		
		
		
		
		
		
	}
}
