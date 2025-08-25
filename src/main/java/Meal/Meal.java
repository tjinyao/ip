package Meal;

import java.util.Scanner;

public class Meal {
    private static final String SEP = "____________________________________________________________";
    private final Scanner sc = new Scanner(System.in);
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Meal(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        }
        catch (MealException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        boolean shouldExit = false;

        while (!shouldExit) {
            try {
                String line = ui.readCommand();
                Command cmd = Parser.parse(line);
                shouldExit = cmd.execute(tasks, ui, storage);
            } catch (Exception e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Meal("data/duke.txt").run();
    }

}
