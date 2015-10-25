package tk.icudi;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;

import tk.icudi.increase.Faction;
import tk.icudi.increase.NearbyPlayer;
import tk.icudi.increase.Point;
import tk.icudi.increase.Unit;

public class PlayerTest extends AbstractGameTest {

	@Test
	public void test_players_name() throws Exception {
		Game game = new Game();
		game.appendLogs(PlextParserTest.parseLogs("attack1.json"));

		List<Unit> players = game.getPlayers();
		assertThat(players.size(), is(1));

		Unit firstPlayer = players.get(0);
		assertThat(firstPlayer.getName(), is("Attacker1"));
	}

	@Test
	public void test_players_faction() throws Exception {
		Game game = new Game();
		game.appendLogs(PlextParserTest.parseLogs("attack1.json"));

		List<Unit> players = game.getPlayers();
		assertThat(players.size(), is(1));

		Unit firstPlayer = players.get(0);
		assertThat(firstPlayer.getFaction(), is(Faction.blue));
	}

	@Test
	public void test_players_location() throws Exception {
		Game game = new Game();
		game.appendLogs(PlextParserTest.parseLogs("attack1.json"));

		Unit firstPlayer = game.getPlayers().iterator().next();
		Point location = firstPlayer.getLastLocation().getPoint();
		assertThat(location.getLat(), is(50113731));
		assertThat(location.getLng(), is(8678958));
	}

	@Test
	public void test_players_time() throws Exception {
		Game game = new Game();
		game.appendLogs(PlextParserTest.parseLogs("attack1.json"));

		Unit firstPlayer = game.getPlayers().iterator().next();
		GregorianCalendar time = firstPlayer.getTime();
		assertThat(time.get(Calendar.HOUR_OF_DAY), is(12));
	}

	@Test
	public void test_players_passed_time() throws Exception {
		Game game = new Game();
		game.appendLogs(PlextParserTest.parseLogs("attack1.json"));

		Unit firstPlayer = game.getPlayers().iterator().next();
		int passedSeconds = firstPlayer.getPassedSeconds();
		assertThat(passedSeconds, notNullValue());
	}

	@Test
	public void test_players_distance() throws Exception {
		Game game = new Game();
		game.appendLogs(PlextParserTest.parseLogs("attack1.json"));

		Unit firstPlayer = game.getPlayers().iterator().next();
		int distance_meter = firstPlayer.getLastLocation().getDistance(getPortalMainStation());
		// assertThat(distance_meter, is(1784));
		// assertThat(distance_meter, is(1276));
		assertThat(distance_meter, is(1273));
	}

	@Test
	public void test_only_the_last() throws Exception {

		// 1225040 anniesa GPS Referenz Punkt Frankfurt Am Main 1994

		Game game = getGame("move.json");

		Point userLoc = getPortalMainStation();

		for (Unit player : game.getPlayers()) {
			System.out.println(player.getPassedSeconds() + " " + player.getName() + " " + player.getLastLocation().getName() + " " + player.getLastLocation().getPoint().distanceTo(userLoc));
		}

		assertThat(game.getPlayers().get(0).getLastLocation().getName(), is("Kinder Museum Frankfurt"));
		assertThat(game.getPlayers().size(), is(1));
	}

	@Test
	public void test_append_realdata_player() throws Exception {
		Game game = new Game();

		Point userLoc = getPortalMainStation();
		long time = 1414335481800L + (1000 * 60 * 15);

		game.appendLogs(PlextParserTest.parseLogs("realdata.json"));
		for (Unit player : game.getSortetUnits(userLoc, time)) {
			System.out.println(player.getMessage(userLoc, time));
		}

		game.appendLogs(PlextParserTest.parseLogs("realdata2.json"));
		for (Unit player : game.getSortetUnits(userLoc, time)) {
			System.out.println(player.getMessage(userLoc, time));
		}

		assertEquals(5 + 8, game.getPlayers().size());
	}

	@Test
	public void test_append_player_multiple() throws Exception {
		Game game = new Game();

		Point userLoc = getPortalMainStation();
		long time = 1414335481800L + (1000 * 60 * 15);

		game.appendLogs(PlextParserTest.parseLogs("realdata.json"));
		for (Unit player : game.getSortetUnits(userLoc, time)) {
			System.out.println(player.getMessage(userLoc, time));
		}
		assertEquals(5, game.getPlayers().size());

		game.appendLogs(PlextParserTest.parseLogs("realdata.json"));
		for (Unit player : game.getSortetUnits(userLoc, time)) {
			System.out.println(player.getMessage(userLoc, time));
		}

		assertEquals(5, game.getPlayers().size());
	}

	@Test
	public void test_player_sort() throws Exception {

		Game game = getGame("realdata.json");
		Point userLoc = getPortalMainStation();

		long time = 1414324082779L + (1000 * 60 * 5);

		List<Unit> players = game.getPlayers();
		List<Unit> sortedPlayers = game.sortPlayers(players, userLoc, time);

		for (Unit player : sortedPlayers) {
			System.out.println(player.getRank(userLoc, time) + " " + player.getPassedSeconds(time) + " fhfgh " + player.getName() + " " + player.getLastLocation().getName() + " "
					+ player.getLastLocation().getPoint().distanceTo(userLoc));
		}

		assertThat(sortedPlayers.get(0).getRank(userLoc, time), is(1500));
		assertThat(sortedPlayers.get(4).getRank(userLoc, time), is(9232));
	}

	@Test
	public void test_nearby_players() throws Exception {

		Game game = getGame("realdata.json");
		Point userLoc = getPortalMainStation();

		long time = 1414324082779L + (1000 * 60 * 5);

		List<NearbyPlayer> players = game.getNearbyPlayers(userLoc, time);

		NearbyPlayer firstPlayer = players.get(0);
		assertThat(firstPlayer.getRank(), is(1500));
		// assertThat(firstPlayer.getPassedSeconds(), is(227));
		assertThat(firstPlayer.getTimestamp(), is(1414324155453L));
		assertThat(firstPlayer.getName(), is("suchef"));
		assertThat(firstPlayer.getLocation(), is("Kinder Museum Frankfurt"));
		assertThat(firstPlayer.getDistance(), is(1273));

	}

}
