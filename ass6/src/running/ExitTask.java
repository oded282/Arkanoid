package running;

import inerfaces.Task;

/**
 * This class responsible of exit some task.
 */
public class ExitTask implements Task {
    /**
     * @return void .
     * This func exist the task.
     */
    public Void run() {
        System.exit(0);
        return null;
    }
}
