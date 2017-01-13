/*
 * This class is the Main window of the application
 * Contains a JPanel UI which contain the actual Ui to display;
 *
 *
 */
package colorization;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import options.Options;


public class MainWindow extends JFrame implements ActionListener  {
	
	//UI
	private Container content = null;
	private JMenuBar menuBar = null;
	private JMenu menu_file = null;
	private JMenuItem item_load = null;
	private JMenuItem item_exit = null;
	private JPanel Ui = null;
		
	
	public MainWindow(String title) {
		super(title);
		setSize(Options.dimensionScreen.width, Options.dimensionScreen.height);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		menu_file = new JMenu();
		item_load = new JMenuItem();
		item_exit = new JMenuItem();
		
		
		//Menu Bar
		setJMenuBar(menuBar);
		menuBar.add(menu_file);
		menu_file.setText("Fichier");
		menu_file.add(item_load);
		item_load.addActionListener(this);
		item_load.setText("Créer un coloriage");
		menu_file.addSeparator();
		menu_file.add(item_exit);
		item_exit.addActionListener(this);
		item_exit.setText("Quitter");
		
                //Centre
		content = getContentPane();
		
                
                
                Ui_list();
                
                revalidate();
		repaint();  
	}	
	
        @Override
	public void actionPerformed(ActionEvent cliqueMenu) 
	{
		if (cliqueMenu.getSource().equals(item_load)) {
			
			final JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "JPG & GIF Images", "jpg", "gif", "png", "jpeg");
			fc.setFileFilter(filter);
			int returnVal = fc.showOpenDialog(this);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
   	
	            File file = fc.getSelectedFile();
	        	Ui_creation(file);

	        }
	        //Permet d'afficher l'image en dimension native
	        content.revalidate();
	        content.repaint();
	        repaint();
					
		} else if (cliqueMenu.getSource().equals(item_exit)) {
			dispose();
		}
	}

	public void Ui_creation(File f) {
            Ui = new CreationColoringPanel(f, this);
            Ui_reset();
	}
    
        
        public void Ui_colorModel(BufferedImage bi) {
            Ui = new CreationModelPanel(bi, this);
            Ui_reset();
        }


    
        public void Ui_list() {       
            DataHandler d = new DataHandler();
            Ui = new listPanel(d.list(), this);
            
            Ui_reset();           
        }
        
        public void Ui_useColoring(Data d) {
            Ui = new UseColoringPanel(d, this);
            Ui_reset();
            
        }
        
        public void Ui_reset() {
            content.removeAll();
            content.add(Ui);
            revalidate();
            repaint();
        }
        
}