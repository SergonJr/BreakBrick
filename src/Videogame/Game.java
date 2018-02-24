package Videogame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Game
 *
 * This is the class of the game, that control the participation of the other
 * classes as Player, Display, etc.
 *
 * @author Noé Campos and Sergio Gonzalez
 * @date 31/01/2018
 * @version 3.0
 */
public class Game implements Runnable 
{
    private BufferStrategy bs;      // To have several buffers when displaying
    private Graphics g;             // To paint objects
    private Display display;        // To display in the game
    String title;                   // Title of the window
    private int width;              // Width of the window
    private int height;             // Height of the window
    
    private Thread thread;          // Thread to create the game
    private boolean running;        // To set the game
    private boolean started;        // To start the game
    
    private KeyManager keyManager;  // To manage the keyboard
    
    private Player player;          // To use a player
    private Ball ball;              // Little ball
    private ArrayList<Brick> bricks;// Bricks
    
    private boolean pause;          //Flag to pause the game
    
    private ArrayList<PowerUp> powUps;  // Manage powerUps
    private boolean iKill, sTime, iBar; // Manage powerUp flags
    private long tKill, tTime, tBar;    // Shut down powers
    private boolean gameOver;           // Flag to know if the player lost

    /**
     * Game(Constructor)
     * 
     * To create title, width and height and set the game is still not running
     * @param title to set the title of the window
     * @param width to set the width of the window
     * @param height  to set the height of the window
     */
    public Game(String title, int width, int height) 
    {
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;
        started = false;
        keyManager = new KeyManager();
        pause = false;
        iKill = false;
        sTime = false;
        iBar = false;
        gameOver = false;
    }

    /**
     * Init
     * 
     * Initializing the display window of the game, player, bricks, etc.
     */
    private void init() 
    {
         display = new Display(title, getWidth(), getHeight());  
         Assets.init();
         player = new Player(getWidth() / 2 - 100, getHeight() - 50, 100, 25, this);
         ball = new Ball(getWidth() / 2 - 20, getHeight() - 50 - 20, 20, 20, 5, -5, this);
         bricks = new ArrayList<Brick>();
         powUps = new ArrayList<PowerUp>();
         int width_brick = getWidth() / 10 - 6;
         int height_brick = getHeight() / 3 / 5 - 10;
         for (int i = 0; i < 10; i++)
         {
             for (int j = 0; j < 5; j++)
             {
                 Brick brick = new Brick(i * (width_brick + 3) + 15, j * (height_brick + 5) + 15, width_brick, height_brick, this);
                 bricks.add(brick);
             }
         }
         display.getJframe().addKeyListener(keyManager);                  
    }
    
     private void tick() 
    {
        keyManager.tick();
        
        if (getKeyManager().isbR() && gameOver)
        {
            reset();
        }
        if (this.getKeyManager().isbSpace() && !isStarted())
        {
            setStarted(true);
        }
        if (getKeyManager().isbP() && !isPaused())
        {
            setPause(true);
        }
        else if (getKeyManager().isbP() && isPaused())
        {
            setPause(false);
        }
        if (!isPaused())
        {
            player.tick();
            if (isStarted())
            {            
                // moving the ball
                ball.tick();
                
                if (ball.getY() >= getHeight() || bricks.isEmpty())
                {
                    gameOver = true;
                }                
            }       
            else
            {
                ball.setX(player.getX() + player.getWidth() / 2 - ball.getWidth() / 2);
            }
        }        
        
        // check collision bricks versus ball     
        Iterator itrB = bricks.iterator();
        while (itrB.hasNext())
        {
            Brick brick = (Brick) itrB.next();
            //if (ball.intersects(brick))
            if(ball.getRectangle().intersects(brick.getRectangle()))
            {
                brick.setHitPoints(brick.getHitPoints() - 1);
                System.out.println(brick.getHitPoints());
                if (brick.getHitPoints() <= 0)
                {
                    bricks.remove(brick);
                    PowerUp powUp;
                    powUp = new PowerUp(brick.getX() + brick.getWidth()/2, 
                            brick.getY() + brick.getHeight(), 10, 10, this);
                    powUps.add(powUp);
                    itrB = bricks.iterator();
                }
                ball.setSpeedY(ball.getSpeedY() * -1);
            }                                    
        }
                
        // manage PowerUps 
        if (!powUps.isEmpty())
        {
            Iterator itrP = powUps.iterator();
            while (itrP.hasNext())
            {
                PowerUp pow = (PowerUp) itrP.next();
                pow.tick();
                //if (pow.intersects(player))
                if(pow.getRectangle().intersects(player.getRectangle()))
                {
                    System.out.println(pow.getPower());     
                    switch (pow.getPower())
                    {
                        case 1: 
                            this.setInstaKill();
                            iKill = true;
                            tKill = System.nanoTime();
                            System.out.println("iKill activated");
                            break;
                        case 2:
                            this.slowTime();
                            sTime = true;
                            tTime = System.nanoTime();
                            System.out.println("Time activated");
                            break;
                        case 3:
                            this.increaseBar();
                            iBar = true; 
                            tBar = System.nanoTime();
                            System.out.println("Bar activated");
                            break;
                    }
                    powUps.remove(pow);
                    itrP = powUps.iterator();                    
                }
            }
        }
        
        if (iKill && System.nanoTime() - tKill >= 15*1000000000)
        {
            this.disableInstaKill();
            System.out.println("iKill deactivated");
            iKill = false;
        }            
        if (sTime && System.nanoTime() - tTime >= 15*1000000000)
        {
            this.resetTime();
            System.out.println("Time deactivated");
            sTime = false;
        }
        if (iBar && System.nanoTime() - tBar >= 15*1000000000)
        {
            this.decreaseBar();
            System.out.println("Bar deactivated");
            iBar = false;
        }
        // check collision bricks versus ball     
//        if (ball.intersects(player))
        if(ball.getRectangle().intersects(player.getRectangle()))
        {            
            ball.setSpeedY(ball.getSpeedY() * -1);
            
            if (ball.getX() + ball.getWidth()/2 > player.getX() + player.getWidth()/2)
            {
                ball.setSpeedX(5 + ((ball.getX() + (ball.getWidth()/2)) - (player.getX() + (player.getWidth()/2))) / 10);
            }
            else 
            {
                ball.setSpeedX(-5 + ((ball.getX() + (ball.getWidth()/2)) - (player.getX() + (player.getWidth()/2))) / 10);                
            }
        }
    }
     
