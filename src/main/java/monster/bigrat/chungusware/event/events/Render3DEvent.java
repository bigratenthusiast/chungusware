package monster.bigrat.chungusware.event.events;

import monster.bigrat.chungusware.event.ClientEvent;

public class Render3DEvent extends ClientEvent {
    public float partialTicks;

    public Render3DEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }
}
