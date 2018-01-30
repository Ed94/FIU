package haunted;
 
 
import java.util.Scanner;

 
public class HauntedHouse //Meta class for program.
{
    //Variables
    private String decision;
   
    /* Decides what floor that is to be used within the scenario.
     * First floor  = true
     * Second floor = false
     * */
    private boolean floorCheck;
   
    //Constructors
    public HauntedHouse(String pName, boolean alive, Scanner userInput)
    {
        System.out.println("Player Info Check:"    );  // Checks to see if player info
        System.out.println("Name:  "  + pName+ "\n"    // transfered.
                          +"Alive? "  + alive      );
        System.out.println();

        
        scenario(pName, alive, userInput);  //Initiates the scenario.
    }
   
    //Methods
    private void scenario(String pName, boolean alive, Scanner userInput) //Where things really start to get going.
    {
        System.out.println("Scenario called: \n");
       
        LoadF1 floorOne = new LoadF1(pName); //Loads data for floor 1.
        LoadF2 floorTwo = new LoadF2(pName); //Loads data for floor 2.     
       
        floorCheck = true; //Player is initially sent to first floor.
       
        System.out.println("Floor is set to floorOne. \n");
       
        while (alive == true) //So long as the player is alive the scenario will continue to play out.
        {
            System.out.println("Checking player positon... \n");
           
            if (floorCheck == true)
            {
                System.out.println("Player is within floor 1.");
               
                alive = floorOne.flowF1(alive, userInput); //Goes to the decision flow for floor 1.
            }
            else
            {
                System.out.println("Player is within floor 2.");
                 
                alive = floorTwo.flowF2(alive, userInput); //Goes to the decision flow for floor 2.
            }
        }
    }
   
    //Sub-Classes
    private class LoadF1
    {
        //Rooms:
        Room hallway    = new Room("Hallway"    , true );
        Room livingRoom = new Room("Living Room", false);
        Room bathroom   = new Room("Bathroom"   , false);
        Room diningRoom = new Room("Dining Room", false);
        Room kitchen    = new Room("Kitchen"    , false);
        Room pantry     = new Room("Pantry"     , false);
        Room stairs     = new Room("Stairs"     , false);
       
        //Room Items:
        //Living Room
        Item chest = new Item("Chest"); //A wooden chest that has a ghost inside.
        //Bathroom
        Item mirror = new Item("Mirror"); //A bloody face looks back at the player.
        Item shower = new Item("Shower"); //The room will steam up and fingers will be felt down the players back of neck.
        //Dining Room
        Item candelabra = new Item("Candelabra"); //Some fixture in the middle of the dining room.
        //Kitchen
        Item cabinet       = new Item("Cabinet"      ); //Dishes and glasses fly at player and kill them.
        Item refridgerator = new Item("Refridgerator"); //Player opens and finds soul food.
        //Pantry   
        Item broom     = new Item("Broom"           ); //When player touches it, it flies into the air.
        Item dustyRBox = new Item("Dusty Recipe Box"); //Book will turn to recipe for chocolate devils food cake.
       
