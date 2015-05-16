package tk.icudi;


public class PlayerDeleteServlet extends UnitServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected Object getResult(Game game, Point userLoc, final long time) {
		game.deleteAllPlayers();

		return null;
	}

}
