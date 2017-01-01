package colorization;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;



class ColorButton extends JComponent implements MouseListener {

	//Attributs
         private final ColorPanel parent; 
	 private Color maCouleur;
         private boolean selected;
         
         private static final int SIZE = 30;
	 
	
	
	//Méthodes
	public ColorButton(Color color, ColorPanel p) {
		super();
                parent = p;
		maCouleur = color;
                selected = false;
                addMouseListener(this);
	}
        
        public Color getColor() {
            return maCouleur;
        }
        
        public void setColor(Color new_c) {
            maCouleur = new_c;
        }
        
        public void select() {
            selected = true;
            repaint();
        }
        
        public void deselect() {
            selected = false;
            repaint();
        }
	
         @Override
	public Dimension getPreferredSize() {
		return new Dimension(SIZE,SIZE);
     	}
	
         @Override
	public Dimension getMinimumSize()  {
		return new Dimension(SIZE,SIZE);	
		
	}
	
         @Override
	public Dimension getMaximumSize() {
		return new Dimension(SIZE,SIZE);
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		
		g.setColor(maCouleur);
		g.fillRect(0, 0, SIZE, SIZE);	
                
                if (selected) {
                    g.setColor(Color.black);
                    g.drawRect(0, 0, SIZE-1, SIZE-1);
                }
                    
	}	

    @Override
    public void mouseClicked(MouseEvent me) {
        selected = true; 
        parent.setSelected(this);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {  
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
	
	
}
