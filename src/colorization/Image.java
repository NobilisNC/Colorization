/*
 * This class is a simple Image class.
 * Override size and draw method of a JComponent.
 * Implements compareTo method.
 *
 */
package colorization;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;


class Image extends JComponent {
    
    protected BufferedImage image;
    //Color of black used to color lines
    static final int BLACK = Color.BLACK.getRGB(); 
  
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

    public int compareTo(Image other) {
        if (image.getWidth() != other.image.getWidth() || image.getHeight() != other.image.getHeight())
                 return 0;

        int nb_differents_pix = 0;
        int nb_pix_black = 0;
        for(int y = 0; y < image.getHeight(); y++ )
            for(int x = 0; x < image.getWidth(); x++ ) 
                if(image.getRGB(x, y) != other.image.getRGB(x,y))
                    nb_differents_pix++;
                else if (image.getRGB(x, y) == Color.black.getRGB())
                    nb_pix_black++;

        
        int nb_pix = image.getHeight()*image.getWidth() - nb_pix_black;

        return (int) ((double)(nb_pix - nb_differents_pix) / nb_pix * 100.d);            
    }
        
    
}
