package edu.csupomona.cs480.data;

import org.joda.time.LocalTime;

public class TimeFrame{
	private Time start;
	private Time end;
	public TimeFrame(){}

	public TimeFrame(Time startTime, Time endTime) {
		this.start = startTime;
		this.end = endTime;
	}
	public Time getStartTime() {
		return start;
	}
	public void setStartTime(Time startTime) {
		this.start = startTime;
	}
	public Time getEndTime() {
		return end;
	}
	public void setEndTime(Time endTime) {
		this.end = endTime;
	}
	@Override
	public String toString() {
		return "TimeFrame [startTime=" + start + ", endTime=" + end
				+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result
				+ ((start == null) ? 0 : start.hashCode());
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
		TimeFrame other = (TimeFrame) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}
	public boolean contains(TimeFrame second) {
		// TODO Auto-generated method stub
		if(second.getStartTime().compareTo(start) >= 0 && second.getStartTime().compareTo(end) <= 0) {
			return true;
		}
		return false;
	}
	public int compareTo(TimeFrame o) {
		if(this.total() > o.total()) {
			return 1;
		} else if(this.total() < o.total()) {
			return -1;
		} else {
			return 0;
		}
	}
	public int total() {
		int total = (end.getHour() - start.getHour())*60 + (end.getMinute() - start.getMinute());
		return total;
	}

	public Time getStart() {
		return start;
	}

	public void setStart(Time start) {
		this.start = start;
	}

	public Time getEnd() {
		return end;
	}

	public void setEnd(Time end) {
		this.end = end;
	}


	//	public Time getHours() {
//		// TODO Auto-generated method stub
//		int hour = end.getHour() - start.getHour();
//		LocalTime test = new LocalTime(hour, end.getMinute());
//		test.minusMinutes(start.getMinute());
//
//		return new Time(test.getHourOfDay(), test.getMinuteOfHour());
//	}
}
