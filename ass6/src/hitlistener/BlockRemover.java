package hitlistener;

import animation.GameLevel;
import inerfaces.HitListener;
import objects.Ball;
import objects.Block;
import running.Counter;

/**
 * This class will be HitListener.
 * It will need to be updated if was any hits.
 * if there is. It should remove the block from the game.
 */
public class BlockRemover implements HitListener {

    private GameLevel game;
    private Counter remainingBlocks;

    /**
     * @param game          the specific level.
     * @param removedBlocks the counter of the blocks.
     */
    public BlockRemover(GameLevel game, Counter removedBlocks) {
        this.game = game;
        this.remainingBlocks = removedBlocks;
    }

    /**
     * @param beingHit the block which being hit.
     * @param hitter   the object who hit the block.
     *                 This func will "hear" that the block was being hit,
     *                 and disappear the block who sent to the func.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getNumOfHits() == 1) {
            beingHit.removeFromGame(this.game);
            remainingBlocks.decrease(1);
            beingHit.removeHitListener(this);
        }

    }
}
