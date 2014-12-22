package tk.icudi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

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
		resp.setHeader("Access-Control-Allow-Origin", "*"); // CORS
		resp.setContentType("application/json");

		resp.getWriter().println("{");
		resp.getWriter().println("\"result\": \"success\"");
		
		if(json != null && json.isEmpty() == false){
			
			json = URLDecoder.decode(json, "UTF-8");
			System.out.println(" --- postData: " + json);
						
			DatabaseService database = new DatabaseService();
			database.save(json);
			
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
