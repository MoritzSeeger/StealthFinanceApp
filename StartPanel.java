package userLogin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class StartPanel extends JPanel {
	
	private static CardSwitcher cardSwitcher;

    public static void setCardSwitcher(CardSwitcher switcher) {
        cardSwitcher = switcher;
    }

	
	private static final long serialVersionUID = 1L;
	
	JPanel panel = new JPanel();
	ImageIcon logo = new ImageIcon("Logo.PNG");
	Image image = logo.getImage();
	JButton firstButton = new RegularButton("New User",Color.decode("#1e59a5"));
	JButton secondButton = new RegularButton("Login", Color.decode("#009c0d"));
	JLabel label = new JLabel();



	public StartPanel() {
		
		setLayout(new BorderLayout());	
		setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));
		setBackground(Color.LIGHT_GRAY);	
		
		panel.setLayout(new FlowLayout());

		
		firstButton.addActionListener(new Clicker());
		firstButton.setActionCommand("1");
		secondButton.addActionListener(new Clicker());
		secondButton.setActionCommand("2");
		
		panel.setBackground(Color.decode("#3d3d3d"));
		panel.add(firstButton);
		panel.add(secondButton);
		add(panel, BorderLayout.SOUTH);
		
		label.setIcon(logo);
		label.setVerticalTextPosition(JLabel.TOP);
		add(label, BorderLayout.CENTER);
	}


	private class Clicker implements ActionListener {
	
		@Override
		public void actionPerformed(ActionEvent e) {
			String action = e.getActionCommand();
			
			if ("1".equals(action)) {
				String codeCheck = JOptionPane.showInputDialog(null,"Bitte geben Sie den aktivierungs code ein");
				if(codeCheck != null) {
					LoginBackEnd.activationCode(codeCheck);	
				}
			}			
			if ("2".equals(action)) {
				cardSwitcher.switchCards("3");;
			}
		}

	
	}
}