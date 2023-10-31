import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LoanCalculatorGUI extends JFrame {

    private JTextField principalField;
    private JTextField rateField;
    private JTextField termField;

    private JLabel monthlyPaymentLabel;
    private JLabel totalPaymentLabel;

    private JTable amortizationTable;

    private JPanel tablePanel;

    private JScrollPane scrollPane;

    private double principal;
    private int term;
    private double rate;

    public LoanCalculatorGUI() {

        // Frame setup
        setTitle("Loan Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = createInputPanel();
        add(inputPanel, BorderLayout.NORTH);

        // Calculate button
        JButton calcButton = createCalculateButton();
        add(calcButton, BorderLayout.PAGE_END);

        initializeUI();

        pack();

        setPreferredSize(new Dimension(400, 300));

        setVisible(true);

    }

    private JPanel createInputPanel() {

        JPanel panel = new JPanel();

        // Add input fields
        panel.add(new JLabel("Principal:"));
        principalField = new JTextField(10);
        panel.add(principalField);

        panel.add(new JLabel("Rate (%):"));
        rateField = new JTextField(10);
        panel.add(rateField);

        panel.add(new JLabel("Term (months):"));
        termField = new JTextField(10);
        panel.add(termField);

        return panel;

    }

    private JButton createCalculateButton() {

        JButton button = new JButton("Calculate");
        button.setPreferredSize(new Dimension(100, 30));

        button.addActionListener(e -> {
            calculate();
        });

        return button;

    }

    private void calculate() {

        principal = getPrincipal();
        term = getTerm();
        rate = getRate();

        double payment = getPayment();
        double totalPayment = getTotalPayment();

        monthlyPaymentLabel.setText("Monthly Payment: " + payment);
        totalPaymentLabel.setText("Total Payment: " + totalPayment);

        populateAmortizationTable();

    }

    // Getters

    private double getPayment() {
        return LoanCalculator.calculatePayment(principal, term, rate);
    }

    private double getTotalPayment() {
        return LoanCalculator.calculateTotalPayment(principal, getPayment(), term);
    }

    private void initializeUI() {

        // Create panels
        JPanel labelsPanel = createLabelsPanel();
        tablePanel = new JPanel();

        // Create table
        createAmortizationTable();

        if(scrollPane != null) {
          tablePanel.add(scrollPane);
        }

        // Add table
        tablePanel.add(scrollPane);

        // Main panel
        JPanel container = new JPanel();
        container.add(tablePanel, BorderLayout.CENTER);

        // Add to frame
        add(container, BorderLayout.CENTER);
        add(labelsPanel, BorderLayout.SOUTH);

    }

    private JPanel createLabelsPanel() {

        JPanel panel = new JPanel();

        monthlyPaymentLabel = new JLabel();
        panel.add(monthlyPaymentLabel);

        totalPaymentLabel = new JLabel();
        panel.add(totalPaymentLabel);

        // Output labels
        JLabel monthlyLabel = new JLabel("Monthly Payment: ");
        panel.add(monthlyLabel);

        JLabel totalLabel = new JLabel("Total Payment: ");
        panel.add(totalLabel);

        return panel;

    }

    private double getPrincipal() {
        return Double.parseDouble(principalField.getText());
    }

    private int getTerm() {
        return Integer.parseInt(termField.getText());
    }

    private double getRate() {
        return Double.parseDouble(rateField.getText());
    }

    private void createAmortizationTable() {

        amortizationTable = new JTable();
      
        DefaultTableModel model = new DefaultTableModel();
        // set model
      
        amortizationTable.setModel(model);
      
        scrollPane = new JScrollPane(amortizationTable);
      
      }

    private void populateAmortizationTable() {

        double payment = getPayment();

        DefaultTableModel model = getAmortizationData(payment);
        amortizationTable.setModel(model);

        // Redraw table
        tablePanel.remove(scrollPane);
        tablePanel.add(scrollPane);

        // Repaint
        tablePanel.revalidate();
        tablePanel.repaint();

        // Labels
        setLabels(payment);

    }

    private void setLabels(double payment) {

        monthlyPaymentLabel.setText(payment + "");
        totalPaymentLabel.setText(getTotalPayment() + "");

    }

    private DefaultTableModel getAmortizationData(double payment) {

        DefaultTableModel model = new DefaultTableModel();
      
        model.setColumnIdentifiers(new String[] {
          "Month", "Principal Paid", "Interest", "Remaining Balance"  
        });
      
        double remainingBalance = principal;
        double monthlyRate = rate/1200;  
      
        for(int month=1; month<=term; month++) {
      
          // Calculate interest
          double interest = remainingBalance * monthlyRate; 
      
          // Principal portion is payment - interest
          double principalPortion = payment - interest;  
      
          // Update remaining balance
          remainingBalance -= principalPortion;

          if(remainingBalance < 0) {
            remainingBalance = 0;
          }
      
          // Add row to model    
          model.addRow(new Object[] {
            month,
            principalPortion,  
            interest,
            remainingBalance
          });
      
        }
      
        return model;
      
      }
    public static void main(String[] args) {
        new LoanCalculatorGUI();
    }

}
