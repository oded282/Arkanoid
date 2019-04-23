package geometry;

/**
 * This class will set a point.
 * Each point got to values x's / y's.
 * A point can use some functions, like,
 * measure the distance from other line,
 * checking if other point is equals to another.
 * And return the values of the methods
 */

public class Point {
    private double x;
    private double y;

    /**
     * @param x initialize the x value of the point.
     * @param y initialize the y value of the point.
     *          the constructor initialize the values of the point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param other Other point.
     * @return the distance between two point.
     * The func measure the distance between a point to another.
     */
    public double distance(Point other) {
        double dx = this.x - other.getX();
        double dy = this.y - other.getY();
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    /**
     * @param other Other point.
     * @return boolean expression.
     * The func checks if two points got the same values.
     * And return true if it is, and false otherwise.
     */

    public boolean equals(Point other) {
        return this.x == other.getX() && this.y == other.getY();
    }

    /**
     * @return the x value of the point.
     */
    public double getX() {
        return this.x;
    }

    /**
     * @return the y value of the point.
     */
    public double getY() {
        return this.y;
    }
}