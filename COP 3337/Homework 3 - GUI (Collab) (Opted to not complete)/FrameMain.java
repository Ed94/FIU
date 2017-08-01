import java.awt.BorderLayout				  ;
import java.awt.event.ActionEvent			  ;
import java.awt.event.ActionListener		  ;
import java.awt.event.FocusAdapter			  ;
import java.awt.event.FocusEvent			  ;
import java.awt.GridLayout					  ;
import javax.swing.BorderFactory			  ;
import javax.swing.DefaultListModel			  ;
import javax.swing.JButton					  ;
import javax.swing.JList					  ;
import javax.swing.JPanel					  ;
import javax.swing.JScrollPane				  ;
import javax.swing.JTextField				  ;
import javax.swing.event.ListSelectionEvent	  ;
import javax.swing.event.ListSelectionListener;


public class FrameMain extends JPanel 
{
    //Public
    public FrameMain() 
    {
        setBorder(BorderFactory.createTitledBorder("Main"));
        
        setLayout(new BorderLayout());

        schoolModelList = new DefaultListModel();
        memberModelList	= new DefaultListModel();
        
        schoolList = new JList(schoolModelList);
        memberList = new JList(memberModelList);

        holding.setLayout(new GridLayout(0, 2));

        school.setBorder(BorderFactory.createTitledBorder("School"));//this is holding the list
        school.setLayout(new BorderLayout());

        member.setBorder(BorderFactory.createTitledBorder("Member"));//this is holding the list
        member.setLayout(new BorderLayout());

        school.add(new JScrollPane(schoolList), BorderLayout.CENTER);
        member.add(new JScrollPane(memberList), BorderLayout.CENTER);

        holding.add(school); //this is holding the panel which hold the list
        holding.add(member); //this is holding the panel which hold the list

        add(holding, BorderLayout.CENTER);
        
        JPanel holdingInput = new JPanel();
        
        holdingInput.add(deleteSelection);
        
        add(holdingInput, BorderLayout.SOUTH);
    }
    
    
    public void setupListeners(IO_Handler io_handler, FrameDetails detailsPlane)
    {
    	memberList.addFocusListener(new FocusAdapter()   //This will give us which list is currently selected.
    	{ 
    		@Override
    		public void focusGained(FocusEvent focusEvent) 
    		{
    			super.focusGained(focusEvent);
    			
    			focusedList = (JList)focusEvent.getSource();   //fix this
    		}
    	});
    	
    	schoolList.addFocusListener(new FocusAdapter() 
    	{
    		@Override
    		public void focusGained(FocusEvent focusEvent) 
    		{
    			super.focusGained(focusEvent);
    			
    			focusedList = (JList)focusEvent.getSource();
    		}
    	});
    	
    	memberList.addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) 
            {
                if (!switching) 
                {
                	String member = memberList.getSelectedValue().toString();
                    
                    for (Member temp : io_handler.members) 
                    	if (temp.getName().equals(member)) 
                        	detailsPlane.changeDetail(temp);
                }
            }
        });
    	
    	schoolList.addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) 
            {
            	if(!switching)
            	{
            		String school = schoolList.getSelectedValue().toString();
            		
            		for (School temp : io_handler.schools) 
            		{
            			if (temp.getName().equals(school)) 
            			{
            				detailsPlane.changeDetail(temp);
            			}
            		}
            	}
            }
        });
    	
    	deleteSelection.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent) 
            {
                if(focusedList.equals(memberList))
                {
                    int row = focusedList.getSelectedIndex();
                    
                    System.out.println("Member list is selected:" + row);
                }
                else if (focusedList.equals(schoolList))
                {
                    int row = focusedList.getSelectedIndex();
                    
                    System.out.println("School list is selected");
                }
            }
        });
    }    
    
    
    public boolean switching = false;
    
    public DefaultListModel memberModelList;   //School's Model
    public DefaultListModel schoolModelList;   //Member's Model
    
    
    //Private
    private int width = 10;
    
    JButton deleteSelection = new JButton("DELETE SELECTION");
    
    JList schoolList;
    JList memberList;
    
    JList focusedList = null;

    private JPanel school  = new JPanel();
    private JPanel member  = new JPanel();
    private JPanel holding = new JPanel();

    private JTextField schoolTXT = new JTextField(width);
    private JTextField memberTXT = new JTextField(width);
}
