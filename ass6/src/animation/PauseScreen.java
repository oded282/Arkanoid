package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import inerfaces.Animation;

import java.awt.Color;


/**
 * This class in in charge of stopping the game.
 */
public class PauseScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;

    /**
     * @param k the keyboard sensor.
     */
    public PauseScreen(KeyboardSensor k) {
        this.keyboard = k;
        this.stop = false;
    }

    /**
     * @param d  the surface upon we draw.
     * @param dt frame speed.
     *           Draw's the screen when "p" is pressed.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.green.darker().darker());
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(Color.black);
        d.drawText(237, d.getHeight() / 2, "press space to continue", 32);
        d.drawText(275, d.getHeight() / 3, "paused", 80);
        d.setColor(Color.BLUE);
        d.drawText(280, d.getHeight() / 3, "paused", 80);
        d.drawText(240, d.getHeight() / 2, "press space to continue", 32);
    }

    /**
     * @return boolean val.
     * release the stoppage screen.
     */
    public boolean shouldStop() {
        return this.stop;
    }

}
