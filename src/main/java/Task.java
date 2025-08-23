public class Task {
    private String taskName;
    private boolean marked;

    public Task(String taskName) {
        this.taskName = taskName;
        this.marked = false;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public String mark() {
        this.marked = true;
        return ("Nice! I've marked this task as done:\n"
        + this.toString());
    }

    public String unmark() {
        this.marked = false;
        return ("OK, I've marked this task as not done yet:\n"
                + this.toString());
    }

    @Override
    public String toString() {
        return (marked ? "[X] " : "[ ] ") + this.taskName;
    }
}
