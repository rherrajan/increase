package tk.icudi;

public class Location {

	private int lat;
	private int lng;

	public int getLat() {
		return lat;
	}

	public void setLat(int latE6) {
		this.lat = latE6;
	}

	public int getLng() {
		return lng;
	}

	public void setLng(int lngE6) {
		this.lng = lngE6;
	}

	@Override
	public String toString() {
		return "Location [lat=" + lat + ", lng=" + lng + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + lat;
		result = prime * result + lng;
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
		Location other = (Location) obj;
		if (lat != other.lat)
			return false;
		if (lng != other.lng)
			return false;
		return true;
	}

}
