package tk.icudi;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class HackedAgentsServlet extends AbstractServlet {

	private static final long serialVersionUID = 1L;

	@Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {	
		super.doGet(req, resp);
		
		@SuppressWarnings("unchecked")
		Map<String,String[]> parameterMap = (Map<String,String[]>)req.getParameterMap();
		writeToResponse(resp, getResult(parameterMap));
    }

	private void writeToResponse(HttpServletResponse resp, Object result) throws IOException {
		if(result != null){
			String json = new Gson().toJson(result);
			resp.getWriter().println(json);
		}
	}

	private Object getResult(Map<String, String[]> parameterMap) {
		Game game = new Game(new GaeDatabase());
		game.getCaughtPlayers();
		return null;
	}

	
}
