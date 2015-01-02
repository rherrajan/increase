package tk.icudi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

	private List<LogEntry> logs;
	private Map<Location, String> portals = new HashMap<Location, String>();
	private Map<String, Player> players = new HashMap<String, Player>();

	public void appendLogsFrom(LogProvider provider) throws IOException {
		PlextParser parser = new PlextParser(provider);
		parser.updateLogs();
		List<LogEntry> logs = parser.extractLogEntries();
		appendLogs(logs);
	}

	void appendLogs(List<LogEntry> logs) {
		this.logs = logs;
		portals.putAll(createPortalList());
		players.putAll(createPlayerlist());
	}

	Map<String, Player> createPlayerlist() {
		Map<String, Player> players = new HashMap<String, Player>();

		for (LogEntry logEntry : logs) {
			Player player = logEntry.getPlayer();
			if (player.getName() != null) {
				players.put(player.getName(), player);
			}
		}

		return players;
	}

	public Map<Location, String> getPortalOwners() {
		return portals;
	}

	private Map<Location, String> createPortalList() {
		Map<Location, String> portals = new HashMap<Location, String>();

		for (LogEntry logEntry : logs) {
			Location portal = logEntry.getPortal();
			if (portal.getName() != null && logEntry.getPlayerName() != null) {
				portals.put(portal, logEntry.getPlayerName());
			}
		}

		return portals;
	}

	public String getFirstPortalsOwner() {
		return portals.entrySet().iterator().next().getValue();
	}

	public List<Player> getPlayers() {
		return new ArrayList<Player>(players.values());
	}

	public static void main(String[] args) throws Exception {
		LogProvider provider = new LogProviderWeb(new RequestDataRherrajan());
		Game game = new Game();

		GameUpdater updater = new GameUpdater(game, provider);
		updater.start();
	}

	public List<LogEntry> getLogs() {
		return logs;
	}

	List<Player> getSortetPlayers(final Point userLoc, final long time) {
		List<Player> players2 = getPlayers();
		return sortPlayers(players2, userLoc, time);
	}

	List<Player> sortPlayers(final List<Player> players, final Point userLoc, final long now) {

		Comparator<Player> comperator = new Comparator<Player>() {

			@Override
			public int compare(Player my, Player other) {

				Integer myRank = my.getRank(userLoc, now);
				Integer otherRank = other.getRank(userLoc, now);

				return myRank.compareTo(otherRank);
			}
		};
		Collections.sort(players, comperator);

		return players;
	}

}
