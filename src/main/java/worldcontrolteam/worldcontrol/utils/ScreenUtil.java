package worldcontrolteam.worldcontrol.utils;

import net.minecraft.util.EnumFacing;

public class ScreenUtil {

    private static final EnumFacing[][] connections = new EnumFacing[][]{
            new EnumFacing[]{EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.WEST},
            new EnumFacing[]{EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.WEST},
            new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN, EnumFacing.EAST, EnumFacing.WEST},
            new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN, EnumFacing.EAST, EnumFacing.WEST},
            new EnumFacing[]{EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.UP, EnumFacing.DOWN},
            new EnumFacing[]{EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.UP, EnumFacing.DOWN},
    };

    public static EnumFacing[] getConnectionFacesForFace(EnumFacing facing) {
        return connections[facing.getIndex()];
    }
}