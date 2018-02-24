package Videogame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * PowerUp
 *
 * Class in charge of create the PowerUps, that are objects that are created
 * randomly and fall after setroy certains bricks, this to give
 * abilities to the player.
 *
 * @author Sergio Gonzalez
 * @date 23/02/2018
 * @version 1.0
 */
public class PowerUp extends Item
{    
    private int iPower;        // Determines the kind of power
    Random rand = new Random();// A random number
    Animation aniPowerUp;      // To store the animation of the PowerUp
    
    /**
     * PowerUp
     * 
     * @param iX to set X value
     * @param iY to set Y value
     * @param iWidth to set iWidth value
     * @param iHeight to set iHeight value
     */
    public PowerUp(int iX, int iY, int iWidth, int iHeight)
    {
        super(iX, iY, iWidth, iHeight);
        iPower = rand.nextInt(3) + 1;    //Generate a random number between 1-3
        
        switch(getPower())
        {
            case 1:
                aniPowerUp = new Animation(Assets.PUGlass, 200);
                break;
            case 2: 
                aniPowerUp = new Animation(Assets.PUGlasses, 200);
                break;
            case 3:
                aniPowerUp = new Animation(Assets.PUHat, 200);
        }
    }

    /**
     * getPower
     * 
     * @return 
     */
    public int getPower()
    {
        return iPower;
    }

    /**
     * tick
     * 
     * To update the position of the PowerUp
     */
    @Override
    public void tick()
    {
        setY(getY() + 3);                               //Make the object fall
        
        //Update the rectangle position
        this.getRectangle().setLocation(getX(), getY());
        
        //Animation Tick
        aniPowerUp.tick();
    }

    /**
     * render 
     * 
     * @param g to print the PowerUp Object
     */
    @Override
    public void render(Graphics g)
    {
        g.drawImage(aniPowerUp.getCurrentFrame(), getX(), getY(),
                getWidth(), getHeight(), null);

    }             
}
