package Meal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskList {
    private final ArrayList<Task> items;

    public TaskList() {
        this.items = new ArrayList<>();
    }

    public TaskList(List<Task> initial) {
        this.items = new ArrayList<>();
        if (initial != null) this.items.addAll(initial);
    }

    public int size() { return items.size(); }
    public Task get(int indexZeroBased) { return items.get(indexZeroBased); }

    public List<Task> all() { return Collections.unmodifiableList(items); }

    /** Adds a task */
    public Task add(Task t) {
        items.add(t);
        return t;
    }

    /** Deletes a task */
    public Task delete(int oneBasedIndex) {
        int i = toZeroBased(oneBasedIndex);
        return items.remove(i);
    }

    /** Marks a task */
    public String mark(int oneBasedIndex) {
        int i = toZeroBased(oneBasedIndex);
        return items.get(i).mark();
    }

    /** Unmarks a task */
    public String unmark(int oneBasedIndex) {
        int i = toZeroBased(oneBasedIndex);
        return items.get(i).unmark();
    }

    /** Find a keyword */
    public java.util.List<Task> find(String keyword) {
        String k = keyword.toLowerCase();
        java.util.ArrayList<Task> out = new java.util.ArrayList<>();
        for (Task t : items) {
            if (t.getTaskName().toLowerCase().contains(k)) {
                out.add(t);
            }
        }
        return out;
    }

    /** Converts one based to zero based indexing */
    private int toZeroBased(int oneBased) {
        int i = oneBased - 1;
        if (i < 0 || i >= items.size()) throw new BadInputException("Number is out of range");
        return i;
    }
}
