package worldcontrolteam.worldcontrol.items;

import net.minecraft.item.Item;
import worldcontrolteam.worldcontrol.WorldControl;

import java.util.ArrayList;
import java.util.List;

public class WCBaseItem extends Item {

	public static final List<WCBaseItem> items = new ArrayList<>();

	public WCBaseItem(String name) {
		this.setCreativeTab(WorldControl.TAB);
		this.setRegistryName(name);
		this.setUnlocalizedName("worldcontrol." + name);

		//GameRegistry.register(this);

		items.add(this);
	}
}
