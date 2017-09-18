package worldcontrolteam.worldcontrol.init;


import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import worldcontrolteam.worldcontrol.blocks.BlockHowlerAlarm;
import worldcontrolteam.worldcontrol.blocks.BlockIndustrialAlarm;
import worldcontrolteam.worldcontrol.blocks.fluids.FluidMercury;
import worldcontrolteam.worldcontrol.tileentity.TileEntityHowlerAlarm;

public class WCBlocks {

    public static Block INDUSTRIAL_ALARM;
    public static Block HOWLER_ALARM;

    public static Fluid MERCURY;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> registry) {
        registry.getRegistry().registerAll(
                new ItemBlock(INDUSTRIAL_ALARM).setRegistryName(INDUSTRIAL_ALARM.getRegistryName()),
                new ItemBlock(HOWLER_ALARM).setRegistryName(HOWLER_ALARM.getRegistryName())
        );
    }
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> registry) {
        INDUSTRIAL_ALARM = new BlockIndustrialAlarm();
        HOWLER_ALARM = new BlockHowlerAlarm();
        GameRegistry.registerTileEntity(TileEntityHowlerAlarm.class, "HowlerAlarm");

        MERCURY = new FluidMercury();

        registry.getRegistry().registerAll(INDUSTRIAL_ALARM, HOWLER_ALARM);
    }
}
