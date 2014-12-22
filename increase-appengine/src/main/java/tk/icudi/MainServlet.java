package tk.icudi;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {	
		
		
		DatabaseService database = new DatabaseService();
		String json = database.load();
		

		resp.setHeader("Access-Control-Allow-Origin", "*"); //cross domain request/CORS
        
        resp.setContentType("text/plain");
        resp.getWriter().println(json);

    }

}
