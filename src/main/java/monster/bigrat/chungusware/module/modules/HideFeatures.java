package monster.bigrat.chungusware.module.modules;

import monster.bigrat.chungusware.module.Module;
import org.lwjgl.input.Keyboard;

public class HideFeatures extends Module {
    /**
     * @see monster.bigrat.chungusware.gui.Hud
     */
    public HideFeatures() {
        super("HideFeatures", Keyboard.KEY_Y, Type.RENDER);
    }
}
