package running;

import geometry.Point;

/**
 * This class will set the ball velocity.
 * each and every ball got velocity of his own.
 * and the velocity is one of the method each and every ball has.
 * we can change and decide which way the ball will move to,
 * using the balls velocity values.
 */

public class Velocity {

    private Point point;
    private double dx;
    private double dy;

    /**
     * @param dx the dx value of the velocity.
     * @param dy the dy value of the velocity.
     *           The func get's two value and initialize the methods,
     *           create a point and initial values.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
        this.point = new Point(dx, dy);
    }

    /**
     * @return the dx value of the velocity.
     * The func return dx value.
     */
    public double getDxVelocity() {
        return this.dx;
    }

    /**
     * @return the dy value of the velocity.
     * The func return dy value.
     */
    public double getDyVelocity() {
        return this.dy;
    }

    /**
     * @param angle the angle, the balls moving forward.
     * @param speed how fast the ball is moving.
     * @return the new Velocity.
     * The func gets angle and speed,
     * and calculate the new dx/dy value
     * create new velocity and return the value.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx2 = speed * Math.cos(Math.toRadians(angle));
        double dy2 = speed * Math.sin(Math.toRadians(angle));
        return new Velocity(dx2, dy2);
    }

    /**
     * @param dt frame speed.
     * @param p  point.
     * @return the new point.
     * The func will add the new dx/dy value,
     * to the existing dx/dy value.
     * make the ball move by applying to a new point.
     */
    public Point applyToPoint(Point p, double dt) {
        return new Point(p.getX() + dx * dt, p.getY() + dy * dt);
    }

    /**
     * @return double the speed value.
     * The func return's the speed value.
     */
    public double getSpeed() {
        return Math.sqrt(this.getDxVelocity() * this.getDxVelocity() + this.getDyVelocity() * this.getDyVelocity());
    }
}
