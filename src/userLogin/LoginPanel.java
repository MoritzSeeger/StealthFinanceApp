package userLogin;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	
	JTextField usernamefield = new JTextField();
	JPasswordField passwordField = new JPasswordField();
	JLabel oneJLabel = new JLabel("Benutzername: ");
	JLabel twoJLabel = new JLabel("Passwort: ");
	
	JButton oneButton = new RegularButton("Enter", Color.decode("#009c0d"));
	JButton twoButton = new RegularButton("New User",Color.decode("#1e59a5"));
	
	public LoginPanel() {
		setLayout(new GridLayout(3,2));
		
		oneButton.addActionListener(new Clicker());
		oneButton.setActionCommand("1");
		
		twoButton.addActionListener(new Clicker());
		twoButton.setActionCommand("2");;
		
		
		
		add(oneJLabel);
		add(usernamefield);
		add(twoJLabel);
		add(passwordField);
		add(twoButton);
		add(oneButton);

	}
	private class Clicker implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String action = e.getActionCommand();
			if ("1".equals(action)) {
				String username = usernamefield.getText();
				char[] passwordChar = passwordField.getPassword();
				String password = new String(passwordChar);
				LoginBackEnd.loginParser(username, password);
				
			}
			if ("2".equals(action)) {
				String codeCheck = JOptionPane.showInputDialog(null,"Bitte geben Sie den aktivierungs code ein");
				if(codeCheck != null) {
					LoginBackEnd.activationCode(codeCheck);	
			}
			
		}
	

		}
	}
}