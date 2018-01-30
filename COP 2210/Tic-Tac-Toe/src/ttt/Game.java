//******************************************************************************
//PANTHERID: 4999406
//CLASS    : COP2210 -  FALL 2016
//ASSIGNMENT #4
//DATE     : 11/28/2016
//
// I hereby swear and affirm that this work is solely my own, and not the work
// or derivative of the work of someone else.
//******************************************************************************

package ttt; ///The only package used in the program.
 
 
import java. util .Random     ;
import javax.swing.JOptionPane;
 
/**
 * <h1>Game Class</h1>
 * 
 * This controls the entire functioning of the game with the exception of the
 * games virtual board.
 * 
 * @author 4999406
 */
public class Game
{
    //Variables
    private boolean playing  ;
    private boolean turn     ; //True: Player Turn || False: CPU turn
    private int     outcome  ;
    private int     turnsP   ;
    private int     turnsCPU ;
    private String  userInput;
    private String  name     ;
 
    //Objects
    Board board = new Board();
   
    /*Constructor*//**
     * The only constructor for the game class. There are no parameters.
     * It runs the necessary methods for the first game the player plays when
     * starting the program.
     */
    public Game()
    {
        intro();
       
        decideFirst();
       
        simGame();
    }
    /*Methods*//**
     * Welcomes the player and obtains their name.
     */
    private void intro()
    {
        name = JOptionPane.showInputDialog(null, "You are now playing Tic-Tac-Toe!     \n\n"
                                               + "What would you like your name to be?"     );
    }
    /**
     * Decides who shall go first in the game of Tic-Tac-Toe. Possible outcomes
     * are either the player or CPU going first.
     */
    private void decideFirst()
    {
        int    decision              ;
        Random genTurn = new Random();
       
        decision = genTurn.nextInt(2);
       
        if ( decision == 0)
        {
            turn = true;
            JOptionPane.showMessageDialog(null, "You are going first.");
        }
        else
        {
            turn = false;
            JOptionPane.showMessageDialog(null, "The computer is going first.");
        }
    }
   /**
    * Runs the simulation of a Tic-Tac-Toe game.
    */
    private void simGame()
    {
        playing = true;
       
        while (playing == true)
        {
            int totalTurns = turnsP + turnsCPU;
           
            if (totalTurns == 9)
            {  
                outcome =     0;
                playing = false;
                result       ();
            }
            else if (turn == true)
            {
                turnPlayer();
            }
            else
            {
                turnCPU(); 
            }
            checkObjective();
        }
    }
   /**
    * Goes through the process of a player's turn.
    */
    private void turnPlayer()
    {
        board.drawBoard();
       
        userInput = JOptionPane.showInputDialog(null, name+ "'s Turn"                                         + "\n"
                                                    + "Choose a slot on the board by entering the "
                                                    + "number that is within one of the spaces not taken.   " + "\n");
       
        int inputIntVal = Integer.parseInt(userInput);
       
        if ( (inputIntVal > 0 ) ||
             (inputIntVal < 10)   )
        {
            userInput = board.convertToSlot(inputIntVal);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "You did not enter a correct choice, please choose a value on the board.");
        }
       
        int row    = Character.getNumericValue(userInput.charAt(0));
        int column = Character.getNumericValue(userInput.charAt(1));
       
        userInput = board.getSlotInfo(row, column);
       
        if (userInput.equals("O") || userInput.equals("X"))
        {
            JOptionPane.showMessageDialog(null, "You did not choose and open slot."+ "\n"
                                              + "Please choose again."                   );
        }
        else
        {
            board.setSlotInfo(row, column, "X");
           
            turnsP++;
           
            turn = false;
        }
        System.out.println("Player Turns: "+ turnsP+ " CPU Turns: "+ turnsCPU);
       
        board.drawBoard();
    }
   /**
    * Goes through the process for the computer's turn.
    */
    private void turnCPU()
    {  
        JOptionPane.showMessageDialog(null, "Computer's Turn.");
        
        boolean passed = false;
        
        while (passed == false)
        {        
            Random genChoice = new Random();
       
            int choice = genChoice.nextInt(9) + 1;
       
            String choiceInStr = board.convertToSlot(choice);
       
            int row    = Character.getNumericValue(choiceInStr.charAt(0));
            int column = Character.getNumericValue(choiceInStr.charAt(1));
       
            choiceInStr = board.getSlotInfo(row, column);
       
            if (choiceInStr.equals("O") || choiceInStr.equals("X"))
            {
            }
            else
            {
            board.setSlotInfo(row, column, "O");
            
            passed = true;
            }
        }   
        
        turnsCPU++;
           
        turn = true;
       
        System.out.println("Player Turns: "+ turnsP+ " CPU Turns: "+ turnsCPU);
       
        board.drawBoard();
    }
   /**
    * Checks to see if either the CPU or the player has completed one of the
    * conditions for wining the game.
    */
    private void checkObjective()
    {
        String slotsPlayer = "";
        String slotsCPU    = "";
       
        int value = 1;
       
        for (int countR = 0;
                 countR < 3;
                 countR  ++)
        {
            for(int countC = 0;
                    countC < 3;
                    countC  ++)
            {
                String check;
               
                check = board.getSlotInfo(countR, countC);
               
                if (check.equals("X"))
                {
                    slotsPlayer = slotsPlayer + value;
                }
                else if (check.equals("O"))
                {
                    slotsCPU = slotsCPU + value;
                }
                value++;
            }
        }
       
        if (slotsPlayer.contains("123") || slotsPlayer.contains("456") || slotsPlayer.contains("789") || //Horizontal
            slotsPlayer.contains("147") || slotsPlayer.contains("258") || slotsPlayer.contains("369") || //Vertical
            slotsPlayer.contains("159") || slotsPlayer.contains("057"))                                  //Diagonal
        {
            playing = false;
            outcome =     1;
           
            result();
        }
       
        if (slotsCPU.contains("123") || slotsCPU.contains("456") || slotsCPU.contains("789") || //Horizontal
            slotsCPU.contains("147") || slotsCPU.contains("258") || slotsCPU.contains("369") || //Vertical
            slotsCPU.contains("159") || slotsCPU.contains("057"))                               //Diagonal
        {
            playing  = false;
            outcome  =     2;
           
            result();
        }
    }
   
    /**
     * Displays results of the game and asks the player if they would like to
     * play another. Also handles the initialization of said game.
     */
    private void result()
    {
        int choice = 99;
       
        if (outcome == 0)
        {
             choice = JOptionPane.showConfirmDialog(null, "Tie Game!"              + "\n"
                                                        + "Would you like to play again?");
        }
        else if (outcome == 1)
        {
            choice = JOptionPane.showConfirmDialog(null, "You've won "+ name+ "!" + "\n"
                                                       + "Would you like to play agian?");
        }
        else if (outcome == 2)
        {
            choice = JOptionPane.showConfirmDialog(null, "You've lost "+ name+ "."+ "\n"
                   
                                                       + "Would you like to play again?");
        }
       
        if (choice == 0)
        {
            intro();
           
            decideFirst();
           
            board.initializeData();
           
            turnsP   = 0;
            turnsCPU = 0;
           
            simGame();
        }
    }
}