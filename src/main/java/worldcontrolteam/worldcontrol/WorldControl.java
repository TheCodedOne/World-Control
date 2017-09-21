package worldcontrolteam.worldcontrol;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import worldcontrolteam.worldcontrol.api.core.WorldControlAPI;
import worldcontrolteam.worldcontrol.api.thermometer.IHeatSeeker;
import worldcontrolteam.worldcontrol.crossmod.Modules;
import worldcontrolteam.worldcontrol.init.WCBlocks;
import worldcontrolteam.worldcontrol.init.WCItems;
import worldcontrolteam.worldcontrol.items.ItemThermometer;
import worldcontrolteam.worldcontrol.network.ChannelHandler;
import worldcontrolteam.worldcontrol.network.GuiHandler;
import worldcontrolteam.worldcontrol.utils.WCConfig;

import java.io.File;
import java.util.ArrayList;

@Mod(modid = WorldControl.MODID, version = "@VERSION@", dependencies = "required-after:ic2")
public class WorldControl {

    public static final String MODID = "worldcontrol";
    @Mod.Instance(value = "worldcontrol")
    public static WorldControl instance;
    @SidedProxy(clientSide = "worldcontrolteam.worldcontrol.client.ClientProxy", serverSide = "worldcontrolteam.worldcontrol.CommonProxy")
    public static CommonProxy PROXY;
    public static WCCreativeTab TAB = new WCCreativeTab();

    public static Side SIDE;
    public static Modules modules = new Modules();
    protected static ArrayList<IHeatSeeker> heatSeekers = new ArrayList<IHeatSeeker>();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        SIDE = event.getSide();
        WCConfig.init(new File(event.getModConfigurationDirectory(), MODID + ".cfg"));
        WorldControlAPI.init(new WCapiImpl());
        PROXY.preinit(event);

        MinecraftForge.EVENT_BUS.register(WCItems.class);
        MinecraftForge.EVENT_BUS.register(WCBlocks.class);

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        ChannelHandler.init();
        modules.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        modules.init();

        ItemThermometer.addInHeatTypes(heatSeekers);

        PROXY.init();

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        modules.postInit();
    }
}
