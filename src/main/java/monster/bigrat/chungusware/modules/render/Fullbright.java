package monster.bigrat.chungusware.modules.render;

import monster.bigrat.chungusware.modules.Module;
import org.lwjgl.input.Keyboard;

public class Fullbright extends Module {

    public Fullbright() { super("Fullbright", Keyboard.KEY_O, Type.RENDER); }

    @Override
    public void onEnable() {
        mc.gameSettings.gammaSetting = 100;
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = 1;
    }
}
