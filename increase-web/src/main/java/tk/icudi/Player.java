package tk.icudi;

import java.util.GregorianCalendar;

public class Player {

	private String name;
	private GregorianCalendar time;
	private Portal lastPortal;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GregorianCalendar getTime() {
		return this.time;
	}

	public void setTime(GregorianCalendar time) {
		this.time = time;
	}

	public int getPassedSeconds() {
		return getPassedSeconds(System.currentTimeMillis());
	}

	public int getPassedSeconds(long now) {
		long durationMsec = now - time.getTimeInMillis();

		return (int) (durationMsec / 1000);
	}

	public Portal getLastPortal() {
		return lastPortal;
	}

	public void setLastPortal(Portal portal) {
		this.lastPortal = portal;
	}

	public int getRank(Location userLoc, long now) {

		int distance = getLastPortal().getLocation().distanceTo(userLoc);
		int passedTime = getPassedSeconds(now);
		int rank = distance + passedTime;

		return rank;
	}

	@Override
	public String toString() {
		return name;
	}

}
