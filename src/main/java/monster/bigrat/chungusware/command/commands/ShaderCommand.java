package monster.bigrat.chungusware.command.commands;

import monster.bigrat.chungusware.Client;
import monster.bigrat.chungusware.command.Command;
import monster.bigrat.chungusware.module.modules.Shader;

import java.util.Arrays;

public class ShaderCommand extends Command {
    public ShaderCommand() {
        super("shader", "Set the current shader", "[shader]");
    }

    @Override
    public void run(String[] args) {
        if (args.length == 0) {
            String currentShader = Shader.shader.replace("shaders/post/", "").replace(".json", ",");
            Client.displayChatMessage("Shaders: " + (String.join(", ", Shader.shaders) + ",").replace(currentShader, "§a" + currentShader + "§f").replaceAll(",[^,]*$", ""));
        } else {
            if (Arrays.stream(Shader.shaders).anyMatch(args[0].toLowerCase()::contains)) {
                if (args[0].equals("none")) {
                    Shader.shader = "none";
                    Shader.removeShader();
                    Client.displayChatMessage("Removed shader");
                } else {
                    Shader.shader = "shaders/post/" + args[0] + ".json";
                    Client.displayChatMessage("Set shader to: §a" + Shader.shader);
                }
            } else Client.displayChatMessage("§c" + args[0] + " is not a valid shader");
        }
    }
}
