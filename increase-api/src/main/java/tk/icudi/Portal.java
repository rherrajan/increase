package tk.icudi;

public class Portal {

	private String name;

	private Location loc = new Location();

	public String getName() {
		return name;
	}

	public void setName(String portalName) {
		this.name = portalName;
	}

	public void setLocation(Location loc) {
		this.loc = loc;
	}

	public Location getLocation() {
		return loc;
	}

	public int getDistance(Location otherLoc) {
		return loc.distanceTo(otherLoc);
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((loc == null) ? 0 : loc.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
