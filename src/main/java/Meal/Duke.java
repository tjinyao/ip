package Meal;

import javafx.application.Platform;

public class Duke {
    private final Storage storage = new Storage("data/duke.txt");
    private final TaskList tasks;
    public Duke() {
        tasks = new TaskList(storage.load());
    }

    public String getResponse(String input) {
        GuiUi ui = new GuiUi();
        try {
            Command c = Parser.parse(input);
            boolean shouldExit = c.execute(tasks, ui, storage);
            String out = ui.flush();
            if (shouldExit) {
                Platform.exit();
            }
            return out.isBlank() ? "(no output)" : out;
        } catch (Exception e) {
            ui.showError(e.getMessage());
            return ui.flush();
        }
    }
}
