package worldcontrolteam.worldcontrol.crossmod.tesla;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import worldcontrolteam.worldcontrol.WorldControl;
import worldcontrolteam.worldcontrol.blocks.BlockBasicTileProvider;
import worldcontrolteam.worldcontrol.utils.GuiLib;

public class BlockTeslaAverageCounter extends BlockBasicTileProvider {

    public BlockTeslaAverageCounter() {
        super(Material.ANVIL);
        this.setRegistryName("tesla_average_counter");
        this.setUnlocalizedName("tesla_average_counter");
        this.setCreativeTab(WorldControl.TAB);

        //GameRegistry.register(this);
        //GameRegistry.register(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public TileEntity getTile(World world, int meta) {
        return new TileEntityTeslaAverageCounter();
    }

    @Override
    public boolean hasGUI(IBlockState state) {
        return true;
    }

    @Override
    public int guiID(IBlockState state) {
        return GuiLib.TESLA_AVERAGE_COUNTER;
    }
}
