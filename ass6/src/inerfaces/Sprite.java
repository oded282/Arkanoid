package inerfaces;

import animation.GameLevel;
import biuoop.DrawSurface;

/**
 * This interface can be drawn and use time passed func.
 */
public interface Sprite {
    /**
     * @param d the surface.
     *          the object can be drawn.
     */
    void drawOn(DrawSurface d);

    /**
     * @param dt frame speed.
     * The func can use time passed.
     */
    void timePassed(double dt);

    /**
     * @param game the class where the game start's
     *             adding classes to the game.
     */
    void addToGame(GameLevel game);
}
