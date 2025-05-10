package cashDrop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import userLogin.CardSwitcher;

public class CashDropStartPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 127652380105979342L;
	private static CardSwitcher cardSwitcher;

    public static void setCardSwitcher(CardSwitcher switcher) {
        cardSwitcher = switcher;
    }
	private String[] cityStrings = {"Wählen Sie die Stadt","Berlin", "Hamburg", "München", "Köln",};
	private String[] moneyStrings = {"Wählen sie den Betrag (€)", "1000€", "5000€", "10000€", "20000€"};
	private JComboBox<String> cityBox = new JComboBox<String>(cityStrings);
	private JComboBox<String> moneyBox = new JComboBox<String>(moneyStrings);
	private JCheckBox checkBox = new JCheckBox("Ich akzeptiere die 20% Aufwandsgebühren");
	private JButton startButton = new JButton("Verbindlich Bestellen");
	
	
	public CashDropStartPanel(CashDropPanel cashDropPanel) {
		
		setLayout(new GridLayout(2,2));
	    setPreferredSize(new Dimension(500, 600));
	    
	    moneyBox.setBackground(Color.decode("#3e3e3e"));
		cityBox.setBackground(Color.decode("#3e3e3e"));
		checkBox.setBackground(Color.decode("#3e3e3e"));
		startButton.setBackground(Color.decode("#009c0d"));
		
		moneyBox.setForeground(Color.decode("#39ade9"));
		cityBox.setForeground(Color.decode("#39ade9"));
		checkBox.setForeground(Color.red);
		startButton.setForeground(Color.red);
		
		moneyBox.setFont(new Font("Arial", Font.BOLD, 18));
		cityBox.setFont(new Font("Arial", Font.BOLD, 18));
		checkBox.setFont(new Font("Arial", Font.BOLD, 10));
		startButton.setFont(new Font("Arial", Font.BOLD, 20));

		
		add(moneyBox);
		add(cityBox);	
		add(checkBox);
		add(startButton);
		
			
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedCity = (String) cityBox.getSelectedItem();
				String moneyValue = (String) moneyBox.getSelectedItem();
				
				if (checkBox.isSelected() && moneyValue != null && selectedCity != null) {

					if (selectedCity.equals("Hamburg")) {
					cashDropPanel.hamburg();
					}
					if (selectedCity.equals("Berlin")) {
						cashDropPanel.berlin();
					}
					if (selectedCity.equals("München")) {
						cashDropPanel.muenchen();
					}
					if (selectedCity.equals("Köln")) {
						cashDropPanel.koeln();
					}
					if (selectedCity.equals("Stadt Auswahl")) {
						return;
					}
					cardSwitcher.switchCards("2");	
					}
				else {
					JOptionPane.showMessageDialog(null, "Alle Felder müssen ausgefüllt sein");
					return;
				}
			}
		});

		

		
	}
}
