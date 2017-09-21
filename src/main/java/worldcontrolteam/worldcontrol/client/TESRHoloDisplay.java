package worldcontrolteam.worldcontrol.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import worldcontrolteam.worldcontrol.api.card.IProviderCard;
import worldcontrolteam.worldcontrol.api.card.StringWrapper;
import worldcontrolteam.worldcontrol.network.ChannelHandler;
import worldcontrolteam.worldcontrol.network.messages.PacketServerRemotePanel;
import worldcontrolteam.worldcontrol.tileentity.TileHolographicDisplay;

import java.util.LinkedList;
import java.util.List;

public class TESRHoloDisplay extends TileEntitySpecialRenderer<TileHolographicDisplay> {

    private static final String TEXTURE_FILE = "worldcontrol:textures/gui/holo.png";
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(TEXTURE_FILE);

    @Override
    public void render(TileHolographicDisplay te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.translate(2, 2.6, 0.5);
        GlStateManager.rotate(180, 0, 0, 1);
        GlStateManager.scale(0.0625f, 0.0625f, 0.0625f);
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        setLightmapDisabled(true);
        bindTexture(TEXTURE_LOCATION);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, 48, 32, 256, 256);
        GlStateManager.pushMatrix();
        GlStateManager.translate(2, 2, -0.001);
        GlStateManager.scale(0.3125f, 0.3125f, 0.3125f);
        int width = 140;
        ItemStack stack = Minecraft.getMinecraft().player.getHeldItemMainhand();
        if (stack.getItem() instanceof IProviderCard) {
            ChannelHandler.network.sendToServer(new PacketServerRemotePanel(stack));
            if (stack.hasTagCompound()) {
                IProviderCard card = (IProviderCard) stack.getItem();
                List<StringWrapper> s = card.getStringData(new LinkedList<>(), 0, stack, true);

            }
        }
        GlStateManager.popMatrix();

        setLightmapDisabled(false);
        GlStateManager.popMatrix();
    }

    private void drawCardData(List<StringWrapper> wrappers) {
        int width = 140;
        int row = 0;
        for (StringWrapper wrapper : wrappers) {
            String left = wrapper.textLeft;
            String center = wrapper.textCenter;
            String right = wrapper.textRight;
            if (left != null)
                getFontRenderer().drawString(left, 0, row * 10, 0);
            if (center != null) {
                int cenerWidth = getFontRenderer().getStringWidth(center);
                getFontRenderer().drawString(center, (width / 2) - (cenerWidth / 2), row * 10, 0);
            }
            if (right != null) {
                int rightWidth = getFontRenderer().getStringWidth(right);
                getFontRenderer().drawString(right, width - rightWidth, row * 10, 0);
            }
            row++;
        }
    }
}
