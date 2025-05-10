package Hauptmenue;

import de.hwr.wm.ds.DaoException;

import javax.swing.*;

public class MainFrame{
    static JFrame frame = new JFrame("Banking App - Home");
    public  MainFrame() throws DaoException {

        JPanel panel = new MainPanel();
        frame.getContentPane().add(panel);
        frame.pack();

        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
