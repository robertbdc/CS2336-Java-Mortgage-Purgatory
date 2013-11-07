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
   private JTextField jtfMonths = new JTextField();
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
      //JPanel p1 = new JPanel(new GridLayout(5, 2));
      JPanel p1 = new JPanel(new GridBagLayout());
      p1.setLayout(new GridBagLayout());

      GridBagConstraints c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      c.gridy = 0;
      p1.add(new JLabel("Annual Interest Rate"), c);
      c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.gridy = 0;
      p1.add(jtfRate, c);
      
      c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      c.gridy = 1;
      p1.add(new JLabel("Number of Years"), c);
      c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.gridy = 1;
      c.weightx = 0.25;
      p1.add(jtfYears, c);

      c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 2;
      c.gridy = 1;
      p1.add(new JLabel("or Months"), c);
      c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 3;
      c.gridy = 1;
      c.weightx = 0.25;
      p1.add(jtfMonths, c);
      
      c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      c.gridy = 2;
      p1.add(new JLabel("Loan Amount"), c);
      c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.gridy = 2;
      p1.add(jtfAmount, c);

      c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      c.gridy = 3;
      p1.add(new JLabel("Monthly Tribute"), c);
      c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.gridy = 3;
      p1.add(jtfMonthly, c);
      
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
         double rate = -1.0, years = -1.0, months = -1.0, amount = -1.0,
            monthly = -1.0;
         int iNeed = 0;
         int iFail = 0;
         
         String curLine = jtfRate.getText().trim();
         if (curLine.equals("")) {
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
         
         // Years or Months. If both, use years.
         curLine = jtfYears.getText().trim();
         if (curLine.equals("")) {
            curLine = jtfMonths.getText().trim();
            if (curLine.equals("")) {
               iNeed++;
            }
            else {
               months = tryParse(curLine, -1.0);
               if (months <= 0) {
                  // months can't be zero
                  iFail++;
                  jtfMonths.setText("");
                  jtfMonths.requestFocusInWindow();
              }
            }
         } else {
            years = tryParse(curLine, -1.0);
            if (years <= 0) {
               // years can't be zero
               iFail++;
               jtfYears.setText("");
               jtfYears.requestFocusInWindow();
               // Also clear months
               jtfMonths.setText("");
            }
            else {
               months = years * 12;
            }
         }
         
         curLine = jtfAmount.getText().trim();
         if (curLine.equals("")) {
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
            JOptionPane.showMessageDialog(null, "One or more fields contained garbage. I threw it out. Try again.");
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

         Loan myLoan = new Loan(rate, months, amount, monthly);
         
         jtfRate.setText(String.format("%.2f", myLoan.getRate()));
         jtfYears.setText(String.format("%.2f", myLoan.getYears()));
         jtfMonths.setText(String.format("%.0f", myLoan.getMonths()));
         jtfAmount.setText(String.format("%.2f", myLoan.getAmount()));
         jtfMonthly.setText(String.format("%.2f", myLoan.getMonthly()));

         JOptionPane.showMessageDialog(null, "If this program were fully functional, you would be in debt now!");
         
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



