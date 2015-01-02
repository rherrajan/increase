package tk.icudi;

public class Location {

	private String name;

	private Point point = new Point();

	public String getName() {
		return name;
	}

	public void setName(String portalName) {
		this.name = portalName;
	}

	public void setPoint(Point loc) {
		this.point = loc;
	}

	public Point getPoint() {
		return point;
	}

	public int getDistance(Point otherLoc) {
		return point.distanceTo(otherLoc);
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((point == null) ? 0 : point.hashCode());
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
		Location other = (Location) obj;
		if (point == null) {
			if (other.point != null)
				return false;
		} else if (!point.equals(other.point))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
