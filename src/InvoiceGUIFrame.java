import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class InvoiceGUIFrame extends JFrame {
    JPanel mainPnl;
    JPanel entryPnl;
    JPanel addressPnl;
    JPanel productInfoPnl;
    JPanel invoicePnl;
    JPanel btnPnl;

    JTextField customerNameTF;
    JTextField streetAddressTF;
    JTextField cityTF;
    JTextField stateTF;
    JTextField zipCodeTF;
    JTextField productNameTF;
    JTextField unitPriceTF;
    JTextField quantityTF;

    JLabel customerNameLbl;
    JLabel streetAddressLbl;
    JLabel cityLbl;
    JLabel stateLbl;
    JLabel zipCodeLbl;
    JLabel productNameLbl;
    JLabel unitPriceLbl;
    JLabel quantityLbl;

    JTextArea invoiceTA;

    JButton quitBtn;
    JButton submitBtn;
    JButton clearBtn;
    JButton addBtn;
    JScrollPane scroller;

    ArrayList<LineItem> lineItems = new ArrayList<>();
    Font invoiceFont = new Font("Courier New", Font.PLAIN, 12);

    public InvoiceGUIFrame() {
        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());

        createEntryPnl();
        mainPnl.add(entryPnl, BorderLayout.CENTER);

        createInvoicePnl();
        mainPnl.add(invoicePnl, BorderLayout.EAST);

        createBtnPnl();
        mainPnl.add(btnPnl, BorderLayout.SOUTH);

        add(mainPnl);
        setSize(800, 600);
        setTitle("Invoice Creator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    private void createEntryPnl() {
        entryPnl = new JPanel();
        entryPnl.setLayout(new GridLayout(2, 1));

        addressPnl = new JPanel();
        addressPnl.setLayout(new GridLayout(5, 2));
        addressPnl.setBorder(new TitledBorder(new EtchedBorder(), "Enter address here"));

        productInfoPnl = new JPanel();
        productInfoPnl.setLayout(new GridLayout(3,2));
        productInfoPnl.setBorder(new TitledBorder(new EtchedBorder(), "Enter product info here"));

        customerNameLbl = new JLabel("Customer Name: ");
        streetAddressLbl = new JLabel("Street Address: ");
        cityLbl = new JLabel("City: ");
        stateLbl = new JLabel("State: ");
        zipCodeLbl = new JLabel("Zip Code: ");
        customerNameTF = new JTextField();
        streetAddressTF = new JTextField();
        cityTF = new JTextField();
        stateTF = new JTextField();
        zipCodeTF = new JTextField();

        addressPnl.add(customerNameLbl);
        addressPnl.add(customerNameTF);
        addressPnl.add(streetAddressLbl);
        addressPnl.add(streetAddressTF);
        addressPnl.add(cityLbl);
        addressPnl.add(cityTF);
        addressPnl.add(stateLbl);
        addressPnl.add(stateTF);
        addressPnl.add(zipCodeLbl);
        addressPnl.add(zipCodeTF);

        productNameLbl = new JLabel("Product Name: ");
        unitPriceLbl = new JLabel("Unit Price ($):");
        quantityLbl = new JLabel("Quantity: ");
        productNameTF = new JTextField();
        unitPriceTF = new JTextField();
        quantityTF = new JTextField();

        productInfoPnl.add(productNameLbl);
        productInfoPnl.add(productNameTF);
        productInfoPnl.add(unitPriceLbl);
        productInfoPnl.add(unitPriceTF);
        productInfoPnl.add(quantityLbl);
        productInfoPnl.add(quantityTF);

        entryPnl.add(addressPnl);
        entryPnl.add(productInfoPnl);
    }

    private void createInvoicePnl() {
        invoicePnl = new JPanel();

        invoiceTA = new JTextArea(36, 50);
        invoiceTA.setEditable(false);
        invoiceTA.setFont(invoiceFont);
        scroller = new JScrollPane(invoiceTA);
        invoiceTA.append("                     Invoice                 \n");
        invoiceTA.append("==================================================\n");
        invoiceTA.append(String.format("%-20s%6s%10s%10s", "Item", "Qty", "Price($)", "Total($)") + "\n");
        invoicePnl.add(scroller);
    }
    private void createBtnPnl() {
        btnPnl = new JPanel();
        btnPnl.setLayout(new GridLayout(1, 4));

        addBtn = new JButton("Add Item");
        addBtn.addActionListener((ActionEvent ae) -> addItem(productNameTF.getText(), unitPriceTF.getText(), quantityTF.getText()));
        submitBtn = new JButton("Submit");
        submitBtn.addActionListener((ActionEvent ae) -> submit());
        clearBtn = new JButton("Clear");
        clearBtn.addActionListener((ActionEvent ae) -> clear());
        quitBtn = new JButton("Quit");
        quitBtn.addActionListener((ActionEvent ae) -> System.exit(0));

        btnPnl.add(addBtn);
        btnPnl.add(submitBtn);
        btnPnl.add(clearBtn);
        btnPnl.add(quitBtn);

    }

    /*
    addItem:
    Converts each value to its proper data type (double or int) to create a LineItem object
    adds the new item to the lineItem array
    adds the new item to the invoice
     */

    private void addItem(String productName, String unitPrice, String quantity) {
        try {
            double unitPriceConverted = Double.parseDouble(unitPrice);
            try {
                int quantityConverted = Integer.parseInt(quantity);
                LineItem newItem = new LineItem(productName, unitPriceConverted, quantityConverted);
                lineItems.add(newItem);
                invoiceTA.append(newItem.toString());
                productNameTF.setText("");
                unitPriceTF.setText("");
                quantityTF.setText("");
            }
            catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Quantity is not in the correct format.");
            }
        }
        catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Unit Price is not in the correct format.");
        }
    }

    /*
    submit:
    puts address on invoice
    clears all fields
    prevents more info from being entered in the text fields
     */
    private void submit() {
        invoiceTA.setText("");
        invoiceTA.append("                     Invoice                 \n");
        invoiceTA.append(" ------------------------------\n");
        invoiceTA.append("|" + String.format("%-30s", customerNameTF.getText()) + "|\n");
        invoiceTA.append("|" + String.format("%-30s", streetAddressTF.getText()) + "|\n");
        invoiceTA.append("|" + String.format("%-30s", cityTF.getText() + ", " + stateTF.getText() + " " + zipCodeTF.getText()) + "|\n");
        invoiceTA.append(" ------------------------------\n");
        invoiceTA.append(String.format("%-20s%6s%10s%10s", "Item", "Qty", "Price($)", "Total($)") + "\n");
        invoiceTA.append("==================================================\n");
        double overallTotal = 0;
        for (LineItem i : lineItems) {
            invoiceTA.append(i.toString());
            overallTotal += i.calculatedTotal;
        }
        invoiceTA.append("==================================================\n");
        invoiceTA.append("Amount Due: $"+overallTotal);

        customerNameTF.setText("");
        streetAddressTF.setText("");
        cityTF.setText("");
        stateTF.setText("");
        zipCodeTF.setText("");
        customerNameTF.setEditable(false);
        streetAddressTF.setEditable(false);
        cityTF.setEditable(false);
        stateTF.setEditable(false);
        zipCodeTF.setEditable(false);
        productNameTF.setEditable(false);
        unitPriceTF.setEditable(false);
        quantityTF.setEditable(false);
    }

    /*
    clear
    clears all text boxes/fields
    makes all text fields editable
     */
    private void clear() {
        invoiceTA.setText("");
        customerNameTF.setText("");
        streetAddressTF.setText("");
        cityTF.setText("");
        stateTF.setText("");
        zipCodeTF.setText("");
        productNameTF.setText("");
        unitPriceTF.setText("");
        quantityTF.setText("");
        customerNameTF.setEditable(true);
        streetAddressTF.setEditable(true);
        cityTF.setEditable(true);
        stateTF.setEditable(true);
        zipCodeTF.setEditable(true);
        productNameTF.setEditable(true);
        unitPriceTF.setEditable(true);
        quantityTF.setEditable(true);
    }
}
