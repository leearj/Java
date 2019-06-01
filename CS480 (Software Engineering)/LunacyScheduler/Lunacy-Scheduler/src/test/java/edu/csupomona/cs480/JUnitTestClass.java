package edu.csupomona.cs480;

import static org.junit.Assert.*;

import edu.csupomona.cs480.data.*;
import org.junit.Test;

// Johnny Lu 
// Assignment 6
// Unit Test
public class JUnitTestClass {

	@Test
	public void test() {
		Time1 noon = new Time1(12, 00);
		Time1 midnight = new Time1(24, 00);
		TimeFrame1 noonToMidnight = new TimeFrame1(noon, midnight);
		TimeFrame1 twelveHours = new TimeFrame1(new Time1(12, 00), new Time1(24, 00));
		assertEquals(noonToMidnight, twelveHours);
	}
}
