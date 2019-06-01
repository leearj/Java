package edu.csupomona.cs480.data;

import java.util.Arrays;

public class ExtractAvailable {
	private MapAvailable[] tests;
	private long start;
	private long end;

	public ExtractAvailable() {}

	public ExtractAvailable(MapAvailable[] tests, long start, long end) {
		this.tests = tests;
		this.start = start;
		this.end = end;
	}

	public MapAvailable[] getTests() {
		return tests;
	}

	public void setTests(MapAvailable[] tests) {
		this.tests = tests;
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
}
