package Videogame;

import java.awt.image.BufferedImage;

/**
 * Assets
 * 
 * In this class all te images, and sprites that the games needs are 
 * defined.
 * 
 * @author Noé Amador Campos Castillo
 * @date 23/02/2018
 * @version 2.0
 */
public class Assets {

    public static BufferedImage imaBackground; //To store background images

    public static BufferedImage spriteBar;          //Store the Bar Sprites  
    public static BufferedImage spriteBall;         //Store the Ball Sprites
    public static BufferedImage spriteHat;          //Store the Hat (Power Up) Sprites
    public static BufferedImage spriteGlass;        //Store the Glass (Power Up) Sprites
    public static BufferedImage spriteGlasses;      //Store the Glasses (Power Up) Sprites
    public static BufferedImage spriteBrick;        //Store the Brick Sprites
    public static BufferedImage spritePowerUpBrick; //Store the PowerUp Brick Sprites

    // ARRAYS TO STORE THE IMAGES TO ANIMATE EACH OBJECT
    public static BufferedImage PoliceBar[];
    public static BufferedImage Ball[];
    public static BufferedImage PUHat[];
    public static BufferedImage PUGlass[];
    public static BufferedImage PUGlasses[];
    public static BufferedImage Brick[];
    public static BufferedImage PowerUpBrick[];

    /**
     * init
     *
     * The function that load everything at the moment to start the game
     */
    public static void init() {
        //The images are loaded to their respective variables
        imaBackground = ImageLoader.loadImage("/Images/HeisenbergBackground.jpg");

        //Getting the sprites from the picture 
        spriteBar = ImageLoader.loadImage("/Images/PoliceBar.png");
        spriteBall = ImageLoader.loadImage("/Images/Ball.png");
        spriteHat = ImageLoader.loadImage("/Images/Hat.png");
        spriteGlass = ImageLoader.loadImage("/Images/Glass.png");
        spriteGlasses = ImageLoader.loadImage("/Images/BlackGlasses.png");
        spriteBrick = ImageLoader.loadImage("/Images/Meta.png");
        spritePowerUpBrick = ImageLoader.loadImage("/Images/Money.png");

        //Creating array of images before animations
        SpriteSheet sshBar = new SpriteSheet(spriteBar);
        SpriteSheet sshBall = new SpriteSheet(spriteBall);
        SpriteSheet sshHat = new SpriteSheet(spriteHat);
        SpriteSheet sshGlass = new SpriteSheet(spriteGlass);
        SpriteSheet sshGlasses = new SpriteSheet(spriteGlasses);
        SpriteSheet sshBrick = new SpriteSheet(spriteBrick);
        SpriteSheet sshPUBrick = new SpriteSheet(spritePowerUpBrick);

        //Creat the arrays of BufferedImage
        PoliceBar = new BufferedImage[4];
        Ball = new BufferedImage[4];
        PUHat = new BufferedImage[4];
        PUGlass = new BufferedImage[4];
        PUGlasses = new BufferedImage[4];
        Brick = new BufferedImage[5];
        PowerUpBrick = new BufferedImage[5];

        //Croping the pictures from the sheet into array
        for (int iC = 0; iC < 5; iC++)
        {
            //This images have 5 sprites
            Brick[iC] = sshBrick.crop(iC * 800, 0, 800, 400);
            PowerUpBrick[iC] = sshPUBrick.crop(iC * 800, 0, 800, 400);

            //This just 4 sprites
            if (iC < 4)
            {
                PUHat[iC] = sshHat.crop(iC * 100, 0, 100, 100);
                Ball[iC] = sshBall.crop(iC * 50, 0, 50, 50);
                PUGlass[iC] = sshGlass.crop(iC * 100, 0, 100, 100);
                PUGlasses[iC] = sshGlasses.crop(iC * 100, 0, 100, 100);
                PoliceBar[iC] = sshBar.crop(iC * 600, 0, 600, 200);
            }
        }

    }
}


