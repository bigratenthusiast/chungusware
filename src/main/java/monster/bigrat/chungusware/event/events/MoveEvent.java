package monster.bigrat.chungusware.event.events;

import monster.bigrat.chungusware.event.ClientEvent;

public class MoveEvent extends ClientEvent {
    public double x, y, z;

    public MoveEvent(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
