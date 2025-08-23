import java.util.Scanner;
import java.util.ArrayList;

public class Meal {
    public static String SEP = "____________________________________________________________";
    public static Scanner sc = new Scanner(System.in);
    public static ArrayList<Task> items = new ArrayList<>();

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

    public static void main(String[] args) {

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
                continue;
            }

            else if (line.startsWith("unmark ")) {
                String numStr = line.substring(7).trim();
                int num = Integer.parseInt(numStr) - 1;
                if (num < 0 || num > items.size()) {
                    throw new BadInputException("Number is out of range");
                }
                System.out.println(SEP);
                System.out.println(items.get(num).unmark());
                System.out.println(SEP);
                continue;
            }

            else if (line.startsWith("delete ")) {
                String numStr = line.substring(7).trim();
                int num = Integer.parseInt(numStr) - 1;
                if (num < 0 || num > items.size()) {
                    throw new BadInputException("Number is out of range");
                }
                deleteTask(num);
                continue;
            }

            else if (line.startsWith("todo ")) {
                String name = line.substring(5).trim();
                if (name.isEmpty()) {
                    throw new EmptyInputException("Description can't be empty.");
                }
                Task todoTask = new ToDo(name);
                addTask(todoTask);
            }

            else if (line.startsWith("deadline ")) {
                String[] parts = line.substring(9).trim().split("/by");
                String name = parts[0].trim();
                String by = parts[1].trim();
                Deadline deadlineTask = new Deadline(name, by);
                addTask(deadlineTask);
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
