package videogame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 *
 * @author Sergon et Noe
 */
public class Brick extends Item
{
    private Game game;
    private int speed = 5;
    private int hitPoints;
    Random rand = new Random();
    public Brick(int x, int y, int width, int height, Game game) 
    {
        super(x, y, width, height);
        this.game = game;
        hitPoints = rand.nextInt(5) + 1;
    }

    @Override
    public void render(Graphics g) 
    {
        g.setColor(Color.blue);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void tick()
    {
    }

    public int getHitPoints()
    {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints)
    {
        this.hitPoints = hitPoints;
    }
    
    
}