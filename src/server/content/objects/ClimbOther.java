package server.content.objects;

import server.objects.Object;
import server.objects.ObjectHandler;
import server.players.Client;
import server.clipping.ObjectDef;
import server.clipping.Region;

public class ClimbOther {

	public enum ClimbData {

		VARROCK_MANHOLE(881, 882),
		LUMBRIDGE_TRAPDOOR(14879, 10698),
		VARROCK_TRAPDOOR(1568, 10698);

		private int closedId, openId;

		private ClimbData(int closedId, int openId) {
			this.closedId = closedId;
			this.openId = openId;
		}

		public int getClosed() {
			return closedId;
		}

		public int getOpen() {
			return openId;
		}
	}

	public static void handleOpenOther(Client player, int objectType) {
		for (ClimbData t: ClimbData.values()) {
			if (objectType == t.getClosed()) {
				new Object(t.getOpen(), player.objectX, player.objectY, player.heightLevel, ObjectHandler.getObjectFace(player, t.getClosed()), 10, t.getClosed(), 100);
				Region.addObject(t.getOpen(), player.objectX, player.objectY, player.heightLevel, 10,  ObjectHandler.getObjectFace(player, t.getClosed()));
			}
		}
	}
	
	public static void useOther(Client player, int objectType) {
		final String objectName = ObjectDef.getObjectDef(objectType).name;
		if (System.currentTimeMillis() - player.climbDelay < 1800) {
			return;
		}
		player.stopMovement();
		player.startAnimation(827);
		player.getPlayerAssistant().removeAllWindows();
		player.teleportToX = player.absX;
		player.teleportToY = player.absY + 6400;
		player.sendMessage("You climb down the " + objectName.toLowerCase() + ".");
		player.climbDelay = System.currentTimeMillis();
	}
	
	public static void useUp(final Client c, final int objectId) {
		c.stopPlayerPacket = true;
		c.startAnimation(828);
		c.getPlayerAssistant().removeAllWindows();
		c.teleportToX = c.absX;
		c.teleportToY = c.absY - 6400;
		c.sendMessage("You climb up.");
		c.stopPlayerPacket = false;
	}

}
