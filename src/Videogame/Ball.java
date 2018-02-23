/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Videogame;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Sergon
 */
public class Ball extends Item
{
    private Game game;
    private int speedX;         //  speed x
    private int speedY;         //  speed y
    private int attackP;        //  attack points
    
    public Ball(int x, int y, int width, int height, int speedX, int speedY, Game game) 
    {
        super(x, y, width, height);
        this.game = game;
        this.speedX = speedX;
        this.speedY = speedY;
        attackP = 1;
    }

    @Override
    public void tick() 
    {
        // moving player depenting on keys
        setX(getX() + getSpeedX());
        setY(getY() + getSpeedY());
        // collision with walls
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
        
        if (getY() + 20 <= 0) 
        {
            setY(0);
            setSpeedY(getSpeedY() * -1);
        }
         
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillOval(getX(), getY(), getWidth(), getHeight());
    }

    public int getAttackP()
    {
        return attackP;
    }

    public void setAttackP(int attackP)
    {
        this.attackP = attackP;
    }
    
    public int getSpeedX()
    {
        return speedX;
    }

    public void setSpeedX(int speedX)
    {
        this.speedX = speedX;
    }

    public int getSpeedY()
    {
        return speedY;
    }

    public void setSpeedY(int speedY)
    {
        this.speedY = speedY;
    }
    
}
