package server.content.skills.impl;

import server.Config;
import server.content.skills.SkillHandler;
import server.items.ItemAssistant;
import server.objects.Object;
import server.players.Client;
import server.players.Player;
import server.util.CycleEvent;
import server.util.CycleEventContainer;
import server.util.CycleEventHandler;
import server.util.Misc;

/**
 * @author Acquittal - good system
 **/

public class Mining extends SkillHandler {

	public static void mineEss(final Client c, final int object) {
	c.turnPlayerTo(c.objectX, c.objectY);
		if(!noInventorySpace(c, "mining")) {
			resetMining(c);
			return;
		}
		if(!hasPickaxe(c)) {
			c.sendMessage("You need a pickaxe of your level to start mining.");
			return;
		}
		if(c.playerSkilling[14]) {
			return;
		}

		c.playerSkilling[14] = true;
		c.stopPlayerSkill = true;
		c.startAnimation(getAnimation(c));
		CycleEventHandler.addEvent(5, c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
			c.turnPlayerTo(c.objectX, c.objectY);
				c.getItems().addItem(1436, 1);
				c.getItems();
				c.sendMessage("You manage to mine some "+ ItemAssistant.getItemName(1436).toLowerCase()+".");
				
				c.getPA().addSkillXP(5 * Config.MINING_EXPERIENCE, Player.playerMining);
				
				c.startAnimation(getAnimation(c));
				if(!hasPickaxe(c)) {
					c.sendMessage("You need a pickaxe to mine this rock.");
					resetMining(c);
					container.stop();
				}
				if(!c.stopPlayerSkill) {
					resetMining(c);
					container.stop();
				}
				if(!noInventorySpace(c, "mining")) {
					resetMining(c);
					container.stop();
				}
			}
			@Override
			public void stop() {

			}
		}, 2);
	}

	public static void attemptData(final Client c, final int object, final int obX, final int obY) {
	c.turnPlayerTo(c.objectX, c.objectY);
		if(!noInventorySpace(c, "mining")) {
			resetMining(c);
			return;
		}
		if(!hasRequiredLevel(c, 14, getLevelReq(c, object), "mining", "mine here")) {
			return;
		}
		if(!hasRequiredLevel(c, 14, getLevelReqPickaxe(c), "mining", "use this pickaxe")) {
			return;
		}
		if(!hasPickaxe(c)) {
			c.sendMessage("You need a pickaxe to mine this rock.");
			return;
		}
		c.sendMessage("You swing your pickaxe at the rock.");
		c.playerSkilling[14] = true;
		c.stopPlayerSkill = true;
		c.startAnimation(getAnimation(c));
		for(int i = 0; i < data.length; i++) {
			if(object == data[i][0]) {
				c.playerSkillProp[14][0] = data[i][1];
				c.playerSkillProp[14][1] = data[i][3];
				c.startAnimation(getAnimation(c));
				CycleEventHandler.addEvent(5, c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if(c.playerSkillProp[14][0] > 0) {
							c.getItems().addItem(c.playerSkillProp[14][0], 1);
							c.getItems();
							c.sendMessage("You manage to mine some "+ ItemAssistant.getItemName(c.playerSkillProp[14][0]).toLowerCase()+".");
							c.startAnimation(getAnimation(c));
						}
						if(c.playerSkillProp[14][1] > 0) {
							c.getPA().addSkillXP(c.playerSkillProp[14][1] * Config.MINING_EXPERIENCE, Player.playerMining);
							new Object(451, obX, obY, c.heightLevel, 0, 10, 451, -1, 0);
						}
						if(!hasPickaxe(c)) {
							c.sendMessage("You need a pickaxe to mine this rock.");
							resetMining(c);
							container.stop();
						}
						if(!c.stopPlayerSkill) {
							resetMining(c);
							container.stop();
						}
						if(!noInventorySpace(c, "mining")) {
							resetMining(c);
							container.stop();
						}

						resetMining(c);
						container.stop();
					}
					@Override
					public void stop() {
					}
				}, getTimer(c, object));
				CycleEventHandler.addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						new Object(object, obX, obY, c.heightLevel, 0, 10, object, -1, 0);
						container.stop();
					}
					@Override
					public void stop() {
					}
				}, getTimer(c, object) + getRespawnTime(c, object));
				CycleEventHandler.addEvent(5, c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if(c.playerSkilling[14]) {
							c.startAnimation(getAnimation(c));
							//c.startAnimation(625);
						}
						if(!c.stopPlayerSkill || !c.playerSkilling[14]) {
							resetMining(c);
							container.stop();
						}
					}
					@Override
					public void stop() {
					}
				}, 15);
			}
		}
	}
	
	public static void attemptDataGem(final Client c, final int object, final int obX, final int obY) {
		c.turnPlayerTo(c.objectX, c.objectY);
			if(!noInventorySpace(c, "mining")) {
				resetMining(c);
				return;
			}
			if(!hasRequiredLevel(c, 14, 40, "mining", "mine here")) {
				return;
			}
			if(!hasPickaxe(c)) {
				c.sendMessage("You need a pickaxe to mine this rock.");
				return;
			}
			c.sendMessage("You swing your pickaxe at the rock.");
			c.playerSkilling[14] = true;
			c.stopPlayerSkill = true;
			c.startAnimation(getAnimation(c));
					c.startAnimation(getAnimation(c));
					CycleEventHandler.addEvent(5, c, new CycleEvent() {
						@Override
						public void execute(CycleEventContainer container) {
							if(object > 0) {
								c.getItems().addItem(c.getPA().randomGems(), 1);
								c.sendMessage("You manage to mine a gem.");
								c.startAnimation(getAnimation(c));
							}
							if(object > 0) {
								c.getPA().addSkillXP(65 * Config.MINING_EXPERIENCE, Player.playerMining);
								new Object(451, obX, obY, c.heightLevel, 0, 10, 451, -1, 0);
							}
							if(!hasPickaxe(c)) {
								c.sendMessage("You need a pickaxe to mine this rock.");
								resetMining(c);
								container.stop();
							}
							if(!c.stopPlayerSkill) {
								resetMining(c);
								container.stop();
							}
							if(!noInventorySpace(c, "mining")) {
								resetMining(c);
								container.stop();
							}

							resetMining(c);
							container.stop();
						}
						@Override
						public void stop() {
						}
					}, 20);
					CycleEventHandler.addEvent(c, new CycleEvent() {
						@Override
						public void execute(CycleEventContainer container) {
							new Object(object, obX, obY, c.heightLevel, 0, 10, object, -1, 0);
							container.stop();
						}
						@Override
						public void stop() {
						}
					}, getTimer(c, object) + 100);
					CycleEventHandler.addEvent(5, c, new CycleEvent() {
						@Override
						public void execute(CycleEventContainer container) {
							if(c.playerSkilling[14]) {
								c.startAnimation(getAnimation(c));
								//c.startAnimation(625);
							}
							if(!c.stopPlayerSkill || !c.playerSkilling[14]) {
								resetMining(c);
								container.stop();
							}
						}
						@Override
						public void stop() {
						}
					}, 15);
		}


	private static int getTimer(Client c, int i) {
		return (getMineTime(c, i) + getTime(c) + playerMiningLevel(c));
	}

	private static int getMineTime(Client c, int object) {
		for(int i = 0; i < data.length; i++) {
			if(object == data[i][0]) {
				return data[i][4];
			}
		}
		return -1;
	}

	private static int playerMiningLevel(Client c) {
		return (10 - (int)Math.floor(c.playerLevel[14] / 10));
	}

	private static int getTime(Client c) {
		for(int i = 0; i < pickaxe.length; i++) {
			if(c.getItems().playerHasItem(pickaxe[i][0]) || c.playerEquipment[3] == pickaxe[i][0]) {
				if(c.playerLevel[Player.playerMining] >= pickaxe[i][1]) {
					return pickaxe[i][2];
				}
			}
		}
		return 10;
	}

	public static void resetMining(Client c) {
		c.playerSkilling[14] = false;
		c.stopPlayerSkill = false;
		for(int i = 0; i < 2; i++) {
			c.playerSkillProp[14][i] = -1;
		}
		c.startAnimation(65535);
		//CycleEventHandler.stopEvents(c, 5);
	}

	public static boolean miningRocks(Client c, int object) {
		for(int i = 0; i < data.length; i++) {
			if(object == data[i][0]) {
				return true;
			}
		}
		return false;
	}

	private static int getRespawnTime(Client c, int object) {
		for(int i = 0; i < data.length; i++) {
			if(object == data[i][0]) {
				return data[i][5];
			}
		}
		return -1;
	}

	private static int getLevelReq(Client c, int object) {
		for(int i = 0; i < data.length; i++) {
			if(object == data[i][0]) {
				return data[i][2];
			}
		}
		return -1;
	}
	
	private static int getLevelReqPickaxe(Client c) {
		for(int i = 0; i < pickaxe.length; i++) {
			if(c.getItems().playerHasItem(pickaxe[i][0])) {
				return pickaxe[i][1];
			}
		}
		return -1;
	}

	public static boolean hasPickaxe(Client c) {
		for(int i = 0; i < animation.length; i++) {
			if(c.getItems().playerHasItem(animation[i][0]) || c.playerEquipment[3] == animation[i][0]) {
				return true;
			}
		}
		return false;
	}

	private static int getAnimation(Client c) {
		for(int i = 0; i < animation.length; i++) {
			if(c.getItems().playerHasItem(animation[i][0]) || c.playerEquipment[3] == animation[i][0]) {
				return animation[i][1];
			}
		}
		return -1;
	}

	private static int[][] animation = {
		{1275, 624}, {1271, 628}, {1273, 629}, {1269, 627},
		{1267, 626}, {1265, 625},
	};

	private static int[][] pickaxe = {
		{1275, 41, 0}, 		//RUNE
		{1271, 31, 1},		//ADDY
		{1273, 21, 2},		//MITH
		{1269, 6, 3},		//STEEL
		{1267, 1, 3},		//IRON
		{1265, 1, 4},		//BRONZE
	};

	private static int[][] data = {
		{2091, 436, 1, 18, 1, 5},	//COPPER
		{2090, 436, 1, 18, 1, 5},	//COPPER
		{11189, 436, 1, 18, 1, 5},	//COPPER
		{11190, 436, 1, 18, 1, 5},	//COPPER
		{11191, 436, 1, 18, 1, 5},	//COPPER
		{2094, 438, 1, 18, 1, 5},	//TIN
		{2095, 438, 1, 18, 1, 5},	//TIN
		{11186, 438, 1, 18, 1, 5},	//TIN
		{11187, 438, 1, 18, 1, 5},	//TIN
		{11188, 438, 1, 18, 1, 5},	//TIN
		{2093, 440, 15, 35, 2, 5},	//IRON
		{2092, 440, 15, 35, 2, 5},	//IRON
		{2097, 453, 30, 50, 3, 8},	//COAL
		{2096, 453, 30, 50, 3, 8},	//COAL
		{2098, 444, 40, 65, 3, 10},	//GOLD
		{2099, 444, 40, 65, 3, 10},	//GOLD
		{11183, 444, 40, 65, 3, 10},	//GOLD
		{11184, 444, 40, 65, 3, 10},	//GOLD
		{11185, 444, 40, 65, 3, 10},	//GOLD
		{2103, 447, 55, 80, 5, 20},	//MITH
		{2102, 447, 55, 80, 5, 20},	//MITH
		{2104, 449, 70, 95, 7, 50},	//ADDY
		{2105, 449, 70, 95, 7, 50},	//ADDY
		{2100, 442, 20, 40, 5, 5},	//SILVER
		{2101, 442, 20, 40, 5, 5},	//SILVER
		{2106, 451, 85, 125, 40, 100},//RUNE
		{2107, 451, 85, 125, 40, 100},//RUNE
		{14859, 451, 85, 125, 40, 100},//RUNE
		{14860, 451, 85, 125, 40, 100},//RUNE
	};

	public static void prospectRock(final Client c, final String itemName) {
		c.sendMessage("You examine the rock for ores...");
		CycleEventHandler.addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
					container.stop();
			}
			@Override
			public void stop() {
				c.sendMessage("This rock contains "+itemName.toLowerCase()+".");
				}
		}, 5);
	}
	public static void prospectNothing(final Client c) {
		c.sendMessage("You examine the rock for ores...");
		CycleEventHandler.addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
					container.stop();
			}
			@Override
			public void stop() {
				c.sendMessage("There is no ore left in this rock.");
				}
		}, 5);
	}
}