package monster.bigrat.chungusware.mixins;

import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityPlayerSP.class)
public interface IEntityPlayerSP {

    @Accessor
    boolean getServerSneakState();

    @Accessor
    void setServerSneakState(boolean serverSneakState);
}
