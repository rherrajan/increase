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
		this.rawLogs = readInputStream(logInput);
		System.out.println("new raw logs: \n" + rawLogs);
	}

	public List<LogEntry> extractLogEntries() {
		return extractLogEntries(rawLogs);
	}

	String readInputStream(InputStream inputStream) throws IOException {
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

		for (int i = 0; i < success.length(); i++) {
			JSONArray logEntry = success.getJSONArray(i);
			LogEntry log = extractLogEntry(logEntry);
			logs.add(log);
		}

		return logs;
	}

	private LogEntry extractLogEntry(JSONArray logEntry) {
		JSONObject innerLogEntry = logEntry.getJSONObject(2);
		JSONArray markup = innerLogEntry.getJSONObject("plext").getJSONArray("markup");

		int id = logEntry.getInt(1);

		LogEntry log = new LogEntry();
		log.setId(id);

		for (int i = 0; i < markup.length(); i++) {
			JSONArray logPart = markup.getJSONArray(i);
			String logIdentification = logPart.getString(0);
			if (logIdentification.equals("PLAYER")) {
				log.setPlayerName(logPart.getJSONObject(1).getString("plain"));
			} else if (logIdentification.equals("PORTAL")) {
				log.getPortal().setPortalName(logPart.getJSONObject(1).getString("name"));
				log.getPortal().setLatE6(logPart.getJSONObject(1).getInt("latE6"));
				log.getPortal().setLngE6(logPart.getJSONObject(1).getInt("lngE6"));
			} else if (logIdentification.equals("TEXT")) {
				// no important information
			} else if (logIdentification.equals("SENDER")) {
				// message between Players
			} else if (logIdentification.equals("AT_PLAYER")) {
				// message between Players

			} else {
				System.err.println("unnkown log identifikation '" + logIdentification + "'");
			}
		}

		return log;
	}

}
