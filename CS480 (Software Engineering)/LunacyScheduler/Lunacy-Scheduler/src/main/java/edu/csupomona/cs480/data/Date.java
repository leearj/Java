package edu.csupomona.cs480.data;

import org.joda.time.Days;
import org.joda.time.LocalDate;

public class Date implements Comparable<Date>{
	private int year;
	private int month;
	private int date;
	
	public Date(int year, int month, int date) {
		this.year = year;
		this.month = month;
		this.date = date;
	}

	public int getDaysBetween(Date date) {
		LocalDate dt = new LocalDate(year, month, this.date);
		LocalDate de = new LocalDate(date.getYear(), date.getMonth(), date.getDate());
		return Days.daysBetween(dt, de).getDays();
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public int compareTo(Date o) {
		if(this.year < o.year) {
			return -1;
		} else if(this.year > o.year) {
			return 1;
		} else if(this.month < o.month) {
			return -1;
		} else if(this.month > o.month) {
			return 1;
		} else if(this.date < o.date){
			return -1;
		} else if(this.date > o.date) {
			return 1;
		}
		// TODO Auto-generated method stub
		return 0;
	}
	public Date nextDate() {
		LocalDate dt = new LocalDate(year, month, date);
		dt = dt.plusDays(1);
		Date date = new Date(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
		return date;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + date;
		result = prime * result + month;
		result = prime * result + year;
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
		Date other = (Date) obj;
		if (date != other.date)
			return false;
		if (month != other.month)
			return false;
		if (year != other.year)
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Date [year=" + year + ", month=" + month + ", date=" + date
				+ "]";
	}
	

}
