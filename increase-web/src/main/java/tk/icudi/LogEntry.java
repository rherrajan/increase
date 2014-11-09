package tk.icudi;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

public class LogEntry {

	private String playerName;
	private Portal portal = new Portal();
	private GregorianCalendar date;

	public void setTimeStamp(long timestamp) {
		this.date = new GregorianCalendar(Locale.GERMAN);
		date.setTimeInMillis(timestamp);
	}

	public GregorianCalendar getDate() {
		return date;
	}

	public String getFormattedDate() {
		return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date.getTime());
	}

	public String getPlayerName() {
		return playerName;
	}

	public Player getPlayer() {
		Player player = new Player();
		player.setName(playerName);
		return player;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Portal getPortal() {
		return portal;
	}

	public void setPortal(Portal portal) {
		this.portal = portal;
	}

	@Override
	public String toString() {
		return "LogEntry [time=" + getFormattedDate() + ", playerName=" + playerName + ", portal=" + portal + "]";
	}

}
