package geometry;

import animation.GameLevel;
import biuoop.DrawSurface;
import inerfaces.GameBackgrounds;
import inerfaces.Sprite;

/**
 * This class is in charge of the rectangles who will be drawn.
 */
public class DrawRectangle implements Sprite {


    private GameBackgrounds b;
    private Point upperLeft;
    private double height;
    private double width;

    /**
     * @param upperLeft upper left point of the rec.
     * @param height    the height of the line.
     * @param width     of the line.
     * @param b         of the line.
     */
    public DrawRectangle(Point upperLeft, double height, double width, GameBackgrounds b) {
        this.upperLeft = upperLeft;
        this.height = height;
        this.width = width;
        this.b = b;
    }

    /**
     * @param d the surface.
     *          the object can be drawn.
     */
    public void drawOn(DrawSurface d) {
        b.draw(d, new Rectangle(this.upperLeft, this.height, this.width));
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
