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

// Note: Initially based on program listing 16.3.7, then enhanced.

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.text.DecimalFormat;

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
   private JButton jbtComputeLoan = new JButton("Compute Loan Parameters");

   // Constructor buids the panel
   public LoanCalculator() {
      // a panel with the fields
      //JPanel p1 = new JPanel(new GridLayout(5, 2)); // This looked ugly when resized
      JPanel p1 = new JPanel(new GridBagLayout());
      p1.setLayout(new GridBagLayout());

      // Use GridBag to make controls size better. Similar to HTML tables.
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
      
      p1.setBorder(new TitledBorder("To calculate any value, leave it blank"));
      
      // a panel with the button
      JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
      p2.add(jbtComputeLoan);
      
      // Won't do much if we don't do something with our shiny new panels
      add(p1, BorderLayout.CENTER);
      add(p2, BorderLayout.SOUTH);

      // Java doc says this will work in the constructor of the window. (Sure enough, it does!)
      getRootPane().setDefaultButton(jbtComputeLoan);
      
      // Register a listener.
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
            // If everything is filled in, assume we want to recalcuate monthly rate
            monthly = -1;
         }

         // Pass everything and create a Loan object we can get values from
         Loan myLoan = new Loan(rate, months, amount, monthly);
         
         rate = myLoan.getRate();
         years = myLoan.getYears();
         months = myLoan.getMonths();
         amount = myLoan.getAmount();
         monthly = myLoan.getMonthly();
         
         // Plain format for textboxes
         String fmtRate = String.format("%.2f", rate);
         String fmtYears = String.format("%.2f", years);
         String fmtMonths = String.format("%.0f", months);
         String fmtAmount = String.format("%.2f", amount);
         String fmtMonthly = String.format("%.2f", monthly);

         jtfRate.setText(fmtRate);
         jtfYears.setText(fmtYears);
         jtfMonths.setText(fmtMonths);
         jtfAmount.setText(fmtAmount);
         jtfMonthly.setText(fmtMonthly);
         
         // Fancier format for amort table
         // percentage (note, don't include % character or it will do *100)
         DecimalFormat formatter = new DecimalFormat("#.00");
         fmtRate = formatter.format(rate);

         // Months doesn't change

         // dollar amounts (keep this formatter for the table)
         formatter = new DecimalFormat("$###,###.00");
         fmtAmount = formatter.format(amount);
         fmtMonthly = formatter.format(monthly);
      
         OutputWindow outFrame = new OutputWindow(750, 300);
         outFrame.setTitle(String.format("Amortization: %s%%, %s Months, %s Total, %s Payment",
            fmtRate, fmtMonths, fmtAmount, fmtMonthly));
         // Just get rid of this window on close, don't close app.
         // Multiple amortization windows are ok
         outFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

         String header = String.format("%s\t%11s\t%11s\t%11s\n",
            "Pmt#", "Principal", "Interest", "Remaining");
         double remPrin = amount;
         double curInt = 0.0;
         double curPrin = 0.0;
         double useRate = rate / 100.0;
         int payNo = 0;
         
         double totInt = 0.0; // will be obscenely high
         
         do {
            // Calculation from http://www.had2know.com/finance/amortization-schedule-calculator.html
            // Clarified by http://www.myamortizationchart.com/articles/how-is-an-amortization-schedule-calculated/
            // Interest paid this month = P*(R/N)
            curInt = remPrin * (useRate/12.0);
            curPrin = monthly - curInt;
            
            remPrin -= curPrin;
            totInt += curInt;
            
            // Header every year
            if (payNo % 12 == 0) {
               outFrame.setText(header);
            }   

            payNo++;

            // Cumulative rounding over 30 years makes the final payment smaller
            if (remPrin < 0.0) {
               // Subtract this (add negative) from the last principal payment
               curPrin += remPrin;
               remPrin = 0.0;
               
               // Did we end up using up all the principal?
               // (unlikely, but account for edge case)
               if (curPrin < 0.0) {
                  curInt += curPrin;
                  curPrin = 0.0;
               }
            }

            outFrame.setText(String.format("%d\t%11s\t%11s\t%11s\n", 
               payNo, formatter.format(curPrin), formatter.format(curInt),
               formatter.format(remPrin)
               ));
               
         } while (remPrin > 0.05); // don't show a silly zero payment due to rounding!

         // Just to add insult to injury, here's how bad it was!
         outFrame.setText(String.format("----\t%11s\t%11s\t%11s\n", 
            "----", "----", "----"));
         outFrame.setText(String.format("\t%11s\t%11s\t%11s\n",
            "Principal", "Interest", "Total Paid"));
         outFrame.setText(String.format("Tot:\t%11s\t%11s\t%11s\n", 
            formatter.format(amount), formatter.format(totInt),
            formatter.format(amount + totInt)
            ));

         
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
