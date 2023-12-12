package monster.bigrat.chungusware.mixins;


import net.minecraft.client.renderer.entity.RendererLivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RendererLivingEntity.class)
public abstract class MixinRendererLivingEntity extends MixinRender {
    // GUESS WHAT (class is just here for inheritance)
}
