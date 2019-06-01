import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// CS3310_A4: EX3

public class EX3Main {
	static int n;
	final static int x=2;	// when x = 2;
	static String[] xs_str;
	static String[] ys_str;
	static float[] xs;
	static float[] ys;
	static float[] cs;
	
	public static void main(String[] args) {
		File file = new File("data1.txt");
		Scanner sc;
		Scanner kb;
		
		try {
			sc = new Scanner(file);
			xs_str = sc.nextLine().trim().split("\\s+");
			ys_str = sc.nextLine().trim().split("\\s+");
			n = xs_str.length;
			xs = new float[n];
			ys = new float[n];
			cs = new float[n];

			for (int i = 0; i < xs_str.length; i++)
				xs[i] = Float.parseFloat(xs_str[i]);
			for (int i = 0; i < ys_str.length; i++)
				ys[i] = Float.parseFloat(ys_str[i]);

			System.out.print("xs: ");
			for (int i = 0; i < xs.length; i++)
				System.out.print(xs[i] + " ");
			System.out.print("\nys: ");
			for (int i = 0; i < ys.length; i++) {
				System.out.print(ys[i] + " ");
			}
			System.out.println("");
			
			while (true) {
				int input_i;
				String input_s;

				System.out.println("\n\nType a value to evaluate the polynomial. 'q' to exit.");
				kb = new Scanner(System.in);
				input_s = kb.next();
				if (input_s.equals("q"))
					System.exit(0);
				else {
					try {
						input_i = Integer.parseInt(input_s);
						coeff(xs,ys);
						evalNewton(xs,input_i);
						
						System.out.println("xs:");
						for(int i=0;i<xs.length;i++)
							System.out.print(xs[i]+" ");
						System.out.println("\nys:");
						for(int i=0;i<ys.length;i++)
							System.out.print(ys[i]+" ");
						System.out.println("\ncs:");
						for(int i=0;i<cs.length;i++)
							System.out.print(cs[i]+" ");
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Input was not integer.");
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	static void coeff(float[] xs, float[] ys) {
		for (int i = 0; i < n; i++) {
			cs[i] = ys[i];
		}

		for (int j = 1; j < n; j++) {
			for (int i = n-1; i >= j; i--) {
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
}
