package indicators;

import animation.GameLevel;
import biuoop.DrawSurface;
import inerfaces.Sprite;

/**
 * Indicates which level is it.
 */
public class LevelIndicator implements Sprite {


    private String levelName;

    /**
     * @param levelName the string of thr level name.
     *                  the constructor of this level.
     */
    public LevelIndicator(String levelName) {
        this.levelName = levelName;
    }

    /**
     * @param d the surface.
     *          the object can be drawn.
     */
    public void drawOn(DrawSurface d) {
        d.drawText(600, 18, this.levelName, 15);
    }

    /**
     * @param dt frame speed.
     *           The func can use time passed.
     */
    public void timePassed(double dt) {

    }

    /**
     * @param game the class where the game start's
     *             adding classes to the game.
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }
}
