package monster.bigrat.chungusware.modules.misc;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import monster.bigrat.chungusware.Client;
import monster.bigrat.chungusware.event.events.TickEvent;
import monster.bigrat.chungusware.modules.Module;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class MurderMysteries extends Module {
    public MurderMysteries() {
        super("MurderMysteries", Keyboard.KEY_NONE, Module.Type.MISC);
        itemsList.add("blaze_rod");
        itemsList.add("bone");
        itemsList.add("carrot");
        itemsList.add("cobweb");
        itemsList.add("cod");
        itemsList.add("feather");
        itemsList.add("redstone");
        itemsList.add("golden");
        itemsList.add("prismarine");
        itemsList.add("salmon");
        itemsList.add("sponge");
        itemsList.add("stick");
        itemsList.add("tropical_fish");
        itemsList.add("pickaxe");
        itemsList.add("sword");
    }

    private ArrayList<String> itemsList = new ArrayList<>();
    private long lastUpdate = 0L;

    @EventHandler
    private Listener<TickEvent> tickEventListener = new Listener<>(event -> {
        if (mc.thePlayer == null || mc.theWorld == null) return;
        if ((lastUpdate + 4000L <= System.currentTimeMillis()))
            mc.theWorld.playerEntities.forEach(otherPlayer -> {
                String chungusCord = (otherPlayer.getHeldItem() == null) ? "air" : otherPlayer.getHeldItem().getUnlocalizedName().replace("item.", "");
                itemsList.forEach(item -> {
                    if (chungusCord.contains(item)) {
                        lastUpdate = System.currentTimeMillis();
                        Client.displayChatMessage("[MurderMysteries] §b" + otherPlayer.getName() + " §fis the §cMurderer §fwields §6" + chungusCord + " §f!");
                    }
                });
            });
    });
}
