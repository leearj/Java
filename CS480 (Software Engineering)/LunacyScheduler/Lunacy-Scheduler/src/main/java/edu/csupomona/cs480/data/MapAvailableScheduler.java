package edu.csupomona.cs480.data;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapAvailableScheduler {
	private long start;
	private long end;
	private List<MapAvailable> schedules;

	public MapAvailableScheduler() {}

	public MapAvailableScheduler(long start, long end, List<MapAvailable> schedules) {
		this.start = start;
		this.end = end;
		this.schedules = schedules;
	}

	public DatewithTime bestAvailableTime() {
		List<DatewithTime> availableTimes = findAvailableTimes();
		DatewithTime best = availableTimes.get(0);
		for(int i = 0; i < availableTimes.size(); i++) {
			System.out.println("Counter: "+i);
			if(best.compareTo(availableTimes.get(i)) == -1) {
				best = availableTimes.get(i);
			}
		}
		return best;
	}

	
	public List<DatewithTime> findAvailableTimes() {
		List<DatewithTime> available = new ArrayList<>();
		
		List<Map<LocalDate, Map<String, TimeFrame>>> availableTimes = new ArrayList<>();
		
		int totalDays = getDaysBetween();
		for(int e = 0; e < schedules.size(); e++) {
				availableTimes.add(schedules.get(e).returnAvailables(start, end, totalDays));
		}
		
		LocalDate current = new LocalDate(start);
		for(int i = 0; i < totalDays + 1; i++) {
			Map<String, TimeFrame> first = availableTimes.get(0).get(current);
			if(first == null) {
				continue;
			}
			for(int e = 0; e < schedules.size() - 1; e++) {
				 Map<String, TimeFrame> second = availableTimes.get(e+1).get(current);
				 if(second == null) {
				 	continue;
				 }
				 first = getAvailableTimes(first, second);	 
			}
			for (Map.Entry<String, TimeFrame> entry : first.entrySet()) {
				available.add(new DatewithTime(current, entry.getValue()));
			}
			current = current.plusDays(1);
		}
		return available;
	}
	public Map<String, TimeFrame> getAvailableTimes(Map<String, TimeFrame> a, Map<String, TimeFrame> b) {
		Map<String, TimeFrame> availableTimes = new HashMap<>();

		a.forEach((k,v)->{
			TimeFrame first = a.get(k);
			b.forEach((kk,vv)->{
				TimeFrame second = b.get(kk);
				if(first.contains(second) || second.contains(first)) {
					if(first.getStartTime().equals(second.getStartTime())) {
						if(first.getEndTime().compareTo(second.getEndTime()) ==1) {
							availableTimes.put(kk+"",new TimeFrame(first.getStartTime(), second.getEndTime()));
						} else if(first.getEndTime().compareTo(second.getEndTime()) ==-1) {
							availableTimes.put(kk+"",new TimeFrame(first.getStartTime(), first.getEndTime()));
						} else {
							availableTimes.put(kk+"",new TimeFrame(first.getStartTime(), second.getEndTime()));
						}
					} else if(second.getStartTime().compareTo(first.getStartTime()) ==1){
						if(first.getEndTime().compareTo(second.getEndTime()) ==1) {
							availableTimes.put(kk+"",new TimeFrame(second.getStartTime(), second.getEndTime()));
						} else if(first.getEndTime().compareTo(second.getEndTime()) ==-1) {
							availableTimes.put(kk+"",new TimeFrame(second.getStartTime(), first.getEndTime()));
						} else {
							availableTimes.put(kk+"",new TimeFrame(second.getStartTime(), second.getEndTime()));
						}
					} else {
						if(first.getEndTime().compareTo(second.getEndTime()) ==1) {
							availableTimes.put(kk+"",new TimeFrame(first.getStartTime(), second.getEndTime()));
						} else if(first.getEndTime().compareTo(second.getEndTime()) ==-1) {
							availableTimes.put(kk+"",new TimeFrame(first.getStartTime(), first.getEndTime()));
						} else {
							availableTimes.put(kk+"",new TimeFrame(first.getStartTime(), second.getEndTime()));
						}
					}
				}
			});
		});
		return availableTimes;
	}
	public int getDaysBetween() {
		LocalDate dt = new LocalDate(start);
		LocalDate de = new LocalDate(end);

		return Days.daysBetween(dt, de).getDays();
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public List<MapAvailable> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<MapAvailable> schedules) {
		this.schedules = schedules;
	}
}
