/* Robert Brooks, rab120130@utdallas.edu
 * CS 2336, Section 501
 * Created and tested using jGRASP
 *
 * Assignment 5
 * Due date: 11/09/2013
 * Implement a Mortgage Calculator with Amortization Schedule
 * Note: project also saved at GitHub:
 * https://github.com/robertbdc/CS2336-Java-Mortgage-Purgatory
 */

// Note: Adapted this window from the DentalOfficeTester flash-exam 11/6/13

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class OutputWindow extends JFrame {
   private JTextArea output;
   private JScrollPane scrollPane; 
   
   // position next one relative to where the last one started
   static int nextX = 0;
   static int nextY = 0;

   // Constructor, create blank area
   public OutputWindow(int myWidth, int myHeight) {  
      output = new JTextArea(""); // wipe out existing text
      output.setBackground(Color.LIGHT_GRAY);
      output.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
      output.setEditable(false);
      scrollPane = new JScrollPane(output); // ScrollPane with output area
      this.add(scrollPane);

      this.setSize(myWidth, myHeight);
      this.setResizable(true);

      // Put one here and then put the next one a little offset
      this.setLocation(new Point(nextX, nextY));
      nextX += 50;
      nextY += 50;
      
      this.setVisible(true);
   }

   public void setText(String s) {
      output.append(s);//blank: add text to existing text?
   }
} // end OutputWindow
