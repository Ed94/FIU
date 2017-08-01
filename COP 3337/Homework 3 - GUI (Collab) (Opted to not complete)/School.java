import java.util.ArrayList;


public class School
{
	//Public:
	public School(String name									   ) { this.name = name; this.schedule = "NAN"; this.memberLeader="NAN";			 }
	public School(String name, String schedule					   ) { this.name = name; this.schedule = schedule; 								     }
	public School(String name, String schedule, String memberLeader) { this.name = name; this.schedule = schedule; this.memberLeader = memberLeader; }
	
	public School(String name, String schedule, ArrayList<String> members)
	{ 
		this.name     = name    ;
		this.schedule = schedule;
		this.members  = members ;
		this.memberLeader="NAN";
	}
	
	public School(String name, String schedule, String memberLeader, ArrayList<String> members)
	{
		this.name         = name    	;
		this.schedule     = schedule	;
		this.members      = members 	;
		this.memberLeader = memberLeader;
	}
	
	
	public School getData()
	{ return new School(this.name, this.schedule, this.memberLeader, this.members); }
	
	public String getName        () { return this.name        ; }
	public String getSchedule    () { return this.schedule	  ; }
	public String getMemberLeader() { return this.memberLeader; }
	
	public ArrayList<String> getMembers() 
	{ return this.members; }
	
	public void setMembers(ArrayList<String> members)
	{ this.members = members; }
	
	public void setName        (String name        ) { this.name         = name    	   ; }
	public void setSchedule    (String schedule    ) { this.schedule     = schedule	   ; }
	public void setMemberLeader(String memberLeader) { this.memberLeader = memberLeader; }
	
	
	//Private:
	String name    ;
	String schedule;
	
	String memberLeader;
	
	ArrayList<String> members = new ArrayList<>();
}