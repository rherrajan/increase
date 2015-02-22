package tk.icudi;


public class PlayerServlet extends UnitServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected Object getResult(Game game, Point userLoc, final long time) {
		return game.getNearbyPlayers(userLoc, time);
	}

}
