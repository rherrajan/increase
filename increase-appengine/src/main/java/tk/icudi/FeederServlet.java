package tk.icudi;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class FeederServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String postData = extractPostData(req);

		System.out.println(" --- postData: " + postData);
		
		JSONObject jsonObject = new JSONObject(postData);

		System.out.println(" --- jsonObject: " + jsonObject);

		resp.setHeader("Access-Control-Allow-Origin", "*"); // CORS
		// resp.setContentType("text/plain");
		resp.setContentType("application/json");
		// resp.getWriter().println("{ \"result\": \"success\" }");

		resp.getWriter().println("{");
		resp.getWriter().println("\"result\": \"success\"");
		resp.getWriter().println("\"jsonObject\": \"" + jsonObject + "\"");
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
