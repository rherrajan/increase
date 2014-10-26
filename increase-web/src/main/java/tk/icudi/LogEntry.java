package tk.icudi;

public class LogEntry {

	private int id;
	private String playerName;
	private Portal portal = new Portal();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPlayerName() {
		return playerName;
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
		return "LogEntry [id=" + id + ", playerName=" + playerName + ", portal=" + portal + "]";
	}

}
