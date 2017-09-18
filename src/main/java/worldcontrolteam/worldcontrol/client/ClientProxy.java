package worldcontrolteam.worldcontrol.client;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import worldcontrolteam.worldcontrol.CommonProxy;
import worldcontrolteam.worldcontrol.api.card.IProviderCard;
import worldcontrolteam.worldcontrol.init.WCBlocks;
import worldcontrolteam.worldcontrol.init.WCItems;
import worldcontrolteam.worldcontrol.inventory.InventoryItem;
import worldcontrolteam.worldcontrol.items.WCBaseItem;
import worldcontrolteam.worldcontrol.tileentity.TileEntityHowlerAlarm;
import worldcontrolteam.worldcontrol.utils.Platform;

import java.lang.reflect.Field;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	@Override
	public void preinit(FMLPreInitializationEvent event){
		AlarmAudioLoader.checkAndCreateFolders(event.getModConfigurationDirectory());
		MinecraftForge.EVENT_BUS.register(this);
		((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(new AlarmAudioLoader.TextureSetting());
	}

	@SubscribeEvent
	public void modelEvent(ModelRegistryEvent event) {
		registerWithMapper(WCBlocks.INDUSTRIAL_ALARM);
		registerWithMapper(WCBlocks.HOWLER_ALARM);

		for(WCBaseItem item : WCBaseItem.items)
			registerItemModel(item);
	}

	private void registerItemModel(Item item) {
		NonNullList<ItemStack> list = NonNullList.create();
		item.getSubItems(CreativeTabs.SEARCH, list);
		for (ItemStack stack : list) {
			ModelLoader.setCustomModelResourceLocation(item, stack.getMetadata(), new ModelResourceLocation(item.getRegistryName().toString()));
		}
	}

	private void registerWithMapper(Block block) {
		if (block != null) {
			final ResourceLocation resourcePath = block.getRegistryName();
			ModelLoader.setCustomStateMapper(block, new DefaultStateMapper() {
				@Override
				protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
					return new ModelResourceLocation(resourcePath, getPropertyString(state.getProperties()));
				}
			});

			NonNullList<ItemStack> subBlocks = NonNullList.create();
			block.getSubBlocks(null, subBlocks);

			for (ItemStack stack : subBlocks) {
				IBlockState state = block.getStateFromMeta(stack.getMetadata());
				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), stack.getMetadata(), new ModelResourceLocation(resourcePath, Platform.getPropertyString(state.getProperties())));
			}
		}
	}

	@Override
	public void init() {
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) -> {
			if (tintIndex == 1) {
				InventoryItem inv = new InventoryItem(stack);
				if (inv.getStackInSlot(0) != null)
					if (inv.getStackInSlot(0).getItem() instanceof IProviderCard)
						return ((IProviderCard) inv.getStackInSlot(0).getItem()).getCardColor();
			}
			return -1;
		}, WCItems.REMOTE_PANEL);
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((state, world, pos, tintIndex) -> {
			if (world != null && pos != null) {
				TileEntity tile = world.getTileEntity(pos);
				if (tile instanceof TileEntityHowlerAlarm) {
					return ((TileEntityHowlerAlarm) tile).getColor();
				}
			}
			return 16777215;
		}, WCBlocks.HOWLER_ALARM);

	}

	public void registerItemTextures(){

		registerBlockTextures();
	}

	public static void registerBlockTextures() {
		try{
			for(Field field : WCBlocks.class.getDeclaredFields()){
				if(field.get(null) instanceof Block){
					Item itemB = Item.REGISTRY.getObject(((Block) field.get(null)).getRegistryName());
					ModelLoader.setCustomModelResourceLocation(itemB, 0, new ModelResourceLocation(((Block) field.get(null)).getRegistryName(), "inventory"));

				}
			}
		}catch (Exception e){

		}
	}
}
