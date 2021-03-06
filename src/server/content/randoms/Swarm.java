package 
server.content.randoms;

import server.npcs.NPCHandler;
import server.players.Client;
import server.util.Misc;

/**
 * Swarm Event
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class Swarm {

	private static int checkStats(Client client, boolean bot) {
		if (bot) {
			return client.getPlayerAssistant().getLevelForXP(client.playerXP[client.playerHitpoints]) * 3;
		} else {
			return client.getPlayerAssistant().getLevelForXP(client.playerXP[client.playerHitpoints]) * 2;
		}
	}

	/**
	 * Spawns the swarm.
	 * 
	 * @param c
	 */
	public static void spawnSwarm(Client client) {
		NPCHandler.spawnNpc(client, // param
				411, // npctype
				client.absX + Misc.random(1), // posX
				client.absY + Misc.random(1), // posY
				client.heightLevel, // height
				0, // walkingtype
				1, // HP
				2, // maxhit
				checkStats(client, false),
				3 * client.combatLevel, // defence
				true, // attackplayer
				false); // headicon
		client.autoRet = 0;
	}

	/**
	 * Spawns the swarm. If suspect a botter ... this one is stronger, botter
	 * has to react faster to survive.
	 * 
	 * @param c
	 */
	public static void checkBot(Client client) {
		NPCHandler.spawnNpc(client, // param
				411, // npctype
				client.absX + Misc.random(1), // posX
				client.absY + Misc.random(1), // posY
				client.heightLevel, // height
				0, // walkingtype
				1, // HP
				6, // maxhit
				checkStats(client, true), // attack
				4 * client.combatLevel, // defence
				true, // attackplayer
				false); // headicon
		client.autoRet = 0;
	}
}
