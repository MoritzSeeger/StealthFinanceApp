package userLogin;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

public class RegularButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	public RegularButton (String text, Color color){
		super(text);
		this.setBackground(color);
		this.setPreferredSize(new Dimension(150,50));
		this.repaint();
	}

}
