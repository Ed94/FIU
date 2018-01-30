/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coplab1;

import javax.swing.JOptionPane;
/**
 *
 * @author dali004
 */
public class DialogViewer 
{
    public static void main(String[] args)
    {
        //JOptionPane.showMessageDialog(null, "Hello, Edward!");
        String name = JOptionPane.showInputDialog("What is your name?"       );
        JOptionPane.showMessageDialog            (null,"Hello, " + name + "!");
        
        JOptionPane.showInputDialog  ("My name is Hal! What would you like me to do?");
        JOptionPane.showMessageDialog(null, "I'm sorry, " +  name + ". " + "I'm afraid I can't do that.");
    }
    
}
