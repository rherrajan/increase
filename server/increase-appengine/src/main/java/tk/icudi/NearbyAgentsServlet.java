package tk.icudi;

import java.io.IOException;

import tk.icudi.increase.Point;

public class NearbyAgentsServlet extends UnitServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected Object getResult(Game game, Point userLoc, final long time) {
		
		try {
			game.appendLogsFrom(new LogProviderWebProxy(new RequestDataPrivate(), userLoc));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return game.getNearbyPlayers(userLoc, time);
	}
	

	
}
