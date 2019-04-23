package inerfaces;

import geometry.Point;
import geometry.Rectangle;
import objects.Ball;
import running.Velocity;

/**
 * This interface can return the collision object.
 * and can be hit by object
 */
public interface Collidable {
    /**
     * @return Rectangle
     * This func can return the object itself.
     */
    Rectangle getCollisionRectangle();

    /**
     * @param hitter          the actual hitting object.
     * @param collisionPoint  collision with the object.
     * @param currentVelocity the velocity of some object before the object have been collided.
     * @return Velocity after the collision.
     * This func can means that the class can be hit.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
