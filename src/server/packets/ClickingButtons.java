package server.packets;


import server.Config;
import server.Server;
import server.content.Teles;
import server.content.minigames.Barrows;
import server.content.objects.Climbing;
import server.content.objects.Flowers;
import server.content.randoms.SandwhichLady;
import server.content.skills.Cooking;
import server.content.skills.CraftingData.tanningData;
import server.content.skills.Fletching;
import server.content.skills.Herblore;
import server.content.skills.JewelryMaking;
import server.content.skills.LeatherMaking;
import server.content.skills.Smelting;
import server.content.skills.Smithing;
import server.content.skills.Tanning;
import server.items.GameItem;
import server.players.Client;
import server.players.PacketType;
import server.players.PlayerHandler;
import server.players.PlayerSave;
import server.util.CycleEvent;
import server.util.CycleEventContainer;
import server.util.CycleEventHandler;
import server.util.Misc;

/**
 * Clicking most buttons
 **/
public class ClickingButtons implements PacketType {

	@Override
	public void processPacket(final Client c, int packetType, int packetSize) {
		int actionButtonId = Misc.hexToInt(c.getInStream().buffer, 0, packetSize);
		String type = c.playerMagicBook == 0 ? "modern" : "ancient";
		//int actionButtonId = c.getInStream().readShort();
		
		if (c.isDead)
			return;
		
			Misc.println(c.playerName+ " - actionbutton: "+actionButtonId);
			c.getPlayList().handleButton(actionButtonId);
			if(c.playerIsFletching) {
				Fletching.handleFletchingClick(c, actionButtonId);
			}
			Herblore.handleHerbloreButtons(c, actionButtonId);
			LeatherMaking.craftLeather(c, actionButtonId);
			SandwhichLady.handleOptions(c, actionButtonId);
			Smelting.makeSilver(c, actionButtonId, 0);
			Climbing.handleLadderButtons(c, actionButtonId);
			Herblore.handleHerbloreButtons(c, actionButtonId);
			for (tanningData t : tanningData.values()) {
				if (actionButtonId == t.getButtonId(actionButtonId)) {
					Tanning.tanHide(c, actionButtonId);
				}
			}
		switch (actionButtonId){
		

		case 117112:
		case 50235:
		case 4140:
		if (c.newPlayer)
			return;
		c.getDH().sendOption5("Rock Crabs", "Slayer Dungeon", "Varrock Sewers", "Bandits", "-> more <-");
		c.teleAction = 1;
		break;

		case 117131:
		case 4143:
		case 50245:
			if (c.newPlayer)
				return;
		c.getDH().sendOption5("Lumbridge", "Varrock", "Falador", "Ardougne", "-> more <-");
		c.teleAction = 5;
		break;

		case 117154:
		case 4146:
		case 50253:
			if (c.newPlayer)
				return;
		c.getDH().sendOption5("Giant Mole", "King Black Dragon", "Dagannoth Lair", "Kalphite Queen", "Coming soon!"); 
		c.teleAction = 3;
		break;


		case 117186:
		case 51005:
		case 4150:
			if (c.newPlayer)
				return;
		c.getDH().sendOption5("PvP zone [SAFE]", "Mage Bank [SAFE]", "West Dragons [DANGEROUS]", "Varrock Multi [SAFE]", "Rogue's Castle [DANGEROUS]");
		c.teleAction = 4;
		break;

		case 117210:
		case 51013:
		case 6004:
			if (c.newPlayer)
				return;
		c.getDH().sendOption5("Barrows", "Castle Wars", "Fight Caves", "Duel Arena", "Coming Soon!");
		c.teleAction = 2;
		break;	

		case 72038:
		case 51039:
		//c.getPA().startTeleport(Config.TROLLHEIM_X, Config.TROLLHEIM_Y, 0, "modern");
		//c.teleAction = 8;
		break;
		
		case 75010:
			c.getPA().spellTeleport(Config.EDGEVILLE_X, Config.EDGEVILLE_Y, 0);
			break;
		
		//1st tele option
		case 9190:
		if (c.teleAction == 1) {
		//rock crabs
		c.getPA().spellTeleport(2674, 3712, 0);
		} else if (c.teleAction == 2) {
		//barrows
		c.getPA().spellTeleport(3565, 3314, 0);
		} else if (c.teleAction == 3) {
		//godwars
		c.getPA().spellTeleport(1751, 5235, 0);
		} else if (c.teleAction == 4) {
		//varrock wildy
		//c.getPA().spellTeleport(2539, 4716, 0);
		c.sendMessage("Coming soon!");
		c.getPA().closeAllWindows();
		} else if (c.teleAction == 5) {
		c.getPA().spellTeleport(3222,3218,0);
		} else if (c.teleAction == 6) {
		//lum
		c.getPA().spellTeleport(3097, 9868, 0);//3222 3218
		} else if (c.teleAction == 7) {
			//lum
			c.getPA().spellTeleport(2757, 3477, 0);//3222 3218
			}

		if (c.dialogueAction == 10) {
		c.getPA().spellTeleport(2845, 4832, 0);
		c.dialogueAction = -1;
		} else if (c.dialogueAction == 11) {
		c.getPA().spellTeleport(2786, 4839, 0);
		c.dialogueAction = -1;
		} else if (c.dialogueAction == 12) {
		c.getPA().spellTeleport(2398, 4841, 0);
		c.dialogueAction = -1;
		}
		break;
		
		//2nd tele option
		case 9191:
		if (c.teleAction == 1) {
		//tav dungeon
		c.getPA().spellTeleport(2807, 10003, 0);
		} else if (c.teleAction == 2) {
		//pest control
		c.getPA().spellTeleport(2441, 3090, 0);
		} else if (c.teleAction == 3) {
		//kbd
		c.getPA().spellTeleport(2272, 4683, 0);
		} else if (c.teleAction == 4) {
		//graveyard
		c.getPA().spellTeleport(2539, 4716, 0);
		} else if (c.teleAction == 5) {
		c.getPA().spellTeleport(3210,3424,0);
		} else if (c.teleAction == 6) {
		c.getPA().spellTeleport(2884,9798,0);	
		} else if (c.teleAction == 7) {
			c.getPA().spellTeleport(3092,3248,0);	
			}
		break;
		//3rd tele option

		case 9192:
		if (c.teleAction == 1) {
		//slayer tower
		c.getPA().spellTeleport(3237, 9859, 0);
		} else if (c.teleAction == 2) {
		//tzhaar
		c.getPA().spellTeleport(2438, 5168, 0);
		c.sendMessage("To fight Jad, enter the cave.");
		} else if (c.teleAction == 3) {
		//dag kings
		c.getPA().spellTeleport(1910, 4367, 0);
		c.sendMessage("Climb down the ladder to get into the lair.");
		} else if (c.teleAction == 4) {
		//Hillz
		c.getPA().spellTeleport(3000, 3626, 0);

		} else if (c.teleAction == 5) {
		c.getPA().spellTeleport(2964,3378,0);
		} else if (c.teleAction == 6) {
			c.getPA().spellTeleport(3428,3537,0);
		} else if (c.teleAction == 6) {
			c.getPA().spellTeleport(2884,9798,0);	
		} else if (c.teleAction == 7) {
		c.getPA().spellTeleport(2606,3093,0);	
		}
		break;
		//4th tele option
		case 9193:
		if (c.teleAction == 1) {
		//brimhaven dungeon
		c.getPA().spellTeleport(3176, 2987, 0);
		} else if (c.teleAction == 2) {
		//duel arena
		c.getPA().spellTeleport(3366, 3266, 0);
		} else if (c.teleAction == 3) {
		//chaos elemental
		c.getPA().spellTeleport(3507, 9494, 0);
		} else if (c.teleAction == 4) {
		//Fala
		c.getPA().spellTeleport(3244, 3512, 0);

		} else if (c.teleAction == 5) {
		c.getPA().spellTeleport(2662,3305,0);
		} else if (c.teleAction == 6) {
			c.getPA().spellTeleport(2710,9466,0);
			} else if (c.teleAction == 7) {
				c.getPA().spellTeleport(2813,3447,0);	
				}
		
		break;
		case 9194:
		if (c.teleAction == 1) {
		//island
			c.getDH().sendOption5("Edgeville Dungeon", "Taverley Dungeon", "Slayer Tower", "Brimhaven Dungeon", "Karamja Dungeon");
			c.teleAction = 6;
		} else if (c.teleAction == 2) {
		//last minigame spot
		c.sendMessage("Coming Soon!");
		c.getPA().closeAllWindows();
		//c.getPA().closeAllWindows();
		} else if (c.teleAction == 3) {
			c.sendMessage("Coming Soon!");
			c.getPA().closeAllWindows();
		c.getPA().closeAllWindows();
		} else if (c.teleAction == 4) {
			c.getPA().spellTeleport(3286,3918,0);	
		} else if (c.teleAction == 5) {
			c.getDH().sendOption5("Camelot", "Draynor", "Yanille", "Catherby", "Shilo Village");
			c.teleAction = 7;
		} else if (c.teleAction == 6) {
			c.getPA().spellTeleport(2844,9637,0);
			} else if (c.teleAction == 7) {
				c.getPA().spellTeleport(2827,2995,0);	
				}
		break;
		
		
		
		
		//quests
		case 28175:
			c.getCooksAssistant().showInformation();
			break;
		case 28184:
			c.getRomeoJuliet().showInformation();
			break;
		case 28167:
			c.getDoricsQuest().showInformation();
			break;
		case 28176:
			c.getGertrudesCat().showInformation();
			break;
		case 28177:
			c.getImpCatcher().showInformation();
			break;
		case 28180:
			c.getPiratesTreasure().showInformation();
			break;
		case 49228:
			c.getRestlessGhost().showInformation();
			break;
		case 2161:
			c.getSheepShearer().showInformation();
			break;
			
		
		
		case 14067:
			if (c.newPlayer)
				c.getDH().sendDialogues(5400, 2244);
			break;
			
			case 150:
				if (c.autoRet == 0)
					c.autoRet = 1;
				else 
					c.autoRet = 0;
			break;
			
			
			case 33206:
				c.getSI().attackComplex(1);
				c.getSI().selected = 0;
				break;
			case 33208:
				c.getSI().miningComplex(1);
				c.getSI().selected = 1;
				break;
			case 33211:
				c.getSI().miningComplex(1);
				c.getSI().selected = 1;
				break;
			case 33209:
				c.getSI().strengthComplex(1);
				c.getSI().selected = 1;
				break;
			case 33212:
				c.getSI().defenceComplex(1);
				c.getSI().selected = 2;
				break;
			case 33215:
				c.getSI().rangedComplex(1);
				c.getSI().selected = 3;
				break;
			case 33218:
				c.getSI().prayerComplex(1);
				c.getSI().selected = 4;
				break;
			case 33221:
				c.getSI().magicComplex(1);
				c.getSI().selected = 6;
				break;
			case 33224: // runecrafting
				c.getSI().runecraftingComplex(1);
				c.getSI().selected = 6;
				break;
			case 33210: // agility
				c.getSI().agilityComplex(1);
					c.getSI().selected = 8;
				//c.sendMessage("Skill not supported yet.");
				break;
			case 33213: // herblore
				c.getSI().herbloreComplex(1);
				c.getSI().selected = 9;
				break;
			case 33216: // theiving
				c.getSI().thievingComplex(1);
				c.getSI().selected = 10;
				break;
			case 33219: // crafting
				c.getSI().craftingComplex(1);
				c.getSI().selected = 11;
				//c.sendMessage("Skill not supported yet.");
				break;
			case 33222: // fletching
				c.getSI().fletchingComplex(1);
				c.getSI().selected = 12;
				break;
			case 47130:// slayer
				c.getSI().slayerComplex(1);
				c.getSI().selected = 13;
				break;
			case 33214: // fishing
				c.getSI().fishingComplex(1);
				c.getSI().selected = 16;
				break;
			case 33217: // cooking
				c.getSI().cookingComplex(1);
				c.getSI().selected = 17;
				break;
			case 33220: // firemaking
				c.getSI().firemakingComplex(1);
				c.getSI().selected = 18;
				break;
			case 33223: // woodcut
				c.getSI().woodcuttingComplex(1);
				c.getSI().selected = 19;
				break;
			case 54104: // farming
				c.getSI().farmingComplex(1);
				c.getSI().selected = 20;
				//c.sendMessage("Skill not supported yet.");
				break;

			case 34142: // tab 1
				c.getSI().menuCompilation(1);
				break;

			case 34119: // tab 2
				c.getSI().menuCompilation(2);
				break;

			case 34120: // tab 3
				c.getSI().menuCompilation(3);
				break;

			case 34123: // tab 4
				c.getSI().menuCompilation(4);
				break;

			case 34133: // tab 5
				c.getSI().menuCompilation(5);
				break;

			case 34136: // tab 6
				c.getSI().menuCompilation(6);
				break;

			case 34139: // tab 7
				c.getSI().menuCompilation(7);
				break;

			case 34155: // tab 8
				c.getSI().menuCompilation(8);
				break;

			case 34158: // tab 9
				c.getSI().menuCompilation(9);
				break;

			case 34161: // tab 10
				c.getSI().menuCompilation(10);
				break;

			case 59199: // tab 11
				c.getSI().menuCompilation(11);
				break;

			case 59202: // tab 12
				c.getSI().menuCompilation(12);
				break;
			case 59203: // tab 13
				c.getSI().menuCompilation(13);
				break;

			case 58253:
			//c.getPA().showInterface(15106);
			c.getItems().writeBonus();
			break;
			
			case 59004:
			c.getPA().removeAllWindows();
			break;
			
		
			
			
			
				
				
			case 1093:
			case 1094:
			case 1097:
				if (c.autocastId > 0) {
					c.getPlayerAssistant().resetAutocast();
				} else {
					if (c.playerMagicBook == 1) {
						if (c.playerEquipment[c.playerWeapon] == 4675) {
							c.setSidebarInterface(0,
									1689);
						} else {
							c
									.sendMessage(
											"You can't autocast ancients without an ancient staff.");
						}
					} else if (c.playerMagicBook == 0) {
						if (c.playerEquipment[c.playerWeapon] == 4170) {
							c.setSidebarInterface(0,
									12050);
						} else {
							c.setSidebarInterface(0,
									1829);
						}
					}

				}
				break;

			case 9167:
				switch (c.dialogueAction) {
				case 63:
					c.getDH().sendDialogues(166, c.npcType);
					return;
				case 64:
					c.getDH().sendDialogues(173, c.npcType);
					return;
				case 60:
					c.getDH().sendDialogues(277, c.npcType);
					return;
				case 61:
					c.getDH().sendDialogues(295, c.npcType);
					return;
				case 129:
					c.getDH().sendDialogues(231, c.npcType);
					return;
				case 58:
					c.getDH().sendDialogues(540, c.npcType);
					return;
				case 68:
					c.getDH().sendDialogues(39, c.npcType);
					return;
				case 124:
					c.getDH().sendDialogues(194, c.npcType);
					return;
				case 230:
					c.getDH().sendDialogues(1053, c.npcType);
					return;
				case 251:
					c.getPlayerAssistant().openUpBank();
					c.nextChat = 0;
					return;
				case 144:
					c.getDH().sendDialogues(1314, c.npcType);
					return;
				case 502:
					c.getDH().sendDialogues(1026, c.npcType);
					return;
				case 1301: // first option haircut.
					c.getDH().sendDialogues(1302, 598);
					return;
				case 53:
					if (c.objectId == 1293 || c.objectId == 1317) {
						c.getPlayerAssistant().startTeleport(2542, 3169, 0, "modern");
					} else {
						c.sendMessage("You can't teleport there, because you are already there!");
						c.getPlayerAssistant().closeAllWindows();
					}
					return;
				case 159:
					c.getDH().sendDialogues(3161, c.npcType);
					return;
				case 167:
					c.getDH().sendDialogues(1343, c.npcType);
					return;
				case 222:
					c.getDH().sendDialogues(911, c.npcType);
					c.dialogueAction = -1;
					return;
				case 182:
					c.getDH().sendNpcChat1("No, I was hoping someone could help me find it though.", c.talkingNpc, "Squire");
					c.nextChat = 0;
					return;
				case 188:
					c.getDH().sendDialogues(3129, 945);
					return;
				case 185:
					c.getDH().sendDialogues(629, c.npcType);
					return;
				}
				c.dialogueAction = 0;
				c.getPlayerAssistant().removeAllWindows();
				break;

			case 9168:
				switch (c.dialogueAction) {
				case 63:
					c.getDH().sendDialogues(167, c.npcType);
					return;
				case 64:
					c.getDH().sendDialogues(174, c.npcType);
					return;
				case 60:
					c.getDH().sendDialogues(279, c.npcType);
					return;
				case 61:
					c.getDH().sendDialogues(297, c.npcType);
					return;
				case 124:
					c.getDH().sendDialogues(192, c.npcType);
					return;
				case 126:
					c.getDH().sendDialogues(203, c.npcType);
					return;
				case 58:
					c.getDH().sendDialogues(538, c.npcType);
					return;
				case 68:
					c.getDH().sendDialogues(40, c.npcType);
					return;
				case 230:
					c.getDH().sendDialogues(1049, c.npcType);
					break;
				case 251:
					//c.getBankPin().bankPinSettings();
					c.nextChat = 0;
					return;
				case 502:
					c.getDH().sendDialogues(1022, c.npcType);
					return;
				case 1301:
					c.getDH().sendDialogues(1308, 598);
					return;
				case 144:
					c.getDH().sendDialogues(1315, c.npcType);
					return;
				case 53:
					if (c.objectId == 1294 || c.objectId == 1317) {
						c.getPlayerAssistant().startTeleport(2461, 3444, 0,
								"modern");
					} else {
						c.sendMessage("You can't teleport there, because you are already there!");
						c.getPlayerAssistant().closeAllWindows();
					}
					return;
				case 159:
					c.getDH().sendDialogues(3195, c.npcType);
					return;
				case 167:
					c.getDH().sendDialogues(1344, c.npcType);
					return;
				case 222:
					c.getDH().sendDialogues(912, c.npcType);
					c.dialogueAction = -1;
					return;
				case 182:
					c.getDH().sendDialogues(615, c.npcType);
					return;
				case 188:
					c.getDH().sendDialogues(3130, 945);
					return;
				case 185:
					c.getDH().sendDialogues(628, c.npcType);
					return;
				}
				c.dialogueAction = 0;
				c.getPlayerAssistant().removeAllWindows();
				break;

			case 9169:
				if (c.newPlayer && c.dialogueAction == 13) {
					c.newPlayer = false;
					c.crown = 8;
					c.gameMode = 2;
					c.getDH().sendDialogues(5402, 2244);
					c.sendMessage("Talk to the Lumbridge guide for server related questions");
					c.sendMessage("You are a @red@hardcore iron man@bla@. Don't die! (relog to see icon)");
					for (int j = 0; j < Server.playerHandler.players.length; j++) {
						if (Server.playerHandler.players[j] != null) {
							Client c2 = (Client)Server.playerHandler.players[j];
							c2.sendMessage("@blu@[Welcome] @bla@Say hi to our newest player, @blu@" + c.playerName + "!");
						}
					}
				}
				switch (c.dialogueAction) {
				case 63:
					c.getDH().sendDialogues(168, c.npcType);
					return;
				case 64:
					c.getDH().sendDialogues(175, c.npcType);
					return;
				case 60:
					c.getDH().sendDialogues(278, c.npcType);
					return;
				case 61:
					c.getDH().sendDialogues(296, c.npcType);
					return;
				case 53:
					if (c.objectId == 1294 || c.objectId == 1293) {
						c.getPlayerAssistant().startTeleport(3179, 3507, 0,
								"modern");
					} else {
						c.sendMessage("You can't teleport there, because you are already there!");
						c.getPlayerAssistant().closeAllWindows();
					}
					return;
				case 129:
					c.getDH().sendDialogues(232, c.npcType);
					return;
				case 126:
					c.getDH().sendDialogues(204, c.npcType);
					return;
				case 144:
					c.getDH().sendDialogues(1316, c.npcType);
					return;
				case 124:
					c.getDH().sendDialogues(3193, 741);
					return;
				case 58:
					c.getDH().sendDialogues(539, c.npcType);
					return;
				case 68:
					c.getDH().sendDialogues(41, c.npcType);
					return;
				case 230:
					c.getDH().sendDialogues(1050, c.npcType);
					break;
				case 251:
					c.getDH().sendDialogues(1015, 494);
					return;
				case 502:
					c.getDH().sendDialogues(1025, c.npcType);
					return;
				case 1301:
					c.getDH().sendDialogues(1306, 598);
					return;
				case 222:
					c.getDH().sendDialogues(913, c.npcType);
					c.dialogueAction = -1;
					return;
				case 167:
					c.getDH().sendDialogues(1342, c.npcType);
					return;
				case 159:
					c.getDH().sendDialogues(3160, c.npcType);
					return;
				case 182:
					c.getDH().sendNpcChat1("Of course he is angry...", c.talkingNpc, "Squire");
					c.nextChat = 0;
					return;
				case 188:
					c.getDH().sendDialogues(3131, 945);
					return;
				case 185:
					c.getDH().sendDialogues(630, c.npcType);
					return;
				}
				c.dialogueAction = 0;
				c.getPlayerAssistant().removeAllWindows();
				break;

			case 9157:// barrows tele to tunnels
				if (c.dialogueAction == 1236)	{
					c.needsNewTask = true;
					c.getSlayer().generateTask();
					
				}
				if (c.dialogueAction == 143) {
					c.getDH().sendDialogues(1232, c.npcType);
				}
				if (c.dialogueAction == 1) {
					int r = 4;
					// int r = Misc.random(3);

					switch (r) {
					case 0:
						c.getPlayerAssistant().movePlayer(3534, 9677, 0);
						break;

					case 1:
						c.getPlayerAssistant().movePlayer(3534, 9712, 0);
						break;

					case 2:
						c.getPlayerAssistant().movePlayer(3568, 9712, 0);
						break;

					case 3:
						c.getPlayerAssistant().movePlayer(3568, 9677, 0);
						break;
					case 4:
						c.getPlayerAssistant().movePlayer(3551, 9694, 0);
						break;
					}
				} else if (c.dialogueAction == 2) {
					c.getPlayerAssistant().movePlayer(2507, 4717, 0);
				} else if (c.dialogueAction == 7) {
					c.getPlayerAssistant().startTeleport(3088, 3933, 0, "modern");
					c.sendMessage(
							"NOTE: You are now in the wilderness...");
				} else if (c.dialogueAction == 8) {
					c.getPlayerAssistant().resetBarrows();
					c.sendMessage(
							"Your barrows have been reset.");
				} else if (c.dialogueAction == 29) {
					c.getDH().sendDialogues(480, c.npcType);
					return;
				} else if (c.dialogueAction == 30) {
					c.getDH().sendDialogues(488, c.npcType);
					return;
				} else if (c.dialogueAction == 34) {
					c.getDH().sendDialogues(361, c.npcType);
					return;
				} else if (c.dialogueAction == 50) {
					c.getPlayerAssistant().startTeleport(2898, 3562, 0,
							"modern");
					Teles.necklaces(c);
					return;
				} else if (c.dialogueAction == 55) {
					c.getDH().sendDialogues(91, c.npcType);
					return;
				} else if (c.dialogueAction == 56) {
					c.getDH().sendDialogues(96, c.npcType);
					return;
				} else if (c.dialogueAction == 57) {
					c.getDH().sendDialogues(57, c.npcType);
					return;
				} else if (c.dialogueAction == 3222) {
					//Barrows.objectAction(c, actionButtonId);
					c.sendMessage("check coffin");
					c.getPlayerAssistant().removeAllWindows();
					return;
				} else if (c.dialogueAction == 3218) {
					c.getDH().sendDialogues(3219, 0);
					return;
				} else if (c.dialogueAction == 65) {
					c.getDH().sendDialogues(179, c.npcType);
					return;
				} else if (c.dialogueAction == 66) {
					c.getDH().sendDialogues(182, c.npcType);
					return;
				} else if (c.dialogueAction == 67) {
					c.getDH().sendDialogues(36, c.npcType);
					return;
				} else if (c.dialogueAction == 68) {
					c.getDH().sendDialogues(587, c.npcType);
					return;
				} else if (c.dialogueAction == 70) {
					c.getDH().sendDialogues(1009, c.npcType);
					return;
				} else if (c.dialogueAction == 71) {
					c.getDH().sendDialogues(556, c.npcType);
					return;
				} else if (c.dialogueAction == 72) {
					c.getDH().sendDialogues(563, c.npcType);
					return;
				} else if (c.dialogueAction == 73) {
					c.getDH().sendDialogues(579, c.npcType);
					return;
				} else if (c.dialogueAction == 74) {
					c.getDH().sendDialogues(534, c.npcType);
					return;
				} else if (c.dialogueAction == 90) {
					c.getDH().sendDialogues(12, c.npcType);
					return;
				} else if (c.dialogueAction == 91) {
					c.getDH().sendDialogues(16, c.npcType);
					return;
				} else if (c.dialogueAction == 92) {
					c.getDH().sendDialogues(9, c.npcType);
					return;
				} else if (c.dialogueAction == 93) {
					c.getDH().sendDialogues(23, c.npcType);
					return;
				} else if (c.dialogueAction == 118) {
					c.getDH().sendDialogues(394, c.npcType);
					return;
				} else if (c.dialogueAction == 119) {
					c.getDH().sendDialogues(399, c.npcType);
					return;
				} else if (c.dialogueAction == 120) {
					c.getDH().sendDialogues(406, c.npcType);
					return;
				} else if (c.dialogueAction == 121) {
					c.getDH().sendDialogues(438, c.npcType);
					return;
				} else if (c.dialogueAction == 125) {
					c.getDH().sendDialogues(154, c.npcType);
					return;
				} else if (c.dialogueAction == 127) {
					c.getDH().sendDialogues(210, c.npcType);
					return;
				} else if (c.dialogueAction == 128) {
					c.getDH().sendDialogues(223, c.npcType);
					return;
				} else if (c.dialogueAction == 130) {
					c.getDH().sendDialogues(594, c.npcType);
					return;
				} else if (c.dialogueAction == 132) {
					c.getDH().sendDialogues(1013, c.npcType);
				} else if (c.dialogueAction == 133) {
					c.getDH().sendDialogues(1016, c.npcType);
				} else if (c.dialogueAction == 140) {
					c.getDH().sendDialogues(198, c.npcType);
					return;
				} else if (c.dialogueAction == 141) {
					c.getDH().sendDialogues(1020, c.npcType);
					return;
				} else if (c.dialogueAction == 143) {
					c.getDH().sendDialogues(1232, c.npcType);
					return;
				} else if (c.dialogueAction == 168) {
					c.getDH().sendDialogues(476, c.npcType);
					return;
				} else if (c.dialogueAction == 508) {
					c.getDH().sendDialogues(1026, c.npcType);
					return;
				} else if (c.dialogueAction == 855) {
					c.getItemAssistant().removeAllItems();
				} else if (c.dialogueAction == 146) {
					c.getDH().sendDialogues(1325, c.npcType);
					return;
				} else if (c.dialogueAction == 177) {
					c.getDH().sendDialogues(1376, c.npcType);
					return;
				} else if (c.dialogueAction == 151) {
					c.getDH().sendDialogues(2998, c.npcType);
					return;
				} else if (c.dialogueAction == 152) {
					c.getDH().sendDialogues(3121, c.npcType);
					return;
				} else if (c.dialogueAction == 154) {
					c.getDH().sendDialogues(3137, c.npcType);
					return;
				} else if (c.dialogueAction == 155) {
					c.getDH().sendDialogues(3142, c.npcType);
					return;
				} else if (c.dialogueAction == 156) {
					c.getDH().sendDialogues(3147, c.npcType);
					return;
				} else if (c.dialogueAction == 157) {
					c.getDH().sendDialogues(3153, c.npcType);
					return;
				} else if (c.dialogueAction == 158) {
					c.getDH().sendDialogues(3156, c.npcType);
					return;
				} else if (c.dialogueAction == 3111) {
					c.getDH().sendDialogues(3112, 946);
					return;
				} else if (c.dialogueAction == 162) {
					c.getDH().sendDialogues(3170, c.npcType);
					return;
				} else if (c.dialogueAction == 163) {
					c.getDH().sendDialogues(3129, c.npcType);
					return;
				} else if (c.dialogueAction == 164) {
					c.getDH().sendDialogues(3177, 510);
					return;
				} else if (c.dialogueAction == 165) {
					c.getDH().sendDialogues(3182, 510);
					return;
				} else if (c.dialogueAction == 166) {
					c.getDH().sendDialogues(1340, c.npcType);
					return;
				} else if (c.dialogueAction == 170) {
					c.getDH().sendDialogues(1348, c.npcType);
					return;
				} else if (c.dialogueAction == 171) {
					c.getDH().sendDialogues(1352, c.npcType);
					return;
				} else if (c.dialogueAction == 172) {
					c.getDH().sendDialogues(1355, c.npcType);
					return;
				} else if (c.dialogueAction == 173) {
					c.getDH().sendDialogues(1360, c.npcType);
					return;
				} else if (c.dialogueAction == 175) {
					c.getDH().sendDialogues(3192, c.npcType);
					return;
				} else if (c.dialogueAction == 176) {
					c.getDH().sendDialogues(1372, c.npcType);
					return;
				} else if (c.dialogueAction == 178) {
					c.getDH().sendDialogues(3186, c.npcType);
					return;
				} else if (c.dialogueAction == 179) {
					c.getDH().sendDialogues(1380, c.npcType);
					return;
				} else if (c.dialogueAction == 180) {
					c.getDH().sendDialogues(3197, c.npcType);
					return;
				} else if (c.dialogueAction == 181) {
					c.getDH().sendDialogues(612, c.npcType);
					return;
				} else if (c.dialogueAction == 183) {
					c.getDH().sendDialogues(620, c.npcType);
					return;
				} else if (c.dialogueAction == 184) {
					c.getDH().sendDialogues(624, c.npcType);
					return;
				} else if (c.dialogueAction == 186) {
					c.getItemAssistant().deleteItem2(1929, 1);
					c.getItemAssistant().deleteItem2(1933, 1);
					c.getItemAssistant().addItem(1953, 1);
					c.getItemAssistant().addItem(1925, 1);
					c.getItemAssistant().addItem(1931, 1);
					c.getPlayerAssistant().addSkillXP(1, c.playerCooking);
					c.nextChat = 0;
				} else if (c.dialogueAction == 187) {
					c.getItemAssistant().deleteItem2(1933, 1);
					c.getItemAssistant().deleteItem2(1937, 1);
					c.getItemAssistant().addItem(1953, 1);
					c.getItemAssistant().addItem(1925, 1);
					c.getItemAssistant().addItem(1935, 1);
					c.getPlayerAssistant().addSkillXP(1, c.playerCooking);
					c.nextChat = 0;
				} else if (c.dialogueAction == 189) {
					c.getDH().sendDialogues(3210, c.npcType);
					return;
				} else if (c.dialogueAction == 161) {// rod
					c.getPlayerAssistant().startTeleport(3313, 3234, 0, "modern");
					Teles.necklaces(c);
					return;
				}
				c.dialogueAction = 0;
				c.getPlayerAssistant().removeAllWindows();
				break;

			case 9158:
				if (c.dialogueAction == 8) {
					c.getPlayerAssistant().fixAllBarrows();
				} else if (c.dialogueAction == 29) {
					c.getDH().sendDialogues(481, c.npcType);
					return;
				} else if (c.dialogueAction == 34) {
					c.getDH().sendDialogues(359, c.npcType);
					return;
				} else if (c.dialogueAction == 50) {
					c.getPlayerAssistant().startTeleport(2545, 3569, 0, "modern");
					Teles.necklaces(c);
					return;
				} else if (c.dialogueAction == 55) {
					c.getDH().sendDialogues(92, c.npcType);
					return;
				} else if (c.dialogueAction == 56) {
					c.getDH().sendDialogues(95, c.npcType);
					return;
				} else if (c.dialogueAction == 74) {
					c.getDH().sendDialogues(535, c.npcType);
					return;
				} else if (c.dialogueAction == 57) {
					c.getDH().sendDialogues(58, c.npcType);
					return;
				} else if (c.dialogueAction == 62) {
					c.getDH().sendDialogues(309, c.npcType);
					return;
				} else if (c.dialogueAction == 67) {
					c.getDH().sendDialogues(35, c.npcType);
					return;
				} else if (c.dialogueAction == 68) {
					c.getDH().sendDialogues(586, c.npcType);
					return;
				} else if (c.dialogueAction == 71) {
					c.getDH().sendDialogues(582, c.npcType);
					return;
				} else if (c.dialogueAction == 72) {
					c.getDH().sendDialogues(562, c.npcType);
					return;
				} else if (c.dialogueAction == 73) {
					c.getDH().sendDialogues(580, c.npcType);
					return;
				} else if (c.dialogueAction == 90) {
					c.getDH().sendDialogues(13, c.npcType);
					return;
				} else if (c.dialogueAction == 91) {
					c.getDH().sendDialogues(17, c.npcType);
					return;
				} else if (c.dialogueAction == 118) {
					c.getDH().sendDialogues(392, c.npcType);
					return;
				} else if (c.dialogueAction == 119) {
					c.getDH().sendDialogues(404, c.npcType);
					return;
				} else if (c.dialogueAction == 120) {
					c.getDH().sendDialogues(404, c.npcType);
					return;
				} else if (c.dialogueAction == 121) {
					c.getDH().sendDialogues(437, c.npcType);
					return;
				} else if (c.dialogueAction == 125) {
					c.getDH().sendDialogues(163, c.npcType);
					return;
				} else if (c.dialogueAction == 130) {
					c.getDH().sendDialogues(593, c.npcType);
					return;
				} else if (c.dialogueAction == 131) {
					JewelryMaking.mouldInterface(c);
					return;
				} else if (c.dialogueAction == 141) {
					c.getDH().sendDialogues(1021, c.npcType);
					return;
				} else if (c.dialogueAction == 143) {
					c.getDH().sendDialogues(1233, c.npcType);
					return;
				} else if (c.dialogueAction == 161) {// rod
					c.getPlayerAssistant().startTeleport(2441, 3090, 0, "modern");
					Teles.necklaces(c);
					return;
				} else if (c.dialogueAction == 508) {
					c.getDH().sendDialogues(1025, c.npcType);
					return;
				} else if (c.dialogueAction == 146) {
					c.getDH().sendDialogues(1324, c.npcType);
					return;
				} else if (c.dialogueAction == 177) {
					c.getDH().sendDialogues(1375, c.npcType);
					return;
				} else if (c.dialogueAction == 21) {
					Flowers.harvestFlower(c, Flowers.lastObject);
					c.getPlayerAssistant().removeAllWindows();
				} else if (c.dialogueAction == 3111) {
					c.getDH().sendDialogues(3117, 946);
					return;
				} else if (c.dialogueAction == 152) {
					c.getDH().sendDialogues(3120, c.npcType);
					return;
				} else if (c.dialogueAction == 151) {
					c.getDH().sendDialogues(3000, c.npcType);
					c.getPlayerAssistant().removeAllWindows();
					return;
				} else if (c.dialogueAction == 154) {
					c.getDH().sendDialogues(3135, c.npcType);
					return;
				} else if (c.dialogueAction == 155) {
					c.getDH().sendDialogues(3141, c.npcType);
					return;
				} else if (c.dialogueAction == 156) {
					c.getDH().sendDialogues(3146, c.npcType);
					return;
				} else if (c.dialogueAction == 157) {
					c.getDH().sendDialogues(3152, c.npcType);
					return;
				} else if (c.dialogueAction == 158) {
					c.getDH().sendDialogues(3157, c.npcType);
					return;
				} else if (c.dialogueAction == 162) {
					c.getDH().sendDialogues(3169, c.npcType);
					return;
				} else if (c.dialogueAction == 163) {
					c.getDH().sendDialogues(3131, c.npcType);
					return;
				} else if (c.dialogueAction == 164) {
					c.getDH().sendDialogues(3175, c.npcType);
					return;
				} else if (c.dialogueAction == 165) {
					c.getDH().sendDialogues(3180, c.npcType);
					return;
				} else if (c.dialogueAction == 166) {
					c.getDH().sendDialogues(1339, c.npcType);
					return;
				} else if (c.dialogueAction == 168) {
					c.getDH().sendDialogues(1337, c.npcType);
					return;
				} else if (c.dialogueAction == 170) {
					c.getDH().sendDialogues(1347, c.npcType);
					return;
				} else if (c.dialogueAction == 171) {
					c.getDH().sendDialogues(1351, c.npcType);
					return;
				} else if (c.dialogueAction == 172) {
					c.getDH().sendDialogues(1356, c.npcType);
					return;
				} else if (c.dialogueAction == 173) {
					c.getDH().sendDialogues(1361, c.npcType);
					return;
				} else if (c.dialogueAction == 175) {
					c.getDH().sendDialogues(3191, c.npcType);
					return;
				} else if (c.dialogueAction == 176) {
					c.getDH().sendDialogues(1371, c.npcType);
					return;
				} else if (c.dialogueAction == 178) {
					c.getDH().sendDialogues(3185, c.npcType);
					return;
				} else if (c.dialogueAction == 179) {
					c.getDH().sendDialogues(1381, c.npcType);
					return;
				} else if (c.dialogueAction == 180) {
					c.getDH().sendDialogues(3199, c.npcType);
					return;
				} else if (c.dialogueAction == 181) {
					c.getDH().sendNpcChat1("No I like my job as Squire, I just need some help.", c.talkingNpc, "Squire");
					c.nextChat = 0;
					return;
				} else if (c.dialogueAction == 183) {
					c.getDH().sendPlayerChat1("Well I hope you find it soon.");
					c.nextChat = 0;
					return;
				} else if (c.dialogueAction == 184) {
					c.getDH().sendPlayerChat1("No, I've got lots of mining work to do.");
					c.nextChat = 0;
					return;
				} else if (c.dialogueAction == 186) {
					c.getItemAssistant().deleteItem2(1929, 1);
					c.getItemAssistant().deleteItem2(1933, 1);
					c.getItemAssistant().addItem(2307, 1);
					c.getItemAssistant().addItem(1925, 1);
					c.getItemAssistant().addItem(1931, 1);
					c.getPlayerAssistant().addSkillXP(1, c.playerCooking);
					c.nextChat = 0;
				} else if (c.dialogueAction == 187) {
					c.getItemAssistant().deleteItem2(1933, 1);
					c.getItemAssistant().deleteItem2(1937, 1);
					c.getItemAssistant().addItem(1953, 1);
					c.getItemAssistant().addItem(1925, 1);
					c.getItemAssistant().addItem(1935, 1);
					c.getPlayerAssistant().addSkillXP(1, c.playerCooking);
					c.nextChat = 0;
				} else if (c.dialogueAction == 189) {
					c.getDH().sendDialogues(3212, c.npcType);
					return;
				}
				c.dialogueAction = 0;
				c.getPlayerAssistant().removeAllWindows();
				break;

			case 9178:
				if (c.newPlayer && c.dialogueAction == 13) {
					c.newPlayer = false;
					c.crown = 0;
					c.gameMode = 0;
					c.getDH().sendDialogues(5402, 2244);
					c.sendMessage("Talk to the Lumbridge guide for server related questions");
					for (int j = 0; j < Server.playerHandler.players.length; j++) {
						if (Server.playerHandler.players[j] != null) {
							Client c2 = (Client)Server.playerHandler.players[j];
							c2.sendMessage("@blu@[Welcome] @bla@Say hi to our newest player, @blu@" + c.playerName + "!");
						}
					}
				
					
					
				}
				if (c.dialogueAction == 2) {
					c.getPlayerAssistant().startTeleport(3428, 3538, 0, "modern");
				}
				if (c.dialogueAction == 122 && c.objectId == 12164 || c.objectId == 12163 || c.objectId == 12166) {//barb
					c.getPlayerAssistant().startTeleport(3112, 3410, 0, "modern");
				} else if (c.objectId == 12165) {
					if (c.dialogueAction == 122) {
					c.sendMessage("You can't take the canoe to barbarian village because you're already there!");
					c.getPlayerAssistant().handleCanoe();
					}
				}
				if (c.dialogueAction == 4) {
					c.getPlayerAssistant().startTeleport(3565, 3314, 0,
							"modern");
				}
				if (c.dialogueAction == 3) {
					c.getPlayerAssistant().startTeleport(3088, 3500, 0,
							"modern");
				}
				if (c.dialogueAction == 31) {
					c.getDH().sendDialogues(500, c.npcType);
				}
				if (c.dialogueAction == 32) {
					c.getDH().sendDialogues(340, c.npcType);
				}
				if (c.dialogueAction == 33) {
					c.getDH().sendDialogues(354, c.npcType);
				}
				if (c.dialogueAction == 35) {
					c.getDH().sendDialogues(378, c.npcType);
				}
				if (c.dialogueAction == 51) {
					c.getPlayerAssistant().gloryTeleport(3088, 3500, 0,
							"modern");
				}
				Teles.necklaces(c);
				if (c.dialogueAction == 52) {
					c.getDH().sendDialogues(52, c.npcType);
				}
				if (c.dialogueAction == 69) {
					c.getDH().sendDialogues(1005, c.npcType);
				}
				if (c.dialogueAction == 228) {
					c.getDH().sendDialogues(1045, c.npcType);
				}
				/*
				 * if (client.dialogueAction == 142)
				 * client.getDialogues().handleDialogues(1231, client.npcType);
				 */
				if (c.dialogueAction == 145) {
					c.getDH().sendDialogues(1318, c.SlayerMaster);
				}
				if (c.dialogueAction == 153) {
					c.getDH().sendDialogues(3123, c.npcType);
				}
				if (c.dialogueAction == 160) {
					c.getDH().sendDialogues(3164, c.npcType);
				}
				if (c.dialogueAction == 142) {
					c.getDH().sendDialogues(1234, c.npcType);
				}
				if (c.dialogueAction == 485) {
					c.getRangersGuild().buyArrows();
				}
				if (c.dialogueAction == 700) {
					c.getDH().sendDialogues(28, c.npcType);
				}
				break;

			case 9179:
				if (c.newPlayer && c.dialogueAction == 13) {
					c.newPlayer = false;
					c.crown = 7;
					c.gameMode = 1;
					c.getDH().sendDialogues(5402, 2244);
					c.sendMessage("Talk to the Lumbridge guide for server related questions");
					c.sendMessage("Check the general stores at cities to buy and sell things");
					c.sendMessage("You are a iron man. No trading! (relog to see icon)");
					for (int j = 0; j < Server.playerHandler.players.length; j++) {
						if (Server.playerHandler.players[j] != null) {
							Client c2 = (Client)Server.playerHandler.players[j];
							c2.sendMessage("@blu@[Welcome] @bla@Say hi to our newest player, @blu@" + c.playerName + "!");
						}
					}
				}
				if (c.dialogueAction == 2) {
					c.getPlayerAssistant().startTeleport(2884, 3395, 0,
							"modern");
				}
				if (c.dialogueAction == 122 && c.objectId == 12163 || c.objectId == 12165 || c.objectId == 12166) {//champ
					c.getPlayerAssistant().startTeleport(3203, 3343, 0, "modern");
				} else if (c.objectId == 12164) {
					if (c.dialogueAction == 122) {
					c.sendMessage("You can't take the canoe to the Champion Guild because you're already there!");
					c.getPlayerAssistant().handleCanoe();
					}
				}
				if (c.dialogueAction == 4) {
					c.getPlayerAssistant().startTeleport(2444, 5170, 0,
							"modern");
				}
				if (c.dialogueAction == 3) {
					c.getPlayerAssistant().startTeleport(3243, 3513, 0,
							"modern");
				}
				if (c.dialogueAction == 31) {
					c.getDH().sendDialogues(502, c.npcType);
				}
				if (c.dialogueAction == 32) {
					c.getDH().sendDialogues(341, c.npcType);
				}
				if (c.dialogueAction == 33) {
					c.getDH().sendDialogues(356, c.npcType);
				}
				if (c.dialogueAction == 35) {
					c.getDH().sendDialogues(376, c.npcType);
				}
				if (c.dialogueAction == 51) {
					c.getPlayerAssistant().gloryTeleport(3293, 3174, 0,
							"modern");
				}
				Teles.necklaces(c);
				if (c.dialogueAction == 52) {
					c.getDH().sendDialogues(64, c.npcType);
				}
				if (c.dialogueAction == 69) {
					c.getDH().sendDialogues(500002, c.npcType);
				}
				if (c.dialogueAction == 228) {
					c.getDH().sendDialogues(1042, c.npcType);
				}
				if (c.dialogueAction == 145) {
					c.getDH().sendDialogues(1319, c.SlayerMaster);
				}
				if (c.dialogueAction == 153) {
					c.getDH().sendDialogues(3124, c.npcType);
				}
				if (c.dialogueAction == 160) {
					c.getDH().sendDialogues(3164, c.npcType);
				}
				if (c.dialogueAction == 142) {
					c.getDH().sendDialogues(1235, c.npcType);
				}
				if (c.dialogueAction == 485) {
					c.getRangersGuild().exchangePoints();
				}
				if (c.dialogueAction == 700) {
					c.getDH().sendDialogues(29, c.npcType);
				}
				break;
				
			case 53152:
					Cooking.cookItem(c, c.cookingItem, 1,
							c.cookingObject);
				break;

			case 53151:
					Cooking.cookItem(c, c.cookingItem, 5,
							c.cookingObject);
				break;

			case 53150:
					Cooking.cookItem(c, c.cookingItem, 10, c.cookingObject);
				break;

			case 53149:
					Cooking.cookItem(c, c.cookingItem, 28,
							c.cookingObject);
				break;

			case 9180:
				if (c.newPlayer && c.dialogueAction == 13) {
					c.newPlayer = false;
					c.crown = 8;
					c.gameMode = 2;
					c.getDH().sendDialogues(5402, 2244);
					c.sendMessage("Talk to the Lumbridge guide for server related questions");
					c.sendMessage("You are a @red@hardcore iron man@bla@. Don't die! (relog to see icon)");
					for (int j = 0; j < Server.playerHandler.players.length; j++) {
						if (Server.playerHandler.players[j] != null) {
							Client c2 = (Client)Server.playerHandler.players[j];
							c2.sendMessage("@blu@[Welcome] @bla@Say hi to our newest player, @blu@" + c.playerName + "!");
						}
					}
						
				}
				if (c.dialogueAction == 2) {
					c.getPlayerAssistant().startTeleport(2471, 10137, 0,
							"modern");
				}
				if (c.dialogueAction == 69) {
					c.getDH().sendDialogues(500003, c.npcType);
				}
				if (c.dialogueAction == 122 && c.objectId == 12164 || c.objectId == 12165 || c.objectId == 12166) {//lumby
					c.getPlayerAssistant().startTeleport(3243, 3237, 0, "modern");
				} else if (c.objectId == 12163) {
					if (c.dialogueAction == 122) {
						c.sendMessage("You can't take the canoe to Lumbridge because you're already there!");
						c.getPlayerAssistant().handleCanoe();
					}
				}
				if (c.dialogueAction == 3) {
					c.getPlayerAssistant().startTeleport(3363, 3676, 0,
							"modern");
				}
				if (c.dialogueAction == 4) {
					c.getPlayerAssistant().startTeleport(2659, 2676, 0,
							"modern");
				}
				if (c.dialogueAction == 31) {
					c.getDH().sendDialogues(501, c.npcType);
				}
				if (c.dialogueAction == 32) {
					c.getDH().sendDialogues(342, c.npcType);
				}
				if (c.dialogueAction == 33) {
					c.getDH().sendDialogues(355, c.npcType);
				}
				if (c.dialogueAction == 35) {
					c.getDH().sendDialogues(377, c.npcType);
				}
				if (c.dialogueAction == 51) {
					c.getPlayerAssistant().gloryTeleport(2911, 3152, 0,
							"modern");
				}
				Teles.necklaces(c);
				if (c.dialogueAction == 52) {
					c.getDH().sendDialogues(65, c.npcType);
				}
				if (c.dialogueAction == 700) {
					c.getDH().sendDialogues(30, c.npcType);
				}
				if (c.dialogueAction == 228) {
					c.getDH().sendDialogues(1041, c.npcType);
				}
				if (c.dialogueAction == 145) {
					c.getDH().sendDialogues(1320, c.SlayerMaster);
				}
				if (c.dialogueAction == 153) {
					c.getDH().sendDialogues(3125, c.npcType);
				}
				if (c.dialogueAction == 160) {
					c.getDH().sendDialogues(3165, c.npcType);
				}
				if (c.dialogueAction == 142) {
					c.getShops().openShop(109);
				}
				if (c.dialogueAction == 485) {
					c.getRangersGuild().howAmIDoing();
				}
				if (c.dialogueAction == 69) {
					c.getDH().sendDialogues(1003, c.npcType);
				}
				break;

			case 9181:
				if (c.newPlayer && c.dialogueAction == 13) {
					c.newPlayer = false;
					c.crown = 6;
					c.gameMode = 3;
					c.getDH().sendDialogues(5402, 2244);
					c.sendMessage("Talk to the Lumbridge guide for server related questions");
					c.sendMessage("You are now an ultimate iron man. No banking! (relog to see icon)");
					for (int j = 0; j < Server.playerHandler.players.length; j++) {
						if (Server.playerHandler.players[j] != null) {
							Client c2 = (Client)Server.playerHandler.players[j];
							c2.sendMessage("@blu@[Welcome] @bla@Say hi to our newest player, @blu@" + c.playerName + "!");
						}
					}
						
				}
				if (c.dialogueAction == 2) {
					c.getPlayerAssistant().startTeleport(2669, 3714, 0, "modern");
				}
				if (c.dialogueAction == 69) {
					c.getDH().sendDialogues(500004, c.npcType);
				}
				if (c.dialogueAction == 122 && c.objectId == 12163 || c.objectId == 12164 || c.objectId == 12165) {//edge
					c.getPlayerAssistant().startTeleport(3132, 3509, 0, "modern");
				} else if (c.objectId == 12166) {
					if (c.dialogueAction == 122) {
						c.sendMessage("You can't take the canoe to Edgeville because you're already there!");
						c.getPlayerAssistant().handleCanoe();
					}
				}
				if (c.dialogueAction == 3) {
					c.getPlayerAssistant().startTeleport(2540, 4716, 0,
							"modern");
				}
				if (c.dialogueAction == 51) {
					c.getPlayerAssistant().gloryTeleport(3103, 3249, 0,
							"modern");
				}
				Teles.necklaces(c);
				if (c.dialogueAction == 52) {
					c.getDH().sendDialogues(63, c.npcType);
				}
				if (c.dialogueAction == 700) {
					c.getDH().sendDialogues(31, c.npcType);
				}
				if (c.dialogueAction == 69) {
					c.getDH().sendDialogues(1004, c.npcType);
				}
				if (c.dialogueAction == 228) {
					c.getDH().sendDialogues(1038, c.npcType);
				}
				if (c.dialogueAction == 145) {
					c.getDH().sendDialogues(1321, c.SlayerMaster);
				}
				if (c.dialogueAction == 153) {
					c.getDH().sendDialogues(3126, c.npcType);
				}
				if (c.dialogueAction == 160) {
					c.getDH().sendDialogues(3166, c.npcType);
				}
				if (c.dialogueAction == 142) {
					c.getDH().sendDialogues(1231, c.npcType);
				}
				if (c.dialogueAction == 485) {
					c.getPlayerAssistant().closeAllWindows();
				}
				if (c.dialogueAction == 700) {
					c.getDH().sendDialogues(28, c.npcType);
				}
				break;

				
				
				
			
				
			
			/**Specials**/
			case 29188:
			c.specBarId = 7636; // the special attack text - sendframe126(S P E C I A L  A T T A C K, c.specBarId);
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			
			case 29163:
			c.specBarId = 7611;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			
			case 33033:
			c.specBarId = 8505;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			
			case 29038:
			c.specBarId = 7486;
			/*if (c.specAmount >= 5) {
				c.attackTimer = 0;
				c.getCombat().attackPlayer(c.playerIndex);
				c.usingSpecial = true;
				c.specAmount -= 5;
			}*/
			c.getCombat().handleGmaulPlayer();
			c.getItems().updateSpecialBar();
			break;
			
			case 29063:
			if(c.getCombat().checkSpecAmount(c.playerEquipment[c.playerWeapon])) {
				c.gfx0(246);
				c.forcedChat("Raarrrrrgggggghhhhhhh!");
				c.startAnimation(1056);
				c.playerLevel[2] = c.getLevelForXP(c.playerXP[2]) + (c.getLevelForXP(c.playerXP[2]) * 15 / 100);
				c.getPA().refreshSkill(2);
				c.getItems().updateSpecialBar();
			} else {
				c.sendMessage("You don't have the required special energy to use this attack.");
			}
			break;
			
			case 48023:
			c.specBarId = 12335;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			
			case 29138:
			c.specBarId = 7586;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			
			case 29113:
			c.specBarId = 7561;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			
			case 29238:
			c.specBarId = 7686;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			
			/**Dueling**/			
			case 26065: // no forfeit
			case 26040:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(0);
			break;
			
			case 26066: // no movement
			case 26048:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(1);
			break;
			
			case 26069: // no range
			case 26042:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(2);
			break;
			
			case 26070: // no melee
			case 26043:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(3);
			break;				
			
			case 26071: // no mage
			case 26041:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(4);
			break;
				
			case 26072: // no drinks
			case 26045:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(5);
			break;
			
			case 26073: // no food
			case 26046:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(6);
			break;
			
			case 26074: // no prayer
			case 26047:	
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(7);
			break;
			
			case 26076: // obsticals
			case 26075:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(8);
			break;
			
			case 2158: // fun weapons
			case 2157:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(9);
			break;
			
			case 30136: // sp attack
			case 30137:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(10);
			break;	

			case 53245: //no helm
			c.duelSlot = 0;
			c.getTradeAndDuel().selectRule(11);
			break;
			
			case 53246: // no cape
			c.duelSlot = 1;
			c.getTradeAndDuel().selectRule(12);
			break;
			
			case 53247: // no ammy
			c.duelSlot = 2;
			c.getTradeAndDuel().selectRule(13);
			break;
			
			case 53249: // no weapon.
			c.duelSlot = 3;
			c.getTradeAndDuel().selectRule(14);
			break;
			
			case 53250: // no body
			c.duelSlot = 4;
			c.getTradeAndDuel().selectRule(15);
			break;
			
			case 53251: // no shield
			c.duelSlot = 5;
			c.getTradeAndDuel().selectRule(16);
			break;
			
			case 53252: // no legs
			c.duelSlot = 7;
			c.getTradeAndDuel().selectRule(17);
			break;
			
			case 53255: // no gloves
			c.duelSlot = 9;
			c.getTradeAndDuel().selectRule(18);
			break;
			
			case 53254: // no boots
			c.duelSlot = 10;
			c.getTradeAndDuel().selectRule(19);
			break;
			
			case 53253: // no rings
			c.duelSlot = 12;
			c.getTradeAndDuel().selectRule(20);
			break;
			
			case 53248: // no arrows
			c.duelSlot = 13;
			c.getTradeAndDuel().selectRule(21);
			break;
			
			
			case 26018:	
			Client o = (Client) Server.playerHandler.players[c.duelingWith];
			if(o == null) {
				c.getTradeAndDuel().declineDuel();
				return;
			}
			
			if(c.duelRule[2] && c.duelRule[3] && c.duelRule[4]) {
				c.sendMessage("You won't be able to attack the player with the rules you have set.");
				break;
			}
			c.duelStatus = 2;
			if(c.duelStatus == 2) {
				c.getPA().sendFrame126("Waiting for other player...", 6684);
				o.getPA().sendFrame126("Other player has accepted.", 6684);
			}
			if(o.duelStatus == 2) {
				o.getPA().sendFrame126("Waiting for other player...", 6684);
				c.getPA().sendFrame126("Other player has accepted.", 6684);
			}
			
			if(c.duelStatus == 2 && o.duelStatus == 2) {
				c.canOffer = false;
				o.canOffer = false;
				c.duelStatus = 3;
				o.duelStatus = 3;
				c.getTradeAndDuel().confirmDuel();
				o.getTradeAndDuel().confirmDuel();
			}
			break;
			
			case 25120:
			if(c.duelStatus == 5) {
				break;
			}
			Client o1 = (Client) Server.playerHandler.players[c.duelingWith];
			if(o1 == null) {
				c.getTradeAndDuel().declineDuel();
				return;
			}

			c.duelStatus = 4;
			if(o1.duelStatus == 4 && c.duelStatus == 4) {				
				c.getTradeAndDuel().startDuel();
				o1.getTradeAndDuel().startDuel();
				o1.duelCount = 4;
				c.duelCount = 4;
				CycleEventHandler.addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if(System.currentTimeMillis() - c.duelDelay > 800 && c.duelCount > 0) {
							if(c.duelCount != 1) {
								c.forcedChat(""+(--c.duelCount));
								c.duelDelay = System.currentTimeMillis();
							} else {
								c.damageTaken = new int[Config.MAX_PLAYERS];
								c.forcedChat("FIGHT!");
								c.duelCount = 0;
							}
						}
						if (c.duelCount == 0) {
							container.stop();
						}
					}
					@Override
					public void stop() {
					}
				}, 1);
				c.duelDelay = System.currentTimeMillis();
				o1.duelDelay = System.currentTimeMillis();
			} else {
				c.getPA().sendFrame126("Waiting for other player...", 6571);
				o1.getPA().sendFrame126("Other player has accepted", 6571);
			}
			break;
	
			
			case 4169: // god spell charge
			c.usingMagic = true;
			if(!c.getCombat().checkMagicReqs(48)) {
				break;
			}
				
			if(System.currentTimeMillis() - c.godSpellDelay < Config.GOD_SPELL_CHARGE) {
				c.sendMessage("You still feel the charge in your body!");
				break;
			}
			c.godSpellDelay	= System.currentTimeMillis();
			c.sendMessage("You feel charged with a magical power!");
			c.gfx100(c.MAGIC_SPELLS[48][3]);
			c.startAnimation(c.MAGIC_SPELLS[48][2]);
			c.usingMagic = false;
	        break;			
			
			case 74214:
			c.isRunning2 = !c.isRunning2;
			int frame = c.isRunning2 == true ? 1 : 0;
			c.getPA().sendFrame36(173,frame);
			break;
			
			case 9154:
			c.logout();
			break;
			
			case 83093: 
				c.getPA().showInterface(21172);
			break;
			
			case 83051: 
				c.getPA().removeAllWindows();
			break;
			
			case 21010:
			c.takeAsNote = true;
			break;

			case 21011:
			c.takeAsNote = false;
			break;
			
			
			
	                 
			case 9125: //Accurate
			case 6221: // range accurate
			case 22228: //punch (unarmed)
			case 48010: //flick (whip)
			case 21200: //spike (pickaxe)
			case 1080: //bash (staff)
			case 6168: //chop (axe)
			case 6236: //accurate (long bow)
			case 17102: //accurate (darts)
			case 8234: //stab (dagger)
			c.fightMode = 0;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;
			
			case 9126: //Defensive
			case 48008: //deflect (whip)
			case 22229: //block (unarmed)
			case 21201: //block (pickaxe)
			case 1078: //focus - block (staff)
			case 6169: //block (axe)
			case 33019: //fend (hally)
			case 18078: //block (spear)
			case 8235: //block (dagger)
			c.fightMode = 1;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;
			
			case 9127: // Controlled
			case 48009: //lash (whip)
			case 33018: //jab (hally)
			case 6234: //longrange (long bow)
			case 6219: //longrange
			case 18077: //lunge (spear)
			case 18080: //swipe (spear)
			case 18079: //pound (spear)
			case 17100: //longrange (darts)
			c.fightMode = 3;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;
			
			case 9128: //Aggressive
			case 6220: // range rapid
			case 22230: //kick (unarmed)
			case 21203: //impale (pickaxe)
			case 21202: //smash (pickaxe)
			case 1079: //pound (staff)
			case 6171: //hack (axe)
			case 6170: //smash (axe)
			case 33020: //swipe (hally)
			case 6235: //rapid (long bow)
			case 17101: //repid (darts)
			case 8237: //lunge (dagger)
			case 8236: //slash (dagger)
			c.fightMode = 2;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;	
			
			
			/**Prayers**/
			case 21233: // thick skin
			c.getCombat().activatePrayer(0);
			break;	
			case 21234: // burst of str
			c.getCombat().activatePrayer(1);
			break;	
			case 21235: // charity of thought
			c.getCombat().activatePrayer(2);
			break;	
			case 77100: // range
			c.getCombat().activatePrayer(3);
			break;
			case 77102: // mage
			c.getCombat().activatePrayer(4);
			break;
			case 21236: // rockskin
			c.getCombat().activatePrayer(5);
			break;
			case 21237: // super human
			c.getCombat().activatePrayer(6);
			break;
			case 21238:	// improved reflexes
			c.getCombat().activatePrayer(7);
			break;
			case 21239: //hawk eye
			c.getCombat().activatePrayer(8);
			break;
			case 21240:
			c.getCombat().activatePrayer(9);
			break;
			case 21241: // protect Item
			c.getCombat().activatePrayer(10);
			break;			
			case 77104: // 26 range
			c.getCombat().activatePrayer(11);
			break;
			case 77106: // 27 mage
			c.getCombat().activatePrayer(12);
			break;	
			case 21242: // steel skin
			c.getCombat().activatePrayer(13);
			break;
			case 21243: // ultimate str
			c.getCombat().activatePrayer(14);
			break;
			case 21244: // incredible reflex
			c.getCombat().activatePrayer(15);
			break;	
			case 21245: // protect from magic
			c.getCombat().activatePrayer(16);
			break;					
			case 21246: // protect from range
			c.getCombat().activatePrayer(17);
			break;
			case 21247: // protect from melee
			c.getCombat().activatePrayer(18);
			break;
			case 77109: // 44 range
			c.getCombat().activatePrayer(19);
			break;	
			case 77111: // 45 mystic
			c.getCombat().activatePrayer(20);
			break;				
			case 2171: // retrui
			c.getCombat().activatePrayer(21);
			break;					
			case 2172: // redem
			c.getCombat().activatePrayer(22);
			break;					
			case 2173: // smite
			c.getCombat().activatePrayer(23);
			break;
			case 77113: // chiv
			c.getCombat().activatePrayer(24);
			break;
			case 77115: // piety
			c.getCombat().activatePrayer(25);
			break;
			
			case 13092:
			Client ot = (Client) Server.playerHandler.players[c.tradeWith];
			if(ot == null) {
				c.getTradeAndDuel().declineTrade();
				c.sendMessage("Trade declined as the other player has disconnected.");
				break;
			}
			c.getPA().sendFrame126("Waiting for other player...", 3431);
			ot.getPA().sendFrame126("Other player has accepted", 3431);	
			c.goodTrade= true;
			ot.goodTrade= true;
			
			for (GameItem item : c.getTradeAndDuel().offeredItems) {
				if (item.id > 0) {
					if(ot.getItems().freeSlots() < c.getTradeAndDuel().offeredItems.size()) {					
						c.sendMessage(ot.playerName +" only has "+ot.getItems().freeSlots()+" free slots, please remove "+(c.getTradeAndDuel().offeredItems.size() - ot.getItems().freeSlots())+" items.");
						ot.sendMessage(c.playerName +" has to remove "+(c.getTradeAndDuel().offeredItems.size() - ot.getItems().freeSlots())+" items or you could offer them "+(c.getTradeAndDuel().offeredItems.size() - ot.getItems().freeSlots())+" items.");
						c.goodTrade= false;
						ot.goodTrade= false;
						c.getPA().sendFrame126("Not enough inventory space...", 3431);
						ot.getPA().sendFrame126("Not enough inventory space...", 3431);
							break;
					} else {
						c.getPA().sendFrame126("Waiting for other player...", 3431);				
						ot.getPA().sendFrame126("Other player has accepted", 3431);
						c.goodTrade= true;
						ot.goodTrade= true;
						}
					}	
				}	
				if (c.inTrade && !c.tradeConfirmed && ot.goodTrade && c.goodTrade) {
					c.tradeConfirmed = true;
					if(ot.tradeConfirmed) {
						c.getTradeAndDuel().confirmScreen();
						ot.getTradeAndDuel().confirmScreen();
						break;
					}
							  
				}

		
			break;
					
			case 13218:
			c.tradeAccepted = true;
			Client ot1 = (Client) Server.playerHandler.players[c.tradeWith];
				if (ot1 == null) {
					c.getTradeAndDuel().declineTrade();
					c.sendMessage("Trade declined as the other player has disconnected.");
					break;
				}
				
				if (c.inTrade && c.tradeConfirmed && ot1.tradeConfirmed && !c.tradeConfirmed2) {
					c.tradeConfirmed2 = true;
					if(ot1.tradeConfirmed2) {	
						c.acceptedTrade = true;
						ot1.acceptedTrade = true;
						c.getTradeAndDuel().giveItems();
						ot1.getTradeAndDuel().giveItems();
						break;
					}
				ot1.getPA().sendFrame126("Other player has accepted.", 3535);
				c.getPA().sendFrame126("Waiting for other player...", 3535);
				}
				
			break;		
			/* Rules Interface Buttons */
			case 125011: //Click agree
				if(!c.ruleAgreeButton) {
					c.ruleAgreeButton = true;
					c.getPA().sendFrame36(701, 1);
				} else {
					c.ruleAgreeButton = false;
					c.getPA().sendFrame36(701, 0);
				}
				break;
			case 125003://Accept
				if(c.ruleAgreeButton) {
					c.getPA().showInterface(3559);
					c.newPlayer = false;
				} else if(!c.ruleAgreeButton) {
					c.sendMessage("You need to click on you agree before you can continue on.");
				}
				break;
			case 125006://Decline
				c.sendMessage("You have chosen to decline, Client will be disconnected from the server.");
				break;
			/* End Rules Interface Buttons */
			/* Player Options */
			case 3146:
				if(!c.mouseButton) {
					c.mouseButton = true;
					c.getPA().sendFrame36(500, 1);
					//c.getPA().sendFrame36(170,1);
				} else if(c.mouseButton) {
					c.mouseButton = false;
					c.getPA().sendFrame36(500, 0);
					//c.getPA().sendFrame36(170,0);					
				}
				break;
			case 3189:
				if(!c.splitChat) {
					c.splitChat = true;
					c.getPA().sendFrame36(502, 1);
					//c.getPA().sendFrame36(287, 1);
				} else {
					c.splitChat = false;
					c.getPA().sendFrame36(502, 0);
					//c.getPA().sendFrame36(287, 0);
				}
				break;
			case 3147:
				if(!c.chatEffects) {
					c.chatEffects = true;
					c.getPA().sendFrame36(501, 1);
					//c.getPA().sendFrame36(171, 0);
				} else {
					c.chatEffects = false;
					c.getPA().sendFrame36(500, 0);
					//c.getPA().sendFrame36(170, 1);
				}
				break;
			case 48176:
				if(!c.acceptAid) {
					c.acceptAid = true;
					c.getPA().sendFrame36(503, 1);
					//c.getPA().sendFrame36(427, 0);
				} else {
					c.acceptAid = false;
					c.getPA().sendFrame36(503, 0);
					//c.getPA().sendFrame36(427, 1);
				}
				break;
			case 74192:
				if(!c.isRunning2) {
					c.isRunning2 = true;
					c.getPA().sendFrame36(504, 1);
					//c.getPA().sendFrame36(173, 1);
				} else {
					c.isRunning2 = false;
					c.getPA().sendFrame36(504, 0);
					//c.getPA().sendFrame36(173, 0);
				}
				break;
			case 74201://brightness1
				c.getPA().sendFrame36(505, 1);
				c.getPA().sendFrame36(506, 0);
				c.getPA().sendFrame36(507, 0);
				c.getPA().sendFrame36(508, 0);
				c.getPA().sendFrame36(166, 1);
				break;
			case 74203://brightness2
				c.getPA().sendFrame36(505, 0);
				c.getPA().sendFrame36(506, 1);
				c.getPA().sendFrame36(507, 0);
				c.getPA().sendFrame36(508, 0);
				c.getPA().sendFrame36(166,2);
				break;

			case 74204://brightness3
				c.getPA().sendFrame36(505, 0);
				c.getPA().sendFrame36(506, 0);
				c.getPA().sendFrame36(507, 1);
				c.getPA().sendFrame36(508, 0);
				c.getPA().sendFrame36(166,3);
				break;

			case 74205://brightness4
				c.getPA().sendFrame36(505, 0);
				c.getPA().sendFrame36(506, 0);
				c.getPA().sendFrame36(507, 0);
				c.getPA().sendFrame36(508, 1);
				c.getPA().sendFrame36(166,4);
				break;
			case 74206://area1
				c.getPA().sendFrame36(509, 1);
				c.getPA().sendFrame36(510, 0);
				c.getPA().sendFrame36(511, 0);
				c.getPA().sendFrame36(512, 0);
				break;
			case 74207://area2
				c.getPA().sendFrame36(509, 0);
				c.getPA().sendFrame36(510, 1);
				c.getPA().sendFrame36(511, 0);
				c.getPA().sendFrame36(512, 0);
				break;
			case 74208://area3
				c.getPA().sendFrame36(509, 0);
				c.getPA().sendFrame36(510, 0);
				c.getPA().sendFrame36(511, 1);
				c.getPA().sendFrame36(512, 0);
				break;
			case 74209://area4
				c.getPA().sendFrame36(509, 0);
				c.getPA().sendFrame36(510, 0);
				c.getPA().sendFrame36(511, 0);
				c.getPA().sendFrame36(512, 1);
				break;
			case 168:
                c.startAnimation(855);
            break;
            case 169:
                c.startAnimation(856);
            break;
            case 162:
                c.startAnimation(857);
            break;
            case 164:
                c.startAnimation(858);
            break;
            case 165:
                c.startAnimation(859);
            break;
            case 161:
                c.startAnimation(860);
            break;
            case 170:
                c.startAnimation(861);
            break;
            case 171:
                c.startAnimation(862);
            break;
            case 163:
                c.startAnimation(863);
            break;
            case 167:
                c.startAnimation(864);
            break;
            case 172:
                c.startAnimation(865);
            break;
            case 166:
                c.startAnimation(866);
            break;
            case 52050:
                c.startAnimation(2105);
            break;
            case 52051:
                c.startAnimation(2106);
            break;
            case 52052:
                c.startAnimation(2107);
            break;
            case 52053:
                c.startAnimation(2108);
            break;
            case 52054:
                c.startAnimation(2109);
            break;
            case 52055:
                c.startAnimation(2110);
            break;
            case 52056:
                c.startAnimation(2111);
            break;
            case 52057:
                c.startAnimation(2112);
            break;
            case 52058:
                c.startAnimation(2113);
            break;
            case 43092:
                c.startAnimation(0x558);
            break;
            case 2155:
                c.startAnimation(0x46B);
            break;
            case 25103:
                c.startAnimation(0x46A);
            break;
            case 25106:
                c.startAnimation(0x469);
            break;
            case 2154:
                c.startAnimation(0x468);
            break;
            case 52071:
                c.startAnimation(0x84F);
            break;
            case 52072:
                c.startAnimation(0x850);
            break;
            case 59062:
                c.startAnimation(2836);
            break;
            case 72032:
                c.startAnimation(3544);
            break;
            case 72033:
                c.startAnimation(3543);
            break;
            case 72254:
                c.startAnimation(3866);
            break;
			/* END OF EMOTES */
			
			case 24017:
				c.getPA().resetAutocast();
				//c.sendFrame246(329, 200, c.playerEquipment[c.playerWeapon]);
				c.getItems().sendWeapon(c.playerEquipment[c.playerWeapon], c.getItems().getItemName(c.playerEquipment[c.playerWeapon]));
				//c.setSidebarInterface(0, 328);
				//c.setSidebarInterface(6, c.playerMagicBook == 0 ? 1151 : c.playerMagicBook == 1 ? 12855 : 1151);
			break;
		}
		if (c.isAutoButton(actionButtonId))
			c.assignAutocast(actionButtonId);
	}

}
