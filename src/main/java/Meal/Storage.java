package Meal;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private Path filePath;

    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
        try {
            Files.createDirectories(this.filePath.getParent());
            if (!Files.exists(this.filePath)) {
                Files.createFile(this.filePath);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Task> load() {
        List<Task> items = new ArrayList<>();
        try {
            if (!Files.exists(filePath)) return items;

            for (String line : Files.readAllLines(filePath)) {
                if (line.isBlank()) continue;
                String[] p = line.split("\\s*\\|\\s*");
                String type = p[0];
                boolean done = "1".equals(p[1]);
                String name = p[2];

                Task task;
                if (type.equals("T")) {
                    task = new ToDo(name);
                } else if (type.equals("D")) {
                    task = new Deadline(name, p[3]);
                } else if (type.equals("E")) {
                    task = new Event(name, p[3], p[4]);
                } else {
                    task = new ToDo(name);
                }

                if (done) task.silentMark();
                items.add(task);
            }
        } catch (IOException e) {
            System.out.println("Load failed: " + e.getMessage());
        }
        return items;
    }

    public void save(List<Task> tasks) {
        try {
            StringBuilder sb = new StringBuilder();
            for (Task t : tasks) {
                sb.append(toDataString(t)).append(System.lineSeparator());
            }
            Files.writeString(filePath, sb.toString(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.out.println("Save failed: " + e.getMessage());
        }
    }

    private String toDataString(Task t) {
        String done = t.isMarked() ? "1" : "0";
        if (t instanceof ToDo) {
            return String.format("T | %s | %s", done, t.getTaskName());
        } else if (t instanceof Deadline d) {
            return String.format("D | %s | %s | %s", done, d.getTaskName(), d.getDeadline());
        } else if (t instanceof Event e) {
            return String.format("E | %s | %s | %s | %s",
                    done, e.getTaskName(), e.getStartTime(), e.getEndTime());
        }
        return String.format("? | %s | %s", done, t.getTaskName());
    }
}