    @Override
    public void run() 
    {
        init();
        // frames per second
        int fps = 60;
        // time for each tick in nano segs
        double timeTick = 1000000000 / fps;
        // initializing delta
        double delta = 0;
        // define now to use inside the loop
        long now;
        // initializing last time to the computer time in nanosecs
        long lastTime = System.nanoTime();
        while (running)
        {
            // setting the time now to the actual time
            now = System.nanoTime();
            // acumulating to delta the difference between times in timeTick units
            delta += (now - lastTime) / timeTick;
            // updating the last time
            lastTime = now;
            
            // if delta is positive we tick the game
            if (delta >= 1) 
            {
                tick();
                render();
                delta --;
            }
        }
        stop();
    }
    // Reposition everything
    public void reset()
    {
        player.setX(getWidth() / 2 - 100);
        player.setY(getHeight() - 50);
        ball.setX(getWidth() / 2 - 20);
        ball.setY(getHeight() - 50 - 20);              
        bricks = new ArrayList<Brick>();        
        int width_brick = getWidth() / 10 - 6;
        int height_brick = getHeight() / 3 / 5 - 10;
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                Brick brick = new Brick(i * (width_brick + 3) + 15, j * (height_brick + 5) + 15, width_brick, height_brick, this);
                bricks.add(brick);
             }
         }
        started = false;
        gameOver = false;
    }

    public KeyManager getKeyManager() 
    {
        return keyManager;
    }
           
    private void render() 
    {
        // get the buffer strategy from the display
        bs = display.getCanvas().getBufferStrategy();
        /* if it is null, we define one with 3 buffers to display images of
        the game, if not null, then we display every image of the game but
        after clearing the Rectangle, getting the graphic object from the 
        buffer strategy element. 
        show the graphic and dispose it to the trash system
        */
        if (bs == null) 
        {
            display.getCanvas().createBufferStrategy(3);
        }
        else
        {
            g = bs.getDrawGraphics();
            g.drawImage(Assets.imaBackground, 0, 0, width, height, null);
            player.render(g);
            ball.render(g);
            for (Brick brick : bricks)
            {
                brick.render(g);
            }
            for (PowerUp powUp : powUps)
            {
                powUp.render(g);
            }
            bs.show();
            g.dispose();
        }    
    }
    
    /**
     * setting the thead for the game
     */
    public synchronized void start() 
    {
        if (!running) 
        {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }
    
    /**
     * stopping the thread
     */
    public synchronized void stop() 
    {
        if (running) 
        {
            running = false;
            try 
            {
                thread.join();
            } 
          	catch (InterruptedException ie) 
            {
                ie.printStackTrace();
            }           
        }
    }

    public Ball getBall()
    {
        return ball;
    }

    public void setBall(Ball ball)
    {
        this.ball = ball;
    }
  
    
  /**
     * To get the width of the game window
     * @return an <code>int</code> value with the width
     */
    public int getWidth() 
    {
        return width;
    }

    /**
     * To get the height of the game window
     * @return an <code>int</code> value with the height
     */
    public int getHeight() 
    {
        return height;
    }

    public boolean isStarted()
    {
        return started;
    }

    public void setStarted(boolean started)
    {
        this.started = started;
    }
    
    public boolean isPaused()
    {
        return pause;
    }
    
    public void setPause(boolean pause)
    {
        this.pause = pause;
    }

    public Player getPlayer()
    {
        return player;
    }      
    
    public void increaseBar()
    {
        getPlayer().setWidth(getPlayer().getWidth() * 2);
    }
    
    public void decreaseBar()
    {
        getPlayer().setWidth(getPlayer().getWidth() / 2);
    }
    
    public void setInstaKill()
    {
        getBall().setAttackP(5);        
    }
    
    public void disableInstaKill()
    {
        getBall().setAttackP(1);
    }
    
    public void slowTime()
    {
        getPlayer().setSpeed(20);
    }
    
    public void resetTime()
    {
        getPlayer().setSpeed(10);
    }
}
