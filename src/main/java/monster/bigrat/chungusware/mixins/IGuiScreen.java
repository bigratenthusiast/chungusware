package monster.bigrat.chungusware.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(GuiScreen.class)
public interface IGuiScreen {
    @Accessor
    int getWidth();

    @Accessor
    int getHeight();

    @Accessor
    Minecraft getMc();

    @Accessor
    List<GuiButton> getButtonList();
}
