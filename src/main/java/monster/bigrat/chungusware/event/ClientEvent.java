package monster.bigrat.chungusware.event;

import me.zero.alpine.event.type.Cancellable;

enum Era {
    PRE, POST
}

public class ClientEvent extends Cancellable {
    public Era era = Era.PRE;

}