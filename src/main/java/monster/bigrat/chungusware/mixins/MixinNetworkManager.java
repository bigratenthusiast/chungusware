package monster.bigrat.chungusware.mixins;

import io.netty.channel.ChannelHandlerContext;
import monster.bigrat.chungusware.Client;
import monster.bigrat.chungusware.event.events.RecievePacketEvent;
import monster.bigrat.chungusware.event.events.SendPacketEvent;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    public void sendPacket(Packet packetIn, CallbackInfo ci) {
        SendPacketEvent event = new SendPacketEvent(packetIn);
        Client.EVENT_BUS.post(event);
        if (event.isCancelled()) ci.cancel();
    }

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    public void channelRead0(ChannelHandlerContext p_channelRead0_1_, Packet p_channelRead0_2_, CallbackInfo ci) {
        RecievePacketEvent event = new RecievePacketEvent(p_channelRead0_2_);
        Client.EVENT_BUS.post(event);
        if (event.isCancelled()) ci.cancel();
    }
}
