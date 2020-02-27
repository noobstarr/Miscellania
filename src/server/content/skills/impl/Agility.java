package server.content.skills.impl;

import server.Config;
import server.players.Client;
import server.players.Player;
import server.util.CycleEvent;
import server.util.CycleEventContainer;
import server.util.CycleEventHandler;
/**
 * Agility.java
 * 
 * @author Acquittal
 *
 *
 **/
 
public class Agility {

	public Client client;
	public int agtimer = 10;
	public boolean bonus = false;

	public Agility(Client c) {
		client = c;
	}
	
	public int steppingStone, steppingStoneTimer = 0, agilityTimer = -1,
			moveHeight = -1, tropicalTreeUpdate = -1, zipLine = -1;
	private int moveX, moveY, moveH;
	
	public static final int LOG_EMOTE = 762, PIPES_EMOTE = 844,
			CLIMB_UP_EMOTE = 828, CLIMB_DOWN_EMOTE = 827,
			CLIMB_UP_MONKEY_EMOTE = 3487, WALL_EMOTE = 840;
	
	public void brimhavenMonkeyBars(Client c, String Object, int level, int x, int y, int a, int b, int xp)
	{
		if (c.playerLevel[Player.playerAgility] < level) {
			c.sendMessage("You need a Agility level of "+ level +" to pass this " + Object + ".");
			return;
		}
		if (c.absX == a && c.absY == b) { 
			c.getPA().walkTo3(x, y);
			
			c.getPA().addSkillXP(xp, Player.playerAgility);
			
			c.getPA().refreshSkill(Player.playerAgility);
		}
	}
	
	/*
	 * Wilderness course
	 */
	
	public void wildernessEntrance(Client c, String Object, int level, int x, int y, int a, int b, int xp) {
		if (c.playerLevel[Player.playerAgility] < level) {
			c.sendMessage("You need a Agility level of "+ level +" to pass this " + Object + ".");
			return;
		}
		if (c.absX == a && c.absY == b) { 
			c.getPA().walkTo3(x, y);
			
			c.getPA().addSkillXP(xp, Player.playerAgility);
			c.getPA().refreshSkill(Player.playerAgility);
			
		}
	}
	
