package colorization;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;
import java.util.*;

import javax.imageio.*;
import javax.swing.JComponent;

public class Coloring extends JComponent implements MouseListener {
	
        private final ColoringPanel parent;
	private BufferedImage image;
	private WritableRaster wraster;
	private ColorModel color_model;
        private Color color;

	
	
	public Coloring(ColoringPanel p) {
		super();
                parent = p;
		image = null;
		addMouseListener(this);
	
	}

	public void loadImage(File file) {
		try {
			image = ImageIO.read(file);
                        wraster = image.getRaster();
                        color_model = image.getColorModel();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
        
       public void setColor(Color c) {
            color = c;
        }
    
        public void setColor() {
            color = parent.getColor();
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
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(image != null)
			g.drawImage(image, 0, 0, getWidth(),getHeight(), null);		
	} 
	
   // ALGO de Remplissage
    private boolean isTargetColor(int x, int y,int targetColor) {
    	if (x < image.getWidth() && x > 0 && y > 0 && y < image.getHeight()) {
	    	Object pix = wraster.getDataElements(x, y, null);
	    	int pix_color = color_model.getRGB(pix);
	    	
                return pix_color == targetColor;
    	} else {
    		return false;
    	}
    }
    

    
    private void color(int x,int y) {
    	
    	Object root_pix = wraster.getDataElements(x, y, null);
    	int targetColor = color_model.getRGB(root_pix);
    	if (targetColor == Color.black.getRGB())
    		return;
        if (targetColor == color.getRGB())
                return;
    	
    	int rgb_new_color = color.getRGB();
    	Object new_color = color_model.getDataElements(rgb_new_color,null);
    	    	
    	
    	Stack<Integer> P_x =  new Stack<>();
    	Stack<Integer> P_y =  new Stack<>();
    	
    	if (isTargetColor(x,y,targetColor)) {
    		P_x.push(x);
    		P_y.push(y);
    	}

    	while(!P_x.empty() && !P_y.empty()) {
    		int current_x = P_x.pop();
    		int current_y = P_y.pop();
    		
    		if (isTargetColor(current_x,current_y,targetColor)) {
	    		int w_x = current_x-1;
	    		int w_y = current_y;
	    		int e_x = current_x+1;
	    		int e_y = current_y;
	    		
	    		while(w_x > 0 && isTargetColor(w_x,w_y,targetColor))
	    			w_x--;
	    		
	    		while(e_x < image.getWidth() && isTargetColor(e_x,e_y,targetColor))
	    			e_x++;
	    		
	    		for(int p = w_x; p < e_x; p++) {
	    			if (p > 0 && p < image.getWidth())
	    				wraster.setDataElements(p, current_y,  new_color);
	    			if (current_y-1 > 0 && current_y-1 < image.getHeight() && isTargetColor(p, current_y-1, targetColor)) {
	    				P_x.push(p);
	    				P_y.push(current_y-1);
	    			}
	    			
	    			if (current_y+1 > 0 && current_y+1 < image.getHeight() && isTargetColor(p, current_y+1, targetColor)) {
	    				P_x.push(p);
	    				P_y.push(current_y+1);
	    			}    			
	    		}	
    		}   
    		repaint();
    	}
     }
    
    
    /* *** *** SLOTS *** *** */
    
        @Override
    public void mouseClicked(MouseEvent e) {
        setColor();
       color(e.getX(), e.getY());
    }
    
        @Override
    public void mousePressed(MouseEvent e) {}
        @Override
    public void mouseEntered(MouseEvent e) {}
        @Override
    public void mouseExited(MouseEvent e) {}
        @Override
    public void mouseReleased(MouseEvent e) {}


    
	
}
