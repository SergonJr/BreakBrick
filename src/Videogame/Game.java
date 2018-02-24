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
    
    private Thread thread;              // Thread to create the game
    private boolean bRunning;           // To set the game
    private boolean bStarted;           // To start the game
    
    private KeyManager keyManager;      // To manage the keyboard
    
    private Player player;              // To use a player
    private Ball Ball;                  // Little Ball
    private ArrayList<Brick> arrBricks; // Bricks
    
    private boolean bPause;              //Flag to bPause the game
    
    private ArrayList<PowerUp> arrPowUps;  // Manage powerUps
    private boolean bKill, bTime, bBar; // Manage powerUp flags
    private long tKill, tTime, tBar;    // Shut down powers
    private boolean bGameOver;           // Flag to know if the player lost

    /**
     * Game(Constructor)
     * 
     * To create title, width and height and set the game is still not bRunning
     * @param title to set the title of the window
     * @param width to set the width of the window
     * @param height  to set the height of the window
     */
    public Game(String title, int width, int height) 
    {
        this.title = title;
        this.width = width;
        this.height = height;
        bRunning = false;
        bStarted = false;
        keyManager = new KeyManager();
        bPause = false;
        bKill = false;
        bTime = false;
        bBar = false;
        bGameOver = false;
    }

    /**
     * Init
     * 
     * Initializing the display window of the game, player, arrBricks, etc.
     */
    private void init() 
    {
         display = new Display(title, getWidth(), getHeight());  
         Assets.init();
         
         player = new Player(getWidth() / 2 - 100, getHeight() - 100, 100, 30, this);
         Ball = new Ball(getWidth() / 2 - 25, player.getY()-25, 25, 25, 5, -5, this);
         
         arrBricks = new ArrayList<Brick>();
         arrPowUps = new ArrayList<PowerUp>();
         
         int width_brick = getWidth() / 10 - 6;
         int height_brick = getHeight() / 3 / 5 - 10;
         for (int i = 0; i < 10; i++)
         {
             for (int j = 0; j < 5; j++)
             {
                 Brick brick = new Brick(i * (width_brick + 3) + 15, j * (height_brick + 5) + 15, width_brick, height_brick, this);
                 arrBricks.add(brick);
             }
         }
         display.getJframe().addKeyListener(keyManager);                  
    }
    
     private void tick() 
    {
        keyManager.tick();
        
        if (getKeyManager().isbR() && bGameOver)
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
                // moving the Ball
                Ball.tick();
                
                if (Ball.getY() >= getHeight() || arrBricks.isEmpty())
                {
                    bGameOver = true;
                }                
            }       
            else
            {
                Ball.setX(player.getX() + player.getWidth() / 2 - Ball.getWidth() / 2);
            }
        }        
        
        // check collision arrBricks versus Ball     
        Iterator itrB = arrBricks.iterator();
        while (itrB.hasNext())
        {
            Brick brick = (Brick) itrB.next();
            //if (Ball.intersects(brick))
            if(Ball.getRectangle().intersects(brick.getRectangle()))
            {
                brick.setHitPoints(brick.getHitPoints() - 1);
                System.out.println(brick.getHitPoints());
                if (brick.getHitPoints() <= 0)
                {
                    arrBricks.remove(brick);
                    PowerUp powUp;
                    powUp = new PowerUp(brick.getX() + brick.getWidth()/2, 
                            brick.getY() + brick.getHeight(), 50, 50);
                    arrPowUps.add(powUp);
                    itrB = arrBricks.iterator();
                }
                Ball.setSpeedY(Ball.getSpeedY() * -1);
            }                                    
        }
                
        // manage PowerUps 
        if (!arrPowUps.isEmpty())
        {
            Iterator itrP = arrPowUps.iterator();
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
                            bKill = true;
                            tKill = System.nanoTime();
                            System.out.println("bKill activated");
                            break;
                        case 2:
                            this.slowTime();
                            bTime = true;
                            tTime = System.nanoTime();
                            System.out.println("Time activated");
                            break;
                        case 3:
                            this.increaseBar();
                            bBar = true; 
                            tBar = System.nanoTime();
                            System.out.println("Bar activated");
                            break;
                    }
                    arrPowUps.remove(pow);
                    itrP = arrPowUps.iterator();                    
                }
            }
        }
        
        if (bKill && System.nanoTime() - tKill >= 15*1000000000)
        {
            this.disableInstaKill();
            System.out.println("bKill deactivated");
            bKill = false;
        }            
        if (bTime && System.nanoTime() - tTime >= 15*1000000000)
        {
            this.resetTime();
            System.out.println("Time deactivated");
            bTime = false;
        }
        if (bBar && System.nanoTime() - tBar >= 15*1000000000)
        {
            this.decreaseBar();
            System.out.println("Bar deactivated");
            bBar = false;
        }
        // check collision arrBricks versus Ball     
//        if (Ball.intersects(player))
        if(Ball.getRectangle().intersects(player.getRectangle()))
        {            
            Ball.setSpeedY(Ball.getSpeedY() * -1);
            
            if (Ball.getX() + Ball.getWidth()/2 > player.getX() + player.getWidth()/2)
            {
                Ball.setSpeedX(5 + ((Ball.getX() + (Ball.getWidth()/2)) - (player.getX() + (player.getWidth()/2))) / 10);
            }
            else 
            {
                Ball.setSpeedX(-5 + ((Ball.getX() + (Ball.getWidth()/2)) - (player.getX() + (player.getWidth()/2))) / 10);                
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
        while (bRunning)
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
        Ball.setX(getWidth() / 2 - 20);
        Ball.setY(getHeight() - 50 - 20);              
        arrBricks = new ArrayList<Brick>();        
        int width_brick = getWidth() / 10 - 6;
        int height_brick = getHeight() / 3 / 5 - 10;
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                Brick brick = new Brick(i * (width_brick + 3) + 15, j * (height_brick + 5) + 15, width_brick, height_brick, this);
                arrBricks.add(brick);
             }
         }
        bStarted = false;
        bGameOver = false;
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
            Ball.render(g);
            for (Brick brick : arrBricks)
            {
                brick.render(g);
            }
            for (PowerUp powUp : arrPowUps)
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
        if (!bRunning) 
        {
            bRunning = true;
            thread = new Thread(this);
            thread.start();
        }
    }
    
    /**
     * stopping the thread
     */
    public synchronized void stop() 
    {
        if (bRunning) 
        {
            bRunning = false;
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
        return Ball;
    }

    public void setBall(Ball Ball)
    {
        this.Ball = Ball;
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
        return bStarted;
    }

    public void setStarted(boolean bStarted)
    {
        this.bStarted = bStarted;
    }
    
    public boolean isPaused()
    {
        return bPause;
    }
    
    public void setPause(boolean bPause)
    {
        this.bPause = bPause;
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
