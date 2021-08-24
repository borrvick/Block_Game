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

public class GameCursor extends JPanel {
    GameGUI gameGui;
    //where the circle is on the panel
    int currentX;
    int currentY;
    //dimensions for the curser
    int cursorWidth;
    int cursorHeight;
    //creates a rectangle for a collison object for the cursor
    private Rectangle cursorRect;
    //buffered image
    private BufferedImage cursorPic;

    public GameCursor(GameGUI pGameGui){

       gameGui = pGameGui;
        //calls the load image method
        loadImage();
        //sets the cursors to the width and hight ofthe size of the picure
        cursorWidth=cursorPic.getWidth();
        cursorHeight=cursorPic.getHeight();
        //sets variables
        currentX = GamePanelClass.PANEL_WIDTH/2;
        currentY = GamePanelClass.PANEL_HEIGHT-cursorHeight;
        //creates teh collsion rect
        cursorRect = new Rectangle(currentX, currentY, cursorWidth, cursorHeight);
    }

    //loads in the image to a buffered pic
    public void loadImage()
    {
        try {
            //assigns image to cursorpic
            cursorPic = ImageIO.read(new File("yellowCursor.png"));
        } catch (IOException e) {
            //Display an error to the user if they load a file that
            //doesn't exist somehow.
            JOptionPane.showMessageDialog(null, "Error loading file.");
        }
    }

    //paint component to draw
    public void draw(Graphics g) {

        //graws the graphic of the yellow cursor
        g.drawImage(cursorPic,currentX, currentY, cursorWidth, cursorHeight, null);
        //all for collsion rect
        Graphics2D g2d = (Graphics2D)g;
        g2d.draw(cursorRect);
    }

    //accessors and mutators
    public int getCursorWidth() {
        return cursorWidth;
    }
    public void setCursorWidth(int cursorWidth) {
        this.cursorWidth = cursorWidth;
    }
    public int getCursorHeight() {
        return cursorHeight;
    }
    public void setCursorHeight(int cursorHeight) {
        this.cursorHeight = cursorHeight;
    }
    public int getCurrentX() {
        return currentX;
    }
    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }
    public int getCurrentY() {
        return currentY;
    }
    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }
    public Rectangle getCursorRect() {
        return cursorRect;
    }
}
