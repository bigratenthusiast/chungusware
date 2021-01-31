package monster.bigrat.chungusware.modules.render;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import monster.bigrat.chungusware.event.events.Render3DEvent;
import monster.bigrat.chungusware.mixins.IMinecraft;
import monster.bigrat.chungusware.mixins.IRenderManager;
import monster.bigrat.chungusware.modules.Module;
import monster.bigrat.chungusware.util.GLUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Timer;
import org.lwjgl.input.Keyboard;

import static net.minecraft.client.renderer.GlStateManager.*;
import static org.lwjgl.opengl.GL11.*;

public class Nametags extends Module {
    public Nametags() {
        super("Nametags", Keyboard.KEY_NONE, Type.RENDER);
    }

    @EventHandler
    private Listener<Render3DEvent> render3DEventListener = new Listener<>(event -> {

        glPushAttrib(GL_ENABLE_BIT);
        glPushMatrix();

        // Disable lightning and depth test
        glDisable(GL_LIGHTING);
        glDisable(GL_DEPTH_TEST);

        glEnable(GL_LINE_SMOOTH);

        // Enable blend
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityPlayerSP)
                renderNameTag((EntityLivingBase)entity, entity.getName());
        }

        glPopMatrix();
        glPopAttrib();

        // Reset color
        glColor4f(1F, 1F, 1F, 1F);
    });

    private void renderNameTag(EntityLivingBase entity, String tag) {
        EntityPlayerSP thePlayer = mc.thePlayer;
        if (thePlayer== null) return;

        String nameColor = (entity.isInvisible()) ? "§6" : (entity.isSneaking()) ? "§4" : "§7";

        //     String distanceText = (distanceValue.get()) ? "§7$ " + Math.round(mc.thePlayer.getDistanceToEntity(entity)) + "m " : "";
        //      String pingText = (pingValue.get() && classProvider.isEntityPlayer(entity)) ? (if (ping > 200) "§c" else if (ping > 100) "§e" else "§a") + ping + "ms §7" else ""
        String healthText = "§7§c " + entity.getHealth() + " HP";

        String text =   nameColor + tag + healthText;//"$distanceText$pingText$nameColor$tag$healthText$botText"

        // Push
        glPushMatrix();

        // Translate to player position
        Timer timer = ((IMinecraft) mc).getTimer();
        RenderManager renderManager = mc.getRenderManager();


        glTranslated( // Translate to player position with render pos and interpolate it
                entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * timer.renderPartialTicks - ((IRenderManager) renderManager).getRenderPosX(),
                entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * timer.renderPartialTicks - ((IRenderManager) renderManager).getRenderPosY() + ((double) entity.getEyeHeight()) + 0.55,
                entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * timer.renderPartialTicks - ((IRenderManager) renderManager).getRenderPosZ()
        );

        glRotatef(-mc.getRenderManager().playerViewY, 0F, 1F, 0F);
        glRotatef(mc.getRenderManager().playerViewX, 1F, 0F, 0F);


        // Scale
        float distance = thePlayer.getDistanceToEntity(entity) * 0.25f;

        if (distance < 1F)
            distance = 1F;

        float scale = distance / 50f;

        glScalef(-scale, -scale, scale);

      //  AWTFontRenderer.assumeNonVolatile = true;

        // Draw NameTag
        float width = mc.fontRendererObj.getStringWidth(text) * 0.5f;

        glDisable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);

  //      if (borderValue.get())
         //   quickDrawBorderedRect(-width - 2F, -2F, width + 4F, fontRenderer.fontHeight + 2F, 2F, Color(255, 255, 255, 90).rgb, Integer.MIN_VALUE)
     //   else
           GLUtils.quickDrawRect(-width - 2F, -2F, width + 4F, 10f /*font height*/ + 2F, Integer.MIN_VALUE);

        glEnable(GL_TEXTURE_2D);

        mc.fontRendererObj.drawString(text, 1F + -width, 1F, 0xFFFFFF, true);

      //  AWTFontRenderer.assumeNonVolatile = false;

      //  if (/*armorValue.get() && classProvider.isEntityPlayer(entity)*/) {
            ((IMinecraft) mc).getRenderItem().zLevel = -147F;

            int[] indices = new int[] {0, 1, 2, 3, 4};

            for (int index : indices) {
                if (entity.getEquipmentInSlot(index) != null)
                    mc.getRenderItem().renderItemAndEffectIntoGUI(entity.getEquipmentInSlot(index), -50 + index * 20, -22);
            }

            enableAlpha();
            disableBlend();
            enableTexture2D();
     //   }

        // Pop
        glPopMatrix();
    }
}
