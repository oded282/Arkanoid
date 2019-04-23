package hitlistener;

import animation.GameLevel;
import inerfaces.HitListener;
import objects.Ball;
import objects.Block;
import running.Counter;

/**
 * This class implements HitListener So it can "hear" notification from hitting objects.
 * And remove balls that got out of the bounds.
 */
public class BallRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBalls;

    /**
     * @param game         the level who sent to the constructor.
     * @param removedBalls the counter of the balls.
     */
    public BallRemover(GameLevel game, Counter removedBalls) {
        this.game = game;
        this.remainingBalls = removedBalls;
    }

    /**
     * @param beingHit the block which being hit.
     * @param hitter   the object who hit the block.
     *                 This func will "hear" that the block was being hit,
     *                 and disappear this ball.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.voidremoveFromGame(this.game);
        this.remainingBalls.decrease(1);
    }

}
