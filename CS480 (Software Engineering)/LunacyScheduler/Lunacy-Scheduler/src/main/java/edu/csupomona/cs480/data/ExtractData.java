package edu.csupomona.cs480.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ExtractData {
	private long start;
	private long end;
	private String[] users;

	public ExtractData() {}
	public ExtractData(long start, long end, String[] users) {
		this.start = start;
		this.end = end;
		this.users = users;
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

	public String[] getUsers() {
		return users;
	}

	public void setUsers(String[] users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "ExtractData{" +
				"start=" + start +
				", end=" + end +
				", users=" + Arrays.toString(users) +
				'}';
	}
}
