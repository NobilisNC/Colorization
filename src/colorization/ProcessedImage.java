/*
 * This class extends Image class
 * It specializes this class by adding some filters to apply
 *      - Alpha filter
 *      - Sobel filter
 *      - Reversed filter
 *      - Accent Line
 *      - Gray Mode     
 * 
 */




package colorization;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RescaleOp;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.coobird.thumbnailator.*;
import options.Options;

class ProcessedImage extends Image {

	
	private BufferedImage default_image;
	private WritableRaster wraster;
	//private ColorModel color_model;
	
	private float contrast_1 = 1.0f;
        protected float alpha = 0.9f;
	private int dist_x = 3;
	private int dist_y = 3;
	
	public ProcessedImage() {
		super(null);
		image = null;		
	}

	public void loadImage(File file) {
            try {
                default_image = Thumbnails.of(file).size(Options.dimensionMaxColoring.width, Options.dimensionMaxColoring.height).asBufferedImage();
            } catch (IOException ex) {
                Logger.getLogger(ProcessedImage.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
        
        public void saveImage(File file) {
            try {
                ImageIO.write(image, "png", file);
            } catch (IOException ex) {
                Logger.getLogger(ProcessedImage.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        public BufferedImage getImage() {
            return ProcessedImage.copyImage(image);
        }
	
	public float getContrast1() { return contrast_1;}
	public float getDistX() { return dist_x;}
	public float getDistY() { return dist_y;}
	
	public void setContrast1(float c) { contrast_1 = c;}
	public void setDistX(int d) { dist_x = d;}
	public void setDistY(int d) { dist_y = d;}
        
         public float getAlpha() { return alpha;}
        public void setAlpha(float a) { alpha = a;}
	
	public static BufferedImage copyImage(BufferedImage source){
	    BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
	    Graphics g = b.getGraphics();
	    g.drawImage(source, 0, 0, null);
	    g.dispose();
	    return b;
	}
        
      
        
	private void setWorkCopy() {
		image = ProcessedImage.copyImage(default_image);
		image = new BufferedImage(default_image.getWidth(), default_image.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D surfaceImg = image.createGraphics();
		surfaceImg.drawImage(default_image, null, null);  
		wraster = image.getRaster();
                //color_model = image.getColorModel();
	}

	public void baseCreation() {
		setWorkCopy();
		alpha(alpha);
		repaint();
	}
	
	
	public void advancedCreation() {
            setWorkCopy();
	    gray();
            RescaleOp contrast = new RescaleOp(contrast_1, 0, null);
            contrast.filter(image,image);   	
            sobel();
            reversed();
            alpha(alpha);

            accentLine_x();
            accentLine_y();

            repaint();
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
	
	protected void accentLine_x() {
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
	
	
	
	protected void accentLine_y() {
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
		
	//Find Lines
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
        
        //Image comprends pixel [0,0,0,255] ou [255,255,255,255]
	public void alpha(float alpha)
	{ 
                WritableRaster wraster = image.getRaster();
                ColorModel color_model = image.getColorModel();
                
		int precision = (int) (255 * alpha);
		
		Object white = color_model.getDataElements(0xFFFFFF, null);
		Object black = color_model.getDataElements(BLACK, null);
		
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

}

    

