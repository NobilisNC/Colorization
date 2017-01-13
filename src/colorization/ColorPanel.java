/*
 * This class is a container of ColorButton
 *
 */
package colorization;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author nobilis
 */
class ColorPanel extends JPanel {
    private ColorButton[] colors;
    private ColorButton selected;
    
    private static int NB_BUTTONS_DEFAULT = 20;
    
    public ColorPanel(int[] palette) {
        super();
       
        initComponents(palette);
    }
    
    private void initComponents(int[] palette) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        int[] default_colors = { 0xFFFFFF, // Blanc
                                 0x010101,  // Noir
                                 0x0000FF, // Bleu
                                 0x00FF00, // Vert
                                 0xFF0000, // Rouge
                                 0xFFFF00, // Jaune
                                 0x00FFFF, // Cyan
                                 0xFF00FF, // Magenta
                                 0xAAAAAA, // Gris 
                                 0xDDDDDD, //Gris clair
                                 0xFF3467,  // Saumon
                                 0xFFFFFF, // Blanc
                                 0x010101,  // Noir
                                 0x0000FF, // Bleu
                                 0x00FF00, // Vert
                                 0xFF0000, // Rouge
                                 0xFFFF00, // Jaune
                                 0x00FFFF, // Cyan
                                 0xFF00FF, // Magenta
                                 0xAAAAAA, // Gris 
                                 0xDDDDDD, //Gris clair
                                 0xFF3467  // Saumon
                                };
        if (palette == null)        
            setPalette(default_colors);
        else 
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
        //Deselection de l'ancienne couleur :
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
          colors = new ColorButton[new_palette.length];
         
           for (int i = 0; i < new_palette.length; i++) {
            colors[i] = new ColorButton(new Color(new_palette[i]), this );
            add(colors[i]);
         }
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);      
    }
    
}
