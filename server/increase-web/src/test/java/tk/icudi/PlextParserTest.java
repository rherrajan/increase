package tk.icudi;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import tk.icudi.objects.LogProviderFile;

public class PlextParserTest {

	@Test
	public void testUpdateLogsFromFile() throws Exception {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("test.txt");

		assertEquals("Inhalt der Testdatei\n", PlextParser.streamToString(in));
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

		assertEquals(50, logs.size());
		assertEquals("Frankfurter Ratskeller", logs.get(0).getPortal().getName());

		for (LogEntry logEntry : logs) {
			System.out.println("logEntry: " + logEntry);
		}
	}

	@Test
	public void testParseTime() throws Exception {

		List<LogEntry> logs = parseLogs("realdata.json");

		assertEquals("26.10.2014 12:48:02", logs.get(0).getFormattedDate());
	}

}
