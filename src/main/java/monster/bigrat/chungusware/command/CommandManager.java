package monster.bigrat.chungusware.command;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listenable;
import me.zero.alpine.listener.Listener;
import monster.bigrat.chungusware.Client;
import monster.bigrat.chungusware.event.events.ChatEvent;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CommandManager implements Listenable {
    public static CopyOnWriteArrayList<Command> commands = new CopyOnWriteArrayList<>();
    @EventHandler
    private final Listener<ChatEvent> chatEventListener = new Listener<>(event -> {
        if (event.message.startsWith(".")) {
            event.cancel();
            String command = event.message.substring(1).split(" ")[0];
            List<String> _args = new ArrayList<>(Arrays.asList(event.message.split(" ")));
            if (_args.size() > 0) _args.remove(0);
            String[] args = _args.toArray(new String[0]);
            for (Command c : commands) {
                if (c.name.equals(command) || c.aliases.contains(command))
                    c.run(args);
            }
            if (commands.stream().filter(c -> c.name.equals(command) || c.aliases.contains(command)).toArray().length == 0)
                Client.displayChatMessage("§c\"" + command + "\" is not a valid command or alias.");
        }
    });

    public CommandManager() {
        for (Class<? extends Command> m : findCommands()) {
            try {
                commands.add(m.getConstructor().newInstance());
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    public static ArrayList<Class<? extends Command>> findCommands() {
        Reflections reflections = new Reflections("monster.bigrat.chungusware.command.commands");
        return new ArrayList<>(reflections.getSubTypesOf(Command.class));
    }
}
