package lab3Lib;

public class DayTester 
{
	/*
	 *  Lab Assignment # 3
	 * Date: September 21st, 2016
	 * Exercise # 2.14
	 * Driver: 4999406
	 * Nav: 5647084
	 */
	
	public static void main(String[] args)
	{
		Day today  = new Day(2016, 9, 21);
		Day tenDay = today   .addDays(10);
		
		int daysBetween = tenDay.daysFrom(today);
		
		System.out.println("The days between "+ today+ " and "+ tenDay+ " is "+ daysBetween);
		System.out.println("Expected Value is 10."                                         );
		
	}

}
