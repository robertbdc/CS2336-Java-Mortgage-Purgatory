public class Loan {
   private double Rate; //annualInterestRate;
   private double Months;
   private double Amount; //loanAmount
   private double Monthly;
   private java.util.Date loanDate;
   
   /** Default constructor */
   // Actually, I'm not going to allow a default constructor.
   // Give it all to me! -1 for the value we need to calculate.
   public Loan(double theRate, double theMonths, double theAmount, double theMonthly) {
      // scratch vars
      double theTop, theBottom;

      loanDate = new java.util.Date(); // initialize
      
      // Rate is entered as a whole number, but processed as a decimal
      Rate = theRate / 100;
      
      Months = (double)(int)theMonths; // only accepting the int portion
      Amount = theAmount;
      Monthly = theMonthly;
      
      // Formulas from: http://www.had2know.com/finance/mortgage-calculator-solver-4-variables.html
         // M = Monthly payment
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
      else if (Months <= 0) { // years can't be 0
         // a/b, where
         // a = Log(M) - Log(M - PR/12)
         // b = Log(1 + R/12)
         // (any non-wooden log is ok)
         theTop = Math.log(Monthly) - Math.log(Monthly - ((Amount * Rate) / 12.0));
         theBottom = Math.log(1 + (Rate/12.0));
         Months = theTop / theBottom;
      }
      else if (Amount <= 0) { // amount can't be 0
         // a/b, where
         // a = M[(1+R/12)^N - 1]
         // b = (R/12)(1+R/12)^N
         double toTheN = Math.pow(1 + (Rate/12.0), Months);
         theTop = Monthly * (toTheN - 1.0);
         theBottom = (Rate/12) * toTheN;
         Amount = theTop / theBottom;
      }
      else if (Monthly <= 0) { // monthly can't be 0
         // From the book, ch 10, adapted for clarity
         double mRate = Rate / 12;
         theTop = Amount * mRate;
         // Note: if mRate >= 0 and Years > 0, demR can't be 0 (no div/0)
         theBottom = 1 / Math.pow(1 + mRate, Months);
         theBottom = 1 - theBottom;
         Monthly = theTop / theBottom;
      }
      


   }
   
   public double getRate() {
      // Returns in percentage, so multiply our internal
      return Rate * 100;
   }
   public double getYears() {
      return Months / 12.0;
   }
   public double getMonths() {
      return Months;
   }   
   public double getAmount() {
      return Amount;
   }
   public double getMonthly() {
      return Monthly;
   }

} // end class Loan