package animation;

import biuoop.DrawSurface;
import biuoop.Sleeper;
import running.Counter;
import inerfaces.Animation;
import running.SpriteCollection;

import java.awt.Color;

/**
 * This func will countdown each game start's.
 * It will implement animation, so it can be drawn on given surface.
 */
public class CountdownAnimation implements Animation {

    private double numOfSeconds;
    private int countFrom;
    private SpriteCollection gameScreen;
    private Counter countdown;

    /**
     * @param numOfSeconds seconds each digit will appear on screen.
     * @param countFrom    second we countdown from.
     * @param gameScreen   all the objects drew before.
     *                     This func will countdown before each game start's.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.countdown = new Counter(countFrom);
    }

    /**
     * @param d  the surface upon we draw.
     * @param dt frame speed.
     *           draw on the surface.
     */

    public void doOneFrame(DrawSurface d, double dt) {
        this.gameScreen.drawAllOn(d);
        d.setColor(Color.white.darker());
        d.drawText(400, 400, this.countdown.toString(), 50);
        long pauseTime = (long) ((this.numOfSeconds / this.countFrom) * 1000);
        Sleeper sleep = new Sleeper();
        if (this.countdown.getValue() < 3) {
            sleep.sleepFor(pauseTime);
        }
        this.countdown.decrease(1);
    }

    /**
     * @return boolean val.
     * This func will check all the conditions to stop drawing frames.
     */
    public boolean shouldStop() {
        if (this.countdown.getValue() == 0) {
            return true;
        }
        return false;
    }
}
