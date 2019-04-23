package inerfaces;

import geometry.Point;
import objects.Block;
import running.Velocity;

import java.util.List;

/**
 * Information on the level.
 */
public interface LevelInformation {

    /**
     * @param levelName the level of the name.
     */
    void setLevelName(String levelName);

    /**
     * @param ballVelocities set's the ball velocity.
     */
    void setBallVelocities(List<Velocity> ballVelocities);

    /**
     * @param paddleSpeed set's the paddle speed.
     */
    void setPaddleSpeed(int paddleSpeed);

    /**
     * @param paddleWidth set's the paddle width.
     */
    void setPaddleWidth(int paddleWidth);

    /**
     * @param backgrounds set's the background of the game.
     */
    void setBackgrounds(GameBackgrounds backgrounds);

    /**
     * @param listOfBlocks set's the list of the blocks.
     */
    void setListOfBlocks(List<Block> listOfBlocks);

    /**
     * @return number of balls at this level.
     */
    int numberOfBalls();

    /**
     * @return The initial velocity of each ball
     * This func will create all the balls velocity.
     */
    List<Velocity> initialBallVelocities();

    /**
     * @return the Speed of the paddle movement.
     */
    int paddleSpeed();

    /**
     * @return the width of the paddle.
     */
    int paddleWidth();

    /**
     * @return the name of the specific level.
     */
    String levelName();

    /**
     * @return all the background objects inside a list of sprite.
     */
    Sprite getBackground();

    /**
     * @return list of the blocks at the first level.
     * The Blocks that make up this level, each block contains.
     * its size, color and location.
     */
    List<Block> blocks();

    /**
     * @return number of blocks needed to remove to win the game.
     * before the level is considered to be "cleared".
     */
    int numberOfBlocksToRemove();

    /**
     * @return the upper left point position of the paddle.
     */
    Point getPaddleLocation();

}
