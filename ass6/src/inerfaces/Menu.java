package inerfaces;

import biuoop.DrawSurface;

/**
 * @param <T> generic interface.
 *            This interface  extends animation and can be drawn.
 */
public interface Menu<T> extends Animation {
    /**
     * @param key       which pressed.
     * @param message   the message that will appear.
     * @param returnVal the generic value that will be return.
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * @return the generic status.
     */
    T getStatus();

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
