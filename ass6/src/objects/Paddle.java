package objects;

import animation.GameLevel;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import geometry.Point;
import geometry.Rectangle;
import inerfaces.Collidable;
import inerfaces.Sprite;
import running.Velocity;

import java.awt.Color;

/**
 * The paddle will be collidable, so each ball who will collide with him
 * will bounce back. further more the block will implement the sprite interface,
 * so he can be drawn on the screen. The ball can move and will return balls the hits him
 * different from regular block.
 */
public class Paddle implements Sprite, Collidable {
    public static final int PADDLE_LEFT_BORDER = 26;
    public static final int PADDLE_RIGHT_BORDER = 774;

    private Rectangle paddle;
    private biuoop.KeyboardSensor keyboard;
    private java.awt.Color color;
    private int paddleMovement;


    /**
     * @param paddleMovement the movment of the paddle.
     * @param rectangle      the shape of the paddle.
     * @param keyboard       the paddle option to move by the keyboard.
     * @param color          the paddle's color.
     *                       All the paddle's members.
     */
    public Paddle(Rectangle rectangle, biuoop.KeyboardSensor keyboard, java.awt.Color color, int paddleMovement) {
        this.paddle = new Rectangle(rectangle.getUpperLeft(), rectangle.getWidth(), rectangle.getHeight());
        this.keyboard = keyboard;
        this.color = color;
        this.paddleMovement = paddleMovement;
    }

    /**
     * @param dt frame speed.
     *           The func will move the paddle to the left.
     */
    public void moveLeft(double dt) {
        this.paddle.setRectangle(new Point(paddle.getUpperLeft().getX()
                - paddleMovement * dt, paddle.getUpperLeft().getY()));
    }

    /**
     * @param dt frame speed.
     *           The func will move the paddle to the right.
     */
    public void moveRight(double dt) {
        this.paddle.setRectangle(new Point(paddle.getUpperLeft().getX()
                + paddleMovement * dt, paddle.getUpperLeft().getY()));
    }

    /**
     * @param dt frame speed.
     *           The func will put limits to the paddle.
     *           if he get's to the left border of the screen,
     *           we will cannot move him anymore.
     */
    public void timePassed(double dt) {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) { // Left limit.
            if (paddle.getUpperLeft().getX() > PADDLE_LEFT_BORDER) {
                moveLeft(dt);
            }
        } else if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) { // Right limit.
            if (paddle.getUpperLeft().getX() + paddle.getWidth() < PADDLE_RIGHT_BORDER) {
                moveRight(dt);
            }
        }
    }

    /**
     * @param d the drawn surface.
     *          The func will draw the paddle on screen.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) this.paddle.getUpperLeft().getX(), (int) this.paddle.getUpperLeft().getY(),
                (int) this.paddle.getWidth(), (int) this.paddle.getHeight());
        d.setColor(Color.BLACK);
        d.drawRectangle((int) this.paddle.getUpperLeft().getX(), (int) this.paddle.getUpperLeft().getY(),
                (int) this.paddle.getWidth(), (int) this.paddle.getHeight());
    }


    /**
     * @param hitter          the actual hitting object.
     * @param collisionPoint  the collision with the paddle.
     * @param currentVelocity the velocity of the ball before collision occur.
     * @return Velocity the velocity after the collision if there is any.
     * The func will differ between five parts of the paddle,
     * each part will return the ball with different velocity.
     * so the ball will return in different angle in each part of the collision.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double speed = currentVelocity.getSpeed();
        double leftPart = paddle.getWidth() / 5;
        if (collisionPoint.getX() < leftPart + this.paddle.getUpperLeft().getX()) { // Leftest part.
            currentVelocity = Velocity.fromAngleAndSpeed(210, speed);
        } else if (collisionPoint.getX() >= leftPart + this.paddle.getUpperLeft().getX() && collisionPoint.getX()
                < 2 * leftPart + this.paddle.getUpperLeft().getX()) {
            currentVelocity = Velocity.fromAngleAndSpeed(240, speed);
        } else if (collisionPoint.getX() >= 3 * leftPart + this.paddle.getUpperLeft().getX() && collisionPoint.getX()
                < 4 * leftPart + this.paddle.getUpperLeft().getX()) {
            currentVelocity = Velocity.fromAngleAndSpeed(300, speed);
        } else if (collisionPoint.getX() >= 4 * leftPart + this.paddle.getUpperLeft().getX()) { // Rightest part.
            currentVelocity = Velocity.fromAngleAndSpeed(330, speed);
        } else { // middle part.
            currentVelocity = new Velocity(currentVelocity.getDxVelocity(), -currentVelocity.getDyVelocity());
        }
        return currentVelocity;
    }

    /**
     * @param g the surface, the blocks will be drawn on.
     *          The func will add the block to the collidable
     *          and the sprite in the game class.
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * @return the paddle.
     * Returns the paddle itself.
     */
    public Rectangle getCollisionRectangle() {
        return this.paddle;
    }

}
