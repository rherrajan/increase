package tk.icudi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tk.icudi.Database.Schema;

public class Game {

	@Deprecated
	private List<LogEntry> logs;

	private Database database;

	public Game() {
		this(new InMemoryDatabase());
	}

	public Game(Database database) {
		this.database = database;
	}

	public void appendLogsFrom(LogProvider provider) throws IOException {
		PlextParser parser = new PlextParser(provider);
		parser.updateLogs();
		List<LogEntry> logs = parser.extractLogEntries();
		appendLogs(logs);
	}

	void appendLogs(List<LogEntry> logs) {
		this.logs = logs;

		Map<String, Unit> newAgents = createPlayerlist(logs);

		// TODO: Vor dem Speichern die doppelten entfernen
		// TODO: Future für Datenbankaufruf erstellen

		for (Unit logEntry : newAgents.values()) {
			database.save(Schema.player, logEntry);
		}
	}

	Map<String, Unit> createPlayerlist(List<LogEntry> log) {
		Map<String, Unit> players = new HashMap<String, Unit>();

		for (LogEntry logEntry : logs) {
			Unit player = logEntry.getPlayer();
			if (player.getName() != null) {
				players.put(player.getName(), player);
			}
		}

		return players;
	}

	// public Map<Location, String> getLocationOwners() {
	// return portals;
	// }
	//
	// public String getFirstLocationsOwner() {
	// return portals.entrySet().iterator().next().getValue();
	// }

	public List<Unit> getPlayers() {
		List<Unit> players = database.load(Schema.player, Unit.class);

		if (players.size() > 15) {
			database.delete(Schema.player, 10);
		}

		return players;
	}

	public static void main(String[] args) throws Exception {
		LogProvider provider = new LogProviderWeb(new RequestDataPrivate());
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
			player.setTimestamp(unit.getTime().getTimeInMillis());
			player.setName(unit.getName());
			player.setLocation(unit.getLastLocation().getName());
			player.setDistance(unit.getLastLocation().getPoint().distanceTo(userLoc));
			player.setAngle(unit.getLastLocation().getAngleFrom(userLoc));
			player.setFaction(unit.getFaction());

			players.add(player);
		}

		return players;
	}

	public void appendLog(String text) {
		try {
			this.appendLogsFrom(new LogProviderString(text));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteOldPlayers() {
		database.delete(Schema.player, 10);
	}

	public void addPlayer(CaughtPlayer player) {
		database.save(Schema.hackedAgents, player);
	}

	public List<CaughtPlayer> getCaughtPlayers() {
		List<CaughtPlayer> players = database.load(Schema.hackedAgents, CaughtPlayer.class);

		if (players.size() > 15) {
			database.delete(Schema.player, 10);
		}

		return players;
	}

}
