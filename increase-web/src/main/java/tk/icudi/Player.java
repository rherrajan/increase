package tk.icudi;

import java.util.Date;
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

		long durationMsec = new Date().getTime() - time.getTimeInMillis();

		return (int) (durationMsec / 1000);
	}

	public Portal getLastPortal() {
		return lastPortal;
	}

	public void setLastPortal(Portal portal) {
		this.lastPortal = portal;
	}

}
