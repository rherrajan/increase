package tk.icudi;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import tk.icudi.increase.Location;
import tk.icudi.increase.Unit;

import com.google.gson.Gson;

public class FeederTest extends AbstractGameTest {

	@Test
	public void test_gson2json() throws Exception {

		Unit player = createDummyPlayer();
		String json = new Gson().toJson(player);

		Assert.assertThat(json, Matchers.containsString("playername"));
	}

	@Test
	public void test_json2gson() throws Exception {

		Unit player = createDummyPlayer();
		Gson gson = new Gson();
		String json = gson.toJson(player);

		Unit obj = gson.fromJson(json, Unit.class);
		Assert.assertThat(obj.getName(), Matchers.is("playername"));
	}

	private Unit createDummyPlayer() {
		long time = 1414324082779L + (1000 * 60 * 5);

		Location portal = new Location();
		portal.setName("portalname");
		portal.setPoint(getPortalMainStation());

		Unit player = new Unit();
		player.setName("playername");

		player.setLastLocation(portal);
		player.setTime(time);

		return player;
	}

}
