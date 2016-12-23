/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colorization;

import javax.swing.JPanel;
import java.awt.Color;
import java.io.File;

class ColoringPanel extends JPanel {
    
    private final MainWindow parent;
    private ColorPanel colors;
    private Coloring coloring;
    private final String path ;
    
    
    
    public ColoringPanel(String _path, MainWindow p) {
       parent = p;  
       path = _path;
       initComponents();
    }
    
    
    
    private void initComponents() {
        colors = new ColorPanel();
        
        coloring = new Coloring(this);
        File file = new File(path);
        coloring.loadImage(file);
        
        this.add(colors);
        this.add(coloring);
    }
    
    protected Color getColor() {
        
        return colors.getSelectedColor();
    }
    
}
