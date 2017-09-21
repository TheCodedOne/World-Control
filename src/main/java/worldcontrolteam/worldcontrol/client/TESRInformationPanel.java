package worldcontrolteam.worldcontrol.client;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import worldcontrolteam.worldcontrol.tileentity.TileInformationPanel;

public class TESRInformationPanel extends TileEntitySpecialRenderer<TileInformationPanel> {
    @Override
    public void render(TileInformationPanel te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        Screen screen = te.getScreen();

    }
}
