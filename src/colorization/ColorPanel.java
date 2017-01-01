/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colorization;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 *
 * @author nobilis
 */
class ColorPanel extends JPanel {
    private ColorButton[] colors;
    private ColorButton selected;
    private boolean modifiable;
    
    private static int NB_BUTTONS = 10;
    
    public ColorPanel(boolean modif, int[] palette) {
        super();
        colors = new ColorButton[NB_BUTTONS];
        modifiable = modif;
        initComponents(palette);
    }
    
    private void initComponents(int[] palette) {
        setLayout(new GridLayout(NB_BUTTONS,2));
        
        int[] default_colors = { 0xFFFFFF, // Blanc
                                 0x010101,  // Noir
                                 0x0000FF, // Bleu
                                 0x00FF00, // Vert
                                 0xFF0000, // Rouge
                                 0xFFFF00, // Jaune
                                 0x00FFFF, // Cyan
                                 0xFF00FF, //Magenta
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
    
    public void setSelected(ColorButton b) {
        //Deselection de l'ancienne couleur :
        selected.deselect();
        selected = b;
        revalidate();
    }
    
    public int[] getPalette() {
        int[] palette = new int[NB_BUTTONS];
        
        for (int i = 0; i < NB_BUTTONS; i++) 
            palette[i] = colors[i].getColor().getRGB();
        
        

        return palette;
    }
    
    public void setPalette(int[] new_palette) {
           for (int i = 0; i < 10; i++) {
            colors[i] = new ColorButton(new Color(new_palette[i]), this );
            add(colors[i]);
         }
        
    }
    
}
