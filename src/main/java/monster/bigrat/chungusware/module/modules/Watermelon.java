package monster.bigrat.chungusware.module.modules;

import monster.bigrat.chungusware.module.Module;
import org.lwjgl.input.Keyboard;

public class Watermelon extends Module {
    /**
     * @see monster.bigrat.chungusware.gui.Hud
     */
    public Watermelon() {
        super("Watermelon", Keyboard.KEY_NONE, Type.MISC);
    }
}