        public LoadF1(String pName) //Other stuff to setup before scenario.
        {
            System.out.println("Floor One objects Loaded.");
           
            //Room Narration
            hallway   .setNarration("So you have entered this house and are standing before it's hallway."+ "\n"
                                   +"Where would you like to go "+ pName+ "?"                             + "\n\n"
                                   +"Possible actions:"                                                   + "\n\n"
                                   +"Head over to:"                                                       + "\n"
                                   +livingRoom.getName()                                                  + "\n"
                                   +diningRoom.getName()                                                  + "\n"
                                   +stairs    .getName()                                                  + "\n");
           
            livingRoom.setNarration("Quite a cozy place I may say. If that is, you ignore the chilling temperature and dust..."+ "\n"
                                   +"Would you like to mess around or go explore somewhere else?"                              + "\n\n"
                                   +"Possible actions:"                                                                        + "\n\n"
                                   +"Head over to:"                                                                            + "\n"
                                   +hallway .getName()                                                                         + "\n"
                                   +bathroom.getName()                                                                         + "\n\n"
                                   +"Interact with:"                                                                           + "\n"
                                   +chest.getName()                                                                            + "\n");
           
            bathroom  .setNarration("Hmm... Has all the fear givien you the urge to use the restroom this quickly?"+ "\n"
                                   +"Well don't worry, I'll be turned away from your buissness..."                 + "\n\n"
                                   +"Possible actions:"                                                            + "\n\n"
                                   +"Head over to:"                                                                + "\n"
                                   +livingRoom.getName()                                                           + "\n\n"
                                   +"Interact with:"                                                               + "\n"
                                   +mirror.getName()                                                               + "\n"
                                   +shower.getName()                                                               + "\n");
           
            diningRoom.setNarration("Its quite a place to eat here... All the furnishings are made of wood or metal, and hand crafted. " + "\n"
                                   + "If only they were not giled with dust."                                                            + "\n"
                                   +"What would you like to do next?"                                                                    + "\n\n"
                                   +"Possible actions:"                                                                                  + "\n\n"
                                   +"Head over to:"                                                                                      + "\n"
                                   +hallway.getName()                                                                                    + "\n"
                                   +kitchen.getName()                                                                                    + "\n\n"
                                   +"Interact with"                                                                                      + "\n"
                                   +candelabra.getName()                                                                                 + "\n");
           
            kitchen   .setNarration("Ok, hungry aren't you? I'm not sure if there is anything here editable for a human like yourself." + "\n"
                                   +"I don't see a problem in looking around though..."                                                 + "\n\n"
                                   +"Possible actions:"                                                                                 + "\n\n"
                                   +"Head over to:"                                                                                     + "\n"
                                   +diningRoom.getName()                                                                                + "\n"
                                   +pantry    .getName()                                                                                + "\n\n"
                                   +"Interact with:"                                                                                    + "\n"
                                   +cabinet      .getName()                                                                             + "\n"
                                   +refridgerator.getName()                                                                             + "\n");
           
            pantry    .setNarration("Oh boy, and I thought the dining room was dusty... I think those physical bulges may just be rotten food, or severed heads."+ "\n"
                                   +"Are you in to those or something more boring?"                                                                              + "\n\n"
                                   +"Possible actions:"                                                                                                          + "\n\n"
                                   +"Head over to:"                                                                                                              + "\n"
                                   +kitchen.getName()                                                                                                            + "\n\n"
                                   +"Interact with:"                                                                                                             + "\n"
                                   +broom    .getName()                                                                                                          + "\n"
                                   +dustyRBox.getName()                                                                                                          + "\n");
           
            stairs    .setNarration("You have reached the stairs. They are quite worn from all the times they have been stepped on."                                   + "\n"
                                   +"Plus since they're made of wood they have been warped severely thanks to the humidity in the house from lack of air conditioning."+ "\n"
                                   +"Would you like to travel up to the next floor?"                                                                                   + "\n\n"
                                   +"Possible actions:"                                                                                                                + "\n\n"
                                   +hallway.getName()                                                                                                                  + "\n"
                                   +"Second Floor"                                                                                                                     + "\n");
           
            //Item Narration
            chest        .setNarration("You opened the chest. Oh my... a ghost came out of that chest and you seemed to perished from frieght.."                 );
            mirror       .setNarration("As you stare deeply into the mirror a bloody face stares back at  you."                                                  );
            shower       .setNarration("You enter the shower, without turning it on, the room becomes steamy. Then fingers start to touch the back of your neck.");
            candelabra   .setNarration("You reach over to light up the candelabra, but it lights it self. As you look around the lit room, you notice that it made a death shadow.");
            cabinet      .setNarration("Curious at the ancient glassware and porcalin within the cabinet. You appraoch it and open it.                                                \n"
                                      +"Seems that all those items had become quite energetic over the past few years as they have all flown at you as soon as the doors were opened. \n"
                                      +"Well it seems to many have hit you in the head... Your seeing light you say...                                                                  ");
            refridgerator.setNarration("So your hunger has led you to the fridge. You open it and find soul food.               \n"
                                      +"Well it looks quite nutrious but may not be exactly tastful for hu-                     \n"
                                      +"Um.. did you just eat them? Well I've never heard of this happening...                  \n"
                                      +"I guess possible side effects would be hearing the screams of those souls in your belly...");
            broom        .setNarration("You approach the broom and decide to touch it. Before you can get a grasp on it, the broom flies into the air. \n"
                                      +"It seems to out of your reach now."                                                                               );
            dustyRBox    .setNarration("You see a dusty recipe box and decide to open it out of curiosity. The pages automatically open to a recipe for chocolate devils fruit cake. \n"
                                      +"Even though I've never tried it, nothing with chocolate has even disapointed me."                                                              );
           
            System.out.println("Room and Item narrations created."); System.out.println();
           
        }
       
