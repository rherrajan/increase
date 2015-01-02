package tk.icudi;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class PlayerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {	
		
		resp.setHeader("Access-Control-Allow-Origin", "*"); //cross domain request/CORS
		resp.setContentType("application/json");
        
		Game game = createGame();
		
		Point userLoc = getLocationFromRequest(req);
		final long time = System.currentTimeMillis();
		
		final List<Unit> units = game.getSortetUnits(userLoc, time);
		
		String json = new Gson().toJson(units);
		
		resp.getWriter().println(json);
    }

	private Game createGame() throws IOException {
		DatabaseService database = new DatabaseService();
		List<String> jsons = database.load();
		Game game = new Game();
		
		for (String json : jsons) {
			game.appendLogsFrom(new LogProviderString(json));
		}
		return game;
	}
	
	private Point getLocationFromRequest(HttpServletRequest req) {
		
		String latString = req.getParameter("lat");
		if(latString == null){
			// Fallback
			return getPortalMainStation();
		}
		
		Point userLoc = new Point();
		userLoc.setLat(Integer.valueOf(latString));
		userLoc.setLng(Integer.valueOf(req.getParameter("lng")));

		return userLoc;
	}

	private Point getPortalMainStation() {
		Point userLoc = new Point();
		userLoc.setLat(50107356);
		userLoc.setLng(8664123);

		return userLoc;
	}
}
