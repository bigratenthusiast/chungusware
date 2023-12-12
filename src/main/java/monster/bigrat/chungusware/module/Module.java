package monster.bigrat.chungusware.module;

import me.zero.alpine.listener.Listenable;
import monster.bigrat.chungusware.Client;
import monster.bigrat.chungusware.util.Settings;
import net.minecraft.client.Minecraft;

public class Module implements Listenable {
    protected final Minecraft mc = Minecraft.getMinecraft();
    public String name;
    public boolean toggled = false;
    public int keycode;
    public Type type;
    public Settings settings = new Settings();
    private int percentSlidIn = 0;

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
            percentSlidIn = 0;
            onDisable();
        }
    }

    public void onEnable() {}

    public void onDisable() {}

    public int getPercentSlidIn() {
        return percentSlidIn;
    }

    public void slideIn() {
        if (percentSlidIn < 100) percentSlidIn += 20;
    }

    public String toggleRespectingName() {
        return "§" + (toggled ? "a" : "c") + this.name + "§f";
    }

    public enum Type { MOVEMENT, RENDER, MISC }
}

