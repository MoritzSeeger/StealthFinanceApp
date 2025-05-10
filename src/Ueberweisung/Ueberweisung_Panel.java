package Ueberweisung;

import Transactions.TransactionPanel;
import de.hwr.wm.ds.DaoException;
import de.hwr.wm.ds.UserDataDAODBImpl;
import userLogin.LoginBackEnd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Ueberweisung_Panel extends JPanel {
    JPanel panel_NORTH, panel_CENTER, panel_BOTTOM, panel_Center_LEFT, panel_Center_RIGHT, panel_North_Left, panel_North_RIGHT, panel_TITLE, panel_LOGO;
    JTextField textField_Receiver, textField_ID, textField_Amount, textField_Message;
    JButton button_Send, button_Back;
    LocalDate date;
    LocalTime time;
    String logo_FilePath = "src/Logo.png";
    String receiver, receiver_ID, message;
    double amount;
    String customer_ID = LoginBackEnd.customer_ID;
    TransactionPanel transactionPanel = new TransactionPanel();
    Ueberweisung_Panel(){
        setPreferredSize(new Dimension(1000,600));
        setBackground(Color.darkGray);

        //Bild skalieren
        ImageIcon icon = new ImageIcon(logo_FilePath);
        Image orginialImage = icon.getImage();

        int newWitdh = 120, newHeight = 80;
        Image resizedImage = orginialImage.getScaledInstance(newWitdh,newHeight, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel label_LOGO = new JLabel(resizedIcon);
        panel_LOGO = new JPanel(new BorderLayout());
        panel_LOGO.setPreferredSize(new Dimension(100,100));
        panel_LOGO.setBackground(Color.darkGray);
        panel_LOGO.add(label_LOGO, BorderLayout.NORTH);


        // Uhrzeit und Datum aktuell iniziieren
        date = LocalDate.now();
        time = LocalTime.now();

        // Button Senden erstellt
        button_Send = new JButton("Senden");
        button_Send.setPreferredSize(new Dimension(100,50));


        // Panel North Konfiguration ohne Zeilen und SPaltenangabe = 1 Zeile und dynamische Spalten
        panel_NORTH = new JPanel(new GridLayout());
        panel_NORTH.setPreferredSize(new Dimension(1000,50));
        panel_NORTH.setBackground(Color.darkGray);

        panel_North_Left = new JPanel();
        panel_North_Left.setBackground(Color.darkGray);

        panel_North_RIGHT = new JPanel(new BorderLayout());
        panel_North_RIGHT.setBackground(Color.darkGray);
        panel_North_RIGHT.add(panel_LOGO, BorderLayout.EAST);

        panel_NORTH.add(panel_North_Left);
        panel_NORTH.add(panel_North_RIGHT);

        // Panle Title Konfiguration
        panel_TITLE = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel_TITLE.setPreferredSize(new Dimension(1000,50));
        panel_TITLE.setBackground(Color.darkGray);
        JLabel lable_Title = new JLabel("Überweisung");
        lable_Title.setFont(new Font("Sans Serif",Font.BOLD,35));
        lable_Title.setForeground(Color.white);
        panel_TITLE.add(lable_Title);

        // Panel Center Konfiguration
        panel_CENTER = new JPanel();
        panel_CENTER.setPreferredSize(new Dimension(700,400));
        panel_CENTER.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel_CENTER.setBackground(Color.darkGray);

        // Label Erstellung und Konfiguration
        JLabel label_Receiver = new JLabel("Empfänger:");
        label_Receiver.setFont(new Font("Sans Serif",Font.BOLD,14));
        label_Receiver.setForeground(Color.white);
        JLabel label_ID = new JLabel("Empfänger-ID:");
        label_ID.setFont(new Font("Sans Serif",Font.BOLD,14));
        label_ID.setForeground(Color.white);
        JLabel label_Amount = new JLabel("Betrag (€):");
        label_Amount.setFont(new Font("Sans Serif",Font.BOLD,14));
        label_Amount.setForeground(Color.white);
        JLabel label_Message = new JLabel("Verwendungszweck:");
        label_Message.setFont(new Font("Sans Serif",Font.BOLD,14));
        label_Message.setForeground(Color.white);

        textField_Receiver = new JTextField();
        textField_ID = new JTextField();
        textField_Amount = new JTextField();
        textField_Message = new JTextField();

        panel_Center_LEFT = new JPanel(new GridLayout(4,1));
        panel_Center_LEFT.setPreferredSize(new Dimension(150,420));
        panel_Center_LEFT.setBackground(Color.darkGray);
        panel_Center_LEFT.add(label_Receiver);
        panel_Center_LEFT.add(label_ID);
        panel_Center_LEFT.add(label_Amount);
        panel_Center_LEFT.add(label_Message);

        panel_Center_RIGHT = new JPanel(new GridLayout(4,1));
        panel_Center_RIGHT.setPreferredSize(new Dimension(500,420));
        panel_Center_RIGHT.setBackground(Color.darkGray);
        panel_Center_RIGHT.add(textField_Receiver);
        panel_Center_RIGHT.add(textField_ID);
        panel_Center_RIGHT.add(textField_Amount);
        panel_Center_RIGHT.add(textField_Message);


        panel_CENTER.add(panel_Center_LEFT);
        panel_CENTER.add(panel_Center_RIGHT);

        // Panel Bottom Konfigurieren
        panel_BOTTOM = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel_BOTTOM.setPreferredSize(new Dimension(1000,60));
        panel_BOTTOM.setBackground(Color.darkGray);
        panel_BOTTOM.add(button_Send);


        add(panel_NORTH);
        add(panel_TITLE);
        add(panel_CENTER);
        add(panel_BOTTOM);

        //ActionListener
        button_Send.addActionListener(listener);

    }

    // Logik der Buttons
    ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == button_Back) {                         //Button rausgenommen
                System.out.println("Button_Back pressed ...");
            }
            // Wenn alle Textfelder bis auf Verwendungszweck gefüllt sind, wird die Bedingung ausgeführt, andernfalls wird eine Fehlermeldung ausgegeben
            else if (e.getSource() == button_Send && !textField_Receiver.getText().isEmpty() && !textField_ID.getText().isEmpty() && !textField_Amount.getText().isEmpty()) {
                System.out.println("Button_Send pressed ...");

                // Daten aus Textfeldern sammeln
                 receiver = textField_Receiver.getText();
                 receiver_ID = textField_ID.getText();
                 String amountString = textField_Amount.getText();
                 amount = Double.parseDouble(amountString);
                 message = textField_Message.getText();

                 // Wenn Button "Senden" gedrückt wird, wird der zu überweisende Betrag vom eingeloggten Customer abgezogen
                 if (e.getSource() == button_Send){
                     UserDataDAODBImpl userDataDAODB = new UserDataDAODBImpl();
                     ArrayList<Double> userBalanceArray;

                     try {
                         userBalanceArray = userDataDAODB.getBalanceUserArray();
                         double newBalance = userBalanceArray.getLast() - amount;
                         userDataDAODB.uploadTransactionToDatabase(newBalance, customer_ID, message, receiver_ID,customer_ID);

                         // Es werden die Kontostände vom Empfänger abgerufen und in einem Array gespeichert
                         userBalanceArray = userDataDAODB.getBalanceUserArray(receiver_ID);
                         if (userBalanceArray.size() < 1){
                             userBalanceArray.add(0.0);
                         }
                         newBalance = userBalanceArray.getLast() + amount;                // neuer Kontostand = Kontostand + empfangenes Geld
                         userDataDAODB.uploadTransactionToDatabase(newBalance, receiver_ID, message, customer_ID, receiver_ID );
                     } catch (DaoException ex) {
                         throw new RuntimeException(ex);
                     }
                 }
            }
        }
    };
}