        private void mapF1(String a, String b,  String c, String d, String e, String f, String g)
        {
            System.out.println("=========================================================="                     + "\n"
                              +"|                                 |   F R O N T          |  Legend:"            + "\n"
                              +"|                                         D O O R        |"                     + "\n"
                              +"|                                        _               |  Player Location = *"+ "\n"
                              +"|                                       / \\   _          |"                    + "\n"
                              +"|       L I V I N G                    |"+a+"| |           |"                   + "\n"
                              +"|                                 |  H  \\_/  |           |"                    + "\n"
                              +"|         R O O M                 |          |           |"                     + "\n"
                              +"|                                 |  A       |        S  |"                     + "\n"
                              +"|                                 |          |           |"                     + "\n"
                              +"|                    _            |  L       |        T  |"                     + "\n"
                              +"|                   / \\           |          |           |"                    + "\n"
                              +"|                  |"+b+"|          |  L       |        A  |"                   + "\n"
                              +"|                   \\_/           |          |           |"                    + "\n"
                              +"|                                 |  W       |   _    I  |"                     + "\n"
                              +"|                                 |          |  / \\      |"                    + "\n"
                              +"|                                 |  A       | |"+g+"|  R  |"                   + "\n"
                              +"|--------------------      -------|          |  \\_/      |"                    + "\n"
                              +"|  P A N T R Y  |     / \\         |  Y       |        S  |"                    + "\n"
                              +"|        / \\    |    |"+c+"|        |          |           |"                  + "\n"
                              +"|       |"+f+"|   |     \\ /         |          |           |"                  + "\n"
                              +"|        \\ /    | B A T H R O O M |          |           |"                    + "\n"
                              +"|-      ----------------------------        -------------|"                     + "\n"
                              +"|                             |                          |"                     + "\n"
                              +"|                             |                          |"                     + "\n"
                              +"|                             |                          |"                     + "\n"
                              +"|                             |                          |"                     + "\n"
                              +"|                             |       D I N I N G        |"                     + "\n"
                              +"|                             |            _             |"                     + "\n"
                              +"|        K I T C H E N        |           / \\            |"                    + "\n"
                              +"|                             |          |"+d+"|           |"                   + "\n"
                              +"|              _              |           \\_/            |"                    + "\n"
                              +"|             / \\             |                          |"                    + "\n"
                              +"|            |"+e+"|            |         R O O M          |"                   + "\n"
                              +"|             \\_/             |                          |"                    + "\n"
                              +"|                             |                          |"                     + "\n"
                              +"|                             |                          |"                     + "\n"
                              +"=========================================================="                     + "\n");
           
        }
       
