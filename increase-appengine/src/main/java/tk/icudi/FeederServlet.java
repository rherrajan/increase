package tk.icudi;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FeederServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String postData = extractPostData(req);

		
		resp.setHeader("Access-Control-Allow-Origin", "*"); // CORS
		resp.setContentType("application/json");

		resp.getWriter().println("{");
		resp.getWriter().println("\"result\": \"success\"");
		
		if(postData != null && postData.isEmpty() == false){
			
			postData = URLDecoder.decode(postData, "UTF-8");
			System.out.println(" --- postData: " + postData.substring(0, 100) + "...");
					
			Game game = new Game();
			game.appendLogsFrom(new LogProviderString(postData));
			
			resp.getWriter().println(",\"firstPortalsOwner\": \"" + URLEncoder.encode(game.getFirstPortalsOwner(), "UTF-8") + "\"");
		}

		resp.getWriter().println("}");

	}

	private String extractPostData(HttpServletRequest req) throws IOException {
		StringBuilder jb = new StringBuilder();
		String line = null;

		BufferedReader reader = req.getReader();
		while ((line = reader.readLine()) != null) {
			jb.append(line);
		}

		return jb.toString();
	}
}
