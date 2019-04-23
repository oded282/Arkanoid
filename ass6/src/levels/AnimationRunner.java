package levels;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import inerfaces.Animation;


/**
 * This class will be responsible to run the game.
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;

    /**
     * @param gui the gui of the game.
     *            This constructor will be in charge for the gui initialization,
     *            and the frames drawn per second.
     */
    public AnimationRunner(GUI gui) {
        this.framesPerSecond = 60;
        this.gui = gui;
    }

    /**
     *
     * @return the gui of the animation.
     */
    public GUI getGui() {
        return this.gui;
    }

    /**
     * @param animation the animation who suppose to run.
     *                  This func will run the animation who was given to him.
     *                  It will checks when to stop as well.
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / this.framesPerSecond;
        Sleeper sleeper = new Sleeper();
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing.
            DrawSurface d = gui.getDrawSurface();
            animation.doOneFrame(d, (double) 1 / (double) this.framesPerSecond);
            gui.show(d);
            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

}
