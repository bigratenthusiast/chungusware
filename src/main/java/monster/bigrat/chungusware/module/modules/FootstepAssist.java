package monster.bigrat.chungusware.module.modules;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import monster.bigrat.chungusware.event.events.RenderFootstepEvent;
import monster.bigrat.chungusware.module.Module;
import net.minecraft.util.EnumParticleTypes;
import org.lwjgl.input.Keyboard;

public class FootstepAssist extends Module {

    @EventHandler
    private final Listener<RenderFootstepEvent> footstepEventListener = new Listener<>(event -> {
        mc.theWorld.spawnParticle(
                EnumParticleTypes.SPELL_WITCH,
                event.posX,
                event.posY,
                event.posZ,
                1,
                0,
                1,
                10
        );
        mc.theWorld.spawnParticle(
                EnumParticleTypes.ENCHANTMENT_TABLE,
                event.posX,
                event.posY,
                event.posZ,
                0,
                2,
                0,
                10
        );
    });

    public FootstepAssist() {
        super("FootstepAssist", Keyboard.KEY_NONE, Type.RENDER);
    }
}
