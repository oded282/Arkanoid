package running;

import inerfaces.BlockCreator;
import inerfaces.GameBackgrounds;
import objects.Block;
import geometry.Point;

import java.awt.Color;
import java.util.Map;

/**
 * The Block factory class will execute the factory pattern.
 */
public class BlockFactory implements BlockCreator {
    private int defaultHeight;
    private int width;
    private int hitPoint;
    private GameBackgrounds background;
    private Map<Integer, GameBackgrounds> mapOfBackground;
    private Color blockStroke;

    /**
     * Initialize the block. The constructor.
     */
    public BlockFactory() {
        this.defaultHeight = -1;
        this.width = -1;
        this.hitPoint = -1;
        this.background = null;
        this.mapOfBackground = null;
        this.blockStroke = null;
    }

    /**
     * @param theBackground sets the background.
     */
    public void setbackground(GameBackgrounds theBackground) {
        this.background = theBackground;
    }

    /**
     * @param theDefaultHeight set the default height.
     */
    public void setDefaultHeight(int theDefaultHeight) {
        this.defaultHeight = theDefaultHeight;
    }

    /**
     * @param theWidth sets the block width.
     */
    public void setWidth(int theWidth) {
        this.width = theWidth;
    }

    /**
     * @param theHitPoint set the block hit point.
     */
    public void setHitPoint(int theHitPoint) {
        this.hitPoint = theHitPoint;
    }

    /**
     * @param theMapOfBackground set's the map background of the block.
     */
    public void setMapOfBackground(Map<Integer, GameBackgrounds> theMapOfBackground) {
        this.mapOfBackground = theMapOfBackground;
    }

    /**
     * @param theBlockStroke set's the blocks frame.
     */
    public void setBlockStroke(Color theBlockStroke) {
        this.blockStroke = theBlockStroke;
    }

    /**
     * @param xpos the x position.
     * @param ypos the y positin.
     * @return new block.
     * This func create new block.
     */
    @Override
    public Block create(int xpos, int ypos) {
        Point p = new Point(xpos, ypos);
        if (this.blockStroke != null) {
            return new Block(p, this.width, this.defaultHeight,
                    this.mapOfBackground, this.background, this.blockStroke, this.hitPoint);
        } else {
            return new Block(p, this.width, this.defaultHeight,
                    this.mapOfBackground, this.background, this.blockStroke, this.hitPoint);
        }
    }
}
