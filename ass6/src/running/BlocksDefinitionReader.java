package running;

import inerfaces.BlockCreator;
import inerfaces.GameBackgrounds;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class gets the information of the block from a file.
 */
public class BlocksDefinitionReader {
    /**
     * @param reader the given file.
     * @return the info about a block that matches given string.
     * This func will initialize the blocks value by the strings whi matches them.
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {

        Map<String, Integer> spaceInfo = new HashMap<String, Integer>();
        List<Map<String, String>> blockInfoMap = new ArrayList<Map<String, String>>();
        Map<String, String> defaultInfoMap = new HashMap<String, String>();

        while (true) {


            List<String> blockInfo = splitToString(reader);
            if (blockInfo == null) {
                break;
            }

            String defaultString = getDefaultString(blockInfo);
            if (defaultString != null) {
                defaultInfoMap = createMap(defaultString);
            }

            for (int i = 0; i < blockInfo.size(); i++) {
                if (blockInfo.get(i).contains("bdef")) {
                    blockInfoMap.add(createMap(blockInfo.get(i)));
                }
                if (blockInfo.get(i).contains("sdef")) {
                    spaceInfo = createSpaceMap(blockInfo.get(i), spaceInfo);
                }
            }
            if (blockInfo.isEmpty()) {
                break;
            }

        }
        Map<String, BlockCreator> map = setBlockCreator(blockInfoMap, defaultInfoMap);
        return new BlocksFromSymbolsFactory(spaceInfo, map);
    }

    /**
     * @param s list of strings.
     * @return the string who contains "default" string.
     */
    public static String getDefaultString(List<String> s) {
        for (int i = 0; i < s.size(); i++) {
            if (s.get(i).contains("default")) {
                return s.get(i);
            }
        }
        return null;
    }

    /**
     * @param s         given string.
     * @param spaceInfo map of info about spaces.
     * @return new map with string and some space value.
     * This func will create the map who contains the space value.
     */
    public static Map<String, Integer> createSpaceMap(String s, Map<String, Integer> spaceInfo) {
        Pattern patSymbols = Pattern.compile("symbol:. width:\\d+");
        Matcher matcher = patSymbols.matcher(s);
        if (matcher.find()) {
            String sub = s.substring(matcher.start(), matcher.end());
            spaceInfo.put(sub.substring(7, 8), Integer.parseInt(sub.substring(15)));
        }
        return spaceInfo;
    }

