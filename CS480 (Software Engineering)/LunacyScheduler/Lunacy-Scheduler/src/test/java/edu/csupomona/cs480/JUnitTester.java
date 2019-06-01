package edu.csupomona.cs480;

import static org.junit.Assert.*;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

//Jaeseung Lee 
//Assignment 6
//Unit Test
// Comment: class needs to be public; beforeclass-static, public- rest
public class JUnitTester {

	private static int value1, value2, result;
	
	@BeforeClass
	public static void setUp() throws Exception {
		Random r = new Random();
		value1 = r.nextInt(10)+1;
		value2 = r.nextInt(10)+1;
	}

	@Test
	public void testAdd() {
		result = add(value1,value2);
		System.out.println("value1: "+value1);
		System.out.println("value2: "+value2);
		System.out.println("result: "+result);
		
		assertTrue(result == value1+value2);
	}
	
	public int add(int n1, int n2) {
		return n1+n2;
	}
}
