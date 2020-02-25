package server.objects;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import server.util.CycleEvent;
import server.util.CycleEventContainer;
import server.util.CycleEventHandler;
import server.util.Misc;
import server.Server;
import server.content.objects.DoubleGates;
import server.players.Client;

/**
 * @author Sanity
 */

public class ObjectManager {
	
	public CopyOnWriteArrayList<Object> object = new CopyOnWriteArrayList<Object>();

	public ArrayList<Object> objects = new ArrayList<Object>();
	private ArrayList<Object> toRemove = new ArrayList<Object>();
	public void process() {
		for (Object o : objects) {
			if (o.tick > 0)
				o.tick--;
			else {
				updateObject(o);
				toRemove.add(o);
			}		
		}
		for (Object o : toRemove) {
			if (isObelisk(o.newId)) {
				int index = getObeliskIndex(o.newId);
				if (activated[index]) {
					activated[index] = false;
					teleportObelisk(index);
				}
			}
			objects.remove(o);	
		}
		toRemove.clear();
	}
	
	public void removeObject(int x, int y) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client)Server.playerHandler.players[j];
				c.getPA().object(-1, x, y, 0, 10);			
			}	
		}	
	}
	
	public void updateObject(Object o) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client)Server.playerHandler.players[j];
				c.getPA().object(o.newId, o.objectX, o.objectY, o.face, o.type);			
			}	
		}	
	}
	
	public void removeGameObjects(int objectClass) {
		for(Object o: object) {
			if(o.objectClass == objectClass)
				object.remove(o);
		}
	}
	
	public void placeObject(Object o) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client)Server.playerHandler.players[j];
				if (c.distanceToPoint(o.objectX, o.objectY) <= 60)
					c.getPA().object(o.objectId, o.objectX, o.objectY, o.face, o.type);
			}	
		}
	}
	
	public Object getObject(int x, int y, int height) {
		for (Object o : objects) {
			if (o.objectX == x && o.objectY == y && o.height == height)
				return o;
		}	
		return null;
	}
	
	public void loadObjects(Client c) {
		if (c == null)
			return;
		for (Object o : objects) {
			if (loadForPlayer(o,c))
				c.getPA().object(o.objectId, o.objectX, o.objectY, o.face, o.type);
		}
		loadCustomSpawns(c);
	}
	
	private int[][] customObjects = {{}};
	public void loadCustomSpawns(Client c) {
		c.getPA().checkObjectSpawn(-1, 2950, 3450, 0, 10);
		c.getPA().checkObjectSpawn(1596, 3008, 3850, 1, 0);
		c.getPA().checkObjectSpawn(1596, 3008, 3849, -1, 0);
		c.getPA().checkObjectSpawn(1596, 3040, 10307, -1, 0);
		c.getPA().checkObjectSpawn(1596, 3040, 10308, 1, 0);
		c.getPA().checkObjectSpawn(1596, 3022, 10311, -1, 0);
		c.getPA().checkObjectSpawn(1596, 3022, 10312, 1, 0);
		c.getPA().checkObjectSpawn(1596, 3044, 10341, -1, 0);
		c.getPA().checkObjectSpawn(1596, 3044, 10342, 1, 0);
		
		
		if (c.heightLevel == 0)
			c.getPA().checkObjectSpawn(2492, 2911, 3614, 1, 10);
		else
			c.getPA().checkObjectSpawn(-1, 2911, 3614, 1, 10);
	}
	
	public final int IN_USE_ID = 14825;
	public boolean isObelisk(int id) {
		for (int j = 0; j < obeliskIds.length; j++) {
			if (obeliskIds[j] == id)
				return true;
		}
		return false;
	}
	public int[] obeliskIds = {14829,14830,14827,14828,14826,14831};
	public int[][] obeliskCoords = {{3154,3618},{3225,3665},{3033,3730},{3104,3792},{2978,3864},{3305,3914}};
	public boolean[] activated = {false,false,false,false,false,false};
	
	public void startObelisk(int obeliskId) {
		int index = getObeliskIndex(obeliskId);
		if (index >= 0) {
			if (!activated[index]) {
				activated[index] = true;
				addObject(new Object(14825, obeliskCoords[index][0], obeliskCoords[index][1], 0, -1, 10, obeliskId,16));
				addObject(new Object(14825, obeliskCoords[index][0] + 4, obeliskCoords[index][1], 0, -1, 10, obeliskId,16));
				addObject(new Object(14825, obeliskCoords[index][0], obeliskCoords[index][1] + 4, 0, -1, 10, obeliskId,16));
				addObject(new Object(14825, obeliskCoords[index][0] + 4, obeliskCoords[index][1] + 4, 0, -1, 10, obeliskId,16));
			}
		}	
	}
	
	public int getObeliskIndex(int id) {
		for (int j = 0; j < obeliskIds.length; j++) {
			if (obeliskIds[j] == id)
				return j;
		}
		return -1;
	}
	
	public void teleportObelisk(int port) {
		int random = Misc.random(5);
		while (random == port) {
			random = Misc.random(5);
		}
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client)Server.playerHandler.players[j];
				int xOffset = c.absX - obeliskCoords[port][0];
				int yOffset = c.absY - obeliskCoords[port][1];
				if (c.goodDistance(c.getX(), c.getY(), obeliskCoords[port][0] + 2, obeliskCoords[port][1] + 2, 1)) {
					c.getPA().startTeleport2(obeliskCoords[random][0] + xOffset, obeliskCoords[random][1] + yOffset, 0);
				}
			}		
		}
	}
	
	public boolean loadForPlayer(Object o, Client c) {
		if (o == null || c == null)
			return false;
		return c.distanceToPoint(o.objectX, o.objectY) <= 60 && c.heightLevel == o.height;
	}
	
	public void addObject(Object o) {
		if (getObject(o.objectX, o.objectY, o.height) == null) {
			objects.add(o);
			placeObject(o);
		}	
	}

	public static void singleGateTicks(final Client player, final int objectId, final int objectX, final int objectY, final int x1, final int y1, final int objectH, final int face, int ticks) {
		CycleEventHandler.addEvent(player, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (DoubleGates.gateAmount == 0) {
					container.stop();
					return;
				}
				Server.objectHandler.placeObject(new Objects(-1, x1, y1, objectH, face, 0, 0));
				Server.objectHandler.placeObject(new Objects(objectId, objectX, objectY, objectH, face, 0, 0));
				container.stop();
			}

			@Override
			public void stop() {
				if (DoubleGates.gateAmount == 1) {
					DoubleGates.gateAmount = 0;
				}
			}
		}, ticks);
	}
	
	public static void doubleGateTicks(final Client player, final int objectId, final int objectX, final int objectY, final int x1, final int y1, final int x2, final int y2, final int objectH, final int face, int ticks) {
		CycleEventHandler.addEvent(player, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (DoubleGates.gateAmount == 0) {
					container.stop();
					return;
				}
				Server.objectHandler.placeObject(new Objects(-1, x1, y1, objectH, face, 0, 0));
				Server.objectHandler.placeObject(new Objects(-1, x2, y2, objectH, face, 0, 0));
				Server.objectHandler.placeObject(new Objects(objectId, objectX, objectY, objectH, face, 0, 0));
				container.stop();
			}

			@Override
			public void stop() {
				if (DoubleGates.gateAmount == 2) {
					DoubleGates.gateAmount = 1;
				} else if (DoubleGates.gateAmount == 1) {
					DoubleGates.gateAmount = 0;
				}
			}
		}, ticks);
	}

 




}