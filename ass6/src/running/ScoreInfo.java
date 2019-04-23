package running;

import java.io.Serializable;

/**
 * This func will hold info about the high score.
 */

public class ScoreInfo implements Serializable, Comparable<ScoreInfo> {

    private String name;
    private int score;

    /**
     * @param name  the name given to him.
     * @param score the score.
     *              The constructor.
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * @return the name of the player.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the score.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * @return the score and the name as a string.
     */
    public String toString() {
        return this.getName() + "      " + this.getScore() + "";
    }

    /**
     * @param o some score info.
     * @return int.
     * This func suppose to do comparison and return which one is bigger,
     * the given class or this class.
     */
    @Override
    public int compareTo(ScoreInfo o) {
        if (this.getScore() > o.getScore()) {
            return -1;
        } else if (this.getScore() < o.getScore()) {
            return 1;
        } else {
            return 0;
        }
    }
}
