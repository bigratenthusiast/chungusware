package monster.bigrat.chungusware.command;

import me.zero.alpine.listener.Listenable;
import net.minecraft.client.Minecraft;

import java.util.Arrays;
import java.util.List;

public class Command implements Listenable {
    protected final Minecraft mc = Minecraft.getMinecraft();
    public String name, description, syntax;
    public List<String> aliases;

    public Command(String name, String description, String syntax, String... aliases) {
        this.name = name;
        this.description = description;
        this.syntax = syntax;
        this.aliases = Arrays.asList(aliases);
    }

    public void run(String[] args) {

    }
}
