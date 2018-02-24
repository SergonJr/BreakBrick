package Videogame;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Player
 * 
 * This is the class that it is about the player or character that the user
 * will be playing with. It is declared variables about it size to print,
 * flags that keep record if the player has a collision, etc.
 * The class inherits of the Item class.
 * 
 * @author Sergio Gonzalez and Noe Campos
 * @date 22/02/2018
 * @version 3.0
 */

public class Player extends Item{

    private Game gamGame;           //Game Object     
    private int iSpeed = 10;        //The speed of the player
    private Animation aniPolice;    //To store the animation of the player
    
    public Player(int iX, int iY, int iWidth, int iHeight, Game gamGame)
    {
        super(iX, iY, iWidth, iHeight);
        this.gamGame = gamGame;
        aniPolice = new Animation(Assets.PoliceBar, 50);
    }
    
    
    /**
     * getSpeed
     * 
     * @return the Speed value
     */
    public int getSpeed()
    {
        return iSpeed;
    }

    /**
     * setSpeed
     * 
     * @param iSpeed to set the speed value
     */
    public void setSpeed(int iSpeed)
    {
        this.iSpeed = iSpeed;
    }
   
    /**
     * tick
     * 
     * Function that actualize the position of the player, by a series 
     * of condition of what keys are being pressed. 
     */
    @Override
    public void tick() 
    {
        // Moving the player depending on wich keys are pressed       
        if (gamGame.getKeyManager().isbLeft()){
           setX(getX() - iSpeed);
        }
        if (gamGame.getKeyManager().isbRigth()) {
           setX(getX() + iSpeed);
        }
        // Define borders, collitions 
        if (getX() + 100 >= gamGame.getWidth()) {
            setX(gamGame.getWidth() - 100);
        }
        else if (getX() <= 0) {
            setX(0);
        }
        
        //Animation Tick
        aniPolice.tick();
        
        //Set the location of the recatangle
        this.getRectangle().setLocation(getX(), getY());
    }

    /**
     * render
     * 
     * @param g to draw the Player Animation
     */
    @Override
    public void render(Graphics g) 
    {
        g.drawImage(aniPolice.getCurrentFrame(), getX(), getY(),
                getWidth(), getHeight(), null);
    } 
}