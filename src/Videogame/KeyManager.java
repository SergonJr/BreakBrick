package Videogame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * KeyManager
 * 
 * In this class it is implement the class KeyListener, this is becouse
 * it can be used to listen wich keys are being pressed and use to 
 * realize certain process
 * 
 * @author Noé Campos and Sergio Gonzalez
 * @date 23/01/2018
 * @version 2.0
 */
public class KeyManager implements KeyListener {
    
    private static boolean bLeft;       // Flag to move bLeft the player
    private static boolean bRigth;      // Flag to move bRigth the player
    private static boolean bSpace;      // Flag to shoot
    private static boolean bP;          // Flag to Pause
    private static boolean bR;          // Flag to Restart
    
    private boolean arrKeys[];          // To store all the flags for every key
    
    /**
     * KeyManager(Constructor)
     * 
     * Create the array to store al the flags
     */
    public KeyManager() {
        arrKeys = new boolean[256];
    }

    /**
     * isbLeft
     * 
     * @return the Left flag
     */
    public static boolean isbLeft() {
        return bLeft;
    }

    /**
     * isbRight
     * 
     * @return the rigth flag
     */
    public static boolean isbRigth() {
        return bRigth;
    }

    /**
     * isbP
     * 
     * @return the P flag
     */
    public static boolean isbP() {
        return bP;
    }

    /**
     * isbR
     * 
     * @return the R flag
     */
    public static boolean isbR() {
        return bR;
    }

    /**
     * isbSpace
     * 
     * @return the Space flag
     */
    public static boolean isbSpace() {
        return bSpace;
    }
    
    
    /**
     * setbLeft
     * 
     * @param bLeft to set the Left flag
     */
    public static void setbLeft(boolean bLeft) {
        KeyManager.bLeft = bLeft;
    }

    /**
     * setbRigth
     * 
     * @param bRigth to set the Rigth flag
     */
    public static void setbRigth(boolean bRigth) {
        KeyManager.bRigth = bRigth;
    }

    /**
     * setbSpace
     * 
     * @param bSpace to set the Space flag
     */
    public static void setbSpace(boolean bSpace) {
        KeyManager.bSpace = bSpace;
    }

    /**
     * setbP
     * 
     * @param bP to set the Pause flag
     */
    public static void setbP(boolean bP) {
        KeyManager.bP = bP;
    }

    /**
     * setbR
     * 
     * @param bR to set the Reset flag
     */
    public static void setbR(boolean bR) {
        KeyManager.bR = bR;
    }
    
    
    @Override
    public void keyTyped(KeyEvent e) 
    {
    }

    /**
     * keyPressed
     * 
     * @param e to know wich key is being pressed
     */
    @Override
    public void keyPressed(KeyEvent e) 
    {
        // Tet true to every key pressed
        // Tet flag bSpace if needed        
        arrKeys[e.getKeyCode()] = true;
        
    }

    /**
     * keyReleased
     * 
     * @param e to know wich key has being released
     */
    @Override
    public void keyReleased(KeyEvent e) 
    {
        // Set false to every key released
        arrKeys[e.getKeyCode()] = false;        
    }
    
    /**
     * Tick
     * 
     * To enable or disable moves on every tick
     */
    public void tick() 
    {
        setbLeft(arrKeys[KeyEvent.VK_LEFT]);
        setbRigth(arrKeys[KeyEvent.VK_RIGHT]);
        setbSpace(arrKeys[KeyEvent.VK_SPACE]);
        setbP(arrKeys[KeyEvent.VK_P]);
        setbR(arrKeys[KeyEvent.VK_R]);
    }
}