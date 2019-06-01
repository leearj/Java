package edu.csupomona.cs480.data;

import java.util.List;



/**
 * The basic user object.
 */
public class RealUser {

    private String id;
    private String name;
    private List<Group> groups;
    private Unavailability schedules;
    private Available availSchedules;

    public RealUser(String id, String name, List<Group> groups,
			Unavailability schedules) {
		this.id = id;
		this.name = name;
		this.groups = groups;
		this.schedules = schedules;
	}

	/**
	 * @param id
	 * @param name
	 * @param groups
	 * @param availSchedules
	 */
	public RealUser(String id, String name, List<Group> groups,
			Available availSchedules) {
		this.id = id;
		this.name = name;
		this.groups = groups;
		this.availSchedules = availSchedules;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public Unavailability getSchedules() {
		return schedules;
	}

	public void setSchedules(Unavailability schedules) {
		this.schedules = schedules;
	}

	/**
	 * @return the availSchedules
	 */
	public Available getAvailSchedules() {
		return availSchedules;
	}

	/**
	 * @param availSchedules the availSchedules to set
	 */
	public void setAvailSchedules(Available availSchedules) {
		this.availSchedules = availSchedules;
	}
}
