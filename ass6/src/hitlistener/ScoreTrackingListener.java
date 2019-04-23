package hitlistener;

import animation.GameLevel;
import inerfaces.HitListener;
import objects.Ball;
import objects.Block;
import running.Counter;

/**
 * This class listen to the blocks removed and keep the score updated.
 */
public class ScoreTrackingListener implements HitListener {

    private Counter currentScore;
    private GameLevel game;

    /**
     * @param scoreCounter of the game.
     * @param game         the specific game.
     */
    public ScoreTrackingListener(Counter scoreCounter, GameLevel game) {
        this.currentScore = scoreCounter;
        this.game = game;
    }


    /**
     * @param beingHit the block which being hit.
     * @param hitter   the object who hit the block.
     *                 This func will "hear" that the block was being hit,
     *                 and update the score counter.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getNumOfHits() == 1) {
            this.currentScore.increase(5);
        }
        if (this.game.getCounterBlocks().getValue() == 0) {
            this.currentScore.increase(100);
        }
        this.currentScore.increase(5);
    }
}
