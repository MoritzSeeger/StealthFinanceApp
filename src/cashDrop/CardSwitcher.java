package cashDrop;

import javax.swing.*;
import java.awt.*;

public class CardSwitcher extends CardLayout {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;
	private CardLayout cardLayout;
	private JPanel container;
	
	public CardSwitcher(CardLayout cardLayout, JPanel container){
		this.cardLayout = cardLayout;
		this.container = container;
		
	}
	public void switchCards(String string) {
		cardLayout.show(container, string);
		
	}

}
