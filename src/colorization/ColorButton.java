package colorization;

import java.awt.*;
import javax.swing.*;



class ColorButton extends JComponent {

	//Attributs
	 private Color maCouleur;
	 
	
	
	//Méthodes
	public ColorButton(Color color) {
		super();
		maCouleur = color;
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(100,100);
		
	}
	
	public Dimension getMinimumSize()  {
		return new Dimension(100,100);	
		
	}
	
	public Dimension getMaximumSize() {
		return new Dimension(100,100);
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		
		g.setColor(maCouleur);
		g.fillRect(0, 0, 100, 100);
		
	
		
	}	
	
	
}
