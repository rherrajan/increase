package tk.icudi;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore("curls ingress. not to be used often")
public class IntegrationTest {

	@Test
	public void test_provide_not() throws Exception {

		LogProviderWeb provider = new LogProviderWeb();

		try {
			PlextParser.streamToString(provider.provideLogs());
			fail("No Exception on input errors");

		} catch (IllegalArgumentException e) {
			// good
		}

	}

	private List<LogEntry> getLogsFromProvider(LogProvider provider) throws IOException {
		PlextParser parser = new PlextParser(provider);
		parser.updateLogs();
		List<LogEntry> logs = parser.extractLogEntries();
		return logs;
	}

	@Test
	public void test_parse() throws Exception {

		List<LogEntry> logs = getLogsFromProvider(new LogProviderCurl());

		assertThat(logs.size(), is(50));
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

		long now = System.currentTimeMillis();
		for (Player player : game.getSortetPlayers(userLoc, now)) {
			System.out.println(player.getMessage(userLoc, now));
		}
	}

	private Location getPortalMainStation() {
		Location userLoc = new Location();
		userLoc.setLat(50107356);
		userLoc.setLng(8664123);

		return userLoc;
	}

}
