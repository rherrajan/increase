package tk.icudi;

import java.util.Map;

public class CaughtPlayer implements Identifyable {

	public String playername;
	public int accuracy;
	public String device_id;

	public String makeQueryString() {
		return "player=" + playername + "&accuracy=" + accuracy + "&id=" + device_id;
	}

	public static CaughtPlayer fromParameterMap(Map<String,String> parameterMap) {
		CaughtPlayer player = new CaughtPlayer(); 
		player.playername = parameterMap.get("player");
		player.accuracy = Integer.valueOf(parameterMap.get("accuracy"));
		return player;
	}

	@Override
	public String getIdentification() {
		return playername;
	}

	@Override
	public Map<String, Object> getIndexes() {
		// TODO Auto-generated method stub
		return null;
	}

}
