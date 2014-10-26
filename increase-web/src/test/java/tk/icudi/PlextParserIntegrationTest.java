package tk.icudi;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore("curls ingress. not to be used often")
public class PlextParserIntegrationTest {

	@Test
	public void testParse() throws Exception {

		PlextParser parser = new PlextParser(new LogProviderCurl());

		parser.updateLogs();
		List<LogEntry> logs = parser.extractLogEntries();

		Assert.assertEquals(50, logs.size());

		for (LogEntry logEntry : logs) {
			System.out.println("logEntry: " + logEntry);
		}
	}

}
