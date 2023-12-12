package monster.bigrat.chungusware.module.modules;

import monster.bigrat.chungusware.module.Module;
import org.lwjgl.input.Keyboard;

public class BetterArmor extends Module {
    /**
     * @see monster.bigrat.chungusware.gui.Hud
     * @see monster.bigrat.chungusware.mixins.MixinItemArmor
     */
    public BetterArmor() {
        super("BetterArmor", Keyboard.KEY_NONE, Type.RENDER);
    }
}
