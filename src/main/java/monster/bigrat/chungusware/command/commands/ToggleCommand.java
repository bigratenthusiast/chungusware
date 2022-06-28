package monster.bigrat.chungusware.command.commands;

import monster.bigrat.chungusware.Client;
import monster.bigrat.chungusware.command.Command;
import monster.bigrat.chungusware.module.Module;

public class ToggleCommand extends Command {
    public ToggleCommand() {
        super("toggle", "Toggle a module", "[module]", "t");
    }

    @Override
    public void run(String[] args) {
        if (args.length > 0) {
            Module[] modules = Client.modules.stream().filter(m -> m.name.equalsIgnoreCase(args[0])).toArray(Module[]::new);
            switch (modules.length) {
                case 0:
                    Client.displayChatMessage("§c\"" + args[0] + "\" is not a valid module.");
                    break;
                case 1:
                    modules[0].toggle();
                    Client.displayChatMessage("Toggled §e" + modules[0].name);
                    break;
                default:
                    Client.displayChatMessage("§cThere is more than one module with that name. This build of the Client is broken.");
            }
        } else {
            Client.displayChatMessage("Modules: " + String.join(", ", Client.modules.stream().map(Module::toggleRespectingName).toArray(String[]::new)));
        }
    }
}
