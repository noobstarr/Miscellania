package server.packets;

import server.Server;
import server.content.items.AdvancedMysteryBox;
import server.content.items.Pouches;
import server.content.minigames.Barrows;
import server.content.minigames.CastleWars;
import server.content.skills.impl.Herblore;
import server.content.skills.impl.Prayer;
import server.players.Client;
import server.players.PacketType;
import server.players.Player;
import server.util.Misc;

/**
 * Clicking an item, bury bone, eat food etc
 **/
public class ClickItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		if (c.newPlayer)
			return;
		int junk = c.getInStream().readSignedWordBigEndianA();
		int itemSlot = c.getInStream().readUnsignedWordA();
		int itemId = c.getInStream().readUnsignedWordBigEndian();
		if (itemId != c.playerItems[itemSlot] - 1) {
			return;
		}
		if(!c.getItemAssistant().playerHasItem(itemId, 1)) {
			return;
		}
		
		if(Herblore.isHerb(itemId)) {
			Herblore.cleanHerb(c, itemId, itemSlot);
			return;
		}
		if (c.getFood().isFood(itemId)) {
			c.getFood().eat(itemId,itemSlot);
		}
		if (Prayer.IsABone(c, itemId)) {
			Prayer.buryBone(c, itemId);
		}
		if (c.getPotions().isPotion(itemId)) {
			c.getPotions().handlePotion(itemId,itemSlot);
		}
		//castlewars
		if(itemId == 4053) {
			if((Player.saraBarricades == 3 && CastleWars.getTeamNumber(c) == 1) || (Player.zammyBarricades == 3 && CastleWars.getTeamNumber(c) == 2)) {
				c.sendMessage("Your team can only setup 3 barricades!");
				return;
			}
			c.getItems().deleteItem(4053,c.getItems().getItemSlot(4053),1);
			c.startAnimation(827);
			Server.npcHandler.spawnBarricade(1532, c.absX, c.absY, c.heightLevel, 0,200,0,0,100,CastleWars.getTeamNumber(c));
			c.sendMessage("You setup a barricade.");
			if(CastleWars.getTeamNumber(c) == 1)
				Player.saraBarricades++;
			if(CastleWars.getTeamNumber(c) == 2)
				Player.zammyBarricades++;
		}
		switch (itemId) {
		
		case 6199:
        	if (c.getItems().playerHasItem(6199)) {
        	AdvancedMysteryBox.Open(itemId, c, itemSlot);
        	return;
}
		case 6865:
			c.startAnimation(3003);
			c.gfx0(511);
			break;
		case 6866:
			c.startAnimation(3003);
			c.gfx0(515);
			break;
		case 6867:
			c.startAnimation(3003);
			c.gfx0(507);
			break;
		case 2528:
			c.getDH().sendDialogues(500, -1);
			break;
		case 4079:
			c.startAnimation(1457);
		break;
		
			
		case 5070:
		case 5071:
		case 5072:
			c.getItems().deleteItem(itemId, c.getItems().getItemSlot(itemId), 1);
			c.getItems().addItem(5075, 1);
			c.getItems().addItem(itemId+6, 1);
			break;

		case 5073:
			c.getItems().deleteItem(itemId, c.getItems().getItemSlot(itemId), 1);
			c.getItems().addItem(5075, 1);
			c.getItems().addItem(5304, Misc.random(100));
			break;

		case 5074:
			int[] rings = { 1635, 1637, 1639, 1641, 1643, 1645 };
			c.getItems().deleteItem(itemId, c.getItems().getItemSlot(itemId), 1);
			c.getItems().addItem(5075, 1);
			if(Misc.random(10) == 0) {
				c.getItems().addItem(6564, 1);
			} else {
				c.getItems().addItem(rings[Misc.random(rings.length)], 1);
			}
			break;
			
		case 5509:
		case 5510:
		case 5511:
		case 5512:
		case 5513:
		case 5514:
			Pouches.fillPouch(c, itemId);
			break;
			
		case 4155:
			c.getDH().sendDialogues(784, 0);
			break;
			
		case 4447:
			c.rubbedLamp = true;
			c.getDH().sendDialogues(554, -1);
			break;
			
		case 952:
			
			if(c.inArea(3553, 3301, 3561, 3294)) {
				//c.teleTimer = 3;
				//c.newLocation = 1;
				Barrows.digToBrother(c);
			} else if(c.inArea(3550, 3287, 3557, 3278)) {
				//c.teleTimer = 3;
				//c.newLocation = 2;
				Barrows.digToBrother(c);
			} else if(c.inArea(3561, 3292, 3568, 3285)) {
				//c.teleTimer = 3;
				//c.newLocation = 3;
				Barrows.digToBrother(c);
			} else if(c.inArea(3570, 3302, 3579, 3293)) {
				//c.teleTimer = 3;
				//c.newLocation = 4;
				Barrows.digToBrother(c);
			} else if(c.inArea(3571, 3285, 3582, 3278)) {
				//c.teleTimer = 3;
				//c.newLocation = 5;
				Barrows.digToBrother(c);
			} else if(c.inArea(3562, 3279, 3569, 3273)) {
				//c.teleTimer = 3;
				//c.newLocation = 6;
				Barrows.digToBrother(c);
			} else if(c.inArea(2835, 3336, 2835, 3336)) {
				//c.teleTimer = 3;
				//c.newLocation = 7;
				Barrows.digToBrother(c);
			} else if(c.inArea(2834, 3336, 2834, 3336)) {
				//c.teleTimer = 3;
				//c.newLocation = 8;
				Barrows.digToBrother(c);
			}
			break;
		}
		
		
		if (itemId == 952) {
			if(c.inArea(3553, 3301, 3561, 3294)) {
				c.teleTimer = 3;
				c.newLocation = 1;
			} else if(c.inArea(3550, 3287, 3557, 3278)) {
				c.teleTimer = 3;
				c.newLocation = 2;
			} else if(c.inArea(3561, 3292, 3568, 3285)) {
				c.teleTimer = 3;
				c.newLocation = 3;
			} else if(c.inArea(3570, 3302, 3579, 3293)) {
				c.teleTimer = 3;
				c.newLocation = 4;
			} else if(c.inArea(3571, 3285, 3582, 3278)) {
				c.teleTimer = 3;
				c.newLocation = 5;
			} else if(c.inArea(3562, 3279, 3569, 3273)) {
				c.teleTimer = 3;
				c.newLocation = 6;
			}
		}
	}

}
