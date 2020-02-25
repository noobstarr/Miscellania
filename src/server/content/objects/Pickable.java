package server.content.objects;

import server.Server;
import server.content.sound.SoundList;
import server.util.CycleEvent;
import server.util.CycleEventContainer;
import server.util.CycleEventHandler;
import server.players.Client;

/**
 * Pickables
 * @author Andrew (I'm A Boss on Rune-Server and Mr Extremez on Mopar & Runelocus)
 */

public class Pickable {

	private final static int[][] PICKABLE_ITEMS = { 
			{ 1161, 1965 }, // Cabbage
			{ 2646, 1779 }, // Flax
			{ 313, 1947 }, // Wheat
			{ 5585, 1947 }, { 5584, 1947 }, { 5585, 1947 }, { 312, 1942 }, // Potato
			{ 3366, 1957 }, // Onion
	};

	public static void pickObject(final Client player, final int objectType, final int objectX, final int objectY) {
		if (player.lastAction + 1800 > System.currentTimeMillis()) {
			return;
		}
		for (int[] data : PICKABLE_ITEMS) {
			final int objectId = data[0];
			int itemId = data[1];
			if (objectType == objectId) {
				player.getItemAssistant().addItem(itemId, 1);
				break;
			}
		}
		if (player.getItemAssistant().freeSlots() > 0) {
			player.turnPlayerTo(objectX, objectY);
			player.startAnimation(827);
			if (objectType == 2646 && random(3) == 0 || objectType != 2646) {
				if (player.outStream != null) {
					Server.objectHandler.createAnObject(player, -1, objectX, objectY);
					CycleEventHandler.addEvent(player, new CycleEvent() {
						@Override
						public void execute(CycleEventContainer container) {
							container.stop();
						}
						@Override
						public void stop() {
							if (player.outStream != null) {
								Server.objectHandler.createAnObject(player,objectType, objectX, objectY);
							}
						}
					}, 5);
				}
			}
			player.getPA().sendSound(SoundList.PICKABLE, 100, 1);
			player.lastAction = System.currentTimeMillis();
		} else {
			player.sendMessage("Your inventory is too full to hold any more items!");
		}
	}

	private static int random(int range) {
		return (int) (java.lang.Math.random() * (range + 1));
	}
}
