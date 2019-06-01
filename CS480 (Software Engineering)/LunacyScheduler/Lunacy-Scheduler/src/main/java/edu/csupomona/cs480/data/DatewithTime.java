package edu.csupomona.cs480.data;

import org.joda.time.LocalDate;

public class DatewithTime {
	private Date date;
	private TimeFrame time;
	/**
	 * @param date
	 * @param time
	 */
	public DatewithTime(Date date, TimeFrame time) {
		this.date = date;
		this.time = time;
	}
	public DatewithTime(LocalDate date, TimeFrame time) {
		this.time = time;
		this.date = new Date(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth());
	}
	
//	public Time findHours() {
//		Time hours = time.getHours();
//		return hours;
//	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
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
		result = prime * result + ((date == null) ? 0 : date.hashCode());
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
		DatewithTime other = (DatewithTime) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
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
		return "DatewithTime [date=" + date + ", time=" + time + "]";
	}

	public int compareTo(DatewithTime datewithTime) {
		// TODO Auto-generated method stub
		if(time.compareTo(datewithTime.time) == 1) {
			return 1;
		} else if(time.compareTo(datewithTime.time) == -1) {
			return -1;
		} else {
			return 0;
		}
	}
	
}
