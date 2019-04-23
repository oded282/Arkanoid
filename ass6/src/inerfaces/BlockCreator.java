package inerfaces;

import objects.Block;

/**
 * This interface is incharge of creating blocks.
 * The interface will implement the factory pattern.
 */
public interface BlockCreator {
    /**
     * @param xpos the x position.
     * @param ypos the y positin.
     * @return new block.
     * This func creates the block.
     */
    Block create(int xpos, int ypos);
}
