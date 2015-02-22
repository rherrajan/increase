package tk.icudi;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Unit {

	private String name;
	private long time;
	private Location lastLocation;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GregorianCalendar getTime() {
		GregorianCalendar cal = new GregorianCalendar(Locale.GERMAN);
		cal.setTimeInMillis(time);
		return cal;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getPassedSeconds() {
		return getPassedSeconds(System.currentTimeMillis());
	}

	public int getPassedSeconds(long now) {
		long durationMsec = now - time;
		return (int) (durationMsec / 1000);
	}

	public Location getLastLocation() {
		return lastLocation;
	}

	public void setLastLocation(Location portal) {
		this.lastLocation = portal;
	}

	public int getRank(Point userLoc, long now) {

		int distance = getLastLocation().getPoint().distanceTo(userLoc);
		int passedTime = getPassedSeconds(now);

		if (passedTime < 0) {
			System.err.println("now: " + new Date(now));
			System.err.println("log: " + new Date(time));
		}

		int rank = distance + passedTime;

		return rank;
	}

	@Override
	public String toString() {
		return name;
	}

	public String getMessage(Point userLoc, long time) {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getName() + ": ");
		builder.append(" in " + this.getLastLocation().getPoint().distanceTo(userLoc) + "m distance");
		builder.append(" " + Math.round(this.getPassedSeconds(time) / 60.0) + " min ago");
		builder.append(" on '" + this.getLastLocation().getName() + "'");
		builder.append(" (rank " + this.getRank(userLoc, time) + ")");
		return builder.toString();
	}

}
