package lab3Lib;

//BONUS 3.8
public class Employee 
{
	private String name;
	private double salary;
	
	public Employee(String currentName, double currentSalary)
	{
		name   = currentName;
		salary = currentSalary;
	}
	
	public String getName()
	{
		return name;
	}
	public double getSalary()
	{
		return salary;
	}
	
	public void raiseSalary(double byPercent)
	{
		byPercent = byPercent/100;
		salary = salary* (1.0 + byPercent);
	}
	
}
