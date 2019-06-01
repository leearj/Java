package edu.csupomona.cs480.data;

import java.io.IOException;

public class Schedule1 {
	private TimeFrame[] monday;
	private TimeFrame[] tuesday;
	private TimeFrame[] wednesday;
	private TimeFrame[] thursday;
	private TimeFrame[] friday;
	private TimeFrame[] saturday;
	private TimeFrame[] sunday;

	public Schedule1(TimeFrame[] monday, TimeFrame[] tuesday,
			TimeFrame[] wednesday, TimeFrame[] thursday,
			TimeFrame[] friday, TimeFrame[] saturday,
			TimeFrame[] sunday) {
		this.monday = monday;
		this.tuesday = tuesday;
		this.wednesday = wednesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
		this.sunday = sunday;
	}
	public TimeFrame[] returnTimeFrames(int i) throws IOException {
		if(i == 1) {
			return monday;
		} else if(i == 2) {
			return tuesday;
		}else if(i == 3) {
			return wednesday;
		}else if(i == 4) {
			return thursday;
		}else if(i == 5) {
			return friday;
		}else if(i == 6) {
			return saturday;
		}else if(i == 7) {
			return sunday;
		}else {
			throw new IOException("Not correct input");
		}
	}

	public TimeFrame[] getSchedule(int day) {
		if(day == 0) {
			return sunday;
		} else if(day == 1) {
			return monday;
		} else if(day == 2) {
			return tuesday;
		} else if(day ==3) {
			return wednesday;
		} else if(day == 4) {
			return thursday;
		} else if(day == 5) {
			return friday;
		} else {
			return saturday;
		}
	}
	public TimeFrame[] getMonday() {
		return monday;
	}

	public void setMonday(TimeFrame[] monday) {
		this.monday = monday;
	}

	public TimeFrame[] getTuesday() {
		return tuesday;
	}

	public void setTuesday(TimeFrame[] tuesday) {
		this.tuesday = tuesday;
	}

	public TimeFrame[] getWednesday() {
		return wednesday;
	}

	public void setWednesday(TimeFrame[] wednesday) {
		this.wednesday = wednesday;
	}

	public TimeFrame[] getThursday() {
		return thursday;
	}

	public void setThursday(TimeFrame[] thursday) {
		this.thursday = thursday;
	}

	public TimeFrame[] getFriday() {
		return friday;
	}

	public void setFriday(TimeFrame[] friday) {
		this.friday = friday;
	}

	public TimeFrame[] getSaturday() {
		return saturday;
	}

	public void setSaturday(TimeFrame[] saturday) {
		this.saturday = saturday;
	}

	public TimeFrame[] getSunday() {
		return sunday;
	}

	public void setSunday(TimeFrame[] sunday) {
		this.sunday = sunday;
	}

}
