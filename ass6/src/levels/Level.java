package levels;

import geometry.DrawRectangle;
import geometry.Point;
import inerfaces.GameBackgrounds;
import inerfaces.LevelInformation;
import inerfaces.Sprite;
import objects.Block;
import running.Velocity;
import java.util.List;

/**
 * This class will hold all the information about the level,
 * which was created in the "factory".
 */
public class Level implements LevelInformation {

    private String levelName;
    private List<Velocity> ballVelocities;
    private int paddleSpeed;
    private int paddleWidth;
    private GameBackgrounds backgrounds;
    private List<Block> listOfBlocks;
    private Point paddleLocation;

    /**
     * The constructor of the level.
     */
    public Level() {
        this.levelName = null;
        this.ballVelocities = null;
        this.paddleSpeed = -1;
        this.paddleWidth = -1;
        this.backgrounds = null;
        this.listOfBlocks = null;
        this.listOfBlocks = null;
        this.paddleLocation = new Point(330, 580);
    }


    /**
     *
     * @param levelName the level's name.
     * @param ballVelocities the balls velocitys.
     * @param paddleSpeed the paddle speed.
     * @param paddleWidth the paddle width.
     *                    This func initialize the level members.
     */
    public Level(String levelName, List<Velocity> ballVelocities, int paddleSpeed, int paddleWidth) {
        this.levelName = levelName;
        this.ballVelocities = ballVelocities;
        this.paddleSpeed = paddleSpeed;
        this.paddleWidth = paddleWidth;
    }

    /**
     *
     * @param blocksList list of the blocks created for spicific level.
     */
    public void setListOfBlocks(List<Block> blocksList) {
        this.listOfBlocks = blocksList;
    }

    /**
     *
     * @param NameOfLevel level name.
     *                  set's the level name.
     */
    public void setLevelName(String NameOfLevel) {
        this.levelName = NameOfLevel;
    }

    /**
     *
     * @param velOfBalls the balls velocity.
     *                       sets the ball velocity.
     */
    public void setBallVelocities(List<Velocity> velOfBalls) {
        this.ballVelocities = velOfBalls;
    }

    /**
     *
     * @param speedOfPaddle the paddle speed.
     *                    set's the paddle speed.
     */
    public void setPaddleSpeed(int speedOfPaddle) {
        this.paddleSpeed = speedOfPaddle;
    }

    /**
     *
     * @param widthOfPaddle the paddle width.
     *                    set's the paddle width.
     */
    public void setPaddleWidth(int widthOfPaddle) {
        this.paddleWidth = widthOfPaddle;
    }

    /**
     *
     * @param theBackgrounds the background of the game.
     *                    set's the background.
     */
    public void setBackgrounds(GameBackgrounds theBackgrounds) {
        this.backgrounds = theBackgrounds;
    }

    /**
     *
     * @return int the number of balls created.
     */
    @Override
    public int numberOfBalls() {
        return this.ballVelocities.size();
    }

    /**
     *
     * @return the balls velocity.
     */
    @Override
    public List<Velocity> initialBallVelocities() {
        return this.ballVelocities;
    }

    /**
     *
     * @return the paddles speed.
     */
    @Override
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     *
     * @return the paddle width.
     */
    @Override
    public int paddleWidth() {
        return this.paddleWidth;
    }

    /**
     *
     * @return the level's name.
     */
    @Override
    public String levelName() {
        return this.levelName;
    }

    /**
     *
     * @return the background of the level.
     */
    @Override
    public Sprite getBackground() {
        return new DrawRectangle(new Point(0, 0), 800,
                600, this.backgrounds);
    }

    /**
     *
     * @return the list of this level.
     */
    @Override
    public List<Block> blocks() {
        return this.listOfBlocks;
    }

    /**
     *
     * @return the number of blocks last to remove.
     */
    public int numberOfBlocksToRemove() {
        return -1;
    }

    /**
     *
     * @return the level's paddle location.
     */
    @Override
    public Point getPaddleLocation() {
        return this.paddleLocation;
    }
}
