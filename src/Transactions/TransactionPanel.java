package Transactions;

import de.hwr.wm.ds.DaoException;
import de.hwr.wm.ds.UserDataDAODBImpl;
import userLogin.LoginBackEnd;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class TransactionPanel extends JPanel {
    UserDataDAODBImpl userDataDAODB = new UserDataDAODBImpl();

    private String customer_ID = LoginBackEnd.customer_ID; // Beispiel-Kundennummer
    private double value;
    private String moneyChange, message, sender_ID, receiver_ID;
    private ArrayList<Double> arrayList_BalanceValues;
    private ArrayList<String> arrayList_Messages;
    private ArrayList<String> arrayListSender_ID;
    private ArrayList<String> arrayListReceiver_ID;
    double balance1, balance2, balance3, balance4, balance5;
    JLabel emptyLabel1, emptyLabel2;
    static int countPanel = 0;
    public TransactionPanel() {
        setLayout(new GridLayout(0, 1)); // Zeilenbasiertes Layout
        setPreferredSize(new Dimension(750, 550));
        setBackground(Color.darkGray);

        try {
            arrayList_BalanceValues = userDataDAODB.getBalanceUserArray(customer_ID);
            arrayList_Messages = userDataDAODB.getUser_TransactionMessage(customer_ID);
            arrayListSender_ID = userDataDAODB.getUser_T_Sender_ID(customer_ID);
            arrayListReceiver_ID = userDataDAODB.getUser_T_Receiver_ID(customer_ID);

        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

        int numTransactions = Math.min(5, arrayList_BalanceValues.size()); // Maximal 5 oder so viele wie verfügbar

        for (int i = 0; i < numTransactions; i++) {
            int currentIndex = arrayList_BalanceValues.size() - 1 - i; // Berechne den aktuellen Index
            int previousIndex = currentIndex - 1; // Berechne den vorherigen Index

            if (currentIndex >= 0) {
                double currentValue = arrayList_BalanceValues.get(currentIndex);

                switch (i) {
                    case 0:
                        balance1 = currentValue;
                        break;
                    case 1:
                        balance2 = currentValue;
                        break;
                    case 2:
                        balance3 = currentValue;
                        break;
                    case 3:
                        balance4 = currentValue;
                        break;
                    case 4:
                        balance5 = currentValue;
                        break;
                }

                if (previousIndex >= 0) { // Stelle sicher, dass der vorherige Index gültig ist
                    double previousValue = arrayList_BalanceValues.get(previousIndex);
                    value = currentValue - previousValue;
                    System.out.println(currentValue + "\n" + previousValue);

                    if (currentValue > previousValue) {
                        moneyChange = "Geld empfangen";
                        System.out.println(moneyChange);
                    } else if (currentValue < previousValue) {
                        moneyChange = "Geld versendet";
                        System.out.println(moneyChange);
                    }
                }

                System.out.println("___________________");
                // Werte aus den anderen Listen holen
                message = arrayList_Messages.get(currentIndex);
                receiver_ID = arrayListSender_ID.get(currentIndex);
                sender_ID = arrayListReceiver_ID.get(currentIndex);

                try {
                    if (sender_ID == null && !message.equalsIgnoreCase("Cashdrop Gebühr") ) {
                        if (value > 0){
                            sender_ID = "Bankeinzahlung von **** **** **** **** **** **";  // Wenn sender_ID leer ist, ruft es die IBAN ab
                        } else {
                            sender_ID = "Stealth Bank inc.";
                        }
                    }else {
                        sender_ID = "Stealth Bank inc.";
                    }
                } catch (Exception ex) {
                    ex.printStackTrace(); // Fehlermeldung ausgeben, falls ein Fehler auftritt
                }

                // Debug-Ausgabe
                System.out.println(i + " " + currentValue + ". " + message + " , " + sender_ID + " , " + receiver_ID);
                add(new CustomaziedPanel(customer_ID, value, message, moneyChange));
                System.out.println("Panel erstellt ...");

            }
        }
    }

    // Innere Klasse für Erstellung Panels
    private class CustomaziedPanel extends JPanel {
        static int countPanelUses = 0;
        JPanel panel1, panel2;
        CustomaziedPanel(String customer_ID, double value, String message, String moneyChange) {
            setBackground(Color.darkGray);
            setLayout(new GridLayout(1, 2));
            setPreferredSize(new Dimension(750, 100));

            panel1 = new JPanel();
            panel1.setPreferredSize(new Dimension(650,100));
            panel1.setBackground(Color.darkGray);

            panel2 = new JPanel();
            panel2.setPreferredSize(new Dimension(50,100));
            panel2.setAlignmentX(LEFT_ALIGNMENT);
            panel2.setBackground(Color.darkGray);

            JLabel titleLabel;
            JLabel transactionLabel;
            JLabel gainMoney = new JLabel("+" + value);
            if (moneyChange.equalsIgnoreCase("Geld empfangen")) {
                gainMoney.setForeground(Color.GREEN);
                titleLabel = new JLabel(moneyChange + " von : " + sender_ID);
                titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                emptyLabel1 = new JLabel(" ");

                titleLabel.setForeground(Color.GREEN);
                transactionLabel = new JLabel(" Betrag: +" + value + "€ Verwendungszweck: " + message);
                transactionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            } else {
                titleLabel = new JLabel(moneyChange + " an : " + sender_ID);
                emptyLabel1 = new JLabel(" ");
                titleLabel.setForeground(Color.RED);
                titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                transactionLabel = new JLabel(" Betrag: " + value + "€ Verwendungszweck: " + message);
                transactionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            }

            transactionLabel.setForeground(Color.WHITE);

            JLabel balanceLabel = new JLabel( "Kontostand: "+getBalanceForGUI(countPanelUses)+"€");
            balanceLabel.setForeground(Color.WHITE);

            panel1.add(titleLabel);
            panel1.add(emptyLabel1);
            panel1.add(transactionLabel);
            panel2.add(balanceLabel);

            add(panel1);
            add(panel2);
            countPanelUses++;
            if (countPanelUses == 5){           // countPanelUses zurücksetzen, damit die statisceh variable bei neu öffnung des panels wieder bei 0 anfängt
                countPanelUses = 0;
            }
        }
    }
    double getBalanceForGUI(int i){
        double balance = 0;
        switch (i){
            case 0:
                balance = balance1;
                break;
            case 1:
                balance = balance2;
                break;
            case 2:
                balance = balance3;
                break;
            case 3:
                balance = balance4;
                break;
            case 4:
                balance = balance5;
                break;
        }
        return balance;
    }
}