package server.packets;


import server.players.Client;
import server.players.PacketType;


public class IdleLogout implements PacketType {
	
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.logout();
	}
}