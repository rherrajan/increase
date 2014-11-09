package tk.icudi;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
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
		Map<Portal, String> portals = game.getPortalOwners();

		assertEquals(10, portals.size());
	}

	@Test
	public void testParseOrder() throws Exception {

		Game game = getGame("doubleAttack.json");
		Map<Portal, String> portals = game.getPortalOwners();

		assertEquals(1, portals.size());

		assertEquals("Attacker2", game.getFirstPortalsOwner());
	}

	@Test
	public void test_append() throws Exception {
		Game game = new Game();

		game.appendLogs(PlextParserTest.parseLogs("doubleAttack.json"));
		assertEquals(1, game.getPortalOwners().size());

		game.appendLogs(PlextParserTest.parseLogs("anotherPortal.json"));
		assertEquals(2, game.getPortalOwners().size());
	}

	@Test
	public void test_append_realdata() throws Exception {
		Game game = new Game();

		game.appendLogs(PlextParserTest.parseLogs("realdata.json"));
		game.appendLogs(PlextParserTest.parseLogs("realdata2.json"));
		assertEquals(10 + 13, game.getPortalOwners().size());
	}

	@Test
	public void test_append_changeOwner() throws Exception {
		Game game = new Game();

		game.appendLogs(PlextParserTest.parseLogs("attack1.json"));
		assertEquals("Attacker1", game.getFirstPortalsOwner());

		game.appendLogs(PlextParserTest.parseLogs("attack2.json"));
		assertEquals("Attacker2", game.getFirstPortalsOwner());
	}

	@Test
	public void test_players_name() throws Exception {
		Game game = new Game();
		game.appendLogs(PlextParserTest.parseLogs("attack1.json"));

		List<Player> players = game.getPlayers();
		assertThat(players.size(), is(1));

		Player firstPlayer = players.get(0);
		assertThat(firstPlayer.getName(), is("Attacker1"));
	}

	@Test
	public void test_players_location() throws Exception {
		Game game = new Game();
		game.appendLogs(PlextParserTest.parseLogs("attack1.json"));

		Location location = game.createPlayerlist().get(0).getLocation();
		assertThat(location.getLat(), is(50113731));
		assertThat(location.getLng(), is(8678958));
	}

	@Test
	public void test_players_time() throws Exception {
		Game game = new Game();
		game.appendLogs(PlextParserTest.parseLogs("attack1.json"));

		GregorianCalendar time = game.createPlayerlist().get(0).getTime();
		assertThat(time.get(Calendar.HOUR_OF_DAY), is(12));
	}

}
