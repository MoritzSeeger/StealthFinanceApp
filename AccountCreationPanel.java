package userLogin;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AccountCreationPanel extends JPanel {
	

	private static final long serialVersionUID = 1L;
	
	JPanel extraJPanel = new JPanel(new GridLayout(3,2) );
	
			
	
	JButton buttonOne = new RegularButton("Neuer Nutzer: ", Color.decode("#009c0d"));
	JButton buttonTwo = new RegularButton("Benutzerkonto Erstellen", Color.decode("#009c0d"));
	
	JTextField textFieldOne = new JTextField();	
	JPasswordField textFieldTwo = new JPasswordField();
	JPasswordField textFieldThree = new JPasswordField(); 
	
	JLabel labelOne = new JLabel("Benutzername (mind. 8 Charakter): ");
	JLabel labelTwo = new JLabel("Passwort (mind. 8 Charakter): ");
	JLabel labelThree = new JLabel("Passwort wiederholen:");
	
	public AccountCreationPanel(){
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		ImageIcon logo = new ImageIcon("Logo.PNG");
		Image image = logo.getImage();
		setPreferredSize(new Dimension(image.getWidth(null),image.getHeight(null)));
		setBackground(Color.LIGHT_GRAY);
		
		
		buttonTwo.addActionListener(new Clicker());
		labelOne.setBackground(Color.black);
		extraJPanel.add(labelOne);
		extraJPanel.add(textFieldOne);
		extraJPanel.add(labelTwo);
		extraJPanel.add(textFieldTwo);
		extraJPanel.add(labelThree);
		extraJPanel.add(textFieldThree);
		add(extraJPanel, BorderLayout.CENTER);
		
		add(buttonOne, BorderLayout.NORTH);
		add(buttonTwo, BorderLayout.SOUTH);
	}
	private class Clicker implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String username = textFieldOne.getText();
			char[] passwordChar = textFieldTwo.getPassword();
			char[] passwordCheckChar = textFieldThree.getPassword();
			String password = new String(passwordChar);
			String passwordCheck = new String(passwordCheckChar);
			LoginBackEnd.addNewUser(username, password, passwordCheck);
		}
		
	}

}
