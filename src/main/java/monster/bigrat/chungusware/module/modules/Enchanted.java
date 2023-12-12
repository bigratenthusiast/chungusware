package monster.bigrat.chungusware.module.modules;


import monster.bigrat.chungusware.gui.Hud;
import monster.bigrat.chungusware.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.input.Keyboard;

public class Enchanted extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final FontRenderer normal = mc.fontRendererObj;
    private final FontRenderer galactic = mc.standardGalacticFontRenderer;

    @Override
    public void onEnable() {
        Hud.fontRenderer = galactic;
        mc.fontRendererObj = galactic;
        mc.standardGalacticFontRenderer = normal;
    }

    @Override
    public void onDisable() {
        Hud.fontRenderer = normal;
        mc.fontRendererObj = normal;
        mc.standardGalacticFontRenderer = galactic;
    }

    public Enchanted() {
        super("Enchanted", Keyboard.KEY_NONE, Type.RENDER);
    }
}
