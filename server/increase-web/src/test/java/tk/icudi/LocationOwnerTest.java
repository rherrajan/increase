package tk.icudi;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

public class LocationOwnerTest extends AbstractGameTest {

	@Test
	public void test_detection_of_player_locations() throws Exception {

		Game game = getGame("realdata.json");
		Map<Location, String> portals = game.getLocationOwners();

		assertEquals(10, portals.size());
	}

	@Test
	public void test_order() throws Exception {

		Game game = getGame("doubleAttack.json");
		Map<Location, String> portals = game.getLocationOwners();

		assertEquals(1, portals.size());

		assertEquals("Attacker2", game.getFirstLocationsOwner());
	}

	@Test
	public void test_append() throws Exception {
		Game game = new Game();

		game.appendLogs(PlextParserTest.parseLogs("doubleAttack.json"));
		assertEquals(1, game.getLocationOwners().size());

		game.appendLogs(PlextParserTest.parseLogs("anotherPortal.json"));
		assertEquals(2, game.getLocationOwners().size());
	}

	@Test
	public void test_append_realdata_portals() throws Exception {
		Game game = new Game();

		game.appendLogs(PlextParserTest.parseLogs("realdata.json"));
		game.appendLogs(PlextParserTest.parseLogs("realdata2.json"));
		assertEquals(10 + 13, game.getLocationOwners().size());
	}

	@Test
	public void test_append_changeOwner() throws Exception {
		Game game = new Game();

		game.appendLogs(PlextParserTest.parseLogs("attack1.json"));
		assertEquals("Attacker1", game.getFirstLocationsOwner());

		game.appendLogs(PlextParserTest.parseLogs("attack2.json"));
		assertEquals("Attacker2", game.getFirstLocationsOwner());
	}

}
