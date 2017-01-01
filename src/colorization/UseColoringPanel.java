package colorization;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

class UseColoringPanel extends JPanel implements ColoringAccessColor {
    
    private final MainWindow parent;
    
    private Coloring coloring;
    private BufferedImage model;
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
        File f = new File(data.path_default);
        coloring.loadImage(f);
        try {
            f = new File(data.path_model);
            model = ImageIO.read(f);
            f = new File(data.path_thumbnail);
            thumbnail = ImageIO.read(f);
        } catch(IOException e) {
            
        }
        
        colors = new ColorPanel(false, data.palette);
        
        finish = new JButton("Valider le coloriage");
         finish.addActionListener((ActionEvent e) -> {
            finish();
        });
         
         this.add(colors);
         this.add(coloring);
         this.add(finish);
         
         repaint();            
    }

    @Override
    public Color getColor() {
        return colors.getSelectedColor();
    }
    
    private void finish() {
        
    }
    
}
