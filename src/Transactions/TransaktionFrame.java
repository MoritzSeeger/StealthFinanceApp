package Transactions;
import javax.swing.*;

public class TransaktionFrame{

    public  TransaktionFrame(){
        JFrame frame = new JFrame("Banking App - Transaktionen");
        JPanel panel = new TransactionPanelLayout();

        frame.getContentPane().add(panel);
        frame.pack();

        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
