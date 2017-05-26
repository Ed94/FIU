//******************************************************************************
//PANTHERID: 4999406, 5453835
//CLASS    : COP2210 -  FALL 2016
//ASSIGNMENT #4
//DATE     : 10/31/2016
//
//Original code from 5453835
//
// I hereby swear and affirm that this work is solely my own, and not the work
// or derivative of the work of someone else.
//******************************************************************************
 
package hauntedhorrorhouse; //The only packaged used in the program.

/**
* <h1>HHH Launcher</h1>
*
* This program runs a text adventure game that goes through a haunted house.
* It is called Haunted Horror House. 
* 
* @author 5453835
*/

public class Launcher
{

    
    private static HauntedHorrorHouse HHH; //Reserves HHH to be used as a Haunted House object
    
    /**
     * The main method that will initialize the HauntedHorrorHouse constructor.
     * 
     * @author 5453835
     * @param args Unused.
     */
    
    public static void main(String[] args)
    {
        HHH = new HauntedHorrorHouse(); //Make a Haunted House using the default constructor.
    }  
}
