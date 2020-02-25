package server.packets;

import server.content.skills.Cooking;
import server.items.UseItem;
import server.players.Client;
import server.players.PacketType;

public class ItemOnObject implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		if (c.newPlayer)
			return;
		/*
		 * a = ?
		 * b = ?
		 */
		
		int a = c.getInStream().readUnsignedWord();
		int objectId = c.getInStream().readSignedWordBigEndian();
		int objectY = c.getInStream().readSignedWordBigEndianA();
		int b = c.getInStream().readUnsignedWord();
		int objectX = c.getInStream().readSignedWordBigEndianA();
		int itemId = c.getInStream().readUnsignedWord();
		if (!c.getItemAssistant().playerHasItem(itemId, 1)) {
			return;
		}
		
		switch (objectId) {
		case 12269:
		case 2732:
		case 114:
		case 2727:
		case 385:
		case 14919:
		case 2728:
		case 9682:
			if (c.absX == 3014 && c.absY > 3235 && c.absY < 3238
			|| c.absX == 3012 && c.absY == 3239 || c.absX == 3020
			&& c.absY > 3236 && c.absY < 3239 || c.absX > 2805
			&& c.absX < 2813 || c.absY > 3437 && c.absY < 3442) {
		return;
	}
		Cooking.startCooking(c, itemId, objectId);
	
	break;
		}
		
		UseItem.ItemonObject(c, objectId, objectX, objectY, itemId);
		
	}

}
