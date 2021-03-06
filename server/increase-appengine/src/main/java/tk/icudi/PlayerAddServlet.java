package tk.icudi;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tk.icudi.increase.CaughtPlayer;

public class PlayerAddServlet extends AbstractServlet {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		super.doGet(req, resp);

		Game game = new Game(new GaeDatabase());

		CaughtPlayer player = CaughtPlayer.fromParameterMap((Map<String, String[]>) req.getParameterMap());
		player.passedSeconds = (System.currentTimeMillis() - player.timestamp) / 1000;

		game.addPlayer(player);

		resp.getWriter().println("{");
		resp.getWriter().println("\"result\": \"success\"");
		resp.getWriter().println("}");
	}

}
