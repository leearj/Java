import java.util.List;

class Algo {
	int coefDeg;
	List<Integer> poly;
	final double eps = (double) 0.01;
	final double delta = 0.05;

	Algo(int coefDeg, List<Integer> poly) {
		this.coefDeg = coefDeg; // 3
		this.poly = poly; // 3 5 0 -7
	}

	double bisection(double a, double b, int maxItr) {
		double c = -1;
		double error;

		double fa = f(a);
		double fb = f(b);

		if (fa * fb >= 0) {
			System.out.println("Inadequate for a and b.");
			return -1.0;
		}

		error = b - a;

		for (int itr = 1; itr <= maxItr; itr++) {

			error = error / 2;
			c = a + error;
			double fc = f(c);

			if (Math.abs(error) < eps || fc == 0) {
				System.out.println("Algorithm has converged after " + itr + "iteration.");
				return c;
			}

			if (fa * fc < 0) {
				b = c;
				fb = fc;
			} else {
				a = c;
				fa = fc;
			}
		}
		System.out.println("Max Iteration reached without convergence...");
		return c;
	}

	double newton(double x, int maxItr) {
		double d, fd;
		double fx = f(x);

		for (int itr = 1; itr <= maxItr; itr++) {
			fd = derF(x);

			if (Math.abs(fd) < delta) {
				System.out.println("Small Slope!!");
				return x;
			}

			d = fx / fd;
			x = x - d;
			fx = f(x);

			if (Math.abs(d) < eps) {
				System.out.println("Algorithm has converged after " + itr + "iterations!");
				return x;
			}
		}
		System.out.println("Max iterations reached without convergence...");
		return x;
	}

	double secant(double a, double b, int maxItr) {
		double d;
		double fa = f(a);
		double fb = f(b);

		for (int itr = 1; itr <= maxItr; i++) {
			if (Math.abs(fa) > Math.abs(fb)) {
				double tmp = a;
				a = b;
				b = tmp;
				tmp = fa;
				fa = fb;
				fb = tmp;
			}

			d = (b - a) / (fb - fa);
			b = a;
			fb = fa;
			d = d * fa;

			if (Math.abs(d) < eps) {
				System.out.println("ALgorithm has converged after " + itr + "iterations.");
				return a;
			}

			a = a - d;
			fa = f(a);
		}
		System.out.println("Maximum number of iterations reached!!");
		return a;
	}

	// Hybrid : Bisection + Newton
	double hybrid(double a, double b , int maxItr) {
		double x=-1,fx=-1;
		double fa = f(a);
		
		for(int itr = 1; itr<=maxItr;itr++) {
			 x = (a+b) /2;
			 fx = f(x);
			 
			if(fx == 0 || f(x) < eps) {
				System.out.println("Exiting loop...");
				return a;
			}
			
			if(fa * fx < 0)
				b = x;
			else
				a = x;
		}
		
		x = x - (fa / derF(x)); 
		
		if(fx <eps)
			return a;
		
		return -1;
	}

	public double f(double num) { // Calculates f(x) given x and returns
		// i = 3 2 1 0
		// poly = 3(deg3) 5(deg2) 0(deg1) -7(deg0)
		// 3 (get(0)) * Math.pow(num,i=3)
		double sum = 0.0;
		int c = 0;
		for (int i = coefDeg; i >= 0; i--) { // 3 2 1 0
			sum += (poly.get(c) * Math.pow(num, i));
			c++;
		}
		return sum;
	}

	double derF(double x) {
		return Math.cos(x);
	}
}