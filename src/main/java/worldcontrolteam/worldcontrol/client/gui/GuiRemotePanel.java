package worldcontrolteam.worldcontrol.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import worldcontrolteam.worldcontrol.api.card.IProviderCard;
import worldcontrolteam.worldcontrol.api.card.StringWrapper;
import worldcontrolteam.worldcontrol.inventory.InventoryItem;
import worldcontrolteam.worldcontrol.inventory.container.ContainerRemotePanel;
import worldcontrolteam.worldcontrol.network.ChannelHandler;
import worldcontrolteam.worldcontrol.network.messages.PacketServerRemotePanel;
import worldcontrolteam.worldcontrol.utils.WCUtility;

import java.util.LinkedList;
import java.util.List;

public class GuiRemotePanel extends GuiContainer {

	private InventoryItem inv;
	private EntityPlayer e;

	public GuiRemotePanel(InventoryPlayer inv, ItemStack stack, InventoryItem inventoryItem, EntityPlayer player) {
		super(new ContainerRemotePanel(inv, stack, inventoryItem));
		this.inv = inventoryItem;
		this.e = player;
	}

	@Override
	public void initGui(){
		super.initGui();
		this.xSize += 50;
		this.mc.player.openContainer = this.inventorySlots;
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX,mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3){
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.renderEngine.bindTexture(new ResourceLocation("worldcontrol", "textures/gui/gui_remote_panel.png"));
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, 204, ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2){
		List<StringWrapper> joinedData = new LinkedList<>();
		boolean anyCardFound = true;
		InventoryItem itemInv = new InventoryItem(e.getHeldItemMainhand());

		if(!inv.getStackInSlot(0).isEmpty() && !itemInv.getStackInSlot(0).isEmpty() && itemInv.getStackInSlot(0).getItem() instanceof IProviderCard) {
			IProviderCard card = (IProviderCard) inv.getStackInSlot(0).getItem();
			joinedData.clear();
			ChannelHandler.network.sendToServer(new PacketServerRemotePanel(inv.getStackInSlot(0)));

			if (itemInv.getStackInSlot(0).hasTagCompound())
				joinedData = card.getStringData(new LinkedList<>(), 0, itemInv.getStackInSlot(0), true);
			else joinedData = getRemoteCustomMSG();

			drawCardStuff(anyCardFound, joinedData);
		}
	}

	private List<StringWrapper> getRemoteCustomMSG(){
		StringWrapper line = new StringWrapper();
		List<StringWrapper> result = new LinkedList<StringWrapper>();
		line.textCenter = I18n.format("nc.msg.notValid");
		result.add(line);
		line = new StringWrapper();
		line.textCenter = I18n.format("nc.msg.notValid2");
		result.add(line);
		line = new StringWrapper();
		line.textCenter = "";
		result.add(line);
		line = new StringWrapper();
		line.textCenter = I18n.format("nc.msg.notValid3");
		result.add(line);
		return result;
	}

	private void drawCardStuff(Boolean anyCardFound, List<StringWrapper> joinedData){
		if(!anyCardFound){
			WCUtility.error("This should never happen. If you see this report immediately to NC2 repo. Include GuiRemoteMonitorError-123 in the report!");
			return;
		}

		int row = 0;
		for(StringWrapper panelString : joinedData){
			if(panelString.textLeft != null)
				fontRenderer.drawString(panelString.textLeft, 9, row * 10 + 20, 0x06aee4);

			if(panelString.textCenter != null)
				fontRenderer.drawString(panelString.textCenter, (168 - fontRenderer.getStringWidth(panelString.textCenter)) / 2, row * 10 + 20, 0x06aee4);

			if(panelString.textRight != null)
				this.fontRenderer.drawString(panelString.textRight, 168 - fontRenderer.getStringWidth(panelString.textRight), (row - 1) * 10 + 20, 0x06aee4);

			row++;
		}
	}

	@Override
	public void updateScreen(){
		super.updateScreen();

		if(this.e.getHeldItemMainhand().isEmpty())
			this.mc.player.closeScreen();
	}
}
