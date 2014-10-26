package tk.icudi;

import java.io.InputStream;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class PlextParserTest {

	@Test
	public void testUpdateLogsFromFile() throws Exception {

		PlextParser parser = new PlextParser(null);
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("test.txt");

		Assert.assertEquals("Inhalt der Testdatei\n", parser.readInputStream(in));
	}

	@Test
	public void testParse() throws Exception {

		PlextParser parser = new PlextParser(new LogProviderFile("result.json"));

		parser.updateLogs();
		List<LogEntry> logs = parser.extractLogEntries();

		Assert.assertEquals(50, logs.size());
		Assert.assertEquals("Palais", logs.get(0).getPortalName());

		for (LogEntry logEntry : logs) {
			System.out.println("logEntry: " + logEntry);
		}
	}

}
