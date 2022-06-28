package monster.bigrat.chungusware.module.modules;

import monster.bigrat.chungusware.module.Module;
import org.lwjgl.input.Keyboard;

public class TransRights extends Module {
    /**
     * @see monster.bigrat.chungusware.gui.Hud
     */
    public TransRights() {
        super("TransRights", Keyboard.KEY_NONE, Type.MISC);
    }
}
