package worldcontrolteam.worldcontrol.tileentity;

import ic2.api.tile.IWrenchable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import worldcontrolteam.worldcontrol.api.IScreenPart;
import worldcontrolteam.worldcontrol.api.PanelType;
import worldcontrolteam.worldcontrol.client.Screen;

import javax.annotation.Nonnull;
import java.util.List;

public class TileInformationPanel extends TileEntity implements IWrenchable, IScreenPart {

    private EnumFacing facing;

    private PanelType type;

    private boolean powered;
    private Screen screen;

    private IItemHandler inventory;
    private IItemHandler upgradeInventory;

    public TileInformationPanel(PanelType type) {
        this.type = type;
        inventory = new ItemStackHandler(1);
        upgradeInventory = new ItemStackHandler(3);
    }

    public TileInformationPanel() {
        this(PanelType.BASIC);
    }

    @Override
    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    @Override
    @Nonnull
    public AxisAlignedBB getRenderBoundingBox() {
        if (screen != null)
            return new AxisAlignedBB(screen.minX, screen.minY, screen.minZ, screen.maxX, screen.maxY, screen.maxZ);
        return super.getRenderBoundingBox();
    }

    @Override
    public Screen getScreen() {
        return screen;
    }

    @Override
    public void updateData() {

    }

    public boolean isPowered() {
        return powered;
    }

    public void setPowered(boolean powered) {
        this.powered = powered;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        type = PanelType.getFromMeta(compound.getInteger("type"));
        powered = compound.getBoolean("powered");
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("type", type.getMeta());
        compound.setBoolean("powered", powered);
        return super.writeToNBT(compound);
    }

    public EnumFacing getFacing() {
        return facing;
    }

    public void setFacing(EnumFacing facing) {
        this.facing = facing;
    }

    @Override
    public EnumFacing getFacing(World world, BlockPos blockPos) {
        return facing;
    }

    @Override
    public boolean setFacing(World world, BlockPos blockPos, EnumFacing enumFacing, EntityPlayer entityPlayer) {
        if (enumFacing != facing) {
            setFacing(enumFacing);
            return true;
        }
        return false;
    }

    @Override
    public boolean wrenchCanRemove(World world, BlockPos blockPos, EntityPlayer entityPlayer) {
        return false;
    }

    @Override
    public List<ItemStack> getWrenchDrops(World world, BlockPos blockPos, IBlockState iBlockState, TileEntity tileEntity, EntityPlayer entityPlayer, int i) {
        return null;
    }
}
