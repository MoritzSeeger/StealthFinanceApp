package bankCard;

import de.hwr.wm.ds.DaoException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;


import javax.swing.JFrame;


public class BankCardGUI extends JFrame {
	
	private BankCardPanel bankCardPanel;
	
	public BankCardGUI() {
		BankCardBackEnd bankCardBackEnd = new BankCardBackEnd();
		try {
			bankCardBackEnd.payment();
		}catch (DaoException ex){
			ex.printStackTrace();
		}
		bankCardPanel = new BankCardPanel(this);
		setSize(new Dimension(500,500));
		setBackground(Color.black);
		setLayout(new GridLayout(1,1));
		setResizable(false);
		setLocation(700, 330);
		add(bankCardPanel, this);
		pack();
		setVisible(true);

	}


}
