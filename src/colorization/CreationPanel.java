package colorization;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

public class CreationPanel extends JPanel implements PropertyChangeListener, ActionListener {
    
        private MainWindow parent;
	
	private JPanel tools_panel;
        private JLabel label_contrast;
	private JFormattedTextField f_contrast;
        private JLabel label_alpha;
	private JFormattedTextField f_alpha;
        private JLabel label_distX;
	private JFormattedTextField f_distX;
        private JLabel label_distY;
	private JFormattedTextField f_distY;
	private DrawingPanel drawing_panel;
	private JCheckBox check_advanced;
        private JButton save;
        

	
	private boolean advanced_creation = false;
	
	
	public CreationPanel(File f, MainWindow p) {
		super();
                parent = p;
		loadImage(f);		
		repaint();
	}
	
	public void loadImage(File f) {
		initComponents();
		drawing_panel.loadImage(f);
		advanced_creation = false;
		createPanel();
	}
	
	protected void createPanel() {
		
		if (!advanced_creation) {
                        tools_panel.add(label_alpha);
			tools_panel.add(f_alpha);
			tools_panel.add(check_advanced);
                        tools_panel.add(save);
		} else {	
                        tools_panel.add(label_contrast);
			tools_panel.add(f_contrast);
                        tools_panel.add(label_alpha);
			tools_panel.add(f_alpha);
                        tools_panel.add(label_distX);
			tools_panel.add(f_distX);
                        tools_panel.add(label_distY);
			tools_panel.add(f_distY);
			tools_panel.add(check_advanced);
                        tools_panel.add(save);
		}
		
		add(tools_panel);
		add(drawing_panel);	
			
		revalidate();
		repaint();
		
		repaintDrawingPanel();
	}
	
	protected void initComponents() {
		
		drawing_panel = new DrawingPanel();
		
		tools_panel = new JPanel();
		tools_panel.setLayout(new BoxLayout(tools_panel, BoxLayout.Y_AXIS));
		
                label_alpha = new JLabel("Précision alpha");
		f_alpha = new JFormattedTextField();
		f_alpha.setValue(new Float(drawing_panel.getAlpha()));
		f_alpha.setColumns(5);
		f_alpha.addPropertyChangeListener("value", this);
		
                label_contrast = new JLabel("Contraste");
		f_contrast = new JFormattedTextField();
		f_contrast.setValue(new Float(drawing_panel.getContrast1()));
		f_contrast.setColumns(5);
		f_contrast.addPropertyChangeListener("value", this);
		
                label_distX = new JLabel("Accentuer trait horizontalement");
		f_distX = new JFormattedTextField();
		f_distX.setValue(new Float(drawing_panel.getDistX()));
		f_distX.setColumns(5);
		f_distX.addPropertyChangeListener("value", this);
		
                label_distY = new JLabel("Accentuer trait verticalement");
		f_distY = new JFormattedTextField();
		f_distY.setValue(new Float(drawing_panel.getDistY()));
		f_distY.setColumns(5);
		f_distY.addPropertyChangeListener("value", this);
		
		check_advanced = new JCheckBox("Mode avancé (Béta)");
		check_advanced.addActionListener(this);
                
                save = new JButton("Sauvegarder le coloriage");
	}
	
	
	private void repaintDrawingPanel()
	{
        if (advanced_creation)
        	drawing_panel.advancedCreation();
        else
        	drawing_panel.baseCreation();
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent e) {
		 Object source = e.getSource();
	        if (source == f_contrast) {
	        	drawing_panel.setContrast1(((Number)f_contrast.getValue()).floatValue());
	     
	        } else if (source == f_alpha) {
	        	drawing_panel.setAlpha(((Number)f_alpha.getValue()).floatValue());
	            
	        } else if (source == f_distX) {
	        	drawing_panel.setDistX(((Number)f_distX.getValue()).intValue());
	        } else if (source == f_distY) {
	        	drawing_panel.setDistY(((Number)f_distY.getValue()).intValue());
	        } 
	        
	        repaintDrawingPanel();
	        
	   	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if (src == check_advanced)
			advanced_creation = !advanced_creation;
			
		tools_panel.removeAll();
		createPanel();
                repaintDrawingPanel();
    
		repaint();
		
	}
	

}
