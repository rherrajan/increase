package tk.icudi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

	private List<LogEntry> logs;

	public void setLogs(List<LogEntry> logs) {
		this.logs = logs;
	}

	public Map<Portal, String> getPortals() {

		Map<Portal, String> portals = new HashMap<Portal, String>();

		for (LogEntry logEntry : logs) {
			Portal portal = logEntry.getPortal();
			if (portal.getPortalName() != null) {
				portals.put(portal, logEntry.getPlayerName());
			}
		}

		return portals;
	}

}
