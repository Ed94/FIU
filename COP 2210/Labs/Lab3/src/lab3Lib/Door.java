package lab3Lib;


//Driver: 5647084
//Nav   : 4999406
//Exercise 1.1

public class Door 
{
	//Driver: 5647084
	//Nav   : Jordis
	//Exercise 1.2
	
	private String doorName ;
	private String doorState;
	
	//Driver: 5647084
	//Nav   : 4999406
	//Exercise 1.3
	
	public String open()
	{
		doorState = "open";
		
		return doorState;
	}
	
	public String close()
	{
		doorState = "closed";
		
		return doorState;
	}
	
	//Driver: 4999406
	//Nav   : 5647084
	//Exercise 1.4
	
	public Door(String newName, String newState)
	{
		doorName  = newName ;
		doorState = newState;
		
	}
	
	
	//Driver: 5647084
	//Nav   : 4999406
	//Exercise 1.5
	
	public String getName()
	{
		return doorName;
		
	}
	
	public String getState()
	{
		return doorState;
	}
	
	//Driver: 4999406
	//Nav   : 5647084
	//Exercise 1.6
	
	public void setName(String newDoorName)
	{
		doorName = newDoorName;
	}
	
	public void setState(String newDoorState)
	{
		doorState = newDoorState;
	}
	
}
