package server.content.randoms;

import server.npcs.NPCHandler;
import server.players.Client;
import server.util.Misc;

public class Shade {

	private static int[][] shade = { { 3, 10, 425, 19, 1 },
			{ 11, 20, 426, 40, 3 }, { 21, 40, 427, 60, 5 },
			{ 41, 60, 428, 80, 8 }, { 61, 90, 429, 105, 11 },
			{ 91, 138, 430, 120, 13 }, };

	public static void spawnShade(Client client) {
		for (int[] element : shade) {
			if (client.shadeSpawned == false) {
			if (client.combatLevel >= element[0]
					&& client.combatLevel <= element[1]) {
				NPCHandler
						.spawnNpc(client, element[2],
								client.absX + Misc.random(1), client.absY
										+ Misc.random(1), client.heightLevel,
								0,
								element[3], // HP
								element[4], // maxhit
								(int) (NPCHandler.getNpcListCombat(element[3]) * 1.5), // defence
								(int) (NPCHandler.getNpcListCombat(element[3]) * 1.5),
								true, false); // attack
				client.randomActions = 0;
				client.shadeSpawned = true;
				}
			}
		}
	}
}