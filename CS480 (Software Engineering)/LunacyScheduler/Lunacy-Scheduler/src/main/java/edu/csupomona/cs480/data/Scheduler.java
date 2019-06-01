package edu.csupomona.cs480.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.Days;
import org.joda.time.LocalDate;

public class Scheduler {
	private Date start;
	private Date end;
	private List<RealUser> users;
	public Scheduler(Date start, Date end, List<RealUser> users) throws Exception {
		if(users.size() < 2) {
			throw new Exception("Not enough users");
		}
		this.start = start;
		this.end = end;
		this.users = users;
	}
	
	public List<DatewithTime> findAvailableSchedules() throws IOException, Exception {
		List<DatewithTime> availableTimes = new ArrayList<>();
		List<Map<Date, List<TimeFrame>>> unavailTimes = new ArrayList<>();
		for(int e = 0; e < users.size(); e++) {
			try {
				unavailTimes.add(users.get(e).getSchedules().returnUnavailables(start, end, getDaysBetween(start, end)));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		Date current = start;
		for(int i = 0; i < getDaysBetween(start,end) + 1; i++) {
			 List<TimeFrame> first = unavailTimes.get(0).get(current);
			 List<TimeFrame> second = unavailTimes.get(1).get(current);
			 List<TimeFrame> availTimes = getAvailableTimes(first, second);
			 if(users.size() == 2) {
				 for(int j = 0; j < availTimes.size(); j++) {
					 availableTimes.add(new DatewithTime(current, availTimes.get(j)));
				 }
				 
			 }
		}
		
		return availableTimes;
	}
	
	public List<TimeFrame> getAvailableTimes(List<TimeFrame> first, List<TimeFrame> second) {
		List<TimeFrame> available = new ArrayList<>();
		for(int i = 0; i < first.size(); i++) {
			TimeFrame unavail1 = first.get(i);
			for(int e = 0; e < second.size(); e++) {
				TimeFrame unavail2 = second.get(e);
				if(unavail1.getStartTime().compareTo(unavail2.getEndTime()) == 1) {
					if(e == second.size() - 1) {
						available.add(new TimeFrame(unavail2.getEndTime(), unavail1.getStartTime()));
					} else if(second.get(e+1).getStartTime().compareTo(unavail1.getStartTime()) == -1) {
						available.add(new TimeFrame(unavail2.getEndTime(), second.get(e+1).getStartTime()));
					} else if(unavail1.getStartTime().compareTo(second.get(e+1).getStartTime()) == -1) {
						available.add(new TimeFrame(unavail2.getEndTime(), unavail1.getStartTime()));
					} else if(unavail1.getStartTime().compareTo(second.get(e+1).getStartTime()) == 0) {
						available.add(new TimeFrame(unavail2.getEndTime(), unavail1.getStartTime()));
					}
				}
//				else if(unavail1.getEndTime().compareTo(unavail2.getStartTime()) == -1) {
//					System.out.println("second'");
//					if(i == first.size() -1) {
//						available.add(new TimeFrame(unavail1.getEndTime(), unavail2.getStartTime()));
//					} else if(first.get(i+1).getStartTime().compareTo(unavail2.getStartTime()) == -1) {
//						available.add(new TimeFrame(unavail1.getEndTime(), first.get(i+1).getStartTime()));
//					} else if(first.get(i+1).getStartTime().compareTo(unavail2.getStartTime()) == 1) {
//						available.add(new TimeFrame(unavail1.getEndTime(), unavail2.getStartTime()));
//					} else if(first.get(i+1).getStartTime().compareTo(unavail2.getStartTime()) == 0) {
//						available.add(new TimeFrame(unavail1.getEndTime(), unavail2.getStartTime()));
//					}
//				}
			}
		}
		return available;
	}
	public void findBestSchedule(Date start, Date end, List<RealUser> users) throws Exception{
		if(users.size() == 1 || users.size() == 0) {
			throw new Exception("Can't have less than or equal to 1 users");
		}
		Date current = start;
		
		for(int i = 0; i < getDaysBetween(start,end); i++) {
//			for(int e = 0; e < users.size(); e++) {
//				users.get(e).getSchedules().returnUnavailability(current);
//				users.get(e+1).getSchedules().returnUnavailability(current);
//			}
			List<TimeFrame> times1 = users.get(0).getSchedules().returnUnavailability(current);
			List<TimeFrame> times2 = users.get(1).getSchedules().returnUnavailability(current);
			for(int e = 0; e < times1.size(); e++) {
				TimeFrame unavailable1 = times1.get(e);
				for(int j = 0; j < times2.size(); j++) {
					TimeFrame unavailable2 = times2.get(j);
				}
			}
		}
		

	}
	private int getDaysBetween(Date start, Date end) {
		LocalDate dt = new LocalDate(start.getYear(), start.getMonth(), start.getDate());
		LocalDate de = new LocalDate(end.getYear(), end.getMonth(), end.getDate());
		return Days.daysBetween(dt, de).getDays();
	}
	private List<List<Schedule>> getSchedules(Date start, Date end, List<RealUser> users) throws Exception {
		List<List<Schedule>>schedules = new ArrayList<>();
		for(int i = 0; i < users.size(); i++) {
			List<Schedule> scheduleforUser = users.get(i).getSchedules().returnSchedule(start, end);
			if(scheduleforUser.isEmpty()) {
				throw new Exception(users.get(i).getName() + " does not have a schedule!");
			}
			schedules.add(scheduleforUser);
		}
		return schedules;
	}
	public static int finddayofWeek(Date date) {
		LocalDate dt = new LocalDate(date.getYear(), date.getMonth(), date.getDate());
		return dt.getDayOfWeek();
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
