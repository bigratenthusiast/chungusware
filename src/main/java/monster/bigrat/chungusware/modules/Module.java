package monster.bigrat.chungusware.modules;

import me.zero.alpine.listener.Listenable;
import monster.bigrat.chungusware.Client;
import monster.bigrat.chungusware.util.Settings;
import net.minecraft.client.Minecraft;

import java.util.HashMap;

public class Module implements Listenable {
    public String name;
    public boolean toggled = false;
    public int keycode;
    public Type type;
    public Settings settings = new Settings();


    protected final Minecraft mc = Minecraft.getMinecraft();

    public Module(String name, int keycode, Type t) {
        this.name = name;
        this.type = t;
        this.keycode = keycode;
    }

    public boolean isEnabled() {
        return toggled;
    }

    public int getKey() {
        return this.keycode;
    }

    public void toggle() {
        toggled = !toggled;
        // It is of note that the Module is subscribed/unsubscribed from
        // the Event Bus prior to the onEnable/Disable methods.
        // This is to prevent some modules (notably blink) from getting stuck in an infinite loop.
        if (toggled) {
            Client.EVENT_BUS.subscribe(this);
            onEnable();
        } else {
            Client.EVENT_BUS.unsubscribe(this);
            onDisable();
        }
    }

    public void onEnable() { }

    public void onDisable() { }


    public enum Type {
        MOVEMENT, RENDER, MISC
    }
}

