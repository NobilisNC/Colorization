package colorization;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;


class Image extends JComponent {
    protected BufferedImage image;
    
    Image(BufferedImage img) {
        image = img;       
    }
    
    @Override
	public Dimension getPreferredSize() {
		if (image == null)
			return new Dimension(100,100);
		else 
			return new Dimension(image.getWidth(),image.getHeight());		
	}
	
        @Override
	public Dimension getMinimumSize()  {
		if (image == null) 
			return new Dimension(100,100);
		 else 
			return new Dimension(image.getWidth(),image.getHeight());	
	}
	
        @Override
	public Dimension getMaximumSize() {
		if (image == null)
			return new Dimension(100,100);
		else 
			return new Dimension(image.getWidth(),image.getHeight());
	}
	
        @Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(image != null)
			g.drawImage(image, 0, 0, getWidth(),getHeight(), null);		
	} 
    
}
