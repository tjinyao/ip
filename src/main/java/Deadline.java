public class Deadline extends Task {
    String taskTime;

    public Deadline(String taskName, String taskTime) {
        super(taskName);
        this.taskTime = taskTime;
    }

    public String getDeadline() {
        return this.taskTime;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.taskTime + ")";
    }
}
