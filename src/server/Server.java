package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.text.DecimalFormat;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.util.HashedWheelTimer;


import server.net.PipelineFactory;
import server.npcs.NPCHandler;
import server.objects.ObjectHandler;
import server.objects.ObjectManager;
import server.players.PlayerHandler;
import server.players.PlayerManager;
import server.util.CycleEventHandler;
import server.util.Logger;
import server.util.StillGraphicsManager;
import server.util.Task;
import server.util.TaskScheduler;
import server.clipping.ObjectDef;
import server.clipping.Region;
import server.content.*;
import server.content.minigames.CastleWars;
import server.content.minigames.FightCaves;
import server.content.minigames.FightPits;
import server.content.npc.ShopHandler;
import server.content.objects.Doors;
import server.content.objects.DoubleDoors;
import server.items.ItemDefinitions;
import server.items.ItemHandler;

/**
 * The main class needed to start the server.
 * @author Sanity
 * @author Graham
 * @author Blake
 * @author Ryan Lmctruck30
 * Revised by Shawn
 * Notes by Shawn
 */
public class Server {
	
	
	/**
	 *Calls to manage the players on the server.
	 */
	public static PlayerManager playerManager = null;
	private static StillGraphicsManager stillGraphicsManager = null;
	
	
	/**
	 * Sleep mode of the server.
	 */
	public static boolean sleeping;
	
	
	/**
	 * Calls the rate in which an event cycles. 
	 */
	public static final int cycleRate;
	
	
	/**
	 * Server updating.
	 */
	public static boolean UpdateServer = false;
	
	
	/**
	 * Calls in which the server was last saved.
	 */
	public static long lastMassSave = System.currentTimeMillis();
	
	
	/**
	 * Calls the usage of CycledEvents. 
	 */
	private static long cycleTime, cycles, totalCycleTime, sleepTime;
	
	
	/**
	 * Used for debugging the server.
	 */
	private static DecimalFormat debugPercentFormat;
	
	
	/**
	 * Forced shutdowns.
	 */
	public static boolean shutdownServer = false;		
	public static boolean shutdownClientHandler;	
	
	
	/**
	 * Used to identify the server port.
	 */
	public static int serverlistenerPort;
	
	
	/**
	 * Calls the usage of player items.
	 */
	public static ItemHandler itemHandler = new ItemHandler();
	
	
	/**
	 * Handles logged in players.
	 */
	public static PlayerHandler playerHandler = new PlayerHandler();
	
	
	/**
	 * Handles global NPCs.
	 */
    public static NPCHandler npcHandler = new NPCHandler();
    
    
    /**
     * Handles global shops.
     */
	public static ShopHandler shopHandler = new ShopHandler();
	
	
	/**
	 * Handles global objects.
	 */
	public static ObjectHandler objectHandler = new ObjectHandler();
	public static ObjectManager objectManager = new ObjectManager();
	
	
	/**
	 * Handles the castlewars minigame.
	 */
	public static CastleWars castleWars = new CastleWars();
	
	
	/**
	 * Handles the fightpits minigame.
	 */
	public static FightPits fightPits = new FightPits();
	
	
	
	/**
	 * Handles the fightcaves minigames.
	 */
	public static FightCaves fightCaves = new FightCaves();
        
	
	/**
	 * Handles the task scheduler.
	 */
	private static final TaskScheduler scheduler = new TaskScheduler();

	
	/**
	 * Gets the task scheduler.
	 */
	public static TaskScheduler getTaskScheduler() {
		return scheduler;
	}
	
	static {
		if(!Config.SERVER_DEBUG) {
			serverlistenerPort = 43594;
		} else {
			serverlistenerPort = 43594;
		}
		cycleRate = 600;
		shutdownServer = false;
		sleepTime = 0;
		debugPercentFormat = new DecimalFormat("0.0#%");
	}
	
	
	/**
	 * Starts the server.
	 */    
	public static void main(java.lang.String args[]) throws NullPointerException, IOException {

		long startTime = System.currentTimeMillis();
		System.setOut(new Logger(System.out));
		System.setErr(new Logger(System.err));
	
		bind();
                
                
		playerManager = PlayerManager.getSingleton();
		playerManager.setupRegionPlayers();
		stillGraphicsManager = new StillGraphicsManager();

		
	
		Doors.getSingleton().load();
		DoubleDoors.getSingleton().load();
		ObjectDef.loadConfig();
		Region.load();
		ItemDefinitions.read();
		Connection.initialize();

		
		/**
		 *Successfully loaded the server.
		 */
                long endTime = System.currentTimeMillis();
                long elapsed = endTime-startTime;
                System.out.println("Server started up in "+elapsed+" ms");
		System.out.println("Server listening on port 127.0.0.1:" + serverlistenerPort);
		
                
                
        /**
		 * Main server tick.
		 */
		scheduler.schedule(new Task() {
			@Override
                    protected void execute() {
				objectManager.process();
                        itemHandler.process();
                        playerHandler.process();	
                        npcHandler.process();
                        shopHandler.process();
                        CycleEventHandler.process();
                        fightPits.process();
                        }
                });
		
	}
	
	/**
	 * Logging execution.
	 */
	public static boolean playerExecuted = false;
	
	
	/**
	 * Gets the sleep mode timer and puts the server into sleep mode.
	 */
	public static long getSleepTimer() {
		return sleepTime;
	}
	
	
	/**
	 * Gets the Graphics manager.
	 */
	public static StillGraphicsManager getStillGraphicsManager() {
		return stillGraphicsManager;
	}
	
	
	/**
	 * Gets the Player manager.
	 */
	public static PlayerManager getPlayerManager() {
		return playerManager;
	}
	
	
	/**
	 * Gets the Object manager.
	 */
	public static ObjectManager getObjectManager() {
		return objectManager;
	}
	
	
/**
 * Java connection.
 * Ports.
 */
        private static void bind() {
            ServerBootstrap serverBootstrap = new ServerBootstrap (new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
            serverBootstrap.setPipelineFactory (new PipelineFactory(new HashedWheelTimer()));
            serverBootstrap.bind (new InetSocketAddress(serverlistenerPort));	
        }
	
}
