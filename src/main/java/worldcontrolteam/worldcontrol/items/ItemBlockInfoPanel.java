package worldcontrolteam.worldcontrol.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import worldcontrolteam.worldcontrol.api.PanelType;

public class ItemBlockInfoPanel extends ItemBlock {
    public ItemBlockInfoPanel(Block block) {
        super(block);
        setRegistryName(block.getRegistryName());
        setUnlocalizedName("worldcontrol." + block.getRegistryName().getResourcePath());
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + "." + PanelType.getFromMeta(stack.getMetadata()).getName();
    }
}