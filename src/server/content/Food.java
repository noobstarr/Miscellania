package server.content;

import java.util.HashMap;

import server.players.Client;

/**
 * @author Sanity
 */

public class Food {
	
	
	private Client c;
	
	public Food (Client c) {
		this.c = c;	
	}
	
	
	
	public static enum FoodToEat {		
		Easter_Egg(1961, 12, 0, "Easter Egg"),
		Pumpkin(1959, 14, 0, "Pumpkin"),
		Half_Jug_of_Wine(1989, 7, 0, "Half Full Wine Jug"),
		Wine(1993, 11, 1989, "Wine"),
		MANTA_RAY(391, 22, 0, "Manta Ray"),
		SHARK(385, 20, 0, "Shark"),
		LOBSTER(379, 12, 0, "Lobster"),
		BEER(1917, 1, 0, "Beer"),
		GREENMANS_ALE(1909, 1, 0, "Greenman's Ale"),
		TROUT(333, 7, 0, "Trout"),
		SALMON(329, 9, 0, "Salmon"),
		SWORDFISH(373, 14, 0, "Swordfish"),
		TUNA(361, 10, 0, "Tuna"),
		MONKFISH(7946, 16, 0, "Monkfish"),
		SEA_TURTLE(397, 21, 0, "Sea Turtle"),
		CABBAGE(1965, 1, 0, "Cabbage"),
		CAKE_FULL(1891, 4, 1893, "Cake"), 
		CAKE_2(1893, 4, 1895, "Cake slice"),
		CAKE_1(1895, 4, 0, "Cake slice"),
		BASS(365, 13, 0, "Bass"),
		COD(339, 7, 0, "Cod"),
		POTATO(1942, 1, 0, "Potato"),
		BAKED_POTATO(6701, 4, 0, "Baked Potato"),
		POTATO_WITH_CHEESE(705, 16, 0, "Potato with Cheese"),
		EGG_POTATO(7056, 16, 0, "Egg Potato"),
		CHILLI_POTATO(7054, 14, 0, "Chilli Potato"),
		MUSHROOM_POTATO(7058, 20, 0, "Mushroom Potato"),
		TUNA_POTATO(7060, 22, 0, "Tuna Potato"),
		SHRIMPS(315, 3, 0, "Shrimps"),
		HERRING(347, 5, 0, "Herring"),
		CHOCOLATE_CAKE(1897, 5, 1899, "Chocolate Cake"),
		HALF_CHOCOLATE_CAKE(1899, 5, 1901, "2/3 Chocolate Cake"),
		CHOCOLATE_SLICE(1901, 5, 0, "Chocolate Slice"),
		ANCHOVIES(319, 1, 0, "Anchovies"),
		PLAIN_PIZZA(2289, 7, 2291, "Plain Pizza"),
		HALF_PLAIN_PIZZA(2291, 7, 0, "1/2 Plain pizza"),
		MEAT_PIZZA(2293, 8, 2295, "Meat Pizza"),
		HALF_MEAT_PIZZA(2295, 8, 0, "1/2 Meat Pizza"),
		ANCHOVY_PIZZA(2297, 9, 2299, "Anchovy Pizza"),
		HALF_ANCHOVY_PIZZA(2299, 9, 0, "1/2 Anchovy Pizza"),
		PINEAPPLE_PIZZA(2301, 11, 2303, "Pineapple Pizza"),
		HALF_PINEAPPLE_PIZZA(2303, 11, 0, "1/2 Pineapple Pizza"),
		BREAD(2309, 5, 0, "Bread"),
		APPLE_PIE(2323, 7, 2335, "Apple Pie"),
		HALF_APPLE_PIE(2335, 7, 0, "Half Apple Pie"),
		REDBERRY_PIE(2325, 5, 2333, "Redberry Pie"),
		HALF_REDBERRY_PIE(2333, 5, 0, "Half Redberry Pie"),
		UGTHANKI_KEBAB(1883, 2, 0, "Ugthanki kebab"),
		MEAT_PIE(2327, 6, 2331, "Meat Pie"),
		HALF_MEAT_PIE(2331, 6, 0, "Half Meat Pie"),
		SUMMER_PIE(7218, 11, 7220, "Summer Pie"),
		HALF_SUMMER_PIE(7220, 11, 0, "Half Summer Pie"), 
		PIKE(351, 8, 0, "Pike"),
		POTATO_WITH_BUTTER(6703, 14, 0, "Potato with Butter"),
		BANANA(1963, 2, 0, "Banana"),
		PEACH(6883, 8, 0, "Peach"),
		ORANGE(2108, 2, 0, "Orange"),
		PINEAPPLE_RINGS(2118, 2, 0, "Pineapple Rings"),
		PINEAPPLE_CHUNKS(2116, 2, 0, "Pineapple Chunks"),
		EASTER_EGG(7928, 1, 0, "Easter Egg"),
		EASTER_EGG2(7929, 1, 0, "Easter Egg"),
		EASTER_EGG3(7930, 1, 0, "Easter Egg"),
		EASTER_EGG4(7931, 1, 0, "Easter Egg"),
		EASTER_EGG5(7932, 1, 0, "Easter Egg"),
		EASTER_EGG6(7933, 1, 0, "Easter Egg"),
		PURPLE_SWEETS(10476, 9, 0, "Purple Sweets"),
		POT_OF_CREAM(2130, 1, 0, "Pot of cream"),
		DARK_CRAB(11936, 22, 0, "Dark crab"),
		COOKED_KARAMBWAN(3144, 18, 0, "Cooked karambwan"),
		ROCKTAIL(15272, 23, 0, "Cooked rocktail"),
		/**
		 * Fish Combo Foods
		 */
		KARAMBWANJI(3151, 3, 0, "Karambwanji"),
		/**
		 * Gnome Combo Foods
		 */
		TOAD_CRUNCHIES(2217, 8, 0, "Toad crunchies"),
		SPICY_CRUNCHIES(2213, 7, 0, "Spicy cruncies"),
		WORM_CRUNCHIES(2205, 8, 0, "Worm crunchies"),
		CHOCOCHIP_CRUNCHIES(9544, 7, 0, "Chocochip crunchies"),
		FRUIT_BATTA(2277, 11, 0, "Fruit batta"),
		TOAD_BATTA(2255, 11, 0, "Toad batta"),
		WORM_BATTA(2253, 11, 0, "Worm batta"),
		VEGETABLE_BATTA(2281, 11, 0, "Vegatable batta"),
		CHEESE_AND_TOMATO_BATTA(9535, 11, 0, "Cheese and tomato batta"),
		WORM_HOLE(2191, 12, 0, "Worm hole"),
		VEG_BALL(2195, 12, 0, "Veg ball"),
		PRE_MADE_VEG_BALL(2235, 12, 0, "Pre made veg ball"),
		TANGLED_TOAD_LEGS(2187, 15, 0, "Tangled toad legs"),
		CHOCOLATE_BOMB(2185, 15, 0, "Chocolate bomb"),
		BANDAGES(4049, 99, 0, "Bandages");
		
		
		private int id; private int heal; private int replacementId; private String name;
		
