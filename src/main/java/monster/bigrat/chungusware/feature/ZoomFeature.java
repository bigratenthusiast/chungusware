package monster.bigrat.chungusware.feature;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listenable;
import me.zero.alpine.listener.Listener;
import monster.bigrat.chungusware.event.events.TickEvent;
import monster.bigrat.chungusware.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

public class ZoomFeature implements Listenable {
    public KeyBinding keyBindZoom;
    public Minecraft mc = Minecraft.getMinecraft();

    public boolean prevState;
    public float initialFov;
    public float initialMouseSensitivity;

    public float zoomDepth;
    public float zoomMouseSensitivity;

    public ZoomFeature (float zoomDepth, float zoomMouseSensitivity) {
       this.keyBindZoom = new KeyBinding("key.zoom", Keyboard.KEY_C, "key.categories.misc");
       this.initialFov = Minecraft.getMinecraft().gameSettings.fovSetting;
       this.initialMouseSensitivity = Minecraft.getMinecraft().gameSettings.mouseSensitivity;
       this.zoomDepth = zoomDepth;
       this.zoomMouseSensitivity = zoomMouseSensitivity;
    }

    @EventHandler
    private final Listener<TickEvent> tickEventListener = new Listener<>(event -> {
        boolean keyPressed = keyBindZoom.isKeyDown();
        // TODO: implementation of cinematic camera (somehow) (PLEASE MAKE A PR IF YOU CAN FIGURE THIS OUT)
        if (keyPressed != prevState) {
            if (keyPressed) {
                initialFov = mc.gameSettings.fovSetting;
                initialMouseSensitivity = mc.gameSettings.mouseSensitivity;

                mc.gameSettings.fovSetting = initialFov / zoomDepth;
                mc.gameSettings.mouseSensitivity = zoomMouseSensitivity;
            }  else {
                mc.gameSettings.fovSetting = initialFov;
                mc.gameSettings.mouseSensitivity = initialMouseSensitivity;
            }
        }
        prevState = keyPressed;
    });
}
