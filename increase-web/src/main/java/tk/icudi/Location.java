package tk.icudi;

import java.awt.geom.Point2D;

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

	// public static double distFrom(double lat1, double lng1, double lat2,
	// double lng2) {
	// double earthRadius = 6371; // kilometers
	// double dLat = Math.toRadians(lat2 - lat1);
	// double dLng = Math.toRadians(lng2 - lng1);
	// double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
	// Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	// Math.sin(dLng / 2) * Math.sin(dLng / 2);
	// double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	// double dist = earthRadius * c;
	//
	// return dist;
	// }

	public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
		final GeodeticCalculator calc = new GeodeticCalculator();

		final Point2D from = new Point2D.Double(lat1, lng1);
		final Point2D to = new Point2D.Double(lat2, lng2);

		calc.setStartingGeographicPoint(from);
		calc.setDestinationGeographicPoint(to);

		return calc.getOrthodromicDistance();
	}

	// double earthRadius = 3958.75;
	// double dLat = ToRadians(lat2-lat1);
	// double dLng = ToRadians(lng2-lng1);
	// double a = sin(dLat/2) * sin(dLat/2) +
	// cos(ToRadians(lat1)) * cos(ToRadians(lat2)) *
	// sin(dLng/2) * sin(dLng/2);
	// double c = 2 * atan2(sqrt(a), sqrt(1-a));
	// double dist = earthRadius * c;
	// double meterConversion = 1609.00;
	// return dist * meterConversion;

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
