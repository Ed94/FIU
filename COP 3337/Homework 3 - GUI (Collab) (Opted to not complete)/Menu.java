import java.awt.event.ActionEvent	;
import java.awt.event.ActionListener;
import java.io.File					;
import java.io.FileNotFoundException;
import javax.swing.JFileChooser		;
import javax.swing.JMenu			;
import javax.swing.JMenuBar			;
import javax.swing.JMenuItem		;
import javax.swing.JPanel			;
import java.util.Scanner			;


public class Menu extends JPanel 
{
    public Menu() 
    {
        fileChooser = new JFileChooser();
        
        menu = new JMenuBar();
    }
    
    
    public class SaveFile implements ActionListener
    {
    	IO_Handler io_handler;
    	String 	   dataType  ;
    	
    	public SaveFile(IO_Handler io_handler, String dataType)
    	{ this.dataType = dataType; this.io_handler = io_handler; }
    	
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			int value = fileChooser.showSaveDialog(Menu.this);
			
			if (value == JFileChooser.APPROVE_OPTION)
			{
				if (dataType.equals("member"))
				{
					io_handler.exportMembers(fileChooser.getSelectedFile());
				}
				if (dataType.equals("schools"))
				{
					io_handler.exportSchools(fileChooser.getSelectedFile());
				}
			}
		}
    }
    
    public class OpenMemberFile implements ActionListener 
    {
    	IO_Handler io		; 
    	FrameMain  mainPlane;
    	
    	public OpenMemberFile(IO_Handler io, FrameMain mainPlane) 
    	{ this.io = io; this.mainPlane = mainPlane; }
    	
        public void actionPerformed(ActionEvent e) 
        {
            int val = fileChooser.showOpenDialog(Menu.this);
            
            if(val == JFileChooser.APPROVE_OPTION)
            {
            	File f = fileChooser.getSelectedFile();
            	
            	mainPlane.switching = true;
            	
            	mainPlane.memberModelList.clear();
            	
            	mainPlane.switching = false;
            	
            	io.importMembers(f.toString());

                System.out.println(f.toString());
                
                try 
                {
                    Scanner s = new Scanner(f);
                } 
                catch (FileNotFoundException e1) 
                {
                    e1.printStackTrace();
                }
                
                for (Member member : io.members)
            		mainPlane.memberModelList.addElement(member.name);
            }
            
            System.out.println("Selected: " + e.getActionCommand());
        }
    }
    
    public class OpenSchoolFile implements ActionListener 
    {
    	IO_Handler io		; 
    	FrameMain  mainPlane;
    	
    	public OpenSchoolFile(IO_Handler io, FrameMain mainPlane) 
    	{ this.io = io; this.mainPlane = mainPlane; }
    	
        public void actionPerformed(ActionEvent e) 
        {
            int val = fileChooser.showOpenDialog(Menu.this);
            
            if(val == JFileChooser.APPROVE_OPTION)
            {
            	File f = fileChooser.getSelectedFile();
            	
            	mainPlane.switching = true;
            	
            	mainPlane.schoolModelList.clear();
            	
            	mainPlane.switching = false;
            	
            	io.importSchools(f.toString());

                System.out.println(f.toString());
                
                try 
                {
                    Scanner s = new Scanner(f);
                } 
                catch (FileNotFoundException e1) 
                {
                    e1.printStackTrace();
                }
                
                for (School school : io.schools)
            		mainPlane.schoolModelList.addElement(school.name);
            }
            
            System.out.println("Selected: " + e.getActionCommand());
        }
    }

    public JMenuBar getMenuBar()
    { return menu; }
    
    public void generateSubMenu(IO_Handler io_handler, FrameMain mainPlane)
    {
    	subMenu = new JMenu("A Menu");
        
    	exportMembers = new JMenuItem("Export Members");
    	exportSchools = new JMenuItem("Export Schools");
    	importMembers = new JMenuItem("Import Members");
        importSchools = new JMenuItem("Import Schools");
        
        subMenu.add(exportMembers);
        subMenu.add(exportSchools);
        subMenu.add(importMembers);
        subMenu.add(importSchools);
        
        exportMembers.addActionListener(new SaveFile	  (io_handler, "member" ));
        exportSchools.addActionListener(new SaveFile	  (io_handler, "school" ));
        importMembers.addActionListener(new OpenMemberFile(io_handler, mainPlane));
        importSchools.addActionListener(new OpenSchoolFile(io_handler, mainPlane));
        
        menu.add(subMenu);
    }
    
    
    //Private
    JFileChooser fileChooser  ;
    JMenu 		 subMenu	  ;
    JMenuBar 	 menu		  ;
    JMenuItem	 exportMembers;
    JMenuItem	 exportSchools;
    JMenuItem	 importMembers;
    JMenuItem 	 importSchools;
}