//package tk.icudi;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.hamcrest.Matchers;
//import org.junit.Assert;
//import org.junit.Test;
//
//public class LocationOwnerTest extends AbstractGameTest {
//
//	@Test
//	public void test_detection_of_player_locations() throws Exception {
//
//		Game game = getGame("realdata.json");
//		Map<Location, String> portals = game.getLocationOwners();
//
//		assertEquals(10, portals.size());
//	}
//
//	@Test
//	public void test_order() throws Exception {
//
//		Game game = getGame("doubleAttack.json");
//		Map<Location, String> portals = game.getLocationOwners();
//
//		assertEquals(1, portals.size());
//
//		assertEquals("Attacker2", game.getFirstLocationsOwner());
//	}
//
//	@Test
//	public void test_append() throws Exception {
//		Game game = new Game();
//
//		game.appendLogs(PlextParserTest.parseLogs("doubleAttack.json"));
//		assertEquals(1, game.getLocationOwners().size());
//
//		game.appendLogs(PlextParserTest.parseLogs("anotherPortal.json"));
//		assertEquals(2, game.getLocationOwners().size());
//	}
//
//	@Test
//	public void test_append_realdata_portals() throws Exception {
//		Game game = new Game();
//
//		game.appendLogs(PlextParserTest.parseLogs("realdata.json"));
//		game.appendLogs(PlextParserTest.parseLogs("realdata2.json"));
//		assertEquals(10 + 13, game.getLocationOwners().size());
//	}
//
//	@Test
//	public void test_append_changeOwner() throws Exception {
//		Game game = new Game();
//
//		game.appendLogs(PlextParserTest.parseLogs("attack1.json"));
//		assertEquals("Attacker1", game.getFirstLocationsOwner());
//
//		game.appendLogs(PlextParserTest.parseLogs("attack2.json"));
//		assertEquals("Attacker2", game.getFirstLocationsOwner());
//	}
//
//	@Test
//	public void test_append() throws Exception {
//
//		LogProvider provider = new LogProviderWeb(new RequestDataRherrajan());
//		Game game = new Game();
//
//		GameUpdater updater = new GameUpdater(game, provider);
//
//		try {
//			updater.start();
//
//			int portalsOnTime1 = game.getLocationOwners().size();
//			for (Entry<Location, String> entry : game.getLocationOwners().entrySet()) {
//				System.out.println(entry.getValue() + ": " + entry.getKey());
//			}
//			System.out.println(portalsOnTime1 + " portals");
//
//			Thread.sleep(90 * 1000);
//			int portalsOnTime2 = game.getLocationOwners().size();
//			for (Entry<Location, String> entry : game.getLocationOwners().entrySet()) {
//				System.out.println(entry.getValue() + ": " + entry.getKey());
//			}
//			System.out.println(portalsOnTime2 + " portals");
//
//			Assert.assertThat(portalsOnTime2, Matchers.greaterThan(portalsOnTime1));
//
//		} finally {
//			updater.stop();
//		}
//
//	}
//
// }
