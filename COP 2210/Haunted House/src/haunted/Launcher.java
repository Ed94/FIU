//******************************************************************************
//PANTHERID: 4999406
//CLASS    : COP2210 -  FALL 2016
//ASSIGNMENT #4
//DATE: 10/31/2016
//
// I hereby swear and affirm that this work is solely my own, and not the work
// or derivative of the work of someone else.
//******************************************************************************
 
 
package haunted;
 
 
import java.util.Scanner;
 
 
public class Launcher
{
    //Variables
    static String  pName;
   
    public static boolean alive;
   
    static HauntedHouse HHouse;
   
    //Initialization
    public static void main(String[] args)
    {
        Scanner userInput = new Scanner(System.in);
       
        narratorOne(userInput);
       
        HHouse = new HauntedHouse(pName, alive = true, userInput); //Most of the program is in here.
       
        narratorTwo();
       
        userInput.close();
    }
   
    //Methods
    static void narratorOne(Scanner userInput) //Introductory message, gets user's name.
    {
        System. out.println("Who is the brave explorer that dares to enter this house of horror? (Enter your name)");
        pName = userInput.nextLine                                                                                ();
        System. out.println                                                                                       ();
    }
   
    static void narratorTwo()  //Game Over message
    {
        System.out.println("Well it seems that you have died, "+"\n"
                          +"Give it another go if you dare..."      );
    }
}