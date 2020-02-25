package server.packets.commands;

import server.Server;
import server.content.randoms.RandomEventHandler;
import server.players.Client;
import server.players.PlayerHandler;

public class OwnerCommands {

	public static void execute(Client c, String playerCommand) {

		if (playerCommand.startsWith("find")) {
			int id = Integer.parseInt(playerCommand.substring(5));
			for (int i = 1000; i < 10000; i++) {
				c.getPA().sendFrame126(""+i, i);
			}
			c.getPA().showInterface(id);
		}
		
	
	if (playerCommand.equals("spec")) {
		if (!c.inWild())
			c.specAmount = 10.0;
	}
	if (playerCommand.equals("random")) {
		RandomEventHandler.callRandom(c);
		c.sendMessage("random event");
	}
	if (playerCommand.startsWith("object")) {
		String[] args = playerCommand.split(" ");				
		c.getPA().object(Integer.parseInt(args[1]), c.absX, c.absY, 0, 10);
	}
	
	
	if (playerCommand.startsWith("dialogue")) {
		
		try {
			String[] args = playerCommand.split(" ");
			if (args.length == 3) {
				int dialogueID = Integer.parseInt(args[1]);
				int npcID = Integer.parseInt(args[2]);
				c.getDH().sendDialogues(dialogueID, npcID);
			} else {
				c.sendMessage("Use as ::dialogue id npc");
			}
			} catch (Exception e) {
			
			}
	}
	
	if (playerCommand.startsWith("item")) {
		if (c.inWild())
			return;
		try {
		String[] args = playerCommand.split(" ");
		if (args.length == 3) {
			int newItemID = Integer.parseInt(args[1]);
			int newItemAmount = Integer.parseInt(args[2]);
			if ((newItemID <= 20000) && (newItemID >= 0)) {
				c.getItems().addItem(newItemID, newItemAmount);
				System.out.println("Spawned: " + newItemID + " by: " + c.playerName);
			} else {
				c.sendMessage("No such item.");
			}
		} else {
			c.sendMessage("Use as ::item 995 200");
		}
		} catch (Exception e) {
		
		}
	}
		
		if (playerCommand.startsWith("reloadshops")) {
			Server.shopHandler = new server.content.npc.ShopHandler();
		}
		
		
		
		
		 if (playerCommand.startsWith("update")) {
             try {
                     String[] args = playerCommand.split(" ");
                     if (args.length == 2) {
                             int seconds = Integer.parseInt(args[1]);
                             PlayerHandler.updateSeconds = seconds;
                             PlayerHandler.updateAnnounced = false;
                             PlayerHandler.updateRunning = true;
                             PlayerHandler.updateStartTime = System.currentTimeMillis();
                     } else {
                             c.sendMessage("Use as ::update (seconds)");
                     }
             } catch (Exception e) {
             }
     }
		
		
		
	
		if (playerCommand.equals("Vote")) {
			for (int j = 0; j < Server.playerHandler.players.length; j++)
				if (Server.playerHandler.players[j] != null) {
					Client c2 = (Client)Server.playerHandler.players[j];
					c2.getPA().sendFrame126("www.google.ca", 12000);
				}
		}


		if (playerCommand.equalsIgnoreCase("debug")) {
			Server.playerExecuted = true;
		}
		
		
		
		if(playerCommand.startsWith("www")) {
			c.getPA().sendFrame126(playerCommand,0);			
		}
		

	
		
		if(playerCommand.startsWith("npc")) {
			try {
				int newNPC = Integer.parseInt(playerCommand.substring(4));
				if(newNPC > 0) {
					Server.npcHandler.spawnNpc(c, newNPC, c.absX, c.absY, 0, 0, 120, 7, 70, 70, false, false);
					c.sendMessage("You spawn a Npc.");
				} else {
					c.sendMessage("No such NPC.");
				}
			} catch(Exception e) {
				
			}			
		}
		
		
	}

}
