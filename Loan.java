public class Loan {
   private double Rate; //annualInterestRate;
   private double Years; // int numberOfYears;
   private double Amount; //loanAmount
   private double Monthly;
   private java.util.Date loanDate;
   
   /** Default constructor */
   // Actually, I'm not going to allow a default constructor.
   // Give it all to me! -1 for the value we need to calculate.
   public Loan(double theRate, double theYears, double theAmount, double theMonthly) {
      loanDate = new java.util.Date(); // initialize
      
      // Rate is entered as a whole number, but processed as a decimal
      Rate = theRate / 100;
      
      Years = (double)(int)theYears; // only accepting the int portion
      int Months = (int)Years * 12;
      Amount = theAmount;
      Monthly = theMonthly;
      
      // Formulas from: http://www.had2know.com/finance/mortgage-calculator-solver-4-variables.html
         // M = Monthly
         // P = principal Amount
         // N = Months
         // R = Rate
      if (Rate < 0) { // rate *can* be 0
         // "Approximation Formula", doesn't require calculus!
         // R = (72/N)sqrt[MN/(3P) - 1/12] - 36/N
         double inSqr = (Monthly * Months) / (3.0 * Amount);
         inSqr -= 1.0/12.0;
         inSqr = Math.sqrt(inSqr);
         Rate = ((72.0/Months) * inSqr) - (36.0/Months);
      }
      else if (Years <= 0) { // years can't be 0
         // a/b, where
         // a = Log(M) - Log(M - PR/12)
         // b = Log(1 + R/12)
         // (any non-wooden log is ok)
         Years = 999;
      }
      else if (Amount <= 0) { // amount can't be 0
         // a/b, where
         // a = M[(1+R/12)^N - 1]
         // b = (R/12)(1+R/12)^N
         double toTheN = Math.pow(1 + (Rate/12.0), Months);
         double theTop = Monthly * (toTheN - 1.0);
         double theBottom = (Rate/12) * toTheN;
         Amount = theTop / theBottom;
      }
      else if (Monthly <= 0) { // monthly can't be 0
         // From the book, ch 10, adapted for clarity
         double mRate = Rate / 12;
         double numR = Amount * mRate;
         // Note: if mRate >= 0 and Years > 0, demR can't be 0 (no div/0)
         double demR = 1 / Math.pow(1 + mRate, Months);
         demR = 1 - demR;
         Monthly = numR / demR;
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

} // end class Loan