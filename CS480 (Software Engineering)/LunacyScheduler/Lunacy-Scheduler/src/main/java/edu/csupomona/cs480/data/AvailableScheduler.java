package edu.csupomona.cs480.data;

import java.util.*;

public class AvailableScheduler {
	private Date start;
	private Date end;
	private List<RealUser> users;
	/**
	 * @param start
	 * @param end
	 * @param users
	 */
	public AvailableScheduler(Date start, Date end, List<RealUser> users) {
		this.start = start;
		this.end = end;
		this.users = users;
	}
	
	public DatewithTime bestAvailableTime() {
		List<DatewithTime> availableTimes = findAvailableTimes();
		DatewithTime best = availableTimes.get(0);
		for(int i = 0; i < availableTimes.size(); i++) {
			if(best.compareTo(availableTimes.get(i)) == -1) {
				best = availableTimes.get(i);
			}
		}
		return best;
	}
	
	public List<DatewithTime> findAvailableTimes() {
		List<DatewithTime> available = new ArrayList<>();
		
		List<Map<Date, List<TimeFrame>>> availableTimes = new ArrayList<>();
		
		int totalDays = start.getDaysBetween(end);
		for(int e = 0; e < users.size(); e++) {
			try {
				availableTimes.add(users.get(e).getAvailSchedules().returnAvailables(start, end, totalDays));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		Date current = start;
		for(int i = 0; i < totalDays + 1; i++) {
			List<TimeFrame> first = availableTimes.get(0).get(current);
			for(int e = 0; e < users.size() - 1; e++) {
				 List<TimeFrame> second = availableTimes.get(e+1).get(current);
				 first = getAvailableTimes(first, second);	 
			}
			for(int j = 0; j < first.size(); j++) {
				available.add(new DatewithTime(current, first.get(j)));
			}
			current = current.nextDate();
		}
		
		return available;
	}
	public List<TimeFrame> getAvailableTimes(List<TimeFrame> a, List<TimeFrame> b) {
		List<TimeFrame> availableTimes = new ArrayList<>();
		
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
	/**
	 * @return the users
	 */
	public List<RealUser> getUsers() {
		return users;
	}
	/**
	 * @param users the users to set
	 */
	public void setUsers(List<RealUser> users) {
		this.users = users;
	}
	
}
