package edu.csupomona.cs480.data.Schedules;

/**
 * Created by justinhan on 11/24/17.
 */
public class DayFrame {
    private long start;
    private long end;

    public DayFrame() {

    }
    public DayFrame(long start, long end) {
        this.start = start;
        this.end = end;
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

    @Override
    public String toString() {
        return "DayFrame{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
