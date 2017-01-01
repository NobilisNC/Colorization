package colorization;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


public class DataHandler {
    
        
    private static final String PATH = "/home/nobilis/.coloriages/";
   
    private Element root = null;
    private org.jdom2.Document document = null;

        
    public DataHandler() {    
        SAXBuilder sxb = new SAXBuilder();
        try {
            document = sxb.build(new File("/home/nobilis/coding/iut/java/Color/test.xml"));
        } catch (JDOMException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        root = document.getRootElement();
    }
    
    public void saveColoring(String name, int[] palette, BufferedImage default_c, BufferedImage model_c, BufferedImage thumbnail_c) {
        String path_default = PATH + "defaults/" + name + ".png";
        String path_model = PATH + "models/" + name + ".png";
        String path_thumbnail = PATH + "thumbnails/" + name + ".png";
        File file_default = new File(path_default);
        File file_model = new File(path_model);
        File file_thumbnail = new File(path_thumbnail);
        
        try {
            ImageIO.write(default_c, "png", file_default);
            ImageIO.write(model_c, "png", file_model);
            ImageIO.write(thumbnail_c, "png", file_thumbnail);
            
        } catch (IOException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("ERREUR LORS DE L'ECRITURE DES DATAS COLORIAGES");
        }     

        //On créer le JDOM du coloriage.          
        Element coloring = new Element("coloring");
        Element n = new Element("name");
        n.setText(name);
        coloring.addContent(n);
         
        Element path = new Element("path");
        path.setText(path_default);
        coloring.addContent(path);
        Element model = new Element("model");
        model.setText(path_model);
        coloring.addContent(model);
        Element thumbnail = new Element("thumbnail");
        thumbnail.setText(path_thumbnail);
        coloring.addContent(thumbnail);
        
        Element pal = new Element("palette");
        for ( int c : palette) {
            Element color = new Element("color");
            color.setText(Integer.toString(c, 16));
            pal.addContent(color);
                     
        }
        coloring.addContent(pal);
        
        root.addContent(coloring);
        XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
        try {
            sortie.output(document, new FileOutputStream("/home/nobilis/coding/iut/java/Color/test.xml"));
        } catch (IOException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
   
    public void list() {
        List colorings = root.getChildren("coloring");
        
        for (Object i : colorings) {
            Element courant = (Element) i;
            System.err.println(courant.getChild("name").getText());
        }
                
    }
   
}