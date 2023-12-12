package monster.bigrat.chungusware.mixins;

import monster.bigrat.chungusware.Client;
import monster.bigrat.chungusware.event.events.ChatEvent;
import monster.bigrat.chungusware.event.events.UpdateEvent;
import monster.bigrat.chungusware.event.events.UpdateWalkingPlayerEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C01PacketChatMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends MixinAbstractClientPlayer {
    @Inject(method = "onUpdateWalkingPlayer", at = @At("HEAD"))
    public void onUpdateWalkingPlayer(CallbackInfo ci) {
        Client.EVENT_BUS.post(new UpdateWalkingPlayerEvent());
    }

    /**
     * @author bigratenthusiast
     */
    @Overwrite
    public void sendChatMessage(String message) {
        ChatEvent event = new ChatEvent(message);
        Client.EVENT_BUS.post(event);
        if (!event.isCancelled()) {
            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(message));
        }
    }

    @Inject(method = "onLivingUpdate", at = @At("HEAD"))
    public void onLivingUpdate(CallbackInfo ci) {
        Client.EVENT_BUS.post(new UpdateEvent());
    }


}