package monster.bigrat.chungusware.module.modules;

import monster.bigrat.chungusware.module.Module;
import org.lwjgl.input.Keyboard;

public class SafeWalk extends Module {
    /**
     * @see monster.bigrat.chungusware.mixins.MixinEntity
     */
    public SafeWalk() {
        super("Safewalk", Keyboard.KEY_N, Type.MOVEMENT);
    }
}
