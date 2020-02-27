package server.packets;


import server.content.quests.QuestRewards;
import server.content.Sailing;
import server.content.items.JewelryMaking;
import server.content.items.Tanning;
import server.players.Client;
import server.players.Player;
import server.players.PlayerAssistant;
import server.Config;
import server.Server;
import server.content.minigames.Barrows;
import server.content.minigames.BarrowsData;
import server.content.minigames.CastleWarObjects;
import server.content.minigames.CastleWars;
import server.content.npc.Shops;
import server.content.objects.Balloons;
import server.content.objects.BrimhavenVines;
import server.content.objects.ClimbOther;
import server.content.objects.Climbing;
import server.content.objects.DoubleGates;
import server.content.objects.Levers;
import server.content.objects.OtherObjects;
import server.content.objects.Pickable;
import server.content.objects.Searching;
import server.content.objects.SingleGates;
import server.content.objects.ClimbOther.ClimbData;
import server.npcs.NPC;
import server.npcs.NPCHandler;
import server.util.CycleEvent;
import server.util.CycleEventContainer;
import server.util.CycleEventHandler;
import server.util.Misc;
import server.util.ScriptManager;
import server.objects.Object;
import server.content.skills.*;
import server.content.skills.impl.Agility;
import server.content.skills.impl.Fishing;
import server.content.skills.impl.Mining;
import server.content.skills.impl.Runecrafting;
import server.content.skills.impl.Smelting;
import server.content.skills.impl.Thieving;
import server.packets.*;

public class ActionHandler {
	
	private Client c;
	
	public ActionHandler(Client Client) {
		this.c = Client;
	}
	
	private boolean obj(int obX, int obY) {
        return c.objectX == obX && c.objectY == obY;
}
	
	
	
	public void firstClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		c.turnPlayerTo(obX, obY);
		
		if (objectType >= 115 && objectType <= 121) {
			Balloons.popBalloon(c, obX, obY);
			return;
		}
		if (objectType >= 5103 && objectType <= 5107) {
			BrimhavenVines.handleBrimhavenVines(c, objectType);
			return;
		}
		OtherObjects.searchSpecialObject(c, objectType);
		Searching.searchObject(c, objectType);
		Levers.pullLever(c, objectType);
		Thieving.lockedDoor(c, objectType);
		
		if (Mining.miningRocks(c, objectType)) {
			Mining.attemptData(c, objectType, obX, obY);
			return;
		}
		
		

		//castlewars
		if(CastleWarObjects.handleObject(c, objectType, obX, obY))
			return;
		
		if(Barrows.barrowObjects(c, objectType)) {
			Barrows.objectAction(c, objectType);
			return;
		}

