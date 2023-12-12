package monster.bigrat.chungusware.gui;

import monster.bigrat.chungusware.Client;
import monster.bigrat.chungusware.event.events.RenderGuiEvent;
import monster.bigrat.chungusware.module.Module;
import monster.bigrat.chungusware.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Hud {
    public static final int offset = 4;
    public static final int actionBarDuration = 1000;
    public static FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
    public Minecraft mc = Minecraft.getMinecraft();
    public String actionBarText = "";
    public Long lastActionFlash = 0L;

    public static void renderPotionLine(int x, double y, String _string) {
        List<String> list = new ArrayList<>();
        list.add(_string);
        double ty = y;
        for (String string : list) {
            int shift = 0;
            int stringWidth = fontRenderer.getStringWidth(string);
            Gui.drawRect(((x - 1) - shift), (int) ((ty - 1)), ((x + 1)) + stringWidth - shift, (int) ((ty + 1)) + 8, Utils.Colors.transparentGray);
            fontRenderer.drawString(string, (x - shift), (int) (ty), 0xFFFFFF, true);
            ty += 10D;
        }
    }

    public void draw() {
        if (!Client.moduleEnabled("hidefeatures")) {
            ScaledResolution sr = new ScaledResolution(mc);
            fontRenderer.drawString(Client.name, offset, offset, -1, true);
            List<Module> enabledModules = Client.modules.stream().filter(Module::isEnabled).collect(Collectors.toList());

            for (int i = 0; i < enabledModules.size(); i++) {
                Module m = enabledModules.get(i);
                m.slideIn();

                int left = (sr.getScaledWidth() - offset) - (int) (fontRenderer.getStringWidth(m.name) * m.getPercentSlidIn() / 100f);
                int top = offset + ((fontRenderer.FONT_HEIGHT + 6) * i);

                // Background Box
                Gui.drawRect(left - 3, top, sr.getScaledWidth(), top + 4 + fontRenderer.FONT_HEIGHT, Utils.Colors.transparentGray);

                // Rectangle
                Gui.drawRect(
                        left - 6, top,
                        left - 3,
                        top + 4 + fontRenderer.FONT_HEIGHT,
                        Client.moduleEnabled("watermelon") ? Utils.palestineColors(enabledModules.size())[i % 3] : -1
                );

                fontRenderer.drawString(m.name, left, top + 3, -1, true);
            }

            // draw action bar
            if (lastActionFlash + actionBarDuration > System.currentTimeMillis()) {
                mc.fontRendererObj.drawString(
                        actionBarText,
                        (sr.getScaledWidth() / 2f) - (mc.fontRendererObj.getStringWidth(actionBarText) / 2f),
                        sr.getScaledHeight() - 60,
                        -1,
                        true);
            } else actionBarText = "";
        }

        if (Client.moduleEnabled("potionhud")) potionHud();
        if (Client.moduleEnabled("betterarmor")) armorHud();

        RenderGuiEvent event = new RenderGuiEvent();
        Client.EVENT_BUS.post(event);
    }

    public void drawActionBar(String string) {
        lastActionFlash = System.currentTimeMillis();
        actionBarText = string;
    }

    public void armorHud() {
        RenderItem itemRenderer = mc.getRenderItem();
        GlStateManager.enableTexture2D();

        ScaledResolution sr = new ScaledResolution(mc);
        int i = -1;
        int x = 3;
        for (ItemStack armor : Minecraft.getMinecraft().thePlayer.inventory.armorInventory) {
            i++;
            if (armor == null) continue;
            int y = (sr.getScaledHeight() - (sr.getScaledHeight() / 2)) + (16 * (4 - i));
            GlStateManager.enableDepth();
            itemRenderer.zLevel = 200F;
            itemRenderer.renderItemIntoGUI(armor, x, y);
            // itemRenderer.renderItemOverlayIntoGUI(mc.fontRendererObj, armor, x, y, armor.getDisplayName());
            itemRenderer.zLevel = 0F;
        }
    }

    public void potionHud() {
        int row = 0;
        Collection<PotionEffect> effects = Minecraft.getMinecraft().thePlayer.getActivePotionEffects();
        int x = 5;
        int y = mc.displayHeight / 5;

        for (PotionEffect potioneffect : effects) {
            StringBuilder s1 = new StringBuilder("              ");

            switch (potioneffect.getAmplifier()) {
                case 1:
                    s1.append(" ").append(I18n.format("enchantment.level.2"));
                    break;
                case 2:
                    s1.append(" ").append(I18n.format("enchantment.level.3"));
                    break;
                case 3:
                    s1.append(" ").append(I18n.format("enchantment.level.4"));
                    break;
            }

            String s = Potion.getDurationString(potioneffect);
            String text = s1 + " - " + s;
            renderPotionLine(x, ((y + row * 16)), text);
            Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
            if (potion.hasStatusIcon()) {
                int potionStatusIconIndex = potion.getStatusIconIndex();
                drawTexturedModalRect(x, ((y + row * 16)) - 4, potionStatusIconIndex % 8 * 18,
                        198 + potionStatusIconIndex / 8 * 18, 18, 18);
            }
            row++;
        }

    }

    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y + height, 0).tex((float) (textureX) * f, (float) (textureY + height) * f1).endVertex();
        worldrenderer.pos(x + width, y + height, 0).tex((float) (textureX + width) * f, (float) (textureY + height) * f1).endVertex();
        worldrenderer.pos(x + width, y, 0).tex((float) (textureX + width) * f, (float) (textureY) * f1).endVertex();
        worldrenderer.pos(x, y, 0).tex((float) (textureX) * f, (float) (textureY) * f1).endVertex();
        tessellator.draw();
    }
}
