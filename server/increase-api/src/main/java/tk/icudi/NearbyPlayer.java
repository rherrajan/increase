package tk.icudi;

public class NearbyPlayer {

	private int passedSeconds;
	private int rank;
	private String location;
	private int distance;
	private String name;
	private Direction direction;
	private Faction faction;

	public Faction getFaction() {
		return faction;
	}

	public void setPassedSeconds(int passedSeconds) {
		this.passedSeconds = passedSeconds;
	}

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

	public int getPassedSeconds() {
		return passedSeconds;
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public int getDistance() {
		return distance;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public void setFaction(Faction faction) {
		this.faction = faction;
	}

}
