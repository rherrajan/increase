package tk.icudi;

import org.geotools.referencing.GeodeticCalculator;

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

	public int distanceTo(Location other) {
		return (int) Math.round(distFrom(this.lat / 1000000.0, this.lng / 1000000.0, other.lat / 1000000.0, other.lng / 1000000.0));
	}

	public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
		final GeodeticCalculator calc = new GeodeticCalculator();

		calc.setStartingGeographicPoint(lng1, lat1);
		calc.setDestinationGeographicPoint(lng2, lat2);

		return calc.getOrthodromicDistance();
	}

	@Override
	public String toString() {
		return lat + "/" + lng;
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
