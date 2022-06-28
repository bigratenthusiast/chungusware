package monster.bigrat.chungusware.command.commands;

import monster.bigrat.chungusware.command.Command;
import net.minecraft.network.play.client.C01PacketChatMessage;


public class SayCommand extends Command {
    public SayCommand() {
        super("say", "Says a message in chat", "[...message]");
    }

    @Override
    public void run(String[] args) {
        mc.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(String.join(" ", args)));
    }

}
