package userLogin;
import javax.swing.*;
import java.awt.*;


public class BenutzerGUI extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5L;
	JFrame frame = new JFrame("Neuer Benutzer");
	CardLayout cardLayout = new CardLayout();


	
	public BenutzerGUI() {
	
		JPanel startPanel = new StartPanel();
		JPanel accountCreationPanel = new AccountCreationPanel();
		JPanel loginPanel = new LoginPanel();
		JPanel cardPanel = new JPanel();	

		cardPanel.setLayout(cardLayout);
		cardPanel.add(startPanel, "1");
		cardPanel.add(accountCreationPanel, "2");
		cardPanel.add(loginPanel, "3");
		
		cardLayout.show(cardPanel,"1");
		CardSwitcher cardSwitcher = new CardSwitcher(cardLayout, cardPanel);
		
		LoginBackEnd.setCardSwitcher(cardSwitcher);
		StartPanel.setCardSwitcher(cardSwitcher);
		add(cardPanel);
		

		pack();
		setBackground(Color.darkGray);
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocation(700, 330);;
		setVisible(true);
		
	}

}
