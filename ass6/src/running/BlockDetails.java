package running;

/**
 * This class will hold the members of specific block.
 */
public class BlockDetails {
    private String fileOfBlocksDef;
    private int blockStartX;
    private int blockStartY;
    private int rowHeight;
    private int numOfBlocks;

    /**
     * The constructor of the details.
     */
    public BlockDetails() {
        this.fileOfBlocksDef = null;
        this.blockStartX = -1;
        this.blockStartY = -1;
        this.rowHeight = -1;
        this.numOfBlocks = -1;
    }

    /**
     * @return the the file name.
     */
    public String getFileOfBlocksDef() {
        return fileOfBlocksDef;
    }

    /**
     * @param fileOfBlocksDefs set the file name.
     */
    public void setFileOfBlocksDef(String fileOfBlocksDefs) {
        this.fileOfBlocksDef = fileOfBlocksDefs;
    }

    /**
     * @return the block x val.
     */
    public int getBlockStartX() {
        return blockStartX;
    }

    /**
     * @param theBlockStartX set the block x val.
     */
    public void setBlockStartX(int theBlockStartX) {
        this.blockStartX = theBlockStartX;
    }

    /**
     * @return the block y val.
     */
    public int getBlockStartY() {
        return blockStartY;
    }

    /**
     * @param theBlockStartY set the block y val.
     */
    public void setBlockStartY(int theBlockStartY) {
        this.blockStartY = theBlockStartY;
    }

    /**
     * @return the row height.
     */
    public int getRowHeight() {
        return rowHeight;
    }

    /**
     * @param theRowHeight set the row height.
     */
    public void setRowHeight(int theRowHeight) {
        this.rowHeight = theRowHeight;
    }

    /**
     * @return the num of blocks.
     */
    public int getNumOfBlocks() {
        return numOfBlocks;
    }

    /**
     * @param theNumOfBlocks set the number of blocks.
     */
    public void setNumOfBlocks(int theNumOfBlocks) {
        this.numOfBlocks = theNumOfBlocks;
    }
}
