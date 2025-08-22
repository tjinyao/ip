import java.util.Scanner;

public class Meal {
    public static void main(String[] args) {
        String SEP = "____________________________________________________________";

        System.out.println("Hello! I'm Meal\n"
                + "What can I do for you?\n");

        Scanner sc = new Scanner(System.in);

        while (true) {
            String line = sc.nextLine().trim();
            if (line.equals("bye")) {
                break;
            }
            System.out.println(SEP);
            System.out.println(line);
            System.out.println(SEP);
        }

        System.out.println(SEP);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(SEP);
    }

}
