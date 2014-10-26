package tk.icudi;

public class Portal {

	private String portalName;
	private int latE6;
	private int lngE6;

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

	@Override
	public String toString() {
		return portalName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + latE6;
		result = prime * result + lngE6;
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
		if (latE6 != other.latE6)
			return false;
		if (lngE6 != other.lngE6)
			return false;
		if (portalName == null) {
			if (other.portalName != null)
				return false;
		} else if (!portalName.equals(other.portalName))
			return false;
		return true;
	}

}
