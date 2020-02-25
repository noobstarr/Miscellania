package server.packets;

import server.util.CycleEvent;
import server.util.CycleEventContainer;
import server.util.CycleEventHandler;
import server.util.Misc;
import server.clipping.Region;
import server.content.minigames.CastleWars;
import server.content.objects.Climbing;
import server.content.objects.Doors;
import server.content.objects.DoubleGates;
import server.content.objects.FlourMill;
import server.content.objects.Levers;
import server.content.objects.OtherObjects;
import server.content.objects.Searching;
import server.content.objects.SingleGates;
import server.content.objects.UseOther;
import server.content.skills.Mining;
import server.content.skills.Runecrafting;
import server.objects.ClimbOther;
import server.objects.ClimbOther.ClimbData;
import server.objects.PassDoor;
import server.players.Client;
import server.players.PacketType;
/**
 * Click Object
 */
public class ClickObject implements PacketType {

	public static final int FIRST_CLICK = 132, SECOND_CLICK = 252, THIRD_CLICK = 70;	
	@Override
	public void processPacket(final Client c, int packetType, int packetSize) {		
		c.clickObjectType = c.objectX = c.objectId = c.objectY = 0;
		c.objectYOffset = c.objectXOffset = 0;
		c.getPA().resetFollow();
		if (c.newPlayer)
			return;
		switch(packetType) {
			
			case FIRST_CLICK:
			c.objectX = c.getInStream().readSignedWordBigEndianA();
			c.objectId = c.getInStream().readUnsignedWord();
			c.objectY = c.getInStream().readUnsignedWordA();
			c.objectDistance = 1;
			//if(c.goodDistance(c.getX(), c.getY(), c.objectX, c.objectY, 1)) {
				//if (Doors.handleDoor(c.objectId, c.objectX, c.objectY, c.heightLevel)) {
				//}
			//}
			
			if(c.playerRights >= 3) {
				Misc.println("objectId: "+c.objectId+"  ObjectX: "+c.objectX+ "  objectY: "+c.objectY+" Xoff: "+ (c.getX() - c.objectX)+" Yoff: "+ (c.getY() - c.objectY)); 
			} else if (c.playerRights == 3) {
				c.sendMessage("objectId: " + c.objectId + " objectX: " + c.objectX + " objectY: " + c.objectY);
			}
			if (Math.abs(c.getX() - c.objectX) > 25 || Math.abs(c.getY() - c.objectY) > 25) {
				c.resetWalkingQueue();
				break;
			}
			int[] altarID= {2478, 2479, 2480, 2481, 2482, 2483, 2484, 2485, 2486,
					2487, 2488, 30624};
			for (int i = 0; i < altarID.length; i++) {
				if (c.objectId == altarID[i]) {
					Runecrafting.craftRunes(c, c.objectId);
				}
			}
			ClimbOther.handleOpenOther(c, packetType);
			for (ClimbData t: ClimbData.values()) {
				if (packetType == t.getOpen()) {
					ClimbOther.useOther(c, packetType);
				}
			}
			if(c.goodDistance(c.getX(), c.getY(), c.objectX, c.objectY, 1)) {
				if (Doors.getSingleton().handleDoor(c.objectId, c.objectX, c.objectY, c.heightLevel)) {
				}
			}
			OtherObjects.searchSpecialObject(c, packetType);
			Searching.searchObject(c, packetType);
			Levers.pullLever(c, packetType);
			
			PassDoor.processDoor(c, packetType);
			switch(c.objectId) {
			case 9398://deposit
				if (c.gameMode == 2) {
				c.sendMessage("you can't use banks in this game mode.");
				break;
				}
				c.getPA().sendFrame126("The Bank of Miscellania-PS - Deposit Box", 7421);
				c.getPA().sendFrame248(4465, 197);//197 just because you can't see it =\
				c.getItems().resetItems(7423);
			break;
			
			 /*
             * CastleWars
             */
			case 4437:
				c.objectDistance = 2;
				break;
            case 4387:
                CastleWars.addToWaitRoom(c, 1); //saradomin
                break;
            case 4388:
                CastleWars.addToWaitRoom(c, 2); // zamorak
                break;
            case 4408:
                CastleWars.addToWaitRoom(c, 3); //guthix
                break;
            case 4389: //sara
            case 4390: //zammy waiting room portal
                CastleWars.leaveWaitingRoom(c);
                break;
			
                //end
                
            case 1782:// full flour bin
    			FlourMill.emptyFlourBin(c);
    			break;

    		case 2718: // Hopper
    			FlourMill.hopperControl(c);
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
                
                
            case 2465:
    		case 2466:
    		case 2467:
    		case 2468:
    		case 2469:
    		case 2470:
    		case 2471:
    		case 2472:
    		case 2473:
    		case 2474:
    		case 2475:
    		case 2478:
    		case 2479:
    		case 2480:
    		case 2481:
    		case 2482:
    		case 2483:
    		case 2484:
    		case 2485:
    		case 2486:
    		case 2487:
    		case 2488:
    		case 2452:
    		case 2453:
    		case 2454:
    		case 2455:
    		case 2456:
    		case 2457:
    		case 2458:
    		case 2459:
    		case 2460:
    		case 2461:
    		case 2462:
    			Runecrafting.craftRunes(c, packetType);
    			break;
                
            case 7142:
			case 7143:
			case 7144:
			case 7145:
			case 7146:
			case 7147:
			case 7148:
			case 7149:
			case 7150:
			case 7151:
			case 7152:
			case 7153:
				c.getPA().movePlayer(3041, 4832, 0);
				break;
                
			case 2491:
				Mining.mineEss(c, c.objectId);
				break;
				
				
				/*
				 * Doors
				 */
				case 6749:
					if (c.objectX == 3562 && c.objectY == 9678) {
						c.getPA().object(6749, 3562, 9678, -3, 0);
						Region.addObject(6749, 3562, 9678, 0, 0, -3);
						c.getPA().object(6730, 3562, 9677, -1, 0);
						Region.addObject(6730, 3562, 9677, 0, 0, -1);
					} else if (c.objectX == 3558 && c.objectY == 9677) {
						c.getPA().object(6749, 3558, 9677, -1, 0);
						Region.addObject(6749, 3558, 9677, 0, 0, -1);
						c.getPA().object(6730, 3558, 9678, -3, 0);
						Region.addObject(6730, 3558, 9677, 0, 0, -3);
					}
					break;
				case 6730:
					if (c.objectX == 3558 && c.objectY == 9677) {
						c.getPA().object(6749, 3562, 9678, -3, 0);
						Region.addObject(6749, 3562, 9678, 0, 0, -3);
						c.getPA().object(6730, 3562, 9677, -1, 0);
						Region.addObject(6730, 3562, 9677, 0, 0, -1);
					} else if (c.objectX == 3558 && c.objectY == 9678) {
						c.getPA().object(6749, 3558, 9677, -1, 0);
						Region.addObject(6749, 3558, 9677, 0, 0, -1);
						c.getPA().object(6730, 3558, 9678, -3, 0);
						Region.addObject(6730, 3558, 9678, 0, 0, -3);
					}
					break;
				case 6727:
					if (c.objectX == 3551 && c.objectY == 9684) {
						c.sendMessage(
								"You cant open this door..");
					}
					break;
				case 6746:
					if (c.objectX == 3552 && c.objectY == 9684) {
						c.sendMessage(
								"You cant open this door..");
					}
					break;
				case 6748:
					if (c.objectX == 3545 && c.objectY == 9678) {
						c.getPA().object(6748, 3545, 9678, -3, 0);
						Region.addObject(6748, 3545, 9678, 0, 0, -3);
						c.getPA().object(6729, 3545, 9677, -1, 0);
						Region.addObject(6729, 3545, 9677, 0, 0, -1);
					} else if (c.objectX == 3541 && c.objectY == 9677) {
						c.getPA().object(6748, 3541, 9677, -1, 0);
						Region.addObject(6748, 3541, 9677, 0, 0, -1);
						c.getPA().object(6729, 3541, 9678, -3, 0);
						Region.addObject(6729, 3541, 9678, 0, 0, -3);
					}
					break;
				case 6729:
					if (c.objectX == 3545 && c.objectY == 9677) {
						c.getPA().object(6748, 3545, 9678, -3, 0);
						Region.addObject(6748, 3545, 9678, 0, 0, -3);
						c.getPA().object(6729, 3545, 9677, -1, 0);
						Region.addObject(6729, 3545, 9677, 0, 0, -1);
					} else if (c.objectX == 3541 && c.objectY == 9678) {
						c.getPA().object(6748, 3541, 9677, -1, 0);
						Region.addObject(6748, 3541, 9677, 0, 0, -1);
						c.getPA().object(6729, 3541, 9678, -3, 0);
						Region.addObject(6729, 3541, 9678, 0, 0, -3);
					}
					break;
				case 6726:
					if (c.objectX == 3534 && c.objectY == 9684) {
						c.getPA().object(6726, 3534, 9684, -4, 0);
						Region.addObject(6726, 3534, 9684, 0, 0, -4);
						c.getPA().object(6745, 3535, 9684, -2, 0);
						Region.addObject(6745, 3535, 9684, 0, 0, -4);
					} else if (c.objectX == 3535 && c.objectY == 9688) {
						c.getPA().object(6726, 3535, 9688, -2, 0);
						Region.addObject(6726, 3535, 9688, 0, 0, -2);
						c.getPA().object(6745, 3534, 9688, -4, 0);
						Region.addObject(6745, 3534, 9688, 0, 0, -4);
					}
					break;
				case 6745:
					if (c.objectX == 3535 && c.objectY == 9684) {
						c.getPA().object(6726, 3534, 9684, -4, 0);
						Region.addObject(6726, 3534, 9684, 0, 0, -4);
						c.getPA().object(6745, 3535, 9684, -2, 0);
						Region.addObject(6745, 3535, 9684, 0, 0, -2);
					} else if (c.objectX == 3534 && c.objectY == 9688) {
						c.getPA().object(6726, 3535, 9688, -2, 0);
						Region.addObject(6726, 3535, 9688, 0, 0, -2);
						c.getPA().object(6745, 3534, 9688, -4, 0);
						Region.addObject(6745, 3534, 9688, 0, 0, -4);
					}
					break;
				case 6743:
					if (c.objectX == 3545 && c.objectY == 9695) {
						c.getPA().object(6724, 3545, 9694, -1, 0);
						Region.addObject(6724, 3545, 9694, 0, 0, -1);
						c.getPA().object(6743, 3545, 9695, -3, 0);
						Region.addObject(6743, 3545, 9695, 0, 0, -3);
					} else if (c.objectX == 3541 && c.objectY == 9694) {
						c.getPA().object(6724, 3541, 9694, -1, 0);
						Region.addObject(6724, 3541, 9694, 0, 0, -1);
						c.getPA().object(6743, 3541, 9695, -3, 0);
						Region.addObject(6743, 3541, 9695, 0, 0, -3);
					}
					break;
				case 6724:
					if (c.objectX == 3545 && c.objectY == 9694) {
						c.getPA().object(6724, 3545, 9694, -1, 0);
						Region.addObject(6724, 3545, 9694, 0, 0, -1);
						c.getPA().object(6743, 3545, 9695, -3, 0);
						Region.addObject(6743, 3545, 9695, 0, 0, -3);
					} else if (c.objectX == 3541 && c.objectY == 9695) {
						c.getPA().object(6724, 3541, 9694, -1, 0);
						Region.addObject(6724, 3541, 9694, 0, 0, -1);
						c.getPA().object(6743, 3541, 9695, -3, 0);
						Region.addObject(6743, 3541, 9695, 0, 0, -3);
					}
					break;

				case 9319:
					if (c.heightLevel == 0) {
						c.getPlayerAssistant().movePlayer(c.absX,
								c.absY, 1);
					} else if (c.heightLevel == 1) {
						c.getPlayerAssistant().movePlayer(c.absX,
								c.absY, 2);
					}
					break;

				case 9320:
					if (c.heightLevel == 1) {
						c.getPlayerAssistant().movePlayer(c.absX,
								c.absY, 0);
					} else if (c.heightLevel == 2) {
						c.getPlayerAssistant().movePlayer(c.absX,
								c.absY, 1);
					}
					break;

				case 4496:
				case 4494:
					if (c.heightLevel == 2) {
						c.getPlayerAssistant().movePlayer(c.absX - 5,
								c.absY, 1);
					} else if (c.heightLevel == 1) {
						c.getPlayerAssistant().movePlayer(c.absX + 5,
								c.absY, 0);
					}
					break;

				case 4493:
					if (c.heightLevel == 0 && c.absY > 3536 && c.absY < 3539 && c.absX == 3438) {
						c.getPlayerAssistant().movePlayer(c.absX - 5, c.absY, 1);
					} else if (c.heightLevel == 1 && c.absY > 3536 && c.absY < 3539 && c.absX == 3433) {
						c.getPlayerAssistant().movePlayer(c.absX + 5, c.absY, 2);
					}
					break;

				case 4495:
					if (c.heightLevel == 1 && c.absX == 3412) {
						c.getPlayerAssistant().movePlayer(c.absX + 5, c.absY, 2);
					}
					break;

				case 5126:
					if (c.absY == 3554) {
						c.getPlayerAssistant().walkTo(0, 1);
					} else {
						c.getPlayerAssistant().walkTo(0, -1);
					}
					break;
			
				
				
				case 3044:
					c.objectDistance = 3;
				break;
				
				
			
				case 10229:
				case 6522:
					c.objectDistance = 2;
				break;
				case 8959:
					c.objectYOffset = 1;
				break;
				case 4417:
				if (c.objectX == 2425 && c.objectY == 3074)
					c.objectYOffset = 2;
				break;
				case 4420:
				if (c.getX() >= 2383 && c.getX() <= 2385){
					c.objectYOffset = 1;
				} else {
					c.objectYOffset = -2;
				}
				case 6552:
				case 409:
					c.objectDistance = 2;
				break;
				case 2879:
				case 2878:
					c.objectDistance = 3;
				break;
				case 2558:
					c.objectDistance = 0;
					if (c.absX > c.objectY& c.objectX == 3044)
						c.objectXOffset = 1;
					if (c.absY > c.objectY)
						c.objectYOffset = 1;
					if (c.absX < c.objectX && c.objectX == 3038)
						c.objectXOffset = -1;
				break;
				case 9356:
					c.objectDistance = 2;
				break;
				case 5959:
				case 1815:
				case 5960:
				case 1816:
					c.objectDistance = 0;
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
					UseOther.useDown(c, c.objectId);
				}
				break;

				case 1759:
				case 9472:
				case 11867:
				case 100:
					UseOther.useDown(c, c.objectId);
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
				
				
				case 9293:
					c.objectDistance = 2;
				break;
				case 4418:
				if (c.objectX == 2374 && c.objectY == 3131)
					c.objectYOffset = -2;
				else if (c.objectX == 2369 && c.objectY == 3126)
					c.objectXOffset = 2;
				else if (c.objectX == 2380 && c.objectY == 3127)
					c.objectYOffset = 2;
				else if (c.objectX == 2369 && c.objectY == 3126)
					c.objectXOffset = 2;
				else if (c.objectX == 2374 && c.objectY == 3131)
					c.objectYOffset = -2;
				break;
				case 9706:
					c.objectDistance = 0;
					c.objectXOffset = 1;
				break;
				case 9707:
					c.objectDistance = 0;
					c.objectYOffset = -1;
				break;
				case 4419:
				case 6707: // verac
				c.objectYOffset = 3;
				break;
				case 6823:
				c.objectDistance = 2;
				c.objectYOffset = 1;
				break;
				
				case 6706: // torag
				c.objectXOffset = 2;
				break;
				case 6772:
				c.objectDistance = 2;
				c.objectYOffset = 1;
				break;
				
				case 6705: // karils
				c.objectYOffset = -1;
				break;
				case 6822:
				c.objectDistance = 2;
				c.objectYOffset = 1;
				break;
				
				case 6704: // guthan stairs
				c.objectYOffset = -1;
				break;
				case 6773:
				c.objectDistance = 2;
				c.objectXOffset = 1;
				c.objectYOffset = 1;
				break;
				
				case 11666:
					c.objectXOffset = -1;
					c.objectYOffset = -1;
					break;
				
				case 6703: // dharok stairs
				c.objectXOffset = -1;
				break;
				case 6771:
				c.objectDistance = 2;
				c.objectXOffset = 1;
				c.objectYOffset = 1;
				break;
				
				case 6702: // ahrim stairs
				c.objectXOffset = -1;
				break;
				case 6821:
				c.objectDistance = 2;
				c.objectXOffset = 1;
				c.objectYOffset = 1;
				break;
				case 1276:
				case 1278://trees
				case 1281: //oak
				case 1308: //willow
				case 1307: //maple
				case 1309: //yew
				case 1306: //yew
				c.objectDistance = 3;
				break;
				default:
				c.objectDistance = 1;
				c.objectXOffset = 0;
				c.objectYOffset = 0;
				break;		
			}
			if(c.goodDistance(c.objectX+c.objectXOffset, c.objectY+c.objectYOffset, c.getX(), c.getY(), c.objectDistance)) {
				c.getActions().firstClickObject(c.objectId, c.objectX, c.objectY);
			} else {
				c.clickObjectType = 1;
				CycleEventHandler.addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if(c.clickObjectType == 1 && c.goodDistance(c.objectX + c.objectXOffset, c.objectY + c.objectYOffset, c.getX(), c.getY(), c.objectDistance)) {
							c.getActions().firstClickObject(c.objectId, c.objectX, c.objectY);
							container.stop();
						}
						if(c.clickObjectType > 1 || c.clickObjectType == 0)
							container.stop();
					}
					@Override
					public void stop() {
						c.clickObjectType = 0;
					}
				}, 1);
			}
			break;
			
			case SECOND_CLICK:
			c.objectId = c.getInStream().readUnsignedWordBigEndianA();
			c.objectY = c.getInStream().readSignedWordBigEndian();
			c.objectX = c.getInStream().readUnsignedWordA();
			c.objectDistance = 1;
			
			if(c.playerRights >= 3) {
				Misc.println("objectId: "+c.objectId+"  ObjectX: "+c.objectX+ "  objectY: "+c.objectY+" Xoff: "+ (c.getX() - c.objectX)+" Yoff: "+ (c.getY() - c.objectY)); 
			}
			
			switch(c.objectId) {
			case 2560:
			case 2561:
			case 2562:
			case 2563:
			case 2564:
			case 2565:
				c.objectDistance = 2;
				c.objectXOffset = 2;
				c.objectYOffset = 2;
				break;
			case 6163:
			case 6165:
			case 6166:
			case 6164:
			case 6162:
				c.objectDistance = 2;
			break;
				default:
				c.objectDistance = 1;
				c.objectXOffset = 0;
				c.objectYOffset = 0;
				break;
				
			}
			if(c.goodDistance(c.objectX+c.objectXOffset, c.objectY+c.objectYOffset, c.getX(), c.getY(), c.objectDistance)) { 
				c.getActions().secondClickObject(c.objectId, c.objectX, c.objectY);
			} else {
				c.clickObjectType = 2;
				CycleEventHandler.addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if(c.clickObjectType == 2 && c.goodDistance(c.objectX + c.objectXOffset, c.objectY + c.objectYOffset, c.getX(), c.getY(), c.objectDistance)) {
							c.getActions().secondClickObject(c.objectId, c.objectX, c.objectY);
							container.stop();
						}
						if(c.clickObjectType < 2 || c.clickObjectType > 2)
							container.stop();
					}
					@Override
					public void stop() {
						c.clickObjectType = 0;
					}
				}, 1);
			}
			break;
			
			case THIRD_CLICK:
			c.objectX = c.getInStream().readSignedWordBigEndian();
			c.objectY = c.getInStream().readUnsignedWord();
			c.objectId = c.getInStream().readUnsignedWordBigEndianA();
			
			if(c.playerRights >= 3) {
				Misc.println("objectId: "+c.objectId+"  ObjectX: "+c.objectX+ "  objectY: "+c.objectY+" Xoff: "+ (c.getX() - c.objectX)+" Yoff: "+ (c.getY() - c.objectY)); 
			}
			
			switch(c.objectId) {
				default:
				c.objectDistance = 1;
				c.objectXOffset = 0;
				c.objectYOffset = 0;
				break;		
			}
			if(c.goodDistance(c.objectX+c.objectXOffset, c.objectY+c.objectYOffset, c.getX(), c.getY(), c.objectDistance)) { 
				c.getActions().secondClickObject(c.objectId, c.objectX, c.objectY);
			} else {
				c.clickObjectType = 3;
				CycleEventHandler.addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if(c.clickObjectType == 3 && c.goodDistance(c.objectX + c.objectXOffset, c.objectY + c.objectYOffset, c.getX(), c.getY(), c.objectDistance)) {
							c.getActions().thirdClickObject(c.objectId, c.objectX, c.objectY);
							container.stop();
						}
						if(c.clickObjectType < 3)
							container.stop();
					}
					@Override
					public void stop() {
						c.clickObjectType = 0;
					}
				}, 1);
			}	
			break;
		}

	}
	public void handleSpecialCase(Client c, int id, int x, int y) {

	}

}
