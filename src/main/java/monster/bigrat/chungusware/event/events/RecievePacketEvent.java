package monster.bigrat.chungusware.event.events;

import monster.bigrat.chungusware.event.ClientEvent;
import net.minecraft.network.Packet;

public class RecievePacketEvent extends ClientEvent {
    public Packet packet;

    public RecievePacketEvent(Packet packet) {
        this.packet = packet;
    }
}
