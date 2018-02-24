package Videogame;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Ball
 * 
 * The class that are in charge of the ball of the game. 
 * Control the animations, or sprite that are printed.
 * 
 * @author Sergio Gonzalez and Noe Campos
 * @date 22/02/2018
 * @version 1.0
 */
public class Ball extends Item
{
    private Game game;
    private int iSpeedX;         // Speed X
    private int iSpeedY;         // Speed Y
    private int iAttackP;        // Attack points
    boolean bCollision;          // The Collision Flag
    Animation aniBall;           // To store the animation 
    
    public Ball(int x, int y, int width, int height, int speedX, int speedY, Game game) 
    {
        super(x, y, width, height);
        this.game = game;
        this.iSpeedX = speedX;
        this.iSpeedY = speedY;
        iAttackP = 1;
        
        aniBall = new Animation(Assets.Ball, 75);
        bCollision = false;
    }

    /**
     * getAttackP
     * 
     * @return the Attack value
     */
    public int getAttackP()
    {
        return iAttackP;
    }

    /**
     * setAttack
     * 
     * @param iAttackP to set the Attack Value
     */
    public void setAttackP(int iAttackP)
    {
        this.iAttackP = iAttackP;
    }
    
    /**
     * getSpeedX 
     * 
     * @return the SpeedX value
     */
    public int getSpeedX()
    {
        return iSpeedX;
    }

    /**
     * setSpeedX
     * 
     * @param iSpeedX  to set the SpeedX
     */
    public void setSpeedX(int iSpeedX)
    {
        this.iSpeedX = iSpeedX;
    }

    /**
     * getSpeedY
     * 
     * @return the SpeedY value
     */
    public int getSpeedY()
    {
        return iSpeedY;
    }

    /**
     * setSpeedY
     * 
     * @param iSpeedY to set the SpeedY value
     */
    public void setSpeedY(int iSpeedY)
    {
        this.iSpeedY = iSpeedY;
    }

    /**
     * bCollision
     * 
     * @param bCollision to set the Collsion Flag
     */
    public void setbCollision(boolean bCollision) {
        this.bCollision = bCollision;
    }
   
    
    @Override
    public void tick() 
    {
        // Moving player depenting on keys
        setX(getX() + getSpeedX());
        setY(getY() + getSpeedY());
        
        // Collision with walls
        if (getX() + 20 >= game.getWidth()) 
        {
            setX(game.getWidth() - 20);
            setSpeedX(getSpeedX() * -1);
        }
        else if (getX() <= 0) 
        {
            setX(0);
            setSpeedX(getSpeedX() * -1);
        }  
        
        // Collision with Y Superior Axis
        if (getY() + 20 <= 0) 
        {
            setY(0);
            setSpeedY(getSpeedY() * -1);
        }
        
        //Update the Rectangle Position
        this.getRectangle().setLocation(getX(), getY());
    }

    /**
     * render
     * 
     * @param g to draw the ball
     */
    @Override
    public void render(Graphics g) {
        if(bCollision)
            g.drawImage(aniBall.getCurrentFrame(), getX(), getY(),
                getWidth(), getHeight(), null);
        else 
        {
            g.drawImage(aniBall.getFirstFrame(), getX(), getY(),
                getWidth(), getHeight(), null);
            
            aniBall.tick();
        }
    }

}