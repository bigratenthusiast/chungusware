package monster.bigrat.chungusware.mixins;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(GuiMainMenu.class)
public class MixinGuiMainMenu {
    /**
     * @author bigratenthusiast
     */
    @Overwrite
    private void addSingleplayerMultiplayerButtons(int p_addSingleplayerMultiplayerButtons_1_, int p_addSingleplayerMultiplayerButtons_2_) {
        ((IGuiMainMenu) this).getButtonList().add(new GuiButton(1, ((IGuiMainMenu) this).getWidth() / 2 - 100, p_addSingleplayerMultiplayerButtons_1_, I18n.format("menu.singleplayer")));
        ((IGuiMainMenu) this).getButtonList().add(new GuiButton(2, ((IGuiMainMenu) this).getWidth() / 2 - 100, p_addSingleplayerMultiplayerButtons_1_ + p_addSingleplayerMultiplayerButtons_2_, I18n.format("menu.multiplayer")));
    }
}
