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
import javax.swing.*;

public class LoanCalculatorApp {

   public static void main(String[] args) {
      // call it frame or maybe...
      LoanCalculator jailCell = new LoanCalculator();
      jailCell.pack(); // Arrange controls compactly based on their properties
      jailCell.setTitle("Maybe you'd be better off renting?");
      jailCell.setLocationRelativeTo(null); // sure, center it, whatever
      jailCell.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jailCell.setVisible(true);
   }

}