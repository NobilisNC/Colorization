/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colorization;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import net.coobird.thumbnailator.*;


class CreationModelPanel extends JPanel {
    
    private final MainWindow parent;
    private ColorPanel colors;
    private Coloring coloring;
    private BufferedImage coloring_empty;
    private JButton save;
    
    
    
    public CreationModelPanel(BufferedImage _coloring_empty, MainWindow p) {
       parent = p;  
       coloring_empty = _coloring_empty;
       initComponents();
    }
    
    
    
    private void initComponents() {
        colors = new ColorPanel(true);
        
        coloring = new Coloring(ProcessedImage.copyImage(coloring_empty), this);
        
        save = new JButton("Sauvegarder le coloriage");

        save.addActionListener((ActionEvent e) -> {
            save();
        });
        
        this.add(colors);
        this.add(coloring);
        this.add(save);
    }
    
    protected Color getColor() {
        return colors.getSelectedColor();
    }
    
    private void save() {
        try {
            String name = JOptionPane.showInputDialog(this, "Entrez un nom de coloriage :");
            if (name == null) {
                JOptionPane.showMessageDialog(this, "Erreur de nom", "Erreur",JOptionPane.ERROR_MESSAGE);
                return;
            }
            DataHandler d = new DataHandler();
            BufferedImage thumbnail = Thumbnails.of(coloring.getImage()).size(200, 200).asBufferedImage();
            d.saveColoring(name, colors.getPalette() , coloring_empty, coloring.getImage(), thumbnail);
            } catch (IOException ex) {
                Logger.getLogger(CreationModelPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            parent.Ui_reset();
    }
    
}
