package running;

import animation.EndScreen;
import animation.GameLevel;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import inerfaces.Animation;
import inerfaces.LevelInformation;
import levels.AnimationRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.util.List;

/**
 * This calss is in charge of the game flow.
 * It will be responsible that each level will start after te other.
 */
public class GameFlow {

    private Counter counterScore;
    private Counter counterLives;
    private AnimationRunner ar;
    private KeyboardSensor ks;
    private GUI gui;
    private HighScoresTable table;
    private File fileName;


    /**
     * @param ar    the animation runner.
     * @param ks    the key boared sensor.
     * @param gui   the given gui.
     * @param table the highscore table.
     * @param name  the file name.
     *              The constructor of the game flow.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, GUI gui, HighScoresTable table, File name) {
        this.gui = gui;
        this.ar = new AnimationRunner(gui);
        this.ks = gui.getKeyboardSensor();
        this.counterLives = new Counter(7);
        this.counterScore = new Counter(0);
        this.table = table;
        this.fileName = name;
    }

    /**
     * @param levels all the levels who wll be played.
     *               This func will run level after level,
     *               and when there is no more levels to run,
     *               it will end.
     */
    public void runLevels(List<LevelInformation> levels) {
        for (LevelInformation levelInfo : levels) {
            GameLevel level = new GameLevel(levelInfo, this.ks, this.ar, this.gui,
                    this.counterScore, this.counterLives);
            level.initialize();

            while (level.getCounterBlocks().getValue() > 0 && this.counterLives.getValue() > 0) {
                level.playOneTurn();
            }

            if (this.counterLives.getValue() == 0) {
                break;
            }
        }
        this.saveScores(); // checks if the current score enter's top five.
        Animation loseScreen = new EndScreen(this.ks,
                "Game Over. Your score is: " + this.counterScore.getValue());
        Animation winScreen = new EndScreen(this.ks,
                "You Win! Your score is: " + this.counterScore.getValue());
        Animation highScoreScreen = new HighScoresAnimation(table, "space", this.ks, false);
        Animation a1k = new KeyPressStoppableAnimation(this.ks, "space", loseScreen);
        Animation a2k = new KeyPressStoppableAnimation(this.ks, "space", winScreen);
        Animation a3k = new KeyPressStoppableAnimation(this.ks, "space", highScoreScreen);
        if (this.counterLives.getValue() == 0) {
            this.ar.run(a1k);
            this.ar.run(a3k);
        } else {
            this.ar.run(a2k);
            this.ar.run(a3k);
        }
        this.counterScore.setCounter(0);
        this.counterLives.setCounter(7);
    }

    /**
     * This func is responsible for saving score to high score table.
     */
    public void saveScores() {
        if (this.isFileExist()) { // case the file exist.
            try {
                this.table.load(fileName);
                if (this.isTopFive()) {
                    ScoreInfo score = new ScoreInfo(askForName(), this.counterScore.getValue());
                    this.table.add(score);
                    this.table.save(fileName);
                }
            } catch (IOException e) {
                System.out.println("");
            }
        } else { // case there is no such file.
            try {
                if (this.isTopFive()) {
                    ScoreInfo score = new ScoreInfo(askForName(), this.counterScore.getValue());
                    this.table.add(score);
                    table.save(fileName);
                }
            } catch (IOException e) {
                System.out.println("");
            }
        }
    }

    /**
     * @return bool val.
     * The func return if the score is at top five.
     */
    public boolean isTopFive() {
        return table.getRank(this.counterScore.getValue()) < table.getTableMaxSize();
    }

    /**
     * @return string.
     * The func will ask the user for his name, case he is top five high score.
     */
    public String askForName() {
        DialogManager dialog = gui.getDialogManager();
        return dialog.showQuestionDialog("Name", "What is your name?", "");
    }

    /**
     * @return bool val.
     * The func checks if some file is exist already.
     */
    public boolean isFileExist() {
        ObjectInputStream in = null;
        try {
            FileInputStream file = new FileInputStream("highscores");
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }
}
