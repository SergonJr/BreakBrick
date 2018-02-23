package Videogame;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Videogame(Main class)
 * 
 * This is the main class, where it is declared and object of type Game
 * and it is called its function start, to run the videogame
 * 
 * @author Sergio Gonzalez
 * @date 22/02/2018
 * @version 1.0
 */

public class VideoGame {

    /**
     * main
     * 
     * The main function of the game
     * @param args the command line araguments
     */
    public static void main(String[] args) {
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        // TODO code application logic here
        Game g = new Game("Brick Breaker", screenSize.width, screenSize.height);
        g.start();
    }        
}
