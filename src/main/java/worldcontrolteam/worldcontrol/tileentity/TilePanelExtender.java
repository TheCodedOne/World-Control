package worldcontrolteam.worldcontrol.tileentity;

import ic2.api.tile.IWrenchable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import worldcontrolteam.worldcontrol.api.IScreenPart;
import worldcontrolteam.worldcontrol.api.PanelType;
import worldcontrolteam.worldcontrol.client.Screen;

import java.util.List;

public class TilePanelExtender extends TileEntity implements IWrenchable,IScreenPart {

    private EnumFacing facing;

    private PanelType type;
    private Screen screen;

    public TilePanelExtender(PanelType type) {
        this.type = type;
        facing=EnumFacing.NORTH;
    }

    public TilePanelExtender() {
        this(PanelType.BASIC);
    }

    @Override
    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    @Override
    public Screen getScreen() {
        return screen;
    }

    @Override
    public void updateData() {

    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        type = PanelType.getFromMeta(compound.getInteger("type"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("type", type.getMeta());
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
        if (enumFacing != null && enumFacing != facing) {
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
