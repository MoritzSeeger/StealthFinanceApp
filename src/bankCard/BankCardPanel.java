package bankCard;

import de.hwr.wm.ds.DaoException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class BankCardPanel extends JPanel {

	private ImageIcon cardIcon;
	private JLabel imageLabel = new JLabel();
	private JPanel topPanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
	private JLayeredPane pane = new JLayeredPane();
	private JLabel namelabel, cardNumberLabel, csvLabel, ibanLabel;
	private JButton generateButton = new JButton("Generate");
	private JButton exitButton = new JButton("Exit");
	private BankCardGUI frame;

	public BankCardPanel(BankCardGUI frame) {
		this.frame = frame;

		// Ressource laden
		java.net.URL imageURL = getClass().getResource("/Karte2.PNG");
		if (imageURL != null) {
			cardIcon = new ImageIcon(imageURL);
		} else {
			System.err.println("Bild konnte nicht geladen werden: /resources/Karte2.PNG");
			return;
		}

		setLayout(new BorderLayout());
		setBackground(Color.decode("#3d3d3d"));

		generateButton.setBackground(Color.green);
		generateButton.addActionListener(new Clicker());
		generateButton.setActionCommand("Generate");
		exitButton.setBackground(Color.red);
		exitButton.addActionListener(new Clicker());
		exitButton.setActionCommand("EXIT");

		imageLabel.setIcon(cardIcon);

		namelabel = new JLabel(BankCardBackEnd.generateName());
		cardNumberLabel = new JLabel(BankCardBackEnd.generateBankCardNumber());

		csvLabel = new JLabel("CSV: " + BankCardBackEnd.generateCSV());
		csvLabel.setForeground(Color.WHITE);
		ibanLabel = new JLabel("IBAN: " + BankCardBackEnd.generateIBAN());
		ibanLabel.setForeground(Color.WHITE);

		imageLabel.setBounds(0, 0, cardIcon.getIconWidth(), cardIcon.getIconHeight());
		namelabel.setBounds(80, 250, 200, 30);
		cardNumberLabel.setBounds(200, 145, 400, 100);

		pane.setPreferredSize(new Dimension(cardIcon.getIconWidth(), cardIcon.getIconHeight()));
		pane.setLayout(null);
		pane.add(imageLabel, JLayeredPane.DEFAULT_LAYER);
		pane.add(namelabel, JLayeredPane.MODAL_LAYER);
		pane.add(cardNumberLabel, JLayeredPane.PALETTE_LAYER);

		topPanel.setLayout(new GridLayout());
		topPanel.setBackground(Color.decode("#3d3d3d"));
		topPanel.add(ibanLabel);
		topPanel.add(csvLabel);

		bottomPanel.setLayout(new GridLayout());
		bottomPanel.add(generateButton);
		bottomPanel.add(exitButton);

		add(pane, BorderLayout.CENTER);
		add(topPanel, BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	class Clicker implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String action = e.getActionCommand();
			if ("Generate".equals(action)) {
				BankCardBackEnd bankCardBackEnd = new BankCardBackEnd();
				namelabel.setText(BankCardBackEnd.generateName());
				cardNumberLabel.setText(BankCardBackEnd.generateBankCardNumber());
				csvLabel.setText("CSV: " + BankCardBackEnd.generateCSV());
				ibanLabel.setText("IBAN: " + BankCardBackEnd.generateIBAN());
				revalidate();
				repaint();
				try {
					bankCardBackEnd.payment();
				} catch (DaoException ex) {
					ex.printStackTrace();
				}

			}
			if ("EXIT".equals(action)) {
				frame.dispose();
			}
		}

	}
}