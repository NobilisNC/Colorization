package colorization;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RescaleOp;
import java.awt.image.WritableRaster;
import java.io.*;
import java.util.*;

import javax.imageio.*;

public class Coloring extends JComponent implements MouseListener {
	;
	private BufferedImage default_image;
	private BufferedImage image;
	private WritableRaster wraster;
	private ColorModel color_model;
	
	private float contrast_1 = 1.0f;
	private float alpha = 0.9f;
	private int dist_x = 3;
	private int dist_y = 3;
	
	
	public Coloring() {
		super();
		image = null;
		addMouseListener(this);
		
	}

	public void loadImage(File file) {
		try {
			default_image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public float getContrast1() { return contrast_1;}
	public float getAlpha() { return alpha;}
	public float getDistX() { return dist_x;}
	public float getDistY() { return dist_y;}
	
	public void setContrast1(float c) { contrast_1 = c;}
	public void setAlpha(float a) { alpha = a;}
	public void setDistX(int d) { dist_x = d;}
	public void setDistY(int d) { dist_y = d;}
	
	public static BufferedImage copyImage(BufferedImage source){
	    BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
	    Graphics g = b.getGraphics();
	    g.drawImage(source, 0, 0, null);
	    g.dispose();
	    return b;
	}	
	private void setWorkCopy() {
		image = Coloring.copyImage(default_image);
		image = new BufferedImage(default_image.getWidth(), default_image.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D surfaceImg = image.createGraphics();
		surfaceImg.drawImage(default_image, null, null);  
		wraster = image.getRaster();
    	color_model = image.getColorModel();
	}

	public void baseCreation() {
		setWorkCopy();
		alpha();
		repaint();
	}
	
	
	public void advancedCreation() {
            setWorkCopy();
	    gray();
            RescaleOp contrast = new RescaleOp(contrast_1, 0, null);
            contrast.filter(image,image);   	
            sobel();
            reversed();
            alpha();

            line_marker_x();
            line_marker_y();

            repaint();
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
	
        /* ***  *** FILTRES *** *** */
        
	protected void reversed() {
		
		for(int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				wraster.setSample(x, y, 0, Math.abs(255 - wraster.getSample(x, y, 0)));	
				wraster.setSample(x, y, 1, Math.abs(255 - wraster.getSample(x, y, 1)));
				wraster.setSample(x, y, 2, Math.abs(255 - wraster.getSample(x, y, 2)));
			}	
		}	
	}
	
	
	

		
	private int checkBlack_x(int x, int y) {
		for( int i = x+1; i-x < dist_x && i < image.getWidth(); i++ ) 
			if (wraster.getSample(i, y, 0) == 0)
				return i-x;
		return 0;
	}
	
	protected void line_marker_x() {
		int y= 0, x = 0;

		while ( y < image.getHeight() ) {
			while( x < image.getWidth()) {
				int old_x = 0;	
				if (wraster.getSample(x, y, 0) == 0) {
					old_x = x;
					while(true && x < image.getWidth()){
						int x_dist = checkBlack_x(x,y);
						if (x_dist == 0)
							break;
						x += x_dist;
					}

					//On colorie
					for (int i = old_x; i < x && i < image.getWidth(); i++ ) {
						wraster.setSample(i, y, 0, 0);
						wraster.setSample(i, y, 1, 0);
						wraster.setSample(i, y, 2, 0);					
					}					
				}
				x++;
			}		
			y++;
			x = 0;
		}
	}
	
	private int checkBlack_y(int x, int y) {
		for( int i = y+1; i-y < dist_y && i < image.getHeight(); i++ ) 
			if (wraster.getSample(x, i, 0) == 0)
				return i-y;
		return 0;
	}
	
	
	
	protected void line_marker_y() {
		int y= 0, x = 0;
		
		while ( x < image.getWidth() ) {
			while( y < image.getHeight()) {
				int old_y = 0;	
				if (wraster.getSample(x, y, 0) == 0) {
					old_y = y;
					while(true && y < image.getHeight()){
						int y_dist = checkBlack_y(x,y);
						if (y_dist == 0)
							break;
						y += y_dist;
					}

					//On colorie
					for (int i = old_y; i < y && i < image.getWidth(); i++ ) {
						wraster.setSample(x, i, 0, 0);
						wraster.setSample(x, i, 1, 0);
						wraster.setSample(x, i, 2, 0);					
					}					
				}
				y++;
			}		
			x++;
			y = 0;
		}
		
	}

	//Image comprends pixel [0,0,0,255] ou [255,255,255,255]
	protected void alpha()
	{ 
		int precision = (int) (255 * alpha);
		
		Object white = color_model.getDataElements(Color.white.getRGB(), null);
		Object black = color_model.getDataElements(Color.black.getRGB(), null);
		
		for(int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				//On teste si la couleur x,y est proche de zéro 
				Object pix = wraster.getDataElements(x, y, null);
				
				if (color_model.getRed(pix) < precision && color_model.getGreen(pix) < precision && color_model.getBlue(pix) < precision )
					wraster.setDataElements(x, y, black);
				else
					wraster.setDataElements(x, y, white);						
			}	
		}
		
	}
	
	//Passe l'image en niveau de gris
	protected void gray() {
		//Niveau de gris = 0.299 Rouge + 0.587 Vert + 0.114 Bleu
		for(int x = 0; x < image.getWidth(); x++)
			for (int y = 0; y < image.getHeight(); y++) {
				int gray  = (int) (0.299 * wraster.getSample(x,y,0) + 0.587 * wraster.getSample(x,y,1) + 0.114 * wraster.getSample(x,y,2));
				wraster.setSample(x, y, 0, gray);
				wraster.setSample(x, y, 1, gray);
				wraster.setSample(x, y, 2, gray);
				//System.out.println(gray);
			}
		}
		
	//Cherche les lignes 
	protected void sobel() {
		int[] G= new int[image.getHeight()* image.getWidth()];
		int[][] sobel_x = {{-1,0,1},
				 	       {-2,0,2},
				 	       {-1,0,1}};

		int[][] sobel_y = {{-1,-2,-1},
				 		   {0,0,0},
				 		   {1,2,1}};
		int max = 0;
		
		for(int x = 1; x < image.getWidth()-2; x++)
			for (int y = 1; y < image.getHeight()-2; y++) {
				
				int pixel_x = (sobel_x[0][0] * wraster.getSample(x-1,y-1,0)) + (sobel_x[0][1] * wraster.getSample(x,y-1,0)) + (sobel_x[0][2] * wraster.getSample(x+1,y-1,0)) +
			              (sobel_x[1][0] * wraster.getSample(x-1,y,0))   + (sobel_x[1][1] * wraster.getSample(x,y,0))   + (sobel_x[1][2] * wraster.getSample(x+1,y,0)) +
			              (sobel_x[2][0] * wraster.getSample(x-1,y+1,0)) + (sobel_x[2][1] * wraster.getSample(x,y+1,0)) + (sobel_x[2][2] * wraster.getSample(x+1,y+1,0));
				
				int pixel_y = (sobel_y[0][0] * wraster.getSample(x-1,y-1,0)) + (sobel_y[0][1] * wraster.getSample(x,y-1,0)) + (sobel_y[0][2] * wraster.getSample(x+1,y-1,0)) +
			              (sobel_y[1][0] * wraster.getSample(x-1,y,0))   + (sobel_y[1][1] * wraster.getSample(x,y,0))   + (sobel_y[1][2] * wraster.getSample(x+1,y,0)) +
			              (sobel_y[2][0] * wraster.getSample(x-1,y+1,0)) + (sobel_y[2][1] * wraster.getSample(x,y+1,0)) + (sobel_y[2][2] * wraster.getSample(x+1,y+1,0));
				
				int val = (int) Math.sqrt(pixel_x*pixel_x + pixel_y*pixel_y);
				if (val > max)
					max = val;
				G[x + y*image.getWidth()] = val;
			}
                        float ratio = max/255;
			
			for(int x = 0; x < image.getWidth(); x++)
				for (int y = 0; y < image.getHeight(); y++) {
				int sum = (int) (G[y*image.getWidth()+x]/ratio);
				wraster.setSample(x, y, 0, sum);
				wraster.setSample(x, y, 1, sum);
				wraster.setSample(x, y, 2, sum);
			}
				
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
    	
    	int rgb_new_color = Color.red.getRGB();
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
    	//System.out.println(e.getX() + " , " + e.getY());
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
