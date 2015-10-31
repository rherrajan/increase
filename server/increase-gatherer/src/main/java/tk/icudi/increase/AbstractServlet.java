package tk.icudi.increase;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AbstractServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {	
		modifyResponse(resp);		
    }

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		modifyResponse(resp);	
	}
	
	protected void modifyResponse(HttpServletResponse resp) {
		resp.setHeader("Access-Control-Allow-Origin", "*"); //cross domain request/CORS
		resp.setContentType("application/json; charset=UTF-8");
	}
	
}
