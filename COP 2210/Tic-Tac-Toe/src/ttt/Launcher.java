//******************************************************************************
//PANTHERID: 4999406
//CLASS    : COP2210 -  FALL 2016
//ASSIGNMENT #4
//DATE     : 11/28/2016
//
// I hereby swear and affirm that this work is solely my own, and not the work
// or derivative of the work of someone else.
//******************************************************************************

package ttt;

/**
 * <h1>Launcher Class</h1>
 * 
 * Initializes the game class where the program will be handled.
 * 
 * @author 4999406
 */
public class Launcher
{
    //Variables
    static Game TTT;
    
    /**
     * The main method will initialize the Game constructor.
     *
     * @param args Unused.
     */
    public static void main(String[] args)
    {
        TTT = new Game();
    }
}