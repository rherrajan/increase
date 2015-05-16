package tk.icudi;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PlayerAddServlet extends AbstractServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		super.doGet(req, resp);

		Game game = new Game(new GaeDatabase());

		String playername = req.getParameter("player");
		String accuracy = req.getParameter("accuracy");

		game.addPlayer(playername, accuracy);
		
		resp.getWriter().println("{");
		resp.getWriter().println("\"result\": \"success\"");
		resp.getWriter().println("}");
	}

}
