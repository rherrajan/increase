package tk.icudi.increase;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tk.icudi.LogProvider;
import tk.icudi.LogProviderWebProxy;
import tk.icudi.PlextParser;
import tk.icudi.RequestDataPrivate;

public class GatherInformationServlet extends AbstractServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		super.doGet(req, resp);

		String logs = gatherLogs();		
		resp.getWriter().println(logs);
	}

	protected String gatherLogs() throws IOException {
		return getLogStringFromProvider(new LogProviderWebProxy(new RequestDataPrivate(), getPortalMainStation()));
	}

	private String getLogStringFromProvider(LogProvider provider) throws IOException {
		PlextParser parser = new PlextParser(provider);
		parser.updateLogs();
		return parser.getRawLogs();
	}
	
	private Point getPortalMainStation() {
		Point userLoc = new Point();
		userLoc.setLat(50107356);
		userLoc.setLng(8664123);

		return userLoc;
	}

}
