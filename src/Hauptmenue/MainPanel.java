package Hauptmenue;

import Graph.GraphPanel;
import Transactions.TransaktionFrame;
import Ueberweisung.UeberweisungFrame;
import bankCard.BankCardGUI;
import bankCard.BankCardPanel;
import cashDrop.CashDropUI;
import de.hwr.wm.ds.DaoException;
import userLogin.BenutzerGUI;
import Profil.ProfilFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel {
    JPanel panel_NORTH, panel_CENTER, panel1_PanelCenter, panel2_PanelCenter, panel_BOTTOM;
    JLabel label9, label10;
    JButton profilButton;
    JButton bankkartePanel;
    JButton cashdroptButton;
    JButton logoutButton;
    JButton ueberweisungButton;
    JButton transaktionButton;
    JButton button7;
    JButton button8;
    JButton einzahlenButton;
    String backgroundImage = "/LogoEDDITED.PNG";

    MainPanel() throws DaoException {
        setBackground(Color.decode("#3d3d3d"));
        setPreferredSize(new Dimension(1000,620));


        label9 = new JLabel("Panel 9");
        label10 = new JLabel("Panel 10");

        panel1_PanelCenter = new JPanel();
        panel2_PanelCenter = new GraphPanel();

        // Button erstellung und listener Zuweisung
        profilButton = new JButton("Profil");
        bankkartePanel = new JButton("Kreditkarte (20€)");
        cashdroptButton = new JButton("Cashdrop");
        logoutButton = new JButton("Logout");
        ueberweisungButton = new JButton("Überweisen");
        transaktionButton = new JButton("Transaktionen");
        button7 = new JButton("Coming soon");
        button7.setEnabled(false);
        button8 = new JButton("Coming soon");
        button8.setEnabled(false);
        einzahlenButton = new JButton("+ Einzahlen");
        einzahlenButton.setForeground(Color.decode("#69B41E"));
        einzahlenButton.setFont(new Font("Sans Serif",Font.BOLD,18));
        einzahlenButton.setBackground(Color.decode("#3d3d3d"));
        einzahlenButton.setOpaque(true);


        profilButton.addActionListener(listener);
        bankkartePanel.addActionListener(listener);
        cashdroptButton.addActionListener(listener);
        logoutButton.addActionListener(listener);
        ueberweisungButton.addActionListener(listener);
        transaktionButton.addActionListener(listener);
        button7.addActionListener(listener);
        button8.addActionListener(listener);
        einzahlenButton.addActionListener(listener);

        // Panle North Erstellung
        panel_NORTH = new JPanel();
        panel_NORTH = new JPanel(new FlowLayout(FlowLayout.CENTER, 100,30));
        panel_NORTH.setPreferredSize(new Dimension(1000,100));
        panel_NORTH.setBackground(Color.decode("#3d3d3d"));


        // Panel_NORTH Konfiguration
        profilButton.setPreferredSize(new Dimension(150,50));

        bankkartePanel.setPreferredSize(new Dimension(150,50));

        cashdroptButton.setPreferredSize(new Dimension(150,50));

        logoutButton.setPreferredSize(new Dimension(150,50));

        panel_NORTH.add(profilButton);
        panel_NORTH.add(bankkartePanel);
        panel_NORTH.add(cashdroptButton);
        panel_NORTH.add(logoutButton);

        // Panel_CENTER Konfiguration
        panel_CENTER = new JPanel();
        panel_CENTER = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,0));
        panel_CENTER.setPreferredSize(new Dimension(1000,400));
        panel_CENTER.setBackground(Color.decode("#3d3d3d"));


        panel1_PanelCenter = new BackgroundPanel(backgroundImage);
        panel1_PanelCenter.setPreferredSize(new Dimension(600,400));
        panel1_PanelCenter.add(einzahlenButton);
        panel1_PanelCenter.setBackground(Color.decode("#3d3d3d"));

        // GraphicPanel Konfiguration
        panel2_PanelCenter.setPreferredSize(new Dimension(400,400));
        panel2_PanelCenter.setBackground(Color.decode("#3d3d3d"));

        panel_CENTER.add(panel1_PanelCenter);
        panel_CENTER.add(panel2_PanelCenter);




        // Panel_BOTTOM Konfiguration

        panel_BOTTOM = new JPanel();

        panel_BOTTOM = new JPanel(new FlowLayout(FlowLayout.CENTER, 100,30));
        panel_BOTTOM.setPreferredSize(new Dimension(1000,100));
        panel_BOTTOM.setBackground(Color.decode("#3d3d3d"));

        ueberweisungButton.setPreferredSize(new Dimension(150,50));

        transaktionButton.setPreferredSize(new Dimension(150,50));

        button7.setPreferredSize(new Dimension(150,50));

        button8.setPreferredSize(new Dimension(150,50));

        panel_BOTTOM.add(ueberweisungButton);
        panel_BOTTOM.add(transaktionButton);
        panel_BOTTOM.add(button7);
        panel_BOTTOM.add(button8);

        add(panel_NORTH);
        add(panel_CENTER);
        add(panel_BOTTOM);
    }
    ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == profilButton){
                System.out.println("Button 1 pressed ...");
                new ProfilFrame();

            } else if (e.getSource() == bankkartePanel) {
                System.out.println("Button 2 pressed ...");
                new BankCardGUI();

            } else if (e.getSource() == cashdroptButton) {
                System.out.println("Button 3 pressed ...");
                new CashDropUI();

            }  else if (e.getSource() == logoutButton) {
                System.out.println("Button 4 pressed ...");
                MainFrame.frame.dispose();
                new BenutzerGUI();
            } else if (e.getSource() == ueberweisungButton) {
                System.out.println("Button 5 pressed ...");
                new UeberweisungFrame();

            }  else if (e.getSource() == transaktionButton) {
                System.out.println("Button 6 pressed ...");
                new TransaktionFrame();

            } else if (e.getSource() == einzahlenButton){
                System.out.println("Button +Einzahlen pressed ...");
                try {
                    new EinzahlenFrame();
                } catch (DaoException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

    };
}
