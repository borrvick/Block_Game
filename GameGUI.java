/*
 * Author: Ben Orrvick
 * Class: CIS 306
 * Date: 4/25
 * Purpose: to create a game where the objective is to avoid objects and get a high
 * score as possible by staying alive as long as possible
 * */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameGUI extends JFrame {

    //JPanels
    private JPanel buttonPanel;
    private JPanel scorePanel;
    //private JPanel gamePanel;
    //JLabels
    private JLabel scoreLbl;
    //label for difficuly
    private JLabel difficutlyLbl;
    //JButtons
    private JButton startBtn;
    //turns on cheats
    private JButton cheatBtn;
    //button for instructions
    private JButton instuctBtn;
    //radio buttons for cheats
    private JRadioButton cheatOnRdo;
    private JRadioButton cheatOffRdo;
    private ButtonGroup cheatBtnGroup;
    //radio buttons for difficulty
    private JRadioButton slowRdo;
    private JRadioButton normalRdo;
    private JRadioButton fastRdo;
    private ButtonGroup difficultyBtnGroup;
    //object to add panel to class
    private GamePanelClass game;
    //listener
    private GUIListener listenerClass;
    //ints for diffucult
    private final int SLOW_BLOCK_TIME=35;
    private final int SLOW_BLOCK_SPEED=7;
    private final int NORMAL_BLOCK_TIME=10;
    private final int NORMAL_BLOCK_SPEED=6;
    private final int FAST_BLOCK_TIME=10;
    private final int FAST_BLOCK_SPEED=15;


    public GameGUI(){
        //sets title
        this.setTitle("Block Game");
        //exists when closed
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //creates the main border layout which everything will be nested in
        this.setLayout(new BorderLayout());
        //stes size obviously
        this.setSize(800,800);
        //makes it so the window can not be resized
        this.setResizable(false);
        //creates a new object of Game pnel class
        game = new GamePanelClass(this);
        //creates listener
        listenerClass = new GUIListener();
        //creates things by calling functions
        ScorePanel();
        ButtonPanel();
        GamePanel();
        //add action listener to button
        startBtn.addActionListener(listenerClass);
        cheatBtn.addActionListener(listenerClass);
        instuctBtn.addActionListener(listenerClass);
        //makes the gui visible
        this.setVisible(true);
        //height is 705 and width is 794 of where the game is played
    }

    private void GamePanel(){
        //assigns the object to a jpanel
        //adds the jpanel to the current form
        this.add(game, BorderLayout.CENTER);
    }

    //creates the score panel
    private void ScorePanel(){
        //creates jopanel
        scorePanel = new JPanel();
        //sets jlabel and creates
        scoreLbl = new JLabel("Score: 0");
        //adds to panel
        scorePanel.add(scoreLbl);
        //changes color and border
        scorePanel.setBackground(Color.yellow);
        scorePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        //adds to border north
        this.add(scorePanel, BorderLayout.NORTH);
    }

    //creates the button panel
    private void ButtonPanel(){
        //creates panel
        buttonPanel = new JPanel();
        //creates button group for radio buttons
        cheatBtnGroup = new ButtonGroup();
        //creates button
        startBtn = new JButton("Start");
        cheatBtn = new JButton("Cheat");
        instuctBtn = new JButton("Instructions");
        //creates jradio buttons
        cheatOffRdo = new JRadioButton("Off");
        cheatOnRdo = new JRadioButton("On");
        //adds radio buttons to group
        cheatBtnGroup.add(cheatOnRdo);
        cheatBtnGroup.add(cheatOffRdo);
        //sets background and font color
        startBtn.setBackground(Color.DARK_GRAY);
        startBtn.setForeground(Color.WHITE);
        //the set borderpainted and st opaque is for running on mac
        startBtn.setBorderPainted(false);
        startBtn.setOpaque(true);
        cheatBtn.setBackground(Color.DARK_GRAY);
        cheatBtn.setForeground(Color.WHITE);
        cheatBtn.setBorderPainted(false);
        cheatBtn.setOpaque(true);
        instuctBtn.setBackground(Color.DARK_GRAY);
        instuctBtn.setForeground(Color.WHITE);
        instuctBtn.setBorderPainted(false);
        instuctBtn.setOpaque(true);
        cheatOnRdo.setBackground(Color.YELLOW);
        cheatOffRdo.setBackground(Color.YELLOW);

        //creats jradiobuttons for difficulty and button group
        slowRdo = new JRadioButton("Slow");
        normalRdo = new JRadioButton("Normal");
        fastRdo = new JRadioButton("Fast");
        difficultyBtnGroup = new ButtonGroup();
        //sets colors
        slowRdo.setBackground(Color.YELLOW);
        normalRdo.setBackground(Color.YELLOW);
        fastRdo.setBackground(Color.YELLOW);
        difficultyBtnGroup.add(slowRdo);
        difficultyBtnGroup.add(normalRdo);
        difficultyBtnGroup.add(fastRdo);
        //jlabel for the difficulty
        difficutlyLbl = new JLabel("Select Difficulty: ");
        //adds button to panel
        buttonPanel.add(difficutlyLbl);
        buttonPanel.add(slowRdo);
        buttonPanel.add(normalRdo);
        buttonPanel.add(fastRdo);
        buttonPanel.add(startBtn);
        buttonPanel.add(cheatBtn);
        buttonPanel.add(cheatOnRdo);
        buttonPanel.add(cheatOffRdo);
        buttonPanel.add(instuctBtn);
        //changes button qualities
        buttonPanel.setBackground(Color.yellow);
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        //sets the off to enabled by default
        cheatOffRdo.setSelected(true);
        normalRdo.setSelected(true);
        //adds the panel to the borderlayout
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void Instructions(){
        //assigns instructions to a string
        String instructions = "Use the left and right arrow keys to navigate and avoid the yellow blocks. " +
                "If you hit one game over. Stay alive as long as possible!";
        //THIS IS A TEST
        JOptionPane.showMessageDialog(null, instructions);
    }
    //this is the subclass used forbuttons to clal the methods above
    private class GUIListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == startBtn) {
                //disables radio buttons
                slowRdo.setEnabled(false);
                normalRdo.setEnabled(false);
                fastRdo.setEnabled(false);
                startBtn.setEnabled(false);
                //if statements for which difficulty is selected
                if (slowRdo.isSelected()){
                    game.setBlockSpeed(SLOW_BLOCK_SPEED);
                    game.setBlockTime(SLOW_BLOCK_TIME);
                }
                else if (normalRdo.isSelected()){
                    game.setBlockSpeed(NORMAL_BLOCK_SPEED);
                    game.setBlockTime(NORMAL_BLOCK_TIME);
                }
                else{
                    game.setBlockSpeed(FAST_BLOCK_SPEED);
                    game.setBlockTime(FAST_BLOCK_TIME);
                }
                //starts the timer to create enemies
                game.StartEnemyTimer();
                //if the start button has been clicked
                game.setPlayGame(true);
                //calls the method in the game oanel class that starts the score
                game.StartScore();
            }
            //if instructions button the instructions method is called
            else if(e.getSource() == instuctBtn){
                Instructions();
            }
            else{
                //sets the cheat ti true in the game object depending on radio button
                if(cheatOnRdo.isSelected()) {
                    game.setCheat(true);
                }
                else{
                    game.setCheat(false);
                }
            }
        }
    }
    //accessors and mutators
    public JLabel getScoreLbl() {
        return scoreLbl;
    }
    public JRadioButton getSlowRdo() {
        return slowRdo;
    }
    public JRadioButton getNormalRdo() {
        return normalRdo;
    }
    public JRadioButton getFastRdo() {
        return fastRdo;
    }
    public JButton getStartBtn() {
        return startBtn;
    }
    public void setStartBtn(JButton startBtn) {
        this.startBtn = startBtn;
    }
}
