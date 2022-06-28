package monster.bigrat.chungusware.module.modules;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import monster.bigrat.chungusware.Client;
import monster.bigrat.chungusware.event.events.TickEvent;
import monster.bigrat.chungusware.module.Module;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import org.lwjgl.input.Keyboard;

public class DiscordStatus extends Module {
    private final long updateLimit = 15000L; // 15 seconds, should be good. (prevents api rate limiting)
    private long lastUpdate = 0L;
    @EventHandler
    private final Listener<TickEvent> tickEventListener = new Listener<>(event -> {
        if ((lastUpdate + updateLimit <= System.currentTimeMillis())) {
            String gameStatus = null;
            if (mc == null) gameStatus = "Offline";
            else if (mc.isSingleplayer()) gameStatus = "SinglePlayer";
            else if (mc.getCurrentServerData() == null) gameStatus = "Offline";
            else gameStatus = mc.getCurrentServerData().serverIP;

            // This is the bottom half of the RPC with server ip and funny message
            DiscordRichPresence.Builder presence = new DiscordRichPresence.Builder("Chungusware")
                    .setDetails("bedwar skill enhancer")
                    .setBigImage("logo", gameStatus)
                    .setSmallImage("logo_swag", "https://github.com/bigratenthusiast/chungusware");

            // Update the RPC
            DiscordRPC.discordUpdatePresence(presence.build());
            lastUpdate = System.currentTimeMillis();
        }
    });

    public DiscordStatus() {
        super("DiscordRPC", Keyboard.KEY_NONE, Type.MISC);
    }

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
