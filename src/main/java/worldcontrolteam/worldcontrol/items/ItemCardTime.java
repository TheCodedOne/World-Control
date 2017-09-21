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

public class ItemCardTime extends ItemBaseCard {

    public ItemCardTime() {
        super("time_card");
    }

    @Override
    public CardState update(World world, ItemStack card) {
        int time = (int) ((world.getWorldTime() + 6000) % 24000);
        int hours = time / 1000;
        int minutes = time % 1000 * 6 / 100;
        String suffix = "";

        // if ((displaySettings & MODE_24H) == 0) {
        suffix = hours < 12 ? "AM" : "PM"; // TODO: localize?
        hours %= 12;
        if (hours == 0)
            hours += 12;
        // }
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("time", String.format("%02d:%02d %s", hours, minutes, suffix));
        card.setTagCompound(tag);
        return CardState.OK;
    }

    @Override
    public List<StringWrapper> getStringData(List<StringWrapper> list, int displaySettings, ItemStack card, boolean showLabels) {
        StringWrapper text = new StringWrapper();
        //text.textLeft = WCUtility.translateFormatted("time_card.time", card.getTagCompound().getString("time"));
        text.textCenter = "Time: " + card.getTagCompound().getString("time");
        list.add(text);
        return list;
    }

    @Override
    public List<String> getGuiData() {
        return null;
    }

    @Override
    public int getCardColor() {
        return WCUtility.YELLOW;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

    }
}
