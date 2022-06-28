package monster.bigrat.chungusware.event.events;

import monster.bigrat.chungusware.event.ClientEvent;
import net.minecraft.network.Packet;

public class SendPacketEvent extends ClientEvent {
    public Packet packet;

    public SendPacketEvent(Packet packet) {
        this.packet = packet;
    }
}
