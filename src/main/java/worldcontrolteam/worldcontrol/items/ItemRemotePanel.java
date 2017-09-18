package worldcontrolteam.worldcontrol.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import worldcontrolteam.worldcontrol.WorldControl;
import worldcontrolteam.worldcontrol.inventory.InventoryItem;
import worldcontrolteam.worldcontrol.utils.GuiLib;

import javax.annotation.Nonnull;

public class ItemRemotePanel extends WCBaseItem {

	public ItemRemotePanel() {
		super("remote_panel");
		this.addPropertyOverride(new ResourceLocation("no_card"), (stack, world, entity) -> {
			InventoryItem inv = new InventoryItem(stack);
			if (inv.getStackInSlot(0).isEmpty())
				return 1;
			return 0;
		});
	}

	@Override
	@Nonnull
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player,@Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (!player.isSneaking()) {
			player.openGui(WorldControl.instance, GuiLib.REMOTE_PANEL, world, (int) player.posX, (int) player.posY, (int) player.posZ);
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 1;
	}

}