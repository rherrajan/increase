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

			int portalsOnTime1 = game.getPortals().size();
			for (Entry<Portal, String> entry : game.getPortals().entrySet()) {
				System.out.println(entry.getValue() + ": " + entry.getKey());
			}
			System.out.println(portalsOnTime1 + " portals");

			Thread.sleep(90 * 1000);
			int portalsOnTime2 = game.getPortals().size();
			for (Entry<Portal, String> entry : game.getPortals().entrySet()) {
				System.out.println(entry.getValue() + ": " + entry.getKey());
			}
			System.out.println(portalsOnTime2 + " portals");

			Assert.assertThat(portalsOnTime2, Matchers.greaterThan(portalsOnTime1));

		} finally {
			updater.stop();
		}

	}

}
