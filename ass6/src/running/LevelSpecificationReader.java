package running;

import inerfaces.GameBackgrounds;
import inerfaces.LevelInformation;
import levels.Level;
import objects.Block;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static running.Velocity.fromAngleAndSpeed;

/**
 * This func will get information from given file about the level.
 */
public class LevelSpecificationReader {


    private GameBackgrounds backgrounds;
    private List<Velocity> ballVelocities;
    private List<LevelInformation> listOfLevels;
    private LevelInformation level;
    private BlockDetails blockDetails;

    /**
     * @param reader the file.
     * @return list of levels
     * This func will creates level from file full of information.
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        ballVelocities = new ArrayList<Velocity>();
        blockDetails = new BlockDetails();
        listOfLevels = new ArrayList<LevelInformation>();
        List<String> level2 = new ArrayList<String>();
        while (!(listOfLevels.size() > 3)) {
            level = new Level();
            try {
                LineNumberReader lineNumberReader = new LineNumberReader(reader);
                String line;
                while ((line = lineNumberReader.readLine()) != null) {
                    if (line.startsWith("#")) {
                        continue;
                    }
                    level2.add(line);
                    if (line.equals("") || line.equals("END_LEVEL")) {
                        level2 = this.checkStructure(level2);
                        int i = level2.indexOf("START_BLOCKS");
                        List<String> blockInfo = level2.subList(i + 1, level2.size() - 1);
                        level2 = level2.subList(0, i);
                        this.createLevel(level2);
                        this.createBlockDetails(level2);
                        BlocksFromSymbolsFactory blockSymbolsFactory = BlocksDefinitionReader.fromReader(
                                new InputStreamReader(
                                        ClassLoader.getSystemResourceAsStream(blockDetails.getFileOfBlocksDef())));
                        this.level.setListOfBlocks(doBlock(blockSymbolsFactory, blockInfo, this.blockDetails));
                        listOfLevels.add(level);
                        lineNumberReader.readLine();
                        level = new Level();
                        level2.clear();
                    }
                }
            } catch (IOException e) {
                System.out.println("Error");
                return null;
            }
        }
        return listOfLevels;
    }

    /**
     * @param bsf          map of blocks and ther symbol.
     * @param blockInfo    info about the level.
     * @param theBlockDetails more block info.
     * @return list of blocks.
     * This func will creates the number of blocks given in the file info.
     * the block will be created by the info whi was sent to her.
     */
    public List<Block> doBlock(BlocksFromSymbolsFactory bsf, List<String> blockInfo, BlockDetails theBlockDetails) {
        List<Block> listOfBlock = new ArrayList<Block>();
        int xDefaultVal = theBlockDetails.getBlockStartX();
        for (int i = 0; i < blockInfo.size(); i++) {
            for (int j = 0; j < blockInfo.get(i).length(); j++) {
                if (bsf.isSpaceSymbol(Character.toString(blockInfo.get(i).charAt(j)))) {
                    theBlockDetails.setBlockStartX(bsf.getSpaceWidth(
                            Character.toString(blockInfo.get(i).charAt(j))) + this.blockDetails.getBlockStartX());
                }
                if (bsf.isBlockSymbol(Character.toString(blockInfo.get(i).charAt(j)))) {
                    Block b = bsf.getBlock(Character.toString(blockInfo.get(i).charAt(j)),
                            blockDetails.getBlockStartX(), blockDetails.getBlockStartY());
                    listOfBlock.add(b);
                    this.blockDetails.setBlockStartX(this.blockDetails.getBlockStartX() + (int) b.getWidth());
                }
            }
            this.blockDetails.setBlockStartY(this.blockDetails.getBlockStartY() + this.blockDetails.getRowHeight());
            this.blockDetails.setBlockStartX(xDefaultVal);
        }
        return listOfBlock;
    }

    /**
     * @param s array of strings.
     * @return list of the array whi casted to ints.
     */
    public List<Integer> castingToInt(String[] s) {
        List<Integer> newList = new ArrayList<Integer>();
        for (int i = 0; i < s.length; i++) {
            newList.add(Integer.parseInt(s[i]));
        }
        return newList;
    }

    /**
     * @param s list of strings.
     * @return list of string.
     * This func checks the structure of the strings.
     */
    public List<String> checkStructure(List<String> s) {
        if (s.contains("START_LEVEL")) {
            s.remove("START_LEVEL");
        } else {
            throw new RuntimeException("illegal Structure");
        }
        if (s.contains("END_LEVEL")) {
            s.remove("END_LEVEL");
            s.remove("");
        }
        return s;
    }

    /**
     * @param reader the file sent.
     * @return list of string.
     * This func will split some strings to levels.
     */
    public List<String> splitLevels(java.io.Reader reader) {
        try {
            LineNumberReader lineNumberReader = new LineNumberReader(reader);
            List<String> level2 = new ArrayList<String>();
            String line;
            while ((line = lineNumberReader.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }
                if (line.equals("")) {
                    break;
                }
                level2.add(line);
            }
            if (level2.isEmpty()) {
                return null;
            }
            return level2;
        } catch (IOException e) {
            System.out.println("Error");
            return null;
        }
    }

