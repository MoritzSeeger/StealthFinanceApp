package Ueberweisung;

import javax.swing.*;

public class TestClass extends Ueberweisung_Panel{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Banking App - Überweisung");
        JPanel panel = new Ueberweisung_Panel();

        frame.getContentPane().add(panel);
        frame.pack();

        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
