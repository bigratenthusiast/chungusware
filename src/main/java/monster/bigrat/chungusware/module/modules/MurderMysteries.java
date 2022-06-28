package monster.bigrat.chungusware.module.modules;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import monster.bigrat.chungusware.Client;
import monster.bigrat.chungusware.event.events.TickEvent;
import monster.bigrat.chungusware.module.Module;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;
import java.util.List;

public class MurderMysteries extends Module {
    private final List<String> itemsList = Arrays.asList("sword", "hatchetDiamond", "enderChest", "apple", "deadbush", "stick", "shovelStone", "blazeRod", "carrotOnAStick", "shears", "carrotGolden", "quartz", "chickenCooked", "prismarineShard", "doublePlant", "hoeDiamond", "record", "sponge", "pickaxeGold", "book", "bone", "cookie", "hatchetWood", "carrots", "dyePowder.red", "boat", "speckledMelon", "shovelIron", "nameTag", "salmon", "shovelDiamond", "beefCooked", "pumpkinPie");
    private long lastUpdate = 0L;
    @EventHandler
    private final Listener<TickEvent> tickEventListener = new Listener<>(event -> {
        if (mc.thePlayer == null || mc.theWorld == null) return;
        if ((lastUpdate + 4000L <= System.currentTimeMillis()))
            mc.theWorld.playerEntities.forEach(otherPlayer -> {
                String item = (otherPlayer.getHeldItem() == null) ? "air" : otherPlayer.getHeldItem().getUnlocalizedName();
                itemsList.forEach(i -> {
                    if (item.contains(i)) {
                        lastUpdate = System.currentTimeMillis();
                        Client.displayChatMessage("§b" + otherPlayer.getName() + " §fpossibly murderer? §fBrandishes §c" + item + " §f!!");
                    }
                });
            });
    });

    public MurderMysteries() {
        super("MurderMysteries", Keyboard.KEY_NONE, Module.Type.MISC);
    }
}
