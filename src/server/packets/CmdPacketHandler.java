package server.packets;

import server.packets.commands.AdministratorCommands;
import server.packets.commands.ModeratorCommands;
import server.packets.commands.OwnerCommands;
import server.packets.commands.PlayerCommands;
import server.players.Client;
import server.players.PacketType;

public class CmdPacketHandler implements PacketType {
	
		
		@Override
		public void processPacket(Client player, int packetType, int packetSize) {
				String command = player.getInStream().readString();
				System.out.println(player.playerName + " has used the command: " + command);
			switch(player.playerRights){
			case 0:
				PlayerCommands.execute(player, command);
				break;
			case 1:
				PlayerCommands.execute(player, command);
				ModeratorCommands.execute(player, command);
				break;
			case 2:
				PlayerCommands.execute(player, command);
				ModeratorCommands.execute(player, command);
				AdministratorCommands.execute(player, command);
				break;
			case 3:
				PlayerCommands.execute(player, command);
				ModeratorCommands.execute(player, command);
				AdministratorCommands.execute(player, command);
				OwnerCommands.execute(player, command);
				break;
			}
		}

		

	}


