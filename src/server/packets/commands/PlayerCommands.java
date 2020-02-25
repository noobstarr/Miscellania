package server.packets.commands;

import server.Config;
import server.Connection;
import server.Server;
import server.content.randoms.RandomEventHandler;
import server.players.Client;
import server.players.PacketType;
import server.players.PlayerHandler;
import server.util.Misc;
import server.util.WorldMap;


/**
 * Commands
 **/
public class PlayerCommands{

	
	public static void execute(Client c, String playerCommand) {
	String type = c.playerMagicBook == 0 ? "modern" : "ancient";
        //Misc.println(c.playerName+" playerCommand: "+playerCommand);
	if (c.newPlayer)
		return;
	if(Config.SERVER_DEBUG)
		
	
		
	
			
			
			if (playerCommand.equalsIgnoreCase("help")) {
				c.sendMessage("Use ::commands or check if there are staff online with ::players");
				c.sendMessage("You can also talk to the lumbridge guide for in-game help.");
				
			}
			
			if (playerCommand.equalsIgnoreCase("commands")) {
				c.getPA().commands();
			}
			
			if (playerCommand.equalsIgnoreCase("updates")) {
				c.getPA().loadUpdates();
			}
			
			if (playerCommand.equalsIgnoreCase("players")) {
				c.sendMessage("There are currently "+PlayerHandler.getPlayerCount()+ " players online.");
			}
			
			if (playerCommand.startsWith("yell")) {
				if (!playerCommand.startsWith("yell ")) {
					c.sendMessage("use as ::yell message");
					return;
				}
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j];
						c2.sendMessage("@blu@[Yell] @bla@[" + c.playerName + "]: " + playerCommand.substring(5));
					}
				}
			}
			
			if (playerCommand.startsWith("changepassword") && playerCommand.length() < 29) {
				if (!playerCommand.startsWith("changepassword ")) {
					c.sendMessage("use as ::changepassword password");
					return;
				}
				c.playerPass = playerCommand.substring(15);
				c.sendMessage("Your password is now: " + c.playerPass);			
			}
			
			
			
	}

	
}		
		
		
		
		
		
		

