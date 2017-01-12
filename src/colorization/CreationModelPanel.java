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
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import net.coobird.thumbnailator.*;


class CreationModelPanel extends JPanel implements ColoringAccessColor {
    
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
        colors = new ColorPanel(true, null);
        
        coloring = new Coloring(ProcessedImage.copyImage(coloring_empty), this);
        
        save = new JButton("Sauvegarder le coloriage");

        save.addActionListener((ActionEvent e) -> {
            save();
        });
        
        JButton changeColor = new JButton("Choisir une couleur");
        changeColor.addActionListener((ActionEvent e) -> {
            chooseColor();
        });
        
        this.add(colors);
        this.add(coloring);
        this.add(save);
        this.add(changeColor);
    }
    
    @Override
    public Color getColor() {
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
            parent.Ui_list();
    }
    
    public void chooseColor() {
            Color newColor;
            newColor = JColorChooser.showDialog(this, "Choisir une couleur", getColor() );
            colors.setSelectedColor(newColor);            
        }
        
    
}
