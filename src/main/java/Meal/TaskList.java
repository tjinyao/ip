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

    public Task add(Task t) {
        items.add(t);
        return t;
    }

    public Task delete(int oneBasedIndex) {
        int i = toZeroBased(oneBasedIndex);
        return items.remove(i);
    }

    public String mark(int oneBasedIndex) {
        int i = toZeroBased(oneBasedIndex);
        return items.get(i).mark();
    }

    public String unmark(int oneBasedIndex) {
        int i = toZeroBased(oneBasedIndex);
        return items.get(i).unmark();
    }

    private int toZeroBased(int oneBased) {
        int i = oneBased - 1;
        if (i < 0 || i >= items.size()) throw new BadInputException("Number is out of range");
        return i;
    }
}
