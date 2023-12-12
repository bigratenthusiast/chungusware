package monster.bigrat.chungusware.mixins;

import net.minecraft.client.particle.EntityFootStepFX;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityFootStepFX.class)
public interface IEntityFootStepFX {
    @Accessor
    int getFootstepAge();

    @Accessor
    int getFootstepMaxAge();
}
