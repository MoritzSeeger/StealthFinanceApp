package Ueberweisung;

import javax.swing.*;

public class UeberweisungFrame {
    public  UeberweisungFrame(){
        JFrame frame = new JFrame("Banking App - Überweisung");
        JPanel panel = new Ueberweisung_Panel();

        frame.getContentPane().add(panel);
        frame.pack();

        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
