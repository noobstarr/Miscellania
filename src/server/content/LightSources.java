package server.content;

import server.players.Client;

/**
 * Lightsources
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class LightSources {

	public static void saveBrightness(Client client) {
		if (client.brightness == 1) {
			brightness1(client);
		} else if (client.brightness == 2) {
			brightness2(client);
		} else if (client.brightness == 4) {
			brightness4(client);
		} else {
			brightness3(client);
		}
	}

	public static void brightness1(Client c) {
		c.getPlayerAssistant().sendFrame36(505, 1);
		c.getPlayerAssistant().sendFrame36(506, 0);
		c.getPlayerAssistant().sendFrame36(507, 0);
		c.getPlayerAssistant().sendFrame36(508, 0);
		c.getPlayerAssistant().sendFrame36(166, 1);
		c.brightness = 1;
	}

	public static void brightness2(Client c) {
		c.getPlayerAssistant().sendFrame36(505, 0);
		c.getPlayerAssistant().sendFrame36(506, 1);
		c.getPlayerAssistant().sendFrame36(507, 0);
		c.getPlayerAssistant().sendFrame36(508, 0);
		c.getPlayerAssistant().sendFrame36(166, 2);
		c.brightness = 2;
	}

	public static void brightness3(Client c) {
		c.getPlayerAssistant().sendFrame36(505, 0);
		c.getPlayerAssistant().sendFrame36(506, 0);
		c.getPlayerAssistant().sendFrame36(507, 1);
		c.getPlayerAssistant().sendFrame36(508, 0);
		c.getPlayerAssistant().sendFrame36(166, 3);
		c.brightness = 3;
	}

	public static void brightness4(Client c) {
		c.getPlayerAssistant().sendFrame36(505, 0);
		c.getPlayerAssistant().sendFrame36(506, 0);
		c.getPlayerAssistant().sendFrame36(507, 0);
		c.getPlayerAssistant().sendFrame36(508, 1);
		c.getPlayerAssistant().sendFrame36(166, 4);
		c.brightness = 4;
	}

	public static void setBrightness(Client c) {
		if (c.getItemAssistant().playerHasItem(594) || c.getItemAssistant().playerHasItem(32) || c.getItemAssistant().playerHasItem(33)) {
			brightness2(c);
		} else if (c.getItemAssistant().playerHasItem(4535) || c.getItemAssistant().playerHasItem(4524)) {
			brightness3(c);
		} else if (c.getItemAssistant().playerHasItem(4550)) {
			brightness4(c);
		}
	}

	public static final int[] lightSources = { 594, 32, 33, 4524, 4539, 4550 };

	public static boolean playerHasLightSource(Client c) {
		for (int lightSource : lightSources) {
			if (c.getItemAssistant().playerHasItem(lightSource)) {
				setBrightness(c);
				return true;
			}
		}
		c.sendMessage("It's recommened that you get a light source to continue.");
		brightness1(c);
		return false;
	}

}
