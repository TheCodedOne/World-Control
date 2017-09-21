package worldcontrolteam.worldcontrol.api;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.IStringSerializable;

public enum PanelType implements IStringSerializable {
    BASIC,
    ADVANCED;

    public static final PanelType[] values = values();

    public static final PropertyEnum<PanelType> TYPE = PropertyEnum.create("type", PanelType.class);

    public static PanelType getFromMeta(int meta) {
        return getValues()[meta % getValues().length];
    }

    public static PanelType[] getValues() {
        return values;
    }

    public int getMeta() {
        return ordinal();
    }

    @Override
    public String getName() {
        return name().toLowerCase();
    }
}
