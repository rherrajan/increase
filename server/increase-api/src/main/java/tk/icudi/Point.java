package tk.icudi;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.util.LengthUnit;

public class Point {

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

	public int distanceTo(Point other) {
		return (int) Math.round(distFrom(this.lat / 1000000.0,
				this.lng / 1000000.0, other.lat / 1000000.0,
				other.lng / 1000000.0));
	}

	public static double distFrom(double lat1, double lng1, double lat2,
			double lng2) {
		// final GeodeticCalculator calc = new GeodeticCalculator();
		LatLng point1 = new LatLng(lat1, lng1);
		LatLng point2 = new LatLng(lat2, lng2);

		double distance = com.javadocmd.simplelatlng.LatLngTool.distance(
				point1, point2, LengthUnit.METER);

		return distance;
	}

	public double getAngleFrom(Point userLoc) {
		long longDistance = this.lng - userLoc.lng;
		long latDistance = this.lat - userLoc.lat;
		double hypothenuse = Math.sqrt((longDistance * longDistance) + (latDistance * latDistance));
		
		if(hypothenuse == 0){
			return 0;
		}
		
		double vAngle = (Math.acos(latDistance / hypothenuse) * 360) / (2 * Math.PI);
		if(longDistance < 0){
			vAngle = 360 - vAngle;
		}
		
		return vAngle;
	}

	@Override
	public String toString() {
		return lng + "/" + lat;
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
		Point other = (Point) obj;
		if (lat != other.lat)
			return false;
		if (lng != other.lng)
			return false;
		return true;
	}

	public Direction getDirectionFrom(Point userLoc) {
		return Direction.valueOfAngle(getAngleFrom(userLoc));
	}

}
