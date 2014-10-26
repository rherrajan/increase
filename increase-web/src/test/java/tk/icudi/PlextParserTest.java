package tk.icudi;

import java.io.IOException;
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

	private List<LogEntry> parseLogs(String file) throws IOException {
		PlextParser parser = new PlextParser(new LogProviderFile(file));

		parser.updateLogs();
		List<LogEntry> logs = parser.extractLogEntries();
		return logs;
	}

	@Test
	public void testParsePortal() throws Exception {

		List<LogEntry> logs = parseLogs("result.json");

		Assert.assertEquals(50, logs.size());
		Assert.assertEquals("Palais", logs.get(0).getPortal().getPortalName());

		for (LogEntry logEntry : logs) {
			System.out.println("logEntry: " + logEntry);
		}
	}

	@Test
	public void testParseTime() throws Exception {

		List<LogEntry> logs = parseLogs("result.json");

		Assert.assertEquals("26.10.2014 12:49:15", logs.get(0).getFormattedDate());
	}

}
