package tk.icudi;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {	
		
		DatabaseService database = new DatabaseService();
		String json = database.load();

		resp.setHeader("Access-Control-Allow-Origin", "*"); //cross domain request/CORS
        
		resp.setContentType("application/json");
        
		Game game = new Game();
		game.appendLogsFrom(new LogProviderString(json));
		
//		Location userLoc = getPortalMainStation();
		long time = System.currentTimeMillis();
		
//		List<Player> players = game.getSortetPlayers(userLoc, time);
		List<Player> players = game.getPlayers();
		
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		for (Player player : players) {
			builder.append("\"player\": {").append("\n");
			
			builder.append("\"name\": \"" + URLEncoder.encode(player.getName(), "UTF-8") + "\"\n");
//			builder.append("\"rank\": \"" + player.getRank(userLoc, time) + "\n");
			builder.append("\"time\": " + player.getPassedSeconds(time) + "\n");
			builder.append("\"portal\": \"" + URLEncoder.encode(player.getLastPortal().getName(), "UTF-8") + "\"\n");
			
			builder.append("}").append("\n");

//			System.out.println(player.getRank(userLoc, time) + " " + player.getPassedSeconds(time) + " fhfgh " + player.getName() + " " + player.getLastPortal().getName() + " "
//					+ player.getLastPortal().getLocation().distanceTo(userLoc));
		}
		builder.append("}");
		
		resp.getWriter().println(builder.toString());
    }
	
	private Location getPortalMainStation() {
		Location userLoc = new Location();
		userLoc.setLat(50107356);
		userLoc.setLng(8664123);

		return userLoc;
	}
}
