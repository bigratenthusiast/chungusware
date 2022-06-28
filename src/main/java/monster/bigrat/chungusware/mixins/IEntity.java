package monster.bigrat.chungusware.mixins;

import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface IEntity {
    @Accessor
    DataWatcher getDataWatcher();
    @Accessor
    int getEntityId();
    @Accessor
    double getPosX();
    @Accessor
    double getPosY();
    @Accessor
    double getPosZ();
}
