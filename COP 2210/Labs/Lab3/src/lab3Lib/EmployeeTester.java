package lab3Lib;

public class EmployeeTester
{
	public static void main(String[] args)
	{
		Employee harry = new Employee("Hacker, Harry", 50000);
		harry.raiseSalary(10); // Harry gets a 10 percent raise
		System.out.println(harry.getName()+ "'s salary is: "+ harry.getSalary());
	}
}
