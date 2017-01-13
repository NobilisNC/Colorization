/*
 * This class contains application's options :
 *          - path
 *          - dimension adapted to user's screen
 *          
 * 
 */
package options;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Options {
    
    public static Dimension dimensionScreen = Toolkit.getDefaultToolkit().getScreenSize();
    public static Dimension dimensionMaxColoring =  new Dimension(
                                                                (int) (0.45f * Toolkit.getDefaultToolkit().getScreenSize().width), 
                                                                (int) (0.85f * Toolkit.getDefaultToolkit().getScreenSize().height) 
                                                                );
    public static Dimension dimensionColorButton = new Dimension(
                                                                (int) (0.025f * Toolkit.getDefaultToolkit().getScreenSize().width), 
                                                                (int) (0.05f * Toolkit.getDefaultToolkit().getScreenSize().height) 
                                                                );
    
    public static Dimension dimensionThumbnail = new Dimension(200, 200);
    
    public static String pathToXmlFile = "colorings.xml";
    public static String mainDirectory = ".colorings/";
    public static String directoryThumbnails = mainDirectory + "thumbnails/";
    public static String directoryDefaults = mainDirectory +"defaults/";
    public static String directoryModels = mainDirectory + "models/"; 
    
    
    public static void create() {
        File xml = new File(pathToXmlFile);
        if(!xml.exists()) {
            
            try {
                xml.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            List<String> lines = Arrays.asList("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "<colorings>", "</colorings>");
            Path file = Paths.get(pathToXmlFile);
            try {
                Files.write(file, lines, Charset.forName("UTF-8"));
            } catch (IOException ex) {
                Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        checkDirectory(mainDirectory);
        checkDirectory(directoryThumbnails);
        checkDirectory(directoryDefaults);
        checkDirectory(directoryModels);
    
        
    }
    
    private static void checkDirectory(String path) {
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory())
            dir.mkdir();  
        
    }
    
}
