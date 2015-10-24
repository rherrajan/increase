package tk.icudi.increase;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tk.icudi.LogProvider;
import tk.icudi.LogProviderWeb;
import tk.icudi.LogProviderWebProxy;
import tk.icudi.PlextParser;
import tk.icudi.RequestDataRherrajan;

public class SendInformationServlet extends AbstractServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		super.doGet(req, resp);

//		Crawler crawler = new Crawler();
//		crawler.updateData();
//		Object result = crawler.getSourcecode();
		
		String logs = getLogStringFromProvider(new LogProviderWebProxy(new RequestDataRherrajan()));

		int statusCode = sendLogs(logs);
		
		resp.getWriter().println("{");
		resp.getWriter().println("\"result\": \"" + statusCode +"\"");
		resp.getWriter().println("}");
	}

	private int sendLogs(String logs) throws MalformedURLException, ProtocolException, IOException {
		String url = "https://sylvan-dragon-772.appspot.com/feeder";
		HttpURLConnection connection = LogProviderWeb.createInputStream(url, new HashMap<String, String>(), logs);

		return connection.getResponseCode();
	}

	public static String getLogStringFromProvider(LogProvider provider) throws IOException {
		PlextParser parser = new PlextParser(provider);
		parser.updateLogs();
		return parser.getRawLogs();
	}

	


}
