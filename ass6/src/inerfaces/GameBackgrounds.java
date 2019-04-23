package inerfaces;

import biuoop.DrawSurface;
import geometry.Rectangle;

/**
 * This class responsible of backgrounds.
 */
public interface GameBackgrounds {
    /**
     *
     * @param d the surface which will be drawn on.
     * @param r the shape that will be drawn.
     *          This func will be in charge of drawing the given shape.
     */
    void draw(DrawSurface d, Rectangle r);

}
