import java.util.Scanner;
import java.util.ArrayList;

public class Meal {
    public static void main(String[] args) {
        String SEP = "____________________________________________________________";

        System.out.println("Hello! I'm Meal\n"
                + "What can I do for you?\n");

        Scanner sc = new Scanner(System.in);
        ArrayList<String> items = new ArrayList<>();

        while (true) {
            String line = sc.nextLine().trim();
            if (line.equals("bye")) {
                break;
            }
            if (line.equals("list")) {
                for (int i = 0; i < items.size(); i++) {
                    System.out.println((i + 1) + "." + items.get(i));
                }
                System.out.println(SEP);
                continue;
            }
            items.add(line);
            System.out.println(SEP);
            System.out.println("added: " + line);
            System.out.println(SEP);
        }

        System.out.println(SEP);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(SEP);
    }

}
