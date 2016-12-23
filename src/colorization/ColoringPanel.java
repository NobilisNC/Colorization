/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colorization;

import javax.swing.JPanel;
import java.awt.Color;

class ColoringPanel extends JPanel {
    
    MainWindow parent;
    ColorPanel colors;
    Coloring coloring;
    
    
    
    public ColoringPanel(MainWindow p) {
       parent = p;  
       initComponents();
    }
    
    
    
    private void initComponents() {
        colors = new ColorPanel();
        
        coloring = new Coloring(this);
        
        this.add(colors);
        this.add(coloring);
    }
    
    protected Color getColor() {
        
        return Color.red;
    }
    
}
