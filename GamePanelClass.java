/*
 * Author: Ben Orrvick
 * Class: CIS 306
 * Date: 4/25
 * Purpose: to create a game where the objective is to avoid objects and get a high
 * score as possible by staying alive as long as possible
 * */
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GamePanelClass extends JPanel implements KeyListener {

    //creates listener for timer
    private GUIListener timeListener = new GUIListener();
    //creates a timer to keep the form refreshing so objects can move
    private EnemyListener enemyListener = new EnemyListener();
    private Timer cursorTimer, enemyTimer;
    //game cursor object
    private GameCursor cursor;
    //game gui object
    private GameGUI gameGUI;
    //object for blocks
    private ArrayList<Blocks> block;
    private ArrayList<Blocks> removeList;
    private Score scoreClass;
    //boolean for if game is still going
    boolean playGame;
    //boolean for if ceat is on
    boolean cheat;
    //speed of cursor
    private final int CURSOR_SPEED = 20;
    //canstants for the size of the game panel
    public static final int PANEL_HEIGHT =705;
    public static final int PANEL_WIDTH =794;
    //variables for difficulty
    private int blockTime;
    private int blockSpeed;
    //counter for how fast blocks are made
    private int createBlockCounter=0;
    //instance of this clas
    private GamePanelClass gamePanel;
    private Clip clip;

    public GamePanelClass(GameGUI pGameGUI) {

        //instatnializes objects
        gameGUI =pGameGUI;
        gamePanel = this;
        //sets the backgorund color
        this.setBackground(Color.darkGray);
        //sets focus on this form
        this.setFocusable(true);
        this.requestFocus();
        //adds the key lister
        this.addKeyListener(this);
        //creates the game cursor
        cursor = new GameCursor(gameGUI);
        //creates a score class to start a timer counter that keeps track of score
        scoreClass = new Score(gameGUI, this);
        //creates teh arraylist for the blocks to be stored in
        block = new ArrayList<Blocks>();
        removeList = new ArrayList<Blocks>();
        //creates and starts timer
        cursorTimer = new Timer(3, timeListener);
        //starts the timer cursor
        cursorTimer.start();
        //if the collision hasnt occured and to keep game going or not in general
        playGame=false;
        //sets cheat to false
        cheat=false;
    }

    public void CheckCollision(){
       //as long as the cheat isnt on the player will collide
        if(cheat==false) {

            for (Blocks aBlock : block) {
                //steatment for if rectangle in cursor
                // intersects the aBlock then game = fasle and then call stop timer method and basically resart everything

                    if (aBlock.getRect().intersects(cursor.getCursorRect())) {
                        //changes playgame to false
                        playGame = false;
                        PlaySound();
                        //shows a message and displays score
                        JOptionPane.showMessageDialog(null, "You have died!\n Score: " + scoreClass.getScoreSeconds());
                        EnableOptions();
                        repaint();

                    }
                }
            }
        if(!playGame) {
            StopScore();
        }
    }

    //diables the radio buttons
    public void EnableOptions(){
        gameGUI.getSlowRdo().setEnabled(true);
        gameGUI.getNormalRdo().setEnabled(true);
        gameGUI.getFastRdo().setEnabled(true);
        gameGUI.getStartBtn().setEnabled(true);
    }

    //plays a crash nose when it colides with block
    public void PlaySound(){
        try {
            // Open an audio input stream.
            File soundFile = new File("carexplosion.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            //starts teh clip
            clip.start();
        }
        //catch for if it fails
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    //starts the timer for the enemy so the timer speed can be set
    public void StartEnemyTimer(){
            //timer for the blocks
            enemyTimer = new Timer(blockTime, enemyListener);
            enemyTimer.start();
    }

    //starts the score timer
    public void StartScore(){
        //class the method in the score class to start the timer
        //method creates timer and starts sceduled class
        scoreClass.StartScoreTimer();
    }

    //this stops the score counter and resets it to 0 this is called when something collides
    public void StopScore(){
        //cancels tasks and timer
       scoreClass.getScoreCounter().stop();
        enemyTimer.stop();
        //sets the cunter to null
       scoreClass.nullCounter();
       //clear the lists
        removeList.clear();
        block.clear();
        //reapsints the screen
        repaint();
        //sets score to 0
        scoreClass.setScoreSeconds(0);
        gameGUI.getScoreLbl().setText("Score: " + scoreClass.getScoreSeconds());
    }

    //paint component to draw
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //focus for swithcing
        this.requestFocus();
        //creates a 2d graphic to display start game on screen
        //if staement to draw start game if game hasnt been started
        Graphics2D g2d = (Graphics2D) g;
       if(playGame==false) {
           g2d.setFont(new Font("TimesRoman", Font.BOLD, 20));
           g2d.setColor(Color.white);
           //centers the text on the screen
           g2d.drawString("Press Start to begin game", 290, 350);
           //resets the score
           scoreClass.setScoreSeconds(0);
       }
       //if the start button has been clicked
        else if (playGame==true){
           //draws the cursor
           cursor.draw(g);
           //removes the blocks in remove list from the block list
           for (Blocks removeBlock : removeList)
           {
               block.remove(removeBlock);
           }
           //draws the blocks in the arraylist
           for(Blocks aBlock: block)
            aBlock.draw(g);
           //clears the remove list
           removeList.clear();
       }
    }

    //this is the sub class for event listeners that the timer uses
    private class GUIListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(playGame==true) {
                //repaints everything
                //sets the current location to where the cursor was plus where the cursor has moved to
                cursor.setCurrentX(cursor.getCurrentX());
                cursor.setCurrentY(cursor.getCurrentY());
                repaint();
            }
        }
    }

    //this is the sub class for event listeners that the timer uses
    private class EnemyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(playGame==true) {
                //sets the current location to where the cursor was plus where the cursor has moved to
                //goes through each block and updates its position
                for (Blocks aBlock : block) {
                    aBlock.updateBlock(blockSpeed);
                    //sets location of the collison block
                    aBlock.getRect().y=aBlock.getY();
                }
                //increments counter
                createBlockCounter++;
                //after counter reaches 20 a new block is made
                if (createBlockCounter >= 20) {
                    //adds new block to the arraylist
                    block.add(new Blocks(gamePanel));
                    //resets the counter to0
                    createBlockCounter = 0;
                }
                CheckCollision();
                //repaints the screen to show new position of the blocks
                repaint();
                //goes through block list
                for(Blocks aBlock : block)
                {
                    //if the block is below the panel add the block to the reemove l8ist
                    if (aBlock.getY()>PANEL_HEIGHT + 50)
                    {
                        removeList.add(aBlock);
                    }
                }
            }
        }
    }

   // methods to mover the circle from left to right
    public void left(){
        //if the game is still going and the cursor hasnt reached it boundary
        if(playGame==true && cursor.getCurrentX()>0) {
            cursor.setCurrentX(cursor.getCurrentX()- CURSOR_SPEED);
            //moves the cursor collsion rect
            cursor.getCursorRect().x=cursor.getCurrentX();
            //the cursor moves to the left 1.5 pxels at a time
        }
    }

    public void right(){
        //if the game is still going and the cursor hasnt reached it boundary
        if(playGame==true && cursor.getCurrentX()+cursor.getCursorWidth()<PANEL_WIDTH) {
            //the cursor moves to the right 3 pxels at a time
            cursor.setCurrentX(cursor.getCurrentX()+CURSOR_SPEED);
            //moves the cursor collsion rect
            cursor.getCursorRect().x=cursor.getCurrentX();
        }
    }

    //actions on keyboard to control the cursor
    public void keyPressed(KeyEvent e){
        //assigns key to int
        int keyCode = e.getKeyCode();
        //if keycode is left
        if(keyCode==KeyEvent.VK_LEFT){
            left();
        }
        //if keycode is right
        if(keyCode==KeyEvent.VK_RIGHT){
            right();
        }
    }

    public void keyTyped(KeyEvent e){}
    public void keyReleased(KeyEvent e){
    }

    //accessors and mutators
    public boolean isPlayGame() {
        return playGame;
    }
    public void setPlayGame(boolean playGame) {
        this.playGame = playGame;
    }
    public boolean isCheat() {
        return cheat;
    }
    public void setCheat(boolean cheat) {
        this.cheat = cheat;
    }
    public Score getScoreClass() {
        return scoreClass;
    }
    public void setScoreClass(Score scoreClass) {
        this.scoreClass = scoreClass;
    }
    public int getBlockTime() {
        return blockTime;
    }
    public void setBlockTime(int blockTime) {
        this.blockTime = blockTime;
    }
    public int getBlockSpeed() {
        return blockSpeed;
    }
    public void setBlockSpeed(int blockSpeed) {
        this.blockSpeed = blockSpeed;
    }
}
