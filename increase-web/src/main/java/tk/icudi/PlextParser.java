package tk.icudi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PlextParser {

	private LogProvider provider;
	private String rawLogs;

	public PlextParser(LogProvider provider) {
		this.provider = provider;
	}

	public void updateLogs() throws IOException {
		InputStream logInput = provider.provideLogs();
		this.rawLogs = streamToString(logInput);
		if (rawLogs.contains("\n") == false) {
			System.out.println("new raw logs: \n" + rawLogs);
		}
	}

	public List<LogEntry> extractLogEntries() {
		return extractLogEntries(rawLogs);
	}

	public static String streamToString(InputStream inputStream) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder builder = new StringBuilder();
		String strLine;
		while ((strLine = in.readLine()) != null) {
			builder.append(strLine).append("\n");
		}
		return builder.toString();
	}

	private List<LogEntry> extractLogEntries(String result) {

		JSONObject obj = new JSONObject(result);
		JSONArray success = obj.getJSONArray("success");

		System.out.println("reading " + success.length() + " logentries");

		List<LogEntry> logs = new ArrayList<LogEntry>();

		for (int i = success.length() - 1; i >= 0; i--) {
			// reverse order, because we get the newest data on top of the json
			JSONArray logEntry = success.getJSONArray(i);
			LogEntry log = extractLogEntry(logEntry);
			logs.add(log);
		}

		return logs;
	}

	private LogEntry extractLogEntry(JSONArray logEntry) {
		JSONObject innerLogEntry = logEntry.getJSONObject(2);
		JSONArray markup = innerLogEntry.getJSONObject("plext").getJSONArray("markup");

		long id = logEntry.getLong(1);

		LogEntry log = new LogEntry();
		log.setTimeStamp(id);

		for (int i = 0; i < markup.length(); i++) {
			JSONArray logPart = markup.getJSONArray(i);
			String logIdentification = logPart.getString(0);
			if (logIdentification.equals("PLAYER")) {
				log.setPlayerName(logPart.getJSONObject(1).getString("plain"));
			} else if (logIdentification.equals("PORTAL")) {
				if (log.getPortal().getName() == null) {
					log.getPortal().setName(logPart.getJSONObject(1).getString("name"));
					Location loc = new Location();
					loc.setLat(logPart.getJSONObject(1).getInt("latE6"));
					loc.setLng(logPart.getJSONObject(1).getInt("lngE6"));
					log.getPortal().setLocation(loc);
				} else {
					// with 2 portals the second is not important
				}
			} else if (logIdentification.equals("TEXT")) {
				// no important information
			} else if (logIdentification.equals("SENDER")) {
				// message between Players
			} else if (logIdentification.equals("AT_PLAYER")) {
				// message between Players
			} else if (logIdentification.equals("SECURE")) {
				// message between Players

			} else {
				System.err.println("unnkown log identifikation '" + logIdentification + "'");
			}
		}

		return log;
	}

}
