package Meal;

public class Parser {

    public static Command parse(String line) {
        if (line == null) throw new BadInputException("Empty command.");
        line = line.trim();

        if (line.equals("bye")) return new ByeCommand();
        if (line.equals("list")) return new ListCommand();

        if (line.startsWith("mark ")) return new MarkCommand(line.substring(5).trim());
        if (line.startsWith("unmark ")) return new UnmarkCommand(line.substring(7).trim());
        if (line.startsWith("delete ")) return new DeleteCommand(line.substring(7).trim());
        if (line.startsWith("todo ")) return new TodoCommand(line.substring(5).trim());
        if (line.startsWith("deadline ")) return new DeadlineCommand(line.substring(9).trim());
        if (line.startsWith("event ")) return new EventCommand(line.substring(6).trim());
        if (line.startsWith("find ")) return new FindCommand(line.substring(5).trim());

        throw new BadInputException("Bad input :(");
    }

    //helpers
    static int parseNum(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new BadInputException("Please give a task number.");
        }
    }
}

interface Command {
    boolean execute(TaskList tasks, Ui ui, Storage storage);
}

/**
 * bye
 */
class ByeCommand implements Command {
    public boolean execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
        return true; // signal exit
    }
}

/**
 * list
 */
class ListCommand implements Command {
    public boolean execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showList(tasks);
        return false;
    }
}

/**
 * mark N
 */
class MarkCommand implements Command {
    private final int n; // 1-based

    MarkCommand(String arg) {
        this.n = Parser.parseNum(arg);
    }

    public boolean execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTextBlock(tasks.mark(n));
        storage.save(tasks.all());
        return false;
    }
}

/**
 * unmark N
 */
class UnmarkCommand implements Command {
    private final int n; // 1-based

    UnmarkCommand(String arg) {
        this.n = Parser.parseNum(arg);
    }

    public boolean execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTextBlock(tasks.unmark(n));
        storage.save(tasks.all());
        return false;
    }
}

/**
 * delete N
 */
class DeleteCommand implements Command {
    private final int n; // 1-based

    DeleteCommand(String arg) {
        this.n = Parser.parseNum(arg);
    }

    public boolean execute(TaskList tasks, Ui ui, Storage storage) {
        Task removed = tasks.delete(n);
        ui.showDelete(removed, tasks.size());
        storage.save(tasks.all());
        return false;
    }
}

/**
 * todo NAME
 */
class TodoCommand implements Command {
    private final String name;

    TodoCommand(String name) {
        if (name == null || name.isBlank())
            throw new BadInputException("Description can't be empty.");
        this.name = name.trim();
    }

    public boolean execute(TaskList tasks, Ui ui, Storage storage) {
        Task t = tasks.add(new ToDo(name));
        ui.showAdd(t, tasks.size());
        storage.save(tasks.all());
        return false;
    }
}

/**
 * deadline NAME /by YYYY-MM-DD
 */
class DeadlineCommand implements Command {
    private final String name, by;

    DeadlineCommand(String raw) {
        String[] parts = raw.split("/by", 2);
        if (parts.length < 2 || parts[0].isBlank() || parts[1].isBlank())
            throw new BadInputException("Usage: deadline <name> /by YYYY-MM-DD");
        this.name = parts[0].trim();
        this.by = parts[1].trim();
    }

    public boolean execute(TaskList tasks, Ui ui, Storage storage) {
        Task t = tasks.add(new Deadline(name, by));
        ui.showAdd(t, tasks.size());
        storage.save(tasks.all());
        return false;
    }
}

/**
 * event NAME /from YYYY-MM-DD /to YYYY-MM-DD
 */
class EventCommand implements Command {
    private final String name, from, to;

    EventCommand(String raw) {
        String[] p1 = raw.split("/from", 2);
        if (p1.length < 2) throw new BadInputException("Usage: event <name> /from YYYY-MM-DD /to YYYY-MM-DD");
        this.name = p1[0].trim();
        String[] p2 = p1[1].trim().split("/to", 2);
        if (p2.length < 2 || name.isBlank() || p2[0].isBlank() || p2[1].isBlank())
            throw new BadInputException("Usage: event <name> /from YYYY-MM-DD /to YYYY-MM-DD");
        this.from = p2[0].trim();
        this.to = p2[1].trim();
    }

    public boolean execute(TaskList tasks, Ui ui, Storage storage) {
        Task t = tasks.add(new Event(name, from, to));
        ui.showAdd(t, tasks.size());
        storage.save(tasks.all());
        return false;
    }
}

class FindCommand implements Command {
    private final String keyword;

    FindCommand(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new BadInputException("Usage: find <keyword>");
        }
        this.keyword = keyword.trim();
    }

    @Override
    public boolean execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showFindResults(tasks.find(keyword));
        return false; // no exit; no saving needed
    }
}