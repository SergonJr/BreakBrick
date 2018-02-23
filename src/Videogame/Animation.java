package Videogame;

import java.awt.image.BufferedImage;

/**
 * Animation
 * 
 * Class that are in charge of control the number of frames or sprites 
 * per a specific amount of time, to give creat an animation.
 * 
 * @author Noé Amador Campos Castillo
 * @date 23/02/2018
 * @version 2.0
 */
public class Animation {
    private int iSpeed;                 //For the speed of every frame
    private int iIndex;                 //To get the index of next frame to paint
    private long lLastTime;             //To save the prevoius time of the animation
    private long lTimer;                //To acumulate the timeof the animation
    private BufferedImage[] bufFrames;  //To store every image-frame
    
    
    /**
     * Animation (Constructor)
     * 
     * Creating the animation with all the franes and the speed for each
     * 
     * @param bufFrames an array of frames
     * @param iSpeed an value for the speed of every frame
     */
    public Animation(BufferedImage[] bufFrames, int iSpeed){
        this.bufFrames = bufFrames;             //To storing frames
        this.iSpeed = iSpeed;                   //To storing speed
        iIndex = 0;                             //Initializing Index
        lTimer = 0;                             //Initializing timer
        lLastTime = System.currentTimeMillis(); //Getting the initial time
    }
    
    
    /**
     * getCurrentFrame
     * 
     * @return the BufferedImage to corresponding frame to paint
     */
    public BufferedImage getCurrentFrame(){
        return bufFrames[iIndex];
    }
    
    /**
     * getFirstFrame
     * 
     * @return the first BufferedImage of the array
     */
    public BufferedImage getFirstFrame(){
        return bufFrames[0];
    }
    
    /**
     * tick
     * 
     * To update the animation to get the right index of the frame to paint
     */
    public void tick(){
        //Acumulating time from the prevoius tick to this one
        lTimer += System.currentTimeMillis() - lLastTime;
        //Updating the lLastTime for the next tick()
        lLastTime = System.currentTimeMillis();
        //Check the timer to increase the index
        if(lTimer > iSpeed){
            iIndex++;
            lTimer = 0;
            //Check index not to get out of the bounds
            if(iIndex >= bufFrames.length){
                iIndex = 0;
            }
        }
    }
}

