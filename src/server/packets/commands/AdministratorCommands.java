package server.packets.commands;

import server.Config;
import server.Connection;
import server.Server;
import server.players.Client;
import server.players.PlayerHandler;

public class AdministratorCommands {

	public static void execute(Client c, String playerCommand) {
		
			
			if (playerCommand.equals("reloaditems")) {
				for(int i = 0; i < Config.ITEM_LIMIT; i++)
					Server.itemHandler.ItemList[i] = null;
				Server.itemHandler.loadItemList("item.cfg");
				Server.itemHandler.loadItemPrices("prices.txt");
				c.sendMessage("Items reloaded.");
			}
				
			if (playerCommand.equals("reloadnpcs")) {
				for(int i = 0; i < Server.npcHandler.maxNPCs; i++) {
					Server.npcHandler.npcs[i] = null;
				}
				for(int i = 0; i < Server.npcHandler.maxListedNPCs; i++) {
					Server.npcHandler.NpcList[i] = null;
				}
				Server.npcHandler.loadNPCList("./Data/cfg/npc.cfg");
				Server.npcHandler.loadAutoSpawn("./Data/cfg/spawn-config.cfg");
				c.sendMessage("NPCs reloaded.");
			}
			
				
			if (playerCommand.startsWith("reloadshops")) {
				Server.shopHandler = new server.content.npc.ShopHandler();
			}
			
			if (playerCommand.toLowerCase().startsWith("giveitem")) {
				try {
					String[] args = playerCommand.split(" ");
					int newItemID = Integer.parseInt(args[1]);
					int newItemAmount = Integer.parseInt(args[2]);
					String otherplayer = args[3];
					Client c2 = null;
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(otherplayer)) {
								c2 = (Client) PlayerHandler.players[i];
								break;
							}
						}
					}
					if (c2 == null) {
						c.sendMessage("Player doesn't exist.");
						return;
					}
					c.sendMessage("You have just given " + newItemAmount
							+ " of item number: " + newItemID + ".");
					c2.sendMessage("You have just been given item(s).");
					c2.getItems().addItem(newItemID, newItemAmount);
				} catch (Exception e) {
					c.sendMessage("Use as ::giveitem ID AMOUNT PLAYERNAME.");
				}
			}
			
			if (playerCommand.toLowerCase().startsWith("takeitem")) {
				try {
					String[] args = playerCommand.split(" ");
					int takenItemID = Integer.parseInt(args[1]);
					int takenItemAmount = Integer.parseInt(args[2]);
					String otherplayer = args[3];
					Client c2 = null;
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (Server.playerHandler.players[i] != null) {
							if (Server.playerHandler.players[i].playerName
									.equalsIgnoreCase(otherplayer)) {
								c2 = (Client) Server.playerHandler.players[i];
								break;
							}
						}
					}
					if (c2 == null) {
						c.sendMessage("Player doesn't exist.");
						return;
					}
					c.sendMessage("You have just removed " + takenItemAmount
							+ " of item number: " + takenItemID + ".");
					c2.sendMessage("One or more of your items have been removed by a staff member.");
					c2.getItems().deleteItem(takenItemID, takenItemAmount);
				} catch (Exception e) {
					c.sendMessage("Use as ::takeitem ID AMOUNT PLAYERNAME.");
				}
			}
			
			if (playerCommand.toLowerCase().startsWith("wipeinv")) {
				try {
					String[] args = playerCommand.split(" ");
					String otherplayer = args[1];
					Client c2 = null;
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(otherplayer)) {
								c2 = (Client) PlayerHandler.players[i];
								break;
							}
						}
					}

					if (c2 == null) {
						c.sendMessage("Player doesn't exist.");
						return;
					}
					c2.getItems().removeAllItems();
					c2.sendMessage("Your inventory has been cleared by a staff member.");
					c.sendMessage("You cleared " + c2.playerName
							+ "'s inventory.");
				} catch (Exception e) {
					c.sendMessage("Use as ::wipeinv PLAYERNAME.");
				}
			}
			
			if (playerCommand.toLowerCase().startsWith("kill")) {
				try {
					String playerToKill = playerCommand.substring(5);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (Server.playerHandler.players[i] != null) {
							if (Server.playerHandler.players[i].playerName
									.equalsIgnoreCase(playerToKill)) {
								c.sendMessage("You have killed the user: "
										+ Server.playerHandler.players[i].playerName);
								Client c2 = (Client) Server.playerHandler.players[i];
								c2.isDead = true;
								break;
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			
			if (playerCommand.equalsIgnoreCase("infhp")) {
				c.getPA().requestUpdates();
				c.playerLevel[3] = 99999;
				c.getPA().refreshSkill(3);
			}
			
			if (playerCommand.toLowerCase().startsWith("bank")) {
				c.getPA().openUpBank();
			}
			
			if (playerCommand.equalsIgnoreCase("alltome")) {
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.teleportToX = c.absX;
						c2.teleportToY = c.absY;
						c2.heightLevel = c.heightLevel;
						c2.sendMessage("Mass teleport to: " + c.playerName + "");
					}
				}
			}
			
			if (playerCommand.startsWith("ipban")) { // use as ::ipban name
				try {
					String playerToBan = playerCommand.substring(6);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Connection.addIpToBanList(Server.playerHandler.players[i].connectedFrom);
								Connection.addIpToFile(Server.playerHandler.players[i].connectedFrom);
								c.sendMessage("You have IP banned the user: "+Server.playerHandler.players[i].playerName+" with the host: "+Server.playerHandler.players[i].connectedFrom);
								Server.playerHandler.players[i].disconnected = true;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			
			
			if (playerCommand.startsWith("ipmute")) {
				try {	
					String playerToBan = playerCommand.substring(7);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Connection.addIpToMuteList(Server.playerHandler.players[i].connectedFrom);
								c.sendMessage("You have IP Muted the user: "+Server.playerHandler.players[i].playerName);
								Client c2 = (Client)Server.playerHandler.players[i];
								c2.sendMessage("You have been muted by: " + c.playerName);
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}
			
			if (playerCommand.startsWith("unipmute")) {
				try {	
					String playerToBan = playerCommand.substring(9);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Connection.unIPMuteUser(Server.playerHandler.players[i].connectedFrom);
								c.sendMessage("You have Un Ip-Muted the user: "+Server.playerHandler.players[i].playerName);
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}
			
			if (playerCommand.startsWith("master")) {
				if (c.inWild())
					return;
				for(int i = 0; i < c.playerLevel.length; i++) {
					c.playerXP[i] = c.getPA().getXPForLevel(99)+5;
					c.playerLevel[i] = c.getPA().getLevelForXP(c.playerXP[i]);
					c.getPA().refreshSkill(i);
				}
			}
			
			if (playerCommand.startsWith("xteleto")) {
				String name = playerCommand.substring(8);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(name)) {
							c.getPA().movePlayer(Server.playerHandler.players[i].getX(), Server.playerHandler.players[i].getY(), Server.playerHandler.players[i].heightLevel);
						}
					}
				}			
			}
			
			if (playerCommand.startsWith("setlevel")) {
				
				try {
					String[] args = playerCommand.split(" ");
					int skill = Integer.parseInt(args[1]);
					int level = Integer.parseInt(args[2]);
					if (level > 99)
					level = 99;
					else if (level < 0)
					level = 1;
					c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
					c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
					c.getPA().refreshSkill(skill);
				} catch (Exception e){}

			}
			
			if (playerCommand.startsWith("interface")) {
				try {	
					String[] args = playerCommand.split(" ");
					int a = Integer.parseInt(args[1]);
					c.getPA().showInterface(a);
				} catch(Exception e) {
					c.sendMessage("::interface ####"); 
				}
			}
			
			if (playerCommand.startsWith("gfx")) {
				String[] args = playerCommand.split(" ");
				c.gfx0(Integer.parseInt(args[1]));
			}
			
			if (playerCommand.startsWith("anim")) {
				String[] args = playerCommand.split(" ");
				c.startAnimation(Integer.parseInt(args[1]));
				c.getPA().requestUpdates();
			}
		}
		
	

}
