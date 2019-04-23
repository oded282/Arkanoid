package running;

import biuoop.DrawSurface;
import geometry.Rectangle;
import inerfaces.GameBackgrounds;

import java.awt.Color;

/**
 * This calss responsible of backgrounds.
 */
public class ColorBackground implements GameBackgrounds {

    private Color color;

    /**
     * @param color the color of the background.
     */
    public ColorBackground(Color color) {
        this.color = color;
    }

    /**
     * @param d the surface which will be drawn on.
     * @param r the shape that will be drawn.
     *          This func will be in charge of drawing the given shape.
     */
    @Override
    public void draw(DrawSurface d, Rectangle r) {
        d.setColor(color);
        d.fillRectangle((int) r.getUpperLeft().getX(), (int) r.getUpperLeft().getY(),
                (int) r.getWidth(), (int) r.getHeight());
    }
}
