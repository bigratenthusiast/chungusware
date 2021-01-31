package monster.bigrat.chungusware;


import com.google.gson.JsonObject;
import me.zero.alpine.bus.EventBus;
import me.zero.alpine.bus.EventManager;
import monster.bigrat.chungusware.command.CommandManager;
import monster.bigrat.chungusware.gui.Hud;
import monster.bigrat.chungusware.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IChatComponent;
import org.lwjgl.opengl.Display;
import org.reflections.Reflections;


import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/****
 *  TODO LIST:
 *   - Armor Hud
 *   - AutoGG (avec support for french :o )
 *   - HYPIXEL TRUESIGHT
 *   - fix non cancelation of chat
 *   - @see MixinRendererLivingEntity:42
 */

public class Client {
    public static String name = "Chungusware", version = "0.0.1", applicationId = "798290488330682370";
    public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<>();
    public static final EventBus EVENT_BUS = new EventManager();
    public static Hud hud = new Hud();

    public static void onStart() {
        System.err.println(name + " - " + version);
        Display.setTitle(name);

        for (Class<? extends Module> m : findModules()) {
            try {
                modules.add(m.getConstructor().newInstance());
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        EVENT_BUS.subscribe(new CommandManager());

    }

    public static void keyPress(int key) {
        if (!Minecraft.getMinecraft().ingameGUI.getChatGUI().getChatOpen()) for (Module m : modules) {
            if (m.getKey() == key) {
                m.toggle();
            }
        }
    }

    public static void displayChatMessage(final String message) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", message);
        Minecraft.getMinecraft().thePlayer.addChatMessage(IChatComponent.Serializer.jsonToComponent(jsonObject.toString()));
    }

    public static ArrayList<Class<? extends Module>> findModules() {
        Reflections reflections = new Reflections("monster.bigrat.chungusware.modules");
        ArrayList<Class<? extends Module>> list = new ArrayList<>();
        list.addAll(reflections.getSubTypesOf(Module.class));
        return list;
    }
}
