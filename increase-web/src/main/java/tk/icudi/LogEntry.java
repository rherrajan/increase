package tk.icudi;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

public class LogEntry {

	private String playerName;
	private Location portal = new Location();
	private GregorianCalendar time;

	public void setTimeStamp(long timestamp) {
		this.time = new GregorianCalendar(Locale.GERMAN);
		time.setTimeInMillis(timestamp);
	}

	public GregorianCalendar getTime() {
		return time;
	}

	public String getFormattedDate() {
		return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(time.getTime());
	}

	public String getPlayerName() {
		return playerName;
	}

	public Player getPlayer() {
		Player player = new Player();
		player.setName(playerName);
		player.setLastLocation(portal);
		player.setTime(time.getTimeInMillis());
		return player;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Location getPortal() {
		return portal;
	}

	public void setPortal(Location portal) {
		this.portal = portal;
	}

	@Override
	public String toString() {
		return "LogEntry [time=" + getFormattedDate() + ", playerName=" + playerName + ", portal=" + portal + "]";
	}

}
