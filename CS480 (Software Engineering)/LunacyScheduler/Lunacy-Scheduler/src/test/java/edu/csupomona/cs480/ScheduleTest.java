package edu.csupomona.cs480;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

import edu.csupomona.cs480.data.*;
import edu.csupomona.cs480.data.Date;

/**
 * Unit test for scheduler.
 */
public class ScheduleTest {
	
	//Justin's TestCase
//	@Test
//	public void testAll() throws Exception {
//
//		List<TimeFrame>temp = new ArrayList<TimeFrame>();
//		temp.add(new TimeFrame(new Time(0,0), new Time(10,0)));
//		temp.add(new TimeFrame(new Time(18,0), new Time(23,59)));
//		Schedule c = new Schedule(temp, temp, temp, temp, temp, temp, temp, new Date(2017, 10, 22), new Date(2017, 10, 31));
//		Unavailability testU1 = new Unavailability();
//		testU1.addSchedule(c);
//		RealUser testUser = new RealUser("111", "Justin Han", null, testU1);
//
//		List<TimeFrame>temp2 = new ArrayList<TimeFrame>();
//		temp2.add(new TimeFrame(new Time(0,0), new Time(11,0)));
//		temp2.add(new TimeFrame(new Time(18,0), new Time(23,59)));
//		Schedule d = new Schedule(temp2, temp2, temp2, temp2, temp2, temp2, temp2, new Date(2017, 10, 22), new Date(2017, 10, 31));
//		Unavailability testU2 = new Unavailability();
//		testU2.addSchedule(d);
//		RealUser testUser2 = new RealUser("112", "Ubaldo Jimenez", null, testU2);
//
//		List<RealUser>users = new ArrayList<>();
//		users.add(testUser);
//		users.add(testUser2);
//		Scheduler testScheduler = new Scheduler(new Date(2017,10,23), new Date(2017,10,23), users);
//		List<DatewithTime> availableTime = testScheduler.findAvailableSchedules();
//		assertEquals(availableTime.get(0), new DatewithTime(new Date(2017,10,23), new TimeFrame(new Time(11,0), new Time(18,0))));
//	}
//
//	@Test
//	public void testAvailable() {
//		List<TimeFrame>temp = new ArrayList<TimeFrame>();
//		temp.add(new TimeFrame(new Time(10,0), new Time(12,0)));
//		Schedule c = new Schedule(temp, temp, temp, temp, temp, temp, temp, new Date(2017, 10, 24), new Date(2017, 10, 31));
//		Available testU1 = new Available(c);
//		RealUser testUser = new RealUser("111", "Justin Han", null, testU1);
//
//		List<TimeFrame>temp2 = new ArrayList<TimeFrame>();
//		temp2.add(new TimeFrame(new Time(11,0), new Time(12,0)));
//		Schedule d = new Schedule(temp2, temp2, temp2, temp2, temp2, temp2, temp2, new Date(2017, 10, 24), new Date(2017, 10, 31));
//		Available testU2 = new Available(d);
//		RealUser testUser2 = new RealUser("112", "Ubaldo Jimenez", null, testU2);
//
//		List<RealUser>users = new ArrayList<>();
//		users.add(testUser);
//		users.add(testUser2);
//		AvailableScheduler testScheduler = new AvailableScheduler(new Date(2017,10,25), new Date(2017,10,25), users);
//		List<DatewithTime> availableTime = testScheduler.findAvailableTimes();
//		assertEquals(new DatewithTime(new Date(2017,10,25), new TimeFrame(new Time(11,0), new Time(12,0))), availableTime.get(0));
//	}
//
//	@Test
//	public void testBestAvailable() {
//		List<TimeFrame>temp = new ArrayList<TimeFrame>();
//		temp.add(new TimeFrame(new Time(10,0), new Time(12,0)));
//		temp.add(new TimeFrame(new Time(13,30), new Time(14,00)));
//		temp.add(new TimeFrame(new Time(15,00), new Time(17,00)));
//		Schedule c = new Schedule(temp, temp, temp, temp, temp, temp, temp, new Date(2017, 10, 24), new Date(2017, 10, 31));
//		Available testU1 = new Available(c);
//		RealUser testUser = new RealUser("111", "Justin Han", null, testU1);
//
//		List<TimeFrame>temp2 = new ArrayList<TimeFrame>();
//		temp2.add(new TimeFrame(new Time(11,0), new Time(12,0)));
//		temp2.add(new TimeFrame(new Time(13,30), new Time(14,00)));
//		temp2.add(new TimeFrame(new Time(15,00), new Time(17,00)));
//		Schedule d = new Schedule(temp2, temp2, temp2, temp2, temp2, temp2, temp2, new Date(2017, 10, 24), new Date(2017, 10, 31));
//		Available testU2 = new Available(d);
//		RealUser testUser2 = new RealUser("112", "Ubaldo Jimenez", null, testU2);
//
//		List<RealUser>users = new ArrayList<>();
//		users.add(testUser);
//		users.add(testUser2);
//		AvailableScheduler testScheduler = new AvailableScheduler(new Date(2017,10,27), new Date(2017,10,27), users);
//		DatewithTime availableTime = testScheduler.bestAvailableTime();
//		assertEquals(new DatewithTime(new Date(2017,10,27), new TimeFrame(new Time(15,0), new Time(17,0))), availableTime);
//	}
	
	//Ubaldo's Test Case # 1
	@Test
	public void mytestCase1(){
		
		Date currentDate = new Date(2017,12,25);
		assertEquals(new Date(2017, 12, 26), currentDate.nextDate());
	}
	
	 //Ubaldo's Test Case # 2
	@Test
	public void mytestCase2(){
		
		Date currentDate = new Date(2017,12,25);
		Date futureDate = new Date(2017,12,31);
	
		assertEquals(6, currentDate.getDaysBetween(futureDate));
	}
}
