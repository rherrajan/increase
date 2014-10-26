package tk.icudi;

import java.io.IOException;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class GameTest {

	private Game getGame(String file) throws IOException {
		Game game = new Game();
		game.appendLogs(PlextParserTest.parseLogs(file));
		return game;
	}

	@Test
	public void testPortals() throws Exception {

		Game game = getGame("realdata.json");
		Map<Portal, String> portals = game.getPortals();

		Assert.assertEquals(11, portals.size());
	}

	@Test
	public void testParseOrder() throws Exception {

		Game game = getGame("doubleAttack.json");
		Map<Portal, String> portals = game.getPortals();

		Assert.assertEquals(1, portals.size());

		Assert.assertEquals("Attacker2", game.getFirstPortalsOwner());
	}

	@Test
	public void test_append() throws Exception {
		Game game = new Game();

		game.appendLogs(PlextParserTest.parseLogs("doubleAttack.json"));
		Assert.assertEquals(1, game.getPortals().size());

		game.appendLogs(PlextParserTest.parseLogs("anotherPortal.json"));
		Assert.assertEquals(2, game.getPortals().size());
	}

	@Test
	public void test_append_realdata() throws Exception {
		Game game = new Game();

		game.appendLogs(PlextParserTest.parseLogs("realdata.json"));
		game.appendLogs(PlextParserTest.parseLogs("realdata2.json"));
		Assert.assertEquals(11 + 18, game.getPortals().size());
	}

	@Test
	public void test_append_changeOwner() throws Exception {
		Game game = new Game();

		game.appendLogs(PlextParserTest.parseLogs("attack1.json"));
		Assert.assertEquals("Attacker1", game.getFirstPortalsOwner());

		game.appendLogs(PlextParserTest.parseLogs("attack2.json"));
		Assert.assertEquals("Attacker2", game.getFirstPortalsOwner());
	}
}
