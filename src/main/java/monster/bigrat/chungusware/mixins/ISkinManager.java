package monster.bigrat.chungusware.mixins;

import com.mojang.authlib.minecraft.MinecraftSessionService;
import net.minecraft.client.resources.SkinManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SkinManager.class)
public interface ISkinManager {
    @Accessor
    MinecraftSessionService getSessionService();
}
