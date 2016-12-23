/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colorization;

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author nobilis
 */
class ColorPanel extends JPanel {
    private ColorButton[] colors;
    private ColorButton selected;
    
    public ColorPanel() {
        super();
        colors = new ColorButton[10];
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
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
        
        for (int i = 0; i < 10; i++) {
            colors[i] = new ColorButton(new Color(default_colors[i]), this );
            add(colors[i]);
         }
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
    
}
