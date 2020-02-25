package server.content.npc;

import server.Server;
import server.npcs.NPC;
import server.npcs.NPCHandler;
import server.players.Client;
import server.players.Player;

public class Pets {

	public void pickUp(Client c, int Type) {
		if (c.inWild()){
			c.sendMessage("How did you get a pet in the wilderness?");
		}
		for (int i = 0; i < NPCHandler.maxNPCs; i++) {
			if (NPCHandler.npcs[i] == null)
					continue;	
			}       
		for (int i = 0; i < NPCHandler.maxNPCs; i++) {
			if (NPCHandler.npcs[i] != null) {
				if (NPCHandler.npcs[i].npcType == Type) {
					if (NPCHandler.npcs[i].spawnedBy == c.playerId && NPCHandler.npcs[i].spawnedBy > 0) {
						NPCHandler.npcs[i].absX = 0;
						NPCHandler.npcs[i].absY = 0;
						NPCHandler.npcs[i] = null;
					break;					
					}
				}
			}			
		}
	}
	public static int summonItemId(int itemId) {
		if (itemId == 1555) {
			return 761;
		} else if (itemId == 1556) {
			return 762;
		} else if (itemId == 1557) {
			return 763;
		} else if (itemId == 1558) {
			return 764;
		} else if (itemId == 1559) {
			return 765;
		} else if (itemId == 1560) {
			return 766;
		} else if (itemId == 1561) {
			return 768;
		} else if (itemId == 1562) {
			return 769;
		} else if (itemId == 1563) {
			return 770;
		} else if (itemId == 1564) {
			return 771;
		} else if (itemId == 1565) {
			return 772;
		} else if (itemId == 1566) {
			return 773;
		} else if (itemId == 7585) {
			return 3507;
		} else if (itemId == 7584) {
			return 3506;
		} else if (itemId == 7583) {
			return 3505;
		}
		return 0;
	}
	
		public static int[][] Pets = { 
			{3505, 7583}, //Hell Kitten		
			{3506, 7584}, //Lazy Hellcat
			{766, 1560}, //Hell Kitten	
			{3507, 7585}, //Wily hellcat	
			{765, 1559}, //Pet Kitten
			{764, 1558}, //Pet Kitten
			{763, 1557}, //Pet Kitten	
			{762, 1556}, //Pet Kitten	
			{761, 1555}, //Pet Kitten
			{768, 1561}, //Pet Kitten	
			{769, 1562}, //Pet Kitten
			{770, 1563}, //Pet Kitten	
			{771, 1564}, //Pet Kitten	
			{772, 1565}, //Pet Kitten	
			{773, 1566}, //Pet Kitten
			{4000, 12500}, //Prince
			{4001, 12501}, //Ele Jr
			{4002, 12502}, //Bandos
			{4003, 12503}, //Arma
			{4004, 12504}, //Zammy
			{4005, 12505}, //Sara
			{4006, 12506}, //Dag Sup
			{4007, 12507}, //Dag Pri
			{4008, 12508}, //Dag Rex
		};
	
	public void pickUpClean(Client c, int id) {
		for (int i = 0; i < Pets.length; i++)
			if (Pets[i][0] == id)
				c.getItems().addItem(Pets[i][1], 1);
		for (int i = 0; i < NPCHandler.maxNPCs; i++) {
			if (NPCHandler.npcs[i] == null)
				continue;
			if (NPCHandler.npcs[i].npcType == id) {
				NPCHandler.npcs[i].absX = 0;
				NPCHandler.npcs[i].absY = 0;
			}
		}
		c.hasNpc = false;
	}
	

	
	 public static void summonPet(Player player, int npcType, int x, int y, int heightLevel)
     {
             int slot = -1;
             for (int i = 1; i < NPCHandler.maxNPCs; i++)
             {
                     if (NPCHandler.npcs[i] == null)
                     {
                             slot = i;
                             break;
                     }
             }
             if (slot == -1)
             {
                     return;
             }
             NPC newNPC = new NPC(slot, npcType);
             newNPC.absX = x;
             newNPC.absY = y;
             newNPC.makeX = x;
             newNPC.makeY = y;
             newNPC.heightLevel = heightLevel;
             newNPC.walkingType = 0;
             newNPC.HP = 0;
             newNPC.MaxHP = 0;
             newNPC.maxHit = 0;
             newNPC.attack = 0;
             newNPC.defence = 0;
             newNPC.spawnedBy = player.getId();
             newNPC.underAttack = true;
             newNPC.facePlayer(player.playerId);
             newNPC.summoned = true;
             newNPC.summonedBy = player.playerId;
             player.summonId = npcType;
             player.hasNpc = true;
             NPCHandler.npcs[slot] = newNPC;
     }
	
	 public static void deletePet(NPC pet)
     {
             pet.absX = -1;
             pet.absY = -1;
             pet.makeX = -1;
             pet.makeY = -1;
             pet.heightLevel = -1;
             pet.walkingType = -1;
             pet.HP = -1;
             pet.MaxHP = -1;
             pet.maxHit = -1;
             pet.attack = -1;
             pet.defence = -1;
             pet.isDead = true;
             pet.applyDead = true;
     }
}