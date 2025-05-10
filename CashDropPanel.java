package cashDrop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class CashDropPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7930355810530866784L;

	private JLayeredPane mapPane = new JLayeredPane();
	
	private JLabel firstLabel = new JLabel();
	private JLabel secondLabel = new JLabel();
	private JLabel thirdLabel = new JLabel();
	
	private ImageIcon hamburgIcon = new ImageIcon("Hamburg.png");
	private ImageIcon berlinIcon = new ImageIcon("Berlin.png");
	private ImageIcon münchenIcon = new ImageIcon("Muenchen.png");
	private ImageIcon kölnIcon = new ImageIcon("Koeln.png");
	private ImageIcon pinIcon = new ImageIcon("Pin.png");
	private ImageIcon loadingIcon = new ImageIcon("Loading.png");
	private int xRectPos = 0;
	private Timer timer;
	private ArrayList<Integer> xPosList = new ArrayList<>();
	
	public CashDropPanel() {
		
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(500,600));
				
		firstLabel.setBounds(0,0,hamburgIcon.getIconWidth(),hamburgIcon.getIconHeight());	
		secondLabel.setIcon(pinIcon);
		thirdLabel.setIcon(loadingIcon);
		thirdLabel.setBounds(150,150, loadingIcon.getIconWidth(), loadingIcon.getIconHeight());
		
		mapPane.setPreferredSize(new Dimension(500, 600));		
		mapPane.add(firstLabel, JLayeredPane.DEFAULT_LAYER);
		mapPane.add(secondLabel, JLayeredPane.PALETTE_LAYER);
		mapPane.add(thirdLabel, JLayeredPane.MODAL_LAYER);
		add(mapPane, BorderLayout.CENTER);

	}
	
	
	public void hamburg() {
		firstLabel.setIcon(hamburgIcon);
		timer();
	}
	public void berlin() {
		firstLabel.setIcon(berlinIcon);
		timer();
		repaint();
	}
	public void muenchen() {
		firstLabel.setIcon(münchenIcon);
		timer();
		repaint();
	}
	public void koeln() {
		firstLabel.setIcon(kölnIcon);
		timer();
		repaint();
	}
	private void pinDrop() {
		int xPos = (int) (Math.random() * 450);
		int yPos = (int) (Math.random() * 410);

		
		secondLabel.setBounds(xPos,yPos,pinIcon.getIconWidth(),pinIcon.getIconHeight());
		repaint();
	}
	private void timer() {
		if (timer !=null && timer.isRunning()) {
			return;
		}
		
	    timer = new Timer(345, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	
	            xPosList.add(xRectPos);
	            xRectPos += 25;
	            
	            if (xRectPos > 500) {
	            	timer.stop();
	            	mapPane.remove(thirdLabel);
	            	pinDrop();
	            }
	            
	            repaint();
	        }
	    });
	    timer.start();
	}
    	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		g.setColor(Color.red);

		for(int xPos : xPosList) {
			g.fillRect(xPos, 500, 23, 100);
		}
	}
}
