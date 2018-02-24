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
    
    private ArrayList<PowerUp> arrPowUps;   // Manage powerUps
    private boolean bKill, bTime, bBar;     // Manage powerUp flags
    private long tKill, tTime, tBar;        // Shut down powers
    private boolean bGameOver;              // Flag to know if the player lost

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
 
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isbRunning() {
        return bRunning;
    }

    public boolean isbStarted() {
        return bStarted;
    }

    public boolean isbPause() {
        return bPause;
    }

    public boolean isbKill() {
        return bKill;
    }

    public boolean isbTime() {
        return bTime;
    }

    public boolean isbBar() {
        return bBar;
    }

    public long gettKill() {
        return tKill;
    }

    public long gettTime() {
        return tTime;
    }

    public long gettBar() {
        return tBar;
    }

    public boolean isbGameOver() {
        return bGameOver;
    }

    public Player getPlayer() {
        return player;
    }

    public Ball getBall() {
        return Ball;
    } 

    public void setbStarted(boolean bStarted) {
        this.bStarted = bStarted;
    }

    public void setbRunning(boolean bRunning) {
        this.bRunning = bRunning;
    }

    public void setbPause(boolean bPause) {
        this.bPause = bPause;
    }

    public void setbGameOver(boolean bGameOver) {
        this.bGameOver = bGameOver;
    }
    
    
    
    
    public void increaseBar()
    {
        getPlayer().setWidth(getPlayer().getWidth() * 2);
        getPlayer().getRectangle().setSize(getPlayer().getWidth() * 2, 30);
    }
    
    public void decreaseBar()
    {
        getPlayer().setWidth(getPlayer().getWidth() / 2);
        getPlayer().getRectangle().setSize(getPlayer().getWidth()/ 2, 30);
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

    /**
     * Init
     * 
     * Initializing the display window of the game, player, arrBricks, etc.
     */
    private void init() 
    {
        display = new Display(title, getWidth(), getHeight());  
        Assets.init();
         
        //The player is created
        player = new Player(getWidth() / 2 - 100, getHeight() - 100, 100, 30, this);
        //The ball is created
        Ball = new Ball(getWidth() / 2 - 25, player.getY()-25, 25, 25, 5, -5, this);
         
        //The Array of Bricks is created
        arrBricks = new ArrayList<Brick>();
        //The array of PowerUps
        arrPowUps = new ArrayList<PowerUp>();

        int width_brick = getWidth() / 10 - 6;
        int height_brick = getHeight() / 3 / 5 - 10;    //The measures to the brick

        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                Brick brick = new Brick(i * (width_brick + 3) + 15,
                        j * (height_brick + 5) + 15, width_brick,
                        height_brick, this);
                arrBricks.add(brick);
            }
        }   //Double for to create the bricks with positions in a matrix order
        
        //Add the KeyListener to the display
        display.getJframe().addKeyListener(keyManager);                  
    }
    
    /**
     * tick
     *
     * The function that has the order of how the objects are updated per frame
     */
    private void tick() 
    {
        //KeyManager ticl
        keyManager.tick();

        //To reset the game once the player lose
        if(this.getKeyManager().isbR() && bGameOver){
            reset();
        }
        
        //To pause or quit the pause
        if(this.getKeyManager().isbP()){
            setbPause(!isbPause());
        }
        
        //To start the game
        if (this.getKeyManager().isbSpace() && !isbStarted())
        {
            setbStarted(true);
        }
        //If the game it is not in pause or the player has not lose
        if (!isbPause() && !isbGameOver())
        {
            player.tick();
            if (isbStarted())
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
        
            // Check collision arrBricks versus Ball     
            Iterator itrB = arrBricks.iterator();
            while (itrB.hasNext())
            {
                Brick brick = (Brick) itrB.next();      //The iterator of Bricks
                
                //If the ball collide with a brick...
                if(Ball.getRectangle().intersects(brick.getRectangle()))
                {
                    //Substract a HitPoint of the brick
                    brick.setHitPoints(brick.getHitPoints() - 1);
                    //Update the sprite of the Brick
                    brick.setiFrame(4 - (brick.getHitPoints() - 1));
                    
                    //If the brick is destroyed
                    if (brick.getHitPoints() <= 0)
                    {
                        arrBricks.remove(brick);        //Remove the brick
                        PowerUp powUp;                  //Create the power up
                        //Set the properties of it
                        powUp = new PowerUp(brick.getX() + brick.getWidth()/2, 
                                brick.getY() + brick.getHeight(), 50, 50);
                        //Add the Power Up to the array
                        arrPowUps.add(powUp);
                        //Create the iterator
                        itrB = arrBricks.iterator();
                    }
                    //Change the ball direction
                    Ball.setSpeedY(Ball.getSpeedY() * -1);
                }                                    
            }

            // Manage PowerUps 
            if (!arrPowUps.isEmpty())   //If exist Power Ups...
            {
                //Make the iterator
                Iterator itrP = arrPowUps.iterator();
                //While there is another Power Up
                while (itrP.hasNext())
                {
                    //Obtain the next PowUp 
                    PowerUp pow = (PowerUp) itrP.next();
                    //Update the current Power Up
                    pow.tick();
                    
                    //If the player reach the Power Up
                    if(pow.getRectangle().intersects(player.getRectangle()))
                    {   
                        //Depends on the kind of Power Up
                        switch (pow.getPower())
                        {
                            case 1: 
                                this.setInstaKill();
                                bKill = true;
                                tKill = System.nanoTime();
                                break;
                                
                            case 2:
                                this.slowTime();
                                bTime = true;
                                tTime = System.nanoTime();
                                break;
                                
                            case 3:
                                this.increaseBar();
                                bBar = true; 
                                tBar = System.nanoTime();
                                break;
                        }
                        
                        //Erase the Power Up Object
                        arrPowUps.remove(pow);
                        //Update the Ireator
                        itrP = arrPowUps.iterator();                    
                    }
                }
            }
        }
        
        
        //To desactivate the Insta Kill
        if (bKill && System.nanoTime() - tKill >= 15*1000000000)
        {
            this.disableInstaKill();
            bKill = false;
        }       
        
        //To desactivate the Reduce Time 
        if (bTime && System.nanoTime() - tTime >= 15*1000000000)
        {
            this.resetTime();
            bTime = false;
        }
        
        //To desactivate the Bigger Bar
        if (bBar && System.nanoTime() - tBar >= 15*1000000000)
        {
            this.decreaseBar();
            bBar = false;
        }
        
        // Check collision arrBricks versus Ball     
        if(Ball.getRectangle().intersects(player.getRectangle()))
        {            
            Ball.setSpeedY(Ball.getSpeedY() * -1);
            
            if (Ball.getX() + Ball.getWidth()/2 > player.getX() 
                    + player.getWidth()/2)
            {
                Ball.setSpeedX(5 + ((Ball.getX() + (Ball.getWidth()/2)) - 
                        (player.getX() + (player.getWidth()/2))) / 10);
            }
            else 
            {
                Ball.setSpeedX(-5 + ((Ball.getX() + (Ball.getWidth()/2)) - 
                        (player.getX() + (player.getWidth()/2))) / 10);                
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
        player.setY(getHeight() - 100);
        Ball.setX(getWidth() / 2 - 25);
        Ball.setY(player.getY() - 25);
        
        arrBricks = new ArrayList<Brick>(); 
        
        int width_brick = getWidth() / 10 - 6;
        int height_brick = getHeight() / 3 / 5 - 10;
        
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                Brick brick = new Brick(i * (width_brick + 3) + 15,
                        j * (height_brick + 5) + 15, width_brick,
                        height_brick, this);
                
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
        if (!isbRunning()) 
        {
            setbRunning(true);
            thread = new Thread(this);
            thread.start();
        }
    }
    
    /**
     * stop
     * 
     * Stopping the thread
     */
    public synchronized void stop() 
    {
        if (isbRunning()) 
        {
            setbRunning(false);
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
}
