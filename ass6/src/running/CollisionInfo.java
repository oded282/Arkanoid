package running;

import geometry.Point;
import inerfaces.Collidable;

/**
 * The class contains information about the collision with some collidable object.
 * The collision point and the object that other object collided with.
 */
public class CollisionInfo {
    private Point collisionPoint;
    private Collidable object;

    /**
     * @param p      collision point.
     * @param object the object that have been collision with.
     *               The class contains information about the collision with some collidable object.
     *               The collision point and the object that other object collided with.
     */
    public CollisionInfo(Point p, Collidable object) {
        this.collisionPoint = p;
        this.object = object;
    }

    /**
     * @return the collision point.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * @return the object that was hit.
     */
    public Collidable collisionObject() {
        return this.object;
    }
}
