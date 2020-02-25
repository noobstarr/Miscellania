package server.content.skills;

import server.npcs.NPCHandler;
import server.players.Client;

/**
 * Slayer Item Requirements
 * 
 * @author Andrew
 */

public class SlayerRequirements {

	private static final int NOSE_PEG = 4168, MIRROR_SHIELD = 4156,
			EAR_MUFFS = 4166, ROCK_HAMMER = 4162, FACEMASK = 4164,
			LEAF_BLADED_SPEAR = 4158, BROAD_ARROWS = 4172, BAG_OF_SALT = 4161;

	public static boolean itemNeededSlayer(Client client, int i) {
		int npcType = NPCHandler.npcs[i].npcType;
		switch (NPCHandler.npcs[i].npcType) {
		case 1622:
		case 1623: //rock slug
		if (!client.getItemAssistant().playerHasItem(BAG_OF_SALT, 1)) {
			client.sendMessage("You need a Bag of Salt to attack Rock Slugs.");
			client.getCombat().resetPlayerAttack();
			return false;	
		}
		break;
		case 1632: // turoth
			if (client.playerEquipment[client.playerWeapon] != LEAF_BLADED_SPEAR && client.playerEquipment[client.playerArrows] != BROAD_ARROWS) {
				client.sendMessage("You need a Leaf Bladed Spear or Broad Arrows to attack Turoths.");
				client.getCombat().resetPlayerAttack();
				return false;
			} else if (client.playerEquipment[client.playerArrows] != BROAD_ARROWS && client.playerEquipment[client.playerWeapon] != LEAF_BLADED_SPEAR) {
				client.sendMessage("You need a Leaf Bladed Spear or Broad Arrows to attack Turoths.");
				client.getCombat().resetPlayerAttack();
				return false;
			}
			break;
		case 1612:// banshee
			if (client.playerEquipment[client.playerHat] != EAR_MUFFS) {
				client.sendMessage("You need some Ear Muffs to attack Banshees.");
				client.getCombat().resetPlayerAttack();
				return false;
			}
			break;
		case 1620: //basilisk
		case 1616:// cockatrice
			if (client.playerEquipment[client.playerShield] != MIRROR_SHIELD) {
				client.sendMessage("You need a Mirror Shield to attack a " + NPCHandler.getNpcListName(npcType).toLowerCase() + ".");
				client.getCombat().resetPlayerAttack();
				return false;
			}
			break;
		case 1624:// dust devil
			if (client.playerEquipment[client.playerHat] != FACEMASK) {
				client.sendMessage("You need a Face Mask to attack Dust devils.");
				client.getCombat().resetPlayerAttack();
				return false;
			}
			break;
		case 1604:
		case 1605:
		case 1606:
		case 1607:// spectre
			if (client.playerEquipment[client.playerHat] != NOSE_PEG) {
				client.sendMessage("You need a Nose Peg to attack Aberrant specter.");
				client.getCombat().resetPlayerAttack();
				return false;
			}
			break;
		case 1611:// garg
			if (!client.getItemAssistant().playerHasItem(ROCK_HAMMER)) {
				client.sendMessage("You need a Rock Hammer to attack gargoyles.");
				client.getCombat().resetPlayerAttack();
				return false;
			}
			break;
		}
		return true;
	}

}