    /**
     * @param s info about the block.
     *          this func will intialize the info from the strings who have been splited from the file.
     */
    public void createBlockDetails(List<String> s) {

        for (int i = 0; i < s.size(); i++) {

            Pattern patBlockValX = Pattern.compile("blocks_start_x:\\d+");
            Pattern patBlockValY = Pattern.compile("blocks_start_y:\\d+");
            Pattern patRowHeight = Pattern.compile("row_height:\\d+");
            Pattern patNumOfBlocks = Pattern.compile("num_blocks:\\d+");
            Pattern patBlockDef = Pattern.compile("block_definitions:.+[.]txt");

            Matcher matcher = patBlockValX.matcher(s.get(i));
            if (matcher.find()) {
                this.blockDetails.setBlockStartX(Integer.parseInt(
                        s.get(i).substring(matcher.start() + 15, matcher.end())));
                continue;
            }

            matcher = patBlockDef.matcher(s.get(i));
            if (matcher.find()) {
                this.blockDetails.setFileOfBlocksDef(s.get(i).substring(matcher.start() + 18, matcher.end()));
            }

            matcher = patBlockValY.matcher(s.get(i));
            if (matcher.find()) { // The y value of the start of the block.
                this.blockDetails.setBlockStartY(
                        Integer.parseInt(s.get(i).substring(matcher.start() + 15, matcher.end())));
                continue;
            }

            matcher = patRowHeight.matcher(s.get(i));
            if (matcher.find()) { // The x value of the start of the block.
                this.blockDetails.setRowHeight(
                        Integer.parseInt(s.get(i).substring(matcher.start() + 11, matcher.end())));
                continue;
            }

            matcher = patNumOfBlocks.matcher(s.get(i));
            if (matcher.find()) { // The number of block.
                this.blockDetails.setNumOfBlocks(
                        Integer.parseInt(s.get(i).substring(matcher.start() + 11, matcher.end())));
                continue;
            }
        }
    }

    /**
     * @param s list of string of the info.
     *          This func will creates the level out of the string given to him.
     */
    public void createLevel(List<String> s) {
        List<Velocity> tempBallVel = new ArrayList<Velocity>();
        this.ballVelocities.clear();
        for (int i = 0; i < s.size(); i++) {
            Pattern patName = Pattern.compile("level_name:\\w*");
            Pattern patPadSpd = Pattern.compile("paddle_speed:\\d+");
            Pattern patPadWidth = Pattern.compile("paddle_width:\\d+");
            Pattern patBallVel = Pattern.compile("ball_velocities:(-*\\d+,\\d+)*");
            Pattern patOfBackground = Pattern.compile("background:");
            Pattern patOfBackgroundHelper = Pattern.compile("\\d+,\\d+,\\d+");

            Matcher matcher = patName.matcher(s.get(i));
            if (matcher.find()) { // Find's file name.
                this.level.setLevelName(s.get(i).substring(matcher.start() + 11, s.get(i).length()));
                continue;
            }

            matcher = patOfBackground.matcher(s.get(i));
            if (matcher.find()) {
                if (s.get(i).contains("image")) {
                    int j = s.get(i).indexOf("(");
                    int k = s.get(i).indexOf(")");
                    try {
                        GameBackgrounds cb = new ImageBackground(
                                ImageIO.read(ClassLoader.getSystemResourceAsStream(s.get(i).substring(j + 1, k))));
                        this.level.setBackgrounds(cb);
                    } catch (IOException e) {
                        System.out.println("Error");
                    }
                }
                if (s.get(i).contains("color") && !s.get(i).contains("RGB")) {
                    int j = s.get(i).indexOf("(");
                    int k = s.get(i).indexOf(")");
                    this.level.setBackgrounds(new ColorBackground(Color.getColor(s.get(i).substring(j + 1, k - 1))));
                }
                matcher = patOfBackgroundHelper.matcher(s.get(i));
                if (s.get(i).contains("RGB")) {
                    if (matcher.find()) {
                        String subString = s.get(i).substring(matcher.start(), matcher.end());
                        String[] temp = subString.split(",");
                        List<Integer> newList = this.castingToInt(temp);
                        if (temp.length != 3) {
                            throw new RuntimeException("Illegal color");
                        }
                        this.level.setBackgrounds(new ColorBackground(new Color(newList.get(0),
                                newList.get(1), newList.get(2))));
                    }
                }
            }

            matcher = patPadSpd.matcher(s.get(i));
            if (matcher.find()) { // Find's paddle speed.
                this.level.setPaddleSpeed(Integer.parseInt(s.get(i).substring(matcher.start() + 13, matcher.end())));
                continue;
            }

            matcher = patPadWidth.matcher(s.get(i));
            if (matcher.find()) { // Find's the paddle width.
                this.level.setPaddleWidth(Integer.parseInt(s.get(i).substring(matcher.start() + 13, matcher.end())));
                continue;
            }

            matcher = patBallVel.matcher(s.get(i));
            if (matcher.find()) { // The ball's velocity.
                String subString = s.get(i).substring(matcher.start() + 16, s.get(i).length());
                String[] temp = subString.split(" ");
                for (int k = 0; k < temp.length; k++) {
                    String[] su = temp[k].split(",");
                    for (int j = 0; j < 1; j += 1) {
                        int r = Integer.parseInt(su[0]);
                        int l = Integer.parseInt(su[1]);
                        tempBallVel.add(fromAngleAndSpeed((double) r, (double) l));
                    }
                }
                this.level.setBallVelocities(tempBallVel);
            }
        }
    }

}
