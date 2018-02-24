package Videogame;

import java.awt.image.BufferedImage;

/**
 * SpriteSheet
 *
 * Class in charge of cut a Sprite Image in many parts to create 
 * an animation
 *
 * @author Noé Campos
 * @date 23/02/2018
 * @version 1.0
 */
public class SpriteSheet {
    private BufferedImage buiSheet;             //To store the spritesheet
    
    /**
     * SpreadSheet
     * 
     * Create a new spritesheet
     * @param buiSheet the <code>image</code> with sprites
     */
    public SpriteSheet(BufferedImage buiSheet){
        this.buiSheet = buiSheet;
    }
    
    /**
     * crop
     * 
     * Crop a sprite from the spritesheet
     * @param iX an int value with x coordinate
     * @param iY an int value with y coordinate
     * @param iWidth an int value with the width if the sprite
     * @param iHeight an int value with the height if the sprite
     * @return 
     */
    public BufferedImage crop(int iX, int iY, int iWidth, int iHeight){
        return buiSheet.getSubimage(iX, iY, iWidth, iHeight);
    }
    
}
