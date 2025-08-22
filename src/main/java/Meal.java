import java.util.Scanner;
import java.util.ArrayList;

public class Meal {
    public static void main(String[] args) {
        String SEP = "____________________________________________________________";

        System.out.println("Hello! I'm Meal\n"
                + "What can I do for you?\n");

        Scanner sc = new Scanner(System.in);
        ArrayList<Task> items = new ArrayList<>();

        while (true) {
            String line = sc.nextLine().trim();
            if (line.equals("bye")) {
                break;
            }
            if (line.equals("list")) {
                for (int i = 0; i < items.size(); i++) {
                    System.out.println((i + 1) + "." + items.get(i).toString());
                }
                System.out.println(SEP);
                continue;
            }

            if (line.startsWith("mark ")) {
                String numStr = line.substring(5).trim();
                int num = Integer.parseInt(numStr) - 1;
                System.out.println(SEP);
                System.out.println(items.get(num).mark());
                System.out.println(SEP);
                continue;
            }

            if (line.startsWith("unmark ")) {
                String numStr = line.substring(7).trim();
                int num = Integer.parseInt(numStr) - 1;
                System.out.println(SEP);
                System.out.println(items.get(num).unmark());
                System.out.println(SEP);
                continue;
            }

            items.add(new Task(line));
            System.out.println(SEP);
            System.out.println("added: " + line);
            System.out.println(SEP);
        }

        System.out.println(SEP);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(SEP);
    }

}
