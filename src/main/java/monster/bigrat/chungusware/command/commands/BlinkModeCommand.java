package monster.bigrat.chungusware.command.commands;

import monster.bigrat.chungusware.Client;
import monster.bigrat.chungusware.command.Command;
import monster.bigrat.chungusware.modules.Module;

public class BlinkModeCommand extends Command {
    public BlinkModeCommand() {
        super("blinkmode", "Switch blink mode", "");
    }

    @Override
    public void run(String[] args) {
        Module blink = Client.modules.stream().filter(x -> x.name.equalsIgnoreCase("blink")).toArray(Module[]::new)[0];
        blink.settings.set("isCancelable", !blink.settings.getBool("isCancelable"));
        Client.displayChatMessage("Toggled §eBlink.isCancelable");
    }
}
