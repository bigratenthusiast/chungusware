package monster.bigrat.chungusware.event.events;

import monster.bigrat.chungusware.event.ClientEvent;

public class ChatEvent extends ClientEvent {
    public String message;

    public ChatEvent(String message) {
        this.message = message;
    }
}
