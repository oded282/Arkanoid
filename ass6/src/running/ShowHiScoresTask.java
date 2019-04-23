package running;

import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import inerfaces.Animation;
import inerfaces.Task;
import levels.AnimationRunner;

import java.io.File;

/**
 * This class is a task that in some point will run and show the high score.
 */
public class ShowHiScoresTask implements Task {
    private AnimationRunner runner;
    private Animation scoreTable;

    /**
     * @param runner     the runner.
     * @param scoreTable the animation of the score.
     *                   The constructor.
     */
    public ShowHiScoresTask(AnimationRunner runner, Animation scoreTable) {
        this.runner = runner;
        this.scoreTable = scoreTable;
    }

    /**
     * @return null;
     * This func runs the high score table.
     */
    public Void run() {
        File fileName = new File("highscores");
        HighScoresTable table = new HighScoresTable(5);
        table = HighScoresTable.loadFromFile(fileName);
        this.runner.run(new KeyPressStoppableAnimation(this.runner.getGui().getKeyboardSensor(), "space",
                new HighScoresAnimation(table, "space", runner.getGui().getKeyboardSensor(), false)));
        return null;
    }

}
