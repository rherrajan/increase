package tk.icudi;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

public class GameTest {

	private Game getGame(String file) throws IOException {
		Game game = new Game();
		game.appendLogs(PlextParserTest.parseLogs(file));
		return game;
	}

	@Test
	public void testPortals() throws Exception {

		Game game = getGame("realdata.json");
		Map<Location, String> portals = game.getPortalOwners();

		assertEquals(10, portals.size());
	}

	@Test
	public void testParseOrder() throws Exception {

		Game game = getGame("doubleAttack.json");
		Map<Location, String> portals = game.getPortalOwners();

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
	public void test_append_realdata_portals() throws Exception {
		Game game = new Game();

		game.appendLogs(PlextParserTest.parseLogs("realdata.json"));
		game.appendLogs(PlextParserTest.parseLogs("realdata2.json"));
		assertEquals(10 + 13, game.getPortalOwners().size());
	}

	@Test
	public void test_append_realdata_player() throws Exception {
		Game game = new Game();

		Point userLoc = getPortalMainStation();
		long time = 1414335481800L + (1000 * 60 * 15);

		game.appendLogs(PlextParserTest.parseLogs("realdata.json"));
		for (Player player : game.getSortetPlayers(userLoc, time)) {
			System.out.println(player.getMessage(userLoc, time));
		}

		game.appendLogs(PlextParserTest.parseLogs("realdata2.json"));
		for (Player player : game.getSortetPlayers(userLoc, time)) {
			System.out.println(player.getMessage(userLoc, time));
		}

		assertEquals(5 + 8, game.getPlayers().size());
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

		Player firstPlayer = game.createPlayerlist().entrySet().iterator().next().getValue();
		Point location = firstPlayer.getLastPortal().getPoint();
		assertThat(location.getLat(), is(50113731));
		assertThat(location.getLng(), is(8678958));
	}

	@Test
	public void test_players_time() throws Exception {
		Game game = new Game();
		game.appendLogs(PlextParserTest.parseLogs("attack1.json"));

		Player firstPlayer = game.createPlayerlist().entrySet().iterator().next().getValue();
		GregorianCalendar time = firstPlayer.getTime();
		assertThat(time.get(Calendar.HOUR_OF_DAY), is(12));
	}

	@Test
	public void test_players_passed_time() throws Exception {
		Game game = new Game();
		game.appendLogs(PlextParserTest.parseLogs("attack1.json"));

		Player firstPlayer = game.createPlayerlist().entrySet().iterator().next().getValue();
		int passedSeconds = firstPlayer.getPassedSeconds();
		assertThat(passedSeconds, notNullValue());
	}

	@Test
	public void test_players_distance() throws Exception {
		Game game = new Game();
		game.appendLogs(PlextParserTest.parseLogs("attack1.json"));

		Player firstPlayer = game.createPlayerlist().entrySet().iterator().next().getValue();
		int distance_meter = firstPlayer.getLastPortal().getDistance(getPortalMainStation());
		// assertThat(distance_meter, is(1784));
		// assertThat(distance_meter, is(1276));
		assertThat(distance_meter, is(1273));
	}

	@Test
	public void test_only_the_last() throws Exception {

		// 1225040 anniesa GPS Referenz Punkt Frankfurt Am Main 1994

		Game game = getGame("move.json");

		Point userLoc = getPortalMainStation();

		for (Player player : game.getPlayers()) {
			System.out.println(player.getPassedSeconds() + " " + player.getName() + " " + player.getLastPortal().getName() + " " + player.getLastPortal().getPoint().distanceTo(userLoc));
		}

		assertThat(game.getPlayers().get(0).getLastPortal().getName(), is("Kinder Museum Frankfurt"));
		assertThat(game.getPlayers().size(), is(1));
	}

	@Test
	public void test_player_sort() throws Exception {

		Game game = getGame("realdata.json");
		Point userLoc = getPortalMainStation();

		long time = 1414324082779L + (1000 * 60 * 5);

		List<Player> players = game.getPlayers();
		List<Player> sortedPlayers = game.sortPlayers(players, userLoc, time);

		for (Player player : sortedPlayers) {
			System.out.println(player.getRank(userLoc, time) + " " + player.getPassedSeconds(time) + " fhfgh " + player.getName() + " " + player.getLastPortal().getName() + " "
					+ player.getLastPortal().getPoint().distanceTo(userLoc));
		}
	}

	@Test
	public void test_gson2json() throws Exception {

		Player player = createDummyPlayer();

		Gson gson = new Gson();

		String json = gson.toJson(player);

		Assert.assertThat(json, Matchers.containsString("playername"));
	}

	@Test
	public void test_json2gson() throws Exception {

		Player player = createDummyPlayer();
		Gson gson = new Gson();
		String json = gson.toJson(player);

		Player obj = gson.fromJson(json, Player.class);
		Assert.assertThat(obj.getName(), Matchers.is("playername"));
	}

	private Player createDummyPlayer() {
		long time = 1414324082779L + (1000 * 60 * 5);

		Location portal = new Location();
		portal.setName("portalname");
		portal.setPoint(getPortalMainStation());

		Player player = new Player();
		player.setName("playername");

		player.setLastPortal(portal);
		player.setTime(time);

		return player;
	}

	private Point getPortalMainStation() {
		Point userLoc = new Point();
		userLoc.setLat(50107356);
		userLoc.setLng(8664123);

		return userLoc;
	}

}
