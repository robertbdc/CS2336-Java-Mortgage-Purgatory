import javax.swing.*;

public class LoanCalculatorApp {

   public static void main(String[] args) {
      // call it frame or maybe...
      LoanCalculator jailCell = new LoanCalculator();
      jailCell.pack(); // Arrange controls compactly based on their properties
      jailCell.setTitle("Calculate your Sentence");
      jailCell.setLocationRelativeTo(null); // sure, center it, whatever
      // todo: implement JFrame.GO_TO_LOAN_PAYMENT_PURGATORY
      jailCell.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jailCell.setVisible(true);
   }

}