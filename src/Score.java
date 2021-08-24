/*
 * Author: Ben Orrvick
 * Class: CIS 306
 * Date: 4/25
 * Purpose: to create a game where the objective is to avoid objects and get a high
 * score as possible by staying alive as long as possible
 * */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;


public class Score {
    //Thread timeThread= new Thread();
    //main gui object
    private GameGUI gameGui;
    //gamepanel object
    private GamePanelClass gamePanel;
    //integer to keep track of score
    private int scoreSeconds;
    //timer
    private Timer scoreCounter;

    //constructor
    public Score(GameGUI pGameGUI, GamePanelClass pGamePanel){
        //creates panels
        gameGui = pGameGUI;
        gamePanel = pGamePanel;
        //initializes counter to 0
        scoreSeconds =0;
    }


    //method to start the timer
    public void StartScoreTimer(){
        //creates new timer
        scoreCounter= new Timer(50, new TimerListener());
        //starts time for every50 milliseconds
        scoreCounter.start();
    }

    //method to set timer to null
    public void nullCounter()
    {
        scoreCounter=null;
    }

    //accesor and mutatora
    public Timer getScoreCounter() {
        return scoreCounter;
    }
    public void setScoreCounter(Timer scoreCounter) {
        this.scoreCounter = scoreCounter;
    }

    public int getScoreSeconds() {
        return scoreSeconds;
    }
    public void setScoreSeconds(int scoreSeconds) {
        this.scoreSeconds = scoreSeconds;
    }


    //listern for the score timer
    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (gamePanel.isPlayGame() == true) {
                //incremets count which is done every 50 millisecons
                scoreSeconds++;
                //updates jlabel
                gameGui.getScoreLbl().setText("Score: " + Integer.toString(scoreSeconds));
            }
        }
    }
}
