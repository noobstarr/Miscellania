package server.packets;

import server.Config;
import server.Server;
import server.content.sound.Music;
import server.players.Client;
import server.players.PacketType;

/**
 * Change Regions
 */
public class ChangeRegions implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		if (c.newPlayer)
			return;
			if (Config.SOUND)
				Music.playMusic(c);
		Server.objectManager.loadObjects(c);
		Server.itemHandler.reloadItems(c);
		
		c.getPA().castleWarsObjects();
		
		c.saveFile = true;
		if(c.skullTimer > 0) {
			c.isSkulled = true;	
			c.headIconPk = 0;
			c.getPA().requestUpdates();
		}

	}
		
}
