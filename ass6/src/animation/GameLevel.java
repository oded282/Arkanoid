package animation;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import geometry.Point;
import geometry.Rectangle;
import hitlistener.BallRemover;
import hitlistener.BlockRemover;
import hitlistener.ScoreTrackingListener;
import indicators.LivesIndicator;
import indicators.ScoreIndicator;

import inerfaces.GameBackgrounds;
import inerfaces.Animation;
import inerfaces.Collidable;
import inerfaces.LevelInformation;
import inerfaces.Sprite;
import levels.AnimationRunner;
import objects.Ball;
import objects.Block;
import objects.Paddle;
import running.ColorBackground;
import running.Counter;
import running.GameEnvironment;
import running.SpriteCollection;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The game class intialize the game itself. It creates a screen,
 * and draw all the blocks, paddle and balls on the screen. Using two functions runs and  intialize.
 * initialize in charge of creating all object and run in charge of starting the game.
 */
public class GameLevel implements Animation {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Counter counterBlocks;
    private Counter counterBalls;
    private Counter counterScore;
    private Counter counterLives;
    private AnimationRunner runner;
    private boolean running;
    private KeyboardSensor keyboard;
    private LevelInformation level;

    /**
     * @param level the specific level.
     * @param ks    the keyboard sensor.
     * @param ar    the class who run's the animation.
     * @param gui   the gui sent to the func.
     * @param score the counter of the score.
     * @param lives the counter of the lives.
     *              initialize all the methods in the game class.
     *              the sprite collection, GameEnvironment, gui and the counters.
     */
    public GameLevel(LevelInformation level, KeyboardSensor ks, AnimationRunner ar,
                     GUI gui, Counter score, Counter lives) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.gui = gui;
        this.counterBlocks = new Counter(level.blocks().size() - 4);
        this.counterBalls = new Counter(level.numberOfBalls());
        this.counterScore = score;
        this.counterLives = lives;
        this.runner = ar;
        this.keyboard = ks;
        this.level = level;
    }

    /**
     * @param c the collidable object.
     *          Add the collidable to the environment.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * @param s the sprite object
     *          Add the sprite to sprite member.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * The func creates the all the listeners, and add all the blocks to the game.
     * furthermore it creates all the indicators of the game, like score and lives.
     */
    public void initialize() {
        this.level.getBackground().addToGame(this);
        LivesIndicator indicateLives = new LivesIndicator(this);
        indicateLives.addToGame(this);
        ScoreIndicator indicateScore = new ScoreIndicator(this);
        indicateScore.addToGame(this);
        ScoreTrackingListener scoreTracking = new ScoreTrackingListener(counterScore, this);
        List<Block> blocksList = new ArrayList<Block>(this.initialLimitBlocks());
        blocksList.addAll(this.level.blocks());
        this.counterBlocks.setCounter(blocksList.size() - 4);
        BallRemover removeBall = new BallRemover(this, counterBalls);
        BlockRemover b = new BlockRemover(this, this.counterBlocks);
        for (int i = 0; i < blocksList.size(); i++) {
            blocksList.get(i).addToGame(this);
            if (i == 3) {
                blocksList.get(i).addHitListener(removeBall);
            }
            if (i > 3) {
                blocksList.get(i).addHitListener(b);
                blocksList.get(i).addHitListener(scoreTracking);
            }
        }
    }

    /**
     * @return list of blocks.
     * This class initial the limits of the game.
     */

    public List<Block> initialLimitBlocks() {
        List<Block> blocksList = new ArrayList<Block>();
        ColorBackground background = new ColorBackground(Color.GRAY);
        Map<Integer, GameBackgrounds> map = new HashMap<Integer, GameBackgrounds>();
        map.put(0, background);
        Block topBlock = new Block(new Point(0, 20), 800, 20, map, background, 0);
        blocksList.add(topBlock);
        Block rightBlock = new Block(new Point(784, 20), 20, 600, map, background, 0);
        blocksList.add(rightBlock);
        Block leftBlock = new Block(new Point(0, 20), 15, 600, map, background, 0);
        blocksList.add(leftBlock);
        Block bottomBlock = new Block(new Point(0, 600), 800, 10, map, background, 0);
        blocksList.add(bottomBlock);
        return blocksList;
    }

    /**
     * This func will play one turn of the specific level.
     * Until there is no more blocks or lives.
     */
    public void playOneTurn() {
        Paddle paddle = new Paddle(new Rectangle(this.level.getPaddleLocation(), this.level.paddleWidth(), 10),
                gui.getKeyboardSensor(), Color.YELLOW, this.level.paddleSpeed());
        paddle.addToGame(this);
        this.createBallsOnTopOfPaddle();
        this.runner.run(new CountdownAnimation(2,
                3, this.sprites)); //stopped here, countdown animation won't show up
        this.running = true;
        this.runner.run(this);
        this.counterBalls.increase(this.level.numberOfBalls());
        this.removeSprite(paddle);
        this.removeCollidable(paddle);
    }

    /**
     * This func will be in charge of creating all the balls.
     */
    public void createBallsOnTopOfPaddle() {
        for (int i = 0; i < this.level.numberOfBalls(); i++) {
            Ball ball = new Ball(new Point(410, 570), 5, Color.white, this.environment);
            ball.setVelocity(this.level.initialBallVelocities().get(i));
            ball.addToGame(this);
        }
    }

    /**
     * @param d  the surface upon we draw.
     * @param dt frame speed.
     *           This func will draw all the sprites on the frame.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);
        if (this.keyboard.isPressed("p")) {
            Animation pause = new PauseScreen(this.keyboard);
            Animation p = new KeyPressStoppableAnimation(this.keyboard, "space", pause);
            this.runner.run(p);
        }
    }

    /**
     * @return boolean val, end the turn or not.
     * This func will be in charge of stopping this turn conditions.
     */
    public boolean shouldStop() {
        if (counterBlocks.getValue() == 0) {
            return this.running;
        }
        if (this.counterBalls.getValue() == 0) {
            this.getCounterLives().decrease(1);
            return this.running;
        }
        return !this.running;
    }

    /**
     * @param c the collidable object.
     *          remove the specific collidable.
     */
    public void removeCollidable(Collidable c) {
        this.environment.getCollidableList().remove(c);
    }

    /**
     * @param s the spritee sent to the func.
     *          Remove tthe spriite.
     */
    public void removeSprite(Sprite s) {
        this.sprites.getSpriteList().remove(s);
    }

    /**
     * @return the counter of the score.
     */
    public Counter getCounterScore() {
        return this.counterScore;
    }

    /**
     * @return the counter of the blocks.
     */
    public Counter getCounterBlocks() {
        return this.counterBlocks;
    }

    /**
     * @return the counter of the lives.
     */
    public Counter getCounterLives() {
        return this.counterLives;
    }

    /**
     *
     * @return Le
     */
    public LevelInformation getTheLevel() {
        return this.level;
    }
}
