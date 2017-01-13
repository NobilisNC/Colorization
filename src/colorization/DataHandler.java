/*
 * This class provides tools to load data about colorings.
 * Data is composed of 'name', the differents path to the model, thumbnail and empty coloring and the palette of the coloring.
 * 
 * This class uses the "JDOM API" (see http://www.jdom.org/ for more details)
 *
 */
package colorization;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.coobird.thumbnailator.Thumbnails;
import options.Options;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;



class Data {
    public String path_default;
    public String path_model;
    public String path_thumbnail;
    public String name;
    public int[] palette;   
}


class DataHandler {
    
        
    
   
    private Element root = null;
    private org.jdom2.Document document = null;

        
    public DataHandler() {    
        SAXBuilder sxb = new SAXBuilder();
        try {
            document = sxb.build(new File(Options.pathToXmlFile));
        } catch (JDOMException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        root = document.getRootElement();
    }
    
    public void saveColoring(String name, int[] palette, BufferedImage default_c, BufferedImage model_c) {
        String id = UUID.randomUUID().toString();        
        String path_default = Options.directoryDefaults + id + ".png";
        String path_model = Options.directoryModels + id + ".png";
        String path_thumbnail = Options.directoryThumbnails + id + ".png";
        
        File file_default = new File(path_default);
        File file_model = new File(path_model);
        File file_thumbnail = new File(path_thumbnail);
        
        
        
        try {
            //Create thumbnail
            BufferedImage thumbnail_c = Thumbnails.of(model_c).size(Options.dimensionThumbnail.width, Options.dimensionThumbnail.height).asBufferedImage();
            
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
         
        Element path = new Element("default");
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
            color.setText(Integer.toString(c));
            pal.addContent(color);
                     
        }
        coloring.addContent(pal);
        
        root.addContent(coloring);
        XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
        try {
            sortie.output(document, new FileOutputStream(Options.pathToXmlFile));
        } catch (IOException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
   
    public Vector<Data> list() {
        List colorings = root.getChildren("coloring");
        Vector<Data> datas = new Vector<>();
        
        for (Object coloring : colorings) {
            Element courant = (Element) coloring;
            Data d = new Data();
            d.name = courant.getChild("name").getText();
            d.path_default = courant.getChild("default").getText();
            d.path_model = courant.getChild("model").getText();
            d.path_thumbnail = courant.getChild("thumbnail").getText();
            
            Element palette = courant.getChild("palette");
            d.palette = new int[10];
            
            int i = 0;           
            for (Object c : palette.getChildren("color")) {
                Element color = (Element) c;              
                d.palette[i++] = Integer.parseInt( color.getText());               
            }
            datas.add(d);
        }
        return datas;                
    }
   
}


