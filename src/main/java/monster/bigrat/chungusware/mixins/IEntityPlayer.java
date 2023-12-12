package monster.bigrat.chungusware.mixins;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.InventoryEnderChest;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityPlayer.class)
public interface IEntityPlayer {
    @Accessor
    InventoryPlayer getInventory();

    @Accessor
    InventoryEnderChest getTheInventoryEnderChest();
}
