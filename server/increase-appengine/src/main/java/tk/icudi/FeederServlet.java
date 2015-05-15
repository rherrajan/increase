package tk.icudi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FeederServlet extends HttpServlet {

	// https://code.google.com/p/jsonengine/
	
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		writeResponse(resp, "{test:true}");
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String postData = extractPostData(req);
		writeResponse(resp, postData);
	}

	private void writeResponse(HttpServletResponse resp, String json)
			throws IOException, UnsupportedEncodingException {
		
//		Game game = AppengineGame.getInstance().getGame();	
		

		Game game = new Game(new GaeDatabase());
		
		if(json != null && json.isEmpty() == false){
			game.appendLog(json);
		}
		
		resp.setHeader("Access-Control-Allow-Origin", "*"); // CORS
		resp.setContentType("application/json; charset=UTF-8");
		
		resp.getWriter().println("{");
		resp.getWriter().println("\"result\": \"success\"");
		resp.getWriter().println("}");
	}

	private String extractPostData(HttpServletRequest req) throws IOException {
		StringBuilder jb = new StringBuilder();
		String line = null;

		BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream(), "UTF-8"));
		while ((line = reader.readLine()) != null) {
			jb.append(line);
		}

		return jb.toString();
	}
}
