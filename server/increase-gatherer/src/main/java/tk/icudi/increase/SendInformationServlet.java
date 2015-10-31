package tk.icudi.increase;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tk.icudi.LogProviderWeb;

public class SendInformationServlet extends GatherInformationServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		modifyResponse(resp);
		
		int statusCode = sendLogs(gatherLogs());
		
		resp.getWriter().println("{");
		resp.getWriter().println("\"result\": \"" + statusCode +"\"");
		resp.getWriter().println("}");
	}

	private int sendLogs(String logs) throws MalformedURLException, ProtocolException, IOException {
		String url = "https://increase-agents.appspot.com/feeder";
		HttpURLConnection connection = LogProviderWeb.createInputStream(url, new HashMap<String, String>(), logs);

		int statusCode = connection.getResponseCode();
		
		if(statusCode != 200){
			System.err.println("statusCode '"+statusCode+"' for " + url);
		}
		
		return statusCode;
	}
	



}
