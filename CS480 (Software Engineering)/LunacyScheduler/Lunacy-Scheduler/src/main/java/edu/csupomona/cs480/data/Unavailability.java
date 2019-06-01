package edu.csupomona.cs480.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

public class Unavailability {
	private List<Schedule> schedules;
	public Unavailability() {
		schedules = new ArrayList<>();
	}
	public Unavailability(List<Schedule> schedules) {
		this.schedules = schedules;
	}

	public void addSchedule(Schedule schedule) {
		schedules.add(schedule);
	}
	public List<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}
	
	public Map<Date,List<TimeFrame>> returnUnavailables(Date start, Date end, int days) throws IOException, Exception {
		Map<Date,List<TimeFrame>> unavailables = new HashMap<>();
		Date current = start;
		for(int i = 0; i < days + 1; i++) {
			unavailables.put(current, findUnavailforDate(current));
			current = current.nextDate();
		}
		return unavailables;
	}
	private List<TimeFrame> findUnavailforDate(Date date) throws IOException, Exception {
		return findCorrectSchedule(date).returnUnavail(date);
	}
	private Schedule findCorrectSchedule(Date date) throws Exception {
		for(int i = 0; i < schedules.size(); i++) {
			if(schedules.get(i).contains(date)) {
				return schedules.get(i);
			}
		}
		throw new Exception("Schedule does not exist!");
	}
	public List<TimeFrame> returnUnavailability(Date date) throws IOException {
		Schedule correct = null;
		for(int i = 0; i < schedules.size(); i++) {
			if(schedules.get(i).getStart().compareTo(date) <= 0 && schedules.get(i).getEnd().compareTo(date) >= 0) {
				correct = schedules.get(i);
				break;
			}
		}
		LocalDate dt = new LocalDate(date.getYear(), date.getMonth(), date.getDate());
		return correct.returnTimeFrames(dt.getDayOfWeek());
	}
	public List<Schedule> returnSchedule(Date startDate, Date endDate){
		List<Schedule> checkSchedules = new ArrayList<>();
		for(int i = 0; i < schedules.size(); i++) {
			Schedule correct = schedules.get(i);
			Date checkStart = correct.getStart();
			Date checkEnd = correct.getEnd();
			if(checkStart.compareTo(startDate) <= 0 && checkEnd.compareTo(startDate) >= 0 || 
				checkStart.compareTo(startDate) >= 0 && checkEnd.compareTo(endDate) <= 0	) {
				checkSchedules.add(correct);
			} else if(checkStart.compareTo(endDate) <= 0 && checkEnd.compareTo(endDate) >= 0) {
				checkSchedules.add(correct);
				break;
			}
		}
		return checkSchedules;
	}
}
