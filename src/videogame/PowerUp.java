package videogame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 *
 * @author Sergon
 */
public class PowerUp extends Item
{    
    private int power;        // determines the kind of power
    private Game game;      // to interact with game object
    Random rand = new Random();
    
    public PowerUp(int x, int y, int width, int height, Game game)
    {
        super(x, y, width, height);
        this.game = game;
        power = rand.nextInt(3) + 1;
    }

    public int getPower()
    {
        return power;
    }

    @Override
    public void tick()
    {
        setY(getY() + 3);                
    }

    @Override
    public void render(Graphics g)
    {
        switch (getPower())
        {
            case 1:
                g.setColor(Color.green);
                break;
            case 2: 
                g.setColor(Color.white);
                break;
            case 3:
                g.setColor(Color.red);
        }
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }             
}
