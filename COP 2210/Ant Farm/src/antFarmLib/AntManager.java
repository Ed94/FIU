//********************************************************************************
// FIU PANTHERID: 4999406
// CLASS: COP 2210 � Fall 2016
// ASSIGNMENT # 2
// DATE: 10/5/2016
//
// I hereby swear and affirm that this work is solely my own, and not the work 
// or the derivative of the work of someone else.
//********************************************************************************

package antFarmLib;

import java .util .Scanner    ;
import javax.swing.JOptionPane;

//A class for initializing and managing AntFarm Objects.
class AntManager
{	
	static Scanner userInput = new Scanner(System.in);	
	
	AntFarm colony;
	
	//Gathers information on parameters for the colony from the user.
	public static void introduction(AntFarm colony)
	{	
		System.out.println("Welcome to Ant Farm Manager!");
		System.out.println(); System.out.println        ();
		
		System.out.println("What would you like to name the Ant Colony?");
		colony.setColonyName(userInput.nextLine                       ());
		System.out.println                                             ();
		
		System.out.println("who is the caretaker?"); 
		colony.setCaretaker(userInput.nextLine  ()); 
		System.out.println                       ();
		
		System.out.println("What will the first queen's name be of "+ colony.getColonyName()+ " ?");
		colony.setQueen1(userInput.nextLine                                                     ());
		System.out.println                                                                       ();
		 
		System.out.println("What will be the starting size of "+ colony.getColonyName()+ " ? (Enter a number)");
		colony.setSizeStart(userInput.nextInt                                                               ());
		System.out.println                                                                                   ();
		
		System.out.println("How many times will you feed the ants?");
		colony.setFeedAmount(userInput.nextInt                   ());
		System.out.println                                        ();
		
		System.out.println("How many times would you like the queen to breed?");
		colony.setBreedAmount(userInput.nextInt                             ());
		System.out.println                                                   ();
		
		System.out.println("Would you like "+ colony.getColonyName()+ " to have an expansion?"
				                            + " (Please enter true for yes, or false for no)" );
		colony.setExpand(userInput.nextBoolean                                              ());
		System.out.println                                                                   ();
	}
	
	//JOptionPane variant of above. 
	public static void introJWin(AntFarm colony)
	{
		JOptionPane.showMessageDialog(null, "Welcome to Ant Manager");
		
		String colonyName   = JOptionPane.showInputDialog("What would you like ot name the Ant Colony?"                                   );
		colony.setColonyName(colonyName  );
		String careTakeName = JOptionPane.showInputDialog("Who is the caretaker?"                                                         );
		String queenName    = JOptionPane.showInputDialog("What will the first queen's name be of "+ colony.getColonyName()+ " ?"         );
		String startingSize = JOptionPane.showInputDialog("What will the starting size of "+ colony.getColonyName()+ " ? (Enter a Number)");
		String feedAmount   = JOptionPane.showInputDialog("How many times will you feed the ants? (Enter a number)"                       );
		String breedAmount  = JOptionPane.showInputDialog("How many times would you like the queen to breed? (Enter a number)"            );
		String expand       = JOptionPane.showInputDialog("Would you like "+ colony.getColonyName()+ " to have an expansion?\n"
														  +"(Please enter true for yes, or false for no)"                                 );
		
		colony.setCaretaker  (careTakeName                  );
		colony.setQueen1     (queenName                     );
		colony.setSizeStart  (Integer.parseInt(startingSize));
		colony.setFeedAmount (Integer.parseInt(feedAmount  ));
		colony.setBreedAmount(Integer.parseInt(breedAmount ));
		colony.setExpand     (Boolean.parseBoolean(expand  ));
	}
	
