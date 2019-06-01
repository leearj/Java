package edu.csupomona.cs480.data;

public class TimeFrame1 {
	private Time1 start;
	private Time1 end;
	public TimeFrame1(Time1 startTime, Time1 endTime) {
		this.start = startTime;
		this.end = endTime;
	}
	public Time1 getStartTime() {
		return start;
	}
	public void setStartTime(Time1 startTime) {
		this.start = startTime;
	}
	public Time1 getEndTime() {
		return end;
	}
	public void setEndTime(Time1 endTime) {
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
		TimeFrame1 other = (TimeFrame1) obj;
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
	public boolean contains(TimeFrame1 second) {
		// TODO Auto-generated method stub
		if(second.getStartTime().compareTo(start) >= 0 && second.getStartTime().compareTo(end) <= 0) {
			return true;
		}
		return false;
	}
	public int compareTo(TimeFrame1 o) {
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
//	public Time getHours() {
//		// TODO Auto-generated method stub
//		int hour = end.getHour() - start.getHour();
//		LocalTime test = new LocalTime(hour, end.getMinute());
//		test.minusMinutes(start.getMinute());
//
//		return new Time(test.getHourOfDay(), test.getMinuteOfHour());
//	}
}
