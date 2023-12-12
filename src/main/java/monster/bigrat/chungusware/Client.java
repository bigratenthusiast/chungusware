package monster.bigrat.chungusware;


import com.google.gson.JsonObject;
import me.zero.alpine.bus.EventBus;
import me.zero.alpine.bus.EventManager;
import monster.bigrat.chungusware.command.CommandManager;
import monster.bigrat.chungusware.feature.ZoomFeature;
import monster.bigrat.chungusware.gui.Hud;
import monster.bigrat.chungusware.module.Module;
import monster.bigrat.chungusware.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IChatComponent;
import org.lwjgl.opengl.Display;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Client {
    public static final EventBus EVENT_BUS = new EventManager();
    public static String name = "Chungusware", applicationId = "798290488330682370";
    public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<>();
    public static Hud hud = new Hud();

    public static void onStart() {
        System.err.println(name);
        Display.setTitle("Minecraft (" + name + ")");

        for (Class<? extends Module> m : findModules()) {
            try {
                modules.add(m.getConstructor().newInstance());
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                System.err.println(e);
            }
        }
        modules.forEach(m -> {
            if (Utils.enableOnStart.stream().anyMatch(m.name.toLowerCase()::contains)) m.toggle();
        });
        EVENT_BUS.subscribe(new CommandManager());
        EVENT_BUS.subscribe(new ZoomFeature(5, 0.1f));
    }

    public static boolean moduleEnabled(String moduleName) {
        return Client.modules.stream().filter(m -> m.name.equalsIgnoreCase(moduleName)).toArray(Module[]::new)[0].isEnabled();
    }

    public static void keyPress(int key) {
        if (!(Minecraft.getMinecraft().ingameGUI.getChatGUI().getChatOpen() || Minecraft.getMinecraft().currentScreen != null)) {

            for (Module m : modules) {
                if (m.getKey() == key) m.toggle();
            }
        }
    }

    public static void displayChatMessage(final String message) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", message);
        Minecraft.getMinecraft().thePlayer.addChatMessage(IChatComponent.Serializer.jsonToComponent(jsonObject.toString()));
    }

    public static ArrayList<Class<? extends Module>> findModules() {
        Reflections reflections = new Reflections("monster.bigrat.chungusware.module.modules");
        return new ArrayList<>(reflections.getSubTypesOf(Module.class));
    }
}
