package monster.bigrat.chungusware.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityRenderer.class)
public interface IEntityRenderer {
    @Accessor
    ShaderGroup getTheShaderGroup();

    @Accessor
    void setTheShaderGroup(ShaderGroup shaderGroup);

    @Accessor
    IResourceManager getResourceManager();

    @Accessor
    Minecraft getMc();

    @Accessor
    int getShaderIndex();

    @Accessor
    void setShaderIndex(int shaderIndex);

    @Accessor
    int getShaderCount();

    @Accessor
    Logger getLogger();

    @Accessor
    void setUseShader(boolean useShader);

    @Accessor
    ResourceLocation[] getShaderResourceLocations();
}
