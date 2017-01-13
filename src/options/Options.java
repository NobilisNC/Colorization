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


public class Options {
    
    public static Dimension dimensionScreen = Toolkit.getDefaultToolkit().getScreenSize();
    public static Dimension dimensionMaxColoring =  new Dimension(
                                                                (int) (0.45f * Toolkit.getDefaultToolkit().getScreenSize().width), 
                                                                (int) (0.85f * Toolkit.getDefaultToolkit().getScreenSize().height) 
                                                                );
    public static Dimension dimensionColorButton = new Dimension(
                                                                (int) (0.05f * Toolkit.getDefaultToolkit().getScreenSize().width), 
                                                                (int) (0.05f * Toolkit.getDefaultToolkit().getScreenSize().height) 
                                                                );
    
    public static Dimension dimensionThumbnail = new Dimension(200, 200);
    
    public static String pathToXmlFile = "colorings.xml";
    public static String directoryThumbnails = ".colorings/thumbnails/";
    public static String directoryDefaults = ".colorings/defaults/";
    public static String directoryModels = ".colorings/models/";
        
    
    
}
