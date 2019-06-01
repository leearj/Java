package edu.csupomona.cs480.data;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ConvertDateTime {
	private String start;
	private String end;

	public ConvertDateTime(DatewithTime temp) {
		Date date = temp.getDate();
		TimeFrame frame = temp.getTime();
		DateTime st = new DateTime(date.getYear(), date.getMonth(), date.getDate(),
				frame.getStart().getHour(), frame.getStart().getMinute());
		DateTime en = new DateTime(date.getYear(), date.getMonth(), date.getDate(),
				frame.getEnd().getHour(), frame.getEnd().getMinute());

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		start = fmt.print(st);
		end = fmt.print(en);
	}
	public ConvertDateTime(String start, String end) {
		this.start = start;
		this.end = end;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "ConvertDateTime{" +
				"start='" + start + '\'' +
				", end='" + end + '\'' +
				'}';
	}
}
