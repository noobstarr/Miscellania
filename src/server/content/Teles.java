package server.content;

import server.players.Client;

/**
 * Teles
 * @author Andrew
 */

public class Teles {

	public static void useAOG(Client player) {
		player.getDH().sendOption4("Edgeville", "Al Kharid", "Karamja", "Draynor");
		player.dialogueAction = 51;
		player.sendMessage("You rub the Amulet of Glory...");
	}

	public static void useROD(Client player) {
		player.getDH().sendOption2("Duel Arena", "Castle Wars");
		player.dialogueAction = 161;
		player.sendMessage("You rub the Ring of Dueling...");
	}

	public static void useGN(Client player) {
		player.getDH().sendOption2("Burthrope Games Room", "Barbarian Outpost");
		player.dialogueAction = 50;
		player.sendMessage("You rub the Games Necklace...");
	}

	private static final int[][] JEWELERY = { 
		{ 3853, 3855, 7 }, { 3855, 3857, 6 }, { 3857, 3859, 5 }, { 3859, 3861, 4 }, { 3861, 3863, 3 },{ 3863, 3865, 2 }, { 3865, 3867, 1 }, { 3867, 0, 0 }, // gn
		{ 2552, 2554, 7 }, { 2554, 2556, 6 }, { 2556, 2558, 5 }, { 2558, 2560, 4 }, { 2560, 2562, 3 }, { 2562, 2564, 2 }, { 2564, 2566, 1 }, { 2566, 0, 0 }, // rod
		{ 1712, 1710, 3 }, { 1710, 1708, 2 }, { 1708, 1706, 1 }, { 1706, 1704, 0 } //aog
	};

	public static void necklaces(Client player) {
		for (int[] element : JEWELERY) {
			if (player.itemUsing == element[0]) {
				if (player.isOperate) {
					player.playerEquipment[player.playerAmulet] = element[1];
				} else {
					player.getItemAssistant().deleteItem(element[0], 1);
					player.getItemAssistant().addItem(element[1], 1);
				}
				if (element[2] > 1) {
					player.sendMessage("You have " + element[2] + " charges left.");
				} else {
					player.sendMessage("You have " + element[2] + " charge left.");
				}
			}
		}
		player.getItemAssistant().updateSlot(player.playerAmulet);
		player.isOperate = false;
		player.itemUsing = -1;
	}
}