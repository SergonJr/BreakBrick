package videogame;

import java.awt.image.BufferedImage;

/**
 *
 * @author antoniomejorado
 */
public class Assets 
{
    public static BufferedImage imaBackground; //To store background image
    //ICNSDINSDANCCSAS

    /**
     * initializing the images of the game
     */
    public static void init() 
    {
        imaBackground = ImageLoader.loadImage("/Images/HeisenbergBackground.jpg");
    }
    
}
