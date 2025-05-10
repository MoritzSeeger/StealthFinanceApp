package cashDrop;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.*;
import userLogin.*;

public class CashDropUI extends JFrame {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3952905500528737096L;
	private CashDropPanel cashDropPanel = new CashDropPanel();
	private CardLayout cardLayout = new CardLayout();
	private JPanel cardPanel = new JPanel();
	private JPanel startPanel = new CashDropStartPanel(cashDropPanel);		


	
	public CashDropUI() {
		super.setName("Cash Drop");
		setMaximumSize(new Dimension(500,600));

		cardPanel.setLayout(cardLayout);
		cardPanel.add(startPanel, "1");
		cardPanel.add(cashDropPanel, "2");
		
		add(cardPanel);
		
		CardSwitcher cardSwitcher = new CardSwitcher(cardLayout, cardPanel);
		CashDropStartPanel.setCardSwitcher(cardSwitcher);
		
		setResizable(false);
		setLocation(700, 330);;
		pack();
		setVisible(true);	
	}

}
