package worldcontrolteam.worldcontrol.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import worldcontrolteam.worldcontrol.api.card.IProviderCard;
import worldcontrolteam.worldcontrol.init.WCItems;

import javax.annotation.Nonnull;

public class ContainerHoloInv extends Container {

    protected IItemHandler invenory;

    public ContainerHoloInv(InventoryPlayer inv, IItemHandler invenory) {
        this.invenory = invenory;

        //this.addSlotToContainer(new SlotFilter(this.item, 0, 177, 21));
        bindPlayerInventory(inv);
    }

    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        /*
		 * for (int i = 0; i < 3; i++) { for (int j = 0; j < 9; j++) {
		 * addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j *
		 * 18, 84 + i * 18)); } }
		 */

        for (int i = 0; i < 9; i++)
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
    }

    @Override
    public boolean canInteractWith(EntityPlayer p_75145_1_) {
        return true;
    }

    @Override
    @Nonnull
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slots = this.inventorySlots.get(slot);

        if (!slots.getStack().isEmpty())
            if (slots.getStack().getItem() == WCItems.REMOTE_PANEL)
                return ItemStack.EMPTY;

        if (slots != null && slots.getHasStack()) {
            ItemStack itemstackR = slots.getStack();
            stack = itemstackR.copy();

            if (slot == 0) {
                boolean fixed = false;
                for (int h = 1; h < 10; h++) {
                    Slot know = this.inventorySlots.get(h);
                    if (!know.getHasStack()) {
                        know.putStack(slots.getStack());
                        slots.decrStackSize(1);
                        fixed = true;
                    }
                }
                if (!fixed)
                    return ItemStack.EMPTY;
                slots.onSlotChange(itemstackR, stack);
            } else if (slots.getStack().getItem() instanceof IProviderCard && !this.inventorySlots.get(0).getHasStack()) {
                this.inventorySlots.get(0).putStack(itemstackR);
                slots.decrStackSize(1);
                slots.onSlotChange(itemstackR, stack);
                this.inventorySlots.get(0).onSlotChanged();
            } else return ItemStack.EMPTY;
        }
        return stack;
    }

    @Override
    @Nonnull
    public ItemStack slotClick(int slot, int dragType, ClickType click, EntityPlayer player) {
        if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getStack() == player.getHeldItemMainhand())
            return ItemStack.EMPTY;
        return super.slotClick(slot, dragType, click, player);
    }
}
