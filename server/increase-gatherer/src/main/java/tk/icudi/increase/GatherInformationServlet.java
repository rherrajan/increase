package tk.icudi.increase;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tk.icudi.LogProvider;
import tk.icudi.LogProviderWebProxy;
import tk.icudi.PlextParser;
import tk.icudi.RequestDataRherrajan;

public class GatherInformationServlet extends AbstractServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		super.doGet(req, resp);


		String logs = getLogStringFromProvider(new LogProviderWebProxy(new RequestDataRherrajan()));		
		resp.getWriter().println(logs);
	}

	public static String getLogStringFromProvider(LogProvider provider) throws IOException {
		PlextParser parser = new PlextParser(provider);
		parser.updateLogs();
		return parser.getRawLogs();
	}

}
