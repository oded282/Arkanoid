package inerfaces;

import objects.Ball;
import objects.Block;

/**
 * Inteface of the class who need's to "hear" hit events.
 */
public interface HitListener {
    /**
     * @param beingHit the block which being hit.
     * @param hitter   the object who hit the block.
     *                 This func will "hear" that the object was being hit,
     *                 and disappear the block who sent to the func.
     */
    void hitEvent(Block beingHit, Ball hitter);
}
