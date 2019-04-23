package inerfaces;

import biuoop.DrawSurface;

/**
 * This interface responsible for the animation of the game.
 */
public interface Animation {
    /**
     * @param d  the surface upon we draw.
     * @param dt frame speed.
     *           This func suppose to draw one frame on the surface given.
     */
    void doOneFrame(DrawSurface d, double dt);


    /**
     * @return boolean val.
     * This func will check all the conditions to stop drawing frames.
     */
    boolean shouldStop();
}
