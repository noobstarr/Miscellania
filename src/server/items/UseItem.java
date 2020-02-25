package server.items;

import server.players.Client;
import server.players.Player;
import server.util.Misc;
import server.Config;
import server.Server;
import server.content.skills.Firemaking;
import server.content.skills.Fletching;
import server.content.skills.GemCutting;
import server.content.skills.Herblore;
import server.content.skills.JewelryMaking;
import server.content.skills.LeatherMaking;
import server.content.skills.Prayer;
import server.npcs.NPC;
import server.npcs.NPCHandler;

/**
 * @author Sanity
 * @author Ryan
 * @author Lmctruck30
 * Revised by Shawn
 * Notes by Shawn
 */

public class UseItem {

	
	/**
	 * Using items on an object.
	 * @param c
	 * @param objectID
	 * @param objectX
	 * @param objectY
	 * @param itemId
	 */
	public static void ItemonObject(Client c, int objectID, int objectX, int objectY, int itemId) {
		if (!c.getItems().playerHasItem(itemId, 1))
			return;
		switch(objectID) {
		
			case 8151:
			case 8389:
				c.getFarming().checkItemOnObject(itemId);
			break;
			case 409:
				if (Prayer.IsABone(c, itemId))
					Prayer.bonesOnAltar(c, itemId);
			break;
			case 2728:
			
		default:
			if(c.playerRights == 3)
				Misc.println("Player At Object id: "+objectID+" with Item id: "+itemId);
			break;
		}
		
	}

	
	/**
	 * Using items on items.
	 * @param c
	 * @param itemUsed
	 * @param useWith
	 */
	public static void ItemonItem(Client c, int itemUsed, int useWith) {
		//Firemaking
				if (Firemaking.playerLogs(c, itemUsed, useWith)) {
					Firemaking.grabData(c,itemUsed, useWith);
				}
				//Herblore
				if(Herblore.isIngredient(itemUsed) || Herblore.isIngredient(useWith)) {
					Herblore.setupPotion(c, itemUsed, useWith);
				} else if(Herblore.isGrindable(itemUsed) || Herblore.isGrindable(useWith)) {
					Herblore.setupGrinding(c, itemUsed, useWith);
				}
				//potionMixing
				if (ItemAssistant.getItemName(itemUsed).contains("(") && ItemAssistant.getItemName(useWith).contains("(")) {
					c.getPotMixing().mixPotion2(itemUsed, useWith);
				}
				//Crafting
				if (itemUsed == 1733 || useWith == 1733) {
					LeatherMaking.craftLeatherDialogue(c, itemUsed, useWith);
				}
				if (itemUsed == 1759 || useWith == 1759) {
					JewelryMaking.stringAmulet(c, itemUsed, useWith);
				}
				if (itemUsed == 1755 || useWith == 1755) {
					GemCutting.cutGem(c, itemUsed, useWith);
				}
				//Fletching - BOWS
				for (int i = 0; i < Fletching.ifItems.length; i++) {
					if (itemUsed == 946 && useWith == Fletching.ifItems[i][0]) {
						Fletching.openFletching(c, useWith);
					} else if (useWith == 946 && itemUsed == Fletching.ifItems[i][0]) {
						Fletching.openFletching(c, itemUsed);
					}
				}
				//Fletching BOLTS & ARROWS & TIPPING
				int[] arrows1 = {53, 52};
				int[] arrows2 = {39, 40, 41, 42, 43, 44, 314};
				for (int i = 0; i < arrows1.length; i++) {
				for (int j = 0; j < arrows2.length; j++) {
					if(itemUsed == arrows1[i] && useWith == arrows2[j] || useWith == arrows1[i] && itemUsed == arrows2[j]){
						Fletching.makeArrows(c, itemUsed, useWith); //arrow making
					}
				}
				}
				int[] bolts = {819, 820, 821, 822, 823, 824};
				for (int i = 0; i < bolts.length; i++) {
					if(itemUsed == bolts[i] && useWith == 314 || useWith == bolts[i] && itemUsed == 314){
						Fletching.makeBolts(c, itemUsed, useWith); //bolt making
					}
				}
				for (int i = 0; i < Fletching.craftingVariables.length; i++) { //bolt tipping
					if ((itemUsed == Fletching.craftingVariables[i][0] || itemUsed == Fletching.craftingVariables[i][1]) && (useWith == Fletching.craftingVariables[i][0] || useWith == Fletching.craftingVariables[i][1])) {
						Fletching.handleBoltTipping(c, itemUsed, useWith);
					}
				}
				for (int i = 0; i < Fletching.boltTips.length; i++) { //tip crafting
					if ((itemUsed == Fletching.boltTips[i][0] || itemUsed == 1755) && (useWith == Fletching.boltTips[i][0] || useWith == 1755)) {
						Fletching.handleBoltTipCrafting(c, itemUsed, useWith);
					}
				}
				//DFS making
				if ((itemUsed == 1540 && useWith == 11286) || (itemUsed == 11286 && useWith == 1540)) {
					if (c.playerLevel[Player.playerSmithing] >= 90) {
						c.getItems().deleteItem(1540, c.getItems().getItemSlot(1540), 1);
						c.getItems().deleteItem(11286, c.getItems().getItemSlot(11286), 1);
						c.getItems().addItem(11284,1);
						c.sendMessage("You combine the two materials to create a dragonfire shield.");
						c.getPA().addSkillXP(500 * Config.SMITHING_EXPERIENCE, Player.playerSmithing);
					} else {
						c.sendMessage("You need a smithing level of 90 to create a dragonfire shield.");
					}
				}
				
		if (c.getItems().isHilt(itemUsed) || c.getItems().isHilt(useWith)) {
					int hilt = c.getItems().isHilt(itemUsed) ? itemUsed : useWith;
					int blade = c.getItems().isHilt(itemUsed) ? useWith : itemUsed;
					if (blade == 11690) {
						c.getItems().makeGodsword(hilt);
					}
				}
				if (itemUsed == 2368 && useWith == 2366 || itemUsed == 2366 && useWith == 2368) {
					c.getItems().deleteItem(2368, c.getItems().getItemSlot(2368),1);
					c.getItems().deleteItem(2366, c.getItems().getItemSlot(2366),1);
					c.getItems().addItem(1187,1);
				}
				
				if (c.getItems().isHilt(itemUsed) || c.getItems().isHilt(useWith)) {
					int hilt = c.getItems().isHilt(itemUsed) ? itemUsed : useWith;
					int blade = c.getItems().isHilt(itemUsed) ? useWith : itemUsed;
					if (blade == 11690) {
						c.getItems().makeGodsword(hilt);
					}
				}
				
				/*Runecrafting Tiaras
				 * by Beanerrr for Beautiful Mistake
				 */
						if(c.getItems().playerHasItem(1448) && c.getItems().playerHasItem(5525)) {
		                    c.startAnimation(791);
		                    c.gfx100(186);
							c.getItems().deleteItem(1448, 1);
							c.getItems().deleteItem(5525, 1);
							c.getItems().addItem(5529, 1);
							c.sendMessage("You infuse the Tiara with the Talisman's magical force!");
							c.getPA().addSkillXP(95 * Config.RUNECRAFTING_EXPERIENCE, Player.playerRunecrafting);
						}
						if(c.getItems().playerHasItem(1450) && c.getItems().playerHasItem(5525)) {
		                    c.startAnimation(791);
		                    c.gfx100(186);
							c.getItems().deleteItem(1450, 1);
							c.getItems().deleteItem(5525, 1);
							c.getItems().addItem(5549, 1);
							c.sendMessage("You infuse the Tiara with the Talisman's magical force!");
							c.getPA().addSkillXP(95 * Config.RUNECRAFTING_EXPERIENCE, Player.playerRunecrafting);
						}
						if(c.getItems().playerHasItem(1444) && c.getItems().playerHasItem(5525)) {
		                    c.startAnimation(791);
		                    c.gfx100(186);
							c.getItems().deleteItem(1444, 1);
							c.getItems().deleteItem(5525, 1);
							c.getItems().addItem(5531, 1);
							c.sendMessage("You infuse the Tiara with the Talisman's magical force!");
							c.getPA().addSkillXP(95 * Config.RUNECRAFTING_EXPERIENCE, Player.playerRunecrafting);
						}
						if(c.getItems().playerHasItem(1440) && c.getItems().playerHasItem(5525)) {
		                    c.startAnimation(791);
		                    c.gfx100(186);
							c.getItems().deleteItem(1440, 1);
							c.getItems().deleteItem(5525, 1);
							c.getItems().addItem(5535, 1);
							c.sendMessage("You infuse the Tiara with the Talisman's magical force!");
							c.getPA().addSkillXP(95 * Config.RUNECRAFTING_EXPERIENCE, Player.playerRunecrafting);
						}
						if(c.getItems().playerHasItem(1442) && c.getItems().playerHasItem(5525)) {
		                    c.startAnimation(791);
		                    c.gfx100(186);
							c.getItems().deleteItem(1442, 1);
							c.getItems().deleteItem(5525, 1);
							c.getItems().addItem(5537, 1);
							c.sendMessage("You infuse the Tiara with the Talisman's magical force!");
							c.getPA().addSkillXP(95 * Config.RUNECRAFTING_EXPERIENCE, Player.playerRunecrafting);
						}
						if(c.getItems().playerHasItem(1446) && c.getItems().playerHasItem(5525)) {
		                    c.startAnimation(791);
		                    c.gfx100(186);
							c.getItems().deleteItem(1446, 1);
							c.getItems().deleteItem(5525, 1);
							c.getItems().addItem(5533, 1);
							c.sendMessage("You infuse the Tiara with the Talisman's magical force!");
							c.getPA().addSkillXP(95 * Config.RUNECRAFTING_EXPERIENCE, Player.playerRunecrafting);
						}
						if(c.getItems().playerHasItem(1454) && c.getItems().playerHasItem(5525)) {
		                    c.startAnimation(791);
		                    c.gfx100(186);
							c.getItems().deleteItem(1454, 1);
							c.getItems().deleteItem(5525, 1);
							c.getItems().addItem(5539, 1);
							c.sendMessage("You infuse the Tiara with the Talisman's magical force!");
							c.getPA().addSkillXP(95 * Config.RUNECRAFTING_EXPERIENCE, Player.playerRunecrafting);
						}
						if(c.getItems().playerHasItem(1462) && c.getItems().playerHasItem(5525)) {
		                    c.startAnimation(791);
		                    c.gfx100(186);
							c.getItems().deleteItem(1462, 1);
							c.getItems().deleteItem(5525, 1);
							c.getItems().addItem(5541, 1);
							c.sendMessage("You infuse the Tiara with the Talisman's magical force!");
							c.getPA().addSkillXP(95 * Config.RUNECRAFTING_EXPERIENCE, Player.playerRunecrafting);
						}
						if(c.getItems().playerHasItem(1452) && c.getItems().playerHasItem(5525)) {
		                    c.startAnimation(791);
		                    c.gfx100(186);
							c.getItems().deleteItem(1452, 1);
							c.getItems().deleteItem(5525, 1);
							c.getItems().addItem(5543, 1);
							c.sendMessage("You infuse the Tiara with the Talisman's magical force!");
							c.getPA().addSkillXP(95 * Config.RUNECRAFTING_EXPERIENCE, Player.playerRunecrafting);
						}
						if(c.getItems().playerHasItem(1456) && c.getItems().playerHasItem(5525)) {
		                    c.startAnimation(791);
		                    c.gfx100(186);
							c.getItems().deleteItem(1456, 1);
							c.getItems().deleteItem(5525, 1);
							c.getItems().addItem(5547, 1);
							c.sendMessage("You infuse the Tiara with the Talisman's magical force!");
							c.getPA().addSkillXP(95 * Config.RUNECRAFTING_EXPERIENCE, Player.playerRunecrafting);
						}
						if(c.getItems().playerHasItem(1458) && c.getItems().playerHasItem(5525)) {
		                    c.startAnimation(791);
		                    c.gfx100(186);
							c.getItems().deleteItem(1458, 1);
							c.getItems().deleteItem(5525, 1);
							c.getItems().addItem(5545, 1);
							c.sendMessage("You infuse the Tiara with the Talisman's magical force!");
							c.getPA().addSkillXP(95 * Config.RUNECRAFTING_EXPERIENCE, Player.playerRunecrafting);
						}
						if(c.getItems().playerHasItem(1438) && c.getItems().playerHasItem(5525)) {
		                    c.startAnimation(791);
		                    c.gfx100(186);
							c.getItems().deleteItem(1438, 1);
							c.getItems().deleteItem(5525, 1);
							c.getItems().addItem(5527, 1);
							c.sendMessage("You infuse the Tiara with the Talisman's magical force!");
							c.getPA().addSkillXP(95 * Config.RUNECRAFTING_EXPERIENCE, Player.playerRunecrafting);
						}
				/*End of Runecrafting Tiaras*/
				
				switch(itemUsed) {
					
				default:
					if(c.playerRights == 3)
						Misc.println("Player used Item id: "+itemUsed+" with Item id: "+useWith);
					break;
				}	
			}
			
			//castlewars
			public static void ItemonNpc(Client c, int itemId, int slot, int npcId) {
				NPC npc = NPCHandler.npcs[c.npcClickIndex];
				switch(itemId) {
				case 4045:
					if(npcId == 1532) {
						c.getItems().deleteItem(4045, 1);
						int damg = 200;
						npc.handleHitMask(damg);
						npc.HP -= damg;
						npc.hitUpdateRequired = true;
						npc.gfx0(346);
						c.sendMessage("You have destroyed the "+Server.npcHandler.getNpcListName(npcId));
					}
					break;
				
				default:
					if(c.playerRights == 3)
						Misc.println("Player used Item id: "+itemId+" with Npc id: "+npcId+" With Slot : "+slot+" the i is: "+c.npcClickIndex);
					break;
				}
				c.npcClickIndex = 0;
				c.clickNpcType = 0;
				
			}
		
			

	
	
	
	/**
	 * Using items on NPCs.
	 * @param c
	 * @param itemId
	 * @param npcId
	 * @param slot
	 */



}
