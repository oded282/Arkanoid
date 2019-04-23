package geometry;

import java.util.List;
import java.util.ArrayList;

/**
 * This class will set a line.
 * each line has start and end point.
 * and each class got few functions he can do.
 * measure the line length, return the middle point,
 * of the line. Checking if it will intersect with other lines.
 * Checking if other line is equal to him,
 * and check if some point is on the specific line.
 */
public class Line {
    public static final double EPSILON = 0.005;
    private Point start;
    private Point end;

    /**
     * @param start the start value of the line.
     * @param end   the end value of the line.
     *              the start value of the line.
     *              the constructor initialize the values of the line.
     *              by point values.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * @param x1 the x coordinate, start value, of the line.
     * @param y1 the y coordinate, start value, of the point.
     * @param x2 the x coordinate, end value, of the line.
     * @param y2 the y coordinate, end value, of the point.
     *           the constructor initialize the values of the line.
     *           by making two points out of the coordinates,
     *           and initialize the members.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * @return the distance from two point of the line.
     * measures the length of the line,
     * by checking distance between two point
     */
    public double length() {
        return this.start.distance(end);
    }

    /**
     * @return middleValue
     * calculate the x and y values of middle line.
     * by calculating the average,
     * between the two dots of the line.
     */
    public Point middle() {
        double middleXValue = (this.start.getX() + this.end.getX()) / 2;
        double middleYValue = (this.start.getY() + this.end.getY()) / 2;
        Point middleValue = new Point(middleXValue, middleYValue);
        return middleValue;
    }

    /**
     * @return start value
     * return the point value of the start of the line.
     */

    public Point start() {
        return this.start;
    }

    /**
     * @return end value
     * return the point value of the end of the line.
     */
    public Point end() {
        return this.end;
    }

    /**
     * @param other some other line.
     * @return boolean value.
     * Gets another line,
     * and return if there is intersection between the two.
     */
    public boolean isIntersecting(Line other) {
        if (this.intersectionWith(other) == null) {
            return false;
        }
        return true;
    }

    /**
     * @param other second line
     * @return null/point
     * This func checks if there is intersection between two lines.
     * calculate two straight lines equations.
     * and find there intersection,
     * by compering the y's values of the equation,
     * than by the y's value, find's the x value.
     * Because we dealing with segmented lines,
     * we check if the intersection is on the two lines.
     * The func checks few edge cases.
     * if there is intersection return the intersection point,
     * otherwise return null.
     */
    public Point intersectionWith(Line other) {
        Point interPoint;
        // Calculation of this line.
        double a1 = this.end.getY() - this.start.getY();
        double b1 = this.start.getX() - this.end.getX();
        double c1 = a1 * this.start.getX() + b1 * this.start.getY();
        // Calculation of the other line.
        double a2 = other.end.getY() - other.start.getY();
        double b2 = other.start.getX() - other.end.getX();
        double c2 = a2 * other.start.getX() + b2 * other.start.getY();
        // Calculate delta for finding the intersection between two lines.
        double delta = a1 * b2 - a2 * b1;
        // Case the lines are parallel.
        interPoint = new Point((b2 * c1 - b1 * c2) / delta, (a1 * c2 - a2 * c1) / delta);
        if (this.betweenTheLine(interPoint) && other.betweenTheLine(interPoint)) { // Check's where is the intersection.
            return interPoint;
        } else {
            return null;
        }
    }

    /**
     * @param interSectionPoint the intersection between two lines.
     * @return true or false
     * checks if the intersection point is on both line.
     */
    public boolean betweenTheLine(Point interSectionPoint) {
        double minX = Math.min(this.start.getX(), this.end.getX());
        double maxX = Math.max(this.start.getX(), this.end.getX());
        double minY = Math.min(this.start.getY(), this.end.getY());
        double maxY = Math.max(this.start.getY(), this.end.getY());
        return (interSectionPoint.getX() >= minX - EPSILON
                && interSectionPoint.getX() <= maxX + EPSILON
                && interSectionPoint.getY() <= maxY + EPSILON
                && interSectionPoint.getY() >= minY - EPSILON);
    }

    /**
     * @param other second line
     * @return boolean value.
     * Checks if two line are the same,
     * by comparing there start and end values.
     */

    public boolean equals(Line other) {
        if (this.start.equals(other.start) && this.end.equals(other.end)) {
            return true;
        }
        if (this.start.equals(other.end) && this.end.equals(other.start)) {
            return true;
        }
        return false;
    }

    /**
     * @param rect the 'x' value of the intersection.
     * @return Point intersection point.
     * The func will find which intersection point between this line and some rectangle
     * are the closest one to the start of the line. if there is no intersection it returns null.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        Line thisLine = new Line(this.start, this.end);
        List<Point> intersectionList = new ArrayList<Point>();
        intersectionList = rect.intersectionPoints(thisLine);
        if (intersectionList == null) {
            return null;
        }
        if (intersectionList.size() == 1) {
            return intersectionList.get(0);
        }
        if (this.start.distance(intersectionList.get(0)) > this.start.distance(intersectionList.get(1))) {
            return intersectionList.get(1);
        } else {
            return intersectionList.get(0);
        }
    }
}