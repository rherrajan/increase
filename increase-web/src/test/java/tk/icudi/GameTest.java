package tk.icudi;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Map;

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

		assertEquals(11, portals.size());
	}

	@Test
	public void testParseOrder() throws Exception {

		Game game = getGame("doubleAttack.json");
		Map<Portal, String> portals = game.getPortals();

		assertEquals(1, portals.size());

		assertEquals("Attacker2", game.getFirstPortalsOwner());
	}

	@Test
	public void test_append() throws Exception {
		Game game = new Game();

		game.appendLogs(PlextParserTest.parseLogs("doubleAttack.json"));
		assertEquals(1, game.getPortals().size());

		game.appendLogs(PlextParserTest.parseLogs("anotherPortal.json"));
		assertEquals(2, game.getPortals().size());
	}

	@Test
	public void test_append_realdata() throws Exception {
		Game game = new Game();

		game.appendLogs(PlextParserTest.parseLogs("realdata.json"));
		game.appendLogs(PlextParserTest.parseLogs("realdata2.json"));
		assertEquals(11 + 18, game.getPortals().size());
	}

	@Test
	public void test_append_changeOwner() throws Exception {
		Game game = new Game();

		game.appendLogs(PlextParserTest.parseLogs("attack1.json"));
		assertEquals("Attacker1", game.getFirstPortalsOwner());

		game.appendLogs(PlextParserTest.parseLogs("attack2.json"));
		assertEquals("Attacker2", game.getFirstPortalsOwner());
	}

}
