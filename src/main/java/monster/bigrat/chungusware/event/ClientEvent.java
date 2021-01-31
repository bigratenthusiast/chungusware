package monster.bigrat.chungusware.event;

import me.zero.alpine.event.type.Cancellable;

public class ClientEvent extends Cancellable {
    public Era era = Era.PRE;

}

enum Era {
    PRE, POST
}