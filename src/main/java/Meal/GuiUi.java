package Meal;

public class GuiUi extends Ui {
    private final StringBuilder buf = new StringBuilder();
    private static final String SEP = "____________________________________________________________";

    private void line() { buf.append(SEP).append('\n'); }

    @Override
    public void showError(String msg) {
        line();
        buf.append(msg).append('\n');
        line();
    }

    @Override
    public void showLoadingError() {
        showError("Load failed.");
    }

    @Override
    public void showWelcome() {
        buf.append("Hello! I'm Meal.Meal\nWhat can I do for you?\n\n");
    }

    @Override
    public void showGoodbye() {
        line();
        buf.append("Bye. Hope to see you again soon!\n");
        line();
    }

    @Override
    public void showList(TaskList tasks) {
        line();
        buf.append("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            buf.append(i + 1).append(". ").append(tasks.get(i)).append('\n');
        }
        line();
    }

    @Override
    public void showAdd(Task t, int size) {
        line();
        buf.append("Got it. I've added this task:\n").append(t).append('\n');
        buf.append("Now you have ").append(size).append(" tasks in the list.\n");
        line();
    }

    @Override
    public void showDelete(Task removed, int size) {
        line();
        buf.append("Got it. I've removed this task:\n").append(removed).append('\n');
        buf.append("Now you have ").append(size).append(" tasks in the list.\n");
        line();
    }

    @Override
    public void showTextBlock(String... lines) {
        line();
        for (String s : lines) {
            buf.append(s).append('\n');
        }
        line();
    }

    @Override
    public void showFindResults(java.util.List<Task> matches) {
        line();
        buf.append("Here are the matching tasks in your list:\n");
        for (int i = 0; i < matches.size(); i++) {
            buf.append(i + 1).append(". ").append(matches.get(i)).append('\n');
        }
        line();
    }

    public String flush() {
        String out = buf.toString().trim();
        buf.setLength(0);
        return out;
    }

}
