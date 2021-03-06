package tk.icudi;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tk.icudi.increase.Point;

import com.google.gson.Gson;

public class UnitServlet extends AbstractServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {	
		super.doGet(req, resp);
		
		Game game = new Game(new GaeDatabase());
		
		Point userLoc = getLocationFromRequest(req);
		final long time = System.currentTimeMillis();
		
		final Object result = getResult(game, userLoc, time);
		
		if(result != null){
			String json = new Gson().toJson(result);
			resp.getWriter().println(json);
		}
		
    }

	protected Object getResult(Game game, Point userLoc, final long time) {
		return game.getSortetUnits(userLoc, time);
	}
	

	private Point getLocationFromRequest(HttpServletRequest req) {
		
		String latString = req.getParameter("lat");
		String lngString = req.getParameter("lng");
		
		if(latString == null){
			// Fallback
			return getPortalMainStation();
		}
		
		latString = makeCharacterNumber(latString, 8);
		lngString = makeCharacterNumber(lngString, 7);
		
		Point userLoc = new Point();
		userLoc.setLat(Integer.valueOf(latString));
		userLoc.setLng(Integer.valueOf(lngString));

		System.out.println("User-Location: " + userLoc);
		
		return userLoc;
	}

	private String makeCharacterNumber(String latString, int characters) {
		while(latString.length() > characters){
			latString = latString.substring(0, latString.length()-1);
		}
		while(latString.length() < characters){
			latString = latString + "0";
		}
		return latString;
	}
	
	private Point getPortalMainStation() {
		Point userLoc = new Point();
		userLoc.setLat(50107356);
		userLoc.setLng(8664123);

		return userLoc;
	}

}