	//A method with instructions for changing the state of the colony.
	public static void inActive(AntFarm colony)
	{
		System.out.println("Colony is no longer active");
		System.out.println(); System.out.println      ();
		
		colony.setStateActive(false);
	
	}
	public static void simColony(AntFarm colony)
	{
		colony.setSize(colony.getSizeStart());
		colony.setStateActive          (true);

		int chance                                       ;
		
		do 
		{
			System.out.println("Entered Loop"                     );
			System.out.println("Current Size : "+ colony.getSize());
			if (colony.getDaysFed() < 20)
			{
				System.out.println("Passed 20");
				if (colony.getDaysFed() < 10)
				{
					System.out.println("Passed 10");
					if (colony.getFeedAmount() == colony.getDaysFed())
					{
						inActive(colony);
					}
					else
					{
						System.out.println("Passed feed check in 10.");
						if (colony.getBreedAmount() == colony.getBredAmount())
						{
							inActive(colony);
						}
						else
						{
							colony.breed(colony.getSize());
							System.out.println("bred within 10 days past");
						}
						
					}
					
				}
				else
				{
					System.out.println("went to else for 10.");
					if (colony.getQ2Birth() == true)
					{
						System.out.println("passed getQ2Birth");
						if (colony.getFeedAmount() == colony.getDaysFed())
						{
							colony.setSize       (colony.getSize       ()/2 );
							inActive(colony);
						}
						else
						{
							System.out.println("passed to getfeed for getq2birth");
							if (colony.getBreedAmount() != colony.getBredAmount())
							{
								System.out.println("breeds past 10");
								colony.breed(colony.getSize()      );
							}
							else
							{
								System.out.println("are you in here");
								colony.setSize(colony.getSize()/ 2  );
								inActive(colony);
							}
							
						}
						 
					}
					if (colony.getQueenDeaths() == 0)
					{
						if (colony.getExpandValue() == true)
						{
							System.out.println("Inisde expand true");
							chance = colony.random(2);
							colony.queenBirth(chance);
							

						}
						else
						{
							System.out.println("Inside expand false");
							chance = colony.random(10);
							colony.queenBirth(chance );
							
							if (colony.getQ2Birth() == false)
							{
								colony.setSize(colony.getSize()/ 2);
								inActive(colony                   );
							}
						}
						
					}//closes DaysFed = 10
					else if (colony.getQ2Birth() == false)
					{
						System.out.println("Ending sim since no queen was born");
						colony.setSize(colony.getSize()/ 2                     );
						inActive(colony                                        );
					}
					
				}
				 
			}
			else
			{
				System.out.println("went to last else");
				
				if (colony.getDaysFed() == 20)
				{
					colony.setQueenDeaths(colony.getQueenDeaths()+ 1);
					colony.setSize       (colony.getSize()/2        );
				}
				
				inActive(colony);
			}
			
		}
		while (colony.getStateActive() == true);
		
	}
	
	//Gathers current information on the colony.
	public static void getResults(AntFarm colony)
	{
		System.out.println("Results for "+ colony.getColonyName()+ ": ");
		System.out.println                                            ();
		
		System.out.println("Colony                   : "+ colony.getColonyName ());
		System.out.println("Caretaker                : "+ colony.getCaretaker  ());
		System.out.println("Queen                    : "+ colony.getQueen1     ());
		System.out.println("Starting Size            : "+ colony.getSizeStart  ());
		System.out.println("Days Fed                 : "+ colony.getDaysFed    ());
		System.out.println("Amount desired to breed  : "+ colony.getBreedAmount());
		System.out.println("Sucessfully bred amount  : "+ colony.getBredAmount ());
		System.out.println("Queen Deaths             : "+ colony.getQueenDeaths());
		System.out.println("Colony Expanded          : "+ colony.getExpandValue());
		System.out.println("Second Queen Birth Status: "+ colony.getQ2Birth    ());
		System.out.println("Second queen name        : "+ colony.getQueen2     ());
		System.out.println("Final ant population     : "+ colony.getSize       ());
		System.out.println                                                      (); 
	}
	
	public static void getResultsJWin(AntFarm colony)
	{
		JOptionPane.showMessageDialog(null, "Results for "+ colony.getColonyName()+ ": \n"
									      + "Colony-----------------------------: "+ colony.getColonyName ()+ "\n"
									      + "Caretaker-------------------------: " + colony.getCaretaker  ()+ "\n"
									      + "Queen-----------------------------: " + colony.getQueen1     ()+ "\n"
									      + "Starting Size--------------------: "  + colony.getSizeStart  ()+ "\n"
									      + "Days Fed-------------------------: "  + colony.getDaysFed    ()+ "\n"
									      + "Amount desired to breed---: "         + colony.getBreedAmount()+ "\n"
									      + "Sucessfully bred amount---: "         + colony.getBredAmount ()+ "\n"
									      + "Queen Deaths------------------: "     + colony.getQueenDeaths()+ "\n"
									      + "Colony Expanded--------------: "      + colony.getExpandValue()+ "\n"
									      + "Second Queen Birth Status: "          + colony.getQ2Birth    ()+ "\n"
									      + "Second Queen Name---------: "         + colony.getQueen2     ()+ "\n"
									      + "Final ant population------------: "   + colony.getSize       ()+ "\n");
	}
	
	public static void main(String[]args)
	{
		AntFarm colony = new AntFarm();
		
	  //introduction  (colony);
		introJWin     (colony);
		
		simColony     (colony);
		
	  //getResults    (colony);
		getResultsJWin(colony);
		
		userInput.close();
	}
} 