package tk.icudi;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class IncreaseServerTest {

	@Test
	public void test_location_conversion_with_network_provider() {
		IncreaseServer server = new IncreaseServer();
		assertThat(server.locDoubleToInt(50.1039066), is(501039066));
		assertThat(server.locDoubleToInt(8.6647223), is(86647223));
	}

	@Test
	public void test_location_conversion_with_gps_provider() {
		IncreaseServer server = new IncreaseServer();
		assertThat(server.locDoubleToInt(50.102731236240494), is(501027312));
	}
	
}
