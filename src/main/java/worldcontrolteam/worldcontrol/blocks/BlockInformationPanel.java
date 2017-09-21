package worldcontrolteam.worldcontrol.blocks;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import worldcontrolteam.worldcontrol.WorldControl;
import worldcontrolteam.worldcontrol.api.PanelType;
import worldcontrolteam.worldcontrol.tileentity.TileInformationPanel;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockInformationPanel extends BlockBasicTileProvider {

    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public static final PropertyInteger COLOUR = PropertyInteger.create("colour", 0, 15);

    public BlockInformationPanel() {
        super(Material.IRON);
        setRegistryName("information_panel");
        setUnlocalizedName("worldcontrol.information_panel");
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
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile != null && tile instanceof TileInformationPanel) {
            float pitch = placer.rotationPitch;
            EnumFacing setFace = placer.getHorizontalFacing().getOpposite();
            if (pitch >= 65)
                setFace = EnumFacing.UP;
            if (pitch <= -65)
                setFace = EnumFacing.DOWN;
            ((TileInformationPanel) tile).setFacing(setFace);
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, PanelType.TYPE, FACING);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile != null && tile instanceof TileInformationPanel) {
            return state.withProperty(FACING, ((TileInformationPanel) tile).getFacing());
        }
        return super.getActualState(state, worldIn, pos);
    }

    @Override
    public TileEntity getTile(World world, int meta) {
        return new TileInformationPanel(PanelType.getFromMeta(meta));
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
