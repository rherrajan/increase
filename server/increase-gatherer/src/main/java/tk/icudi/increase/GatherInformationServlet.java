package tk.icudi.increase;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GatherInformationServlet extends AbstractServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		super.doGet(req, resp);

		Crawler crawler = new Crawler();
		crawler.updateData();
		
		resp.getWriter().println("{");
		resp.getWriter().println("\"result\": \"" + crawler.getSourcecode() +"\"");
		resp.getWriter().println("}");
	}

}