        public boolean flowF1(boolean alive, Scanner userInput)  //Decision flow for floor one.
        {
            System.out.println("Proceeding to floor 1. \n");
           
            //Floor tree
            if (hallway.getActive() == true)  //Hallway's options
            {  
               
                System.out.println("---------------------- At the hallway ---------------------------------------------------- \n");
               
                mapF1(" * ", "   ", "   ", "   ", "   ", "   ", "   ");
               
                System.out.println(hallway.getNarration()+ "\n");
               
                decision = userInput.nextLine();
               
                hallway.setNarration("Back at entrance hall I see... Getting to scared?"              + "\n"
                                    +"Too bad. The haunted house never ends. ^_^"                     + "\n\n"
                                    +"Possible actions:"                                              + "\n\n"
                                    +"Head over to:"                                                  + "\n"
                                    +"Living Room"                                                    + "\n"
                                    +"Dining Room"                                                    + "\n"
                                    +"Stairs"                                                         + "\n");
               
                //Possible routes for the player.
                if (decision.equalsIgnoreCase(livingRoom.getName()))  //When player decides to head to living room.
                {
                    System.out.println("Heading to living room. \n");
                   
                    livingRoom.setActive(true );   
                    hallway   .setActive(false);
                }
                else if (decision.equalsIgnoreCase(diningRoom.getName())) //When player decides to head to dining room.
                {
                    System.out.println("Heading to dining room \n");
                   
                    diningRoom.setActive(true );
                    hallway   .setActive(false);
                }
                else if (decision.equalsIgnoreCase(stairs.getName())) //When player decides to head to the stairs.
                {
                    System.out.println("Heading to stairs. \n");
                   
                    hallway.setActive(false);
                    stairs .setActive(true );
                }
                else //When player gives incorrect input.
                {
                    System.out.println("You didn't give me something I can work with. Try entering one of your options again. \n");
                }
               
            }
            else if (livingRoom.getActive() == true) //Living room's options.
            {
               
                System.out.println("---------------------- At the living room ------------------------------------------------ \n");
               
                mapF1("   ", " * ", "   ", "   ", "   ", "   ", "   ");
               
                System.out.println(livingRoom.getNarration()+ "\n");
               
                decision = userInput.nextLine();
               
                //Possible routes for player
                if (decision.equalsIgnoreCase(chest.getName()))  //Player wanted to interact with chest.
                {
                    System.out.println("Interacting with chest    \n");
                    System.out.println(chest.getNarration()    + "\n");
                   
                    alive = false;
                   
                    livingRoom.setActive(false);
                }
                else if (decision.equalsIgnoreCase(hallway.getName())) //Player wanted to go back to the hallway.
                {
                    System.out.println("Heading to the hallway. \n");
                    
                   
                    hallway   .setActive(true );
                    livingRoom.setActive(false);
                }
                else if (decision.equalsIgnoreCase(bathroom.getName())) //Player wanted to head to the bathroom.
                {
                    System.out.println("Heading to the bathroom. \n");
                   
                    bathroom  .setActive(true );
                    livingRoom.setActive(false);
                }
                else //When player gives incorrect input.
                {
                    System.out.println("You didn't give me something I can work with. Try entering one of your options again. \n");
                }
               
            }
            else if (bathroom.getActive() == true) //Bathroom options.
            {
               
                System.out.println("---------------------- At the bathroom ------------------------------------------------- \n");
               
                mapF1("   ", "   ", " * ", "   ", "   ", "   ", "   ");
               
                System.out.println(bathroom.getNarration()+ "\n");
               
                decision = userInput.nextLine();
               
                if (decision.equalsIgnoreCase(livingRoom.getName())) //Player wanted to go back to the living room.
                {
                    livingRoom.setActive(true );
                    bathroom  .setActive(false);
                }
                else if (decision.equalsIgnoreCase(mirror.getName())) //Player wanted to interact with the mirror.
                {
                    System.out.println("Interacting wth mirror. \n");
                    System.out.println(mirror.getNarration()+  "\n");
                }
                else if (decision.equalsIgnoreCase(shower.getName())) //Player wanted to interact with the shower.
                {
                    System.out.println("Interacting with shower. \n");
                    System.out.println(shower.getNarration()+   "\n");
                }
                else //When player gives incorrect input.
                {
                    System.out.println("You didn't give me something I can work with. Try entering one of your options again. \n");
                }
               
            }          
            else if (diningRoom.getActive() == true)//Dining room options.
            {
               
                System.out.println("---------------------- At the dining room ------------------------------------------------ \n");
               
                mapF1("   ", "   ", "   ", " * ", "   ", "   ", "   ");
               
                System.out.println(diningRoom.getNarration()+ "\n");
               
                decision = userInput.nextLine();
               
                if (decision.equalsIgnoreCase(candelabra.getName())) //Player wanted to interact with the candle thing.
                {
                    System.out.println("Interacting with candelabra \n");
                    System.out.println(candelabra.getNarration()+  "\n");
                }
                else if (decision.equalsIgnoreCase(kitchen.getName())) //Player wanted to head over to the kitchen.
                {
                    System.out.println("Heading to kitchen. \n");
                   
                    kitchen   .setActive(true );
                    diningRoom.setActive(false);
                }
                else if (decision.equalsIgnoreCase(hallway.getName())) //Player wanted to head over to the hallway.
                {
                    System.out.println("Heading to hallway \n");
                   
                    hallway   .setActive(true );
                    diningRoom.setActive(false);
                }
                else //When player gives incorrect input.
                {
                    System.out.println("You didn't give me something I can work with. Try entering one of your options again. \n");
                }
               
            }
            else if (kitchen.getActive() == true) //Kitchen options.
            {
               
                System.out.println("---------------------- At the kitchen ------------------------------------------------ \n");
               
                mapF1("   ", "   ", "   ", "   ", " * ", "   ", "   ");
               
                System.out.println(kitchen.getNarration()+ "\n");
               
                decision = userInput.nextLine();
               
                if (decision.equalsIgnoreCase(cabinet.getName())) //Player wanted to interact with the cabinet.
                {
                    System.out.println("Interacting with cabinet. \n");
                    System.out.println(cabinet.getNarration()+   "\n");
                   
                    alive = false;
                }
                else if (decision.equalsIgnoreCase(refridgerator.getName())) //Player wanted to interact with the refrigerator.
                {
                    System.out.println("Interacting with refridgerator. \n");
                    System.out.println(refridgerator.getNarration()+   "\n");
                }
                else if (decision.equalsIgnoreCase(diningRoom.getName())) //Player wanted to head over to the dining room.
                {
                    System.out.println("Heading to dining room. \n");
                   
                    diningRoom.setActive(true );
                    kitchen   .setActive(false);
                }
                else if (decision.equalsIgnoreCase(pantry.getName())) //Player wanted to head over to the pantry.
                {
                    System.out.println("Heading to pantry \n");
                   
                    pantry .setActive(true );
                    kitchen.setActive(false);
                }
                else //When player gives incorrect input.
                {
                    System.out.println("You didn't give me something I can work with. Try entering one of your options again. \n");
                }
               
            }
            else if (pantry.getActive() == true) //Pantry options.
            {
               
                System.out.println("---------------------- At the pantry -------------------------------------------------- \n");
               
                mapF1("   ", "   ", "   ", "   ", "   ", " * ", "   ");
               
                System.out.println(pantry.getNarration()+ "\n");
               
                decision = userInput.nextLine();
               
                if (decision.equalsIgnoreCase(broom.getName())) //Player intends to interact with broom.
                {
                    System.out.println(broom.getNarration()+ "\n");
                }
                else if (decision.equalsIgnoreCase(dustyRBox.getName())) //Player intends to interact with box.
                {
                    System.out.println(dustyRBox.getNarration()+ "\n");
                }
                else if (decision.equalsIgnoreCase(kitchen.getName())) //Player wants to head back to the kitchen.
                {
                    System.out.println("Heading to kitchen. \n");
                   
                    kitchen.setActive(true );
                    pantry .setActive(false);
                }
                else //When player gives incorrect input.
                {
                    System.out.println("You didn't give me something I can work with. Try entering one of your options again. \n");
                }
               
            }
            else if (stairs.getActive() == true) //Stair Options.
            {
               
                System.out.println("---------------------- At the stairs ------------------------------------------------ \n");
               
                mapF1("   ", "   ", "   ", "   ", "   ", "   ", " * ");
               
                System.out.println(stairs.getNarration()+ "\n");
               
                decision = userInput.nextLine();
               
                if (decision.equalsIgnoreCase("Second Floor")) //Player wanted to head to second floor.
                {
                    System.out.println("Heading upstairs.");
                   
                    floorCheck = false;
                   
                    stairs.setActive(true);
                }
                else if (decision.equalsIgnoreCase(hallway.getName())) //Player wanted ot head over to the hallway.
                {
                    System.out.println("Heading to hallway. \n");
                   
                    hallway.setActive(true);
                    stairs.setActive(false);
                }
                else //When player gives incorrect input.
                {
                    System.out.println("You didn't give me something I can work with. Try entering one of your options again. \n");
                }
               
            }
            else
            {
               
                System.out.println("Why are you here? The player has caused a distrubance in the game's space-time fabric.");
                System.out.println("A singularity has been born. The resulting mini black hole has destoryed everything in this game's universe, including the player.");
               
                alive = false;
               
            }
           
            System.out.println("Finished a sweep of floor 1 decision flow. \n");
           
            return alive;
        }
       
    }
   
