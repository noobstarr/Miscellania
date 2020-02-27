package server.content.items;

import server.Config;
import server.Server;
import server.players.Client;
import server.util.CycleEvent;
import server.util.CycleEventContainer;
import server.util.CycleEventHandler;
import server.util.Misc;

/*
 * Author - Ferocious & Mod Loc (ZacharySaysWut@yahoo.com)
 * http://www.rune-server.org/members/ags 
 * http://www.rune-server.org/members/Ferocious
 */

public class AdvancedMysteryBox {

	public static boolean Canusebox = true;
	
	public static int Common [] = 
	{1323, 1313, 1315, 1321, 1109}; // Add more item Id's
	
	public static int Uncommon [] = 
	{4587, 4586}; // Add more item Id's
	
	public static int Rare [] = 
	{4151}; // Add more item Id's

	public static int GenerateMyrsteryPrize(final Client c) {
		 CycleEventHandler.addEvent(c, new CycleEvent() {
			int BoxTimer = 6;
			int Coins = 50000 + Misc.random(25000);
			@Override
			public void execute(CycleEventContainer Box) {
				Canusebox = false;
				if (BoxTimer == 6) {
					c.sendMessage("Opening...");
				}
				if (BoxTimer == 0) {
					c.getItems().addItem(995, Coins);
					int Random = Misc.random(100);
					if (Random < 2) {
						c.getItems().addItem(Rare[(int) (Math.random() * Rare.length)], 1);
						for (int j = 0; j < Server.playerHandler.players.length; j++) {
							if (Server.playerHandler.players[j] != null) {
								Client c2 = (Client)Server.playerHandler.players[j];
								c2.sendMessage("[@mag@Mystery Box@bla@] "+c.playerName+" has just received an @cya@ULTRA RARE@bla@ item!");
							}
						}
					} else if (Random > 2 && Random <= 20) {
						c.getItems().addItem(Uncommon[(int) (Math.random() * Uncommon.length)], 1);
						c.sendMessage("You have recieved a @red@Rare @bla@item from the @mag@Mystery Box@bla@!");
					} else if (Random > 20 && Random <= 50) {
						c.getItems().addItem(Rare[(int) (Math.random() * Rare.length)], 1);
						c.sendMessage("You have recieved an @yel@Uncommon @bla@item from the @mag@Mystery Box@bla@!");
					} else if (Random > 50) {
						c.getItems().addItem(Common[(int) (Math.random() * Common.length)], 1);
						c.sendMessage("You have recieved a @gre@Common @bla@item from the @mag@Mystery Box@bla@!");
					}
				}
				if (c == null || BoxTimer <= 0) {
				   	Box.stop();
					Canusebox = true;
                    return; 
				}
				
				if (BoxTimer >= 0) { 
					BoxTimer--;
				}
			}@Override
			public void stop() {
			}
		}, 1);
		return Common[(int) (Math.random() * Common.length)];
	}
	
	public static void Open(int itemID, Client c, int slot) {
		if (itemID == 6199) {
			if (c.getItems().freeSlots() > 1) {
				if (Canusebox == true) {
					c.getItems().deleteItem(6199, slot, 1);
					GenerateMyrsteryPrize(c);
				} 
			} else {
				c.sendMessage("You need atleast 2 slots to open the Mystery box.");
			}
		}
	}
	
}