package tk.icudi;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

//@Ignore("curls ingress. not to be used often")
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

	@Test
	public void should_get_plexus_logs_from_credentials() throws Exception {

		List<LogEntry> logs = PlextParser.getLogsFromProvider(new LogProviderWeb(new RequestDataPrivate()));

		assertThat(logs.size(), is(50));
	}

	@Test
	public void should_get_plexus_logs_from_proxy() throws Exception {

		List<LogEntry> logs = PlextParser.getLogsFromProvider(new LogProviderWebProxy(new RequestDataPrivate()));

		assertThat(logs.size(), is(50));
	}

	@Test
	public void test_players() throws Exception {

		LogProvider provider = new LogProviderWeb(new RequestDataPrivate());
		Game game = new Game();
		GameUpdater updater = new GameUpdater(game, provider);
		updater.update();

		for (LogEntry logEntry : game.getLogs()) {
			System.out.println("logEntry: " + logEntry);
		}

		Point userLoc = getPortalMainStation();

		long now = System.currentTimeMillis();
		for (Unit player : game.getSortetUnits(userLoc, now)) {
			System.out.println(player.getMessage(userLoc, now));
		}
	}

	private Point getPortalMainStation() {
		Point userLoc = new Point();
		userLoc.setLat(50107356);
		userLoc.setLng(8664123);

		return userLoc;
	}

}
