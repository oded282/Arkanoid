package running;

import biuoop.DrawSurface;

import java.awt.Color;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This func is responsible of the high score table.
 */
public class HighScoresTable implements Serializable {

    private List<ScoreInfo> scoreInfoList;
    private int size;

    /**
     * @param size the size of the table.
     *             Create an empty high-scores table with the specified size.
     *             The size means that the table holds up to size top scores.
     */

    public HighScoresTable(int size) {
        this.scoreInfoList = new ArrayList<>();
        this.size = size;
    }

    /**
     * @param score some score info.
     *              Add a high-score.
     */

    public void add(ScoreInfo score) {
        int rank = this.getRank(score.getScore());
        if (rank > size - 1) {
            return;
        }
        this.scoreInfoList.add(score);
        Collections.sort(this.scoreInfoList);

    }

    /**
     * @return the size of the score table.
     * Return table size.
     */

    public int size() {
        return this.scoreInfoList.size();
    }

    /**
     * @return the list of the score info.
     * Return the current high scores.
     * The list is sorted such that the highest
     * scores come first.
     */

    public List<ScoreInfo> getHighScores() {
        return this.scoreInfoList;
    }

    /**
     * @param score some score.
     * @return the rank of the given score.
     * return the rank of the current score: where will it
     * be on the list if added?
     * Rank 1 means the score will be highest on the list.
     * Rank `size` means the score will be lowest.
     * Rank > `size` means the score is too low and will not
     * be added to the list.
     */
    public int getRank(int score) {
        int counter = this.scoreInfoList.size();
        if (this.scoreInfoList.size() > 5) {
            counter = 6;
        }
        for (int i = 0; i < this.scoreInfoList.size(); i++) {
            if (this.scoreInfoList.get(this.scoreInfoList.size() - 1).getScore() < score) {
                counter--;
            }
        }
        return counter;
    }

    /**
     * Clears the table
     */

    public void clear() {
        this.scoreInfoList.clear();
    }

    /**
     * @param filename the file's name.
     * @throws IOException if the file failed to load.
     *                     Tries to load the file.
     */

    public void load(File filename) throws IOException {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(filename));
            HighScoresTable scoresTable = (HighScoresTable) in.readObject();
            this.scoreInfoList = scoresTable.getHighScores();
        } catch (ClassNotFoundException e) {
            e.getMessage();
        }
    }

    /**
     * @param filename the file name.
     * @throws IOException if failed to save file.
     *                     Try to save given file.
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(this);
        } finally {
            if (out != null) {
                out.close();
            }

        }
    }

    /**
     * @param filename the file name.
     * @return The high score table.
     * Read a table from file and return it.
     * If the file does not exist, or there is a problem with
     * reading it, an empty table is returned.
     */
    public static HighScoresTable loadFromFile(File filename) {
        ObjectInputStream in = null;
        HighScoresTable scoresTable = new HighScoresTable(5);
        try {
            in = new ObjectInputStream(new FileInputStream(filename));
            scoresTable = (HighScoresTable) in.readObject();
        } catch (IOException e) {
            System.out.println("Failed to load file");
        } catch (ClassNotFoundException e) {
            e.getMessage();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    System.out.println("Failed to close file");
                }
            }
        }
        return scoresTable;
    }

    /**
     * This func prints the table.
     */
    public void printTable() {
        int i = 1;
        for (ScoreInfo currScore : this.scoreInfoList) {
            System.out.println("Rank(" + i + ") - name: " + currScore.getName() + "score: " + currScore.getScore());
            i++;
        }
    }

    /**
     * @param d the surface.
     *          prints the table.
     */
    public void printVisualTable(DrawSurface d) {
        int i = 1;
        for (int j = 0; j < this.scoreInfoList.size() && j < this.size; j++) {
            d.setColor(Color.black);
            d.drawText(80, 200 + 50 * i, "" + i + ". " + "" + this.scoreInfoList.get(i - 1).toString(), 30);
            i++;
        }
    }

    /**
     * @return the maximum size if the table.
     */
    public int getTableMaxSize() {
        return this.size;
    }
}
