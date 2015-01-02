package tk.icudi;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

public class FeederTest extends AbstractGameTest {

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

		player.setLastLocation(portal);
		player.setTime(time);

		return player;
	}

}
