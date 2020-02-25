package server.content.objects;

import server.Server;
import server.content.sound.SoundList;
import server.players.Client;
import server.util.Misc;

/**
 * @author Andrew
 */

public class Webs {

	public static int[] CLICKING_OBJECTS = { 733 };

	public static boolean webs(Client c, int object) {
		for (int element : CLICKING_OBJECTS) {
			if (object == element) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("static-access")
	public static void slashWeb(Client c, final int objectClickId,
			final int objectX, final int objectY) {
		if (System.currentTimeMillis() - c.webSlashDelay > 1800) {
			if (Misc.random(3) > 0) {
				Server.objectHandler.createAnObject(c, -1, objectX, objectY);
				// c.startAnimation(451);
				c.startAnimation(c.getCombat().getWepAnim(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase()));
				c.webSlashDelay = System.currentTimeMillis();
				c.getPA().sendSound(SoundList.SLASH_WEB, 100, 0);
				c.sendMessage("You successfully slash open the web.");
			} else {
				c.sendMessage("You fail to slash through the web.");
				return;
			}
		}
	}
}