    /**
     * @param s given string.
     * @return the map with the block value mapping.
     */
    public static Map<String, String> createMap(String s) {

        Map<String, String> map = new HashMap<String, String>();
        Pattern patHeight = Pattern.compile("height:\\d+");
        Pattern patWidth = Pattern.compile("width:\\d+");
        Pattern patHitPoint = Pattern.compile("hit_points:\\d+");
        Pattern patStroke = Pattern.compile("stroke:color\\([a-z]+|RGB\\(\\d+,\\d+,\\d+\\)\\)");
        Pattern patFill = Pattern.compile(
                "(fill(-\\d)*:((color\\((RGB\\(\\d+,\\d+,\\d+\\)|[A-Za-z]+)\\))|(image\\([^\\s]+\\))))");
        //Pattern patFill2 = Pattern.compile("(fill(-\\d)*:(color|image)\\([^\\s]\\))");
        Pattern patSymbols = Pattern.compile("symbol:.");


        Matcher matcher = patHeight.matcher(s);
        if (matcher.find()) {
            String subString = s.substring(matcher.start() + 7, matcher.end());
            map.put("height", subString);
        }
        matcher = patWidth.matcher(s);
        if (matcher.find()) {
            String subString = s.substring(matcher.start() + 6, matcher.end());
            map.put("width", subString);
        }
        matcher = patHitPoint.matcher(s);
        if (matcher.find()) {
            String subString = s.substring(matcher.start() + 11, matcher.end());
            map.put("hit_points", subString);
        }
        matcher = patStroke.matcher(s);
        if (matcher.find()) {
            String subString = s.substring(matcher.start() + 13, matcher.end());
            if (subString.contains("RGB")) {
                String[] subStringList = subString.split(",");
                map.put("stroke", subString);
            } else {
                map.put("stroke", subString);
            }
        }
        matcher = patFill.matcher(s);
        for (int k = 0; k < 10; k++) {
            if (matcher.find()) {
                String sub = s.substring(matcher.start(), matcher.end());
                if (sub.contains("-")) {
                    int index = sub.indexOf("-");
                    String s2 = sub.substring(index + 1, index + 2);

                    if (sub.contains("color")) {
                        if (sub.contains("RGB")) {
                            Pattern p = Pattern.compile("RGB\\(\\d+,\\d+,\\d+\\)");
                            Matcher m = p.matcher(sub);
                            if (m.find()) {
                                map.put(s2, sub.substring(m.start(), m.end()));
                            }
                        } else {
                            map.put(s2, sub.substring(13, sub.length() - 1));
                        }
                    }
                    if (sub.contains("image")) {
                        map.put(s2, sub.substring(13, sub.length() - 1));
                    }
                } else {
                    if (sub.contains("color")) {
                        if (sub.contains("RGB")) {
                            Pattern p = Pattern.compile("RGB\\(\\d+,\\d+,\\d+\\)");
                            Matcher m = p.matcher(sub);
                            if (m.find()) {
                                map.put("fill", sub.substring(m.start(), m.end()));
                            }
                        } else {
                            map.put("fill", sub.substring(11, sub.length() - 1));
                        }
                    }
                    if (sub.contains("image")) {
                        map.put("fill", sub.substring(11, sub.length() - 1));
                    }
                }
            }
        }
        matcher = patSymbols.matcher(s);
        if (s.startsWith("b")) {
            if (matcher.find()) {
                String sub = s.substring(matcher.start(), matcher.end());
                map.put("symbol", sub.substring(sub.length() - 1, sub.length()));
            }
        }
        if (s.startsWith("s")) {
            if (matcher.find()) {
                String sub = s.substring(matcher.start(), matcher.end());
                map.put(sub.substring(13, 13), s.substring(22));
            }
        }
        return map;
    }

    /**
     * @param reader the file which given.
     * @return list of string.
     * This func split the file into string.
     */
    public static List<String> splitToString(java.io.Reader reader) {
        try {
            LineNumberReader lineNumberReader = new LineNumberReader(reader);
            List<String> level = new ArrayList<String>();
            String line;
            while ((line = lineNumberReader.readLine()) != null) {
                if (line.startsWith("#") || line.isEmpty()) {
                    continue;
                }
                level.add(line);
            }
            return level;
        } catch (IOException e) {
            System.out.println("Error");
            return null;
        }
    }

