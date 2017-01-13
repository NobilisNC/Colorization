/* 
 * This class displays the list of coloring.
 * Each Item is clickable.
 * 
 *
 *
 *
 */




package colorization;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
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
            ItemList item = new ItemList(d, this);
            item.setAlignmentY(Component.BOTTOM_ALIGNMENT);
            panel.add(item);
        });
        
        
        spane = new JScrollPane(panel);
        spane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //spane.setSize(parent.getSize());
        spane.revalidate();
        
        setLayout(new BorderLayout());
        
        this.add(spane, BorderLayout.CENTER); 
        repaint();
    }

    @Override
    public void select(Data d) {
        parent.Ui_useColoring(d);        
    }
    
    
}

class ItemList extends JPanel implements MouseListener {
    Data data;
    itemClicked parent;
    Image image;   
    
    public ItemList(Data d, itemClicked list) {
        super();
       data = d;
       parent = list;
       File f = new File(data.path_thumbnail);
       
        
        try {
            image = new Image(ImageIO.read(f));          
        } catch (IOException ex) {
            Logger.getLogger(ItemList.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        
        addMouseListener(this);
        JLabel name = new JLabel(data.name);
  
        
        

        
        
        this.add(image);
        this.add(name);
        
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
