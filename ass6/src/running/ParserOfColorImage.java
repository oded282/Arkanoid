package running;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Color;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * This func will have map of color and creates color from string and image as well.
 */
public class ParserOfColorImage {
    private Map<String, Color> colorsMap;


    /**
     * generate color from given string.
     **/
    public ParserOfColorImage() {
        this.colorsMap = new TreeMap<String, Color>();
        this.colorsMap.put("black", Color.black);
        this.colorsMap.put("blue", Color.blue);
        this.colorsMap.put("cyan", Color.cyan);
        this.colorsMap.put("gray", Color.gray);
        this.colorsMap.put("lightGray", Color.lightGray);
        this.colorsMap.put("green", Color.green);
        this.colorsMap.put("orange", Color.orange);
        this.colorsMap.put("pink", Color.pink);
        this.colorsMap.put("red", Color.red);
        this.colorsMap.put("white", Color.white);
        this.colorsMap.put("yellow", Color.yellow);

    }

    /**
     * @param string of the name of the file
     * @return color
     */
    public Color colorsFromString(String string) {
        if (!string.contains("(")) {
            return colorsMap.get(string);
        }
        int x, y, z;
        int startIndex = string.lastIndexOf("(") + 1;
        int endIndex = string.indexOf(")");
        if (string.startsWith("color(RGB")) {
            string = string.substring(startIndex, endIndex);
            String[] numbers = string.split(",");
            x = Integer.parseInt(numbers[0]);
            y = Integer.parseInt(numbers[1]);
            z = Integer.parseInt(numbers[2]);
            return new Color(x, y, z);
        }
        string = string.substring(startIndex, endIndex);
        return this.colorsMap.get(string);
    }

    /**
     * @param s a string of the file name
     * @return image
     */
    public Image imageFromString(String s) {
        int beginIndex = s.indexOf("(") + 1;
        int endIndex = s.indexOf(")");
        s = s.substring(beginIndex, endIndex);
        Image image = null;
        try {
            // try reading the image definition
            image = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(s));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return image;

    }
}