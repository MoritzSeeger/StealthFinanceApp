package Transactions;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class TransactionPanelLayout extends JPanel {
    TransactionPanel transactionPanel = new TransactionPanel();
    TransaktionLoadingScreen transaktionLoadingScreen = new TransaktionLoadingScreen();
    JPanel panelMain, panel_NORTH, panel_CENTER, panel_BOTTOM, column1, column2, column3;
    JLabel title, label_Column1, label_Column2, label_Column3;
    int xPosition = 10;
    public TransactionPanelLayout(){

        setBackground(Color.darkGray);
        setPreferredSize(new Dimension(1000,620));

        title = new JLabel("Transaktionen");
        title.setFont(new Font("Serif Sans",Font.BOLD,30));
        title.setForeground(Color.WHITE);
        title.setBackground(Color.darkGray);

        label_Column1 = new JLabel("Spalte 1");
        label_Column2 = new JLabel("Spalte 2");
        label_Column3 = new JLabel("Spalte 3");

        column1 = new JPanel();
        column1.add(label_Column1);

        column2 = new JPanel();
        column2.add(label_Column2);

        column3 = new JPanel();
        column3.add(label_Column3);

        panelMain = new JPanel();
        panelMain.setPreferredSize(new Dimension(1000,620));
        panelMain.setBackground(Color.darkGray);
        panelMain.setLayout(new BorderLayout(2,2));

        panel_NORTH = new JPanel();
        panel_NORTH.setPreferredSize(new Dimension(1000,100));
        panel_NORTH.setBackground(Color.red);


        panel_CENTER = new JPanel(new GridLayout(1,1));
        panel_CENTER.setBackground(Color.darkGray);
        panel_CENTER.add(transactionPanel);


        panel_BOTTOM = new JPanel();
        panel_BOTTOM.setPreferredSize(new Dimension(1000,100));
        panel_BOTTOM.setBackground(Color.green);


        panel_NORTH = new JPanel();
        panel_NORTH.setBackground(Color.darkGray);
        panel_NORTH.add(title);

        panelMain.add(panel_NORTH, BorderLayout.NORTH);
        panelMain.add(panel_CENTER, BorderLayout.CENTER);
        add(transaktionLoadingScreen);
        Timer timer = new Timer(5000, new ActionListener() {       // 5 Sekunden Delay, bis transaktionsScreen entfernt und panelMain hinz. wird
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(transaktionLoadingScreen);
                add(panelMain);
                revalidate();
                repaint();
            }
        });
        timer.start();
    }

    private class TransaktionLoadingScreen extends JPanel{
        JLabel labelTitle;
        TransaktionLoadingScreen(){
            setPreferredSize(new Dimension(300,100));
            setBackground(Color.darkGray);

            labelTitle = new JLabel("Transaktionen werden geladen");
            labelTitle.setForeground(Color.WHITE);
            add(labelTitle);

        }

        Timer timer = new Timer(10, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                xPosition += 2;
                if (xPosition > (getWidth())) {
                    xPosition = 2;
                }
                repaint();
            }
        });

        {
            timer.start(); // Start the timer immediately
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.WHITE);
            for (int i = 0; i < 5; i++){
                g.fillOval(xPosition, 50, 10,10);
            }
        }
    }
}
