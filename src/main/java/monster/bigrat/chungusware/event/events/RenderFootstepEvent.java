package monster.bigrat.chungusware.event.events;

import monster.bigrat.chungusware.event.ClientEvent;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;

public class RenderFootstepEvent extends ClientEvent {
    public int entityId;
    public float partialTicks;
    public double posX, posY, posZ;
    public int age, maxAge;
    public RenderFootstepEvent(int entityId, float partialTicks, double posX, double posY, double posZ, int age, int maxAge) {
        this.entityId = entityId;
        this.partialTicks = partialTicks;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.age = age;
        this.maxAge = maxAge;
    }
}
