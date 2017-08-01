import java.awt.BorderLayout  ;
import java.awt.GridLayout	  ;
import javax.swing.JFrame	  ;
import javax.swing.JPanel	  ;
import javax.swing.JScrollPane;
import java.util.ArrayList	  ;


public class GuiHandler 
{
	//Public
    public GuiHandler()
    {
        baseSetup			();
        initializeComponents();
    }
    
    
    public void baseSetup()
    {
    	handler = new JFrame();
        
        handler.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        handler.setSize					(1000, 600			 );
        handler.setLocationRelativeTo   (null				 );
        
        BorderLayout borderLayout = new BorderLayout();

        handler.setLayout(borderLayout);

        GridLayout gL = new GridLayout(1,2);
        
        basePanel = new JPanel();
        
        basePanel.setLayout(gL);
        
        handler.add(basePanel, "Center");
    }
    
    public void initializeComponents()
    {
    	io_handler = new IO_Handler();
    	
    	setupTestData();
    	
    	mainPlane    = new FrameMain   ();
        detailsPlane = new FrameDetails();
        menu 		 = new Menu		   ();

        handler.setJMenuBar(menu.getMenuBar());

        populateMembers();
        populateSchools();
        
        setupActionListeners();

        this.addPanel(mainPlane   );
        this.addPanel(detailsPlane);
        this.addPanel(menu     	  );
        
        menu     .generateSubMenu(io_handler, mainPlane);
        mainPlane.setVisible	 (true				   );
    }
    
    public void setupActionListeners()
    {
    	mainPlane	.setupListeners(io_handler, detailsPlane);
    	detailsPlane.setupListeners(io_handler, mainPlane	);
    }
    
    public void setupTestData()
    {
    	io_handler.addSchool(new School("BOIS"	   ));
    	io_handler.addMember(new Member("BOBBY JOE"));
    	io_handler.addMember(new Member("JOHN CENA"));
    }
    
    public void addPanel(JPanel panel)
    {
        basePanel.add	 (panel);
        basePanel.repaint	  ();
        
        handler.repaint();
    }
    
    public void addPane(JScrollPane pane)
    {
        basePanel.add	 (pane);
        basePanel.repaint	 ();
        
        handler.repaint();
    }
    
    public void makeVisible()
    { handler.setVisible(true); }

    public void print()
    { System.out.println("hello"); }
    
    public ArrayList<School> getSchools()
    { return io_handler.schools; }
    
    public void populateSchools()
    {
    	for (School school : io_handler.schools)
    		mainPlane.schoolModelList.addElement(school.name);
    } 
    
    public void populateMembers()
    {
    	for (Member member : io_handler.members)
    		mainPlane.memberModelList.addElement(member.name);
    }
    
    public void removeMember(String name)
    {
        for (School school : io_handler.schools) 
        	if (school.members.contains(name)) 
                school.members.remove(school.members.indexOf(name));
    }
    
    
    //Private
    JFrame handler  ;
    JPanel basePanel;
    
    IO_Handler	 io_handler  ;
    FrameMain 	 mainPlane   ;
    FrameDetails detailsPlane;
    Menu		 menu	   	 ;
}