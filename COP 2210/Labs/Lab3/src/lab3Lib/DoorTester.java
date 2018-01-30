package lab3Lib;

//Driver: 5647084
//Nav   : 4999406
//Exercise 1.7

public class DoorTester 
{
	public static void main(String[] args)
	{
		Door frontDoor = new Door("Front", "open" );
		System.out.println("The front door is " + frontDoor.getState());
		System.out.println("Expected:  open"                              );
		
		Door backDoor  = new Door("Back", "closed");
		System.out.println("The back door is "+ backDoor.getState()   );
		System.out.println("Expeted: closed"                              );
		
		backDoor.setState("open"              );
		System.out.println("The back door is " + backDoor.getState()  );
		System.out.println("Expected: open' "                             );
		
		Door sideDoor = new Door("Side","closed");
		System.out.println("The "+ sideDoor.getName()+ " door is "+       sideDoor.getState());
		sideDoor.setState("open");
		System.out.println("Current state of "+ sideDoor.getName()+ ": "+ sideDoor.getState());
		
	}
	
}

