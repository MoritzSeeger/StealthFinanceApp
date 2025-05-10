package Hauptmenue;

import de.hwr.wm.ds.DaoException;
import de.hwr.wm.ds.UserDataDAODBImpl;
import Graph.GraphPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDate;

public class EinzahlenFrame extends JFrame {
    UserDataDAODBImpl userDataDAODB = new UserDataDAODBImpl();
    private String empfaenger = "";
    private String iban = "";
    JLabel ibanLabel, empfaengerLabel, betragLabel;
    JTextArea hinweisJTextArea;
    JPanel hinweisPanel, ibanPanel, empfaengerPanel, mainPanel, betragPanel;
    JTextField betragTextField;
    GraphPanel graphPanel = new GraphPanel();


    public EinzahlenFrame() throws DaoException {
        loadData();

        // JFrame-Einstellungen
        setTitle("BankingApp - Einzahlen");
        setSize(320, 350);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Schließt nur dieses Fenster
        setResizable(false);

        // Haupt-Panel
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.darkGray);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        // Hinweis Panel
        hinweisPanel = new JPanel();
        hinweisPanel.setPreferredSize(new Dimension(300, 50));
        hinweisPanel.setBackground(Color.gray);
        hinweisJTextArea = new JTextArea("Sende einen gewünschten Betrag per \nBanküberweisung an folgendes Konto");
        hinweisJTextArea.setEditable(false); // Nicht editierbar
        hinweisJTextArea.setFocusable(false); // Fokus deaktivieren (wie ein Label)
        hinweisJTextArea.setFont(new Font("Sans Serif", Font.BOLD, 14));
        hinweisJTextArea.setOpaque(true);
        hinweisJTextArea.setBackground(Color.gray);
        hinweisJTextArea.setForeground(Color.WHITE);
        hinweisPanel.add(hinweisJTextArea);

        // IBAN Panel
        ibanPanel = new JPanel();
        ibanPanel.setPreferredSize(new Dimension(300, 50));
        ibanPanel.setBackground(Color.darkGray);
        ibanLabel = new JLabel("IBAN: " + iban);
        ibanLabel.setForeground(Color.WHITE);
        ibanPanel.add(ibanLabel);

        // Empfänger Panel
        empfaengerPanel = new JPanel();
        empfaengerPanel.setPreferredSize(new Dimension(300, 50));
        empfaengerPanel.setBackground(Color.darkGray);
        empfaengerLabel = new JLabel("Empfänger: " + empfaenger);
        empfaengerLabel.setForeground(Color.WHITE);
        empfaengerPanel.add(empfaengerLabel);

        // Betrag Panel
        betragPanel = new JPanel();
        betragPanel.setPreferredSize(new Dimension(300, 50));
        betragPanel.setBackground(Color.darkGray);
        betragLabel = new JLabel("Betrag (€): ");
        betragLabel.setForeground(Color.WHITE);
        betragTextField = new JTextField();
        betragTextField.setPreferredSize(new Dimension(110, 50));
        betragTextField.addKeyListener(keyListener);
        betragTextField.addActionListener(listener);

        betragPanel.add(betragLabel);
        betragPanel.add(betragTextField);

        // Panels zum Haupt-Panel hinzufügen
        mainPanel.add(hinweisPanel);
        mainPanel.add(empfaengerPanel);
        mainPanel.add(ibanPanel);
        mainPanel.add(betragPanel);

        // Haupt-Panel zum JFrame hinzufügen
        add(mainPanel);
        setLocationRelativeTo(null); // Fenster in der Mitte des Bildschirms anzeigen
        setVisible(true);
    }

    KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                System.out.println("Enter pressed ...");
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    };

    ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == betragTextField) {
                LocalDate localDate =  LocalDate.now();
                String betrag = betragTextField.getText();
                System.out.println("Eingegebener Betrag: " + betrag + "€");
                double betragToDouble = Double.parseDouble(betrag);
                double currentBalancetToDouble = 0;

                try {
                    currentBalancetToDouble = graphPanel.checkArrayBalance();
                    double newBalance = betragToDouble + currentBalancetToDouble;
                    userDataDAODB.addBalanceFromBank(newBalance, localDate ,iban);
                } catch (DaoException ex) {
                    throw new RuntimeException(ex);
                }

            }
        }
    };
    public void loadData() {
        try {
            iban = userDataDAODB.getIban();
            empfaenger = userDataDAODB.getUser_Name();
        } catch (DaoException ex){
            throw new RuntimeException(ex);
        }

    }
}
