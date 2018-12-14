/**************************************************************
 Purpose/Description: Get a fibonacchi number in sub-linear time.
 Author’s Panther ID: 4999406
 Certification:
 I hereby certify that this work is my own and none of it is the work of
 any other person.
 **************************************************************/ 
import java.math.BigInteger;

public class Problem_3
{
	//Gets the fibonachi using matrix multiplication.
    public static void calcualteViaMatrix(BigInteger[][] first, BigInteger[][]second)
    {
    	/*
    	 *  | w x |   Reference to array order.
    	 *  | y z |
    	 */
    	BigInteger w = (first[0][0].multiply(second[0][0])).add(first[0][1].multiply(second[1][0]));
    	BigInteger x = (first[0][0].multiply(second[0][1])).add(first[0][1].multiply(second[1][1]));
    	BigInteger y = (first[1][0].multiply(second[0][0])).add(first[1][1].multiply(second[0][1]));
    	BigInteger z = (first[1][0].multiply(second[0][1])).add(first[1][1].multiply(second[1][1]));
    	
    	first[0][0] = w;
    	first[0][1] = x;
    	first[1][0] = y;
    	first[1][1] = z;
    }
	
	public static BigInteger subFib(int n)
	{	
		BigInteger[][] firstMatrix, secondMatrix;
			
		firstMatrix  = new BigInteger[2][2];   //Stores the resulting fibonachi at (1, 0)y
		secondMatrix = new BigInteger[2][2];   //The fibonachi matrix
		
		firstMatrix[0][0] = new BigInteger("1");
		firstMatrix[0][1] = new BigInteger("0");
		firstMatrix[1][0] = new BigInteger("0");
		firstMatrix[1][1] = new BigInteger("1");
		
		secondMatrix[0][0] = new BigInteger("1");
		secondMatrix[0][1] = new BigInteger("1");
		secondMatrix[1][0] = new BigInteger("1");
		secondMatrix[1][1] = new BigInteger("0");
		
		while (n > 0)
		{
			if (n % 2 == 1)
			{
				calcualteViaMatrix(firstMatrix, secondMatrix);
			}
			
			n = n / 2; //Produces log N
					
			calcualteViaMatrix(firstMatrix, secondMatrix);
		}
		
		return firstMatrix[1][0];
	}
	
	public static BigInteger fib(int n)
	{
		BigInteger result = fib(n -1).add(fib(n-2));
		
		return result;
	}
	
	public static void main(String[] args) 
	{
		BigInteger result = fib(250);
		
		System.out.println( result.toString() );
	}
}
