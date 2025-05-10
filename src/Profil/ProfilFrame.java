package Profil;

import javax.swing.*;

public class ProfilFrame {
    public  ProfilFrame(){
        JFrame frame = new JFrame("Banking App - Profil");
        JPanel panel = new ProfilPanel();

        frame.getContentPane().add(panel);
        frame.pack();

        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
