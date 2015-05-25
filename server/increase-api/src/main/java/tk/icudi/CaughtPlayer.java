package tk.icudi;

import java.util.Map;

public class CaughtPlayer implements Identifyable, Player {

	public String playername;
	public int accuracy;
	public String device_id;
	public int distance;
	public long timestamp;
	public long passedSeconds;

	public String makeQueryString() {
		return "player=" + playername + "&accuracy=" + accuracy + "&id=" + device_id + "&distance=" + distance + "&timestamp=" + timestamp;
	}

	public static CaughtPlayer fromParameterMap(Map<String,String[]> parameterMap) {
		CaughtPlayer player = new CaughtPlayer(); 
		player.playername = parameterMap.get("player")[0];
		player.accuracy = Integer.valueOf(parameterMap.get("accuracy")[0]);
		player.device_id = parameterMap.get("id")[0];
		player.distance = Integer.valueOf(parameterMap.get("distance")[0]);
		player.timestamp = Long.valueOf(parameterMap.get("timestamp")[0]);
		return player;
	}

	@Override
	public String getIdentification() {
		return playername;
	}

	@Override
	public Map<String, Object> getIndexes() {
		return null;
	}

	@Override
	public String getName() {
		return playername;
	}

	@Override
	public String toString() {
		return "CaughtPlayer [playername=" + playername + ", accuracy=" + accuracy + ", device_id=" + device_id + "]";
	}

	public long getRank() {
		return distance + passedSeconds + accuracy;
	}

}
