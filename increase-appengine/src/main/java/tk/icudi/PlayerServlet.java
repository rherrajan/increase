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
		
		Location userLoc = getLocationFromRequest(req);
		final long time = System.currentTimeMillis();
		
		final List<Player> players = game.getSortetPlayers(userLoc, time);
		
//		String json = toJson(players, userLoc, time);
		
		Gson gson = new Gson();
		String json = gson.toJson(players);
		
		resp.getWriter().println(json);
    }

//	private String toJson(final List<Player> players, Location userLoc, final long time) {
//		StringBuilder builder = new StringBuilder();
//		builder.append("{");
//		for (Player player : players) {
//			builder.append("\"player\": {").append("\n");
//			
//			builder.append("\"name\": \"" + player.getName() + "\"\n");
//			builder.append("\"rank\": " + player.getRank(userLoc, time) + "\n");
//			builder.append("\"time\": " + player.getPassedSeconds(time) + "\n");
//			builder.append("\"portal\": \"" + player.getLastPortal().getName() + "\"\n");
//			builder.append("\"distance\": " + player.getLastPortal().getLocation().distanceTo(userLoc) + "\n");
//						
//			builder.append("}").append("\n");
//
//		}
//		builder.append("}");
//		
//		String json = builder.toString();
//		return json;
//	}

	private Game createGame() throws IOException {
		DatabaseService database = new DatabaseService();
		List<String> jsons = database.load();
		Game game = new Game();
		
		for (String json : jsons) {
			game.appendLogsFrom(new LogProviderString(json));
		}
		return game;
	}
	
	private Location getLocationFromRequest(HttpServletRequest req) {
		
		String latString = req.getParameter("lat");
		if(latString == null){
			// Fallback
			return getPortalMainStation();
		}
		
		Location userLoc = new Location();
		userLoc.setLat(Integer.valueOf(latString));
		userLoc.setLng(Integer.valueOf(req.getParameter("lng")));

		return userLoc;
	}

	private Location getPortalMainStation() {
		Location userLoc = new Location();
		userLoc.setLat(50107356);
		userLoc.setLng(8664123);

		return userLoc;
	}
}
