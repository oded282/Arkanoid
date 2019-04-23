package objects;

import animation.GameLevel;
import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;
import inerfaces.Collidable;
import inerfaces.HitListener;
import inerfaces.HitNotifier;
import inerfaces.Sprite;
import running.ColorBackground;
import running.Velocity;
import inerfaces.GameBackgrounds;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The block will be collidable, so each ball who will collide with him
 * will bounce back. further more the ball block will implement the sprite interface,
 * so he can be drawn on the screen.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    public static final double EPSILON = 0.005;
    private Rectangle block;
    private Point upperLeft;
    private double width;
    private double height;
    private java.awt.Color color;
    private int numOfHits;
    private ArrayList<HitListener> hitListeners;
    private GameBackgrounds background;
    private Map<Integer, GameBackgrounds> mapOfBackground;
    private Color stroke;


    /**
     * @param upperLeft the upper left corner of the block
     * @param width     width of the block.
     * @param height    height of the block.
     * @param hit       number of times ball hit's the block.
     * @param color     the ball's color.
     *                  All the block's members.
     */
    public Block(Point upperLeft, double width, double height, java.awt.Color color, int hit) {
        this.block = new Rectangle(upperLeft, width, height);
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        this.color = color;
        this.numOfHits = hit;
        this.hitListeners = new ArrayList<HitListener>();
        this.background = new ColorBackground(Color.BLACK);
    }

    /**
     * @param p             the upperleft point of the balls.
     * @param width         the blocks width.
     * @param height        the height of the block
     * @param map           the map of the the block background.
     * @param bg            the default background.
     * @param blockStroke   the block's frame.
     * @param numOfHitPoint number of hits.
     *                      The block constructor.
     */
    public Block(Point p, int width, int height, Map<Integer, GameBackgrounds> map,
                 GameBackgrounds bg, Color blockStroke, int numOfHitPoint) {
        this.block = new Rectangle(p, width, height);
        this.upperLeft = p;
        this.width = width;
        this.height = height;
        this.numOfHits = numOfHitPoint;
        this.mapOfBackground = map;
        this.stroke = blockStroke;
        this.hitListeners = new ArrayList<HitListener>();
        this.background = bg;
    }


    /**
     * @param p             the upperleft point of the balls.
     * @param width         the blocks width.
     * @param height        the height of the block
     * @param map           the map of the the block background.
     * @param bg            the default background.
     * @param numOfHitPoint number of hits.
     *                      The block constructor.
     */
    public Block(Point p, int width, int height, Map<Integer, GameBackgrounds> map,
                 GameBackgrounds bg, int numOfHitPoint) {
        this.block = new Rectangle(p, width, height);
        this.upperLeft = p;
        this.width = width;
        this.height = height;
        this.numOfHits = numOfHitPoint;
        this.mapOfBackground = map;
        this.stroke = null;
        this.hitListeners = new ArrayList<HitListener>();
        this.background = bg;
    }

    /**
     * @param height the blocks height.
     * @param width  the blocks width.
     */
    public Block(double height, double width) {
        this.height = height;
        this.width = width;
        this.hitListeners = new ArrayList<>();
        this.background = null;
        this.numOfHits = -1;
        this.stroke = null;
        this.mapOfBackground = null;
    }

    /**
     * @param p set's the block upperleft point.
     */
    public void setBlock(Point p) {
        this.block = new Rectangle(p, this.width, this.height);
        this.upperLeft = p;
    }

    /**
     * @param numberOfHits set't num of hits.
     */
    public void setNumOfHits(int numberOfHits) {
        this.numOfHits = numberOfHits;
    }

    /**
     * @param theBackground set's the block backgeiund.
     */
    public void setBackgroud(GameBackgrounds theBackground) {
        this.background = theBackground;
    }

    /**
     * @param numOfhits  the number of hits.
     * @param theBackground the background that matches the number of hits.
     */
    public void setMapOfBackground(Integer numOfhits, GameBackgrounds theBackground) {
        this.mapOfBackground.put(numOfhits, theBackground);
    }

    /**
     * @param colorFrame set's the block color.
     */
    public void setFrame(Color colorFrame) {
        this.stroke = colorFrame;
    }

    /**
     * @return the block width.
     */
    public double getWidth() {
        return this.width;
    }


    /**
     * @return the block's color.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * @return the block itself.
     */
    public Rectangle getCollisionRectangle() {
        return this.block;
    }

    /**
     * @param hitter          the object that actual hit.
     * @param collisionPoint  the collision with the block.
     * @param currentVelocity the ball's current velocity.
     * @return the new velocity
     * The func checks which velocity will need to return back,
     * after the ball's collision with the block. after colliding the block
     * the block will count down the hits.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        this.notifyHit(hitter);
        //case Collision point is at the upper left corner.
        if (collisionPoint.equals(upperLeft)) {
            this.numOfHits--;
            if ((currentVelocity.getDxVelocity() > 0 && currentVelocity.getDyVelocity() < 0)
                    || (currentVelocity.getDxVelocity() < 0 && currentVelocity.getDyVelocity() > 0)) {
                return new Velocity(-currentVelocity.getDxVelocity(), currentVelocity.getDyVelocity());
            } else {
                return new Velocity(-currentVelocity.getDxVelocity(), -currentVelocity.getDyVelocity());
            }
        }
        if (collisionPoint.equals(new Point(upperLeft.getX() + width, upperLeft.getY() + height))) {
            //case Collision point is at the lower right corner.
            this.numOfHits--;
            if ((currentVelocity.getDxVelocity() > 0 && currentVelocity.getDyVelocity() < 0)
                    || (currentVelocity.getDxVelocity() < 0 && currentVelocity.getDyVelocity() > 0)) {
                return new Velocity(currentVelocity.getDxVelocity(), -currentVelocity.getDyVelocity());
            } else {
                return new Velocity(-currentVelocity.getDxVelocity(), -currentVelocity.getDyVelocity());
            }
        }
        if (collisionPoint.equals(new Point(upperLeft.getX() + width, upperLeft.getY()))) {
            // case Collision point is at the upper right corner.
            if ((currentVelocity.getDxVelocity() > 0 && currentVelocity.getDyVelocity() > 0)) {
                return new Velocity(currentVelocity.getDxVelocity(), -currentVelocity.getDyVelocity());
            } else if ((currentVelocity.getDxVelocity() < 0 && currentVelocity.getDyVelocity() < 0)) {
                return new Velocity(-currentVelocity.getDxVelocity(), currentVelocity.getDyVelocity());
            } else {
                return new Velocity(-currentVelocity.getDxVelocity(), -currentVelocity.getDyVelocity());
            }
        }
        if (collisionPoint.equals(new Point(upperLeft.getX(), upperLeft.getY() + height))) {
            this.numOfHits--;
            // case Collision point is at the lower left corner.
            if ((currentVelocity.getDxVelocity() > 0 && currentVelocity.getDyVelocity() > 0)) {
                return new Velocity(-currentVelocity.getDxVelocity(), currentVelocity.getDyVelocity());
            } else if ((currentVelocity.getDxVelocity() < 0 && currentVelocity.getDyVelocity() < 0)) {
                return new Velocity(currentVelocity.getDxVelocity(), -currentVelocity.getDyVelocity());
            } else {
                return new Velocity(-currentVelocity.getDxVelocity(), -currentVelocity.getDyVelocity());
            }
        }
        // case Collision point is one of the walls, but not the corners.
        if (collisionPoint.getX() <= upperLeft.getX() + EPSILON
                || collisionPoint.getX() >= upperLeft.getX() + width - EPSILON) {
            this.numOfHits--;
            return new Velocity(-currentVelocity.getDxVelocity(), currentVelocity.getDyVelocity());
        } else if (collisionPoint.getY() <= upperLeft.getY() + EPSILON
                || collisionPoint.getY() >= upperLeft.getY() + height - EPSILON) {
            this.numOfHits--;
            return new Velocity(currentVelocity.getDxVelocity(), -currentVelocity.getDyVelocity());
        }
        this.numOfHits--;
        return currentVelocity;
    }


    /**
     * @param surface the surface, the blocks will be drawn on.
     *                The func will draw all the numbers on the blocks,
     *                and the specific block.
     */
    public void drawOn(DrawSurface surface) {
        if (this.mapOfBackground != null) {
            if (this.mapOfBackground.containsKey(this.numOfHits)) {
                this.mapOfBackground.get(this.numOfHits).draw(surface, this.block);
            } else {
                this.background.draw(surface, this.block);
            }
        } else {
            this.background.draw(surface, this.block);
        }
        if (this.stroke != null) {
            surface.setColor(this.stroke);
            surface.drawRectangle((int) this.block.getUpperLeft().getX(),
                    (int) this.block.getUpperLeft().getY(), (int) this.width, (int) this.height);
        }
    }

    /**
     * @param dt frame speed.
     *           Time passed have not been written yet.
     */
    public void timePassed(double dt) {

    }

    /**
     * @param game the surface, the blocks will be drawn on.
     *             The func will add the block to the collidable
     *             and the sprite in the game class.
     */
    public void addToGame(GameLevel game) {
        game.addCollidable(this);
        game.addSprite(this);
    }

    /**
     * @param game the specific game level.
     *             This func will remove the sprite from the game.
     */
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * @param hl the one who will "hear" hits.
     *           Add new listener.
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * @param hl the one who will "hear" hits.
     *           Remove the listener.
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * @param hitter the ball who hit's this block.
     *               This func will notify all the hit listeners that was a hit.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * @return int of number of hits.
     */
    public int getNumOfHits() {
        return numOfHits;
    }
}
