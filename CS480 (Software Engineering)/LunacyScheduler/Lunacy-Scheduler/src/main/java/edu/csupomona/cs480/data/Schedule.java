package edu.csupomona.cs480.data;

import java.io.IOException;
import java.util.List;

import org.joda.time.LocalDate;

public class Schedule {
	private TimeFrame[] schedule;
	private List<TimeFrame> monday;
	private List<TimeFrame> tuesday;
	private List<TimeFrame> wednesday;
	private List<TimeFrame> thursday;
	private List<TimeFrame> friday;
	private List<TimeFrame> saturday;
	private List<TimeFrame> sunday;
	private Date start;
	private Date end;


	public Schedule(List<TimeFrame> monday, List<TimeFrame> tuesday,
			List<TimeFrame> wednesday, List<TimeFrame> thursday,
			List<TimeFrame> friday, List<TimeFrame> saturday,
			List<TimeFrame> sunday, Date start, Date end) {
		this.monday = monday;
		this.tuesday = tuesday;
		this.wednesday = wednesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
		this.sunday = sunday;
		this.start = start;
		this.end = end;
	}
	public List<TimeFrame> returnTimeFrames(int i) throws IOException {
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
	public Schedule(TimeFrame[] schedule) {
		this.schedule = schedule;
	}

	public TimeFrame[] getSchedule() {
		return schedule;
	}
	
	
	public void setSchedule(TimeFrame[] schedule) {
		this.schedule = schedule;
	}
	
	public void setSchedule(int day, TimeFrame schedule) {
		this.schedule[day] = schedule;
	}
	
	public List<TimeFrame> getSchedule(int day) {
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
	public List<TimeFrame> getMonday() {
		return monday;
	}

	public void setMonday(List<TimeFrame> monday) {
		this.monday = monday;
	}

	public List<TimeFrame> getTuesday() {
		return tuesday;
	}

	public void setTuesday(List<TimeFrame> tuesday) {
		this.tuesday = tuesday;
	}

	public List<TimeFrame> getWednesday() {
		return wednesday;
	}

	public void setWednesday(List<TimeFrame> wednesday) {
		this.wednesday = wednesday;
	}

	public List<TimeFrame> getThursday() {
		return thursday;
	}

	public void setThursday(List<TimeFrame> thursday) {
		this.thursday = thursday;
	}

	public List<TimeFrame> getFriday() {
		return friday;
	}

	public void setFriday(List<TimeFrame> friday) {
		this.friday = friday;
	}

	public List<TimeFrame> getSaturday() {
		return saturday;
	}

	public void setSaturday(List<TimeFrame> saturday) {
		this.saturday = saturday;
	}

	public List<TimeFrame> getSunday() {
		return sunday;
	}

	public void setSunday(List<TimeFrame> sunday) {
		this.sunday = sunday;
	}

	/**
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(Date start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public Date getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(Date end) {
		this.end = end;
	}
	public boolean contains(Date date) {
		// TODO Auto-generated method stub
		if(start.compareTo(date) <= 0 && end.compareTo(date) >=0) {
			return true;
		}
		return false;
	}
	public List<TimeFrame> returnUnavail(Date date) throws IOException {
		LocalDate dt = new LocalDate(date.getYear(), date.getMonth(), date.getDate());
		return returnTimeFrames(dt.getDayOfWeek());
	}
	public List<TimeFrame> returnScheduleforDayofWeek(Date date) throws IOException {
		LocalDate dt = new LocalDate(date.getYear(), date.getMonth(), date.getDate());
		return returnTimeFrames(dt.getDayOfWeek());
	}
}
