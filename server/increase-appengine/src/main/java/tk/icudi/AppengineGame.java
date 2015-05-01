package tk.icudi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AppengineGame {

	private static AppengineGame instance;
	private Game game;

	private AppengineGame() throws IOException {
		this.game = createGame();
	}

	public static synchronized AppengineGame getInstance() throws IOException {
		
		if(instance == null){
			instance = new AppengineGame();
		}
		return instance;
	}

	public Game getGame() {
		return game;
	}

	private Game createGame() throws IOException {

		DatabaseService database = new DatabaseService();
		List<String> jsons_raw = database.load();

		List<LogEntry> logs = sort(jsons_raw);

		System.out.println("loading " + logs.size() + " log eintries");

		Game game = new Game();
		game.appendLogs(logs);

		return game;
	}

	private List<LogEntry> sort(List<String> jsons_raw) throws IOException {

		List<LogEntry> result = new ArrayList<LogEntry>();

		for (String json : jsons_raw) {
			PlextParser parser = new PlextParser(new LogProviderString(json));
			parser.updateLogs();
			List<LogEntry> logs = parser.extractLogEntries();

			for (LogEntry logEntry : logs) {
				result.add(logEntry);
			}
		}

		Comparator<? super LogEntry> comperator = new Comparator<LogEntry>() {
			@Override
			public int compare(LogEntry o1, LogEntry o2) {
				return Long.compare(o1.getTime().getTimeInMillis(), o2.getTime().getTimeInMillis());
			}
		};
		Collections.sort(result, comperator);

		return result;
	}
}
