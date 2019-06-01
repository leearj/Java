package edu.csupomona.cs480.data;

import org.joda.time.LocalTime;


public class Time implements Comparable<Time>{
	private long time;
	public Time() {}
	public Time(long time) {
		this.time = time;
	}
	public int getHour() {
		LocalTime a = new LocalTime(time);
		return a.getHourOfDay();
	}
	public int getMinute() {
		LocalTime a = new LocalTime(time);
		return a.getMinuteOfHour();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Time time1 = (Time) o;

		return time == time1.time;
	}

	@Override
	public int hashCode() {
		return (int) (time ^ (time >>> 32));
	}
	@Override
	public int compareTo(Time o) {
		// TODO Auto-generated method stub
		if(this.time > o.time) {
			return 1;
		} else if(this.time < o.time) {
			return -1;
		} else {
			return 0;
		}
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Time [hour=" + getHour() + ", minute=" + getMinute() + "]";
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}
