package edu.csupomona.cs480.data;

public class DayTime {
	private int date;
	private TimeFrame time;
	/**
	 * @param date
	 * @param time
	 */
	public DayTime(int date, TimeFrame time) {
		this.date = date;
		this.time = time;
	}

	/**
	 * @return the date
	 */
	public int getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(int date) {
		this.date = date;
	}
	/**
	 * @return the time
	 */
	public TimeFrame getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(TimeFrame time) {
		this.time = time;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + date;
		result = prime * result + ((time == null) ? 0 : time.hashCode());
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
		DayTime other = (DayTime) obj;
		if (date != other.date)
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DayTime [date=" + date + ", time=" + time + "]";
	}

	public int compareTo(DayTime dateTime) {
		// TODO Auto-generated method stub
		if(time.compareTo(dateTime.time) == 1) {
			return 1;
		} else if(time.compareTo(dateTime.time) == -1) {
			return -1;
		} else {
			return 0;
		}
	}
	
}
