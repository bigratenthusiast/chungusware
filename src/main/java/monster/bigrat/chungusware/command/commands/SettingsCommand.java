package monster.bigrat.chungusware.command.commands;

import monster.bigrat.chungusware.Client;
import monster.bigrat.chungusware.command.Command;
import monster.bigrat.chungusware.module.Module;
import monster.bigrat.chungusware.util.Settings;

public class SettingsCommand extends Command {
    public SettingsCommand() {
        super("setting", "Set a setting in a module", "[module!] [setting] [value?]", "set", "s");
    }

    @Override
    public void run(String[] args) {
        if (args.length > 0) {
            Module[] modules = Client.modules.stream().filter(m -> m.name.equalsIgnoreCase(args[0])).toArray(Module[]::new);
            switch (modules.length) {
                case 0:
                    Client.displayChatMessage("§cPlease provide a valid module.");
                    break;
                case 1:
                    switch (args.length) {
                        case 1:
                            Client.displayChatMessage("Settings:§b " + modules[0].settings.toString());
                            break;
                        case 2:
                            if (modules[0].settings.get(args[1]) != null) {
                                Client.displayChatMessage("Setting:§b " + args[1] + Settings.getDatatypeSymbol(modules[0].settings.get(args[1])) + " : " + modules[0].settings.get(args[1]));
                            } else
                                Client.displayChatMessage("§c\"" + args[1] + "\" is not a valid setting in " + modules[0].name);
                            break;
                        default:
                            if (modules[0].settings.get(args[1]) != null) {
                                Object setting = modules[0].settings.get(args[1]);
                                try {
                                    if (setting instanceof Integer) {
                                        modules[0].settings.set(args[1], Integer.valueOf(args[2]));
                                    } else if (setting instanceof String) {
                                        modules[0].settings.set(args[1], args[2]);
                                    } else if (setting instanceof Float) {
                                        modules[0].settings.set(args[1], Float.valueOf(args[2]));
                                    } else if (setting instanceof Boolean) {
                                        String bV = args[2].toLowerCase();
                                        if (bV.equals("yes") || bV.equals("+") || bV.equals("true")) {
                                            modules[0].settings.set(args[1], true);
                                        } else if (bV.equals("no") || bV.equals("-") || bV.equals("false")) {
                                            modules[0].settings.set(args[1], false);
                                        } else modules[0].settings.set(args[1], Integer.parseInt(args[2]) != 0);
                                    } else modules[0].settings.set(args[1], args[2]);
                                    Client.displayChatMessage("§cSet " + args[1] + " to " + args[2] + " in " + modules[0].name);
                                } catch (Exception e) {
                                    Client.displayChatMessage("§cThe value provided is not of the right datatype.");
                                }

                            } else
                                Client.displayChatMessage("§c\"" + args[1] + "\" is not a valid setting in " + modules[0].name);
                    }
                    break;
                default:
                    Client.displayChatMessage("§cThere is more than one module with that name. This build of the Client is broken.");
            }
        } else {
            Client.displayChatMessage("§cPlease provide a module.");
        }
    }
}