		private FoodToEat(int id, int heal, int replacementId, String name) {
			this.id = id;
			this.heal = heal;
			this.replacementId = replacementId;
			this.name = name;		
		}
		
		public int getId() {
			return id;
		}

		public int getHeal() {
			return heal;
		}
		
		public int getReplacement() {
			return replacementId;
		}
		
		public String getName() {
			return name;
		}
		public static HashMap <Integer,FoodToEat> food = new HashMap<Integer,FoodToEat>();
		
		public static FoodToEat forId(int id) {
			return food.get(id);
		}
		
		static {
		for (FoodToEat f : FoodToEat.values())
			food.put(f.getId(), f);
		}
	}
	
	public void eat(int id, int slot) {
		if (c.duelRule[6]) {
			c.sendMessage("You may not eat in this duel.");
			return;
		}
		if (System.currentTimeMillis() - c.foodDelay >= 1500 && c.playerLevel[3] > 0) {
			c.getCombat().resetPlayerAttack();
			c.attackTimer += 2;
			c.startAnimation(829);
			c.getItems().deleteItem(id,slot,1);
			
			FoodToEat f = FoodToEat.food.get(id);
			if (f.getReplacement() != 0) {
				c.getItems().addItem(f.getReplacement(), 1);
			}
			if (c.playerLevel[3] < c.getLevelForXP(c.playerXP[3])) {
				c.playerLevel[3] += f.getHeal();
				if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3]))
					c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
			}
			c.foodDelay = System.currentTimeMillis();
			c.getPA().refreshSkill(3);
			c.sendMessage("You eat the " + f.getName() + ".");
		}		
	}

	
	public boolean isFood(int id) {
		return FoodToEat.food.containsKey(id);
	}	
	

}