package antFarmLib;


import java.util.Random;


//Class for the AntFarm Object
public class AntFarm 
{
	//Variables
	private boolean active ;
	private boolean expand ;
	private boolean q2Birth;
	
	private int bred       ;
	private int breedAmount;
	private int daysFed    ;
	private int feedAmount ;
	private int queenDeaths;
	
	private long size     ;
	private long sizeStart;
	
	private String caretaker ;
	private String colonyName;
	private String queen1    ;
	private String queen2    ;
	
	
	//Constructors
	public AntFarm()
	{
	}
	
	//Colony Methods
	public void    setStateActive(boolean newState)
	{
		active = newState;
	}
	
	public boolean getStateActive()
	{
		return active;
	}
	
	public void    setColonyName(String newName)
	{
		colonyName = newName;
	}
	
	public String  getColonyName()
	{
		return colonyName;
	}
	
	public void    setDaysFed(int numDays)
	{
		daysFed = numDays;
	}
	
	public int     getDaysFed()
	{
		return daysFed;
	}
	
	public void    setExpand(boolean newChoice)
	{
		expand = newChoice;
	}
	
	public boolean getExpandValue()
	{
		return expand;
	}
	
	public void    setFeedAmount(int newFeed)
	{
		feedAmount = newFeed;
	}
	
	public int     getFeedAmount()
	{
		return feedAmount;
	}
	
	public void    setSize(long newSize)
	{
		size = newSize;
	}
	
	public long    getSize()
	{
		return size;
	}
	
	public void    setSizeStart(long newSize)
	{
		sizeStart = newSize;
	}
	
	public long    getSizeStart()
	{
		return sizeStart;
	}
	
	//Care-taker Methods
	public void    setCaretaker(String newCaretaker)
	{
		caretaker = newCaretaker;
	}
	
	public String  getCaretaker()
	{
		return caretaker;
	}
	
	//Non-Specific Methods
	public int     random(int range)
	{
		Random r = new Random    ();
		int    x = r.nextInt(range); 
		
		return x;
	}
	
	//Queen Methods
	public void    setBreedAmount(int newBreedAmount)
	{
		breedAmount = newBreedAmount;
	}
	
	public int     getBreedAmount()
	{
		return breedAmount;
	}
	
	public void    setBredAmount(int bredAmount)
	{
		bred = bredAmount;
	}
	
	public int     getBredAmount()
	{
		return bred;
	}
	
	public void    breed(long currentSize)
	{
			size        = currentSize * 3;
			bred    ++;
			daysFed ++;
	}
	
	public void    setQueen1(String newQueen)
	{
		queen1 = newQueen;
	}
	 
	public String  getQueen1()
	{
		return queen1;
	}
	
	public void    setQueen2()
	{
		queen2 = queen1+ " 2.0";
	}
	
	public String  getQueen2()
	{
		return queen2;
	}
	
	public void    queenBirth(int chance)
	{
		if (chance == 0)
		{
			System.out.println("Queen birth occured");
			setQ2Birth(true);
			setQueen2     ();
			size = size / 2 ;
			queenDeaths   ++;
		}
		
		else
		{
			setQ2Birth(false);
			queenDeaths    ++;
		}
	}
	
	public void    setQ2Birth(boolean birthState)
	{
		q2Birth = birthState;
	}
	
	public boolean getQ2Birth()
	{
		return q2Birth;
	}
	
	public void    setQueenDeaths(int deathAmount)
	{
		queenDeaths = deathAmount;
	}
	
	public int     getQueenDeaths()
	{
		return queenDeaths;
	}
	
}