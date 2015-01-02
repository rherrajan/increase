package tk.icudi;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class UnitServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {	
		
		resp.setHeader("Access-Control-Allow-Origin", "*"); //cross domain request/CORS
		resp.setContentType("application/json");
        
		Game game = createGame();
		
		Point userLoc = getLocationFromRequest(req);
		final long time = System.currentTimeMillis();
		
		final Object result = getResult(game, userLoc, time);
		
		String json = new Gson().toJson(result);
		
		resp.getWriter().println(json);
    }

	protected Object getResult(Game game, Point userLoc, final long time) {
		return game.getSortetUnits(userLoc, time);
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
	
//	private Point getPortalRedGate() {
//		Point userLoc = new Point();
//		userLoc.setLat(50080677);
//		userLoc.setLng(8630383);
//
//		return userLoc;
//	}

}
