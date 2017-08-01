public class Member 
{
	//Public:
	public Member(String name)
	{ this(name, "", "", "", "", "", false); }
	
	public Member(String name,String openTimes)
	{ this(name,openTimes,"","","","",false); }

	public Member(String name,String openTimes,String startDate)
	{ this(name,openTimes,startDate,"","","",false); }

	public Member(String name,String openTimes,String startDate,String endDate)
	{ this(name,openTimes,startDate,endDate,"","",false); }

	public Member(String name,String openTimes,String startDate,String endDate, String school)
	{ this(name,openTimes,startDate,endDate,school,"",false); }
	
	public Member(String name,String openTimes,String startDate,String endDate,String school, String lastAttended)
	{ this(name,openTimes,startDate,endDate,school,lastAttended,false); }

	public Member(String name, String openTimes, String startDate, String endDate, String school, String lastAttended, boolean state)
	{
		this.name         = name	 	; 
		this.openTimes    = openTimes	; 
		this.startDate    = startDate	; 
		this.endDate      = endDate  	;
		this.school       = school   	;
		this.lastAttended = lastAttended;
		this.state        = state	  	;
	}
	
	
	public Member getData()
	{ return new Member(name, startDate, endDate, openTimes, school, lastAttended, state); }
	
	public boolean getState()
	{ return this.state; }
	
	public String getName		 () { return name		 ; }
	public String getStartDate	 () { return startDate	 ; }
	public String getEndDate	 () { return endDate	 ; }
	public String getOpenTimes	 () { return openTimes	 ; }
	public String getSchool		 () { return school		 ; }
	public String getLastAttended() { return lastAttended; }
	
	public void setState(boolean state)
	{ this.state = state; }
	
	public void setName		   (String name        ) { this.name         = name	   	   ; }
	public void setStartDate   (String startDate   ) { this.startDate    = startDate   ; }
	public void setEndDate     (String endDate     ) { this.endDate      = endDate     ; }
	public void setOpenTimes   (String openTimes   ) { this.openTimes    = openTimes   ; }
	public void setSchool	   (String school      ) { this.school       = school      ; }
	public void setLastAttended(String lastAttended) { this.lastAttended = lastAttended; }
	
	
	//Private:
	boolean state;
	
	String name;
	
	String startDate;
	String endDate  ;
	String openTimes;
	
	String school	   ;
	String lastAttended;
}
