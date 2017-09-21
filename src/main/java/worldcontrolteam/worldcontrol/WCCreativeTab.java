package worldcontrolteam.worldcontrol;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import worldcontrolteam.worldcontrol.init.WCBlocks;

public class WCCreativeTab extends CreativeTabs {

    public WCCreativeTab() {
        super("worldcontrol");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getTabIconItem() {
        return new ItemStack(WCBlocks.PANEL, 1, 1);
    }
}