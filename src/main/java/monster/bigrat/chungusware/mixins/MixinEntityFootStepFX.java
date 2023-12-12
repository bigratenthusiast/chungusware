package monster.bigrat.chungusware.mixins;

import monster.bigrat.chungusware.Client;
import monster.bigrat.chungusware.event.events.RenderFootstepEvent;
import monster.bigrat.chungusware.event.events.TickEvent;
import net.minecraft.client.particle.EntityFootStepFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityFootStepFX.class)
public class MixinEntityFootStepFX {
    @Inject(method ="renderParticle", at = @At("HEAD"))
    public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ, CallbackInfo ci)
    {
        IEntity e = ((IEntity) this);
        IEntityFootStepFX f = ((IEntityFootStepFX) this);
        RenderFootstepEvent event = new RenderFootstepEvent(e.getEntityId(), partialTicks, e.getPosX(), e.getPosY(), e.getPosZ(), f.getFootstepAge(), f.getFootstepMaxAge());
        Client.EVENT_BUS.post(event);
    }
}
