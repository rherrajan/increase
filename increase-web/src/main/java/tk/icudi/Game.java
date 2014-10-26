package tk.icudi;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

	private List<LogEntry> logs;
	private Map<Portal, String> portals = new HashMap<Portal, String>();

	public void appendLogsFrom(LogProvider provider) throws IOException {
		PlextParser parser = new PlextParser(provider);
		parser.updateLogs();
		List<LogEntry> logs = parser.extractLogEntries();
		appendLogs(logs);
	}

	void appendLogs(List<LogEntry> logs) {
		this.logs = logs;
		portals.putAll(createPortalList());
	}

	public Map<Portal, String> getPortals() {
		return portals;
	}

	private Map<Portal, String> createPortalList() {
		Map<Portal, String> portals = new HashMap<Portal, String>();

		for (LogEntry logEntry : logs) {
			Portal portal = logEntry.getPortal();
			if (portal.getPortalName() != null && logEntry.getPlayerName() != null) {
				portals.put(portal, logEntry.getPlayerName());
			}
		}

		return portals;
	}

	public String getFirstPortalsOwner() {
		return portals.entrySet().iterator().next().getValue();
	}

}
