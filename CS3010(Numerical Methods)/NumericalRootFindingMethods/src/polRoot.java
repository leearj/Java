import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class polRoot {
	public static void main(String[] args) {
		
//		Uncomment to Test
//		
// 		Testing0 (default-Bisection): polRoot 0 1 fun1.pol //run newton's with initial point 0
//		args = new String[3];
//		args[0] = "0";
//		args[1] = "1";
//		args[2] = "fun1.pol";
		
//		Testing1 (newton): polRoot -newt 0 fun1.pol
//		args[0] = "-newt";
//		args[1] = "0";
//		args[2] = "fun1.pol";
//
//		Testing2 (secant): polRoot -sec -maxIter 100000 0 1 fun1.pol with initial points 0 and 1, for 100,000 iterations
//		args[0] = "-sec";
//		args[1] = "-maxIter";
//		args[2] = "100000";
//		args[3] = "0";
//		args[4] = "1";
//		args[5] = "fun1.pol";

		File file;
		Scanner sc;
		
		// Newton case
		if(args[0].equals("-newt")) {
			file = new File(args[0]);
		}
		
		// Secant case
		else if(args[0].equals("-sec")){
			file = new File(args[5]);
		}
		
		// Bisection case
		else {
			file = new File(args[2]);
		}
		
		// Testing case
		// file = new File("fun1.pol");
		
		try {
			sc = new Scanner(file);
			int coefDeg = sc.nextInt();// degree of the polynomial
			System.out.println(coefDeg); 
			
			List<Integer> poly = new ArrayList<Integer>();
			while(sc.hasNextInt())
			poly.add(sc.nextInt());
			System.out.println(poly);
			
			Algo algo = new Algo(coefDeg,poly);

			
			if(args[0].equals("-newt")) {
				algo.newton(Double.parseDouble(args[1]),1000);	// 1000: default maxItr;
				
			}
			else if(args[0].equals("-sec")) {
				algo.secant(Double.parseDouble(args[3]),Double.parseDouble(args[4]),Integer.parseInt(args[2]));
			}
			else { 
				algo.bisection(Double.parseDouble(args[0]),Double.parseDouble(args[1]),1000);	// 1000: default maxItr;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}