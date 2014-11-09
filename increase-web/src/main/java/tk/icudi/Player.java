package tk.icudi;

import java.util.Date;
import java.util.GregorianCalendar;

public class Player {

	private String name;
	private Location location;
	private GregorianCalendar time;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public GregorianCalendar getTime() {
		return this.time;
	}

	public void setTime(GregorianCalendar time) {
		this.time = time;
	}

	public int getPassedSeconds() {

		long durationMsec = new Date().getTime() - time.getTimeInMillis();

		return (int) (durationMsec / 1000);
	}

}
