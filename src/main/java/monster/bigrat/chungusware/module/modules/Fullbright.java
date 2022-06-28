package monster.bigrat.chungusware.module.modules;

import monster.bigrat.chungusware.module.Module;
import org.lwjgl.input.Keyboard;

public class Fullbright extends Module {
    public float initialGamma = 1;

    public Fullbright() {
        super("Fullbright", Keyboard.KEY_O, Type.RENDER);
    }

    @Override
    public void onEnable() {
        initialGamma = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 100;
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = initialGamma;
        initialGamma = 1;
    }
}
