package edu.csupomona.cs480.data;

import java.util.List;

public class Group {
	private String groupName;
	private Date dayofEvent;
	private TimeFrame timeofEvent;
	private List<String> userIDs;
	public Group(String groupName, Date dayofEvent, List<String> userIDs, TimeFrame timeofEvent) {
		this.groupName = groupName;
		this.dayofEvent = dayofEvent;
		this.userIDs = userIDs;
		this.timeofEvent = timeofEvent;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Date getDayofEvent() {
		return dayofEvent;
	}
	public void setDayofEvent(Date dayofEvent) {
		this.dayofEvent = dayofEvent;
	}
	public List<String> getUserIDs() {
		return userIDs;
	}
	public void setUserIDs(List<String> userIDs) {
		this.userIDs = userIDs;
	}
	public TimeFrame getTimeofEvent() {
		return timeofEvent;
	}
	public void setTimeofEvent(TimeFrame timeofEvent) {
		this.timeofEvent = timeofEvent;
	}
	
}
