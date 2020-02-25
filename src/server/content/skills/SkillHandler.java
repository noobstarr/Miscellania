package server.content.skills;

import server.Config;
import server.items.ItemAssistant;
import server.players.Client;

public class SkillHandler {

	public static final int WOODCUTTING_XP = Config.WOODCUTTING_EXPERIENCE;

	public static final boolean view190 = true;

	public static String getLine(Client c) {
		return ("\\n\\n\\n\\n\\n");
	}
	
	
	
	
	public static void resetSkills(Client c) {// call when walking, dropping,
		// picking up, leveling up
if (c.playerSkilling[10]) {// fishing
Fishing.resetFishing(c);
} else if (c.isMining) {// mining
Mining.resetMining(c);
} else if (c.playerIsFletching == true) {// fletching
c.playerIsFletching = false;
} else if (c.playerIsCooking) {// cooking
Cooking.resetCooking(c);
} else if (c.isSmithing) {// smithing
c.isSmithing = false;
} else if (c.isPotionMaking) {// herblore
Herblore.resetHerblore(c);
} else if (c.isWc) {// woodcutting
Woodcutting.stopWoodcutting(c);
}
}
	
	

	public static boolean noInventorySpace(Client c, String skill) {
		if (c.getItems().freeSlots() == 0) {
			c.sendMessage("You haven't got enough inventory space to continue "+skill+"!");
			c.getPA().sendStatement("You haven't got enough inventory space to continue "+skill+"!");
			return false;
		}
		return true;
	}
	
	public static void send1Item(Client c, int itemId, boolean view190) {
		c.getPA().sendFrame246(1746, view190 ? 190 : 150, itemId);
		c.getPA().sendFrame126(getLine(c) + "" + ItemAssistant.getItemName(itemId) + "", 2799);
		c.getPA().sendFrame164(4429);
	}

	public static boolean hasRequiredLevel(Client c, int id, int lvlReq, String skill, String event) {
		if(c.playerLevel[id] < lvlReq) {
			c.sendMessage("You haven't got high enough "+skill+" level to "+event+"");
			c.sendMessage("You at least need the "+skill+" level of "+ lvlReq +".");
			c.getPA().sendStatement("You haven't got high enough "+skill+" level to "+event+"!");
			return false;
		}
		return true;
	}

	public static void deleteTime(Client c) {
		c.doAmount--;
	}
}