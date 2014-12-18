package tk.icudi;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {	
		
		
		@SuppressWarnings("unchecked")
		RequestData data = RequestData.fromParmeter((Map<String, String[]>)req.getParameterMap());
		
		
		resp.setHeader("Access-Control-Allow-Origin", "*"); //cross domain request/CORS
        
        resp.setContentType("text/plain");
        resp.getWriter().println("{ \"name\": \"Welt\" }");
        
//        if(data.isEmpty() == false){
//        	
//    		LogProvider provider = new LogProviderWeb(data);
//    		Game game = new Game();
//    		GameUpdater updater = new GameUpdater(game, provider);
//    		updater.update();
//
//    		for (LogEntry logEntry : game.getLogs()) {
//    			System.out.println("logEntry: " + logEntry);
//    		}
//           
//        }

    }

}