    /**
     * @param blockInfoMap   the inforamtion of the block.
     * @param defaultInfoMap the info that was given as default string.
     * @return map of the block val.
     * This func will initialize block by default map and info map.
     */
    public static Map<String, BlockCreator> setBlockCreator(List<Map<String, String>> blockInfoMap,
                                                            Map<String, String> defaultInfoMap) {

        Map<String, BlockCreator> blockCreatorMap = new HashMap<String, BlockCreator>();
        BlockFactory block = new BlockFactory();
        ParserOfColorImage parser = new ParserOfColorImage();

        for (int i = 0; i < blockInfoMap.size(); i++) {

            if (defaultInfoMap.containsKey("height")) {
                block.setDefaultHeight(Integer.parseInt(defaultInfoMap.get("height")));
            }
            if (blockInfoMap.get(i).containsKey("height")) {
                block.setDefaultHeight(Integer.parseInt(blockInfoMap.get(i).get("height")));
            }

            if (defaultInfoMap.containsKey("width")) {
                block.setWidth(Integer.parseInt(defaultInfoMap.get("width")));
            }
            if (blockInfoMap.get(i).containsKey("width")) {
                block.setWidth(Integer.parseInt(blockInfoMap.get(i).get("width")));
            }

            if (defaultInfoMap.containsKey("hit_points")) {
                block.setHitPoint(Integer.parseInt(defaultInfoMap.get("hit_points")));
            }
            if (blockInfoMap.get(i).containsKey("hit_points")) {
                block.setHitPoint(Integer.parseInt(blockInfoMap.get(i).get("hit_points")));
            }

            if (defaultInfoMap.containsKey("stroke")) {
                block.setBlockStroke(parser.colorsFromString(defaultInfoMap.get("stroke")));
            }
            if (blockInfoMap.get(i).containsKey("stroke")) {
                block.setBlockStroke(parser.colorsFromString(blockInfoMap.get(i).get("stroke")));
            }

            if (blockInfoMap.get(i).containsKey("fill")) {
                if (blockInfoMap.get(i).get("fill").contains("png") || blockInfoMap.get(i).get("fill").contains("jpg")) {
                    try {
                        GameBackgrounds cb = new ImageBackground(
                                ImageIO.read(ClassLoader.getSystemResourceAsStream(blockInfoMap.get(i).get("fill"))));
                        block.setbackground(cb);
                    } catch (IOException e) {
                        System.out.println("No Image");
                    }
                } else if (blockInfoMap.get(i).get("fill").contains("RGB")) {
                    String s = blockInfoMap.get(i).get("fill").substring(4,
                            blockInfoMap.get(i).get("fill").length() - 2);
                    String[] s1 = s.split(",");
                    GameBackgrounds rgbColor = new ColorBackground(new Color(Integer.parseInt(s1[0]),
                            Integer.parseInt(s1[2]), Integer.parseInt(s1[3])));
                    block.setbackground(rgbColor);
                } else {
                    GameBackgrounds regularColor = new ColorBackground(
                            parser.colorsFromString(blockInfoMap.get(i).get("fill")));
                    block.setbackground(regularColor);
                }
            }
            Map<Integer, GameBackgrounds> backgroundMap = new HashMap<Integer, GameBackgrounds>();
            for (int j = 0; j < 20; j++) {
                if (blockInfoMap.get(i).containsKey("" + j + "")) {
                    if (blockInfoMap.get(i).get("" + j + "").contains("png") ||
                            blockInfoMap.get(i).get("" + j + "").contains("jpg")) {
                        try {
                            GameBackgrounds cb = new ImageBackground(ImageIO.read(
                                    ClassLoader.getSystemResourceAsStream(blockInfoMap.get(i).get("" + j + ""))));
                            backgroundMap.put(j, cb);
                        } catch (IOException e) {
                            System.out.println("No Image");
                        }
                    } else if (blockInfoMap.get(i).get("" + j + "").contains("RGB")) {
                        String s = blockInfoMap.get(i).get("" + j + "").substring(4,
                                blockInfoMap.get(i).get("" + j + "").length() - 1);
                        String[] s1 = s.split(",");
                        GameBackgrounds rgbColor = new ColorBackground(new Color(Integer.parseInt(s1[0]),
                                Integer.parseInt(s1[1]), Integer.parseInt(s1[2])));
                        block.setbackground(rgbColor);
                        backgroundMap.put(j, rgbColor);
                    } else {
                        GameBackgrounds regularColor = new ColorBackground(
                                parser.colorsFromString(blockInfoMap.get(i).get("" + j + "")));
                        backgroundMap.put(j, regularColor);
                    }
                }
            }
            if (!backgroundMap.isEmpty()) {
                block.setMapOfBackground(backgroundMap);
            }
            if (blockInfoMap.get(i).containsKey("symbol")) {
                blockCreatorMap.put(blockInfoMap.get(i).get("symbol"), block);
            } else {
                throw new RuntimeException("No Symbol");
            }
            block = new BlockFactory();
        }
        return blockCreatorMap;
    }
}
