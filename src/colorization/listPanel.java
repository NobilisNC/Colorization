package colorization;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

interface itemClicked {
    void select(Data data);
}

class listPanel extends JPanel implements itemClicked {
    private JScrollPane spane;
    private JPanel panel;
    private final MainWindow parent;
      
    
    
    public listPanel(Vector<Data> datas, MainWindow _parent) {
        super();
        parent = _parent;
        
        
        
        panel = new JPanel();
        panel.setLayout(new GridLayout(3, datas.size()));
        
        datas.stream().forEach((d) -> { 
            panel.add(new ItemList(d, this));
        });
        
        
        spane = new JScrollPane(panel);
        spane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        
        this.add(spane); 
        this.add(Box.createHorizontalStrut(1100));
        this.add(Box.createVerticalStrut(-1000));
        repaint();
    }

    @Override
    public void select(Data d) {
        parent.Ui_useColoring(d);        
    }
    
    
}

class ItemList extends JComponent implements MouseListener {
    Data data;
    itemClicked parent;
    BufferedImage image;   
    
    public ItemList(Data d, itemClicked list) {
        super();
       data = d;
       parent = list;
       File f = new File(data.path_thumbnail);
        try {
            image = ImageIO.read(f);
        } catch (IOException ex) {
            Logger.getLogger(ItemList.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        addMouseListener(this);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters            
    }
    
    @Override
	public Dimension getPreferredSize() {
		return new Dimension(image.getWidth(),image.getHeight());
		
	}
	
         @Override
	public Dimension getMinimumSize()  {
		return new Dimension(image.getWidth(),image.getHeight());
		
	}
	
         @Override
	public Dimension getMaximumSize() {
		return new Dimension(image.getWidth(),image.getHeight());
		
	}

    @Override
    public void mouseClicked(MouseEvent me) {
        parent.select(data);        
    }

    @Override
    public void mousePressed(MouseEvent me) {}
    @Override
    public void mouseReleased(MouseEvent me) {}
    @Override
    public void mouseEntered(MouseEvent me) {}
    @Override
    public void mouseExited(MouseEvent me) {}    
    
}
