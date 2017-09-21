package worldcontrolteam.worldcontrol.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import worldcontrolteam.worldcontrol.api.card.CardState;
import worldcontrolteam.worldcontrol.api.card.StringWrapper;
import worldcontrolteam.worldcontrol.utils.WCUtility;

import javax.annotation.Nullable;
import java.util.List;

public class ItemCardText extends ItemBaseCard {

    public ItemCardText() {
        super("text_card");
    }

    @Override
    public CardState update(World world, ItemStack card) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("center-text", "Custom Text Goes Here");
        card.setTagCompound(tag);
        return CardState.OK;
    }

    @Override
    public List<StringWrapper> getStringData(List<StringWrapper> list, int displaySettings, ItemStack card, boolean showLabels) {
        StringWrapper text = new StringWrapper();
        text.textCenter = card.getTagCompound().getString("center-text");
        list.add(text);
        return list;
    }

    @Override
    public List<String> getGuiData() {
        return null;
    }

    @Override
    public int getCardColor() {
        return WCUtility.GREEN;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

    }
}
