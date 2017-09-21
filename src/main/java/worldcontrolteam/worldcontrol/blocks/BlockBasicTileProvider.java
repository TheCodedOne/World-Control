package worldcontrolteam.worldcontrol.blocks;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import worldcontrolteam.worldcontrol.WorldControl;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class BlockBasicTileProvider extends Block implements ITileEntityProvider {

    public BlockBasicTileProvider(Material blockMaterial) {
        super(blockMaterial);

    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return getTile(world, meta);
    }

    public abstract TileEntity getTile(World world, int meta);

    public abstract boolean hasGUI(IBlockState state);

    public abstract int guiID(IBlockState state);

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (this.hasGUI(state)) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity == null || player.isSneaking())
                return false;

            player.openGui(WorldControl.instance, guiID(state), world, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
        return false;
    }
}
