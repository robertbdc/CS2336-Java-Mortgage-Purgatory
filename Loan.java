public class Loan {
   private double Rate; //annualInterestRate;
   private double Years; // int numberOfYears;
   private double Amount; //loanAmount
   private double Monthly;
   private double Total;
   private java.util.Date loanDate;
   
   /** Default constructor */
   // Actually, I'm not going to allow a default constructor.
   // Give it all to me! -1 for the value we need to calculate.
   public Loan(double theRate, double theYears, double theAmount, double theMonthly, double theTotal) {
      loanDate = new java.util.Date(); // initialize
      
      // Rate is entered as a whole number, but processed as a decimal
      if (theRate >= 0)
         Rate = theRate / 100;
      
      Years = (double)(int)theYears; // only accepting the int portion
      Amount = theAmount;
      Monthly = theMonthly;
      Total = theTotal;
      
      if (Rate < 0) {
         Rate = 999;
      }
      else if (Years <= 0) { // years can't be 0
         Years = 999;
      }
      else if (Amount < 0) {
         Amount = 999;
      }
      else if (Monthly < 0) {
         // From the book, ch 10, adapted for clarity
         double mRate = Rate / 12;
         double numR = Amount * mRate;
         // Note: if mRate >= 0 and Years > 0, demR can't be 0 (no div/0)
         double demR = 1 / Math.pow(1 + mRate, Years * 12);
         demR = 1 - demR;
         Monthly = numR / demR;
      }
      else if (Total < 0) {
         Total = 999;
      }
      


   }
   
   public double getRate() {
      // Returns in percentage, so multiply our internal
      return Rate * 100;
   }
   public double getYears() {
      return Years;
   }
   public double getAmount() {
      return Amount;
   }
   public double getMonthly() {
      return Monthly;
   }
   public double getTotal() {
      return Total;
   }

} // end class Loan