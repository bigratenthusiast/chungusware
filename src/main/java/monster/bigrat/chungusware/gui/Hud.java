package monster.bigrat.chungusware.gui;

import monster.bigrat.chungusware.Client;
import monster.bigrat.chungusware.event.events.RenderGuiEvent;
import monster.bigrat.chungusware.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.util.List;
import java.util.stream.Collectors;

public class Hud {
    public Minecraft mc = Minecraft.getMinecraft();
    public static final int offset = 4;
    public static final int actionBarDuration = 1000;

    public String actionBarText = "";
    public Long lastActionFlash = 0L;

    public void draw() {
        ScaledResolution sr = new ScaledResolution(mc);
        mc.fontRendererObj.drawString(Client.name, offset, offset, -1);
        List<Module> enabledModules = Client.modules.stream().filter(Module::isEnabled).collect(Collectors.toList());

        // TODO: Trans rights GUI Colouring Module
        // TODO: Enable/disable slide effect
        for (int i = 0; i < enabledModules.size(); i++) {
            Module m = enabledModules.get(i);
            mc.fontRendererObj.drawString(
                    m.name,
                    sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(m.name) - 4,
                    offset + 10 * i,
                    -1,
                    true);
        }
        // draw action bar
        if (lastActionFlash + actionBarDuration > System.currentTimeMillis()) {
            mc.fontRendererObj.drawString(
                    actionBarText,
                    (sr.getScaledWidth() / 2f) - (mc.fontRendererObj.getStringWidth(actionBarText) / 2f),
                    sr.getScaledHeight() - 60,
                    -1,
                    true);
        } else actionBarText = "";
        RenderGuiEvent event = new RenderGuiEvent();
        Client.EVENT_BUS.post(event);
    }

    public void drawActionBar(String string) {
        lastActionFlash = System.currentTimeMillis();
        actionBarText = string;
    }
}
