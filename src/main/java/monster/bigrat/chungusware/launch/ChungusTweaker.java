package monster.bigrat.chungusware.launch;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.tools.obfuscation.mcp.ObfuscationServiceMCP;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChungusTweaker implements ITweaker {
    public static ChungusTweaker INSTANCE;
    private List<String> args;

    public ChungusTweaker() {
        INSTANCE = this;
    }

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        (this.args = new ArrayList<>()).addAll(args);
        addArg("gameDir", gameDir);
        addArg("assetsDir", assetsDir);
        addArg("version", profile);
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        if (((List<String>) Launch.blackboard.get("TweakClasses")).contains("net.minecraftforge.fml.common.launcher"))
            System.out.println("forge detected kekw");

        MixinBootstrap.init();
        String obfuscation = ObfuscationServiceMCP.NOTCH;
        MixinEnvironment environment = MixinEnvironment.getDefaultEnvironment();
        Mixins.addConfiguration("mixins.chungusware.json");

        environment.setObfuscationContext(obfuscation);
        environment.setSide(MixinEnvironment.Side.CLIENT);

    }

    @Override
    public String getLaunchTarget() {
        return "net.minecraft.client.main.Main";
    }

    @Override
    public String[] getLaunchArguments() {
        return args.toArray(new String[0]);
    }

    // chungus tweaker :widecatflushed:
    private void addArg(String label, Object value) {
        args.add("--" + label);
        args.add(value instanceof String ? (String) value : value instanceof File ? ((File) value).getAbsolutePath() : ".");
    }
}
