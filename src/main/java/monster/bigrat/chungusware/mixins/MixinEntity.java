package monster.bigrat.chungusware.mixins;

import monster.bigrat.chungusware.Client;
import monster.bigrat.chungusware.event.events.MoveEvent;
import monster.bigrat.chungusware.modules.Module;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Entity.class)
public abstract class MixinEntity {

    @Inject(method ="moveEntity", at = @At("HEAD"))
    public void moveEntity(double x, double y, double z, CallbackInfo ci) {
        MoveEvent moveEvent = new MoveEvent(x, y, z);
        Client.EVENT_BUS.post(moveEvent);

        if (moveEvent.isCancelled())
            ci.cancel();
    }

    @Redirect(method = "moveEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isSneaking()Z"))
    public boolean isSneaking(Entity entity) {
        Module safewalk = Client.modules.stream().filter(x -> x.name.equalsIgnoreCase("safewalk")).toArray(Module[]::new)[0];
        return (((IEntity) this).getDataWatcher().getWatchableObjectByte(0) & 1 << 1) != 0 || safewalk.isEnabled();
        // get flag 1 : is sneaking
    }

}
