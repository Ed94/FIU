package lab3Lib;

//Driver: Edward
//Nav   : Jordis
//Exercise: 2.7

public class VendingMachine 
{

	//Driver: Jordis
	//Nav   : Edward
	//Exercise: 2.3
	private int tokens = 0;
	private int cans  = 0;
	
	//Driver: Edward
	//Nav   : Jordis
	//Exercise: 2.4
	public void purchase()
	{
		cans = cans - 1;
		tokens = tokens + 1;
	}
	
	//Driver: Edward
	//Nav   : Jordis
	//Exercise: 2.5
	public void refill(int newCans)
	{
		cans = cans + newCans;
	}
	
	//Driver: Jorids
	//Nav   : Edward
	//Exercise: 2.6
	
	public int getCans()
	{
		return cans;
	}
	public int getTokens()
	{
		return tokens;
	}	
	
	//Driver: Jordis
	//Nav   : Edward
	//Exercise: 2.9
	
	public VendingMachine()
	{
		cans = 10;
	}
	
	public VendingMachine(int newCans)
	{
		cans = newCans;
	}
	
}
