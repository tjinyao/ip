import java.nio.file.*;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Meal {
    public static String SEP = "____________________________________________________________";
    public static Scanner sc = new Scanner(System.in);
    public static ArrayList<Task> items = new ArrayList<>();
    public static Path filePath = Paths.get("data", "duke.txt");

    public static void addTask(Task task) {
        items.add(task);
        System.out.println(SEP);
        System.out.println("Got it. I've added this task:\n"
                + task.toString()
                + "\nNow you have "
                + items.size()
                + " tasks in the list.");
        System.out.println(SEP);
    }

    public static void deleteTask(int i) {
        Task removedTask = items.get(i);
        items.remove(i);
        System.out.println(SEP);
        System.out.println("Got it. I've removed this task:\n"
                + removedTask.toString()
                + "\nNow you have "
                + items.size()
                + " tasks in the list.");
        System.out.println(SEP);
    }

    public static String toDataString(Task t) {
        String done = t.isMarked() ? "1" : "0";
        if (t instanceof ToDo) {
            return String.format("T | %s | %s", done, t.getTaskName());
        }
        else if (t instanceof Deadline) {
            LocalDate by = ((Deadline) t).getDeadline();
            return String.format("D | %s | %s | %s", done, t.getTaskName(), by);
        }
        else if (t instanceof Event) {
            LocalDate from = ((Event) t).getStartTime();
            LocalDate to = ((Event) t).getEndTime();
            return String.format("E | %s | %s | %s | %s", done, t.getTaskName(), from, to);
        }
        return String.format("? | %s | %s", done, t.getTaskName());
    }

    public static void saveAll() {
        try {
            StringBuilder sb = new StringBuilder();
            for (Task t : items) {
                sb.append(toDataString(t)).append(System.lineSeparator());
            }
            Files.writeString(
                    filePath,
                    sb.toString(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            System.out.println("Save failed: " + e.getMessage());
        }
    }

    public static void loadAll() {
        try {
            if (!Files.exists(filePath)) {
                return;
            }
            for (String line : Files.readAllLines(filePath)) {
                if (line.isBlank()) {
                    continue;
                }

                String[] p = line.split("\\s*\\|\\s*");
                String type = p[0];
                boolean done = "1".equals(p[1]);
                String name = p[2];

                Task task;
                if (type.equals("T")) {
                    task = new ToDo(name);
                }
                else if (type.equals("D")) {
                    String by = p[3];
                    task = new Deadline(name, by);
                }
                else if (type.equals("E")) {
                    String from = p[3];
                    String to = p[4];
                    task = new Event(name, from, to);
                }
                else {
                    task = new ToDo(name);
                }
                if (done) {
                    task.silentMark();
                }
                items.add(task);
            }
        }
        catch (IOException e) {
            System.out.println("Load failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        try {
            Files.createDirectories(filePath.getParent());
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
        }
        catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }

        loadAll();

        System.out.println("Hello! I'm Meal\n"
                + "What can I do for you?\n");

        while (true) {
            String line = sc.nextLine().trim();
            try {

            if (line.equals("bye")) {
                break;
            }
            else if (line.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < items.size(); i++) {
                    System.out.println((i + 1) + "." + items.get(i).toString());
                }
                System.out.println(SEP);
                continue;
            }

            else if (line.startsWith("mark ")) {
                String numStr = line.substring(5).trim();
                int num = Integer.parseInt(numStr) - 1;
                if (num < 0 || num >= items.size()) {
                    throw new BadInputException("Number is out of range");
                }
                System.out.println(SEP);
                System.out.println(items.get(num).mark());
                System.out.println(SEP);
                saveAll();
                continue;
            }

            else if (line.startsWith("unmark ")) {
                String numStr = line.substring(7).trim();
                int num = Integer.parseInt(numStr) - 1;
                if (num < 0 || num >= items.size()) {
                    throw new BadInputException("Number is out of range");
                }
                System.out.println(SEP);
                System.out.println(items.get(num).unmark());
                System.out.println(SEP);
                saveAll();
                continue;
            }

            else if (line.startsWith("delete ")) {
                String numStr = line.substring(7).trim();
                int num = Integer.parseInt(numStr) - 1;
                if (num < 0 || num >= items.size()) {
                    throw new BadInputException("Number is out of range");
                }
                deleteTask(num);
                saveAll();
                continue;
            }

            else if (line.startsWith("todo ")) {
                String name = line.substring(5).trim();
                if (name.isEmpty()) {
                    throw new EmptyInputException("Description can't be empty.");
                }
                Task todoTask = new ToDo(name);
                addTask(todoTask);
                saveAll();
                continue;
            }

            else if (line.startsWith("deadline ")) {
                String[] parts = line.substring(9).trim().split("/by");
                String name = parts[0].trim();
                String by = parts[1].trim();
                Deadline deadlineTask = new Deadline(name, by);
                addTask(deadlineTask);
                saveAll();
                continue;
            }

            else if (line.startsWith("event ")) {
                String[] parts = line.substring(6).trim().split("/from");
                String name = parts[0].trim();
                String rest = parts[1].trim();
                String[] parts2 = rest.split("/to");
                String from = parts2[0].trim();
                String to = parts2[1].trim();
                Event eventTask = new Event(name, from, to);
                addTask(eventTask);
                saveAll();
                continue;
            }
            else {
                throw new BadInputException("Bad input :(");
            }
        }
            catch (Exception e) {
                System.out.println(SEP);
                System.out.println(e.getMessage());
                System.out.println(SEP);
                continue;
            }
        }

        System.out.println(SEP);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(SEP);
    }

}
