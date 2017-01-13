/*
 * This class provides functionalities to color the model of a coloring.
 * You can - color the model
 *         - change palette
 *         - name and save coloring
 * 
 */
package colorization;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;


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
        colors = new ColorPanel(null);
        coloring = new Coloring(ProcessedImage.copyImage(coloring_empty), this);
        
        save = new JButton("Sauvegarder le coloriage");

        save.addActionListener((ActionEvent e) -> {
            save();
        });
        
        JButton changeColor = new JButton("Choisir une couleur");
        changeColor.addActionListener((ActionEvent e) -> {
            chooseColor();
        });
        
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(changeColor);
        buttonsPanel.add(save);
        
        JPanel main = new JPanel();
        main.add(colors);
        main.add(coloring);
        
        this.add(buttonsPanel);
        this.add(main);      
        
    }
    
    @Override
    public Color getColor() {
        return colors.getSelectedColor();
    }
    
    private void save() {
        
            String name = JOptionPane.showInputDialog(this, "Entrez un nom de coloriage :");
            if (name == null) {
                JOptionPane.showMessageDialog(this, "Erreur de nom", "Erreur",JOptionPane.ERROR_MESSAGE);
                return;
            }
            DataHandler d = new DataHandler();
            d.saveColoring(name, colors.getPalette() , coloring_empty, coloring.getImage());

            parent.Ui_list();
    }
    
    public void chooseColor() {
            Color newColor;
            newColor = JColorChooser.showDialog(this, "Choisir une couleur", getColor() );
                        
            if (newColor.getRGB() == Image.BLACK) 
                newColor = new Color(254,254,254);
   
            
            coloring.replaceColor( getColor().getRGB() , newColor.getRGB());
            
            colors.setSelectedColor(newColor);            
        }
        
    
}
