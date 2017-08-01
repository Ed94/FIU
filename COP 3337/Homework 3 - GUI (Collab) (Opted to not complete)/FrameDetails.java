import java.awt.GridLayout			;
import java.awt.event.ActionEvent	;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory	;
import javax.swing.JButton			;
import javax.swing.JComboBox		;
import javax.swing.JLabel			;
import javax.swing.JPanel			;
import javax.swing.JTextField		;
import java.util.ArrayList			;


public class FrameDetails extends JPanel 
{
    public FrameDetails() 
    {
        setLayout(new GridLayout(11, 2));

        add(Name			 );
        add(nameInput		 );
        add(openTimes		 );
        add(openTimesInput   );
        add(startDate		 );
        add(startDateInput	 );
        add(endDate			 );
        add(endDateInput	 );
        add(School			 );
        add(schoolInput		 );
        add(lastAttended	 );
        add(lastAttendedInput);
        add(schedule		 );
        add(scheduleInput	 );
        add(Leader			 );
        add(LeaderInput		 );
        add(state			 );
        add(stateInput		 );
        add(members			 );
        add(memberList		 );
        add(update			 );

        setBorder(BorderFactory.createTitledBorder("Detail"));

        addValue();
        
        setVisible(true);
    }

    public void addValue() 
    {

    }

    public void changeDetail(Member member) 
    {
        currentMember = member;
        
        nameInput.setEditable(true			  );
        nameInput.setText	 (member.getName());
        
        openTimesInput.setEditable(true					);
        openTimesInput.setText	  (member.getOpenTimes());
        
        startDateInput.setEditable(true					);
        startDateInput.setText	  (member.getStartDate());
        
        endDateInput.setEditable(true				);
        endDateInput.setText	(member.getEndDate());
        
        schoolInput.setEditable(true			  );
        schoolInput.setText	   (member.getSchool());
        
        lastAttendedInput.setEditable(true					  );
        lastAttendedInput.setText	 (member.getLastAttended());
        
        scheduleInput.setEditable(true);
        
        LeaderInput.setEditable(true);
        
        stateInput.setEditable(true								);
        stateInput.setText	  (String.valueOf(member.getState()));

        whichType = "Member";
    }

    public void changeDetail(School school) 
    {
        CurrentSchool = school;
        
        nameInput.setText(school.getName());
        
        openTimesInput	 .setEditable(false);
        startDateInput	 .setEditable(false);
        endDateInput  	 .setEditable(false);
        schoolInput   	 .setEditable(false);
        lastAttendedInput.setEditable(false);
        scheduleInput	 .setEditable(true );
        LeaderInput		 .setEditable(true);
        stateInput		 .setEditable(false);
        
        scheduleInput.setText(school.getSchedule	());
        LeaderInput  .setText(school.getMemberLeader());
        
        switching = true;
        
        memberList.removeAllItems();

        for (String s : school.members) 
        	memberList.addItem(s);
        
        whichType = "School";
        switching = false   ;
    }
    
    public void setupListeners(IO_Handler io_handler, FrameMain mainPlane)
    {
    	memberList.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent) 
            {
                if (!mainPlane.switching) 
                {
                	System.out.println(memberList.getSelectedItem().toString());
                    
                    for (Member m : io_handler.members) 
                    {
                        if (m.getName().equals(memberList.getSelectedItem().toString()))
                        {
                            System.out.println("Item is in memberlist");
                            
                            changeDetail(m);
                        }
                    }
                } 
            }
        });
    	
    	update.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent) 
            {
                if (whichType.equals("School"))
                {
                    for (School s : io_handler.schools) 
                    {
                        if (s.getName().equals(CurrentSchool.getName())) 
                        {
                        	io_handler.schools.remove(CurrentSchool);

                        	mainPlane.switching = true;
                        	
                        	
                        	mainPlane.schoolModelList.removeElement(CurrentSchool.getName());
                        	mainPlane.schoolModelList.addElement   (nameInput	  .getText());
                        	
                        	CurrentSchool.setName		 (nameInput	   .getText());
                        	CurrentSchool.setMemberLeader(LeaderInput  .getText());
                        	CurrentSchool.setSchedule	 (scheduleInput.getText());
                        	
                          ArrayList<String> mems = new ArrayList<>();
                          
                          for(int i = 0; i < memberList.getItemCount(); i++)
                        	  mems.add(memberList.getItemAt(i).toString());
                          
                          CurrentSchool.setMembers(mems);
                          
                          io_handler.schools.add(CurrentSchool);
                          
                          mainPlane.switching = false;
                          
                          break;
                        }
                    }
                } 
                else 
                {
                    for (Member me : io_handler.members) 
                    {
                        if (me.getName().equals(currentMember.getName())) 
                        {
                            mainPlane.switching = true;
                            
                            mainPlane.memberModelList.removeElement(currentMember.getName());
                            
                            mainPlane.switching = false;
                            
                            currentMember = new Member(
                            		nameInput	     .getText(),
                                    openTimesInput	 .getText(),
                                    startDateInput	 .getText(),
                                    endDateInput  	 .getText(),
                                    schoolInput      .getText(),
                                    lastAttendedInput.getText(),
                                    
                                    Boolean.valueOf(stateInput.getText()));

                            io_handler.members.remove(currentMember);
                            io_handler.members.add	 (currentMember);
                            
                            mainPlane.switching = true;
                            
                            mainPlane.memberModelList.addElement(currentMember.getName());
                            
                            mainPlane.switching = false;
                            
                            break;
                        }
                    }
                }
            }
        });
    }
    
    
    //Private
    boolean switching = true;

    JLabel 	   Name 	 = new JLabel	 ("Name");
    JTextField nameInput = new JTextField(10	);

    JLabel openTimes		  = new JLabel	  ("Open Times:");
    JTextField openTimesInput = new JTextField(10			);

    JLabel	   startDate 	  = new JLabel	  ("Start Date:");
    JTextField startDateInput = new JTextField(10			);

    JLabel 	   endDate 		= new JLabel	("End Date:");
    JTextField endDateInput = new JTextField(10			);

    JLabel 	   School 	   = new JLabel	   ("School:");
    JTextField schoolInput = new JTextField(10		 );

    JLabel 	   lastAttended 	 = new JLabel	 ("Last Attended:");
    JTextField lastAttendedInput = new JTextField(10			  );

    JLabel 	   schedule		 = new JLabel	 ("School Schedule:");
    JTextField scheduleInput = new JTextField(10				);

    JLabel 	   Leader 	   = new JLabel	   ("Leader:");
    JTextField LeaderInput = new JTextField(10		 );

    JLabel members = new JLabel("Members:");
    
    JComboBox<String> memberList = new JComboBox<>();

    JLabel 	   state 	  = new JLabel	  ("State:");
    JTextField stateInput = new JTextField(10	   );

    JButton update = new JButton("UPDATE");

    String whichType;

    Member currentMember;
    School CurrentSchool;
}
