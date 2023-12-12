package monster.bigrat.chungusware.mixins;

import monster.bigrat.chungusware.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ItemArmor.class)
public class MixinItemArmor {
    /**
     * @author bigratenthusiast
     */
    @Overwrite
    public ItemStack onItemRightClick(ItemStack newArmor, World world, EntityPlayer player) {
        InventoryPlayer inventory = player.inventory;
        int firstEmptySlot = inventory.getFirstEmptyStack();
        int armorSlot = EntityLiving.getArmorPosition(newArmor) - 1;

        if (player.getCurrentArmor(armorSlot) == null) {
            // Move armor to be worn to armor slot (vanilla behavior)
            player.setCurrentItemOrArmor(armorSlot, newArmor.copy());
            newArmor.stackSize = 0;
        } else if (Client.moduleEnabled("betterarmor")) {
            if (firstEmptySlot != -1 && firstEmptySlot < 9) {
                // PICKUP newArmor
                clickInInventory(player.inventory.currentItem < 9 ? 36 + player.inventory.currentItem : player.inventory.currentItem, 0, player);
                // PICKUP oldArmor
                clickInInventory(8 - armorSlot, 0, player);
                if (!Minecraft.getMinecraft().isSingleplayer()) {
                    // PICKUP newArmor
                    clickInInventory(player.inventory.currentItem < 9 ? 36 + player.inventory.currentItem : player.inventory.currentItem, 0, player);
                }
            } else Client.hud.drawActionBar("No empty slots in hotbar");
        }

        return newArmor;
    }

    private void clickInInventory(int slotId, int clickType, EntityPlayer player) {
        Minecraft.getMinecraft().playerController.windowClick(0, slotId, 0, clickType, player);
    }
}