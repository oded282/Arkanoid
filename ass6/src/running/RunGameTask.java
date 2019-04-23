package running;

import inerfaces.LevelInformation;
import inerfaces.Task;

import java.util.List;

/**
 * This class run's the game task.
 */
public class RunGameTask implements Task {
    private GameFlow game;
    private List<LevelInformation> level;

    /**
     * @param game  run some game task.
     * @param level list of levels.
     *              The constructor.
     */
    public RunGameTask(GameFlow game, List<LevelInformation> level) {
        this.game = game;
        this.level = level;
    }

    /**
     * @return null;
     * This func runs the levels.
     */
    public Void run() {
        this.game.runLevels(this.level);
        return null;
    }
}
