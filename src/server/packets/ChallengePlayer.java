package server.packets;

import server.Server;
import server.players.Client;
import server.players.PacketType;

/**
 * Challenge Player
 **/
public class ChallengePlayer implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {		
		if (c.newPlayer)
			return;
		if(c.gameMode == 1) {
			c.sendMessage("You can't duel as an ironman!");
			return;
		}
		if(c.gameMode == 2) {
			c.sendMessage("You can't trade as an ultimate ironman!");
			return;
		}
		switch(packetType) {
			case 128:
			int answerPlayer = c.getInStream().readUnsignedWord();
			if(Server.playerHandler.players[answerPlayer] == null) {
				return;
			}			
			
			if(c.arenas() || c.duelStatus == 5) {
				c.sendMessage("You can't challenge inside the arena!");
				return;
			}

			c.getTradeAndDuel().requestDuel(answerPlayer);
			break;
		}		
	}	
}