    private class LoadF2
    {
        //Rooms
        Room stairs     = new Room("Stairs"         , true );
        Room bedroomOne = new Room("Bedroom One"    , false);
        Room bedroomTwo = new Room("Bedroom Two"    , false);
        Room masterBed  = new Room("Master Bedroom" , false);
        Room masterBath = new Room("Master Bathroom", false);  
        Room bathroom   = new Room("Bathroom"       , false);
       
        //Room Items:
        //Bedroom One
        Item rockingChair = new Item("Rocking Chair");
        Item window       = new Item("Window"       );
        //Bedroom Two
        Item dollHouse = new Item("Doll House");
        Item dresser   = new Item("Dresser"   );
        //Master Bedroom
        Item jewelryBox = new Item("Jewelry Box");
        //Master Bathroom
        Item oilLamp = new Item("Intricate Oil Lamp");
        Item mShower = new Item("Shower"            );
        //Bathroom
        Item mirror = new Item("Mirror");
        Item shower = new Item("Shower");
       
        public LoadF2(String pName)
        {
            System.out.println("Floor Two objects loaded.");
           
            //Room Narration
            stairs    .setNarration("You've reached the second floor and somehow avoided death!"                                                    + "\n"
                                   +"There are plenty of more rooms to explore up here so don't be scared now, you may live for another momment!"   + "\n\n"
                                   +"Possible actions:"                                                                                             + "\n\n"
                                   +"Head over to:"                                                                                                 + "\n"
                                   +masterBed .getName()                                                                                            + "\n"
                                   +bedroomOne.getName()                                                                                            + "\n"
                                   +bedroomTwo.getName()                                                                                            + "\n"
                                   +"First Floor"                                                                                                   + "\n");
           
            bedroomOne.setNarration("Here we are at the first bedroom of the second floor. Seems to be a rocking chair near a window. Maybe this was a place for an old man to reminisce... "+ "\n"
                                   +"Or watch their future victims."                                                                                                                         + "\n\n"
                                   +"Possible actions:"                                                                                                                                      + "\n\n"
                                   +"Head over to:"                                                                                                                                          + "\n"
                                   +bathroom.getName()                                                                                                                                       + "\n"
                                   +stairs  .getName()                                                                                                                                       + "\n\n"
                                   +"Interact with:"                                                                                                                                         + "\n"
                                   +rockingChair.getName()                                                                                                                                   + "\n"
                                   +window      .getName()                                                                                                                                   + "\n");
           
            bedroomTwo.setNarration("Its an almost fully pink room. There is a doll house and a dresser here."  + "\n\n"
                                   +"Possbile actions:"                                                         + "\n\n"
                                   +"Head over to:"                                                             + "\n"
                                   +bathroom.getName()                                                          + "\n"
                                   +stairs  .getName()                                                          + "\n\n"
                                   +"Interact with:"                                                            + "\n"
                                   +dollHouse.getName()                                                         + "\n"
                                   +dresser  .getName()                                                         + "\n");
           
            masterBed .setNarration("This is the finest bedroom in the entire house. "                                                                              + "\n"
                                   +"Unfortunately all the windows are boarded up, the mattress is missing, and the only shiny thing in the room is a jewelry box." + "\n\n"
                                   +"Possible actions:"                                                                                                             + "\n\n"
                                   +masterBath.getName()                                                                                                            + "\n"
                                   +stairs.getName()                                                                                                                + "\n\n"
                                   +"Interact with:"                                                                                                                + "\n"
                                   +jewelryBox.getName()                                                                                                            + "\n");
           
            masterBath.setNarration("Unlike the master bedroom, the bathroom is still in decent condition. All the metal is gilded in gold. Its quite spectacular." + "\n\n"
                                   +"Possile actions:"                                                                                                              + "\n\n"
                                   +"Head over to:"                                                                                                                 + "\n"
                                   +masterBed.getName()                                                                                                             + "\n"
                                   +"Interact with:"                                                                                                                + "\n\n"
                                   +oilLamp.getName()                                                                                                               + "\n"
                                   +mShower.getName()                                                                                                               + "\n");
           
            bathroom  .setNarration("Your now inside the second floors bathroom. Did you need to use it after all the fear you've felt exploring this house?"   + "\n\n"
                                   +"Possible actions:"                                                                                                         + "\n\n"
                                   +"Head over to:"                                                                                                             + "\n"
                                   +bedroomOne.getName()                                                                                                        + "\n"
                                   +bedroomTwo.getName()                                                                                                        + "\n\n"
                                   +"Interact with:"                                                                                                            + "\n"
                                   +mirror.getName()                                                                                                            + "\n"
                                   +shower.getName()                                                                                                            + "\n");
                                   
            //Room Items
            rockingChair.setNarration("You approach the chair only to see it rock by itself. Seems the chair misses being rocked by someone.");
            window      .setNarration("As you gaze out the window. You see a child on a swing having fun. Then they suddenly disappear. I wonder where they went?");
            dollHouse   .setNarration("You decide to have fun dolls inside the doll house. Before you can open it, the dolls stand up and begin to dance on their own.");
            dresser     .setNarration("You decide to open to the dresser to see if there is anything insteresting.  \n"
                                     +"As soon as you open a drawer, a ghost flies out of it and through your body. \n"
                                     +"That must have felt weird. I wonder if it wanted anything.                     ");
            jewelryBox  .setNarration("You open the jewelry box and find only one object inside.                                        \n"
                                     +"There is a tag that states its a hope diamond, just being in its presence because you to feel doom.");
            oilLamp     .setNarration("You rub the lamp and and smoke flies out. Suddenly a genie forms and state that he will grant three wishes.  \n"
                                     +"But he cannot grant escape from the haunted house.                                                           \n"
                                     + "Oh you finished your wishes already? Ok moving on..."                                                          );
            mShower     .setNarration("You approach the shower and hear someone singing. You decide to open the shower curtian to find no one there.\n"
                                     +"Someone is still singing though. Maybe they like you."                                                          );
            mirror      .setNarration("As you gaze into the mirror you see a bloody version version of yourself looking back.");
            shower      .setNarration("You head into the shower and without needing to touch anything the room steams up. Suddenly you feel fingers touch the back of your neck.");
           
            System.out.println("Room and Item narrations created. \n");
           
        }
       
