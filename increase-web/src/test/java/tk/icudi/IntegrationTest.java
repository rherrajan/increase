package tk.icudi;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map.Entry;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore("curls ingress. not to be used often")
public class IntegrationTest {

	@Test
	public void test_parse() throws Exception {

		PlextParser parser = new PlextParser(new LogProviderCurl());

		parser.updateLogs();
		List<LogEntry> logs = parser.extractLogEntries();

		assertEquals(50, logs.size());

		for (LogEntry logEntry : logs) {
			System.out.println("logEntry: " + logEntry);
		}
	}

	@Test
	public void test_append() throws Exception {

		LogProvider provider = new LogProviderCurl();
		Game game = new Game();

		GameUpdater updater = new GameUpdater(game, provider);

		try {
			updater.start();

			int portalsOnTime1 = game.getPortalOwners().size();
			for (Entry<Portal, String> entry : game.getPortalOwners().entrySet()) {
				System.out.println(entry.getValue() + ": " + entry.getKey());
			}
			System.out.println(portalsOnTime1 + " portals");

			Thread.sleep(90 * 1000);
			int portalsOnTime2 = game.getPortalOwners().size();
			for (Entry<Portal, String> entry : game.getPortalOwners().entrySet()) {
				System.out.println(entry.getValue() + ": " + entry.getKey());
			}
			System.out.println(portalsOnTime2 + " portals");

			Assert.assertThat(portalsOnTime2, Matchers.greaterThan(portalsOnTime1));

		} finally {
			updater.stop();
		}

	}

	@Test
	public void test_players() throws Exception {

		LogProvider provider = new LogProviderCurl();
		Game game = new Game();
		GameUpdater updater = new GameUpdater(game, provider);
		updater.update();

		for (LogEntry logEntry : game.getLogs()) {
			System.out.println("logEntry: " + logEntry);
		}

		Location userLoc = getPortalMainStation();

		for (Player player : game.getPlayers()) {
			System.out.println(player.getPassedSeconds() + " " + player.getName() + " " + player.getLastPortal().getName() + " " + player.getLastPortal().getLocation().distanceTo(userLoc));
		}
	}

	private Location getPortalMainStation() {
		Location userLoc = new Location();
		userLoc.setLat(50107356);
		userLoc.setLng(8664123);

		return userLoc;
	}

}
