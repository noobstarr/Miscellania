package server.content.randoms;

import server.npcs.NPCHandler;
import server.players.Client;
import server.util.Misc;

public class Zombie {

	private static int[][] zombie = { { 3, 10, 419, 28, 2, 10, 10 },
			{ 11, 20, 420, 36, 4, 20, 20 }, { 21, 40, 421, 57, 6, 30, 30 },
			{ 61, 90, 422, 90, 8, 40, 40 }, { 91, 110, 423, 130, 10, 50, 50 },
			{ 111, 138, 424, 160, 12, 60, 60 } };

	public static void spawnZombie(Client c) {
		for (int[] element : zombie) {
			if (c.zombieSpawned == false) {
			if (c.combatLevel >= element[0] && c.combatLevel <= element[1]) {
				NPCHandler.spawnNpc(c, element[2], c.absX + Misc.random(1),
						c.absY + Misc.random(1), c.heightLevel, 0, element[3],
						element[4], element[5], element[6], true, false);
				c.randomActions = 0;
				c.zombieSpawned = true;
				NPCHandler.npcs[element[2]].forceChat("Braaaainssss!");
				}
			}
		}
	}
}