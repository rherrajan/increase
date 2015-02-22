package tk.icudi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

//@Ignore("needs a running system")
public class FeederServletSystemTest {

	@Test
	public void testDoGet() throws Exception {
				
		URL plexts = new URL("http://localhost:8080/feeder");
		HttpURLConnection connection = (HttpURLConnection) plexts.openConnection();
		
		String result = streamToString(connection.getInputStream());
				
		Assert.assertThat(result, Matchers.notNullValue());
	}
	
	@Test
	public void testDoPost() throws Exception {
				
		String params = "{}";
		
		URL plexts = new URL("http://localhost:8080/feeder");
		HttpURLConnection connection = (HttpURLConnection) plexts.openConnection();
		connection.setRequestMethod("POST"); 
		connection.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream ());
		wr.writeBytes(params);
		wr.flush();
		wr.close();
		connection.disconnect();
		
		String result = streamToString(connection.getInputStream());
				
		Assert.assertThat(result, Matchers.notNullValue());
	}
	
	private static String streamToString(InputStream inputStream) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder builder = new StringBuilder();
		String strLine;
		while ((strLine = in.readLine()) != null) {
			builder.append(strLine).append("\n");
		}
		return builder.toString();
	}
	
}
