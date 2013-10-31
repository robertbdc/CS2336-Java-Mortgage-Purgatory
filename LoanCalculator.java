// Initially based on program listing 16.3.7, then enhanced.

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class LoanCalculator extends JFrame {
   // Create text fields for interest rate, years,
   // loan amount, monthly payment
   private JTextField jtfRate = new JTextField();//jtfAnnualInterestRate
   private JTextField jtfYears = new JTextField();//jtfNumberOfYears
   private JTextField jtfAmount = new JTextField();//jtfLoanAmount
   private JTextField jtfMonthly = new JTextField();//jtfMonthlyPayment
   
   @Override
   public Dimension getPreferredSize() {
      // This will help Pack to pack it up better
      return new Dimension(600, 300);
   }   
   
   
   // Create a Compute Payment button
   // todo: fancier than this
   private JButton jbtComputeLoan = new JButton("Compute Sentence");

   // Constructor buids the panel
   public LoanCalculator() {
      // a panel with the fields
      // todo: definitely make this better
      JPanel p1 = new JPanel(new GridLayout(5, 2));

      p1.add(new JLabel("Annual Interest Rate"));
      p1.add(jtfRate);
      
      p1.add(new JLabel("Number of Years"));
      p1.add(jtfYears);
      
      p1.add(new JLabel("Loan Amount"));
      p1.add(jtfAmount);
      
      p1.add(new JLabel("Monthly Tribute"));
      p1.add(jtfMonthly);
      
      p1.setBorder(new TitledBorder("Enter all but one of the parameters of your servitude"));
      
      // a panel with the button (need to make generic)
      JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
      p2.add(jbtComputeLoan);
      
      // Won't do much if we don't do something with our shiny new panels
      add(p1, BorderLayout.CENTER);
      add(p2, BorderLayout.SOUTH);
      
      // Register a listener. This is the part I need to understand better.
      // ButtonListener is something I define, so I'll give it a different name.
      jbtComputeLoan.addActionListener(new ComputeListener());
   } // end LoanCalculator constructor

   // Compute Payment button
   private class ComputeListener implements ActionListener {
      // ActionListener is a Java abstract class, so I need to implement
      // its abstract methods.
      @Override
      public void actionPerformed(ActionEvent e) {
         // Here's where we get custom. :)
         // Find which field is blank, and calculate it.
         boolean needRate = false, needYears = false, needAmount = false,
            needMonthly = false;
         double rate = -1.0, years = -1.0, amount = -1.0,
            monthly = -1.0;
         int iNeed = 0;
         int iFail = 0;
         
         String curLine = jtfRate.getText().trim();
         if (curLine.equals("")) {
            needRate = true;
            iNeed++;
         } else {
            rate = tryParse(curLine, -1.0);
            if (rate < 0) {
               // rate *can* be zero
               iFail++;
               jtfRate.setText("");
               jtfRate.requestFocusInWindow();
            }   
         }
         curLine = jtfYears.getText().trim();
         if (curLine.equals("")) {
            needYears = true;
            iNeed++;
         } else {
            years = tryParse(curLine, -1.0);
            if (years <= 0) {
               // years can't be zero
               iFail++;
               jtfYears.setText("");
               jtfYears.requestFocusInWindow();
            }   
         }
         curLine = jtfAmount.getText().trim();
         if (curLine.equals("")) {
            needAmount = true;
            iNeed++;
         } else {
            amount = tryParse(curLine, -1.0);
            if (amount <= 0) {
               // amount can't be zero
               iFail++;
               jtfAmount.setText("");
               jtfAmount.requestFocusInWindow();
            }   
         }
         curLine = jtfMonthly.getText().trim();
         if (curLine.equals("")) {
            needMonthly = true;
            iNeed++;
         } else {
            monthly = tryParse(curLine, -1.0);
            if (monthly <= 0) {
               // monthly can't be zero
               iFail++;
               jtfMonthly.setText("");
               jtfMonthly.requestFocusInWindow();
            }   
         }
         
         // Validation!
         if (iFail > 0) {
            JOptionPane.showMessageDialog(null, "One or more fields contained garbage. I threw it out.");
            return;
         }

         if (iNeed > 1) {
            JOptionPane.showMessageDialog(null, "You can only leave one box blank. Make up your mind!");
            return;
         }
         
         if (iNeed == 0) {
            // Assume we want the new monthly rate
            monthly = -1;
         }

         Loan myLoan = new Loan(rate, years, amount, monthly);
         
         jtfRate.setText(String.format("%.2f", myLoan.getRate()));
         jtfYears.setText(String.format("%.0f", myLoan.getYears()));
         jtfAmount.setText(String.format("%.2f", myLoan.getAmount()));
         jtfMonthly.setText(String.format("%.2f", myLoan.getMonthly()));

         JOptionPane.showMessageDialog(null, "If this program was functional, you would be in debt now!");
         
      } // end actionPerformed
   
   } // end ComputeListener


   // Parse the incoming data, return defVal if invalid
   private double tryParse(String str, double defVal) {
      double retVal;
      try {
         retVal = Double.parseDouble(str);
      }
      catch (NumberFormatException nfe)
      {
         retVal = defVal;
      }
      
      return retVal;
   
   }

} // end class definition


