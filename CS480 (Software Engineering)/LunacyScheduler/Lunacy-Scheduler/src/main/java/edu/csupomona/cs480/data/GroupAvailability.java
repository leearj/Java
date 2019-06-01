package edu.csupomona.cs480.data;

import java.util.*;

public class GroupAvailability {
	private List<Schedule> availableTimes;
	private Date start;
	private Date end;

	/**
	 * @param availableTimes
	 */
	public GroupAvailability(List<Schedule> availableTimes, Date start, Date end) {
		this.availableTimes = availableTimes;
		this.start = start;
		this.end = end;
	}
	
	/**
	 * @return the availableTimes
	 */
	public List<Schedule> getAvailableTimes() {
		return availableTimes;
	}
	/**
	 * @param availableTimes the availableTimes to set
	 */
	public void setAvailableTimes(List<Schedule> availableTimes) {
		this.availableTimes = availableTimes;
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
	
	public int size() {
		return availableTimes.size();
	}
}
