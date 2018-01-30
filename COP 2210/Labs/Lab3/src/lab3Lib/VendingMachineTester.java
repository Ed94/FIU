package lab3Lib;

//Driver: Edward
//Nav   : Jorids
//Exercise 2.8
public class VendingMachineTester
{
	public static void main(String[] args)
	{
		VendingMachine machine = new VendingMachine();
		machine.refill(10); //Fill up with 10 cans.
		machine.purchase();
		machine.purchase();
		System.out.print("Token count: ");
		System.out.println(machine.getTokens());
		System.out.println("Expected: 2");
		System.out.print("Can count: ");
		System.out.println(machine.getCans());
		System.out.println("Expected: 18");
		
	}
}
