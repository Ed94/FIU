package Lab2Library;


import java.util.StringTokenizer;


public class Main
{
	/*
	 * Lab Assignment#2
	 * Exercise #1.1
	 * Driver   : Edward R. Gonzalez
	 * Navigator: Kitiara Soley Rivera
	 * Date: 9/21/16, 9/21/16
	 * 
	 */
	
	public static void main (String []args)
	{
		String      test = "This is a test.";
		String smallTest = test.toLowerCase();
		
		System.out.println(smallTest);
		System.out.println       ("");
		
		
		/*
		 * Driver   : Kitiara
		 * Navigator: Edward
		 */
		
		
		String bigtest = test.toUpperCase();
		
		System.out.println(bigtest);
		System.out.println     ("");
		
		
		/*
		 * Driver   : Edward
		 * Navigator: Kitiara
		 *  
		 */
		
		
		String animal1 = "quick brown fox ";
		String animal2 = "lazy dog"        ;
		
		String article = "the "       ;
		String action  = "jumps over ";
		
		String foxString = article.concat(animal1);
		
		foxString = foxString.concat(action) ;
		foxString = foxString.concat(article);
		foxString = foxString.concat(animal2);
		
		System.out.println("Fox String 1:" + foxString);
		System.out.println                         ("");
		
		/*
		 * Driver   : Kitiara
		 * Navigator: Edward
		 */
		
		String foxString2 = article + animal1 + action + article + animal2;
		
		System.out.println("Fox Stirng 2:" + foxString2);
		System.out.println                          ("");
		
		
		/*
		 * Driver   : Edward
		 * Navigator: Kitiara
		 * 
		 */
		
		
		String         sentence = "Mary had a litle lamb."     ;
		StringTokenizer mystery = new StringTokenizer(sentence);
		
		System.out.println(mystery.countTokens());
		System.out.println(mystery.nextToken())  ;
		System.out.println(mystery.nextToken())  ;
		
	}
}

		
		
