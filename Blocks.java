/*
 * Author: Ben Orrvick
 * Class: CIS 306
 * Date: 4/25
 * Purpose: to create a game where the objective is to avoid objects and get a high
 * score as possible by staying alive as long as possible
 * */
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Blocks {

    //constants for the size of thje blocks
    private final int BLOCK_WIDTH=50;
    private final int BLOCK_HEIGHT=50;
    //for where the block spawns above the screen
    private final int BLOCK_SPAWN=100;
    //for location of bloc
    private int x, y;
    //random for random spawn location
    private Random gen;
    //objects for if needed
    GamePanelClass gamePanel;



    //Bounds of the block
    private Rectangle rect;

    public Blocks(GamePanelClass pGamePanel){
        gen = new Random();
        //instantiates objects
        gamePanel = pGamePanel;
        //asssigns random numbers to location variables
        x=gen.nextInt(GamePanelClass.PANEL_WIDTH-BLOCK_WIDTH);
        y=gen.nextInt(BLOCK_SPAWN)*-1;
        //creates a rectangle for a collision object
        rect = new Rectangle(x, y, 50,50);
    }

    //paint component to draw
    public void draw(Graphics g) {
        //graws the graphic of the yellow cursor
        g.setColor(Color.yellow);
        g.fillRect(x,y,BLOCK_WIDTH,BLOCK_HEIGHT);
        //for the collsion object
        Graphics2D g2d = (Graphics2D)g;
        g2d.draw(rect);
    }

    public void updateBlock(int updateVal)
    {
        //updates the y value of the block down the screen
        y=y+updateVal;
    }

    //accessors and mutators
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    //gets our bounds
    public Rectangle getRect() {
        return rect;
    }

}
