/*
u * PantherID : 4999406
 * Class     : COP2210 - Fall 2016
 * Assignment: #1 
 * Date      : September 14, 2016 (9/14/16)
 * 
 * I hereby swear that and affirm that this work is solely my own, and not the work or
 * derivative of the work of someone else.
 */

package lottoLib;

import java . util.     Random;
import javax.swing.JOptionPane;


public class Main 
{
	
	public static String LotteryGen (String Lottery , int length)
	{ 
		
		String numList = "";
		
		numList = numLister( numList, length );
		numList = numLister( numList, length );
		numList = numLister( numList, length );
		numList = numLister( numList, length );
		numList = numLister( numList, length );
		numList = numLister( numList, length );
		
		String result = Lottery+ "'s Numbers: "+ numList;
		
		return result;
	}
	 
	
	public static String numLister (String numList, int length)
	{
		
		int num = numGen (length);
		
		numList = numList + " " +  String.valueOf (num);
	
		return numList;
	}
	
	
	public static int numGen (int length)
	{
		int num = 0;
				
		Random   r = new Random         ();	
		num        = 1 + r.nextInt(length);
		
		return num;
	}
	
	
	public static void main (String[] args) 
	{
		JOptionPane.showMessageDialog(null, "Welcome to Lotto Number Generator!\n\n"
						                  + "    Press enter for lottery numbers."  );
	 
		JOptionPane.showMessageDialog(null, LotteryGen ("Fantasy5", 36)+ "\n"
							              + LotteryGen ("Lotto"   , 53)      );

	}

}