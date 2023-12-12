package monster.bigrat.chungusware.module.modules;

import monster.bigrat.chungusware.module.Module;
import org.lwjgl.input.Keyboard;

public class PotionHud extends Module {
    /**
     * @see monster.bigrat.chungusware.gui.Hud
     */
    public PotionHud() {
        super("PotionHud", Keyboard.KEY_NONE, Type.RENDER);
    }
}
