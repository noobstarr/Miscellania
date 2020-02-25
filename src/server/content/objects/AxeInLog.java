package server.content.objects;

import server.objects.Object;
import server.players.Client;

public class AxeInLog {

	public static void pullAxeFromLog(Client client, int x, int y) {
		if (client.getItemAssistant().freeSlots() <= 0) {
			client.sendMessage(
					"Not enough space in your inventory.");
			return;
		}
		client.startAnimation(832);
		client.getItemAssistant().addItem(1351, 1);
		client.sendMessage(
				"You take the axe from the log.");
		new Object(5582, x, y, client.heightLevel, 2, 10, 5581, 100);
	}

}
