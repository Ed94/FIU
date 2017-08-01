import java.io.BufferedWriter		;
import java.io.File					;
import java.io.FileNotFoundException;
import java.io.FileWriter			;
import java.io.IOException			;
import java.util.ArrayList			;
import java.util.Scanner			;


public class IO_Handler
{
	//Public
	public IO_Handler() { }
	
	
	public void addMember(Member member)
	{
		if (members.equals(null))
		{ members = new ArrayList<Member>(); members.add(member); }
		else
			members.add(member);
	}
	
	public void addSchool(School school)
	{
		if (schools.equals(null))
		{ schools = new ArrayList<School>(); schools.add(school); }
		else
			schools.add(school);
	}
	
	public void exportMembers(File file)
	{
		if (members.equals(null))
			System.out.println("No members to export.");
		else
		{
			File memberFile = file;
			
			try 
			{
				@SuppressWarnings("resource")
				BufferedWriter out = new BufferedWriter(new FileWriter(memberFile));
				
				out.write("{");
				
				for (Member member : members)
				{
					out.write("name        : "+ member.name );
					out.write("state       : "+ member.state);
					
					if (!member.startDate	.equals(null)) { out.write("startDate   : \""+ member.startDate	  + "\""); }
					if (!member.endDate  	.equals(null)) { out.write("endDate     : \""+ member.endDate  	  + "\""); }
					if (!member.openTimes	.equals(null)) { out.write("openTimes   :   "+ member.openTimes		    ); }
					if (!member.school   	.equals(null)) { out.write("school      : \""+ member.school	  		); }
					if (!member.lastAttended.equals(null)) { out.write("lastAttended: \""+ member.lastAttended+ "\""); }
					
					out.write("}"); 
					out.write("" );
				}
			}
			catch (IOException e) 
			{ e.printStackTrace(); }
		}
	}
	
	public void exportSchools(File file)
	{
		if (schools.equals(null))
			System.out.println("No schools to export.");
		else
		{
			File schoolFile = file;
			
			try 
			{
				BufferedWriter out = new BufferedWriter(new FileWriter(schoolFile));
			
				out.write("{");
				
				for (School school : schools)
				{
					out.write("name        : "+ school.name);
					
					if (!school.schedule.equals(null)) { out.write("schedule    : "+ school.schedule); }
					if (!school.members .equals(null))
					{
						String memList = "(";
						
						for (String string : school.members)
						{
							memList.concat("'"+ string);
						}
						
						memList.concat(")");
					}
					if (!school.memberLeader.equals(null)) 
						out.write("memberLeader: "+ school.memberLeader);
					
					out.write("}"); 
					out.write("" );
				}
			} 
			catch (IOException e) 
			{ e.printStackTrace(); }
		}
	}
	
	public void importMembers(String filePath)
	{
		if (filePath.contains(".m"))
		{
			File memberFile = new File(filePath);
			
			boolean state = false;
			
			String name 		= null;
			String startDate    = null;
			String endDate      = null;
			String openTimes    = null;
			String school       = null;
			String lastAttended = null;
			
			try 
			{
				Scanner scnr = new Scanner(memberFile);
				
				while (scnr.hasNextLine())
				{
					String crtLine = scnr.nextLine();
					
					if (crtLine.contentEquals("{"))
					{
						while (!crtLine.contentEquals("}") && scnr.hasNextLine())
						{
							crtLine = scnr.nextLine();
							
							state 	     = LineTranscriber(crtLine, "state"		  , ':');
							
							if (name 	 	 == null) 
								name 	  	 = LineTranscriber(crtLine, "name"	   	  , ':',  0);
							if (startDate 	 == null)
								startDate 	 = LineTranscriber(crtLine, "startDate"	  , '"', -1);
							if (endDate  	 == null)
								endDate   	 = LineTranscriber(crtLine, "endDate"  	  , '"', -1);
							if (openTimes 	 == null)
								openTimes 	 = LineTranscriber(crtLine, "openTimes"	  , ':',  0);
							if (school 		 == null)
								school 		 = LineTranscriber(crtLine, "school"	  , ':',  0);
							if (lastAttended == null)
								lastAttended = LineTranscriber(crtLine, "lastAttended", '"', -1);
						}
						
						if (crtLine.contentEquals("}"))
						{
							System.out.println(state +" "+ name+ " "+ startDate+ " ");
							
							if (!name.equals(null))
							{
								if (members.equals(null))
								{
									members = new ArrayList<Member>();
									
									members.add(new Member(name, startDate, endDate, openTimes, school, lastAttended));
								}
								else
									members.add(new Member(name, startDate, endDate, openTimes, school, lastAttended));
							}
						}
					}
				}
			}
			catch (FileNotFoundException e) 
			{ e.printStackTrace(); System.out.println("File not found."); }
		}
		else
			System.out.println("file not reconized as a valid file type.");
	}
	
	public void importSchools(String filePath)
	{
		if (filePath.contains(".s"))
		{
			File schoolFile = new File(filePath);
			
			String name 		= null;
			String schedule 	= null;
			String memberLeader = null;
			
			ArrayList<String> memberList = null;
			
			try 
			{
				Scanner scnr = new Scanner(schoolFile);
				
				while (scnr.hasNextLine())
				{
					String crtLine = scnr.nextLine();
					
					if (crtLine.contentEquals("{"))
					{
						while (!crtLine.contentEquals("}") && scnr.hasNextLine())
						{
							crtLine = scnr.nextLine();
							
							if (name 		 == null) 
								name 		 = LineTranscriber(crtLine, "name"		  , ':', 0);
							if (schedule	 == null)
								schedule 	 = LineTranscriber(crtLine, "schedule"	  , '"', 0);
							if (memberLeader == null)
								memberLeader = LineTranscriber(crtLine, "memberLeader", ':', 0);
							
							if (crtLine.contains("members"))
							{
								memberList = new ArrayList<String>();
								
								int start = crtLine.indexOf(':');
								
								String memberListSub = crtLine.substring(start + 2);
							
								Scanner scnrSub = new Scanner(memberListSub);
								
								scnrSub.useDelimiter("'");
								
								while (scnrSub.hasNext())
									memberList.add(scnrSub.next());
							}
						}
						
						System.out.println("crtl contains: " + crtLine);
						
						if (crtLine.contentEquals("}"))
						{
							if (!name.equals(null))
							{
									schools.add(new School(name, schedule, memberLeader, memberList));
							}
						}
					}
				}
			}
			catch (FileNotFoundException e) 
			{ e.printStackTrace(); }
		}
		else
			System.out.println("file not reconized as a valid file type.");
	}
	
	
	//Private
	private String LineTranscriber(String source, String key, char posStart, int offset)
	{
		if (source.contains(key))
			return source.substring((source.indexOf(posStart) + 1), source.length() + offset);
		else
			return null;
	}
	
	private Boolean LineTranscriber(String source, String key, char posStart)
	{
		if (source.contains(key))
			return Boolean.valueOf(source.substring((source.indexOf(posStart) + 1), source.length()));
		else
			return false;
	}
	
	
	ArrayList<Member> members = new ArrayList<>();
	ArrayList<School> schools = new ArrayList<>();
}