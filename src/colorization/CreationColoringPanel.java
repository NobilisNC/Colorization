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


class CreationColoringPanel extends JPanel implements PropertyChangeListener, ActionListener {
    
        private final MainWindow parent;
	
	private JPanel tools_panel;
        private JLabel label_contrast;
	private JFormattedTextField f_contrast;
        private JLabel label_alpha;
	private JFormattedTextField f_alpha;
        private JLabel label_distX;
	private JFormattedTextField f_distX;
        private JLabel label_distY;
	private JFormattedTextField f_distY;
	private ProcessedImage processed_image;
	private JCheckBox check_advanced;
        private JButton save;
        

	
	private boolean advanced_creation = false;
	
	
	public CreationColoringPanel(File f, MainWindow p) {
		super();
                parent = p;
		loadImage(f);		
		repaint();
	}
	
	public void loadImage(File f) {
		initComponents();
		processed_image.loadImage(f);
		advanced_creation = false;
		repaintComponents();
	}
	
	protected void repaintComponents() {
		
            if (advanced_creation) {
                    label_contrast.setVisible(true);
                    f_contrast.setVisible(true);
                    label_alpha.setVisible(true);
                    f_alpha.setVisible(true);
                    label_distY.setVisible(true);
                    f_distY.setVisible(true);
                    label_distX.setVisible(true);
                    f_distX.setVisible(true);
                } else {
                    label_contrast.setVisible(false);
                    f_contrast.setVisible(false);
                    label_alpha.setVisible(true);
                    f_alpha.setVisible(true);
                    label_distY.setVisible(false);
                    f_distY.setVisible(false);
                    label_distX.setVisible(false);
                    f_distX.setVisible(false);
                    
                }
			
		tools_panel.revalidate();
		tools_panel.repaint();
		
		repaintImage();
	}
	
	protected void initComponents() {
		
		processed_image = new ProcessedImage();
                
                
		
		tools_panel = new JPanel();
		tools_panel.setLayout(new BoxLayout(tools_panel, BoxLayout.Y_AXIS));
		
                label_alpha = new JLabel("Précision alpha");
		f_alpha = new JFormattedTextField();
		f_alpha.setValue(processed_image.getAlpha());
		f_alpha.setColumns(5);
		f_alpha.addPropertyChangeListener("value", this);
		
                label_contrast = new JLabel("Contraste");
		f_contrast = new JFormattedTextField();
		f_contrast.setValue(processed_image.getContrast1());
		f_contrast.setColumns(5);
		f_contrast.addPropertyChangeListener("value", this);
		
                label_distX = new JLabel("Accentuer trait horizontalement");
		f_distX = new JFormattedTextField();
		f_distX.setValue(processed_image.getDistX());
		f_distX.setColumns(5);
		f_distX.addPropertyChangeListener("value", this);
		
                label_distY = new JLabel("Accentuer trait verticalement");
		f_distY = new JFormattedTextField();
		f_distY.setValue(processed_image.getDistY());
		f_distY.setColumns(5);
		f_distY.addPropertyChangeListener("value", this);
		
		check_advanced = new JCheckBox("Mode avancé (Béta)");
		check_advanced.addActionListener(this);
                
                save = new JButton("Passer à la création du model du coloriage");
                
                 save.addActionListener((ActionEvent e) -> {
                     save();
                });

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
                        
                add(tools_panel);
		add(processed_image);
	}
        
        
        private void save() {
            parent.Ui_colorModel(processed_image.getImage());
        }
        
	
	
	private void repaintImage()
	{
        if (advanced_creation)
        	processed_image.advancedCreation();
        else
        	processed_image.baseCreation();
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent e) {
		 Object source = e.getSource();
	        if (source == f_contrast) 
	        	processed_image.setContrast1(((Number)f_contrast.getValue()).floatValue());
	        else if (source == f_alpha) 
	        	processed_image.setAlpha(((Number)f_alpha.getValue()).floatValue());
	        else if (source == f_distX) 
	        	processed_image.setDistX(((Number)f_distX.getValue()).intValue());
	        else if (source == f_distY) 
	        	processed_image.setDistY(((Number)f_distY.getValue()).intValue());
	        
                
	        repaintImage();
	   	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if (src == check_advanced)
			advanced_creation = !advanced_creation;
			
		repaintComponents();
                repaintImage();
    
		repaint();
		
	}
        
   
        
	

}
