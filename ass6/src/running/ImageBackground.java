package running;

import biuoop.DrawSurface;
import geometry.Rectangle;
import inerfaces.GameBackgrounds;

import java.awt.Image;

/**
 * This calss responsible of backgrounds.
 */
public class ImageBackground implements GameBackgrounds {

    private Image image;

    /**
     * @param image The image of the background.
     */
    public ImageBackground(Image image) {
        this.image = image;
    }

    /**
     * @param d the surface which will be drawn on.
     * @param r the shape that will be drawn.
     *          This func will be in charge of drawing the given shape.
     */
    @Override
    public void draw(DrawSurface d, Rectangle r) {
        d.drawImage((int) r.getUpperLeft().getX(), (int) r.getUpperLeft().getY(), this.image);
    }
}
