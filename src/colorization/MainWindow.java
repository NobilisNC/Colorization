package colorization;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


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
		setSize((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
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
		item_load.setText("Charger une image");
		menu_file.addSeparator();
		menu_file.add(item_exit);
		item_exit.addActionListener(this);
		item_exit.setText("Quitter");
		//Centre
		FlowLayout main_layout = new FlowLayout();
		content = getContentPane();
		content.setLayout(main_layout);
		
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

	private void Ui_creation(File f) {
			Ui = new CreationPanel(f, this);
			content.removeAll();
			content.add(Ui);
			revalidate();
	}
        
}