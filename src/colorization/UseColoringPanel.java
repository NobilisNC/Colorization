/*
 * This class provides functionalities to paint the Coloring
 * It displays the coloring, its model ant the color palette.
 *
 * Its allows to check if the coloring is the same as its model.
 *
 */



package colorization;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.coobird.thumbnailator.*;
import options.Options;

class UseColoringPanel extends JPanel implements ColoringAccessColor {
    
    private final MainWindow parent;
    
    private Coloring coloring;
    private Image model;
    private BufferedImage thumbnail;
    private ColorPanel colors;
    private JButton finish;
    
    public UseColoringPanel(Data data, MainWindow _parent) {
        super();
        parent = _parent;
        
        initComponents(data);
    }

    private void initComponents(Data data) {
        
        
        coloring = new Coloring(this);
  
        
        try {
            model = new Image(Thumbnails.of(data.path_model).size(Options.dimensionMaxColoring.width, Options.dimensionMaxColoring.height).asBufferedImage());
            coloring = new Coloring(Thumbnails.of(data.path_default).size(Options.dimensionMaxColoring.width, Options.dimensionMaxColoring.height).asBufferedImage(), this);
        } catch(IOException e) {
            
        }
        //coloring.alpha(0.8f);
        
        colors = new ColorPanel(data.palette);
        
        finish = new JButton("Valider le coloriage");
         finish.addActionListener((ActionEvent e) -> {
            finish();
        });
         
         setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
         finish.setAlignmentX(Component.CENTER_ALIGNMENT);
         
         JPanel panel = new JPanel();
         panel.setLayout(new FlowLayout(FlowLayout.CENTER));
         
         this.add(finish);
        
         panel.add(coloring);
         panel.add(colors);
         panel.add(model);
         
         this.add(panel);
         
         repaint();            
    }

    @Override
    public Color getColor() {
        return colors.getSelectedColor();
    }
    
    private void finish() {
        JOptionPane.showMessageDialog(this, "Votre score : " + model.compareTo(coloring) +"%");
        
        parent.Ui_list();
        
    }
    
}
