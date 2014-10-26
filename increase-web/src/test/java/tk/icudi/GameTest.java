package tk.icudi;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class GameTest {

	private Game getGame(String file) throws IOException {

		List<LogEntry> logs = PlextParserTest.parseLogs(file);

		Game game = new Game();
		game.setLogs(logs);
		return game;
	}

	@Test
	public void testPortals() throws Exception {

		Game game = getGame("result.json");
		Map<Portal, String> portals = game.getPortals();

		Assert.assertEquals(13, portals.size());
	}

	@Test
	public void testParseOrder() throws Exception {

		// 2: 1414324155453
		// 1: 1414324152066

		Game game = getGame("doubleAttack.json");
		Map<Portal, String> portals = game.getPortals();

		Assert.assertEquals(1, portals.size());

		String owner = portals.entrySet().iterator().next().getValue();

		Assert.assertEquals("Attacker2", owner);
	}

}
