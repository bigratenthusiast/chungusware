package monster.bigrat.chungusware.mixins;

import monster.bigrat.chungusware.Client;
import monster.bigrat.chungusware.event.events.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Shadow
    private boolean fullscreen;
    @Shadow
    public GameSettings gameSettings;
    @Shadow
    public int displayWidth;
    @Shadow
    public int displayHeight;
    @Shadow
    private int tempDisplayWidth;
    @Shadow
    private int tempDisplayHeight;
    @Shadow
    public GuiScreen currentScreen;
    @Shadow
    public abstract void resize(int width, int height);
    @Shadow
    public abstract void updateFramebufferSize();
    @Shadow
    public abstract void updateDisplay();
    @Shadow
    public abstract void updateDisplayMode();
    @Shadow
    @Final
    private static Logger logger;

    @Inject(method = "startGame", at = @At("RETURN"))
    private void init(CallbackInfo ci) {
        Client.onStart();
    }

    @Inject(method = "dispatchKeypresses", at = @At("RETURN"))
    public void dispatchKeypresses(CallbackInfo ci) {
        int k = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();
        if (Keyboard.getEventKeyState())
            Client.keyPress(k);
    }

    @Inject(method = "runTick", at = @At("HEAD"))
    public void onTick(CallbackInfo ci) {
        TickEvent event = new TickEvent();
        Client.EVENT_BUS.post(event);
    }

    /**
     * @author bigratenthusiast
     */
    @Overwrite
    public void toggleFullscreen() {
        try
        {
            this.fullscreen = !this.fullscreen;
            this.gameSettings.fullScreen = this.fullscreen;

            System.setProperty("org.lwjgl.opengl.Window.undecorated", this.fullscreen ? "true":"false"); // borderless

            if (this.fullscreen)
            {
                this.updateDisplayMode();
                this.displayWidth = Display.getDisplayMode().getWidth();
                this.displayHeight = Display.getDisplayMode().getHeight();

            }
            else
            {
                Display.setDisplayMode(new DisplayMode(this.tempDisplayWidth, this.tempDisplayHeight));
                this.displayWidth = this.tempDisplayWidth;
                this.displayHeight = this.tempDisplayHeight;

            }
            if (this.displayWidth <= 0) this.displayWidth = 1;

            if (this.displayHeight <= 0) this.displayHeight = 1;


            if (this.currentScreen != null) this.resize(this.displayWidth, this.displayHeight);
            else this.updateFramebufferSize();

            Display.setResizable(!this.fullscreen);
            Display.setFullscreen(false);
            Display.setVSyncEnabled(this.gameSettings.enableVsync);
            this.updateDisplay();
        }
        catch (Exception exception)
        {
            logger.error("Couldn\'t toggle fullscreen", (Throwable)exception);
        }
    }

}
