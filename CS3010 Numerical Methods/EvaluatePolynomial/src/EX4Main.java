import java.util.Random;
import java.util.Scanner;

import AlgoMain.Timer;

// CS3310_A4: EX4

public class EX4Main {
	static int n;
	final static int x = 2; // when x = 2;
	static String[] xs_str;
	static String[] ys_str;
	static float[] xs;
	static float[] ys;
	static float[] cs;

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Type n: ");
		n = Integer.parseInt(sc.next());

		
		xs = new float[n];
		ys = new float[n];
		cs = new float[n];

		for (int i = 0; i < n; i++)
			xs[i] = new Random().nextInt(100);
		for (int i = 0; i < n; i++)
			ys[i] = new Random().nextFloat() * 100 - 100;

		System.out.print("\nxs: ");
		for (int i = 0; i < xs.length; i++)
			System.out.print(xs[i] + " ");
		System.out.print("\nys: ");
		for (int i = 0; i < ys.length; i++) {
			System.out.print(ys[i] + " ");
		}
		System.out.println("");

		float randFloat = new Random().nextFloat() * 10 - 10;
		System.out.println("Random point to evaluate: " + randFloat);
		Timer.startTimer();
		coeff(xs, ys);
		evalNewton(xs, randFloat);
		Timer.endTimer();
		
		System.out.println("\nxs:");
		for (int i = 0; i < xs.length; i++)
			System.out.print(xs[i] + " ");
		System.out.println("\nys:");
		for (int i = 0; i < ys.length; i++)
			System.out.print(ys[i] + " ");
		System.out.println("\ncs:");
		for (int i = 0; i < cs.length; i++)
			System.out.print(cs[i] + " ");
	}

	static void coeff(float[] xs, float[] ys) {
		for (int i = 0; i < n; i++) {
			cs[i] = ys[i];
		}

		for (int j = 1; j < n; j++) {
			for (int i = n - 1; i >= j; i--) {
				cs[i] = (cs[i] - cs[i - 1]) / (xs[i] - xs[i - j]);
			}
		}
	}

	static float[] evalNewton(float[] xs, float z) {
		float[] result = cs;

		for (int i = n - 1; i > 0; i--) {
			result[i] = result[i] * (z - xs[i]) + cs[i];
		}
		return result;
	}
	
	/** Inner class: Timer **/
	static class Timer {
		static long startTime;
		static long endTime;
		static long timeElapsed;

		static void startTimer() {
			startTime = System.nanoTime();
		}

		static void endTimer() {
			endTime = System.nanoTime();
			timeElapsed = endTime - startTime;
			System.out.println(timeElapsed+"ns or "+ timeElapsed/1E6 +"ms");
		}
	}
}
