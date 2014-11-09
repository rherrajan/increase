package tk.icudi;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import tk.icudi.objects.LogProviderFile;

public class PlextParserTest {

	@Test
	public void testUpdateLogsFromFile() throws Exception {

		PlextParser parser = new PlextParser(null);
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("test.txt");

		Assert.assertEquals("Inhalt der Testdatei\n", parser.readInputStream(in));
	}

	static List<LogEntry> parseLogs(String file) throws IOException {
		PlextParser parser = new PlextParser(new LogProviderFile(file));

		parser.updateLogs();
		List<LogEntry> logs = parser.extractLogEntries();
		return logs;
	}

	@Test
	public void testParsePortal() throws Exception {

		List<LogEntry> logs = parseLogs("realdata.json");

		Assert.assertEquals(50, logs.size());
		Assert.assertEquals("Frankfurter Ratskeller", logs.get(0).getPortal().getPortalName());

		for (LogEntry logEntry : logs) {
			System.out.println("logEntry: " + logEntry);
		}
	}

	@Test
	public void testParseTime() throws Exception {

		List<LogEntry> logs = parseLogs("realdata.json");

		Assert.assertEquals("26.10.2014 12:48:02", logs.get(0).getFormattedDate());
	}

}
