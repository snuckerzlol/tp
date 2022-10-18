package recipeditor.parser;

import recipeditor.command.AddCommand;
import recipeditor.command.Command;
import recipeditor.command.DeleteCommand;
import recipeditor.exception.ExcessArgumentException;
import recipeditor.command.ListCommand;
import recipeditor.command.ExitCommand;
import recipeditor.command.InvalidCommand;
import recipeditor.command.ViewCommand;

import recipeditor.recipe.Recipe;
import recipeditor.ui.AddMode;
import recipeditor.ui.AddModeText;
import recipeditor.ui.Ui;

public class Parser {

    public static Command parseCommand(String input) {
        String[] parsed = input.split(" ");
        String commandWord = parsed[0].toLowerCase();

        switch (commandWord) {
        case AddCommand.COMMAND_TYPE:
            return parseAddCommand();
        case ListCommand.COMMAND_TYPE:
            return new ListCommand();
        case ExitCommand.COMMAND_TYPE:
            return new ExitCommand();
        case DeleteCommand.COMMAND_TYPE:
        case ViewCommand.COMMAND_TYPE:
            return parseListAlterCommand(parsed, commandWord);
        default:
            return new InvalidCommand();
        }

    }

    private static Command parseAddCommand() {

        AddModeText add = new AddModeText();
        add.enterAddModeText();

        //        AddMode add = new AddMode(); // Switch to Add Mode in here
        //        add.enterAddMode();
        //        add.exitAddMode();
        //        Ui.showMessage("Is the recipe valid? " + add.isValid);
        return new AddCommand(true, new Recipe()); // Dummy
    }

    private static Command parseListAlterCommand(String[] parsed, String commandWord) {
        if (parsed.length == 2) {
            try {
                int index = Integer.parseInt(parsed[1]) - 1; // to account for 0-based indexing in recipelist
                if (commandWord.equals(ViewCommand.COMMAND_TYPE)) {
                    return new ViewCommand(index);
                }
                return new DeleteCommand(index);
            } catch (Exception e) {
                System.out.format("Exception: Wrong command Format%n"
                        + "Try the command in correct format: view/delete <index of task>%n");
                return new InvalidCommand();
            }
        }
        return new InvalidCommand();
    }

}
