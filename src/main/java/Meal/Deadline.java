package Meal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    LocalDate taskTime;
    private static final DateTimeFormatter PRETTY = DateTimeFormatter.ofPattern("MMM d yyyy");

    public Deadline(String taskName, String taskTime) {
        super(taskName);
        this.taskTime = LocalDate.parse(taskTime);
    }

    public LocalDate getDeadline() {
        return this.taskTime;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.taskTime.format(PRETTY) + ")";
    }
}
