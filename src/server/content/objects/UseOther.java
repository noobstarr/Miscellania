package server.content.objects;

import server.players.Client;

public class UseOther {


	public static void useUp(final Client c, final int objectId) {
		c.stopPlayerPacket = true;
		c.startAnimation(828);
		c.getPlayerAssistant().removeAllWindows();
		c.teleportToX = c.absX;
		c.teleportToY = c.absY - 6400;
		c.sendMessage("You climb up.");
		c.stopPlayerPacket = false;
	}

	public static void useDown(final Client c, final int objectId) {
		if (c.objectX == 2647 && c.objectY == 3657 || c.objectX == 2650 && c.objectY == 3661) {
			c.sendMessage("This trapdoor is currently disabled.");
			return;
		}
		c.stopMovement();
		c.startAnimation(827);
		c.getPlayerAssistant().removeAllWindows();
		c.teleportToX = c.absX;
		c.teleportToY = c.absY + 6400;
		c.sendMessage("You climb down.");
	}
}