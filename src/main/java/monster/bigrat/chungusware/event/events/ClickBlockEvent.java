package monster.bigrat.chungusware.event.events;

import monster.bigrat.chungusware.event.ClientEvent;
import net.minecraft.util.BlockPos;

public class ClickBlockEvent extends ClientEvent {
    public BlockPos location;

    public ClickBlockEvent(BlockPos location) { this.location = location; }
}