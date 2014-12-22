package tk.icudi;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

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
		resp.getWriter().println(",\"firstPortalsOwner\": \"" + URLEncoder.encode(game.getFirstPortalsOwner(), "UTF-8") + "\"");

    }
}
