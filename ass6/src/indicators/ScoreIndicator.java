package indicators;

import animation.GameLevel;
import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;
import inerfaces.Sprite;

/**
 * This interface can be drawn and use time passed func.
 * indicates the score of the player.
 */
public class ScoreIndicator implements Sprite {

    private Rectangle rec;
    private GameLevel game;

    /**
     * @param game the actual level.
     *             the constructor of the score class.
     */
    public ScoreIndicator(GameLevel game) {
        this.rec = new Rectangle(new Point(0, 0), 800, 10);
        this.game = game;
    }

    /**
     * @param d the surface.
     *          the object can be drawn.
     */
    public void drawOn(DrawSurface d) {
        d.drawRectangle((int) this.rec.getUpperLeft().getX(), (int) this.rec.getUpperLeft().getY(),
                (int) this.rec.getWidth(), (int) this.rec.getHeight() + 10);
        // Draw the text on the blocks.
        d.drawText((int) (this.rec.getUpperLeft().getX() + (this.rec.getWidth() / 2)) - 25,
                (int) (this.rec.getUpperLeft().getY() + (this.rec.getHeight() / 2)) + 12,
                "Score:" + this.game.getCounterScore().toString(), 15);
        d.drawText(620, 16, game.getTheLevel().levelName(), 15);
    }


    /**
     * @param dt frame speed.
     *           The func can use time passed.
     */
    public void timePassed(double dt) {

    }

    /**
     * @param theGame the class where the game start's
     */
    public void addToGame(GameLevel theGame) {
        theGame.addSprite(this);
    }
}
