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
 * <h1>Board Class</h1>
 * 
 * Controls aspects of data handling for the board as well as presentation of
 * board to player.
 * 
 * @author 4999406
 */
public class Board
{
    //Variables
    private String slots[][];
    private String board    ;
   
    /*Constructor*//**
     * The only constructor for the board class.
     * Handles the initialization of the boards data.
     */
    public Board()
    {
        initializeData();
    }
    /*Methods*//**
     * Method directly responsible for the initialization of the data for the
     * boards slots.
     */
    public void initializeData()
    {
        slots = new String[3][3];
       
        int value = 1;
       
        for (int countR  = 0;
                 countR  < 3;
                 countR   ++)
        {  
            for(int countC  = 0;
                    countC  < 3;
                    countC   ++)
            {
                slots[countR][countC] = Integer.toString(value);
               
                value++;
            }
        }
    }
   /**
    * Handles the drawing of the Tic-Tac-Toe board onto console.
    */
    public void drawBoard() //Draws the Tic-Tac-Toe Board
    {
        String empty = "";
       
        System.out.println("**************Current State of Board*****************");
   
        board = (" "+  empty    +"  | "+   empty   +"  | "+  empty   +"  "+ "\n"
                +" "+slots[0][0]+ " | "+slots[0][1]+ " | "+slots[0][2]+" "+ "\n"
                +"_"+  empty    +"__|_"+   empty   +"__|_"+  empty   +"__"+ "\n"
                +" "+slots[1][0]+ " | "+slots[1][1]+ " | "+slots[1][2]+" "+ "\n"
                +"_"+  empty    +"__|_"+   empty   +"__|_"+  empty   +"__"+ "\n"
                +" "+  empty    +"  | "+   empty   +"  | "+  empty   +"  "+ "\n"
                +" "+slots[2][0]+ " | "+slots[2][1]+ " | "+slots[2][2]+" "+ "\n"
                +" "+  empty    +"  | "+   empty   +"  | "+  empty   +"  "      );
       
        System.out.println(board                                             + "\n"
                          +"*****************************************************");
    }
   /**
    * Converts the single digit value given to the equivalent two value address
    * of the slot array location.
    * 
    * @param location The single digit value that represents an array location
    *                 for the slots array.
    * @return        Returns the two value address.
    */
    public String convertToSlot(int location)
    {
        int row    = 0;
        int column = 0;
       
        double value = (double)location / 3.0;
               
        if      (value <= 1)              //Row 1
        {
            row    =            0;
            column = location - 1;
        }
        else if (value <= 2 && value > 1) //Row 2
        {
            row    =            1;
            column = location - 4;
        }
        else if (value <= 3 && value > 2) //Row 3
        {
            row    =            2;
            column = location - 7;
        }
       
        String slotLocal = Integer.toString(row)+ Integer.toString(column);
       
        return slotLocal;
    }
   /**
    * Allows for the setting of a value within the slots arrayfor the given data
    * address.
    * 
    * @param row      First  value of data address for slots array.
    * @param column   Second value of data address for slots array.
    * @param newValue New data to be put into the address of the array.
    */
    public void setSlotInfo(int row, int column, String newValue)
    {
        slots[row][column] = newValue;
    }
   /**
    * Allows for the obtaining of the data within the slots array address
    * given.
    * 
    * @param row    First  value of the data address for the slots array.
    * @param column Second value of the data address for the slots array.
    * @return       Returns data of address specified.
    */
    public String getSlotInfo(int row, int column)
    {  
        return slots[row][column];
    }
}