//******************************************************************************
//PANTHERID: 4999406, 5453835
//CLASS    : COP2210 -  FALL 2016
//ASSIGNMENT #4
//DATE: 10/31/2016
//
// I hereby swear and affirm that this work is solely my own, and not the work
// or derivative of the work of someone else.
//******************************************************************************

package hauntedhorrorhouse;

/**
 * <h1>Room</h1>
 * Used to create and manage the rooms of the Haunted House.
 * 
 * @author 5453835
 */
public class Room
    {
        //Variables
        private final String NAME     ; //Name of the room.
        private       String narration; //Narration for the room.
        
        boolean state; //Active is true. Inactive is false.
        
        
        /*Constructors*//**
         * The only constructor for the room class. 
         * A room can be assigned the name once during creation of the object.
         * The state of the room is also initialized.
         * 
         * @author 4999406
         * @param newName  Name of the room.
         * @param newState Initial state of the room. (Should start false unless the front door)
         */
        public Room(String newName, boolean newState)
        {
            NAME  = newName ;
            state = newState;
        }
        
        
        /*Methods*//**
         * Allows for information regarding the rooms name to given.
         * 
         * @author 499406
         * @return Name of the room.
         */
        public String getName()
        {
            return NAME;
        }
        /**
         * Allows for the a new narration to be set for the room.
         * 
         * @author 4999406
         * @param newNarration What the narration of the room will be set to.
         */
        public void   setNarration(String newNarration)
        {
            narration = newNarration;
        }
        /**
         * 
         * @author 4999406
         * @return 
         */
        public String getNarration()
        {
            return narration;
        }
        /**
         * Allows for the state of the room to be set to active or inactive.
         * 
         * @author 4999406
         * @param newState Provides a new state for the room.
         */
        public void    setState(boolean newState)
        {
            state = newState;
        }
        /**
         * Returns the state of the room.
         * 
         * @author 4999406
         * @return State of the room. (If it is active or not.)
         */
        public boolean getState()
        {
            return state;
        }
    }