	public void doWildernessEntrance(final Client c) {
		if (System.currentTimeMillis() - c.foodDelay < 2000) {
			return;
		}
			c.stopMovement();
			c.freezeTimer = 16;
			c.playerWalkIndex = 762;
			c.updateRequired = true;
			c.appearanceUpdateRequired = true;
			c.getAgility().wildernessEntrance(c, "Door", 1, 0, +15, 2998, 3917, 40 * Config.AGILITY_EXPERIENCE);
			c.foodDelay = System.currentTimeMillis();
			CycleEventHandler.addEvent(c, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					c.playerStandIndex = 0x328;
					c.playerTurnIndex = 0x337;
					c.playerWalkIndex = 0x333;
					c.playerTurn180Index = 0x334;
					c.playerTurn90CWIndex = 0x335;
					c.playerTurn90CCWIndex = 0x336;
					c.playerRunIndex = 0x338;
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					container.stop();
				}
				@Override
				public void stop() {
				}
			}, 14);
	}
	
	private static void setAnimationBack(Client c) {
		c.isRunning2 = true;
		c.getPlayerAssistant().sendFrame36(173, 1);
		c.playerWalkIndex = 0x333;
		c.getPlayerAssistant().requestUpdates();
	}
	
	public static void brimhavenSkippingStone(final Client c) {
		if (c.stopPlayerPacket) {
			return;
		}
		if (c.playerLevel[c.playerAgility] < 12) {
			c.getDH().sendStatement("You need 12 agility to use these stepping stones");
			c.nextChat = 0;
			return;
		}
		c.stopPlayerPacket = true;
		CycleEventHandler.addEvent(c, new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				c.startAnimation(769);
				if (c.absX <= 2997) {
					container.stop();
				}
			}

			@Override
			public void stop() {
				// c.getPlayerAssistant().addSkillXP(100, c.playerAgility);
			}
		}, 1);
		CycleEventHandler.addEvent(c, new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				if (c.absX >= 2648) {
					c.teleportToX = c.absX - 2;
					c.teleportToY = c.absY - 5;
					if (c.absX <= 2997) {
						container.stop();
					}
				} else if (c.absX <= 2648) {
					c.teleportToX = c.absX + 2;
					c.teleportToY = c.absY + 5;
					if (c.absX >= 2645) {
						container.stop();
					}
				}

			}

			@Override
			public void stop() {
				// c.getPlayerAssistant().addSkillXP(300, c.playerAgility);
				setAnimationBack(c);
				c.stopPlayerPacket = false;
			}
		}, 3);
	}
	
	
	/*
	 * Gnome course
	 */
	
	public void gnomeLog(Client c, String Object, int level, int x, int y, int a, int b, int xp)
	{
		if (c.playerLevel[Player.playerAgility] < level) {
			c.sendMessage("You need a Agility level of "+ level +" to pass this " + Object + ".");
			return;
		}
		if (c.absX == a && c.absY == b) { 
			c.getPA().walkTo3(x, y);
			
			c.getPA().addSkillXP(xp, Player.playerAgility);
			c.getPA().refreshSkill(Player.playerAgility);
			
		}
	}
	
	public void climbUp(final Client c, final int moveX, final int moveY, final int moveH) {
		c.startAnimation(828);
		c.setCanWalk(false);
		  CycleEventHandler.addEvent(c, new CycleEvent() {
	            @Override
	            public void execute(CycleEventContainer container) {
				if (c.disconnected) {
					stop();
					return;
				}
				c.setCanWalk(true);
				c.getPlayerAssistant().movePlayer(moveX, moveY, moveH);
				container.stop();
			}
			@Override
				public void stop() {
					
				}
		}, 1);
	}

	public void gnomeNet(Client c, String net, int level, int a, int b, int h, int x, int y, int emote, int xp)
	{
		if (c.playerLevel[Player.playerAgility] < level) {
			c.sendMessage("You need a Agility level of "+ level +" to pass this " + net + ".");
			return;
		}
		if (c.absX == a && c.absY == b) {
			c.teleportToX = x;
			c.teleportToY = y;
			c.heightLevel = h;
			c.updateRequired = true;
			c.appearanceUpdateRequired = true;
			
			c.getPA().addSkillXP(xp, Player.playerAgility);
			c.getPA().refreshSkill(Player.playerAgility);
			
			c.turnPlayerTo(c.getX()- 1, c.getY());
		}
	}

	public void gnomeBranch(Client c, String branch, int level, int x, int y, int h, int a, int b, int emote, int xp)
	{
		if (c.playerLevel[Player.playerAgility] < level) {
			c.sendMessage("You need a Agility level of "+ level +" to pass this " + branch + ".");
			return;
		}
		if (c.absX == a && c.absY == b) {
			c.teleportToX = x;
			c.teleportToY = y;
			c.heightLevel = h;
			
			c.getPA().addSkillXP(xp, Player.playerAgility);
			c.getPA().refreshSkill(Player.playerAgility);
			
		}
	}



	public void gnomePipe(Client c, String pipe, int level, int a, int b, int x, int y, int add, int amount, int xp)
	{

		if (c.playerLevel[Player.playerAgility] < level) {
			c.sendMessage("You need a Agility level of "+ level +" to pass this " + pipe + ".");
			return;
		}
		if (c.absX == a && c.absY == b)
		{
			if (bonus && c.ag1 == 1 && c.ag2 >= 1 
					&& c.ag3 >= 1 && c.ag4 >= 1 
					&& c.ag5 >= 1 && c.ag6 >= 1)
			{
				c.getPA().walkTo3(x, y);
				c.turnPlayerTo(c.getX(), c.getY()+ 1);
				
				c.getPA().addSkillXP(360, Player.playerAgility);
				c.getPA().refreshSkill(Player.playerAgility);
				
				c.getItems().addItem(add, amount);
				c.sendMessage("Congratulations, you have been awarded for completing the course, you...");
				c.sendMessage("... receive "+ amount +" tickets, and 360 experience!");
				bonus = false;
				c.ag1 = 0;
				c.ag2 = 0;
				c.ag3 = 0;
				c.ag4 = 0;
				c.ag5 = 0;
				c.ag6 = 0;
			} else {
				c.getPA().walkTo3(x, y);
				
				c.getPA().addSkillXP(xp, Player.playerAgility);
				c.getPA().refreshSkill(Player.playerAgility);
				
				c.getItems().addItem(add, 1);
				c.turnPlayerTo(c.getX(), c.getY()+ 1);
				bonus = false;
				c.sendMessage("You did not complete the full course, you only receive one agility ticket.");
				c.ag1 = 0;
				c.ag2 = 0;
				c.ag3 = 0;
				c.ag4 = 0;
				c.ag5 = 0;
				c.ag6 = 0;
			}
		}
	}
	
	public void doGnomeLog(final Client c) {
			if (System.currentTimeMillis() - c.foodDelay < 2000) {
				return;
			}
				c.stopMovement();
				c.freezeTimer = 8;
				c.playerWalkIndex = 762;
				c.updateRequired = true;
				c.appearanceUpdateRequired = true;
				c.getAgility().gnomeLog(c, "Log", 1, 0, -7, 2474, 3436, 8 * Config.AGILITY_EXPERIENCE);
				c.ag1 = 1;
				c.foodDelay = System.currentTimeMillis();
				CycleEventHandler.addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						c.playerStandIndex = 0x328;
						c.playerTurnIndex = 0x337;
						c.playerWalkIndex = 0x333;
						c.playerTurn180Index = 0x334;
						c.playerTurn90CWIndex = 0x335;
						c.playerTurn90CCWIndex = 0x336;
						c.playerRunIndex = 0x338;
						c.updateRequired = true;
						c.appearanceUpdateRequired = true;
						container.stop();
					}
					@Override
					public void stop() {
					}
				}, 6);
	}
	
	public void doGnomeNet1(final Client c) {
		if (System.currentTimeMillis() - c.foodDelay < 2000) {
		return;
		}
			c.stopMovement();
			c.startAnimation(828);
			c.getAgility().gnomeNet(c, "Net", 1, 2471, 3426, 1, 2471, 3424, 828, 8 * Config.AGILITY_EXPERIENCE);
			c.getAgility().gnomeNet(c, "Net", 1, 2472, 3426, 1, 2472, 3424, 828, 8 * Config.AGILITY_EXPERIENCE);
			c.getAgility().gnomeNet(c, "Net", 1, 2473, 3426, 1, 2473, 3424, 828, 8 * Config.AGILITY_EXPERIENCE);
			c.getAgility().gnomeNet(c, "Net", 1, 2474, 3426, 1, 2474, 3424, 828, 8 * Config.AGILITY_EXPERIENCE);
			c.getAgility().gnomeNet(c, "Net", 1, 2475, 3426, 1, 2475, 3424, 828,8 * Config.AGILITY_EXPERIENCE);
			c.getAgility().gnomeNet(c, "Net", 1, 2476, 3426, 1, 2476, 3424, 828, 8 * Config.AGILITY_EXPERIENCE);
			c.ag2 = 1;
			c.foodDelay = System.currentTimeMillis();
	}
	
	public void doGnomeBranch1(final Client c) {
		if (System.currentTimeMillis() - c.foodDelay < 2000) {
			return;
			}
				c.stopMovement();
				c.startAnimation(828);
				c.getAgility().gnomeBranch(c, "Branch", 1, 2473, 3420, 2, 2473, 3423, 828, 5 * Config.AGILITY_EXPERIENCE);
				c.getAgility().gnomeBranch(c, "Branch", 1, 2473, 3420, 2, 2474, 3422, 828, 5 * Config.AGILITY_EXPERIENCE);
				c.getAgility().gnomeBranch(c, "Branch", 1, 2473, 3420, 2, 2472, 3422, 828, 5 * Config.AGILITY_EXPERIENCE);
				c.ag3 = 1;
				c.foodDelay = System.currentTimeMillis();
	}
	
	public void doGnomeBranch2(final Client c) {
		if (System.currentTimeMillis() - c.foodDelay < 2000) {
			return;
			}
				c.stopMovement();
				c.startAnimation(828);
				c.sendMessage("You slip and fall down.");
				c.getAgility().gnomeBranch(c, "Branch", 1, 2486, 3420, 0, 2485, 3419, 828, 5 * Config.AGILITY_EXPERIENCE);
				c.getAgility().gnomeBranch(c, "Branch", 1, 2486, 3420, 0, 2485, 3420, 828, 5 * Config.AGILITY_EXPERIENCE);
				c.getAgility().gnomeBranch(c, "Branch", 1, 2486, 3420, 0, 2486, 3420, 828, 5 * Config.AGILITY_EXPERIENCE);
				c.ag5 = 1;
				c.foodDelay = System.currentTimeMillis();
	}
	
	public void doGnomeNet2(final Client c) {
		if (System.currentTimeMillis() - c.foodDelay < 2000) {
			return;
			}
				c.stopMovement();
				c.startAnimation(828);
				c.getAgility().gnomeNet(c, "Net", 1, 2483, 3425, 0, 2483, 3427, 828, 8 * Config.AGILITY_EXPERIENCE);
				c.getAgility().gnomeNet(c, "Net", 1, 2484, 3425, 0, 2484, 3427, 828, 8 * Config.AGILITY_EXPERIENCE);
				c.getAgility().gnomeNet(c, "Net", 1, 2485, 3425, 0, 2485, 3427, 828, 8 * Config.AGILITY_EXPERIENCE);
				c.getAgility().gnomeNet(c, "Net", 1, 2486, 3425, 0, 2486, 3427, 828, 8 * Config.AGILITY_EXPERIENCE);
				c.getAgility().gnomeNet(c, "Net", 1, 2487, 3425, 0, 2487, 3427, 828, 8 * Config.AGILITY_EXPERIENCE);
				c.getAgility().gnomeNet(c, "Net", 1, 2488, 3425, 0, 2488, 3427, 828, 8 * Config.AGILITY_EXPERIENCE);
				c.ag6 = 1;
				c.getAgility().bonus = true;
				c.foodDelay = System.currentTimeMillis();
	}
	
	public void doGnomeRope(final Client c) {
				if (System.currentTimeMillis() - c.foodDelay < 2000) {
					return;
					}
				c.stopMovement();
				c.freezeTimer = 8;
				c.playerWalkIndex = 762;
				c.updateRequired = true;
				c.appearanceUpdateRequired = true;
				c.getAgility().gnomeLog(c, "Log", 1, +6, 0, 2477, 3420, 7 * Config.AGILITY_EXPERIENCE);
				c.ag4 = 1;
				c.foodDelay = System.currentTimeMillis();
				CycleEventHandler.addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						c.playerStandIndex = 0x328;
						c.playerTurnIndex = 0x337;
						c.playerWalkIndex = 0x333;
						c.playerTurn180Index = 0x334;
						c.playerTurn90CWIndex = 0x335;
						c.playerTurn90CCWIndex = 0x336;
						c.playerRunIndex = 0x338;
						c.updateRequired = true;
						c.appearanceUpdateRequired = true;
						container.stop();
					}
					@Override
					public void stop() {
					}
				}, 6);
	}
		
	public void doGnomePipe1(final Client c) {
			if (System.currentTimeMillis() - c.foodDelay < 2000) {
				return;
			}
			c.stopMovement();
			c.freezeTimer = 8;
			c.playerWalkIndex = 746;
			c.updateRequired = true;
			c.appearanceUpdateRequired = true;
			c.getAgility().gnomePipe(c, "Pipe", 1, 2484, 3430, 0, +7, 2996, 10, 47 * Config.AGILITY_EXPERIENCE);
			c.foodDelay = System.currentTimeMillis();
			CycleEventHandler.addEvent(c, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					c.playerStandIndex = 0x328;
					c.playerTurnIndex = 0x337;
					c.playerWalkIndex = 0x333;
					c.playerTurn180Index = 0x334;
					c.playerTurn90CWIndex = 0x335;
					c.playerTurn90CCWIndex = 0x336;
					c.playerRunIndex = 0x338;
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(748);
					container.stop();
				}
				@Override
				public void stop() {
				}
			}, 7);
	}
	
	public void doGnomePipe2(final Client c) {
		if (System.currentTimeMillis() - c.foodDelay < 2000) {
			return;
		}
		c.stopMovement();
		c.freezeTimer = 8;
		c.playerWalkIndex = 746;
		c.updateRequired = true;
		c.appearanceUpdateRequired = true;
		c.getAgility().gnomePipe(c, "Pipe", 1, 2487, 3430, 0, +7, 2996, 10, 47 * Config.AGILITY_EXPERIENCE);
		c.foodDelay = System.currentTimeMillis();
		CycleEventHandler.addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				c.playerStandIndex = 0x328;
				c.playerTurnIndex = 0x337;
				c.playerWalkIndex = 0x333;
				c.playerTurn180Index = 0x334;
				c.playerTurn90CWIndex = 0x335;
				c.playerTurn90CCWIndex = 0x336;
				c.playerRunIndex = 0x338;
				c.updateRequired = true;
				c.appearanceUpdateRequired = true;
				c.startAnimation(748);
				container.stop();
			}
			@Override
			public void stop() {
			}
		}, 7);
	}
	

	/*
	 * Rewards.
	 */

	public void gnomeTicketCounter(Client c, String ticket, int remove, int amount, int xp) {
		
		if (c.getItems().playerHasItem(2996, 1)) {
			if (ticket.equals("1"))
			{
				c.getItems().deleteItem2(remove, amount);
				c.getPA().addSkillXP(xp, Player.playerAgility);
				c.getPA().refreshSkill(Player.playerAgility);
				
				c.sendMessage("You've recieved "+ xp + " Agility Experience!");
				return;
			}
		}
		if (c.getItems().playerHasItem(2996, 10)) {
			if (ticket.equals("10"))
			{
				c.getItems().deleteItem2(remove, amount);
				c.getPA().addSkillXP(xp, Player.playerAgility);
				c.getPA().refreshSkill(Player.playerAgility);
				
				c.sendMessage("You've recieved "+ xp + " Agility Experience!");
				return;
			}
		}
		if (c.getItems().playerHasItem(2996, 25)) {
			if (ticket.equals("25"))
			{
				c.getItems().deleteItem2(remove, amount);
				c.getPA().addSkillXP(xp, Player.playerAgility);
				c.getPA().refreshSkill(Player.playerAgility);
				
				c.sendMessage("You've recieved "+ xp + " Agility Experience!");
				return;
			}
		}
		if (c.getItems().playerHasItem(2996, 100)) {
			if (ticket.equals("100"))
			{
				c.getItems().deleteItem2(remove, amount);
				c.getPA().addSkillXP(xp, Player.playerAgility);
				c.getPA().refreshSkill(Player.playerAgility);
				
				c.sendMessage("You've recieved "+ xp + " Agility Experience!");
				return;
			}
		}
		if (c.getItems().playerHasItem(2996, 1000)) {
			if (ticket.equals("1000"))
			{
				c.getItems().deleteItem2(remove, amount);
				c.getPA().addSkillXP(xp, Player.playerAgility);
				c.getPA().refreshSkill(Player.playerAgility);
				
				c.sendMessage("You've recieved "+ xp + " Agility Experience!");
				return;
			}
		}
		else
		{
			c.sendMessage("You need more agility tickets to get this reward!");
			
			return;
		}
		
	}
	
	private void stopEmote() {
		
		client.getCombat().getPlayerAnimIndex(client.getItems().getItemName(client.playerEquipment[client.playerWeapon]).toLowerCase());
		client.setCanWalk(false);
		client.getPlayerAssistant().requestUpdates(); // this was needed to make the
													// agility work
		client.isRunning2 = true;
	}
	
	public void destinationReached(int x2, int y2, final int endingEmote) {
		if (x2 >= 0 && y2 >= 0 && x2 != y2) {
			  CycleEventHandler.addEvent(client, new CycleEvent() {
		            @Override
		            public void execute(CycleEventContainer container) {
					if (client.disconnected) {
						container.stop();
						return;
					}
					if (moveHeight >= 0) {
						client.getPlayerAssistant().movePlayer(client.getX(), client.getY(), moveHeight);
						moveHeight = -1;
					}
					stopEmote();
					client.startAnimation(endingEmote);
					container.stop();
				}
				@Override
					public void stop() {
						
					}
			}, x2 + y2);
		} else if (x2 == y2) {
			  CycleEventHandler.addEvent(client, new CycleEvent() {
		            public void execute(CycleEventContainer container) {
					if (client.disconnected) {
						container.stop();
						return;
					}
					if (moveHeight >= 0) {
						client.getPlayerAssistant().movePlayer(client.getX(), client.getY(), moveHeight);
						moveHeight = -1;
					}
					stopEmote();
					client.startAnimation(endingEmote);
					container.stop();
				}
		            public void stop() {
						
					}
			}, x2);
		} else if (x2 < 0) {
			  CycleEventHandler.addEvent(client, new CycleEvent() {
		            @Override
		            public void execute(CycleEventContainer container) {
					if (client.disconnected) {
						container.stop();
						return;
					}
					if (moveHeight >= 0) {
						client.getPlayerAssistant().movePlayer(client.getX(), client.getY(), moveHeight);
						moveHeight = -1;

					}
					stopEmote();
					client.startAnimation(endingEmote);
					container.stop();
				}
				@Override
					public void stop() {
						
					}
			}, -x2 + y2);
		} else if (y2 < 0) {
			  CycleEventHandler.addEvent(client, new CycleEvent() {
		            @Override
		            public void execute(CycleEventContainer container) {
					if (client.disconnected) {
						container.stop();
						return;
					}
					if (moveHeight >= 0) {
						client.getPlayerAssistant().movePlayer(client.getX(), client.getY(), moveHeight);
						moveHeight = -1;

					}
					stopEmote();
					client.startAnimation(endingEmote);
					container.stop();
				}
				@Override
					public void stop() {
						
					}
			}, x2 - y2);
		}
	}

	
	
	private void walkToEmote(int id) {
		client.isRunning2 = false;
		client.playerWalkIndex = id;
		client.getPlayerAssistant().requestUpdates(); // this was needed to make the
													// agility work
	}
	
	
	public void walk(int EndX, int EndY, int Emote, int endingAnimation) {
		client.setCanWalk(true);
		walkToEmote(Emote);
		client.getPlayerAssistant().walkTo2(EndX, EndY);
		destinationReached(EndX, EndY, endingAnimation);
	}
	
	
	private static final int WALK = 1, MOVE = 2, AGILITY = 3;

	private static void handleAgility(Client client, int x, int y, int levelReq, int anim, int walk, String message) {
		if (client.playerLevel[client.playerAgility] < levelReq) {
			client.sendMessage("You need " + levelReq + " agility to use this shortcut.");
			return;
		}
		switch (walk) {
		case 1:
			client.getPlayerAssistant().walkTo(x, y);
			break;
		case 2:
			client.getPlayerAssistant().movePlayer(x, y, client.heightLevel);
			break;
		case 3:
			client.getAgility().walk(x, y, anim, -1);
			break;
		}
		if (anim != 0 && anim != -1) {
			client.startAnimation(anim);
		}
		client.sendMessage(message);
	}

	public static void processAgilityShortcut(Client client) {
		switch (client.objectId) {
		case 993:
		if (client.absY == 3435) {
			handleAgility(client, 2761, 3438, 1, 3067, MOVE, "You jump over the stile.");
		} else if (client.absY == 3438) {
			handleAgility(client, 2761, 3435, 1, 3067, MOVE, "You jump over the stile.");
		}
		break;
		case 9326:
		if (client.absX == 2773) {
			handleAgility(client, 2, 0, 81, 3067, WALK, "You jump over the strange floor.");
		} else if (client.absX == 2775) {
			handleAgility(client, -2, 0, 81, 3067, WALK, "You jump over the strange floor.");
		}
		break;
		case 9321:
		if (client.absX == 2735) {
			handleAgility(client, -5, 0, 62, 2240, WALK, "You squeeze through the crevice.");
		} else if (client.absX == 2730) {
			handleAgility(client, 5, 0, 62, 2240, WALK, "You squeeze through the crevice.");
		}
		break;
		case 12127:
			if (client.absY == 4403) {
				handleAgility(client, 0, -2, 66, 2240, WALK,
						"You squeeze past the jutted wall.");
			} else if (client.absY == 4401) {
				handleAgility(client, 0, 2, 66, 2240, WALK,
						"You squeeze past the jutted wall.");
			} else if (client.absY == 4404) {
				handleAgility(client, 0, -2, 46, 2240, WALK,
						"You squeeze past the jutted wall.");
			} else if (client.absY == 4402) {
				handleAgility(client, 0, 2, 46, 2240, WALK,
						"You squeeze past the jutted wall.");
			}
			break;
		case 3933:
			if (client.absY == 3232) {
				handleAgility(client, 0, 7, 85, 762, WALK,
						"You pass through the agility shortcut.");
			} else if (client.absY == 3239) {
				handleAgility(client, 0, -7, 85, 762, WALK,
						"You pass through the agility shortcut.");
			}
			break;
		case 4615:
		case 4616:
			if (client.absX == 2595) {
				handleAgility(client, 2599, client.absY, 1, 3067, MOVE,
						"You pass through the agility shortcut.");
			} else if (client.absX == 2599) {
				handleAgility(client, 2595, client.absY, 1, 3067, MOVE,
						"You pass through the agility shortcut.");
			}
			break;
		case 11844:
			if (client.absX == 2936) {
				handleAgility(client, -2, 0, 5, -1, WALK,
						"You pass through the agility shortcut.");
			} else if (client.absX == 2934) {
				handleAgility(client, 2, 0, 5, -1, WALK,
						"You pass through the agility shortcut.");
			}
			break;
		case 5090:
			if (client.absX == 2687) {// 2682, 9506
				handleAgility(client, -5, 0, 5, 762, WALK,
						"You walk across the log balance.");
			}
			break;
		case 5088:
			if (client.absX == 2682) {// 2867, 9506
				handleAgility(client, 5, 0, 5, 762, WALK,
						"You walk across the log balance.");
			}
			break;
		case 14922:
			if (client.objectX == 2344 && client.objectY == 3651) {
				handleAgility(client, 2344, 3655, 1, 762, MOVE,
						"You crawl through the hole.");
			} else if (client.objectX == 2344 && client.objectY == 3654) {
				handleAgility(client, 2344, 3650, 1, 762, MOVE,
						"You crawl through the hole.");
			}
			break;
		case 9330:
			if (client.objectX == 2601 && client.objectY == 3336) {
				handleAgility(client, -4, 0, 33, 844, AGILITY,
						"You pass through the agility shortcut.");
			}
		case 5100:
			if (client.absY == 9566) {
				handleAgility(client, 2655, 9573, 17, 762, MOVE,
						"You pass through the agility shortcut.");
			} else if (client.absY == 9573) {
				handleAgility(client, 2655, 9573, 17, 762, MOVE,
						"You pass through the agility shortcut.");
			}
			break;
		case 9328:
			if (client.objectX == 2599 && client.objectY == 3336) {
				handleAgility(client, 4, 0, 33, 844, AGILITY,
						"You pass through the agility shortcut.");
			}
			break;

		case 9293:
			if (client.absX < client.objectX) {
				handleAgility(client, 2892, 9799, 70, 844, MOVE,
						"You pass through the agility shortcut.");
			} else {
				handleAgility(client, 2886, 9799, 70, 844, MOVE,
						"You pass through the agility shortcut.");
			}
			break;

		case 9294:
			if (client.absX < client.objectX) {
				client.getPlayerAssistant().movePlayer(client.objectX + 1,
						client.absY, 0);
				handleAgility(client, 2880, 9713, 80, 3067, MOVE,
						"You jump over the strange wall.");
			} else if (client.absX > client.objectX) {
				handleAgility(client, 2878, 9713, 80, 3067, MOVE,
						"You jump over the strange wall.");
			}
			break;

		case 9302:
			if (client.absY == 3112) {
				handleAgility(client, 2575, 3107, 16, 844, MOVE,
						"You pass through the agility shortcut.");
			}
			break;

		case 9301:
			if (client.absY == 3107) {
				handleAgility(client, 2575, 3112, 16, 844, MOVE,
						"You pass through the agility shortcut.");
			}
			break;
		case 9309:
			if (client.absY == 3309) {
				handleAgility(client, 2948, 3313, 26, 844, MOVE,
						"You pass through the agility shortcut.");
			}
			break;
		case 9310:
			if (client.absY == 3313) {
				handleAgility(client, 2948, 3309, 26, 844, MOVE,
						"You pass through the agility shortcut.");
			}
			break;
		case 2322:
			if (client.absX == 2709) {
				handleAgility(client, 2704, 3209, 10, 3067, MOVE,
						"You pass through the agility shortcut.");
			}
			break;
		case 2323:
			if (client.absX == 2705) {
				handleAgility(client, 2709, 3205, 10, 3067, MOVE,
						"You pass through the agility shortcut.");
			}
			break;
		case 2332:
			if (client.absX == 2906) {
				handleAgility(client, 4, 0, 1, 762, WALK,
						"You pass through the agility shortcut.");
			} else if (client.absX == 2910) {
				handleAgility(client, -4, 0, 1, 762, WALK,
						"You pass through the agility shortcut.");
			}
			break;
		case 3067:
			if (client.absX == 2639) {
				handleAgility(client, -1, 0, 1, 3067, WALK,
						"You pass through the agility shortcut.");
			} else if (client.absX == 2638) {
				handleAgility(client, -1, 0, 1, 3067, WALK,
						"You pass through the agility shortcut.");
			}
			break;
		case 2618:
			if (client.absY == 3492) {
				handleAgility(client, 0, +2, 1, 3067, WALK,
						"You jump over the broken fence.");
			} else if (client.absY == 3494) {
				handleAgility(client, -0, -2, 1, 3067, WALK,
						"You jump over the broken fence.");
			}
			break;
		case 5110:
			Agility.brimhavenSkippingStone(client);
			break;
		case 5111:
			Agility.brimhavenSkippingStone(client);
			break;
		case 2296:
			if (client.absX == 2603) {
				handleAgility(client, -5, 0, 1, -1, WALK,
						"You pass through the agility shortcut.");
			} else if (client.absX == 2598) {
				handleAgility(client, 5, 0, 1, -1, WALK,
						"You pass through the agility shortcut.");
			}
			break;
		}
	}
}