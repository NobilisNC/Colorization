package colorization;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.coobird.thumbnailator.*;

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
        setLayout(new BorderLayout());
        
        coloring = new Coloring(this);
        File f = new File(data.path_default);
        coloring.loadImage(f);
        try {
            model = new Image(Thumbnails.of(data.path_model).size(700,700).asBufferedImage());
        } catch(IOException e) {
            
        }
        
        colors = new ColorPanel(false, data.palette);
        
        finish = new JButton("Valider le coloriage");
         finish.addActionListener((ActionEvent e) -> {
            finish();
        });
         
        
         this.add(model, BorderLayout.LINE_START);
         this.add(colors, BorderLayout.CENTER);
         this.add(coloring, BorderLayout.LINE_END);
         
         this.add(finish, BorderLayout.PAGE_START);
         
         repaint();            
    }

    @Override
    public Color getColor() {
        return colors.getSelectedColor();
    }
    
    private void finish() {
        
    }
    
}