		SingleGates.useSingleGate(c, objectType);
		DoubleGates.useDoubleGate(c, objectType);
		switch(objectType) {
		
		
	
		    
		case 9330:
		case 9328:
		case 9293:
		case 11844:
		case 9301:
		case 9302:
		case 2322:
		case 2323:
		case 2296:
		case 5100:
		case 5110:
		case 5111:
		case 14922:
		case 3067:
		case 9309:
		case 9310:
		case 2618:
		case 2332:
		case 5088:
		case 5090:
		case 4615:
		case 4616:
		case 3933:
		case 12127:
		case 9294:
		case 9326:
		case 9321:
		case 993:
			Agility.processAgilityShortcut(c);
			break;
			
		case 881: // manhole in varrock
		    server.Server.objectHandler.createAnObject(c, 882, obX, obY);
		    break;
		case 882:
		    c.startAnimation(828);
		    c.getPA().movePlayer(3237, 9859, 0);
		    break;
		
		
		case 4490:
		case 4487:// slayer tower doors
			if (c.absY == 3535) {
				c.getPlayerAssistant().movePlayer(c.absX,
						c.absY + 1, 0);
			} else if (c.absY == 3536) {
				c.getPlayerAssistant().movePlayer(c.absX,
						c.absY - 1, 0);
			}
			break;
			
			
		case 2079:
			if (c.getItemAssistant().playerHasItem(432, 1)) {
				c.getItemAssistant().addItem(433, 1);
				c.sendMessage(
						"All that's in the chest is a message...");
				c.pirateTreasure = 4;
			} else {
				c.sendMessage(
						"You need a key to open this chest.");
			}
			break;

		case 2071:
			if (c.pirateTreasure == 2) {
				c.getDH().sendStatement("You search the crate...");
				c.sendMessage(
						"You find a bottle of rum and 10 bananas.");
				c.getItemAssistant().addItem(431, 1);
				c.getItemAssistant().addItem(1963, 10);
				c.nextChat = 0;
			} else {
				c.sendMessage(
						"You aren't on this step right now.");
			}
			break;

		case 2593:
			c.sendMessage(
					"Disabled for dragon slayer.");
			break;

		case 2024: // WP quest
			if (c.witchspot == 2) {
				// c.getDH().sendStatement("You drink from the cauldron, it tastes horrible!",
				// "You feel yourself imbued with power.");
				c.witchspot = 3;
				QuestRewards.witchFinish(c);
			} else {
				c.sendMessage(
						"You are not on this part of the quest.");
			}
			break;

		case 2614:
			if (c.vampSlayer == 3 && c.clickedVamp == false) {
				NPCHandler.spawnNpc(c, 757, c.getX(), c.getY(), 0, 0, 50, 10, 30, 30, true, true);
				c.sendMessage("You will need a stake and hammer to attack count draynor.");
				c.clickedVamp = true;
			} else if (c.vampSlayer == 3 && c.clickedVamp == true) {
				c.sendMessage("You have already spawned the vampyre.");
				return;
			} else if (c.vampSlayer > 3) {
				c.sendMessage("You have already killed the vampire.");
			} else if (c.vampSlayer < 3) {
				c.sendMessage("You still need to progress into vampire slayer to fight this monster.");
			}
			break;

		case 2617:
			if (c.absX > 3076 && c.absX < 3079 && c.absY == 9771) {
				c.getPlayerAssistant().movePlayer(3115, 3356, 0);
			}
			break;

		case 2616:
			if (c.absX > 3114 && c.absX < 3117 && c.absY == 3356) {
				c.getPlayerAssistant().movePlayer(3077, 9771, 0);
			}
			break;

		case 10093:
		case 10094:
			if (c.getItemAssistant().playerHasItem(1927, 1)) {
				c.turnPlayerTo(c.objectX, c.objectY);
				c.startAnimation(883);
				c.getItemAssistant().addItem(2130, 1);
				c.getItemAssistant().deleteItem2(1927, 1);
				c.getPlayerAssistant()
						.addSkillXP(18, c.playerCooking);
			} else {
				c.sendMessage(
						"You need a bucket of milk to do this.");
			}
			break;

		case 2072: // crate
			if (c.getItemAssistant().playerHasItem(1963, 10)
					&& c.luthas == true) {
				c.getItemAssistant().deleteItem2(1963, 10);
				c.getDH().sendStatement(
						"You pack your bananas in the crate...");
				c.sendMessage(
						"Talk to luthas for your reward.");
				c.bananas = 2;
			} else if (c.getItemAssistant().playerHasItem(431, 1)
					&& c.pirateTreasure == 1) {
				c.getItemAssistant().deleteItem2(431, 1);
				c.getDH().sendStatement(
						"You stash your rum in the crate");
				c.pirateTreasure = 2;
			} else if (c.objectX == 2746) {
				c.sendMessage("You search the crate...");
				c.stopPlayerPacket = true;
				   CycleEventHandler.addEvent(c, new CycleEvent() {
			            @Override
			            public void execute(CycleEventContainer container) {
						c.sendMessage("You find nothing of interest.");
						container.stop();
					}

					@Override
					public void stop() {
						c.stopPlayerPacket = false;
					}
				}, 2);
			} else {
				c.getDH().sendStatement(
						"I should talk to luthas and see what to do.");
				c.sendMessage(
						"I think I need to put some bannanas in this crate.");
			}
			break;

		case 2073: // Banana tree
		case 4754:
			if (System.currentTimeMillis() - c.waitTime > 2000) {
				if (c.luthas == true) {
					c.bananas += 1;
					c.getItemAssistant().addItem(1963, 1);
					c.waitTime = System.currentTimeMillis();
				}
				c.getItemAssistant().addItem(1963, 1);
				c.waitTime = System.currentTimeMillis();
			} else {
				c.sendMessage("You must wait two seconds before grabbing another banana.");
			}
			break;

		case 2406:
			if (c.playerEquipment[c.playerWeapon] == 772) {
				c.getPlayerAssistant().startTeleport(2452, 4470, 0,
						"modern");
				c.sendMessage(
						"You are suddenly teleported away.");
			} else {
				c
						.sendMessage("This door is locked.");
			}
			break;

		case 3759://entrance
			if (c.absX == 2893 && c.absY == 3671) {
				c.getPlayerAssistant().movePlayer(2893, 10074, 0);
			}
			break;

		case 3760://exit
			if (c.absX == 2893 && c.absY == 10074) {
				c.getPlayerAssistant().movePlayer(2893, 3671, 0);
			}
			break;

	
		
		case 8143: //picking herbs
			c.getFarming().pickHerb();
			break;
		//castlewars
		case 4467:
		c.getPA().movePlayer(c.absX == 2834 ? 2385 : 2384, 3134, 0);
		break;
		
		case 4427:
		c.getPA().movePlayer(2373, c.absY == 3120 ? 3119 : 3120, 0);
		break;
		
		case 4428:
		c.getPA().movePlayer(2372, c.absY == 3120 ? 3119 : 3120, 0);
		break;
		
		case 4465:
		c.getPA().movePlayer( c.absX == 2414 ? 2415 : 2414, 3073, 0);
		break;
		
		case 4424:
		c.getPA().movePlayer(2427, c.absY == 3087 ? 3088 : 3087, 0);
		break;
		
		case 4423:
		c.getPA().movePlayer(2427, c.absY == 3087 ? 3088 : 3087, 0);
		break;
        case 4411:
        case 4415:
        case 4417:
        case 4418:
        case 4420:
        case 4469:
        case 4470:
        case 4419:
        case 4911:
        case 4912:
        case 1757:
        case 4437:
        case 6281:
        case 6280:
        case 4472:
        case 4471:
        case 4406:
        case 4407:
        case 4458:
        case 4902:
        case 4903:
        case 4900:
        case 4901:
        case 4461:
        case 4463:
        case 4464:
        case 4377:
        case 4378:
            CastleWarObjects.handleObject(c, objectType, obX, obY);
        case 1568:
        	
            if (obX == 3097 && obY == 3468)
                c.getPA().movePlayer(3097, 9868, 0);
            else
                CastleWarObjects.handleObject(c, obY, obY, obY);
            break;
            //end
            
        case 1161:
		case 2646:
		case 313:
		case 5585:
		case 5584:
		case 312:
		case 3366:
			Pickable.pickObject(c, c.objectId, c.objectX,
					c.objectY);
			break;
            
		case 1276:
			c.getWoodcutting().startWoodcutting(0, c.objectX, c.objectY, c.clickObjectType);
			break;
		case 1278:
			c.getWoodcutting().startWoodcutting(1, c.objectX, c.objectY, c.clickObjectType);
			break;
		case 1286:
			c.getWoodcutting().startWoodcutting(2, c.objectX, c.objectY, c.clickObjectType);
			break;
		case 1281:
			c.getWoodcutting().startWoodcutting(3, c.objectX, c.objectY, c.clickObjectType);
			break;
		case 1308:
			c.getWoodcutting().startWoodcutting(4, c.objectX, c.objectY, c.clickObjectType);
			break;
		case 5552:
			c.getWoodcutting().startWoodcutting(5, c.objectX, c.objectY, c.clickObjectType);
			break;
		case 1307:
			c.getWoodcutting().startWoodcutting(6, c.objectX, c.objectY, c.clickObjectType);
			break;
		case 1309:
			c.getWoodcutting().startWoodcutting(7, c.objectX, c.objectY, c.clickObjectType);
			break;
		case 1306:
			c.getWoodcutting().startWoodcutting(8, c.objectX, c.objectY, c.clickObjectType);
			break;
		case 5551:
			c.getWoodcutting().startWoodcutting(9, c.objectX, c.objectY, c.clickObjectType);
			break;
		case 5553:
			c.getWoodcutting().startWoodcutting(10, c.objectX, c.objectY, c.clickObjectType);
			break;
			
			
			
			
			
			
			
		case 96:
		case 98:
		case 1722:
		case 1723:
		case 1733:
		case 1734:
		case 1736:
		case 1737:
		case 1742:
		case 1744:
		case 1755:
		case 2405:
		case 2711:
		case 3432:
		case 3443:
		case 4383:
		case 4755:
		case 4756:
		case 4879:
		case 5492:
		case 5096:
		case 6278:
		case 11724:
		case 11725:
		case 11727:
		case 11728:
		case 11729:
		case 11731:
		case 11732:
		case 11733:
		case 11734:
		case 11735:
		case 11736:
		case 11737:
		case 12265:
		case 2147:
		case 2148:
		case 2408:
		case 6279:
		case 7257:
		case 6439:
		case 11888:
		case 11889:
		case 11890:
		case 4568:
		case 4569:
		case 4570:
		case 4413:
		case 9582:
		case 9584:
		case 5131:
		case 5130:
		case 1725:
		case 1726:
		case 6434:
		case 6436:
		case 1738:
		case 5167:
		case 12266:
		case 272:
		case 273:
		case 245:
		case 246:
		case 1767:
			Climbing.handleClimbing(c);
			break;
			
		case 190:
			if (c.absY == 3385) {
				c.getPlayerAssistant().movePlayer(c.absX, 3382, 0);
			} else if (c.absY == 3382) {
				c.getPlayerAssistant().movePlayer(c.absX, 3385, 0);
			}
		break;
			
		case 1754:
		if (c.objectX == 2696 && c.objectY == 3282) {
			c.startAnimation(827);
			c.getPlayerAssistant().removeAllWindows();
			c.getPlayerAssistant().movePlayer(2696, 9683, 0);
			c.sendMessage("You climb down.");
		} else {
			ClimbOther.useOther(c, c.objectId);
		}
		break;

		case 1759:
		case 9472:
		case 11867:
		case 100:
			ClimbOther.useOther(c, c.objectId);
			break;

		case 1739:
				Climbing.handleLadder(c);
				c.dialogueAction = 147;
			break;
			
		case 1748:
		if (c.objectX == 3286 && c.objectY == 3192) {
			Climbing.climbDown(c);
		} else {
			Climbing.handleLadder(c);
			c.dialogueAction = 147;
		}
		break;

		case 12537:
		case 2884:
		case 12965:
		case 14747:
			Climbing.handleLadder(c);
			c.dialogueAction = 147;
			break;

		case 12536:
		case 12964:
		case 1750:
		case 2833:
		case 2796:
		case 4772:
		case 1752:
		case 11739:
		case 14745:
		case 9558:
			Climbing.climbUp(c);
			break;

		case 1747:
			if (c.objectX == 2642 && c.objectY == 3428 && c.absX == 2643 && c.absY == 3429) { 
				return;
			}
			if (c.absX > 3081 && c.absX < 3085 && c.absY == 3514) {
				return;
			}
			if (c.objectX == 2532 && c.objectY == 3545) {
				c.getAgility().climbUp(c, c.getX(), c.getY(), 1);
			} else {
				Climbing.climbUp(c);
			}
			break;

		case 1740:
		case 12538:
		case 1746:
		case 4778:
		case 12966:
		case 2797:
		case 1749:
		case 11742:
		case 11741:
		case 14746:
		case 9559:
			Climbing.climbDown(c);
		break;
			
			
			
			
		case 733:
			

			c.startAnimation(451);
			if (Misc.random(4) == 1) {
				c.getPA().removeObject(c.objectX, c.objectY);
				c.sendMessage("You slash the web apart.");
			} else {
				c.sendMessage("You fail to cut through it.");
				return;
			}

			if (c.objectX == 3158 && c.objectY == 3951) {
				new Object(734, c.objectX, c.objectY, c.getHeightLevel(), 1, 10,
						733, 50);
			} else {
				new Object(734, c.objectX, c.objectY, c.getHeightLevel(), 0, 10,
						733, 50);
			}
			break;
		case 5960:
			c.getPA().startTeleport2(3090, 3956, 0);
			break;
			case 5959:
				c.getPA().startTeleport2(2539, 4712, 0);
			break;
		case 1815:
			c.getPA().startTeleport2(2561, 3311, 0);
			break;
		case 11666:
		case 3044:
		case 2781:
			Smelting.openInterface(c);
			break;
		case 492:
			c.getPA().movePlayer(2857, 9569, 0);
			break;
		case 1764:
			c.getPA().movePlayer(2857, 3167, 0);
			break;
		case 9358:
			c.getPA().movePlayer(2480, 5175, 0);
			break;
		case 9359:
			c.getPA().movePlayer(2862, 9572, 0);
			break;
		case 12467:
		case 12468:
			c.getPA().movePlayer(2974, 9511, 0);
			break;
			
		case 2643:
			JewelryMaking.mouldInterface(c);
			break;
			
		case 8930:
			c.getPA().movePlayer(1975, 4409, 3);
		break;
		
		case 10177: // Dagganoth ladder 1st level
			c.getPA().movePlayer(1798, 4407, 3);
		break;	
		
		case 10193:
			c.getPA().movePlayer(2545, 10143, 0);
		break;
		
		case 10194:
			//c.getPA().movePlayer(2544, 3741, 0); watchtower TODO
		break;
		
		case 10195: 
			c.getPA().movePlayer(1809, 4405, 2);
		break;
		
		case 10196:
			c.getPA().movePlayer(1807, 4405, 3);
		break;
		
		case 10197:
			c.getPA().movePlayer(1823, 4404, 2);
		break;
		
		case 10198:
			c.getPA().movePlayer(1825, 4404, 3);
		break;
		
		case 10199:
			c.getPA().movePlayer(1834, 4388, 2);
		break;
		
		case 10200:
			c.getPA().movePlayer(1834, 4390, 3);
		break;
	
		case 10201:
			c.getPA().movePlayer(1811, 4394, 1);
		break;
		
		case 10202:
			c.getPA().movePlayer(1812, 4394, 2);
		break;
		
		case 10203:
			c.getPA().movePlayer(1799, 4386, 2);
		break;
		
		case 10204:
			c.getPA().movePlayer(1799, 4388, 1);
		break;
		
		case 10205:
			c.getPA().movePlayer(1796, 4382, 1);
		break;
		
		case 10206:
			c.getPA().movePlayer(1796, 4382, 2);
		break;
		
		case 10207:
			c.getPA().movePlayer(1800, 4369, 2);
		break;
		
		case 10208:
			c.getPA().movePlayer(1802, 4370, 1);
		break;
		
		case 10209:
			c.getPA().movePlayer(1827, 4362, 1);
		break;
		
		case 10210:
			c.getPA().movePlayer(1825, 4362, 2);
		break;
		
		case 10211:
			c.getPA().movePlayer(1863, 4373, 2);
		break;
		
		case 10212:
			c.getPA().movePlayer(1863, 4371, 1);
		break;
		
		case 10213:
			c.getPA().movePlayer(1864, 4389, 1);
		break;
		
		case 10214:
			c.getPA().movePlayer(1864, 4387, 2);
		break;
		
		case 10215:
			c.getPA().movePlayer(1890, 4407, 0);
		break;
		
		case 10216:
			c.getPA().movePlayer(1890, 4406, 1);
		break;
		
		case 10217:
			c.getPA().movePlayer(1957, 4373, 1);
		break;
		
		case 10218:
			c.getPA().movePlayer(1957, 4371, 0);
		break;
		
		case 10219:
			c.getPA().movePlayer(1824, 4379, 3);
		break;
		
		case 10220:
			c.getPA().movePlayer(1824, 4381, 2);
		break;
		
		case 10221:
			c.getPA().movePlayer(1838, 4375, 2);
		break;
		
		case 10222:
			c.getPA().movePlayer(1838, 4377, 3);
		break;
		
		case 10223:
			c.getPA().movePlayer(1850, 4386, 1);
		break;
		
		case 10224:
			c.getPA().movePlayer(1850, 4387, 2);
		break;
		
		case 10225:
			c.getPA().movePlayer(1932, 4378, 1);
		break;
		
		case 10226:
			c.getPA().movePlayer(1932, 4380, 2);
		break;
		
		case 10227:
			if (obj(1961, 4392))
				c.getPA().movePlayer(1961, 4392, 2);
			else 
				c.getPA().movePlayer(1932, 4377, 1);
		break;
		
		case 10228:
			c.getPA().movePlayer(1961, 4393, 3);
		break;
		
		case 10229:
			c.getPA().movePlayer(1912, 4367, 0);
		break;
		
		case 10230:
			c.getPA().movePlayer(2899, 4449, 0);
		break;
		
		case 2823:
			c.getPA().movePlayer(2881, 5310, 2);
			break;
		case 12230:
			c.getPA().movePlayer(2506, 3038, 0);
			break;
		case 5099:
			if(c.playerLevel[16] < 34) {
				c.sendMessage("You need an Agility level of 34 to pass this.");
				return;
			}
			if(c.objectX == 2698 && c.objectY == 9498) {
				c.getPA().movePlayer(2698, 9492, 0);
			} else if(c.objectX == 2698 && c.objectY == 9493) {
				c.getPA().movePlayer(2698, 9499, 0);
			}
			break;
		
		case 5103:
		case 5105:
		case 5106:
		case 5107:
			if(c.getX() > obX) {
				c.getPA().walkTo(-2, 0);
			} else {
				c.getPA().walkTo(2, 0);
			}
			break;
		case 5104:
			if(c.getY() > obY) {
				c.getPA().walkTo(0, -2);
			} else {
				c.getPA().walkTo(0, 2);
			}
			break;
		case 2320:
			if(obX == 3120 && obY == 9964) {
				c.getPA().movePlayer(3120, 9970, 0);
			} else if(obX == 3120 && obY == 9969) {
				c.getPA().movePlayer(3120, 9963, 0);
			}
			break;
		case 2111:
			Mining.attemptDataGem(c, objectType, obX, obY);
			break;
			
		case 69:
	

				
		/*Abyss - By Beanerrr*/
		case 7129: //Fire Riff
			if ((c.getItems().playerHasItem(1442,1)) || (c.getItems().playerHasItem(5537)) || (c.playerEquipment[c.playerHat] == 5537)) {
				c.getPA().spellTeleport(2583, 4838, 0);
				c.getPA().addSkillXP(15 * Config.RUNECRAFTING_EXPERIENCE, Player.playerRunecrafting);
			} else {
				c.sendMessage("You need either a Talisman or a Tiara to get past this.");
			}
		break;
		case 7130: //Earth Riff
			if ((c.getItems().playerHasItem(1440,1)) || (c.getItems().playerHasItem(5535)) || (c.playerEquipment[c.playerHat] == 5535)) {
				c.getPA().spellTeleport(2660, 4839, 0);
				c.getPA().addSkillXP(15 * Config.RUNECRAFTING_EXPERIENCE, Player.playerRunecrafting);
			} else {
				c.sendMessage("You need either a Talisman or a Tiara to get past this.");
			}
		break;
		case 7131: //Body Riff
			if ((c.getItems().playerHasItem(1446,1)) || (c.getItems().playerHasItem(5533)) || (c.playerEquipment[c.playerHat] == 5533)) {
				c.getPA().spellTeleport(2527, 4833, 0);
				c.getPA().addSkillXP(15 * Config.RUNECRAFTING_EXPERIENCE, Player.playerRunecrafting);
				
			} else {
				c.sendMessage("You need either a Talisman or a Tiara to get past this.");
			}
		break;
		case 7132: //Cosmic Riff
			if ((c.getItems().playerHasItem(1454,1)) || (c.getItems().playerHasItem(5539)) || (c.playerEquipment[c.playerHat] == 5539)) {
				c.getPA().spellTeleport(2162, 4833, 0);
				
				c.getPA().addSkillXP(15 * Config.RUNECRAFTING_EXPERIENCE, Player.playerRunecrafting);
				
			} else {
				c.sendMessage("You need either a Talisman or a Tiara to get past this.");
			}
		break;
		case 7133: //Nature Riff
			if ((c.getItems().playerHasItem(1462,1)) || (c.getItems().playerHasItem(5541)) || (c.playerEquipment[c.playerHat] == 5541)) {
				c.getPA().spellTeleport(2398, 4841, 0);
				
				c.getPA().addSkillXP(15 * Config.RUNECRAFTING_EXPERIENCE, Player.playerRunecrafting);
				
			} else {
				c.sendMessage("You need either a Talisman or a Tiara to get past this.");
			}
		break;
		case 7134: //Chaos Riff
			if ((c.getItems().playerHasItem(1452,1)) || (c.getItems().playerHasItem(5543)) || (c.playerEquipment[c.playerHat] == 5543)) {
				c.getPA().spellTeleport(2269, 4843, 0);
				
				c.getPA().addSkillXP(15 * Config.RUNECRAFTING_EXPERIENCE, Player.playerRunecrafting);
				
			} else {
				c.sendMessage("You need either a Talisman or a Tiara to get past this.");
			}
		break;
		case 7135: //Law Riff
			if ((c.getItems().playerHasItem(1458,1)) || (c.getItems().playerHasItem(5545)) || (c.playerEquipment[c.playerHat] == 5545)) {
				c.getPA().spellTeleport(2464, 4834, 0);
				
				c.getPA().addSkillXP(15 * Config.RUNECRAFTING_EXPERIENCE, Player.playerRunecrafting);
				
			} else {
				c.sendMessage("You need either a Talisman or a Tiara to get past this.");
			}
		break;
		case 7136: //Death Riff
			if ((c.getItems().playerHasItem(1456,1)) || (c.getItems().playerHasItem(5547)) || (c.playerEquipment[c.playerHat] == 5547)) {
				c.getPA().spellTeleport(2207, 4836, 0);
				
				c.getPA().addSkillXP(15 * Config.RUNECRAFTING_EXPERIENCE, Player.playerRunecrafting);
				
			} else {
				c.sendMessage("You need either a Talisman or a Tiara to get past this.");
			}
		break;
		case 7137: //Water Riff
			if ((c.getItems().playerHasItem(1444,1)) || (c.getItems().playerHasItem(5531)) || (c.playerEquipment[c.playerHat] == 5531)) {
				c.getPA().spellTeleport(2713, 4836, 0);
				
				c.getPA().addSkillXP(15 * Config.RUNECRAFTING_EXPERIENCE, Player.playerRunecrafting);
				
			} else {
				c.sendMessage("You need either a Talisman or a Tiara to get past this.");
			}
		break;
		case 7138: //Soul Riff
			if ((c.getItems().playerHasItem(1460,1)) || (c.getItems().playerHasItem(5551)) || (c.playerEquipment[c.playerHat] == 5551)) {
				
				Runecrafting.craftRunes(c, 30625);
				
			} else {
				c.sendMessage("You need either a Talisman or a Tiara to get past this.");
			}
		break;
		case 7139: //Air Riff
			if ((c.getItems().playerHasItem(1438,1)) || (c.getItems().playerHasItem(5527)) || (c.playerEquipment[c.playerHat] == 5527)) {
				c.getPA().spellTeleport(2845, 4832, 0);
				
				c.getPA().addSkillXP(15 * Config.RUNECRAFTING_EXPERIENCE, Player.playerRunecrafting);
				
			} else {
				c.sendMessage("You need either a Talisman or a Tiara to get past this.");
			}
		break;
		case 7140: //Mind Riff
			if ((c.getItems().playerHasItem(1448,1)) || (c.getItems().playerHasItem(5529)) || (c.playerEquipment[c.playerHat] == 5529)) {
				c.getPA().spellTeleport(2788, 4841, 0);
				
				c.getPA().addSkillXP(15 * Config.RUNECRAFTING_EXPERIENCE, Player.playerRunecrafting);
				
			} else {
				c.sendMessage("You need either a Talisman or a Tiara to get past this.");
			}
		break;
		case 7141: //Blood Riff
			if ((c.getItems().playerHasItem(1450,1)) || (c.getItems().playerHasItem(5549)) || (c.playerEquipment[c.playerHat] == 5549)) {
				
				Runecrafting.craftRunes(c, 30624);
				
			} else {
				c.sendMessage("You need either a Talisman or a Tiara to get past this.");
			}
		break;
		/*End of Abbyss - By Beanerrr*/
		
		/*
		 * Agility
		 */
		 		case 2492:
			if (c.killCount >= 20) {
				c.getDH().sendOption4("Armadyl", "Bandos", "Saradomin", "Zamorak");
				c.dialogueAction = 20;
			} else {
				c.sendMessage("You need 20 kill count before teleporting to a boss chamber.");
			}
		break;
		case 2288:
			break;
		case 2309:
			if (c.getX() == 2998 && c.getY() == 3916) {
				c.getAgility().doWildernessEntrance(c);
			}
			break;
		case 2295:
			if (c.getX() == 2474 && c.getY() == 3436) {
				c.getAgility().doGnomeLog(c);
			}
			break;
		case 2285: //NET1
			c.getAgility().doGnomeNet1(c);
				break;
		case 2313: //BRANCH1
			c.getAgility().doGnomeBranch1(c);
				break;
		case 2312: //ROPE
			if (c.getX() == 2477 && c.getY() == 3420) {
				c.getAgility().doGnomeRope(c);
			}
				break;
			case 2314: //BRANCH2
			c.getAgility().doGnomeBranch2(c);
				break;
			case 2286: //NET2
			c.getAgility().doGnomeNet2(c);
				break;
			case 154: //PIPE1
				if (c.getX() ==  2484 && c.getY() == 3430) {
					c.getAgility().doGnomePipe1(c);
				}
				break;
			case 4058: //PIPE2
				if (c.getX() == 2487 && c.getY() == 3430) {
					c.getAgility().doGnomePipe2(c);
				}
				break;
		/*
		 * END OF AGILITY
		 * 
		 * */
		case 26933:
			if (c.objectX == 3097 && c.objectY == 3468) {
				c.getPA().movePlayer(3117, 9852, 0);
			}
			break;
		
		case 2:
			c.getPA().movePlayer(3029, 9582, 0);
			break;
			/* Shops */
		case 6839:
			c.getShops().openShop(9);
			c.sendMessage("As you look down in the chest, you see a small monkey with a money-pouch");
			c.sendMessage("next to him. I think there is where I should put the coins.");
			break;
		/*case 2492:
			if (c.objectX == 2889 && c.objectY == 4813) {
				c.getPA().startTeleport(3252, 3401, 0, "modern");
			}
			break;*/
		

                case 2557:
                     if(c.getItems().playerHasItem(1523, 1) && c.absX == 3190 && c.absY == 3957) {
                        c.getPA().movePlayer(3190, 3958, 0);
                     } else if(c.getItems().playerHasItem(1523, 1) && c.absX == 3190 && c.absY == 3958) {
                        c.getPA().movePlayer(3190, 3957, 0);
                     }
                break;

                case 2995:
                       c.getPA().startTeleport2(2717, 9801, 0);
                       c.sendMessage("Welcome to the dragon lair, be aware. It's very dangerous.");
                break;

		case 1816:
			c.getPA().startTeleport2(2271, 4680, 0);			
		break;
		case 1817:
			c.getPA().startTeleport(3067, 10253, 0, "modern");
		break;
		case 1814:
			//ardy lever
			c.getPA().startTeleport(3153, 3923, 0, "modern");
		break;

		case 2882:
		case 2883:
			if (c.objectX == 3268) {
				if (c.absX < c.objectX) {
					c.getPA().walkTo(1,0);
				} else {
					c.getPA().walkTo(-1,0);
				}
			}
		break;

		case 1765:
			c.getPA().movePlayer(3067, 10256, 0);
		break;
	

		case 1766:
		c.getPA().movePlayer(3016, 3849, 0);
		break;

		
		case 9357:
			c.getPA().resetTzhaar();
		break;
		
		case 8959:
			if (c.getX() == 2490 && (c.getY() == 10146 || c.getY() == 10148)) {
				if (c.getPA().checkForPlayer(2490, c.getY() == 10146 ? 10148 : 10146)) {
					new Object(6951, c.objectX, c.objectY, c.heightLevel, 1, 10, 8959, 15);	
				}			
			}
		break;

		case 2623:
			if (c.absX >= c.objectX)
				c.getPA().walkTo(-1,0);
			else
				c.getPA().walkTo(-1,0);
		break;
		//pc boat
		case 14315:
			c.getPA().movePlayer(2661,2639,0);
		break;
		case 14314:
			c.getPA().movePlayer(2657,2639,0);
		break;
		
		case 1596:
		case 1597:
		if (c.getY() > c.objectY)
			c.getPA().walkTo(0, -1);
		else
			c.getPA().walkTo(0, 1);
		break;
		
		case 1557:
		case 1558:
			if((c.objectX == 3106 || c.objectX == 3105) && c.objectY == 9944) {
				if (c.getY() > c.objectY)
					c.getPA().walkTo(0, -1);
				else
					c.getPA().walkTo(0, 1);
			} else {
				if (c.getX() > c.objectX)
					c.getPA().walkTo(-1, 0);
				else
					c.getPA().walkTo(1, 0);
			}
		break;
		
		case 14235:
		case 14233:
			if (c.objectX == 2670)
				if (c.absX <= 2670)
					c.absX = 2671;
				else
					c.absX = 2670;
			if (c.objectX == 2643)
				if (c.absX >= 2643)
					c.absX = 2642;
				else
					c.absX = 2643;
			if (c.absX <= 2585)
				c.absY += 1;
			else c.absY -= 1;
			c.getPA().movePlayer(c.absX, c.absY, 0);
		break;
		
	 
		
		case 9369:
			if (c.getY() > 5175)
				c.getPA().movePlayer(2399, 5175, 0);
			else
				c.getPA().movePlayer(2399, 5177, 0);
		break;
		
		case 10284:
			if(c.barrowsKillCount < 5) {
				c.sendMessage("You must kill all the brothers to receive a reward!");
				return;
			}
			if(c.barrowsKillCount == 5) {
				Barrows.spawnLastBrother(c);
			}
			if(c.barrowsKillCount > 5) {
				Barrows.refreshBrothers(c);
				BarrowsData.addLoot(c);
			}
			break;
		
		/*//barrows
		//Chest
		case 10284:
			//c.shakeScreen(3, 2, 3, 2);
			if(c.barrowsKillCount < 5) {
				c.sendMessage("You must kill all the brothers to receive a reward!");
			}
			if(c.barrowsKillCount == 5 && c.barrowsNpcs[c.randomCoffin][1] == 1) {
				c.sendMessage("I have already awakened this brother.");
			}
			if(c.barrowsNpcs[c.randomCoffin][1] == 0 && c.barrowsKillCount >= 5) {
				Server.npcHandler.spawnNpc(c, c.barrowsNpcs[c.randomCoffin][0], 3551, 9694-1, 0, 0, 120, 30, 200, 200, true, true);
				c.barrowsNpcs[c.randomCoffin][1] = 1;
			}
			if((c.barrowsKillCount > 5 || c.barrowsNpcs[c.randomCoffin][1] == 2) && c.getItems().freeSlots() >= 2) {
				c.resetShaking();
				c.getPA().resetBarrows();
				c.getItems().addItem(c.getPA().randomRunes(), Misc.random(150) + 100);
				if (Misc.random(2) == 1)
					c.getItems().addItem(c.getPA().randomBarrows(), 1);
				c.getPA().startTeleport(3564, 3288, 0, "modern");
			} else if(c.barrowsKillCount > 5 && c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need two empty slots in your inventory to receive the reward.");
			}
			break;
		//doors
		case 6749:
			if(obX == 3562 && obY == 9678) {
				c.getPA().object(3562, 9678, 6749, -3, 0);
				c.getPA().object(3562, 9677, 6730, -1, 0);
			} else if(obX == 3558 && obY == 9677) {
				c.getPA().object(3558, 9677, 6749, -1, 0);
				c.getPA().object(3558, 9678, 6730, -3, 0);
			}
			break;
		case 6730:
			if(obX == 3558 && obY == 9677) {
				c.getPA().object(3562, 9678, 6749, -3, 0);
				c.getPA().object(3562, 9677, 6730, -1, 0);
			} else if(obX == 3558 && obY == 9678) {
				c.getPA().object(3558, 9677, 6749, -1, 0);
				c.getPA().object(3558, 9678, 6730, -3, 0);
			}
			break;
		case 6727:
			if(obX == 3551 && obY == 9684) {
				c.sendMessage("You can't open this door...");
			}
			break;
		case 6746:
			if(obX == 3552 && obY == 9684) {
				c.sendMessage("You can't open this door...");
			}
			break;
		case 6748:
			if(obX == 3545 && obY == 9678) {
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if(obX == 3541 && obY == 9677) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
			break;
		case 6729:
			if(obX == 3545 && obY == 9677){
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if(obX == 3541 && obY == 9678) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
			break;
		case 6726:
			if(obX == 3534 && obY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if(obX == 3535 && obY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
			break;
		case 6745:
			if(obX == 3535 && obY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if(obX == 3534 && obY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
			break;
		case 6743:
			if(obX == 3545 && obY == 9695) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if(obX == 3541 && obY == 9694) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
			break;
		case 6724:
			if(obX == 3545 && obY == 9694) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if(obX == 3541 && obY == 9695) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
			break; 

		case 6707: // verac
			c.getPA().movePlayer(3556, 3298, 0);
			break;
			
		case 6823:
			if(server.model.minigames.barrows.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[0][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2030, c.getX(), c.getY()-1, -1, 0, 120, 25, 200, 200, true, true);
				c.barrowsNpcs[0][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 6706: // torag 
			c.getPA().movePlayer(3553, 3283, 0);
			break;
			
		case 6772:
			if(server.model.minigames.barrows.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[1][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2029, c.getX()+1, c.getY(), -1, 0, 120, 20, 200, 200, true, true);
				c.barrowsNpcs[1][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;
			
			
		case 6705: // karil stairs
			c.getPA().movePlayer(3565, 3276, 0);
			break;
		case 6822:
			if(server.model.minigames.barrows.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[2][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2028, c.getX(), c.getY()-1, -1, 0, 90, 17, 200, 200, true, true);
				c.barrowsNpcs[2][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;
			
		case 6704: // guthan stairs
			c.getPA().movePlayer(3578, 3284, 0);
			break;
		case 6773:
			if(server.model.minigames.barrows.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[3][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2027, c.getX(), c.getY()-1, -1, 0, 120, 23, 200, 200, true, true);
				c.barrowsNpcs[3][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;
			
		case 6703: // dharok stairs
			c.getPA().movePlayer(3574, 3298, 0);
			break;
		case 6771:
			if(server.model.minigames.barrows.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[4][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2026, c.getX(), c.getY()-1, -1, 0, 120, 45, 250, 250, true, true);
				c.barrowsNpcs[4][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;
			
		case 6702: // ahrim stairs
			c.getPA().movePlayer(3565, 3290, 0);
			break;
		case 6821:
			if(server.model.minigames.barrows.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[5][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2025, c.getX(), c.getY()-1, -1, 0, 90, 19, 200, 200, true, true);
				c.barrowsNpcs[5][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;*/
			
		
		
		/*case 8143:
			if (c.farm[0] > 0 && c.farm[1] > 0) {
				Farming.pickHerb(c);
			}
		break;*/
	
			// DOORS
	
		
	
		
		
		
		
		
		
		
 
 	/**
		 * Entering the Fight Caves.
		 */
		case 9356:
			c.getPA().enterCaves();
			c.sendMessage("Best of luck in the waves!");
			c.sendMessage("If something bugs, just relog!");
		break;
				
		
		/**
		 * Clicking on the Ancient Altar. 
		 */
		case 6552:
		if (c.playerMagicBook == 0) {
                        c.playerMagicBook = 1;
                        c.setSidebarInterface(6, 12855);
                        c.autocasting = false;
                        c.sendMessage("An ancient wisdomin fills your mind.");
                        c.getPA().resetAutocast();
		} else {
			c.setSidebarInterface(6, 1151); 
			c.playerMagicBook = 0;
                        c.autocasting = false;
			c.sendMessage("You feel a drain on your memory.");
			c.autocastId = -1;
			c.getPA().resetAutocast();
		}	
		break;
		
		
        /**
         * Recharing prayer points.
         */
		case 409:
			if(c.playerLevel[5] < c.getPA().getLevelForXP(c.playerXP[5])) {
				c.startAnimation(645);
				c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
				c.sendMessage("You recharge your prayer points.");
				c.getPA().refreshSkill(5);
			} else {
				c.sendMessage("You already have full prayer points.");
			}
			break;
		
		
		/**
		 * Opening the bank when clicking on bank booths, etc.
		 */
		case 2213:
		case 14367:
		case 11758:
		case 3193:
			c.getPA().openUpBank();
		break;
		
		
		/**
		 * Aquring god capes.
		 */
		case 2873:
			if (!c.getItems().ownsCape()) {
				c.startAnimation(645);
				c.sendMessage("Saradomin blesses you with a cape.");
				c.getItems().addItem(2412, 1);
			}	
		break;
		case 2875:
			if (!c.getItems().ownsCape()) {
				c.startAnimation(645);
				c.sendMessage("Guthix blesses you with a cape.");
				c.getItems().addItem(2413, 1);
			}
		break;
		case 2874:
			if (!c.getItems().ownsCape()) {
				c.startAnimation(645);
				c.sendMessage("Zamorak blesses you with a cape.");
				c.getItems().addItem(2414, 1);
			}
		break;
		
		
		/**
		 * Oblisks in the wilderness.
		 */
		case 14829:
		case 14830:
		case 14827:
		case 14828:
		case 14826:
		case 14831:
			Server.objectManager.startObelisk(objectType);
		break;
		
		
		/**
		 * Clicking certain doors.
		 */
		case 6749:
			if(obX == 3562 && obY == 9678) {
				c.getPA().object(3562, 9678, 6749, -3, 0);
				c.getPA().object(3562, 9677, 6730, -1, 0);
			} else if(obX == 3558 && obY == 9677) {
				c.getPA().object(3558, 9677, 6749, -1, 0);
				c.getPA().object(3558, 9678, 6730, -3, 0);
			}
			break;
			
		case 6730:
			if(obX == 3558 && obY == 9677) {
				c.getPA().object(3562, 9678, 6749, -3, 0);
				c.getPA().object(3562, 9677, 6730, -1, 0);
			} else if(obX == 3558 && obY == 9678) {
				c.getPA().object(3558, 9677, 6749, -1, 0);
				c.getPA().object(3558, 9678, 6730, -3, 0);
			}
			break;
			
		case 6727:
			if(obX == 3551 && obY == 9684) {
				c.sendMessage("You cant open this door..");
			}
			break;
			
		case 6746:
			if(obX == 3552 && obY == 9684) {
				c.sendMessage("You cant open this door..");
			}
			break;
			
		case 6748:
			if(obX == 3545 && obY == 9678) {
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if(obX == 3541 && obY == 9677) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
			break;
			
		case 6729:
			if(obX == 3545 && obY == 9677){
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if(obX == 3541 && obY == 9678) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
			break;
			
		case 6726:
			if(obX == 3534 && obY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if(obX == 3535 && obY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
			break;
			
		case 6745:
			if(obX == 3535 && obY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if(obX == 3534 && obY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
			break;
			
		case 6743:
			if(obX == 3545 && obY == 9695) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if(obX == 3541 && obY == 9694) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
			break;
			
		case 6724:
			if(obX == 3545 && obY == 9694) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if(obX == 3541 && obY == 9695) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
			break; 
			
		case 1516:
		case 1519:
			if (c.objectY == 9698) {
				if (c.absY >= c.objectY)
					c.getPA().walkTo(0,-1);
				else
					c.getPA().walkTo(0,1);
				break;
			}
		case 1530:
		case 1531:
		case 1533:
		case 1534:
		case 11712:
		case 11711:
		case 11707:
		case 11708:
		case 6725:
		case 3198:
		case 3197:
			Server.objectHandler.doorHandling(objectType, c.objectX, c.objectY, 0);	
			break;
		
		case 9319:
			if (c.heightLevel == 0)
				c.getPA().movePlayer(c.absX, c.absY, 1);
			else if (c.heightLevel == 1)
				c.getPA().movePlayer(c.absX, c.absY, 2);
		break;
		
		case 9320:
			if (c.heightLevel == 1)
				c.getPA().movePlayer(c.absX, c.absY, 0);
			else if (c.heightLevel == 2)
				c.getPA().movePlayer(c.absX, c.absY, 1);
		break;
		
		case 4496:
		case 4494:
			if (c.heightLevel == 2) {
				c.getPA().movePlayer(c.absX - 5, c.absY, 1);
			} else if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 0);
			}
		break;
		
		case 4493:
			if (c.heightLevel == 0) {
				c.getPA().movePlayer(c.absX - 5, c.absY, 1);
			} else if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 2);
			}
		break;
		
		case 4495:
			if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 2);
			}
		break;
		
		case 5126:
			if (c.absY == 3554)
				c.getPA().walkTo(0,1);
			else
				c.getPA().walkTo(0,-1);
		break;
		
	
	
		
		default:
			ScriptManager.callFunc("objectClick1_"+objectType, c, objectType, obX, obY);
			break;
			
		
		/**
		 * Forfeiting a duel.
		 */
		case 3203: 
			if (c.duelCount > 0) {
				c.sendMessage("You may not forfeit yet.");
				break;
			}
			Client o = (Client) Server.playerHandler.players[c.duelingWith];				
			if(o == null) {
				c.getTradeAndDuel().resetDuel();
				c.getPA().movePlayer(Config.DUELING_RESPAWN_X+(Misc.random(Config.RANDOM_DUELING_RESPAWN)), Config.DUELING_RESPAWN_Y+(Misc.random(Config.RANDOM_DUELING_RESPAWN)), 0);
				break;
			}
			if(c.duelRule[0]) {
				c.sendMessage("Forfeiting the duel has been disabled!");
				break;
			}
			if(o != null) {
				o.getPA().movePlayer(Config.DUELING_RESPAWN_X+(Misc.random(Config.RANDOM_DUELING_RESPAWN)), Config.DUELING_RESPAWN_Y+(Misc.random(Config.RANDOM_DUELING_RESPAWN)), 0);
				c.getPA().movePlayer(Config.DUELING_RESPAWN_X+(Misc.random(Config.RANDOM_DUELING_RESPAWN)), Config.DUELING_RESPAWN_Y+(Misc.random(Config.RANDOM_DUELING_RESPAWN)), 0);
				o.duelStatus = 6;
				o.getTradeAndDuel().duelVictory();
				c.getTradeAndDuel().resetDuel();
				c.getTradeAndDuel().resetDuelItems();
				o.sendMessage("The other player has forfeited the duel!");
				c.sendMessage("You forfeit the duel!");
				break;
			}
		
		
	

		}
	}
	
	public void secondClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		switch(objectType) {
		
		
		
		
		case 2090:
		case 2091:
		case 3042:
			Mining.prospectRock(c, "copper ore");
			break;
		case 2094:
		case 2095:
		case 3043:
			Mining.prospectRock(c, "tin ore");
			break;
		case 2110:
			Mining.prospectRock(c, "blurite ore");
			break;
		case 2092:
		case 2093:
			Mining.prospectRock(c, "iron ore");
			break;
		case 2100:
		case 2101:
			Mining.prospectRock(c, "silver ore");
			break;
		case 2098:
		case 2099:
			Mining.prospectRock(c, "gold ore");
			break;
		case 2096:
		case 2097:
			Mining.prospectRock(c, "coal");
			break;
		case 2102:
		case 2103:
			Mining.prospectRock(c, "mithril ore");
			break;
		case 2104:
		case 2105:
			Mining.prospectRock(c, "adamantite ore");
			break;
		case 2106:
		case 2107:
			Mining.prospectRock(c, "runite ore");
			break;
		case 10947:
			Mining.prospectRock(c, "granite");
			break;
		case 10946:
			Mining.prospectRock(c, "sandstone");
			break;
		case 2111:
			Mining.prospectRock(c, "gem rocks");
			break;
			
			
		/**
		 * Opening the bank.
		 */
	
		
		case 11338: // Bank Booth
		case 2214: // Bank Booth
		case 3045: // Bank Booth
		case 5276: // Bank Booth
		case 6084: // Bank Booth
		case 11758: // Bank Booth
		case 14367: // Bank Booth
		case 4483: // open bank chest
		case 3194: // open bank chest
		case 10517:
		case 2213:
			c.getPA().openUpBank();
		break;
		
		case 11666:
		case 3044:
		case 2781:
			Smelting.openInterface(c);
			break;
			
		}
	}
	
	
	public void thirdClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		c.sendMessage("Object type: " + objectType);
		switch(objectType) {
		default:
			ScriptManager.callFunc("objectClick3_"+objectType, c, objectType, obX, obY);
			break;
		}
	}
	
	public void firstClickNpc(int npcType) {
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		Shops.dialogueShop(c, npcType);
		switch (npcType) {
		
		
		case 309:
		case 312:
		case 313:
		case 316:
		case 326:
			Fishing.setupFishing(c, Fishing.forSpot(npcType, true));
			break;
		
		
		case 2538:
			c.getShops().openShop(145);
			break;
			
		case 2435:
			c.getShops().openShop(150);
			break;
			
		case 2059:
			c.getShops().openShop(146);
			break;	
			
		case 1187:
			c.getShops().openShop(147);
			break;
		case 1055:
			c.getShops().openShop(148);
			break;
		case 710:
			c.getShops().openShop(149);
			break;
		
		case 209:
	        	c.getDH().sendDialogues(3500, 209);
	        break;
		
		case 2238:
			c.getDH().sendDialogues(3214, npcType);
		break;
		
		case 958:
			c.getDH().sendDialogues(3208, npcType);
		break;

		case 606://squire
		if (c.knightS == 0) {
			c.getDH().sendDialogues(610, 606);
		} else if (c.knightS == 4) {
			c.getDH().sendDialogues(654, 606);
		} else if (c.knightS == 8) {
			c.getDH().sendDialogues(682, 606);
		}
		break;
		case 647://reldo
		if (c.knightS == 1) {
			c.getDH().sendDialogues(626, 647);
		}
		break;
		case 604://thurgo
		if (c.knightS == 2) {
			c.getDH().sendDialogues(640, 604);
		} else if (c.knightS == 3) {
			c.getDH().sendDialogues(648, 604);
		} else if (c.knightS == 6) {
			c.getDH().sendDialogues(660, 604);
		} else if (c.knightS == 7) {
			c.getDH().sendDialogues(669, 604);
		} else if (c.knightS == 8) {
			c.getDH().sendDialogues(674, 604);
		}
		break;
		
		case 693: //rang guild shots
			c.getDH().sendDialogues(3201, npcType);
		break;
		
		case 694: //rang guild store
			c.getShops().openShop(111);
		break;

		case 1834:
			c.getDH().sendDialogues(1378, npcType);
			break;

		case 537:
		case 536:
			if (c.questPoints >= 32) {
				c.getDH().sendDialogues(1373, npcType);
			} else {
				c.sendMessage(
						"You need " + 32
								+ " quest points to open this shop.");
			}
			break;

		
		case 663:
			c.getDH().sendDialogues(3189, npcType);
			break;

		case 802:
			c.getDH().sendDialogues(1358, npcType);
			break;

		case 2205:
			c.getDH().sendDialogues(1353, npcType);
			break;

		case 3830:
			c.getDH().sendDialogues(1349, npcType);
			break;

		case 2270:
			if (c.playerLevel[c.playerThieving] > 98) {
				c.getShops().openShop(118);
			} else if (c.playerLevel[c.playerThieving] > 49
					&& c.playerLevel[c.playerAgility] > 49) {
				c.getShops().openShop(118);
			} else {
				c.sendMessage(
						"You don't have the required skills to open this shop");
			}
			break;

		case 1071:
			c.getDH().sendDialogues(1345, npcType);
			break;

		case 666:
			c.getDH().sendDialogues(3183, npcType);
			break;

		case 510:
			if (c.absY > 3209 && c.absY < 3215) {
				c.getDH().sendDialogues(3173, npcType);
			} else {
				c.getDH().sendDialogues(3178, npcType);
			}
			break;

		case 1042:
			c.getDH().sendDialogues(3167, npcType);
			break;

		case 735:
			c.getDH().sendDialogues(3167, npcType);
			break;

		case 36:
			c.getDH().sendDialogues(3158, npcType);
			break;

		case 844:
			if (c.runeMist < 4) {
				c.getDH().sendStatement(
						"You need to beat rune mysteries first to do this.");
				c.nextChat = 0;
				return;
			}
			c.getDH().sendDialogues(3144, npcType);
			break;

		case 798:
			c.getDH().sendDialogues(3133, npcType);
			break;

		case 736:
		case 3217:
		case 3218:
			c.getDH().sendDialogues(3118, npcType);
			break;

		/*
		 * tutorial island
		 */
		case 945:
			if (c.tutorialProgress == 0) {
				c.getDH().sendDialogues(3001, npcType);
			}
			if (c.tutorialProgress == 1) {
				c.getDH().sendDialogues(3008, npcType);
			}
			if (c.tutorialProgress == 2) {
				c.getDH().sendNpcChat1("You should move on now.",
						npcType, "Miscellania-PS Guide");
			}
			break;

		case 943:// survival
			if (c.tutorialProgress == 2) {
				c.getDH().sendDialogues(3012, npcType);
			}
			if (c.tutorialProgress == 5) {
				c.getDH().sendDialogues(3017, npcType);
			}
			break;

		case 942: // master chef
			if (c.tutorialProgress == 7) {
				c.getDH().sendDialogues(3021, npcType);
			}
			break;

		case 949: // quest guide
			if (c.tutorialProgress == 12) {
				c.getDH().sendDialogues(3043, npcType);
			}
			if (c.tutorialProgress == 13) {
				c.getDH().sendDialogues(3045, npcType);
			}
			break;
		case 948: // mining tutor
			if (c.tutorialProgress == 14) {
				c.getDH().sendDialogues(3052, npcType);
			}
			if (c.tutorialProgress == 16) {
				c.getDH().sendDialogues(3056, npcType);
			}
			if (c.tutorialProgress == 20) {
				c.getDH().sendDialogues(3063, npcType);
			}
			break;
		case 944: // Combat deud
			if (c.tutorialProgress == 21) {
				c.getDH().sendDialogues(3067, npcType);
			} else if (c.tutorialProgress == 23
					&& !c.getItemAssistant().playerHasItem(1171)
					&& !c.getItemAssistant().playerHasItem(1277)) {
				c.getDH().sendDialogues(3072, npcType);
			} else if (c.getItemAssistant().playerHasItem(1171)
					&& c.getItemAssistant().playerHasItem(1277) && c.tutorialProgress == 23) {
				c.sendMessage(
						"I already gave you a sword and shield.");
				c.nextChat = 0;
				c.getDH()
						.chatboxText(
								c,
								"In your worn inventory panel, right click on the dagger and",
								"select the remove option from the drop down list. After you've",
								"unequipped the dagger, wield the sword and shield. As you",
								"pass the mouse over an item you will see its name.",
								"Unequipping items");
				PlayerAssistant.removeHintIcon(c);
			} else if (c.tutorialProgress == 25) {
				c.getDH().sendDialogues(3074, npcType);
			}
			break;

		case 947: // fiancial dude
			if (c.tutorialProgress == 27) {
				c.getDH().sendDialogues(3079, npcType);
			}
			// c.getPacketDispatcher().createArrow(1, 7);
			break;

		case 954: // prayer dude
			if (c.tutorialProgress == 28) {
				c.getDH().sendDialogues(3089, npcType);
			}
			if (c.tutorialProgress == 29) {
				c.getDH().sendDialogues(3092, npcType);
			}
			if (c.tutorialProgress == 31) {
				c.getDH().sendDialogues(3097, npcType);
			}
			break;
		case 946:// mage
			if (c.tutorialProgress == 32) {
				c.getDH().sendDialogues(3105, npcType);
			}
			if (c.tutorialProgress == 33) {
				c.getDH().sendDialogues(3108, npcType);
			}
			if (c.tutorialProgress == 34) {
				c.getDH().sendDialogues(3110, npcType);
			}
			if (c.tutorialProgress == 35) {
				c.getDH().sendDialogues(3112, npcType);
			}
			break;

		case 922:
			c.getDH().sendDialogues(1312, npcType);
			break;

		case 805:
			c.getDH().sendDialogues(1317, npcType);
			break;

		case 519:
			c.getDH().sendDialogues(15, npcType); // barrows fix
															// barrows
			break;

		case 598:
			c.getDH().sendDialogues(1300, npcType);
			break;

	

		case 1595:
			c.getDH().sendDialogues(1036, npcType);
			break;

		case 170:
			c.getDH().sendDialogues(591, npcType);
			break;

		case 925:
		case 926:
			c.getDH().sendDialogues(1018, npcType);
			break;

		case 2728:
		case 2729:
			c.getDH().sendDialogues(1011, npcType);
			break;

		case 376:
		case 377:
		case 378:
			if (c.getItemAssistant().playerHasItem(995, 30)) {
				c.getDH().sendDialogues(33, npcType);
			} else {
				c.getDH().sendStatement(
						"You need 30 coins to travel on this ship.");
			}
			break;

		case 380:
			if (c.getItemAssistant().playerHasItem(995, 30)) {
				c.getDH().sendDialogues(584, npcType);
			} else {
				c.getDH().sendStatement(
						"You need 30 coins to travel on this ship.");
			}
			break;

		/**
		 * Start of Quests
		 */

		case 557:
			if (c.ptjob == 0) {
				c.getDH().sendDialogues(37, npcType);
			} else if (c.ptjob == 1) {
				c.getDH().sendDialogues(47, npcType);
			} else if (c.ptjob == 2) {
				c.getDH().sendDialogues(1000, npcType);
			}
			break;

		case 375:
			if (c.pirateTreasure == 0) {
				c.getDH().sendDialogues(554, npcType);
			} else if (c.pirateTreasure == 1) {
				c.getDH().sendStatement(
						"Talk to lucas and help him transport the bannanas.");
			} else if (c.pirateTreasure == 2) {
				c.getDH().sendDialogues(569, npcType);
			} else if (c.pirateTreasure == 3) {
				c.getDH().sendDialogues(580, npcType);
			} else {
				c.sendMessage(
						"Arr! Thanks for me helping me.");
			}
			break;

		case 307:
			if (c.witchspot == 0) {
				c.getDH().sendDialogues(532, npcType);
			} else if (c.witchspot == 1) {
				c.getDH().sendDialogues(546, npcType);
			} else if (c.witchspot == 2) {
				c.getDH().sendDialogues(548, npcType);
			} else if (c.witchspot == 3) {
				c.getDH().sendNpcChat1(
						"Welcome back, thank you again for helping me.",
						c.talkingNpc, "Hetty");
			}
			break;

		case 755:// morgan
			if (c.vampSlayer == 3) {
				c.getDH().sendDialogues(531, npcType);
			} else if (c.vampSlayer == 4) {
				c.getDH().sendDialogues(529, npcType);
			} else if (c.vampSlayer == 0) {
				c.getDH().sendDialogues(476, npcType);
			}
			break;

		case 743:// ned
			if (c.vampSlayer == 0) {
				c.getDH().sendDialogues(211, npcType);
			} else if (c.vampSlayer == 1) {
				c.getDH().sendStatement("I should go find harlow.");
			} else if (c.vampSlayer > 1) {
				c.getDH().sendDialogues(1337, npcType);
			}
			break;

		case 756:// harlow
			if (c.vampSlayer == 1) {
				c.getDH().sendDialogues(510, npcType);
			} else if (c.vampSlayer == 3) {
				c.getDH().sendDialogues(531, npcType);
			} else {
				c.getDH().sendStatement("I'm not on this step yet.");
			}
			break;

		case 456:
			if (c.restGhost == 0) {
				c.getDH().sendDialogues(338, 456);
			}
			break;

		case 457:
			if (c.restGhost == 2) {
				c.getDH().sendDialogues(371, npcType);
			}
			break;

		case 458:
			if (c.restGhost == 1) {
				c.getDH().sendDialogues(352, npcType);
			}
			break;

		case 759:
			if (c.getItemAssistant().playerHasItem(1927, 1) && c.gertCat == 2) {
				c.getDH().sendDialogues(319, npcType);
				c.getItemAssistant().deleteItem2(1927, 1);
				c.getItemAssistant().addItem(1925, 1);
				c.gertCat = 3;
			} else if (c.getItemAssistant().playerHasItem(1552, 1)
					&& c.gertCat == 3) {
				c.getDH().sendDialogues(323, npcType);
				c.getItemAssistant().deleteItem2(1552, 1);
				c.gertCat = 4;
			} else if (c.gertCat == 4) {
				c.getDH().sendStatement("Hiss!");
				c.getDH().sendDialogues(325, npcType);
				c.gertCat = 5;
			} else if (c.getItemAssistant().playerHasItem(1554, 1)
					&& c.gertCat == 6) {
				c.getItemAssistant().deleteItem2(1554, 1);
				c.getDH().sendDialogues(326, npcType);
				c.gertCat = 6;
			} else if (c.gertCat == 2) {
				c.sendMessage("Hiss!");
				c.getDH().sendStatement("Fluffs hisses but clearly wants something - maybe she is thirsty?");
			}
			break;

		case 780:
			if (c.playerLevel[10] < 4) {
				c.getDH().sendStatement(
						"You don't have the requirements to do this quest.");
				return;
			}
			if (c.gertCat == 0) {
				c.getDH().sendDialogues(269, npcType);
			} else if (c.gertCat == 1) {
				c.getDH().sendDialogues(276, npcType);
			} else if (c.gertCat == 6) {
				c.getDH().sendDialogues(328, npcType);
			} else {
				c.getDH()
						.sendStatement("She has nothing to say to you.");
			}
			break;

		case 783:
			if (c.gertCat == 1) {
				c.getDH().sendDialogues(286, npcType);
			} else if (c.gertCat == 2) {
				c.getDH().sendDialogues(314, npcType);
			}
			break;

		case 639:
			if (c.romeojuliet == 0) {
				c.getDH().sendDialogues(389, npcType);
			} else if (c.romeojuliet == 1) {
				c.getDH().sendDialogues(408, npcType);
			} else if (c.romeojuliet == 3) {
				c.getDH().sendDialogues(415, npcType);
			} else if (c.romeojuliet == 4) {
				c.getDH().sendDialogues(424, npcType);
			} else if (c.romeojuliet == 5) {
				c.getDH().sendDialogues(431, npcType);
			} else if (c.romeojuliet == 6) {
				c.getDH().sendDialogues(443, npcType);
			} else if (c.romeojuliet == 8) {
				c.getDH().sendDialogues(469, npcType);
			} else if (c.romeojuliet == 9) {
				c.sendMessage("Thanks for helping me!");
			}
			if (c.romeojuliet == 2
					&& c.getItemAssistant().playerHasItem(755, 1)) {
				c.getDH().sendDialogues(415, npcType);
			}
			if (c.romeojuliet == 2
					&& !c.getItemAssistant().playerHasItem(755, 1)) {
				c.getDH().sendDialogues(421, npcType);
			}
			break;

		case 276:
			if (c.romeojuliet == 5) {
				c.getDH().sendDialogues(432, npcType);
			}
			if (c.romeojuliet == 6
					&& c.getItemAssistant().playerHasItem(300, 1)
					&& c.getItemAssistant().playerHasItem(227, 1)
					&& c.getItemAssistant().playerHasItem(526, 1)) {
				c.getDH().sendDialogues(448, npcType);
			} else {
				if (c.romeojuliet == 6) {
					c.getDH().sendDialogues(439, npcType);
				}
			}
			break;

		case 637:
			if (c.romeojuliet == 0) {
				c.getDH().sendDialogues(409, npcType);
			} else if (c.romeojuliet == 1) {
				c.getDH().sendDialogues(410, npcType);
			} else if (c.romeojuliet == 2) {
				c.getDH().sendDialogues(414, npcType);
			} else if (c.romeojuliet == 7) {
				c.getDH().sendDialogues(457, npcType);
			} else if (c.romeojuliet == 8) {
				c.getDH().sendDialogues(468, npcType);
			}
			break;

		case 741:
			c.getDH().sendDialogues(190, npcType);
			break;

		case 553:
			if (c.runeMist == 2) {
				c.getDH().sendDialogues(229, npcType);
			} else if (c.runeMist == 3) {
				c.getDH().sendDialogues(237, npcType);
			}
			break;

		case 300:
			if (c.runeMist == 1) {
				c.getDH().sendDialogues(201, npcType);
			} else if (c.runeMist == 2) {
				c.getDH().sendDialogues(213, npcType);
			} else if (c.runeMist == 3) {
				c.getDH().sendDialogues(238, npcType);
			} else if (c.runeMist > 3 || c.runeMist < 1) {
				c.sendMessage(
						"He has nothing to say to you.");
			}
			break;

		case 284:
			if (c.playerLevel[14] < 14) {
				c.getDH().sendStatement(
						"You don't have the requirements to do this quest.");
				return;
			}
			if (c.doricQuest == 0) {
				c.getDH().sendDialogues(89, npcType);
			} else if (c.doricQuest == 1) {
				c.getDH().sendDialogues(84, npcType);
			} else if (c.doricQuest == 2) {
				c.getDH().sendDialogues(86, npcType);
			} else if (c.doricQuest == 3) {
				c.getDH().sendDialogues(100, npcType);
			}
			break;

		case 706:
			if (c.impsC == 0) {
				c.getDH().sendDialogues(145, npcType);
			} else if (c.impsC == 1) {
				c.getDH().sendDialogues(156, npcType);
			}
			if (c.impsC == 1 && c.getItemAssistant().playerHasItem(1470, 1)
					&& c.getItemAssistant().playerHasItem(1472, 1)
					&& c.getItemAssistant().playerHasItem(1474, 1)
					&& c.getItemAssistant().playerHasItem(1476, 1)) {
				c.getDH().sendDialogues(158, npcType);
			} else if (c.impsC == 1) {
				c.getDH().sendDialogues(157, npcType);
			}
			break;

		case 278:
			if (c.cookAss == 0) {
				c.getDH().sendDialogues(50, npcType);
			} else if (c.cookAss == 1) {
				c.getDH().sendDialogues(67, npcType);
			} else if (c.cookAss == 2) {
				c.getDH().sendDialogues(69, npcType);
			} else if (c.cookAss == 3) {
				c.getDH().sendDialogues(76, npcType);
			}
			break;

		case 758:
			if (c.sheepShear == 0) {
				c.getDH().sendDialogues(164, npcType);
			} else if (c.sheepShear == 1) {
				c.getDH().sendDialogues(185, 1);
			} else {
				c.sendMessage(
						"He has nothing to say to you.");
			}
			break;

		case 379:
			if (c.bananas == 0 || c.luthas == false) {
				c.getDH().sendDialogues(8, npcType);
			} else if (c.bananas > 0) {
				c.getDH().sendDialogues(4, npcType);
			} else {
				c.sendMessage(
								"You may now talk to Luthas your bananna task has been reset.");
				c.luthas = false;
				c.bananas = 0;
			}
			break;

		/**
		 * End of Quests
		 */

		case 2294:
			c.getDH().sendDialogues(24, npcType);
			break;

		case 2296:
			c.getDH().sendDialogues(26, npcType);
			break;

		case 500:
			c.getDH().sendDialogues(21, npcType);
			break;

		case 659:
			c.getDH().sendDialogues(18, npcType);
			break;

		case 2244:
			c.getDH().sendDialogues(14, npcType);
			break;

		case 641:
			c.getDH().sendDialogues(11, npcType);
			break;

		case 2458:
			c.getDH().sendDialogues(2, npcType);
			break;

		case 731:
			c.getDH().sendDialogues(19, npcType);
			break;

		case 732:
			c.getDH().sendDialogues(3150, npcType);
			break;

		/**
		 * Bankers
		 */
		case 953:
		case 166:
		case 1702: 
		case 495: 
		case 496:
		case 497:
		case 498: 
		case 499: 
		case 567: 
		case 1036: 
		case 1360: 
		case 2163: 
		case 2164:
		case 2354: 
		case 2355: 
		case 2568: 
		case 2569: 
		case 2570: 
		case 2271: 
		case 494: 
		case 2619:
			c.getDH().sendDialogues(1013, npcType);
		break;

		case 1152:
			c.getDH().sendDialogues(16, npcType);
			break;

		case 905:
			c.getDH().sendDialogues(5, npcType);
			break;

		case 460:
			c.getDH().sendDialogues(3, npcType);
			break;

		case 462:
			c.getDH().sendDialogues(3149, npcType);
			break;

		case 658:
			Sailing.startTravel(c, 2);
			break;
			
		case 2437:
		case 2438:
		if (!c.getItemAssistant().playerHasItem(995, 1000)) {
			c.getDH().sendStatement("You need 1000 coins to go here!");
			c.nextChat = 0;
			return;
		}
		if (c.absX > 2619 && c.absX < 2622 && c.absY > 3680 && c.absY < 3689 && c.getItemAssistant().playerHasItem(995, 1000)) {
			//Sailing.startTravel(c, 18);
			c.getPlayerAssistant().startTeleport(2551, 3759, 0, "modern");
			c.getItemAssistant().deleteItem2(995, 1000);
			c.getDH().sendStatement("You arrive safely.");
			c.nextChat = 0;
		} else {
			 if (c.getItemAssistant().playerHasItem(995, 1000)) {
			//Sailing.startTravel(c, 17);	
			c.getPlayerAssistant().startTeleport(2620, 3686, 0, "modern");
			c.getItemAssistant().deleteItem2(995, 1000);
			c.getDH().sendStatement("You arrive safely.");
			c.nextChat = 0;
			 }
		}
		break;

		case 381:
			if (c.absY > 3230 && c.absY < 3236) {
				Sailing.startTravel(c, 8);
			} else {
				Sailing.startTravel(c, 7);
			}
			break;

		case 3506:
		case 3507:
		case 761:
		case 760:
		case 762:
		case 763:
		case 764:
		case 765:
		case 766:
		case 767:
		case 768:
		case 769:
		case 770:
		case 771:
		case 772:
		case 773:
		case 3505:
			c.getSummon().pickUpClean(c, c.summonId);
			c.hasNpc = false;
			c.summonId = 0;
			break;

		case 804:
		case 1041:
			Tanning.sendTanningInterface(c);
			break;

		case 657:
			Sailing.startTravel(c, 1);
			break;

		case 8689:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				if (c.getItemAssistant().playerHasItem(1925, 1)) {
					c.turnPlayerTo(c.objectX, c.objectY);
					c.startAnimation(2292);
					c.getItemAssistant().addItem(1927, 1);
					c.getItemAssistant().deleteItem2(1925, 1);
					c.buryDelay = System.currentTimeMillis();
				} else {
					c.sendMessage(
							"You need a bucket to milk a cow!");
				}
			}
			break;

		case 3789:
			c.sendMessage(
					new StringBuilder().append("You currently have ")
							.append(c.pcPoints).append(" pest control points.")
							.toString());
			break;

	

		
		
		
			
		case 70:
		case 1596:
		case 1597:
		case 1598:
		case 1599:
			c.getDH().sendDialogues(1228, npcType);
			c.SlayerMaster = npcType;
			break;
		
        /**
         * Shops.
         */
		case 2566:
			c.getShops().openSkillCape();
		break;
		
		
		/**
		 * Make over mage.
		 */
		case 599:
			c.getPA().showInterface(3559);
			c.canChangeAppearance = true;
		break;
		
		}
	}

	public void secondClickNpc(int npcType) {
		c.clickNpcType = 0;
		c.rememberNpcIndex = c.npcClickIndex;
		c.npcClickIndex = 0;
		Shops.openShop(c, npcType);
		switch(npcType) {
		case 2234:
		case 2235:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 1757:
		case 1758:
		case 1759:
		case 1760:
		case 1761:
		case 1715:
		case 1714:
		case 1710:
		case 1711:
		case 1712:
		case 15:
		case 18:
		case 187:
		case 9:
		case 10:
		case 1880:
		case 1881:
		case 1926:
		case 1927:
		case 1928:
		case 1929:
		case 1930:
		case 1931:
		case 23:
		case 26:
		case 1883:
		case 1884:
		case 32:
		case 1904:
		case 1905:
		case 20:
		case 365:
		case 2256:
		case 66:
		case 67:
		case 68:
		case 21:
			c.getThieving().pickpocketNpc(c, npcType);
			break;
		case 309:
		case 312:
		case 313:
		case 316:
		case 326:
			Fishing.setupFishing(c, Fishing.forSpot(npcType, false));
			break;
		case 1526:
			c.getShops().openShop(112);
			break;
		
		
		}
		
	
		
	}
	
	public void thirdClickNpc(int npcType) {
		c.clickNpcType = 0;
		c.rememberNpcIndex = c.npcClickIndex;
		c.npcClickIndex = 0;
		switch(npcType) {
		

		}
	}
	

}