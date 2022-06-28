package monster.bigrat.chungusware.mixins;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.properties.Property;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Mixin(World.class)
public class MixinWorld {
    public JsonParser jsonParser = new JsonParser();
    public Property newProperty;

    @Inject(method = "spawnEntityInWorld", at = @At("HEAD"))
    public void spawnEntityInWorld(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof EntityPlayer && false) {
            try {
                EntityPlayer player = (EntityPlayer) entity;
                player.getGameProfile().getProperties().asMap().forEach((name, value) -> {
                    if (name.equals("textures")) {
                        value.forEach((property) -> {
                            if (property.getName().equals("textures")) {
                                String decoded = new String(Base64.getDecoder().decode(property.getValue()));
                                JsonObject profile = jsonParser.parse(decoded).getAsJsonObject();
                                JsonObject textures = profile.getAsJsonObject("textures");
                                if (!textures.has("CAPE")) {
                                    textures.add("CAPE", new JsonObject());
                                    JsonObject cape = textures.getAsJsonObject("CAPE");
                                    cape.addProperty("url", "http://i.imgur.com/2zndJGu.png");
                                }
                                String encoded = Base64.getEncoder().encodeToString(profile.toString().getBytes(StandardCharsets.UTF_8));
                                newProperty = new Property(property.getName(), encoded, property.getSignature());
                            }
                        });
                    }
                });

                player.getGameProfile().getProperties().removeAll("textures");
                player.getGameProfile().getProperties().put("textures", newProperty);
            } catch (Exception e) {
                System.out.println("Error in applying cape or something im not too sure myself really");
            }
        }
    }
}
