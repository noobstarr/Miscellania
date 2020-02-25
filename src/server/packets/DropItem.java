package server.packets;

import server.Config;
import server.Server;
import server.content.minigames.CastleWars;
import server.content.npc.Pets;
import server.players.Client;
import server.players.PacketType;

/**
 * Drop Item
 **/
public class DropItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		if (c.newPlayer)
			return;
		int itemId = c.getInStream().readUnsignedWordA();
		c.getInStream().readUnsignedByte();
		c.getInStream().readUnsignedByte();
		int slot = c.getInStream().readUnsignedWordA();
		if(c.arenas()) {
			c.sendMessage("You can't drop items inside the arena!");
			return;
		}
		
		if(c.inTrade || c.isBanking) {
			c.sendMessage("You can't drop items while trading or banking!");
			return;
		}
		if(c.isDead) {
			return;
		}

		boolean droppable = true;
		for (int i : Config.UNDROPPABLE_ITEMS) {
			if (i == itemId) {
				droppable = false;
				break;
			}
		}
		
		if(itemId == 4045) {
			int explosiveHit = 15;
			c.startAnimation(827);
			c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
			c.handleHitMask(explosiveHit);
			c.dealDamage(explosiveHit);
			c.getPA().refreshSkill(3);
			c.forcedText = "Ow! That really hurt!";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
		}
		
		if(c.playerItemsN[slot] != 0 && itemId != -1 && c.playerItems[slot] == itemId + 1) {
			if(droppable) {
				if (c.underAttackBy > 0) {
					if (c.getShops().getItemShopValue(itemId) > 1000) {
						c.sendMessage("You may not drop items worth more than 1000 while in combat.");
						return;
					}
				}
				Server.itemHandler.createGroundItem(c, itemId, c.getX(), c.getY(), c.playerItemsN[slot], c.getId());
				c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
			} else {
				
				for (int pets = 0; pets < Pets.Pets.length; pets++) {
					if (itemId == Pets.Pets[pets][1]) {
						Server.npcHandler.spawnNpc3(c, Pets.summonItemId(itemId),
								c.absX, c.absY - 1, c.heightLevel, 0, 120, 25, 200,
								200, false, false, true);
						c.getItemAssistant().deleteItem(itemId, slot,
								c.playerItemsN[slot]);
						c.hasNpc = true;
						c.getPlayerAssistant().followPlayer();
						c.sendMessage("You drop your Kitten.");
						return;
						
					} 
						
				}
				c.sendMessage("This item cannot be dropped.");
			}
		}
		
		switch (itemId) {
		
		}
		
	}
}
