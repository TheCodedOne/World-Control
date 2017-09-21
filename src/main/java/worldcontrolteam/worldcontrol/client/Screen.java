package worldcontrolteam.worldcontrol.client;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import worldcontrolteam.worldcontrol.api.IScreenPart;
import worldcontrolteam.worldcontrol.tileentity.TileInformationPanel;

public class Screen {
    public int minX;
    public int minY;
    public int minZ;
    public int maxX;
    public int maxY;
    public int maxZ;
    private int coreX;
    private int coreY;
    private int coreZ;
    private boolean powered = false;

    public TileInformationPanel getCore(IBlockAccess world) {
        TileEntity tile = world.getTileEntity(new BlockPos(coreX, coreY, coreZ));
        if (tile != null && tile instanceof TileInformationPanel) {
            return (TileInformationPanel) tile;
        }
        return null;
    }

    public void extenderPlaced(World worldIn, BlockPos pos) {

    }

    public void extenderRemoved(World worldIn, BlockPos pos) {

    }

    public void setCore(TileInformationPanel panel) {
        coreX = panel.getPos().getX();
        coreY = panel.getPos().getY();
        coreZ = panel.getPos().getZ();
        powered = panel.isPowered();
    }

    public boolean isTileNearby(TileEntity tile) {
        int x = tile.getPos().getX();
        int y = tile.getPos().getY();
        int z = tile.getPos().getZ();
        return (x == minX - 1 && y >= minY && y <= maxY && z >= minZ && z <= maxZ)
                || (x == maxX + 1 && y >= minY && y <= maxY && z >= minZ && z <= maxZ)
                || (x >= minX && x <= maxX && y == minY - 1 && z >= minZ && z <= maxZ)
                || (x >= minX && x <= maxX && y == maxY + 1 && z >= minZ && z <= maxZ)
                || (x >= minX && x <= maxX && y >= minY && y <= maxY && z == minZ - 1)
                || (x >= minX && x <= maxX && y >= minY && y <= maxY && z == maxZ + 1);
    }

    public boolean isTilePartOf(TileEntity tileEntity) {
        int x = tileEntity.getPos().getX();
        int y = tileEntity.getPos().getY();
        int z = tileEntity.getPos().getZ();
        return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ
                && z <= maxZ;
    }

    public NBTTagCompound toTag() {
        NBTTagCompound tag = new NBTTagCompound();

        tag.setInteger("minX", minX);
        tag.setInteger("minY", minY);
        tag.setInteger("minZ", minZ);

        tag.setInteger("maxX", maxX);
        tag.setInteger("maxY", maxY);
        tag.setInteger("maxZ", maxZ);

        return tag;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + coreX;
        result = prime * result + coreY;
        result = prime * result + coreZ;
        result = prime * result + maxX;
        result = prime * result + maxY;
        result = prime * result + maxZ;
        result = prime * result + minX;
        result = prime * result + minY;
        result = prime * result + minZ;
        return result;
    }

    public void init(boolean force, World world) {
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
                    if (tileEntity == null || !(tileEntity instanceof IScreenPart))
                        continue;
                    ((IScreenPart) tileEntity).setScreen(this);
                }
            }
        }
        if (powered || force) {
            world.markBlockRangeForRenderUpdate(minX, minY, minZ, maxX, maxY, maxZ);
        }
    }

    public void destroy(boolean force, World world) {
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
                    if (tileEntity == null
                            || !(tileEntity instanceof IScreenPart))
                        continue;
                    IScreenPart part = (IScreenPart) tileEntity;
                    Screen targetScreen = part.getScreen();
                    if (targetScreen != null && targetScreen.equals(this)) {
                        part.setScreen(null);
                        part.updateData();
                    }
                }
            }
        }
        if (powered || force) {
            world.markBlockRangeForRenderUpdate(minX, minY, minZ, maxX, maxY, maxZ);
        }
    }

    public boolean isCore(int x, int y, int z) {
        return x == coreX && y == coreY && z == coreZ;
    }

    public int getDx() {
        return maxX - minX;
    }

    public int getDy() {
        return maxY - minY;
    }

    public int getDz() {
        return maxZ - minZ;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Screen other = (Screen) obj;
        return coreX == other.coreX && coreY == other.coreY && coreZ == other.coreZ && maxX == other.maxX && maxY == other.maxY && maxZ == other.maxZ && minX == other.minX && minY == other.minY && minZ == other.minZ;
    }
}