package indicators;

import animation.GameLevel;
import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;
import inerfaces.Sprite;

import java.awt.Color;

/**
 * Indicates number of lives left.
 */
public class LivesIndicator implements Sprite {

    private GameLevel game;
    private Rectangle rec;

    /**
     * @param game the specific level.
     */
    public LivesIndicator(GameLevel game) {
        this.game = game;
        this.rec = new Rectangle(new Point(0, 0), 800, 600);
    }

    /**
     * @param d the surface.
     *          the object can be drawn.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.white);
        d.fillRectangle(0, 0, 800, 20);
        // Draw the text on the blocks.
        d.setColor(Color.BLACK);
        d.drawText((int) (this.rec.getUpperLeft().getX() + 100),
                (int) (this.rec.getUpperLeft().getY() + 18),
                "Lives:" + this.game.getCounterLives().toString(), 15);
    }


    /**
     * @param dt frame speed.
     * The func can use time passed.
     */
    public void timePassed(double dt) {

    }

    /**
     * @param theGame the class where the game start's.
     *                adding classes to the game.
     */
    public void addToGame(GameLevel theGame) {
        theGame.addSprite(this);
    }
}
