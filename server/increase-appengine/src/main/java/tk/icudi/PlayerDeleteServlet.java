package tk.icudi;

import tk.icudi.increase.Point;


public class PlayerDeleteServlet extends UnitServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected Object getResult(Game game, Point userLoc, final long time) {
		game.deleteOldPlayers();

		return null;
	}

}
