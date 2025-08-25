package Meal;

import java.util.Scanner;

public class Ui {
    private static final String SEP = "____________________________________________________________";
    private final Scanner sc = new Scanner(System.in);

    /** Read one trimmed command line from user */
    public String readCommand() {
        return sc.nextLine().trim();
    }

    public void showLine() {
        System.out.println(SEP);
    }

    public void showError(String msg) {
        showLine();
        System.out.println(msg);
        showLine();
    }

    public void showLoadingError() {
        showError("Load failed.");
    }

    public void showWelcome() {
        System.out.println("Hello! I'm Meal.Meal\nWhat can I do for you?\n");
    }

    public void showGoodbye() {
        showLine();
        System.out.println("Bye. Hope to see you again soon!");
        showLine();
    }

    /** Print the current list with numbering */
    public void showList(TaskList tasks) {
        showLine();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
        showLine();
    }

    /** After adding a task */
    public void showAdd(Task t, int size) {
        showLine();
        System.out.println("Got it. I've added this task:\n" + t);
        System.out.println("Now you have " + size + " tasks in the list.");
        showLine();
    }

    /** After deleting a task */
    public void showDelete(Task removed, int size) {
        showLine();
        System.out.println("Got it. I've removed this task:\n" + removed);
        System.out.println("Now you have " + size + " tasks in the list.");
        showLine();
    }

    /** For mark/unmark which already return a user-facing string */
    public void showTextBlock(String s) {
        showLine();
        System.out.println(s);
        showLine();
    }

    public void close() {
        sc.close();
    }
}
