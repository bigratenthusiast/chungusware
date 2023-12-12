package monster.bigrat.chungusware.command.commands;

import monster.bigrat.chungusware.Client;
import monster.bigrat.chungusware.command.Command;
import monster.bigrat.chungusware.command.CommandManager;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help", "Get help for a command", "[command?]");
    }

    @Override
    public void run(String[] args) {
        if (args.length > 0) {
            Command[] commands = CommandManager.commands.stream().filter(x -> x.name.equalsIgnoreCase(args[0]) || x.aliases.contains(args[0])).toArray(Command[]::new);
            switch (commands.length) {
                case 0:
                    Client.displayChatMessage("§c\"" + args[0] + "\" is not a valid command or alias.");
                    break;
                case 1:
                    Client.displayChatMessage("Command:§b " + commands[0].name);
                    Client.displayChatMessage("Description:§b " + commands[0].description);
                    Client.displayChatMessage("Aliases:§b " + String.join(", ", commands[0].aliases.toArray(new String[0])));
                    Client.displayChatMessage("Use:§b " + commands[0].syntax);
                    break;
                default:
                    Client.displayChatMessage("§cThere is more than one command with that name or alias. This build of the Client is broken.");
            }
        } else {
            Client.displayChatMessage("Commands:§b " + String.join("§b, ", CommandManager.commands.stream().map(c -> c.name).toArray(String[]::new)));
        }
    }
}
