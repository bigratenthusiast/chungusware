package monster.bigrat.chungusware.mixins;

import monster.bigrat.chungusware.Client;
import monster.bigrat.chungusware.event.events.RenderEntityEvent;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Render.class)
public abstract class MixinRender<T extends Entity> {
    @Inject(method = "doRender", at = @At("HEAD"))
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        RenderEntityEvent event = new RenderEntityEvent(entity, x, y, z, entityYaw, partialTicks);
        Client.EVENT_BUS.post(event);
    }
}