        private void mapF2(String a, String b,  String c, String d, String e, String f)
        {
            System.out.println("=========================================================="                         + "\n"
                              +"|                                    |                   |  Legend:"                + "\n"
                              +"|                                         M A S T E R    |"                         + "\n"
                              +"|                                             _          |  Player Location = *"    + "\n"
                              +"|                                            / \\         |"                        + "\n"
                              +"|        M A S T E R                        |"+e+"|        |"                       + "\n"
                              +"|                                            \\_/         |"                        + "\n"
                              +"|                                                        |"                         + "\n"
                              +"|               _                    |      B A T H      |"                         + "\n"
                              +"|              / \\                   |                   |"                        + "\n"
                              +"|             |"+d+"|                  |___________________|"                       + "\n"
                              +"|              \\_/                   |         |         |"                        + "\n"
                              +"|                                    |         |       S |"                         + "\n"
                              +"|                                    |         |         |"                         + "\n"
                              +"|            B E D R O O M           |         |   _   T |"                         + "\n"
                              +"|                                    |         |  / \\    |"                        + "\n"
                              +"|                                    |         | |"+a+"| A |"                       + "\n"
                              +"|                                    |         |  \\_/    |"                        + "\n"
                              +"|                                    |         |       I |"                         + "\n"
                              +"|                                    |         |         |"                         + "\n"
                              +"|_____________            ___________|         |       R |"                         + "\n"
                              +"|                                              |         |"                         + "\n"
                              +"|                                              |_      S_|"                         + "\n"
                              +"|                                                        |"                         + "\n"
                              +"|                                                        |"                         + "\n"
                              +"|___________________          ___          ______________|"                         + "\n"
                              +"|                              |                         |"                         + "\n"
                              +"|   B E D R O O M              |     B E D R O O M       |"                         + "\n"
                              +"|        _                     |            _            |"                         + "\n"
                              +"|       / \\                    |           / \\           |"                       + "\n"
                              +"|      |"+c+"|                   |          |"+b+"|          |"                     + "\n"
                              +"|       \\_/                    |           \\_/           |"                       + "\n"
                              +"|                     _________|___________    O N E     |"                         + "\n"
                              +"|                     |       / \\         |              |"                        + "\n"
                              +"|      T W O                 |"+f+"|                       |"                       + "\n"
                              +"|                             \\_/                        |"                        + "\n"
                              +"|                     |  B A T H R O O M  |              |"                         + "\n"
                              +"=========================================================="                         + "\n");
           
        }
       
