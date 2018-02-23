/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Videogame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import javafx.scene.shape.Circle;

/**
 *
 * @author antoniomejorado
 */
public abstract class Item {
    protected int x;        // To store x position
    protected int y;        // To store y position
    protected int width;    // To store width
    protected int height;   // To store height
    protected Game gamGame; // To store the game object
    protected Rectangle rectangle;  //To store the rectangle attribute
    
    /**
     * Item
     * 
     * @param x to set the X value
     * @param y to set the Y value
     * @param width to set the Width of the Item
     * @param height to set the Height of the item
     */
    public Item(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rectangle = new Rectangle(x, y, width, height);
    }

    /**
     * getWidth
     * 
     * @return the Width of the Item
     */
    public int getWidth() {
        return width;
    }

    /**
     * getHeight
     * 
     * @return the height of the item
     */
    public int getHeight() {
        return height;
    }
  
    /**
     * getX
     * 
     * @return x value
     */
    public int getX() {
        return x;
    }

    /**
     * getY
     * 
     * @return y value
     */
    public int getY() {
        return y;
    }

    /**
     * getRectangle
     * 
     * @return the rectangle or cicle of the item
     */
    public Rectangle getRectangle() {    
        return rectangle;
    }

    /**
     * Set x value
     * @param x to modify
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set y value
     * @param y to modify
     */
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * setWidth
     * 
     * @param width to modify the width of the item
     */
    public void setWidth(int width) {
        this.width = width;
    }
    
    /**
     * setHeight
     * 
     * @param height to modify the heigth of the item
     */
    public void setHeight(int height) {
        this.height = height;
    }
    
   
    /*
    
    public boolean intersects(Object obj)
    {
        // check if the object is an Item        
        return (obj instanceof Item && this.getBounds().intersects(((Item) obj).getBounds()));        
    }
    
    private Rectangle getBounds()
    {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
    */
    
    /**
     * tick 
     * 
     * To update positions of the item for every tick
     */
    public abstract void tick();
    
    /**
     * render
     * 
     * To paint the item
     * @param g <b>Graphics</b> object to paint the item
     */
    public abstract void render(Graphics g);
}
