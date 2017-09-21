package worldcontrolteam.worldcontrol.init;


import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import worldcontrolteam.worldcontrol.blocks.*;
import worldcontrolteam.worldcontrol.blocks.fluids.FluidMercury;
import worldcontrolteam.worldcontrol.items.ItemBlockInfoPanel;
import worldcontrolteam.worldcontrol.tileentity.TileEntityHowlerAlarm;
import worldcontrolteam.worldcontrol.tileentity.TileHolographicDisplay;
import worldcontrolteam.worldcontrol.tileentity.TileInformationPanel;
import worldcontrolteam.worldcontrol.tileentity.TilePanelExtender;

public class WCBlocks {

    public static Block INDUSTRIAL_ALARM;
    public static Block HOWLER_ALARM;
    public static Block HOLO_DISPLAY;

    public static Block PANEL;
    public static Block EXTENDER;

    public static Fluid MERCURY;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> registry) {
        registry.getRegistry().registerAll(
                new ItemBlock(INDUSTRIAL_ALARM).setRegistryName(INDUSTRIAL_ALARM.getRegistryName()),
                new ItemBlock(HOWLER_ALARM).setRegistryName(HOWLER_ALARM.getRegistryName()),
                new ItemBlock(HOLO_DISPLAY).setRegistryName(HOLO_DISPLAY.getRegistryName()),
                new ItemBlockInfoPanel(PANEL),
                new ItemBlockInfoPanel(EXTENDER)
        );
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> registry) {
        INDUSTRIAL_ALARM = new BlockIndustrialAlarm();
        HOWLER_ALARM = new BlockHowlerAlarm();
        HOLO_DISPLAY = new BlockHolographicDisplay();
        PANEL = new BlockInformationPanel();
        EXTENDER = new BlockPanelExtender();
        GameRegistry.registerTileEntity(TileEntityHowlerAlarm.class, "HowlerAlarm");
        GameRegistry.registerTileEntity(TileHolographicDisplay.class, "HoloDisplay");
        GameRegistry.registerTileEntity(TileInformationPanel.class, "InfoPanel");
        GameRegistry.registerTileEntity(TilePanelExtender.class, "PanelExtender");

        MERCURY = new FluidMercury();

        registry.getRegistry().registerAll(INDUSTRIAL_ALARM, HOWLER_ALARM, HOLO_DISPLAY, PANEL, EXTENDER);
    }
}