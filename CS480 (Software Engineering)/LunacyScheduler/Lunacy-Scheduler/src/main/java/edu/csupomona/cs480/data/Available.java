package edu.csupomona.cs480.data;

import java.io.IOException;
import java.util.*;

public class Available {
	List<Schedule> availableTimes;

	/**
	 * @param availableTimes
	 */
	public Available(List<Schedule> availableTimes) {
		this.availableTimes = availableTimes;
	}
	public Available(Schedule schedule) {
		availableTimes = new ArrayList<>();
		availableTimes.add(schedule);
	}
	public Map<Date,List<TimeFrame>> returnAvailables(Date start, Date end, int days) throws IOException, Exception {
		Map<Date,List<TimeFrame>> unavailables = new HashMap<>();
		Date current = start;
		for(int i = 0; i < days + 1; i++) {
			unavailables.put(current, findAvailforDate(current));
			current = current.nextDate();
		}
		return unavailables;
	}
	private List<TimeFrame> findAvailforDate(Date date) throws IOException, Exception {
		return findCorrectSchedule(date).returnScheduleforDayofWeek(date);
	}
	private Schedule findCorrectSchedule(Date date) throws Exception {
		for(int i = 0; i < availableTimes.size(); i++) {
			if(availableTimes.get(i).contains(date)) {
				return availableTimes.get(i);
			}
		}
		throw new Exception("Schedule does not exist!");
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
	public Schedule getSchedule(int i) {
		return availableTimes.get(i);
	}
	public void addSchedule(Schedule schedule) {
		availableTimes.add(schedule);
	}
	public void removeSchedule(int i) {
		availableTimes.remove(i);
	}
	
}
