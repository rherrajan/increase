package tk.icudi;

import java.io.IOException;
import java.util.Map.Entry;

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
					try {
						game.appendLogsFrom(provider);
					} catch (IOException e) {
						throw new RuntimeException("error during game", e);
					}
					for (Entry<Portal, String> entry : game.getPortalOwners().entrySet()) {
						System.out.println(entry.getValue() + " owns " + entry.getKey());
					}
					Thread.sleep(60 * 1000);
				} else {
					System.out.println("game stopped");
					break;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		System.out.println("initiate stop ...");
		running = false;
	}

}
