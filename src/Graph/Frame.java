package Graph;

import de.hwr.wm.ds.DaoException;

import javax.swing.*;

public class Frame extends JFrame {
    public static void main(String[] args) throws DaoException {
        JFrame frame = new JFrame("Graph_Test");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new GraphMainPanel();

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(true);

    }
}
