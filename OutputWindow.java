// Adapted this window from the DentalOfficeTester flash-exam 11/6/13
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class OutputWindow extends JFrame {
   private JTextArea output;
   private JScrollPane scrollPane; 

   // Constructor, create blank area
   public OutputWindow() {  
      output = new JTextArea(""); // wipe out existing text
      output.setBackground(Color.LIGHT_GRAY);
      output.setFont(new Font("Arial", Font.BOLD, 12));
      output.setEditable(false);
      scrollPane = new JScrollPane(output); // ScrollPane with output area
      this.add(scrollPane);
   }

   public void setText(String s) {
      output.append(s);//blank: add text to existing text?
   }
} // end OutputWindow
