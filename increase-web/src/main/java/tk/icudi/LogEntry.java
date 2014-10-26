package tk.icudi;
public class LogEntry {

	private String playerName;
	private String portalName;
	private int latE6;
	private int lngE6;
	private int id;
	
	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPortalName() {
		return portalName;
	}

	public void setPortalName(String portalName) {
		this.portalName = portalName;
	}

	public int getLatE6() {
		return latE6;
	}

	public void setLatE6(int latE6) {
		this.latE6 = latE6;
	}

	public int getLngE6() {
		return lngE6;
	}

	public void setLngE6(int lngE6) {
		this.lngE6 = lngE6;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "LogEntry [playerName=" + playerName + ", portalName=" + portalName + ", latE6=" + latE6 + ", lngE6=" + lngE6 + ", id=" + id + "]";
	}
	
	

}
