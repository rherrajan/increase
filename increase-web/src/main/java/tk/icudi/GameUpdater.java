package tk.icudi;

import java.io.IOException;
import java.util.Date;

public class GameUpdater implements Runnable {

	private Game game;
	private LogProvider provider;

	private Thread updateThread;
	private boolean running;

	public GameUpdater(Game game, LogProvider provider) {
		this.game = game;
		this.provider = provider;
	}

	public void start() {
		if (updateThread != null) {
			stop();
			throw new RuntimeException("already started");
		}
		running = true;
		this.updateThread = new Thread(this);
		updateThread.start();
		System.out.println("initiate start ...");
	}

	@Override
	public void run() {
		System.out.println("game started");
		try {
			while (true) {
				if (this.running) {
					update();

					// for (Entry<Portal, String> entry :
					// game.getPortalOwners().entrySet()) {
					// System.out.println(entry.getValue() + " owns " +
					// entry.getKey());
					// }

					Location userLoc = getPortalMainStation();
					long now = System.currentTimeMillis();
					System.out.println("");
					System.out.println(new Date(now));
					for (Player player : game.getSortetPlayers(userLoc, now)) {
						System.out.println(player.getMessage(userLoc, now));
					}

					Thread.sleep(5 * 60 * 1000);
				} else {
					System.out.println("game stopped");
					break;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private Location getPortalMainStation() {
		Location userLoc = new Location();
		userLoc.setLat(50107356);
		userLoc.setLng(8664123);

		return userLoc;
	}

	void update() {
		try {
			game.appendLogsFrom(provider);
		} catch (IOException e) {
			throw new RuntimeException("error during game", e);
		}
	}

	public void stop() {
		System.out.println("initiate stop ...");
		running = false;
	}

}
