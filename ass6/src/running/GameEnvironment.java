package running;

import geometry.Line;
import geometry.Point;
import inerfaces.Collidable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class got member of all the collidable objects in the game,
 * and can find the collision with some line with all the collidables
 * inside this member list.
 */
public class GameEnvironment {

    private List<Collidable> collidableList;

    /**
     * Constructor of the ball.
     */
    public GameEnvironment() {
        this.collidableList = new ArrayList<Collidable>();
    }

    /**
     * @param c collidable object.
     *          Add the collidablel sent to the member.
     */
    public void addCollidable(Collidable c) {
        this.collidableList.add(c);
    }

    /**
     * @return List<Collidable> collidable objects.
     * Return the list of colliadables.
     */
    public List<Collidable> getCollidableList() {
        return this.collidableList;
    }

    /**
     * @param trajectory the ball's trajectory, some line.
     * @return CollisionInfo inforamtion about the collision.
     * The func will check the line collision with all the collidables,
     * and insert it to an array. if there is no collision return null.
     * if there is couple collision it will check which one is the closest to the
     * line's start.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        int j;
        List<Point> closestInterList = new ArrayList<Point>();
        closestInterList = createIntersectionList(trajectory);
        if (isArrayItemsAreNUll(closestInterList)) { // Case there's no collision with any collidable object.
            return null;
        }
        for (j = 0; j < closestInterList.size(); j++) { // find's the first intersection point in the array.
            if (closestInterList.get(j) != null) {
                break;
            }
        }
        return findTheRightCollision(j, trajectory, closestInterList);
    }

    /**
     * @param j                the  location of the first collision in the list of points.
     * @param trajectory       the ball's trajectory, some line.
     * @param closestInterList list of the line intersection with all collidable.
     * @return CollisionInfo inforamtion about the collision.
     * The func will cehck which of the points in the array
     * is the closest one to the trajectory line start point.
     * than will intialize the collision info with the inforamtion about the collision.
     * Which object the line collide with and what is the intersection point value.
     */
    public CollisionInfo findTheRightCollision(int j, Line trajectory, List<Point> closestInterList) {
        Collidable object;
        Point p;
        p = closestInterList.get(j);
        object = collidableList.get(j);
        for (int i = j + 1; i < closestInterList.size(); i++) { // find the closest intersection point.
            if (closestInterList.get(i) != null) {
                if (trajectory.start().distance(closestInterList.get(j))
                        > trajectory.start().distance(closestInterList.get(i))) {
                    p = closestInterList.get(i);
                    object = collidableList.get(i);
                } else {
                    p = closestInterList.get(j);
                    object = collidableList.get(j);
                }
            }
        }
        return new CollisionInfo(p, object);
    }

    /**
     * @param trajectory the ball's trajectory, some line.
     * @return List<Point> list of points.
     * The func will cehck all the collision and get it into array.
     */
    public List<Point> createIntersectionList(Line trajectory) {
        List<Point> closestInterList = new ArrayList<Point>();
        for (int i = 0; i < collidableList.size(); i++) { // Check's if trajectory collide with one of the collidable.
            closestInterList.add(
                    trajectory.closestIntersectionToStartOfLine(collidableList.get(i).getCollisionRectangle()));
        }
        return closestInterList;
    }

    /**
     * @param closestInterList list of intersection.
     * @return boolean true or false value.
     * Checks if the array contains only null values.
     */
    public boolean isArrayItemsAreNUll(List<Point> closestInterList) {
        for (Point p : closestInterList) {
            if (p != null) {
                return false;
            }
        }
        return true;
    }
}
