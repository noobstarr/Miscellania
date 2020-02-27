package server.content.items;

import server.Config;
import server.content.items.CraftingData.cutGemData;
import server.items.ItemAssistant;
import server.players.Client;
import server.util.CycleEvent;
import server.util.CycleEventContainer;
import server.util.CycleEventHandler;

public class GemCutting extends CraftingData {
	
	public static void cutGem(final Client c, final int itemUsed, final int usedWith) {
		if (c.playerIsCrafting == true) {
			return;
		}
		final int itemId = (itemUsed == 1755 ? usedWith : itemUsed);
		for (final cutGemData g : cutGemData.values()) {
			if (itemId == g.getUncut()) {
				if (c.playerLevel[12] < g.getLevel()) {
					c.sendMessage("You need a crafting level of "+ g.getLevel() +" to cut this gem.");
					return;
				}
				if (!c.getItems().playerHasItem(itemId)) {
					return;
				}
				c.playerIsCrafting = true;
				c.startAnimation(g.getAnimation());
				CycleEventHandler.addEvent(3, c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if (c.playerIsCrafting == true) {
							if (c.getItems().playerHasItem(itemId)) {
								c.getItems().deleteItem(itemId, 1);
								c.getItems().addItem(g.getCut(), 1);	
								c.getPA().addSkillXP((int) g.getXP()*Config.CRAFTING_EXPERIENCE, 12);
								c.sendMessage("You cut the "+ ItemAssistant.getItemName(itemId).toLowerCase() +".");
								c.startAnimation(g.getAnimation());
							} else {
								container.stop();
							}
						} else {
							container.stop();
						}
					}
					@Override
					public void stop() {
						c.playerIsCrafting = false;
					}
				}, 4);
			}
		}
	}
}
