package monster.bigrat.chungusware.util;


import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.InsecureTextureException;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import monster.bigrat.chungusware.mixins.ISkinManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.SkinManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Utils {
    public static List<String> autoEZTriggers = Arrays.asList(
            "1st Killer - ",
            "1st Place - ",
            "Winner: ",
            " - Damage Dealt -",
            "Winning Team -",
            "1st - ",
            "Winners: ",
            "Winner: ",
            "Winning Team: ",
            " won the game!",
            "Top Seeker: ",
            "1st Place: ",
            "Last team standing!",
            "Winner #1 (",
            "Top Survivors",
            "Winners - ",
            "Sumo Duel -"
    );
    public static List<String> enableOnStart = Arrays.asList(
            "discordrpc",
            "autoez",
            "footstepassist",
            "autotool"
    );

    public static int[] palestineColors(int length) {
        return length == 1 ? new int[]{Colors.white} : new int[]{Colors.red, Colors.gray, Colors.green};
    }

    public static Runnable getRunnable(SkinManager outer, GameProfile gameProfile, SkinManager.SkinAvailableCallback callback, boolean secure) {
        return () -> {
            final HashMap<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = Maps.newHashMap();
            try {
                map.putAll(((ISkinManager) outer).getSessionService().getTextures(gameProfile, secure));
            } catch (InsecureTextureException ignored) {
                // i do not see it
            }

            if (map.isEmpty() && gameProfile.getId().equals(Minecraft.getMinecraft().getSession().getProfile().getId())) {
                gameProfile.getProperties().clear();
                gameProfile.getProperties().putAll(Minecraft.getMinecraft().getProfileProperties());
                map.putAll(((ISkinManager) outer).getSessionService().getTextures(gameProfile, false));
            }

            // loadCape(gameProfile, map, callback);

            Minecraft.getMinecraft().addScheduledTask(() -> {
                if (map.containsKey(MinecraftProfileTexture.Type.SKIN)) {
                    outer.loadSkin(map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN, callback);
                }

                if (map.containsKey(MinecraftProfileTexture.Type.CAPE)) {
                    outer.loadSkin(map.get(MinecraftProfileTexture.Type.CAPE), MinecraftProfileTexture.Type.CAPE, callback);
                }
            });
        };
    }

    public static void loadCape(GameProfile gameprofile, HashMap<MinecraftProfileTexture.Type, MinecraftProfileTexture> skinMap, SkinManager.SkinAvailableCallback callback) {
        try {
            skinMap.put(MinecraftProfileTexture.Type.CAPE, new MinecraftProfileTexture("https://i.imgur.com/GBFcK0J.png", null));
        } catch (Exception e) {
            System.out.println("lol new error just dropped " + e);
            e.printStackTrace();
        }
    }

    public static class Colors {
        public static int blue = 0xFF55CDFC;
        public static int gray = 0xFF242424;
        public static int white = -1;
        public static int pink = 0xFFF7A8B8;
        public static int red = 0xFFF03E3E;
        public static int green = 0xFF239063;
        public static int transparentGray = 0x90000000;
    }

}
