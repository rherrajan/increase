package tk.icudi;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class GameTest {

	private Game getGame() throws IOException {

		PlextParser parser = new PlextParser(new LogProviderFile("result.json"));

		parser.updateLogs();
		List<LogEntry> logs = parser.extractLogEntries();

		Game game = new Game();
		game.setLogs(logs);
		return game;
	}

	@Test
	public void testPortals() throws Exception {

		Game game = getGame();

		Set<String> portals = game.getPortals();

		Assert.assertEquals(13, portals.size());

		System.out.println("portals: " + portals);
	}

}
