package server.objects;

import server.Server;

public class Object {
	
	public int objectId;
	public int objectX;
	public int objectY;
	public int height;
	public int face;
	public int type;
	public int newId;
	public int tick;
	public boolean remove;
	public boolean isFire = false;
	public int objectClass; // dunno why i named it like this
	
	
	private int absX;
	private int absY;
	private int heightLevel;
	

	
	public Object(int id, int x, int y, int height, int face, int type, int newId, int ticks) {
		this.objectId = id;
		this.objectX = x;
		this.objectY = y;
		this.height = height;
		this.face = face;
		this.type = type;
		this.newId = newId;
		this.tick = ticks;
		this.remove = true;
		this.objectClass = 0;
		Server.objectManager.addObject(this);
	}
	
	//objectClass: 0 = default objects, 1 = castlewars objects
	public Object(int id, int x, int y, int height, int face, int type, int newId, int ticks, int objectClass) {
		this.objectId = id;
		this.objectX = x;
		this.objectY = y;
		this.height = height;
		this.face = face;
		this.type = type;
		this.newId = newId;
		this.tick = ticks;
		//this.remove = remove;
		this.objectClass = objectClass;
		Server.objectManager.addObject(this);
	}
	
	public Object(int id, int x, int y, int height, int face, int type, int newId, int ticks, int objectClass, boolean fire) {
		this.objectId = id;
		this.objectX = x;
		this.objectY = y;
		this.height = height;
		this.face = face;
		this.type = type;
		this.newId = newId;
		this.tick = ticks;
		//this.remove = remove;
		this.objectClass = objectClass;
		this.isFire = fire;
		Server.objectManager.addObject(this);
	}


	public int getAbsX() {
		return absX;
	}

	public int getAbsY() {
		return absY;
	}

	public int getHeightLevel() {
		return heightLevel;
	}
}