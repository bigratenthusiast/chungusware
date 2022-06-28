package monster.bigrat.chungusware.event.events;

import monster.bigrat.chungusware.event.ClientEvent;
import net.minecraft.entity.Entity;


public class RenderEntityEvent extends ClientEvent {
    public Entity entity;
    public double x, y, z;
    public float entityYaw, partialTicks;

    public RenderEntityEvent(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {

    }
}
