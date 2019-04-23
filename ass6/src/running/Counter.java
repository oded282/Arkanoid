package running;

/**
 * This class is in charge of counting.
 */
public class Counter {

    private int counter;

    /**
     * @param counter initialize the counter.
     */

    public Counter(int counter) {
        this.counter = counter;
    }

    /**
     * @param number the number added to counter.
     *               add number to current count.
     */
    public void increase(int number) {
        this.counter = this.counter + number;
    }

    /**
     * @param number the number subtracted from counter.
     *               subtract number from current count.
     */
    public void decrease(int number) {
        this.counter = this.counter - number;
    }

    /**
     * // @return  int the counter val.
     */
    public int getValue() {
        return counter;
    }

    /**
     * @return String of the counter number.
     */
    public String toString() {
        return "" + this.counter + "";
    }

    /**
     * @param size the size of the counter.
     *             set the counter to given size.
     */
    public void setCounter(int size){
        this.counter = size;
    }

}
