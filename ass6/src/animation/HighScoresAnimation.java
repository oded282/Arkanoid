package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import inerfaces.Animation;
import running.HighScoresTable;

import java.awt.Color;

/**
 * This class is resposible of the high score animation.
 * It hold's all the functions that suppose to screen, open and save scores.
 */

public class HighScoresAnimation implements Animation {

    private KeyboardSensor keyboard;
    private boolean stop;
    private HighScoresTable scores;
    private String endKey;

    /**
     * @param scores   the high sccore table.
     * @param endKey   the key which stops the animation from running.
     * @param keyboard the keyboared sensor.
     * @param stop     the val which responsible of stop running the frame.
     *                 This is the constructor.
     */
    public HighScoresAnimation(HighScoresTable scores, String endKey, KeyboardSensor keyboard, boolean stop) {
        this.keyboard = keyboard;
        this.stop = stop;
        this.scores = scores;
        this.endKey = endKey;

    }

    /**
     * @param d  the surface upon we draw.
     * @param dt frame speed.
     *           This func suppose to draw one frame on the surface given.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.gray.brighter());
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(Color.gray.darker());
        d.drawText(176, d.getHeight() / 2 - 200, "High Score Table", 60);
        d.setColor(Color.black.brighter());
        d.drawText(180, d.getHeight() / 2 - 200, "High Score Table", 60);
        this.scores.printVisualTable(d);
    }


    /**
     * @return boolean val.
     * This func will check all the conditions to stop drawing frames.
     */
    public boolean shouldStop() {
        return this.stop;
    }

}
