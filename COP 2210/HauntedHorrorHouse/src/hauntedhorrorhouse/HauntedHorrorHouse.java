//******************************************************************************
//PANTHERID: 4999406, 5453835
//CLASS    : COP2210 -  FALL 2016
//ASSIGNMENT #4
//DATE: 10/31/2016
//
//Original Code from 5453835
//
// I hereby swear and affirm that this work is solely my own, and not the work
// or derivative of the work of someone else.
//******************************************************************************

package hauntedhorrorhouse;


import javax.swing.JOptionPane;

/**
 * <h1>Haunted Horror House</h1>
 * Where most of the program is handled. It controls all aspects of the haunted house.
 * 
 * @author 5453835
 * @author 4999406
 */
public class HauntedHorrorHouse 
{
    //Variables
    private String userName;
    
    private        boolean playerStatus;
    private static boolean floorCheck  ;

    /*Constructor*//**
     * The only constructor for the haunted horror house class.
     * It does not have any parameters. It runs the methods that
     * collect the player's name and runs the simulation method for the haunted house.
     * 
     * @author 4999406
     */
    public HauntedHorrorHouse()
    {
        setUserName        ();
        exploreHauntedHouse();
    }
    
    /*Methods*//**
     * Collects the player's name and sets them alive, as well as set their position to the first floor.
     * 
     * @author 4999406
     */
    private void setUserName()
    {
    
        userName = JOptionPane.showInputDialog("Welcome to this haunted horror house!!"+ "\n"
                                              +"A great adventure lies ahead of you."  + "\n"
                                              +"You must open this door by giving your name.");
        
        System.out.println("     ");
    
        playerStatus = true;
        floorCheck   = true;

    }
    /**
     * Displays a map within console for the player to observe their location
     * relative to the floor of the haunted house.
     * 
     * @author 4999406
     * @author 5453835
     * @param a Front Door  player position marker.
     * @param b Living Room  ^^
     * @param c Dining Room  ^^
     * @param d Kitchen      ^^
     * @param e Pantry       ^^
     * @param f Bath         ^^
     */
    private void houseMap(String a, String b,  String c, String d, String e, String f) //Handles the display of the map for the haunted House.
    {
        
        if(floorCheck == true) //Use map for floor one.
        {
            System.out.println("_____________________________"             + "\n"
                              +"|                 Front Door|"             + "\n"
                              +"|                       "+a+"   |"         + "\n"
                              +"|  Living Room |       |  S |"             + "\n"
                              +"|     "+b+"        |       |  t |"        + "\n"
                              +"|              |       |  a |"             + "\n"
                              +"|_________  ___|       |  i |"             + "\n"
                              +"|Pantry | Bath |       |  r |"             + "\n"
                              +"|__"+e+"__|__"+f+"___|_       _|__s_|"   + "\n"
                              +"|             |             |"             + "\n"
                              +"|             |             |"             + "\n"
                              +"|   Kitchen   | Dining Room |"             + "\n"
                              +"|    "+d+"              "+c+"       |"   + "\n"
                              +"|_____________|_____________|"                   );
        }  
        else //Use map for floor two.
        {
            System.out.println("_____________________________"            + "\n"
                              +"|              | Master Bath|"            + "\n"
                              +"|               _____"+e+"_______|"       + "\n"
                              +"|    Master    |       |  S |"            + "\n"
                              +"|   Bedroom    |       |  t |"            + "\n"
                              +"|              |       |  a |"            + "\n"
                              +"|     "+d+"         |       |  i |"       + "\n"
                              +"|              |       |  r |"            + "\n"
                              +"|__   _________|       |  s |"            + "\n"
                              +"|                        "+a+"  |"        + "\n"
                              +"|___   ____________   ______|"            + "\n"
                              +"| Bed 2 _____|_____   Bed 1 |"            + "\n"
                              +"|  "+c+"     |  Bath   |    "+b+"     |"  + "\n"
                              +"|_______________"+f+"____________|"       + "\n");
        }
    }
    /**
     * Handles the simulation of the haunted house game.
     * 
     * @author 4999406
     * @author 5453835
     */
    private void exploreHauntedHouse()
    {
        //Variables
        String decision;
        
        //Rooms
        //Floor1
        Room frontDoor        = new Room("Front Door"     , true );
        Room frontDoorReturn  = new Room("Front Door"     , false);
        Room livingRoom       = new Room("Living Room"    , false);
        Room bathroom         = new Room("Bathroom"       , false);
        Room diningRoom       = new Room("Dining Room"    , false);
        Room kitchen          = new Room("Kitchen"        , false);
        Room pantry           = new Room("Pantry"         , false);
        Room stairs           = new Room("Stairs"         , false);
        //Floor 2
        Room upstairs         = new Room("Stairs"         , false);
        Room bedOne           = new Room("First Bedroom"  , false);
        Room bedTwo           = new Room("Second Bedroom" , false);
        Room upstairsBathroom = new Room("Bathroom"       , false);
        Room masterBedroom    = new Room("Master Bedroom" , false);
        Room masterBathroom   = new Room("Master Bathroom", false);
        
        //Room Narrations
        //Floor 1
        frontDoor       .setNarration
                        (userName+ ", you are now inside of the house."                                                 + "\n"
                        +"Eerie noises fill your ears and a great smell of dust surrounds you."                         + "\n"
                        +userName+ ", do you want to enter the 'Living Room', the 'Dining Room', or go to the 'Stairs'?"+ "\n");
        
        frontDoorReturn .setNarration
                        (userName+", you are back at the front door."                                                        + "\n"
                        +"You hear the lock click open."                                                                     + "\n"
                        +"You are now free to go from this haunted horror house."                                            + "\n"
                        +"But, the spirits might follow you back."                                                           + "\n"
                        +"Do you want to 'Leave' the house, go into the 'Living Room', the 'Dining Room', or go to the 'Stairs'?"  );
        
        livingRoom      .setNarration
                        (userName+ ", you are now located in the living room."                                                       + "\n"
                        +"There are dusty furniture everwhere and shadows dancing around when you move your eyes."                   + "\n"
                        +userName+ ", do you want to move on to the 'Bathroom', explore the 'Chest', or go back to the 'Front Door'?"+ "\n");
        
        bathroom        .setNarration
                        (userName+ ", you are inside of the bathroom."                                                           + "\n"
                        +"The bathroom has a small window that is able to light up a portion of the bathroom."                   + "\n"
                        +"This allows you to be able to see in the mirror and the shower."                                       + "\n"
                        +userName+", do you want to look into the 'Mirror', check the 'Shower', or go back to the 'Living Room'?"+ "\n");
        
        diningRoom      .setNarration
                        (userName+ ", you are now located in the dining room."                                                                       + "\n"
                        +"There is a large ancient table in the middle of the room, with the table completely set."                                  + "\n"
                        +"Almost like its still waiting for the food and people to arrive to eat."                                                   + "\n"
                        +userName+ ", do you want to move on to the 'Kitchen', explore the 'Candelabra' on the table, or go back to the 'Front Door'?"+ "\n");
        
        kitchen         .setNarration
                        (userName+ ", you are now inside of the kitchen."                                                                                              + "\n"
                        +"The kitchen is very dusty and you can almost hear the spiders crawl across the floor."                                                       + "\n"
                        +"The floor creaks with every step you take."                                                                                                  + "\n"
                        +userName+ ", do you want to move to the 'Pantry', check out the 'Refrigerator', check inside the 'Cabinet', or go back to the 'Dining Room'?" + "\n");
        
        pantry          .setNarration
                        (userName+ ", you are located inside of the pantry."                                              + "\n"
                        +"The pantry is claustrophoblic and very dark. You want to leave as soon as possible."            + "\n"
                        +userName+ ", do you want to look at the 'Recipe Box', the 'Broom', or go back into the 'Kitchen'?"+ "\n");
        
        stairs          .setNarration
                        (userName+ ", you are now at the stairs."                              + "\n"
                        + "They are quite worn from all the times they have been stepped on."  + "\n"
                        +"Would you like to head back to the 'Front Door', or head up the 'Stairs'?"      );
       
        //Floor 2
        upstairs        .setNarration
                        (userName+ ", you headed upstairs to the second floor."                                                                             + "\n"
                        +"The stairs creaked under each one of your steps."                                                                                 + "\n"
                        +"Upstairs, there is so much to explore."                                                                                           + "\n"
                        +userName+ ", do you want to enter the 'First Bedroom', the 'Second Bedroom',the 'Master Bedroom', or head back to the 'Front Door?"+ "\n");
        
        bedOne          .setNarration
                        (userName+ ", you are now inside of the first bedroom."                                                                            + "\n"
                        +"Inside, it has a ghost like light coming from the window and spider webs everywhere."                                            + "\n"
                        +"It seems like it was probably the guest room for the house but now just abandoned."                                              + "\n"
                        +userName+ ", do you want to move to the 'Bathroom', look out the 'Window', check the 'Rocking Chair', or go back to the 'Stairs'?"+ "\n");
        
        bedTwo          .setNarration
                        (userName+ ", you are now inside of the second bedroom."                                                                              + "\n"
                        +"Inside, it looks like an abandoned kids room."                                                                                      + "\n"
                        +"They are broken and dusty toys scattered everywhere."                                                                               + "\n"
                        +userName+ ", do you want to move to the 'Bathroom', look at the 'Doll House', look inside the 'Dresser', or go back to the 'Stairs'?"+ "\n");
        
        upstairsBathroom.setNarration
                        (userName+ ", you are inside of the upstairs bathroom."                                                                          + "\n"
                        +"The bathroom looks eerie and moldy."                                                                                           + "\n"
                        +"The smell inside is putrid and completely surrounds you."                                                                      + "\n"
                        +userName+ ", do you want to check the 'Mirror', look inside the 'Shower', go to 'First Bedroom', or go into 'Second Bedroom 2'?"+ "\n");
        
        masterBedroom   .setNarration
                        (userName+ ", you are in the master bedroom on the second floor."                                                                       + "\n"
                        +"It has grand funiture everywhere."                                                                                                    + "\n"
                        +"It also feels like you are being watched with every move you make."                                                                   + "\n"
                        +userName+ ", do you want to move on to the 'Master Bathroom', look inside the 'Jewelry box', or go back to the 'Stairs'?"+ "\n");
        
        masterBathroom  .setNarration
                        (userName+ ", you are inside of the master bathroom."                                                        + "\n"
                        +"The master bathroom has marble designs on the inside."                                                     + "\n"
                        +"Webs get in your way as you walk inside."                                                                  + "\n"
                        +userName+ ", do you want to look at the 'Oil Lamp', check the 'Shower', or go back to the 'Master Bedroom'?"+ "\n");
              
        
        while (playerStatus == true)
        {
            
            if (floorCheck == true) //First floor's decision flow.
            { 
                //Each decision is put into a String to be able to later put into all of the if and else if statements.
                if (frontDoor.getState() == true)
                {
                    houseMap("o", " ", " ", " ", " ", " ");
                    
                    System.out.println("     ");
                    
                    decision = JOptionPane.showInputDialog(frontDoor.getNarration());
                    
                    if      (decision.equalsIgnoreCase(livingRoom.getName()))
                    {
                        livingRoom.setState(true );
                        frontDoor .setState(false);
                    }
                    else if (decision.equalsIgnoreCase(diningRoom.getName()))
                    {
                        diningRoom.setState(true );
                        frontDoor .setState(false);
                    }
                    else if (decision.equalsIgnoreCase(stairs.getName()))
                    {
                       stairs    .setState(true );
                       frontDoor .setState(false);
                    }
                    else
                    {
                        System.out.println("I couldn't understand what you wrote. Please enter a choice again.");
                    }
                }
                if (frontDoorReturn.getState() == true)
                {
                    houseMap("o"," "," "," "," "," ");
                    
                    System.out.println("     ");
                	
                    decision = JOptionPane.showInputDialog(frontDoorReturn.getNarration());
                    
                    if      (decision.equalsIgnoreCase(livingRoom.getName()))
                    {
                        livingRoom .setState(true );
                        frontDoor  .setState(false);
                    }
                    else if (decision.equalsIgnoreCase(diningRoom.getName()))
                    {
                        diningRoom.setState(true );
                        frontDoor .setState(false);
                    }
                    else if (decision.equalsIgnoreCase(stairs.getName()))
                    {
                        stairs   .setState(true );
                        frontDoor.setState(false);
                    }
                    else if(decision.equalsIgnoreCase("Leave"))
                    {
                        JOptionPane.showMessageDialog(null, userName + ", you have decided to leave the haunted horror house."+ "\n"
                                                            +"You turn the door knob on the front door."                      + "\n"
                                                            +"Opening the door to safety, you feel such relief."              + "\n"
                                                            +"You have excited the haunted house with your life still intact."+ "\n"
                                                            +"Have fun out in the darkness of the night."                     + "\n\n"
                                                            +"Game Over!"                                                     + "\n"
                                                            +"You have beat the game!!"                                               );
                        frontDoorReturn.setState(false);
                        playerStatus = false;
                    }
                    else
                    {
                        System.out.println("I couldn't understand what you wrote. Please enter a choice again.");
                    }
                }
                if (livingRoom.getState() == true)
                {
                    houseMap(" ", "o", " ", " ", " ", " ");
                    
                    System.out.println("     ");
                	
                    decision = JOptionPane.showInputDialog(livingRoom.getNarration());
                    

                    
                    if      (decision.equalsIgnoreCase(frontDoor.getName()))
                    {
                        livingRoom     .setState(false);
                        frontDoorReturn.setState(true );
                    }
                    else if (decision.equalsIgnoreCase(bathroom.getName()))
                    {
                        livingRoom.setState(false);
                        bathroom  .setState(true );
                    }
                    else if (decision.equalsIgnoreCase("Chest"))
                    {
                        JOptionPane.showMessageDialog(null, userName+ ", you have decided to explore the chest."                                      + "\n"
                                                            +"The chest is wooden with gold accents. You hope to find treasure in this haunted house."+ "\n"
                                                            +"You slowly creak open the chest when a great wind rushes by."                           + "\n"
                                                            +"A ghost escaped from the chest and passed through your body."                           + "\n"
                                                            +"You died in the living room."                                                           + "\n"
                                                            +"Game Over!"                                                                                   );
                        
                        playerStatus = false;
                    }
                    else
                    {
                        System.out.println("I couldn't understand what you wrote. Please enter a choice again.");
                    }
                }
                if (bathroom.getState() == true) 
                {
                    houseMap(" ", " ", " ", " ", " ", "o");
                    
                    System.out.println("     ");
                	
                    decision = JOptionPane.showInputDialog(bathroom.getNarration());
                         
                    if      (decision.equalsIgnoreCase(livingRoom.getName()))
                    {
                        livingRoom.setState(true );
                        bathroom  .setState(false);
                    }
                    else if (decision.equalsIgnoreCase("Mirror"))
                    {
                        JOptionPane.showMessageDialog
                        (null, userName+ ", you looked at yourself in the mirror."                                                                 + "\n"
                                       +"Your eyes start to cry blood and blood falls out of your nose until your entire face is covered in blood."+ "\n"
                                       +""                                                                                                               );
                    }
                    else if (decision.equalsIgnoreCase("Shower"))
                    {
                        JOptionPane.showMessageDialog
                        (null, userName+ ", you checked the shower."                                        + "\n"
                               +"You slowly move back the ratty old shower curtains."                       + "\n"
                               +"The bathroom steams up from an unknown force and you feel fingers touching"+ "\n"
                               +"the back of your neck, you feel like getting out."                               );
                    }
                    else
                    {
                        System.out.println("I couldn't understand what you wrote. Please enter a choice again.");
                    }
                }
                if (diningRoom.getState() == true) 
                {
                    houseMap(" ", " ", "o", " ", " ", " ");
                    
                    System.out.println("     ");
                    
                    decision = JOptionPane.showInputDialog(diningRoom.getNarration());
                    
                    if      (decision.equalsIgnoreCase(kitchen.getName()))
                    {
                        diningRoom.setState(false);
                        kitchen   .setState(true );
                    }
                    else if (decision.equalsIgnoreCase(frontDoor.getName()))
                    {
                        diningRoom     .setState(false);
                        frontDoorReturn.setState(true );
                    }
                    else if (decision.equalsIgnoreCase("candelabra"))
                    {
                        JOptionPane.showMessageDialog(null, userName+ ", you check out the candelabra in the middle of the table."    + "\n"
                                                            +"It lights up when you get near."                                        + "\n"
                                                            +"It shows a terrifying shadow in the corner that gets larger and larger."+ "\n"
                                                            +"It makes shivers run down your spine."                                        );
                    }
                    else
                    {
                        System.out.println("I couldn't understand what you wrote. Please enter a choice again.");
                    }
                }
                if (kitchen.getState() == true)
                {
                    houseMap(" ", " ", " ", "o", " ", " ");
                    
                    System.out.println("     ");
                	
                    decision = JOptionPane.showInputDialog(kitchen.getNarration());
                    
                    if(decision.equalsIgnoreCase(diningRoom.getName()))
                    {
                        kitchen   .setState(false);
                        diningRoom.setState(true );
                    }
                    else if(decision.equalsIgnoreCase(pantry.getName()))
                    {
                        kitchen.setState(false);
                        pantry .setState(true );
                    }
                    else if(decision.equalsIgnoreCase("Refrigerator"))
                    {
                        JOptionPane.showMessageDialog(null, userName+ ", you are going to check the refrigerator."                                                + "\n"
                                                            +"The fridge opens up the moment you get close to it."                                                + "\n"
                                                            +"Inside, there are appetizing food that just calls your name."                                       + "\n"
                                                            +"When you reach for your favorite all the way in the back, the fridge attempts to bite off your arm,"+ "\n"
                                                            +"but you pull it away just in time to save it."                                                            );
                    }
                    else if(decision.equalsIgnoreCase("Cabinet"))
                    {
                        JOptionPane.showMessageDialog(null, userName+ ", you decided to check inside the cabinets."                                        + "\n"
                                                            + "When you open up the first cabinet you see, all of the other ones open up at the same time."+ "\n"
                                                            +"Dishes and glasses start flying accross the kitchen and fall to the ground."                 + "\n"
                                                            +"One plate hits you right in the head, making you fall right on the floor."                   + "\n"
                                                            +"You died in the kitchen."                                                                    + "\n"
                                                            +"Game Over!"                                                                                        );
                        
                        playerStatus = false;
                    }
                    else
                    {
                        System.out.println("I couldn't understand what you wrote. Please enter a choice again.");
                    }
                }
                if (pantry.getState() == true)
                {
                    houseMap(" "," "," "," ","o"," ");
                    
                    System.out.println("     ");
                	
                    decision = JOptionPane.showInputDialog(pantry.getNarration());
                    
                    if      (decision.equalsIgnoreCase(kitchen.getName()))
                    {
                        pantry .setState(false);
                        kitchen.setState(true );
                    }
                    else if (decision.equalsIgnoreCase("Broom"))
                    {
                        JOptionPane.showMessageDialog(null, userName+ ", you have decided to check the broom in the pantry."        + "\n"
                                                            +"The moment you approach the broom it starts to vibrate."              + "\n"
                                                            +"When you get very near and reach out to it, it flies up into the air."+ "\n"
                                                            +""                                                                           );
                    }
                    else if (decision.equalsIgnoreCase("Recipe Box"))
                    {
                        JOptionPane.showMessageDialog(null, userName+ ", you have decided to look at the recipe box."                           + "\n"
                                                            + "The recipe box throws dust all over the floor when you pick it up."              + "\n"
                                                            + "You open the box to find a perfectly fine paper inside."                         + "\n"
                                                            + "The paper states all of the instructions to creat a chocolate devil's food cake."+ "\n"
                                                            + "The main ingredient is your soul."                                               + "\n"
                                                            + ""                                                                                      );
                    } 
                }
                if (stairs.getState() == true)
                {
                	houseMap("o", " ", " ", " ", " ", " ");
                	
                    System.out.println("     ");
                	
                    decision = JOptionPane.showInputDialog(stairs.getNarration());
                            
                    if      (decision.equalsIgnoreCase(upstairs.getName()))
                    {
                        upstairs.setState(true );
                        stairs  .setState(false);
                        
                        floorCheck = false;
                    }
                    else if (decision.equalsIgnoreCase(frontDoorReturn.getName()))
                    {
                        frontDoorReturn.setState(true );
                        stairs         .setState(false);
                    }
                    else
                    {
                        System.out.println("I couldn't understand what you wrote. Please enter a choice again.");
                    }
                }
            } 
            else if (floorCheck == false)
            {
                
                if(upstairs.getState() == true)
                {
                    houseMap("o"," "," "," "," "," ");
                    
                    System.out.println("     ");
                    
                    decision = JOptionPane.showInputDialog(upstairs.getNarration());
                   
                    if(decision.equalsIgnoreCase(bedOne.getName()))
                    {
                        bedOne.setState(true );
                        stairs.setState(false);  
                    }
                    else if(decision.equalsIgnoreCase(bedTwo.getName()))
                    {
                        bedTwo.setState(true );
                        stairs.setState(false);  
                    }
                    else if(decision.equalsIgnoreCase(masterBedroom.getName()))
                    {
                        masterBedroom.setState(true );
                        stairs       .setState(false);  
                    }
                    else if(decision.equalsIgnoreCase(frontDoor.getName()))
                    {
                        upstairs .setState(false);
                        frontDoor.setState(true ); 
                        
                        floorCheck = true;
                    }
                    else
                    {
                        System.out.println("I couldn't understand what you wrote. Please enter a choice again.");
                    }
                
                }
                if(bedOne.getState() == true)
                {
                	houseMap(" ","o"," "," "," "," ");
                    
                    System.out.println("     ");
                    
                    decision = JOptionPane.showInputDialog(bedOne.getNarration());
                  
                    if(decision.equalsIgnoreCase(frontDoor.getName()))
                    {
                        bedOne  .setState(false);
                        upstairs.setState(true ); 
                    }
                    else if(decision.equalsIgnoreCase(upstairsBathroom.getName()))
                    {
                        bedOne          .setState(false);
                        upstairsBathroom.setState(true );
                    }
                    else if(decision.equalsIgnoreCase("rocking chair"))
                    {
                        JOptionPane.showMessageDialog(null, userName+ ", you decided to check out the rocking chair."+ "\n"
                                                            +"The chair looks lonely in the corner of the room."     + "\n"
                                                            +"When you get near, it starts rocking by itself."       + "\n"
                                                            +"A ghostly figure appears on the chair."                + "\n"
                                                            +"It looks at you and screams."                          + "\n"
                                                            +""                                                            );
                    }
                    else if(decision.equalsIgnoreCase("window"))
                    {
                        JOptionPane.showMessageDialog(null, userName+ ", you looked out the window."                                             + "\n"
                                                            + "The window is blurry from all of the dust that has collected."                    + "\n"
                                                            +"Suddenly, it becomes clear that outside you see a little girl swinging on a swing."+ "\n"
                                                            +"The same little girl then quickly appears behind you and scares you."              + "\n"
                                                            +""                                                                                        );  
                    }
                    else
                    {
                        System.out.println("I couldn't understand what you wrote. Please enter a choice again.");
                    }
                }
                if(bedTwo.getState() == true)
                {
                	houseMap(" "," ","o"," "," "," ");
                    
                    System.out.println("     ");
                    
                    decision = JOptionPane.showInputDialog(bedTwo.getNarration());
                  
                    if(decision.equalsIgnoreCase(frontDoor.getName()))
                    {
                        bedTwo  .setState(false);
                        upstairs.setState(true );
                    }
                    else if(decision.equalsIgnoreCase(upstairsBathroom.getName()))
                    {
                        bedOne          .setState(false);
                        upstairsBathroom.setState(true );
                    }
                    else if(decision.equalsIgnoreCase("Doll House"))
                    {
                        JOptionPane.showMessageDialog(null, userName+ ", you decided to look at the doll house."+ "\n"
                                                            +"The doll house is small and has 2 dolls inside."  + "\n"
                                                            +"Both dolls had their heads ripped off."           + "\n"
                                                            +"Suddenly, both dolls begin to move on their own." + "\n"
                                                            +""                                                       );
                    }
                    else if(decision.equalsIgnoreCase("Dresser"))
                    {
                        JOptionPane.showMessageDialog(null, userName+ ", you decided to check inside the dresser."                                                          + "\n"
                                                            +"The dresser has a beautiful inticrate flower design on the outside,"                                          + "\n"
                                                            +"but the years have completely worn it out."                                                                   + "\n"
                                                            +"When you open the dresser, a ghostly figure flies out."                                                       + "\n"
                                                            +"It passes right through your body and makes you feel so cold that you fall on the ground and freeze to death."+ "\n"
                                                            +"You have died in the second bedroom."                                                                         + "\n"
                                                            +"Game Over!"                                                                                                         );
                        
                        playerStatus = false;
                    }
                    else
                    {
                        System.out.println("I couldn't understand what you wrote. Please enter a choice again.");
                    }
                }
                if(upstairsBathroom.getState() == true)
                {
                	houseMap(" "," "," "," "," ","o");
                    
                    System.out.println("     ");
                  
                    decision = JOptionPane.showInputDialog(upstairsBathroom.getNarration());

                    if(decision.equalsIgnoreCase(bedOne.getName()))
                    {
                        upstairsBathroom.setState(false);
                        bedOne          .setState(true ); 
                    }
                    else if(decision.equalsIgnoreCase(bedTwo.getName()))
                    {
                        upstairsBathroom.setState(false);
                        bedTwo          .setState(true );
                    }
                    else if(decision.equalsIgnoreCase("mirror"))
                    {
                        JOptionPane.showMessageDialog(null, userName+ ", you looked at yourself in the mirror."                    + "\n"
                                                            +"You see a reflection of a lady's face in the mirror."                + "\n"
                                                            +"Her face looks like its screaming and then the head falls right off."+ "\n"
                                                            +""                                                                          );
                    }
                    else if(decision.equalsIgnoreCase("shower"))
                    {
                        JOptionPane.showMessageDialog(null, userName+ ", you checked inside the shower."           + "\n"
                                                            + "You head towards the tub."                          + "\n"
                                                            + "The tub fills up with blood right before your eyes."+ "\n"
                                                            + "Hands come out of the water and try to grab you."   + "\n"
                                                            + ""                                                         );
                    }
                    else
                    {
                        System.out.println("I couldn't understand what you wrote. Please enter a choice again.");
                    }
                }
                if(masterBedroom.getState() == true)
                {
                    houseMap(" "," "," ","o"," "," ");
                    
                    System.out.println("     ");
                    
                    decision = JOptionPane.showInputDialog(masterBedroom.getNarration());
                  
                    if(decision.equalsIgnoreCase(frontDoor.getName()))
                    {
                        masterBedroom.setState(false);
                        upstairs     .setState(true ); 
                    }
                    else if(decision.equalsIgnoreCase(masterBathroom.getName()))
                    {
                        masterBedroom .setState(false);
                        masterBathroom.setState(true );
                    }
                    else if(decision.equalsIgnoreCase("jewelry box"))
                    {
                        JOptionPane.showMessageDialog(null, userName+ ", you have decided to look inside of the jewelry box."                                     + "\n"
                                                            +"The jewelry box is absolutely gorgeous and caught your attention the most."                         + "\n"
                                                            +"You feel a great presence inside."                                                                  + "\n"
                                                            +"When you open it, you see the rumored Hope Diamond necklace from the legends."                      + "\n"
                                                            +"You know that your death will come soon."                                                           + "\n"
                                                            +"A ghostly figure that has been watching you appears in front of you and lifts you up from the neck."+ "\n"
                                                            +"You died in the master bedroom."                                                                    + "\n"
                                                            +"Game Over!"                                                                                               ); 
                        playerStatus = false;
                    }
                    else
                    {
                        System.out.println("I couldn't understand what you wrote. Please enter a choice again.");
                    }
                }
                if(masterBathroom.getState()==true)
                {
                    houseMap(" "," "," "," ","o"," ");
                    
                    System.out.println("     ");
                    
                    decision = JOptionPane.showInputDialog(masterBathroom.getNarration());
                  
                    if(decision.equalsIgnoreCase(masterBedroom.getName()))
                    {
                        masterBathroom.setState(false);
                        masterBedroom .setState(true );
                    }
                    else if(decision.equalsIgnoreCase("oil lamp"))
                    {
                        JOptionPane.showMessageDialog(null, userName+ ", you decided to look at the oil lamp on the bathroom sink."                                  + "\n"
                                                            +"You decided to rub the lamp. A genie pops up and tells you to make a wish."                            + "\n"
                                                            +"You feel like you should avoid him and that he is lying to you."                                             );
                    }
                    else if(decision.equalsIgnoreCase("shower"))
                    {
                    JOptionPane.showMessageDialog(null, userName + ", you checked the shower in the master bathroom."+ "\n"
                                                        + "You get near the shower and it suddenly turns on."        + "\n"
                                                        + "Then, you hear humming coming from the shower."           + "\n"
                                                        + ""                                                               );
                    }
                    else
                    {
                        System.out.println("I couldn't understand what you wrote. Please enter a choice again.");
                    }
                }
            }
            else
            {
                System.out.println("You have created a singularity in time space. The entire haunted house has been destroyed.");
                 
                playerStatus = false;
            }
        }
    }
}