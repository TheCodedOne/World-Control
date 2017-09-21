package worldcontrolteam.worldcontrol.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import worldcontrolteam.worldcontrol.tileentity.TileHolographicDisplay;
import worldcontrolteam.worldcontrol.utils.GuiLib;

public class BlockHolographicDisplay extends BlockBasicTileProvider {
    public BlockHolographicDisplay() {
        super(Material.IRON);
        setRegistryName("holo_display");
    }

    @Override
    public TileEntity getTile(World world, int meta) {
        return new TileHolographicDisplay();
    }

    @Override
    public boolean hasGUI(IBlockState state) {
        return true;
    }

    @Override
    public int guiID(IBlockState state) {
        return GuiLib.HOLO_DISPLAY;
    }
}
