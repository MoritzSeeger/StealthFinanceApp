package Profil;

import userLogin.LoginBackEnd;

import javax.swing.*;
import java.awt.*;

public class ProfilPanel extends JPanel {

    String customerID = LoginBackEnd.customer_ID;
    JLabel customerID_Label;
    ProfilPanel(){
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400,200));
        setBackground(Color.decode("#3d3d3d"));
        customerID_Label = new JLabel("Customer ID: " + customerID);
        customerID_Label.setForeground(Color.WHITE);
        add(customerID_Label, BorderLayout.CENTER);
    }
}
