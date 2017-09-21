package worldcontrolteam.worldcontrol.blocks;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import worldcontrolteam.worldcontrol.WorldControl;
import worldcontrolteam.worldcontrol.api.IScreenPart;
import worldcontrolteam.worldcontrol.api.PanelType;
import worldcontrolteam.worldcontrol.tileentity.TilePanelExtender;
import worldcontrolteam.worldcontrol.utils.ScreenUtil;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockPanelExtender extends BlockBasicTileProvider {
    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public BlockPanelExtender() {
        super(Material.IRON);
        setRegistryName("panel_extender");
        setUnlocalizedName("worldcontrol.panel_extender");
        setCreativeTab(WorldControl.TAB);
        setDefaultState(getBlockState().getBaseState().withProperty(PanelType.TYPE, PanelType.BASIC).withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (PanelType type : PanelType.values())
            items.add(new ItemStack(this, 1, type.getMeta()));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(PanelType.TYPE, PanelType.getFromMeta(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(PanelType.TYPE).getMeta();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, PanelType.TYPE, FACING);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        TileEntity tile = worldIn.getTileEntity(pos);
        EnumFacing setFace = EnumFacing.NORTH;
        if (tile != null && tile instanceof TilePanelExtender) {
            float pitch = placer.rotationPitch;
            setFace = placer.getHorizontalFacing().getOpposite();
            if (pitch >= 65)
                setFace = EnumFacing.UP;
            if (pitch <= -65)
                setFace = EnumFacing.DOWN;
            ((TilePanelExtender) tile).setFacing(setFace);
        }

        for (EnumFacing facing : ScreenUtil.getConnectionFacesForFace(setFace)) {
            BlockPos offPos = pos.offset(facing);
            TileEntity tileEntity = worldIn.getTileEntity(offPos);
            if (tileEntity != null && tile instanceof IScreenPart && ((IScreenPart) tile).getScreen() != null) {
                ((IScreenPart) tile).getScreen().extenderPlaced(worldIn, pos);
            }
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile != null && tile instanceof TilePanelExtender) {
            return state.withProperty(FACING, ((TilePanelExtender) tile).getFacing());
        }
        return super.getActualState(state, worldIn, pos);
    }

    @Override
    public TileEntity getTile(World world, int meta) {
        return new TilePanelExtender(PanelType.getFromMeta(meta));
    }

    @Override
    public boolean hasGUI(IBlockState state) {
        return false;
    }

    @Override
    public int guiID(IBlockState state) {
        return 0;
    }
}
