package running;

import inerfaces.BlockCreator;
import objects.Block;

import java.util.Map;

/**
 * This class hold two maps.
 * Map of space and map of block information.
 */
public class BlocksFromSymbolsFactory {

    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * @param spacerWidths  map of spaces.
     * @param blockCreators map of blocks.
     *                      The constructor of the class.
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> spacerWidths, Map<String, BlockCreator> blockCreators) {
        this.spacerWidths = spacerWidths;
        this.blockCreators = blockCreators;
    }

    /**
     * @param s string.
     * @return bool val.
     * returns true if 's' is a valid space symbol.
     */
    public boolean isSpaceSymbol(String s) {
        return this.spacerWidths.containsKey(s);
    }

    /**
     * @param s string.
     * @return bool val.
     * returns true if 's' is a valid block symbol.
     */

    public boolean isBlockSymbol(String s) {
        return this.blockCreators.containsKey(s);
    }

    /**
     * @param s string.
     * @param x x pos.
     * @param y y pos.
     * @return the block.
     * Return a block according to the definitions associated
     * with symbol s. The block will be located at position (xpos, ypos).
     */

    public Block getBlock(String s, int x, int y) {
        return this.blockCreators.get(s).create(x, y);
    }

    /**
     * @param s string.
     * @return the space width.
     * Returns the width in pixels associated with the given spacer-symbol.
     */

    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }
}
