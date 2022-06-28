package monster.bigrat.chungusware.module.modules;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import monster.bigrat.chungusware.Client;
import monster.bigrat.chungusware.event.events.SendPacketEvent;
import monster.bigrat.chungusware.module.Module;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import org.lwjgl.input.Keyboard;

import java.util.ArrayDeque;
import java.util.Queue;

public class Blink extends Module {
    static final int FAKE_PLAYER_ID = -68419;
    Queue<Packet> packets = new ArrayDeque<>(); // Create list to hold our packets
    private EntityOtherPlayerMP clonedPlayer; // Fake Player when player blinked

    @EventHandler
    private final Listener<SendPacketEvent> packetEventListener = new Listener<>(event -> {
        if (mc.isSingleplayer()) {
            Client.hud.drawActionBar("Blink cannot be used in singeplayer");
            toggle();
            return;
        }
        // C03PacketPlayer 1.8 is the equivalent of PlayerMoveC2SPacket
        if (event.packet instanceof C03PacketPlayer || (settings.getBool("withholdAllPackets") && mc.theWorld != null && !(event.packet instanceof S07PacketRespawn) && !(event.packet instanceof S0CPacketSpawnPlayer))) {
            event.cancel();
            packets.add(event.packet);
            Client.hud.drawActionBar("Packets " + packets.size() + "/" + (settings.getBool("isCancelable") ? Integer.toString(settings.getInt("maxPacketAmount")) : "∞"));
        }
    });

    public Blink() {
        super("Blink", Keyboard.KEY_R, Type.MOVEMENT);
        this.settings.add("isCancelable", false);
        this.settings.add("withholdAllPackets", true);
        this.settings.add("maxPacketAmount", 200);
    }

    @Override
    public void onEnable() {
        if (mc.isSingleplayer()) return;
        if (mc.thePlayer != null) {
            assert mc.theWorld != null;
            clonedPlayer = new EntityOtherPlayerMP(mc.theWorld, mc.getSession().getProfile()); // Create Fake Player
            clonedPlayer.copyDataFromOld(mc.thePlayer);
            clonedPlayer.copyLocationAndAnglesFrom(mc.thePlayer);
            mc.theWorld.addEntityToWorld(FAKE_PLAYER_ID, clonedPlayer); //There is likely not going to be an entity with this id
        }
    }

    @Override
    public void onDisable() {
        if (mc.isSingleplayer()) return;
        if (packets.size() > settings.getInt("maxPacketAmount") && settings.getBool("isCancelable") && mc.thePlayer != null) {
            if (clonedPlayer != null)  // We don't want a NPE when a player tries to disable blink after logging in
                mc.thePlayer.setPositionAndUpdate(clonedPlayer.posX, clonedPlayer.posY, clonedPlayer.posZ); // Snap back to where we were
            packets.clear(); //Empty the list of packets to send
        }

        NetworkManager netMan = mc.getNetHandler().getNetworkManager();
        while (!packets.isEmpty() && netMan != null) {
            netMan.sendPacket(packets.poll()); // Send all packets at once
        }

        EntityPlayerSP localPlayer = mc.thePlayer;
        if (localPlayer != null && mc.theWorld != null) {
            mc.theWorld.removeEntityFromWorld(FAKE_PLAYER_ID); // Remove fake blink Player
            clonedPlayer = null;
        }
    }
}

