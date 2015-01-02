package tk.icudi;

import java.net.HttpURLConnection;
import java.net.URL;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore("curls ingress. not to be used often")
public class MainServletSystemTest {

	@Test
	public void testDoGet() throws Exception {
		
		RequestDataRherrajan data = new RequestDataRherrajan();
		
		String params = data.toGetParameter();
		
		URL plexts = new URL("http://localhost:8080/main?" + params);
		HttpURLConnection connection = (HttpURLConnection) plexts.openConnection();

		String result = PlextParser.streamToString(connection.getInputStream());
				
		Assert.assertThat(result, Matchers.notNullValue());
	}

}
