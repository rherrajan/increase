package tk.icudi;

import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;
import java.nio.charset.Charset;

import org.junit.Test;

public class LogProviderStringTest {

	@Test
	public void testProvideLogs() throws Exception {

		// default encoding in google app engine :(
		setDefaultCharset("US-ASCII");

		LogProviderString provider = new LogProviderString("üäö");

		String output = PlextParser.streamToString(provider.provideLogs());

		assertThat(output, startsWith("üäö"));
	}

	private void setDefaultCharset(String string) throws NoSuchFieldException, IllegalAccessException {
		System.setProperty("file.encoding", string);
		Field charset = Charset.class.getDeclaredField("defaultCharset");
		charset.setAccessible(true);
		charset.set(null, null);
		System.out.println("defaultCharset: " + Charset.defaultCharset());
	}

}
