package monster.bigrat.chungusware.module.modules;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import monster.bigrat.chungusware.event.events.RecievePacketEvent;
import monster.bigrat.chungusware.module.Module;
import monster.bigrat.chungusware.util.Utils;
import net.minecraft.network.play.server.S02PacketChat;
import org.lwjgl.input.Keyboard;

public class AutoEZ extends Module {

    // This only works in English! booooooooooo!

    @EventHandler
    private final Listener<RecievePacketEvent> packetEventListener = new Listener<>(event -> {
        if (!mc.isSingleplayer()) {
            if (event.packet instanceof S02PacketChat) {
                S02PacketChat packet = (S02PacketChat) event.packet;
                String unformattedMessage = packet.getChatComponent().getUnformattedText();
                if (packet.getChatComponent().getFormattedText().contains("§") && mc.getCurrentServerData().serverIP.contains("hypixel")) {
                    if (Utils.autoEZTriggers.stream().anyMatch(unformattedMessage::contains)) {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mc.thePlayer.sendChatMessage("/achat " + (settings.getBool("sportsmanship") ? "gg" : "e z"));
                    }
                }
            }
        }
    });

    public AutoEZ() {
        super("AutoEZ", Keyboard.KEY_NONE, Type.MISC);
        this.settings.add("sportsmanship", false);
    }
}
