package tk.icudi;

public class CaughtPlayer {

	public String playername;
	public int accuracy;
	public String device_id;

	public String makeQueryString() {
		return "player=" + playername + "&accuracy=" + accuracy + "&id=" + device_id;
	}

}
