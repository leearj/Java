package edu.csupomona.cs480.data;

public class Time1 implements Comparable<Time1>{
	private int hour;
	private int minute;
	public Time1(int hour, int minute) {
		this.hour = hour;
		this.minute = minute;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hour;
		result = prime * result + minute;
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Time1 other = (Time1) obj;
		if (hour != other.hour)
			return false;
		if (minute != other.minute)
			return false;
		return true;
	}
	@Override
	public int compareTo(Time1 o) {
		// TODO Auto-generated method stub
		if(hour < o.hour){
			return -1;
		} else if(hour > o.hour) {
			return 1;
		} else if(minute < o.minute){
			return -1;
		} else if(minute > o.hour){
			return 1;
		}
		return 0;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Time [hour=" + hour + ", minute=" + minute + "]";
	}
	
}
