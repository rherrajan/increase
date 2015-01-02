package tk.icudi;

import java.io.DataOutputStream;
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
		
		String result = PlextParser.streamToString(connection.getInputStream());
				
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
		
		String result = PlextParser.streamToString(connection.getInputStream());
				
		Assert.assertThat(result, Matchers.notNullValue());
	}
	
//	String urlParameters = "param1=a&param2=b&param3=c";
//	String request = "http://example.com/index.php";
//	URL url = new URL(request); 
//	HttpURLConnection connection = (HttpURLConnection) url.openConnection();           
//	connection.setDoOutput(true);
//	connection.setDoInput(true);
//	connection.setInstanceFollowRedirects(false); 
//	connection.setRequestMethod("POST"); 
//	connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
//	connection.setRequestProperty("charset", "utf-8");
//	connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
//	connection.setUseCaches (false);
//
//	DataOutputStream wr = new DataOutputStream(connection.getOutputStream ());
//	wr.writeBytes(urlParameters);
//	wr.flush();
//	wr.close();
//	connection.disconnect();

}
