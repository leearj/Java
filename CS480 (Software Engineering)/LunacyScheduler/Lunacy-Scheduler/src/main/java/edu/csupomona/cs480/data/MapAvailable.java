package edu.csupomona.cs480.data;

import org.joda.time.LocalDate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapAvailable {
	Map<String, Schedule2> availableTimes;


	public MapAvailable() {}
	public Map<LocalDate,Map<String, TimeFrame>> returnAvailables(long start, long end, int days){
		Map<LocalDate,Map<String, TimeFrame>> availables = new HashMap<>();
		LocalDate current = new LocalDate(start);
		for(int i = 0; i < days + 1; i++) {
			availables.put(current, findAvailforDate(current));
			current = current.plusDays(1);
		}
		return availables;
	}
	private Map<String, TimeFrame> findAvailforDate(LocalDate date) {
		Schedule2 check = findCorrectSchedule(date);
		if(check == null) {
			return null;
		} else {
			return check.returnScheduleforDayofWeek(date);
		}
	}
	private Schedule2 findCorrectSchedule(LocalDate date) {
		for (Map.Entry<String, Schedule2> entry : availableTimes.entrySet()) {
			if(entry.getValue().contains(date)) {
				return entry.getValue();
			}
		}
		return null;
	}

	public Map<String, Schedule2> getAvailableTimes() {
		return availableTimes;
	}

	public void setAvailableTimes(Map<String, Schedule2> availableTimes) {
		this.availableTimes = availableTimes;
	}

	@Override
	public String toString() {
		return "MapAvailable{" +
				"availableTimes=" + availableTimes +
				'}';
	}
}
