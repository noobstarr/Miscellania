package server.packets;

import server.players.Client;
import server.players.PacketType;

/**
 * Move Items
 **/
public class MoveItems implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		if (c.newPlayer)
			return;
		int somejunk = c.getInStream().readUnsignedWordA(); //junk
		int itemFrom =  c.getInStream().readUnsignedWordA();// slot1
		int itemTo = (c.getInStream().readUnsignedWordA() -128);// slot2
		//c.sendMessage("junk: " + somejunk);
		c.getItems().moveItems(itemFrom, itemTo, somejunk);
	}
}
