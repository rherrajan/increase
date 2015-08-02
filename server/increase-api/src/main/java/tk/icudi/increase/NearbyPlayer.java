package tk.icudi.increase;

public class NearbyPlayer implements Player {

//	private int passedSeconds;
	private int rank;
	private String location;
	private int distance;
	private String name;
	private double angle;
	private Faction faction;
	private long timestamp;

	public Faction getFaction() {
		return faction;
	}

//	public void setPassedSeconds(int passedSeconds) {
//		this.passedSeconds = passedSeconds;
//	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRank() {
		return rank;
	}

//	public int getPassedSeconds() {
//		return passedSeconds;
//	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public int getDistance() {
		return distance;
	}

	public void setFaction(Faction faction) {
		this.faction = faction;
	}

	public String getHumanReadableTime() {
		return makeHumanReadableTime(this.getPassedSeconds());
	}

	private int getPassedSeconds() {
		return getPassedSeconds(System.currentTimeMillis());
	}

	public int getPassedSeconds(long now) {
		long durationMsec = now - timestamp;
		return (int) (durationMsec / 1000);
	}
	
	public static String makeHumanReadableTime(final int seconds) {
		
		int timetmp = seconds;
		if (timetmp < 2*60) {
			return timetmp + "sec";
		}

		timetmp = timetmp / 60;
		if (timetmp < 2*60) {
			return timetmp + "min";
		}

		timetmp = timetmp / 60;
		if (timetmp < 24 * 2) {
			return timetmp + "hr";
		}

		timetmp = timetmp / 24;
		return timetmp + "days";
	}

	public String getHumanReadableDistance() {

		int distTmp = this.getDistance();
		
		if(distTmp < 1000){
			return distTmp + "m";
		}
		
		distTmp = distTmp/1000;
		return distTmp + "km";
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public Direction getDirection() {
		return Direction.valueOfAngle(angle);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		NearbyPlayer other = (NearbyPlayer) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	

}
