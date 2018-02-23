package Videogame;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * Display
 * 
 * The class where a JFrame and a Canvas are created to show the game 
 * in the screen
 * 
 * @author Noé Campos and Sergio Gonzalez 
 * @date 22/02/2018
 * @version 2.0
 */


public class Display {
    private JFrame jframe;  // This is the app class
    private Canvas canvas;  // To display images
    
    private String title;   // Title of the window
    private int width;      // Width of the window
    private int height;     // Height of the window
    
    /**
     * Display(Cosntructor)
     * 
     * initializes the values for the application game
     * @param title to display the title of the window
     * @param width to set the width
     * @param height to set the height
     */
    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;        
        createDisplay();
    }
    
    /**
     * createDisplay
     * 
     * Create the app and the canvas and add the canvas to the window app
     */
    public void createDisplay() {
        // Create the app window
        jframe = new JFrame(title);
        
        // Set the size of the window
        jframe.setSize(width, height);
        
        // Setting not resizable, visible and possible to close
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setResizable(true);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
        
        // Creating the canvas to paint and setting size
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);
        
        // Adding the canvas to the app window and packing to
        // get the right dimensions
        jframe.add(canvas);
        jframe.pack();
    }

    /**
     * getJframe
     * 
     * to get the jframe of the game
     * @return jframe
     */
    public JFrame getJframe() {
        return jframe;
    }
    
    /**
     * getCanvas
     * 
     * To get the canvas of the game
     * @return the canvas
     */
    public Canvas getCanvas() {
        return canvas;
    }
}
