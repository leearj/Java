package edu.csupomona.cs480.data;

import java.util.*;

public class AngularScheduler {
	private Schedule1[] schedules;
	public AngularScheduler(Schedule1[] schedules) {
		this.schedules = schedules;
	}
	public String bestAvailableTime() {
		ArrayList<DayTime> availableTimes = findAvailableTimes();
		if(availableTimes.isEmpty()) {
			return "None";
		}
		DayTime best = availableTimes.get(0);
		for(int i = 0; i < availableTimes.size(); i++) {
			if(best.compareTo(availableTimes.get(i)) == -1) {
				best = availableTimes.get(i);
			}
		}
		return best.toString();
	}
	
	public ArrayList<DayTime> findAvailableTimes() {
		ArrayList<DayTime> available = new ArrayList<>();
		
		for(int i = 0; i < 7; i++) {
			ArrayList<TimeFrame> first = new ArrayList<TimeFrame>(Arrays.asList(schedules[0].getSchedule(i)));
			for(int j = 1; j < schedules.length; j++) {
				ArrayList<TimeFrame> second = new ArrayList<TimeFrame>(Arrays.asList(schedules[j].getSchedule(i)));
				first = getAvailableTimes(first, second);
			}
			if(!first.isEmpty()) {
				for(int j = 0; j < first.size(); j++) {
					available.add(new DayTime(0, first.get(j)));
				}
			}
		}
		
		return available;
	}
	

	
	public ArrayList<TimeFrame> getAvailableTimes(List<TimeFrame> a, List<TimeFrame> b) {
		ArrayList<TimeFrame> availableTimes = new ArrayList<>();
		
		for(int i = 0; i < a.size(); i++) {
			TimeFrame first = a.get(i);
			for(int e = 0; e < b.size(); e++) {
				TimeFrame second = b.get(e);
				if(first.contains(second) || second.contains(first)) {
					if(first.getStartTime().equals(second.getStartTime())) {
						if(first.getEndTime().compareTo(second.getEndTime()) ==1) {
							availableTimes.add(new TimeFrame(first.getStartTime(), second.getEndTime()));
						} else if(first.getEndTime().compareTo(second.getEndTime()) ==-1) {
							availableTimes.add(new TimeFrame(first.getStartTime(), first.getEndTime()));
						} else {
							availableTimes.add(new TimeFrame(first.getStartTime(), second.getEndTime()));
						}
					} else if(second.getStartTime().compareTo(first.getStartTime()) ==1){
						if(first.getEndTime().compareTo(second.getEndTime()) ==1) {
							availableTimes.add(new TimeFrame(second.getStartTime(), second.getEndTime()));
						} else if(first.getEndTime().compareTo(second.getEndTime()) ==-1) {
							availableTimes.add(new TimeFrame(second.getStartTime(), first.getEndTime()));
						} else {
							availableTimes.add(new TimeFrame(second.getStartTime(), second.getEndTime()));
						}
					} else {
						if(first.getEndTime().compareTo(second.getEndTime()) ==1) {
							availableTimes.add(new TimeFrame(first.getStartTime(), second.getEndTime()));
						} else if(first.getEndTime().compareTo(second.getEndTime()) ==-1) {
							availableTimes.add(new TimeFrame(first.getStartTime(), first.getEndTime()));
						} else {
							availableTimes.add(new TimeFrame(first.getStartTime(), second.getEndTime()));
						}
					}
				}
			}
		}
		return availableTimes;
	}
	/**
	 * @return the users
	 */
	public Schedule1[] getSchedules() {
		return schedules;
	}
	public void setSchedules(Schedule1[] schedules) {
		this.schedules = schedules;
	}
	
}
