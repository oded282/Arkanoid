package objects;

import animation.GameLevel;
import biuoop.DrawSurface;
import geometry.Line;
import geometry.Point;
import inerfaces.Sprite;
import running.CollisionInfo;
import running.GameEnvironment;
import running.Velocity;

import java.awt.Color;

/**
 * This class will create balls.
 * Ball will be created by center value, radius,
 * color and boundaries that ball can move into.
 * as well as a frame that can limit him as well.
 */

public class Ball implements Sprite {
    private Point center;
    private int r;
    private java.awt.Color color;
    private Velocity v;
    private GameEnvironment environmentGame;

    /**
     * @param p               the start value of the line.
     * @param r               the end value of the line.
     * @param color           the coloer of the ball.
     * @param environmentGame things the ball can collide with.
     *                        The constructor initialize the methods.
     *                        color, boundaries, radius and the center
     *                        of the ball.
     */
    public Ball(Point p, int r, java.awt.Color color, GameEnvironment environmentGame) {
        this.center = p;
        this.r = r;
        this.color = color;
        this.environmentGame = environmentGame;
    }

    /**
     * @return the x value of the center's ball.
     * The func return x value.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * @return the y value of the center's ball.
     * The func return y value.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * @return the raduis value of the ball.
     * The func return y value.
     */
    public int getSize() {
        return r;
    }

    /**
     * @return the color value of the ball.
     * The func return the color of the ball.
     */
    public java.awt.Color getColor() {
        return this.color;

    }

    /**
     * @param surface the screen surface.
     *                The func use other function.
     *                to draw the ball on the given DrawSurface
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(getColor());
        surface.fillCircle(this.getX(), this.getY(), r);
        surface.setColor(Color.BLACK);
        surface.drawCircle(this.getX(), this.getY(), r);
    }

    /**
     * @param velocity The func set's the ball's velocity.
     */
    public void setVelocity(Velocity velocity) {
        this.v = velocity;
    }


    /**
     * @param dx sets dx value.
     * @param dy sets dy value.
     *           The func set's the ball's velocity.
     */
    public void setVelocity(double dx, double dy) {
        Velocity velocity = new Velocity(dx, dy);
        setVelocity(velocity);
    }

    /**
     * @return Velocity.
     * The func return the ball's velocity.
     */
    public Velocity getVelocity() {
        return this.v;
    }

    /**
     * @param dt frame speed.
     *           The func move's center of the ball, changing ball's velocity.
     *           if the ball hit's one of the boundaries,
     *           the ball will change his direction.
     *           the direction of the ball determined by the x's and y's value.
     *           whenever the ball's value get to one of the boundaries,
     *           the value will be changed,
     *           to the negative value,
     *           that way the ball will return the opposite way.
     *           At the end of each equation, decrease/increase numbers,
     *           for more accuracy.
     */
    public void moveOneStep(double dt) {
        Line trajectory = new Line(this.center, this.getVelocity().applyToPoint(this.center, dt));
        CollisionInfo cInfo = environmentGame.getClosestCollision(trajectory);
        if (cInfo != null) {
            this.setVelocity(cInfo.collisionObject().hit(this, cInfo.collisionPoint(), this.getVelocity()));
        } else {
            this.center = this.getVelocity().applyToPoint(this.center, dt);
        }
    }

    /**
     * @param dt frame speed.
     * The func will call the move one step func.
     */
    public void timePassed(double dt) {
        this.moveOneStep(dt);
    }

    /**
     * @param game the game class.
     *             The func will add the ball to the game.
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }

    /**
     * @param g the specific level who run's
     *          This func will remove the sprites.
     */
    public void voidremoveFromGame(GameLevel g) {
        g.removeSprite(this);
    }
}




