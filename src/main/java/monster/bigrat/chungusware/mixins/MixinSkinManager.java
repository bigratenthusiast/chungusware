package monster.bigrat.chungusware.mixins;

import com.mojang.authlib.GameProfile;
import monster.bigrat.chungusware.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.SkinManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.concurrent.ExecutorService;

@Mixin(SkinManager.class)
public class MixinSkinManager {
    @Shadow
    @Final
    private static ExecutorService THREAD_POOL;

    /**
     * @author bigratenthusiast
     */
    @Overwrite
    public void loadProfileTextures(final GameProfile gameProfile, final SkinManager.SkinAvailableCallback callback, final boolean secure) {
        THREAD_POOL.submit(Utils.getRunnable(Minecraft.getMinecraft().getSkinManager(), gameProfile, callback, secure));
    }
}
