package inerfaces;

/**
 * @param <T> generic parameter.
 */
public interface Task<T> {
    /**
     * @return generic parameter.
     * This func run's this task.
     */
    T run();
}
