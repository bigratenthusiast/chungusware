package monster.bigrat.chungusware.module.modules;

import com.google.gson.JsonSyntaxException;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import monster.bigrat.chungusware.event.events.TickEvent;
import monster.bigrat.chungusware.mixins.IEntityRenderer;
import monster.bigrat.chungusware.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class Shader extends Module {
    public static String[] shaders = new String[]{"none", "notch", "fxaa", "art", "bumpy", "blobs2", "pencil", "color_convolve", "deconverge", "flip", "invert", "ntsc", "outline", "phosphor", "scan_pincushion", "sobel", "bits", "desaturate", "green", "blur", "wobble", "blobs", "antialias", "creeper", "spider"};
    public static String shader = "shaders/post/color_convolve.json";
    @EventHandler
    private final Listener<TickEvent> tickEventListener = new Listener<>(event -> {
        ShaderGroup shaderGroup = mc.entityRenderer.getShaderGroup();
        if (shaderGroup == null || !shaderGroup.getShaderGroupName().contains(shader)) applyShader(shader);
    });

    public static void removeShader() {
        IEntityRenderer renderer = ((IEntityRenderer) Minecraft.getMinecraft().entityRenderer);
        if (renderer.getTheShaderGroup() != null) renderer.getTheShaderGroup().deleteShaderGroup();
    }

    public static void applyShader(String shader) {
        if (shader == null || shader.equals("none") || Minecraft.getMinecraft().theWorld == null) return;
        IEntityRenderer renderer = (IEntityRenderer) Minecraft.getMinecraft().entityRenderer;
        if (OpenGlHelper.shadersSupported) {
            removeShader();
            ResourceLocation res = new ResourceLocation(shader);
            try {
                renderer.setTheShaderGroup(new ShaderGroup(renderer.getMc().getTextureManager(), renderer.getResourceManager(), renderer.getMc().getFramebuffer(), res));
                renderer.getTheShaderGroup().createBindFramebuffers(renderer.getMc().displayWidth, renderer.getMc().displayHeight);
                renderer.setUseShader(true);
            } catch (IOException | JsonSyntaxException e) {
                renderer.getLogger().warn("Failed to load shader: " + res, e);
                renderer.setUseShader(false);
            }
        } else System.out.println("wtf shaders are not supported :(");
    }

    @Override
    public void onEnable() {
        applyShader(shader);
    }

    @Override
    public void onDisable() {
        removeShader();
    }

    public Shader() {
        super("Shader", Keyboard.KEY_NONE, Type.RENDER);
    }
}
