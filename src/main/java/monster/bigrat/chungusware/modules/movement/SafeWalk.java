package monster.bigrat.chungusware.modules.movement;

import monster.bigrat.chungusware.modules.Module;
import org.lwjgl.input.Keyboard;

public class SafeWalk extends Module {
    /**
     * @see monster.bigrat.chungusware.mixins.MixinEntity
     */
    public SafeWalk() {
        super("Safewalk", Keyboard.KEY_N, Type.MOVEMENT);
    }
}
