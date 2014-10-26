package tk.icudi;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {

	private List<LogEntry> logs;

	public void setLogs(List<LogEntry> logs) {
		this.logs = logs;
	}

	public Set<String> getPortals() {

		Set<String> portals = new HashSet<String>();

		for (LogEntry logEntry : logs) {
			if (logEntry.getPortalName() != null) {
				portals.add(logEntry.getPortalName());
			}
		}

		return portals;
	}

}
