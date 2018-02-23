
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

    private Game game;       
    private int speed = 10;
    
    public Player(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
    }

    @Override
    public void tick() 
    {
        // moving player depending on keys       
        if (game.getKeyManager().left) {
           setX(getX() - speed);
        }
        if (game.getKeyManager().right) {
           setX(getX() + speed);
        }
        // collision with walls
        if (getX() + 100 >= game.getWidth()) {
            setX(game.getWidth() - 100);
        }
        else if (getX() <= 0) {
            setX(0);
        }       
    }

    @Override
    public void render(Graphics g) 
    {
        g.setColor(Color.red);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }

    public int getSpeed()
    {
        return speed;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }
    
    
}