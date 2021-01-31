package monster.bigrat.chungusware.command;

import me.zero.alpine.listener.Listenable;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Command implements Listenable {
    public String name, description, syntax;
    public List<String> aliases = new ArrayList<>();

    protected final Minecraft mc = Minecraft.getMinecraft();

    public Command(String name, String description, String syntax, String... aliases) {
        this.name = name;
        this.description = description;
        this.syntax = syntax;
        this.aliases = Arrays.asList(aliases);
    }

    public void run(String[] args) {

    }
}
