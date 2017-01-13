/*
 * This class is a container of ColorButton
 *
 */
package colorization;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author nobilis
 */
class ColorPanel extends JPanel {
    private ColorButton[] colors;
    private ColorButton selected;
    
    private static int NB_BUTTONS_MAX = 20;
    
    public ColorPanel(int[] palette) {
        super();
        
        int[] default_colors = {
                         0xAF74C4, 
                         0x6A49B1, 
                         0x2F38C5, 
                         0x046BEB, 
                         0x02A2FA, 
                         0x00BFEB, 
                         0x02859F, 
                         0x317A5F, 
                         0x01C9AD,  
                         0x02F2B1, 
                         0x98E678, 
                         0xE5FE58, 
                         0xECF214, 
                         0xFFD100, 
                         0xFFA801, 
                         0xFE7000, 
                         0xFE7EAE,  
                         0xFE2B60,
                         0xFE3F05,
                         0xF13519
                        };
        
        if(palette == null)
            palette = default_colors;
 
       
        initComponents(palette);
    }
    
    private void initComponents(int[] palette) {
        setLayout(new GridLayout(palette.length/2, 2));
        
        setPalette(palette);
        

        selected = colors[0];      
        colors[0].select();
    }
    
    public Color getSelectedColor() {
        return selected.getColor();
    }
    
     public void setSelectedColor(Color c) {
        selected.setColor(c);
        repaint();
     }
    
    public void setSelected(ColorButton b) {
        //Deselect the old color
        selected.deselect();
        selected = b;
        revalidate();
    }
    
    public int[] getPalette() {
        int[] palette = new int[colors.length];
        
        for (int i = 0; i < colors.length; i++) 
            palette[i] = colors[i].getColor().getRGB();
        
        

        return palette;
    }
    
    public void setPalette(int[] new_palette) {
        
        int NB = (new_palette.length > NB_BUTTONS_MAX ? NB_BUTTONS_MAX : new_palette.length );
        
        colors = new ColorButton[NB];
         
        for (int i = 0; i < NB; i++) {
            colors[i] = new ColorButton(new Color(new_palette[i]), this );
            add(colors[i]);
         }
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);      
    }

    boolean hasColor(Color color) {
        for (ColorButton c : colors) 
            if (c.getColor().getRGB() == color.getRGB()) 
                return true;
        
     
        return false;        
    }
    
}
