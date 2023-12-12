package monster.bigrat.chungusware.module.modules;


import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import monster.bigrat.chungusware.event.events.ClickBlockEvent;
import monster.bigrat.chungusware.module.Module;
import net.minecraft.block.Block;
import org.lwjgl.input.Keyboard;

public class AutoTool extends Module {
    @EventHandler
    private final Listener<ClickBlockEvent> clickBlockEventListener = new Listener<>(event -> {
        Block target = mc.theWorld.getBlockState(event.location).getBlock();

        float max = 0f;
        int index = -1;

        for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
            if (mc.thePlayer.inventory.mainInventory[hotbarSlot] != null) {
                float current = mc.thePlayer.inventory.mainInventory[hotbarSlot].getStrVsBlock(target);
                if (current > max) {
                    max = current;
                    index = hotbarSlot;
                }
            }
        }

        if (index > -1) {
            mc.thePlayer.inventory.currentItem = index;
        }
    });

    public AutoTool() {
        super("AutoTool", Keyboard.KEY_NONE, Type.MISC);
    }
}
