package geometry;
import java.util.ArrayList;
import java.util.List;

/**
 * The rectangle get's the upper left point the width and the height,
 * and build the shape by those parameters. Can return his parameters.
 * And can find some intersection point between him and some line.
 */
public class Rectangle {

    private double width;
    private double height;
    private Point upperLeft;

    /**
     * @param upperLeft the upper left of the rectangle.
     * @param width     the rectangle width.
     * @param height    the rectangle height.
     *                  The rectangle constructor.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.width = width;
        this.height = height;
        this.upperLeft = upperLeft;
    }

    /**
     * @param newUpperLeft the upper left of the rectangle.
     *                  The func will set the upper left point to new one.
     *                  So the rectangle will be places in a new place.
     */
    public void setRectangle(Point newUpperLeft) {
        this.upperLeft = newUpperLeft;
    }

    /**
     * @param line the line who will be checked.
     * @return java.util.List list of the intersection.
     *                  The func will check collision of the line sent,
     *                  with the specific rectangle.
     *                  Return list of the collisions with the object.
     *                  If there is no collision it will return null.
     */
    public java.util.List intersectionPoints(Line line) {
        List<Point> intersectionList = new ArrayList<Point>();
        Line topLine = new Line(upperLeft.getX(), upperLeft.getY(),
                upperLeft.getX() + width, upperLeft.getY());
        Line bottomLine = new Line(upperLeft.getX(), upperLeft.getY() + height,
                upperLeft.getX() + width, upperLeft.getY() + height);
        Line leftLine = new Line(upperLeft.getX(), upperLeft.getY(), upperLeft.getX(), upperLeft.getY() + height);
        Line rightLine = new Line(upperLeft.getX() + width, upperLeft.getY(),
                upperLeft.getX() + width, upperLeft.getY() + height);
        Line[] lineList = {topLine, bottomLine, leftLine, rightLine};
        for (int i = 0; i < lineList.length; i++) {
            if (line.isIntersecting(lineList[i])) {
                intersectionList.add(line.intersectionWith(lineList[i]));
            }
        }
        if (intersectionList.size() == 0) {
            return null;
        }
        intersectionList = removeDuplicates(intersectionList); // Remove duplicate values.
        return intersectionList;
    }

    /**
     * @return double the width size.
     * The func will return the rectangle width.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * @return double the height size.
     * The func will return the rectangle height.
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * @return point the upper left point.
     * The func will return the rectangle upper left point.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * @param list of collision points.
     * @return java.util.List the clean list.
     * The func will remove duplicates point.
     * case there is kind of point in the array,
     * the func will remove them.
     * Further more the func will remove the null's point.
     */
    public java.util.List removeDuplicates(List<Point> list) {
        List<Point> cleanList = new ArrayList<Point>();
        for (int j = 0; j < list.size(); j++) {
            for (int i = j + 1; i < list.size(); i++) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(i);
                }
            }
        }
        for (int i = 0; i < list.size(); i++) { // remove all the non null values to new list.
            if (list.get(i) != null) {
                cleanList.add(list.get(i));
            }
        }
        return cleanList;
    }
}
