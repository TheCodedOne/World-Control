package worldcontrolteam.worldcontrol.crossmod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import worldcontrolteam.worldcontrol.api.core.ModuleBase;
import worldcontrolteam.worldcontrol.crossmod.industrialcraft2.IC2Module;
import worldcontrolteam.worldcontrol.crossmod.tesla.TeslaModule;

import java.util.ArrayList;

public class Modules {

    private static ArrayList<Class<? extends ModuleBase>> modules = new ArrayList<Class<? extends ModuleBase>>();

    public Modules() {
        modules.add(IC2Module.class);
        modules.add(TeslaModule.class);
    }

    public static void removeModule(Class<? extends ModuleBase> moduleBase) {
        modules.remove(moduleBase);
    }

    public void preInit() {
        for (Class<? extends ModuleBase> mod : modules)
            try {
                ModuleBase moz = mod.newInstance();
                if (Loader.isModLoaded(moz.modID()))
                    moz.preInit();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
    }

    public void init() {
        for (Class<? extends ModuleBase> mod : modules)
            try {
                ModuleBase moz = mod.newInstance();
                if (Loader.isModLoaded(moz.modID()))
                    moz.init();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
    }

    public void postInit() {
        for (Class<? extends ModuleBase> mod : modules)
            try {
                ModuleBase moz = mod.newInstance();
                if (Loader.isModLoaded(moz.modID()))
                    moz.postInit();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
    }

    public Object guiHandlerServer(int ID, EntityPlayer player, World world, int x, int y, int z) {
        for (Class<? extends ModuleBase> mod : modules)
            try {
                ModuleBase moz = mod.newInstance();
                if (moz.handleServerGUI(ID, player, world, x, y, z) != null)
                    return moz.handleServerGUI(ID, player, world, x, y, z);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        return null;
    }

    public Object guiHandlerClient(int ID, EntityPlayer player, World world, int x, int y, int z) {
        for (Class<? extends ModuleBase> mod : modules)
            try {
                ModuleBase moz = mod.newInstance();
                if (moz.handleClientGUI(ID, player, world, x, y, z) != null)
                    return moz.handleClientGUI(ID, player, world, x, y, z);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        return null;
    }
}
