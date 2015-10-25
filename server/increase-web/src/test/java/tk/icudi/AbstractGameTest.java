package tk.icudi;

import java.io.IOException;

import tk.icudi.increase.Point;

public abstract class AbstractGameTest {

	protected Game getGame(String file) throws IOException {
		Game game = new Game();
		game.appendLogs(PlextParserTest.parseLogs(file));
		return game;
	}

	protected Point getPortalMainStation() {
		Point userLoc = new Point();
		userLoc.setLat(50107356);
		userLoc.setLng(8664123);

		return userLoc;
	}
}
