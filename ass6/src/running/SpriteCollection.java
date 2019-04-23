package running;

import biuoop.DrawSurface;
import inerfaces.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * The class contains list of sprite,
 * adding all the sprite into list.
 */
public class SpriteCollection {
    private List<Sprite> listSprite;

    /**
     * The contrucrot set the list member.
     */
    public SpriteCollection() {
        this.listSprite = new ArrayList<Sprite>();
    }

    /**
     * @param s sent sprite.
     *          The func add the sprite into list.
     */
    public void addSprite(Sprite s) {
        listSprite.add(s);
    }

    /**
     * @return getCollidableList return sprite.
     * The func return's the sprite.
     */
    public List<Sprite> getSpriteList() {
        return this.listSprite;
    }

    /**
     * @param dt frame speed.
     *           The func will activate the time pased func of all the sprites.
     */
    public void notifyAllTimePassed(double dt) {
        for (int i = 0; i < listSprite.size(); i++) {
            listSprite.get(i).timePassed(dt);
        }
    }

    /**
     * @param d the surface.
     *          The func draw all the sprites.
     */
    public void drawAllOn(DrawSurface d) {
        for (int i = 0; i < listSprite.size(); i++) {
            listSprite.get(i).drawOn(d);
        }
    }
}
