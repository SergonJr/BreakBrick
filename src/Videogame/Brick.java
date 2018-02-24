package Videogame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * Ball
 * 
 * Create the brick that are going to be destroy.
 * 
 * @author Noe Campos and Sergio Gonzalez 
 * @date 22/02/2018
 * @version 1.0
 */
public class Brick extends Item
{
    private Game gamGame;
    private int iHitPoints;
    Random rand = new Random();
   
    Animation aniBrick;
    private int iFrame;
    
    public Brick(int iX, int iY, int iWidth, int iHeight, Game gamGame) 
    {
        super(iX, iY, iWidth, iHeight);
        this.gamGame = gamGame;
        iHitPoints = rand.nextInt(5) + 1;
        aniBrick = new Animation(Assets.Brick, 100);
        iFrame = 0;
    }
    
    
    /**
     * getHitPoints
     * 
     * @return the HitPoints value
     */
    public int getHitPoints()
    {
        return iHitPoints;
    }

    /**
     * setHitPoints
     * 
     * @param hitPoints to set the value
     */
    public void setHitPoints(int hitPoints)
    {
        this.iHitPoints = hitPoints;
    }

    public int getiFrame() {
        return iFrame;
    }

    public void setiFrame(int iFrame) {
        this.iFrame = iFrame;
    }
    
    @Override
    public void tick()
    {
    }

    @Override
    public void render(Graphics g) 
    {
        g.drawImage(aniBrick.getSpecificFrame(iFrame), getX(), getY(), getWidth(), getHeight(), null);
    }
    
}