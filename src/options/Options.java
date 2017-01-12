/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package options;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author nobilis
 */
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
    
    public static String pathToXmlFile = "colorings.xml";
    public static String directoryThumbnails = ".colorings/thumbnails";
    public static String directoryDefaults = ".colorings/defaults";
    public static String directoryModels = ".colorings/models";
        
    
    
}
