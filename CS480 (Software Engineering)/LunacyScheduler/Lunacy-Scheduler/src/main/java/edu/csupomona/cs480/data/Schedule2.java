package edu.csupomona.cs480.data;

import org.joda.time.LocalDate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Schedule2 {

	private Map<String, TimeFrame> monday;
	private Map<String, TimeFrame> tuesday;
	private Map<String, TimeFrame> wednesday;
	private Map<String, TimeFrame> thursday;
	private Map<String, TimeFrame> friday;
	private Map<String, TimeFrame> saturday;
	private Map<String, TimeFrame> sunday;
	private long start;
	private long end;

	public Schedule2() {}
	public Schedule2(Map<String, TimeFrame> monday, Map<String, TimeFrame> tuesday, Map<String, TimeFrame> wednesday,
                     Map<String, TimeFrame> thursday, Map<String, TimeFrame> friday, Map<String, TimeFrame> saturday,
                     Map<String, TimeFrame> sunday, long start, long end) {
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




	public Map<String, TimeFrame> getSchedule(int day) {
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

	public Map<String, TimeFrame> getMonday() {
		return monday;
	}

	public void setMonday(Map<String, TimeFrame> monday) {
		this.monday = monday;
	}

	public Map<String, TimeFrame> getTuesday() {
		return tuesday;
	}

	public void setTuesday(Map<String, TimeFrame> tuesday) {
		this.tuesday = tuesday;
	}

	public Map<String, TimeFrame> getWednesday() {
		return wednesday;
	}

	public void setWednesday(Map<String, TimeFrame> wednesday) {
		this.wednesday = wednesday;
	}

	public Map<String, TimeFrame> getThursday() {
		return thursday;
	}

	public void setThursday(Map<String, TimeFrame> thursday) {
		this.thursday = thursday;
	}

	public Map<String, TimeFrame> getFriday() {
		return friday;
	}

	public void setFriday(Map<String, TimeFrame> friday) {
		this.friday = friday;
	}

	public Map<String, TimeFrame> getSaturday() {
		return saturday;
	}

	public void setSaturday(Map<String, TimeFrame> saturday) {
		this.saturday = saturday;
	}

	public Map<String, TimeFrame> getSunday() {
		return sunday;
	}

	public void setSunday(Map<String, TimeFrame> sunday) {
		this.sunday = sunday;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "Schedule2{" +
				"start=" + start +
				", end=" + end +
				'}';
	}

	public boolean contains(LocalDate date) {
		LocalDate st = new LocalDate(start);
		LocalDate en = new LocalDate(end);
		// TODO Auto-generated method stub
		if(st.compareTo(date) <= 0 && en.compareTo(date) >= 0) {
			return true;
		}
		return false;
	}

	public Map<String, TimeFrame> returnScheduleforDayofWeek(LocalDate date) {
		LocalDate dt = new LocalDate(date);
		return returnTimeFrames(dt.getDayOfWeek());
	}

	public Map<String, TimeFrame> returnTimeFrames(int i){
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
		}else{
			return sunday;
		}
	}
}
