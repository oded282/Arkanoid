package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import inerfaces.Animation;


/**
 * This class is responsible to activate all the static screens
 * that appears in the game, such as end screen, pause and high score table.
 */
public class KeyPressStoppableAnimation implements Animation {

    private KeyboardSensor ks;
    private String key;
    private Animation animation;
    private boolean stop;
    private boolean isAlreadyPressed;

    /**
     * @param ks        the keyboard.
     * @param key       the key who stops the specfic screen from running.
     * @param animation the specific screen animation.
     *                  The constructor of this class.
     */
    public KeyPressStoppableAnimation(KeyboardSensor ks, String key, Animation animation) {
        this.ks = ks;
        this.key = key;
        this.animation = animation;
        this.stop = false;
        this.isAlreadyPressed = true;
    }

    /**
     * @param d  the surface upon we draw.
     * @param dt frame speed.
     *           This func suppose to draw one frame on the surface given.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        this.animation.doOneFrame(d, dt);
        if (!this.ks.isPressed(this.key)) {
            this.isAlreadyPressed = false;
        }
        if (this.ks.isPressed(this.key)) {
            if (this.isAlreadyPressed) {
                return;
            }
            this.stop = true;
        }
    }

    /**
     * @return boolean val.
     * This func will check all the conditions to stop drawing frames.
     */
    public boolean shouldStop() {
        return this.stop;
    }
}