        public boolean flowF2(boolean alive, Scanner userInput) //Decision flow for floor 2.
        {
            System.out.println("Proceding to floor 2. \n");
           
            //Floor Tree   
            if (stairs.getActive() == true) //Stairs options.
            {
               
                System.out.println("---------------------- At the stairs ------------------------------------------------ \n");
               
                mapF2(" * ", "   ", "   ", "   ", "   ", "   ");
               
                System.out.println(stairs.getNarration()+ "\n");
               
                stairs.setNarration("Your back at the stairs. Go ahead and go back down but there is no leaving."+ "\n\n"
                                   +"Possible actions:"                                                          + "\n\n"
                                   +"Head over to:"                                                              + "\n"
                                   +masterBed .getName()                                                         + "\n"
                                   +bedroomOne.getName()                                                         + "\n"
                                   +bedroomTwo.getName()                                                         + "\n"
                                   +"First Floor");
               
                decision = userInput.nextLine();
               
                //Possible routes for player.
                if (decision.equalsIgnoreCase(bedroomOne.getName())) //Player wants to head to the first bedroom.
                {
                    System.out.println("Heading to first bedroom. \n");
                   
                    bedroomOne.setActive(true );
                    stairs    .setActive(false);
                }
                else if (decision.equalsIgnoreCase(bedroomTwo.getName())) //Player wants to head to the second bedroom.
                {
                    System.out.println("Heading to second bedroom. \n");
                   
                    bedroomTwo.setActive(true );
                    stairs    .setActive(false);
                }
                else if (decision.equalsIgnoreCase("Master Bedroom")) //Player wants to head to the master bedroom.
                {
                    System.out.println("At Master Bedroom. \n");
                   
                    masterBed.setActive(true );
                    stairs   .setActive(false);
                }
                else if (decision.equalsIgnoreCase("First Floor")) //Player wants to head back to the first floor.
                {
                    System.out.println("Heading downstairs. \n");
                   
                    floorCheck = true;
                }
                else //When player gives incorrect input.
                {
                    System.out.println("You didn't give me something I can work with. Try entering one of your options again. \n");
                }
               
            }
            else if (bedroomOne.getActive() == true) //First bedroom's options.
            {
               
                System.out.println("---------------------- At the first bedroom ------------------------------------------------ \n");
               
                mapF2("   ", " * ", "   ", "   ", "   ", "   ");
               
                System.out.println(bedroomOne.getNarration()+ "\n");
               
                decision = userInput.nextLine();
               
                //Possible routes for player.
                if (decision.equalsIgnoreCase(rockingChair.getName())) //Player wants to interact with the rocking chair.
                {
                    System.out.println("Interacting with rocking chair. \n");
                    System.out.println(rockingChair.getNarration()+    "\n");
                }
                else if (decision.equalsIgnoreCase(window.getName())) //Player wants to interact with the window.
                {
                    System.out.println("Interacting with window. \n");
                    System.out.println(window.getNarration()+   "\n");
                }
                else if (decision.equalsIgnoreCase(bathroom.getName())) //Player wants to head over to the bathroom.
                {
                    System.out.println("Heading over to the bathroom. \n");
                   
                    bathroom  .setActive(true );
                    bedroomOne.setActive(false);
                }
                else if (decision.equalsIgnoreCase(stairs.getName())) //Player wants to head over to the stairs.
                {
                    System.out.println("Heading over to stairs. \n");
                   
                    stairs    .setActive(true );
                    bedroomOne.setActive(false);
                }
                else //When player gives incorrect input.
                {
                    System.out.println("You didn't give me something I can work with. Try entering one of your options again. \n");
                }
               
            }
            else if (bedroomTwo.getActive() == true) //Second bedroom's options.
            {
               
                System.out.println("---------------------- At the second bedroom ------------------------------------------------ \n");
               
                mapF2("   ", "   ", " * ", "   ", "   ", "   ");
               
                System.out.println(bedroomTwo.getNarration()+ "\n");
               
                decision = userInput.nextLine();
               
                //Possible routes for player
                if (decision.equalsIgnoreCase(dollHouse.getName())) //Player wanted to interact with the doll house.
                {
                    System.out.println("Interacting with doll house \n");
                    System.out.println(dollHouse.getNarration()+   "\n");
                }
                else if (decision.equalsIgnoreCase(dresser.getName())) //Player wanted to interact with the dresser.
                {
                    System.out.println("Interacting with dresser. \n");
                    System.out.println(dresser.getNarration()+ 	 "\n");
                    
                    alive = false;
                }
                else if (decision.equalsIgnoreCase(bathroom.getName())) //Player wanted to head over to the bathroom.
                {
                    System.out.println("Heading over to the bathroom. \n");
                   
                    bathroom  .setActive(true );
                    bedroomTwo.setActive(false);
                }
                else if (decision.equalsIgnoreCase(stairs.getName())) //Player wanted to head over to the stairs.
                {
                    System.out.println("Heading over to stairs. \n");
                    
                    stairs    .setActive(true );
                    bedroomTwo.setActive(false);
                }
                else //When player gives incorrect input.
                {
                    System.out.println("You didn't give me something I can work with. Try entering one of your options again. \n");
                }
               
            }
            else if (masterBed.getActive() == true) //Master bedroom options.
            {
               
                System.out.println("---------------------- At the master bedroom ------------------------------------------------ \n");
               
                mapF2("   ", "   ", "   ", " * ", "   ", "   ");
               
                System.out.println(masterBed.getNarration()+ "\n");
               
                decision = userInput.nextLine();
               
                //Possible routes for player
                if (decision.equalsIgnoreCase(jewelryBox.getName())) //Player wants to interact with jewelry box.
                {
                    System.out.println("Interacting with jewelry box.  \n");
                    System.out.println(jewelryBox.getNarration()+     "\n");
                    
                    alive = false;
                }
                else if (decision.equalsIgnoreCase(masterBath.getName())) //Player wants to head over to master bathroom.
                {
                    System.out.println("Heading over to master bathroom. \n");
                   
                    masterBath.setActive(true );
                    masterBed .setActive(false);
                }
                else if (decision.equalsIgnoreCase(stairs.getName())) //Player wants to head over to stairs.
                {
                    System.out.println("Heading over to stairs. \n");
                   
                    stairs   .setActive(true );
                    masterBed.setActive(false);
                }
                else //When player gives incorrect input.
                {
                    System.out.println("You didn't give me something I can work with. Try entering one of your options again. \n");
                }
               
            }
            else if (masterBath.getActive() == true) //Master bathroom options.
            {
               
                System.out.println("---------------------- At the master bathroom ------------------------------------------------ \n");
               
                mapF2("   ", "   ", "   ", "   ", " * ", "   ");
               
                System.out.println(masterBath.getNarration()+ "\n");
               
                decision = userInput.nextLine();
               
                //Possible routes for player
                if (decision.equalsIgnoreCase(oilLamp.getName())) //Player wants to interact with oil lamp.
                {
                    System.out.println("Interacting with oil lamp. \n");
                    System.out.println(oilLamp.getNarration()+    "\n");
                }
                else if (decision.equalsIgnoreCase(mShower.getName())) //Player wants to interact with shower.
                {
                    System.out.println("Interacting with shower. \n");
                    System.out.println(mShower.getNarration()+  "\n");
                }
                else if (decision.equalsIgnoreCase(masterBed.getName())) //Player wants to head over to master bedroom.
                {
                    System.out.println("Heading over to master bedroom. \n");
                   
                    masterBed.setActive(true);
                    masterBath.setActive(false);
                }
                else //When player gives incorrect input.
                {
                    System.out.println("You didn't give me something I can work with. Try entering one of your options again. \n");
                }
               
            }
            else if (bathroom.getActive() == true) //Bathroom options.
            {
               
                System.out.println("---------------------- At the bathroom ------------------------------------------------ \n");
               
                mapF2("   ", "   ", "   ", "   ", "   ", " * ");
               
                System.out.println(bathroom.getNarration());
               
                decision = userInput.nextLine();
               
                //Possible routes for player.
                if (decision.equalsIgnoreCase(mirror.getName())) //Player wants to interact with mirror.
                {
                    System.out.println("Interacting with mirror. \n");
                    System.out.println(mirror.getNarration()+   "\n");
                }
                else if (decision.equalsIgnoreCase(shower.getName())) //Player wants to interact with shower.
                {
                    System.out.println("Interacting with shower. \n");
                    System.out.println(shower.getNarration()+   "\n");
                }
                else if (decision.equalsIgnoreCase(bedroomOne.getName())) //Player wants to head over to the first bedroom.
                {
                    System.out.println("Heading over to first bedroom. \n");
                   
                    bedroomOne.setActive(true );
                    bathroom  .setActive(false);
                }
                else if (decision.equalsIgnoreCase(bedroomTwo.getName())) //Player wants to head over to the second bedroom.
                {
                    System.out.println("Heading over to second bedroom. \n");
                   
                    bedroomTwo.setActive(true );
                    bathroom  .setActive(false);
                }
                else //When player gives incorrect input.
                {
                    System.out.println("You didn't give me something I can work with. Try entering one of your options again. \n");
                }
               
            }
            else
            {
               
                System.out.println("Why are you here? The player has caused a distrubance in the game's space-time fabric."                                            );
                System.out.println("A singularity has been born. The resulting mini black hole has destoryed everything in this game's universe, including the player.");
               
                alive = false;
               
            }
           
            return alive;
           
        }
       
    }
 
   
    private class Room //Stores information regarding the room.
    {
        //Variables
        private String rName    ;
        private String narration;
       
        private boolean active;
       
        //Constructor
        public Room(String newName, boolean newState)
        {
            rName = newName;
            active = newState;
        }
       
        //Methods
        public void    setActive(boolean newActive) //Change so that it changes the floor you want to active and then the floor that currnetly is active.
        {
            active = newActive;
        }
        public boolean getActive()
        {
            return active;
        }
       
        public String getName()
        {
            return rName;
        }
       
        public void   setNarration(String newNarration)
        {
            narration = newNarration;
        }
        public String getNarration()
        {
            return narration;
        }
    }
   
   
    private class Item //Stores information regarding the items.
    {
        private String iName    ;
        private String narration;
       
        public Item(String newName)
        {
            iName = newName;
        }
       
        public String getName()
        {
            return iName;
        }
       
        public void setNarration(String newNarration)
        {
            narration = newNarration;
        }
        public String getNarration()
        {
            return narration;
        }
    }
}