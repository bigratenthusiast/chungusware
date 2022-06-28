package monster.bigrat.chungusware.mixins;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerControllerMP.class)
public class MixinPlayerControllerMP {
//    @Inject(method = "windowClick", at = @At("HEAD"))
//    public void windowClick(int windowId, int slotId, int mouseButton, int clickType, EntityPlayer player, CallbackInfoReturnable<ItemStack> idc) {
//        System.out.println("clickInInventory(slotId:" + slotId + ", clickType:" + clickType + ", player:self)");
//    }
}
