package tk.icudi.increase;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tk.icudi.LogEntry;
import tk.icudi.LogProvider;
import tk.icudi.LogProviderWeb;
import tk.icudi.PlextParser;
import tk.icudi.RequestDataRherrajan;

public class GatherInformationServlet extends AbstractServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		super.doGet(req, resp);

//		Crawler crawler = new Crawler();
//		crawler.updateData();
//		Object result = crawler.getSourcecode();
		
		Object result = getLogsFromProvider(new LogProviderGAEWeb(new RequestDataRherrajan()));

		resp.getWriter().println("{");
		resp.getWriter().println("\"result\": \"" + result +"\"");
		resp.getWriter().println("}");
	}

	public static List<LogEntry> getLogsFromProvider(LogProvider provider) throws IOException {
		PlextParser parser = new PlextParser(provider);
		parser.updateLogs();
		List<LogEntry> logs = parser.extractLogEntries();
		return logs;
	}
	


}
