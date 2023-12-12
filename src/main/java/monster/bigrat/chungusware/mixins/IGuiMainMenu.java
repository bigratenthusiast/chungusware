package monster.bigrat.chungusware.mixins;

import net.minecraft.client.gui.GuiMainMenu;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GuiMainMenu.class)
public interface IGuiMainMenu extends IGuiScreen {
    // just for inheritance
}
