import java.util.Scanner;

public class LoanCalculator {

  /**
   * Главный метод программы для расчета графика погашения кредита
   */
  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in, "UTF-8");

    double principal; 
    int term;
    double rate;
    double payment;
    double totalPayment;

    try {

      principal = getDouble(scanner, "Enter the loan amount: ");

      term = getInt(scanner, "Enter the term in months: ");   

      rate = getDouble(scanner, "Enter the interest rate: ");

      // Расчет ежемесячного платежа
      payment = calculatePayment(principal, term, rate);  

      System.out.println("Monthly payment: " + payment);

      totalPayment = calculateTotalPayment(principal, payment, term);

      System.out.println("Total amount of payments: " + totalPayment);

      // Печать графика погашения
      printAmortizationSchedule(principal, payment, term, rate);

    } catch (Exception e) {
      System.out.println("Input error!");
      e.printStackTrace();
      return;
    } finally {
      scanner.close();
    }

  }

  /**
   * Метод для ввода числа с клавиатуры
   */
  private static double getDouble(Scanner scanner, String prompt) {

    System.out.print(prompt);

    if(scanner.hasNextDouble()) {
      return scanner.nextDouble();
    } else {
      scanner.next();  
      throw new NumberFormatException("Incorrect number entry");
    }

  }

  /**
   * Метод для ввода целого числа с клавиатуры  
   */
  private static int getInt(Scanner scanner, String prompt) {

    System.out.print(prompt);

    if(scanner.hasNextInt()) {
      return scanner.nextInt();
    } else {
      scanner.next();
      throw new NumberFormatException("Incorrect number entry");
    }

  }

  /**
   * Метод для расчета ежемесячного платежа
   */
  public static double calculatePayment(double principal, int term, double rate) {

    rate /= 1200;

    double payment = principal * (rate / (1 - Math.pow(1+rate, -term)));
    
    return payment;

  }
    /**
   * Метод для расчета общего платежа
   */ 
  public static double calculateTotalPayment(double principal, double payment, int term) {

    return payment * term;

  }
  /**
   * Метод для вывода графика погашения
   */ 
  private static void printAmortizationSchedule(double principal, 
  double payment, int term, double rate) {

  for(int month=1; month<=term; month++) {

    // Calculate interest for period
    double interest = principal * rate;

    // Apply payment
    principal += interest - payment;

    if(principal < 0) principal = 0;

    // Output schedule 
    System.out.println("Month " + month + ": " +
                       "Principal = " + principal + 
                       " Interest = " + interest);
  }

}

}
