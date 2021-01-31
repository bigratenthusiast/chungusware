package monster.bigrat.chungusware.modules.misc;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import monster.bigrat.chungusware.Client;
import monster.bigrat.chungusware.event.events.TickEvent;
import monster.bigrat.chungusware.modules.Module;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import org.lwjgl.input.Keyboard;

public class DiscordStatus extends Module {
    private long updateLimit = 15000L; // 15 seconds, should be good. (prevents api rate limiting)
    private long lastUpdate = 0L;

    public DiscordStatus() {
        super("DiscordRPC", Keyboard.KEY_NONE, Type.MISC);
    }

    @EventHandler
    private Listener<TickEvent> tickEventListener = new Listener<>(event -> {
        if ((lastUpdate + updateLimit <= System.currentTimeMillis())) {
            // This is the bottom half of the RPC with server ip and funny message
            DiscordRichPresence.Builder presence = new DiscordRichPresence.Builder("swag")
                    .setDetails("Using Chungusware utility mod.")
                    .setBigImage("logo", "Chungusware")
                    .setSmallImage("logo_swag", "(c) 2021 BigratEnthusiast");

            // Update the RPC
            DiscordRPC.discordUpdatePresence(presence.build());
            lastUpdate = System.currentTimeMillis();
        }
    });

    @Override
    public void onEnable() {
        initDiscord();
    }

    @Override
    public void onDisable() {
        DiscordRPC.discordShutdown();
    }

    public void initDiscord() {
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().build();
        DiscordRPC.discordInitialize(Client.applicationId, handlers, false);
        DiscordRPC.discordRegister(Client.applicationId, "");
        lastUpdate = System.currentTimeMillis() - updateLimit; // Make it update instantly
    }

}
