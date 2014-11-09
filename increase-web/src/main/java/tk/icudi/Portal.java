package tk.icudi;

public class Portal {

	private String portalName;

	private Location loc = new Location();

	public String getPortalName() {
		return portalName;
	}

	public void setPortalName(String portalName) {
		this.portalName = portalName;
	}

	public void setLocation(Location loc) {
		this.loc = loc;
	}

	public Location getLocation() {
		return loc;
	}

	@Override
	public String toString() {
		return portalName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((loc == null) ? 0 : loc.hashCode());
		result = prime * result + ((portalName == null) ? 0 : portalName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Portal other = (Portal) obj;
		if (loc == null) {
			if (other.loc != null)
				return false;
		} else if (!loc.equals(other.loc))
			return false;
		if (portalName == null) {
			if (other.portalName != null)
				return false;
		} else if (!portalName.equals(other.portalName))
			return false;
		return true;
	}
}
