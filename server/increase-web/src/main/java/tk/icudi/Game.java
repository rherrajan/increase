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
	private Map<String, Unit> players = new HashMap<String, Unit>();

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

	Map<String, Unit> createPlayerlist() {
		Map<String, Unit> players = new HashMap<String, Unit>();

		for (LogEntry logEntry : logs) {
			Unit player = logEntry.getPlayer();
			if (player.getName() != null) {
				players.put(player.getName(), player);
			}
		}

		return players;
	}

	public Map<Location, String> getLocationOwners() {
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

	public String getFirstLocationsOwner() {
		return portals.entrySet().iterator().next().getValue();
	}

	public List<Unit> getPlayers() {
		return new ArrayList<Unit>(players.values());
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

	List<Unit> getSortetUnits(final Point userLoc, final long time) {
		List<Unit> players2 = getPlayers();
		return sortPlayers(players2, userLoc, time);
	}

	List<Unit> sortPlayers(final List<Unit> players, final Point userLoc, final long now) {

		Comparator<Unit> comperator = new Comparator<Unit>() {

			@Override
			public int compare(Unit my, Unit other) {

				Integer myRank = my.getRank(userLoc, now);
				Integer otherRank = other.getRank(userLoc, now);

				return myRank.compareTo(otherRank);
			}
		};
		Collections.sort(players, comperator);

		return players;
	}

	public List<NearbyPlayer> getNearbyPlayers(Point userLoc, long time) {

		List<NearbyPlayer> players = new ArrayList<NearbyPlayer>();

		for (Unit unit : getSortetUnits(userLoc, time)) {

			NearbyPlayer player = new NearbyPlayer();
			player.setRank(unit.getRank(userLoc, time));
			player.setPassedSeconds(unit.getPassedSeconds(time));
			player.setName(unit.getName());
			player.setLocation(unit.getLastLocation().getName());
			player.setDistance(unit.getLastLocation().getPoint().distanceTo(userLoc));

			players.add(player);

			// for (Player player : sortedPlayers) {
			// System.out.println(player.getRank(userLoc, time) + " " +
			// unit.getPassedSeconds(time) + " fhfgh " + unit.getName() + " " +
			// unit.getLastLocation().getName() + " "
			// + unit.getLastLocation().getPoint().distanceTo(userLoc));
			// }

		}

		return players;
	}

}
