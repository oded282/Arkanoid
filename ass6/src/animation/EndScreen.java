package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import inerfaces.Animation;


import java.awt.Color;

/**
 * This is the last screen who's shown, when the game ends.
 */
public class EndScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;
    private String winOrLose;

    /**
     * @param k         the key board sent.
     * @param winOrLose the string who will be printed at the end of the game.
     */
    public EndScreen(KeyboardSensor k, String winOrLose) {
        this.keyboard = k;
        this.stop = false;
        this.winOrLose = winOrLose;

    }

    /**
     * @param d  the surface upon we draw.
     * @param dt frame speed.
     *           draw the end screen frame with the score and a string.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.green.darker().darker());
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(Color.BLACK);
        if (winOrLose.contains("You Win")) {
            d.drawText(92, d.getHeight() / 2, winOrLose, 50);
            d.setColor(Color.blue);
            d.drawText(95, d.getHeight() / 2, winOrLose, 50);

        } else {
            d.drawText(73, d.getHeight() / 2, winOrLose, 50);
            d.setColor(Color.blue);
            d.drawText(75, d.getHeight() / 2, winOrLose, 50);
        }
    }

    /**
     * @return boolean val.
     * This func will check if the frame drawn should stop appear.
     */
    public boolean shouldStop() {
        return this.stop;
    }
}
