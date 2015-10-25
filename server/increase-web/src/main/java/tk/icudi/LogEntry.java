package tk.icudi;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

import tk.icudi.increase.Faction;
import tk.icudi.increase.Location;
import tk.icudi.increase.Unit;

public class LogEntry {

	private String playerName;
	private Location portal = new Location();
	private GregorianCalendar time;
	private Faction faction;

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

	public Unit getPlayer() {
		Unit player = new Unit();
		player.setName(playerName);
		player.setLastLocation(portal);
		player.setTime(time.getTimeInMillis());
		player.setFaction(this.faction);
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

	public Faction getFaction() {
		return faction;
	}

	public void setFaction(Faction faction) {
		this.faction = faction;
	}

}
