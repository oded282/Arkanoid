package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import inerfaces.Menu;
import inerfaces.Task;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * @param <T> generic parameter.
 *            This class is responsible of showing the menu of the game.
 *            In the menu will be some options the player can choose from like
 *            start a game, show high score table and exit the game.
 */
public class MenuAnimation<T> implements Menu<Task<Void>> {


    private List<String> keyList;
    private List<String> messageList;
    private List<Task<Void>> valList;
    private Task<Void> status;
    private KeyboardSensor ks;
    private String title;

    /**
     * @param title the title of the animation.
     * @param ks    keyboared sensor.
     *              The constructor of the class.
     */
    public MenuAnimation(String title, KeyboardSensor ks) {
        this.ks = ks;
        this.title = title;
        this.keyList = new ArrayList<String>();
        this.messageList = new ArrayList<String>();
        this.valList = new ArrayList<Task<Void>>();
        this.title = title;
        this.status = null;
    }

    /**
     * @param key       the value which was given by the user.
     * @param message   the maessage that will show.
     * @param returnVal the value of the task.
     */
    public void addSelection(String key, String message, Task<Void> returnVal) {
        this.keyList.add(key);
        this.messageList.add(message);
        this.valList.add(returnVal);
    }

    /**
     * @return the specific status.
     */
    public Task<Void> getStatus() {
        Task<Void> temp = this.status;
        this.status = null;
        return temp;
    }


    /**
     * @param d  the surface upon we draw.
     * @param dt frame speed.
     *           This func suppose to draw one frame on the surface given.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.GRAY.brighter());
        d.fillRectangle(0, 0, 800, 600);
        d.drawText(100, 100, this.title, 40);
        for (int i = 0; i < this.keyList.size(); i++) {
            if (this.ks.isPressed(this.keyList.get(i))) {
                this.status = this.valList.get(i);
            }
            d.setColor(Color.BLACK);
            d.drawText(200, 150 + 50 * i, this.messageList.get(i), 40);
        }
        d.drawText(200, 80, this.title, 70);
    }


    /**
     * @return boolean val.
     * This func will check all the conditions to stop drawing frames.
     */
    public boolean shouldStop() {
        return this.status != null;
    }

}
