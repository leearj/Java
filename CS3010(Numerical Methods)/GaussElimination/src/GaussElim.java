import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class GaussElim {
	private File file;
	private String fileName;
	private double[][] A;
	private double[] B;
	private double[] sol;

	GaussElim(String fileName) {
		this.fileName = fileName;
		file = new File(fileName);
	}

	@SuppressWarnings("resource")
	public void read() {
		int lc = 0; // lineCounter excluding the integer at the top.

		try {
			Scanner sc = new Scanner(file);
			int n = sc.nextInt();
			sc.nextLine();

			while (sc.hasNextLine()) {
				lc++; // lineCounter
				sc.nextLine();
			}

			A = new double[lc - 1][n]; // exclude the integer at top, and very bottom line(B).
			B = new double[n]; // all B here.

			sc = new Scanner(file);
			sc.nextLine();
			for (int i = 0; i < lc - 1; i++) {
				for (int j = 0; j < n; j++) {
					A[i][j] = sc.nextDouble();
				}
			}
			for (int i = 0; i < n; i++)
				B[i] = sc.nextDouble();

			System.out.println("\n=== Original Value ===");
			System.out.println("matrixA");
			printArray(A);
			System.out.println("\nmatrixB");
			printArray(B);
			System.out.println("======================\n");

			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void write() { // create outputfile

		try {
			String outputName = fileName.replace(".lin", ".sol");
			File outputFile = new File(outputName);
			outputFile.createNewFile();

			PrintWriter pw = new PrintWriter(outputFile);

			for (int i = 0; i < sol.length; i++) {
				pw.println("x" + i + " = " + sol[i]);
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 1] Naive_Section ********************************************************/
	public void naiveGaussElim() {
		// Allow the main method to run without arguments.
		naiveGaussian(A, B);

		System.out.println("== After Naive Algorithm ========");
		System.out.println("A: ");
		printArray(A);
		System.out.println();
		System.out.println("B: ");
		printArray(B);
		System.out.println();
		System.out.println("Solution:");
		printArray(sol);
		System.out.println("================================");
	}

	void naiveGaussian(double[][] A, double[] B) {
		sol = new double[A.length];
		fwdElim(A, B);
		backSubst(A, B, sol);
	}

	private void fwdElim(double[][] A, double[] B) {
		int n = B.length;
		for (int k = 0; k < n; k++) {
			for (int i = k + 1; i < n; i++) {
				double mult = A[i][k] / A[k][k];
				for (int j = k + 1; j < n; j++) {
					A[i][j] = A[i][j] - mult * A[k][j];
				}
				B[i] = B[i] - mult * B[k];
			}
		}
	}

	private void backSubst(double[][] A, double[] B, double[] sol) {
		int n = B.length;

		for (int i = 0; i < n; i++)
			sol[i] = B[i] / A[i][i]; // sol[n] := const[n] / coeff[n][n];

		for (int i = n - 1; i >= 0; i--) {
			double sum = 0.0;
			for (int j = i + 1; j < n; j++)
				sum += A[i][j] * sol[j];
			sol[i] = (B[i] - sum) / A[i][i];
		}
	}

	/** 2] SPP_Section ********************************************************/
	public void SPPElim() {
		SPPGaussian(A, B); // allow main method to run without arguments.

		System.out.println("matrixA");
		printArray(A);
		System.out.println("\nmatrixB");
		printArray(B);
		System.out.println();

		System.out.println("Solutions: ");
		printArray(sol);
	}

	void SPPGaussian(double[][] A, double[] B) {
		int n = B.length;
		sol = new double[A.length];
		int[] ind = new int[n];

		for (int i = 1; i < n; i++) {
			ind[i] = i;
		}
		
		
		SPPFwdElim(A, B, ind);
		SPPBackSubst(A, B, sol, ind);
	}

	void SPPFwdElim(double[][] A, double[] B, int[] ind) {
		int n = B.length;
		double[] scaling = new double[n];

		for (int i = 1; i < n; i++) {
			double smax = 0;

			for (int j = 1; j < n; j++) {
				smax = Math.max(smax, Math.abs(A[i][j])); // find coefficient with greatest absolute value
			}
			scaling[i] = smax;
		}

		for (int k = 1; k < n - 1; k++) {
			double rmax = 0;
			int maxInd = k;

			for (int i = k; i < n; i++) {
				double r = Math.abs(A[ind[i]][k] / scaling[ind[i]]); // ratio of coefficient to scaling factor

				if (r > rmax) {
					rmax = r;
					maxInd = i;
				}
			}
			
			swap(ind, maxInd, k);

			for (int i = k + 1; i < n; i++) {
				double mult = A[ind[i]][k] / A[ind[k]][k];

				for (int j = k + 1; j < n; j++) {
					A[ind[i]][j] = A[ind[i]][j] - mult * A[ind[k]][j];
				}
				B[ind[i]] = B[ind[i]] - mult * B[ind[k]];	// ERROR FIXED: A[ind[i]][k] -> mult
			}
		}
	}

	private void SPPBackSubst(double[][] A, double[] B, double[] sol, int[] ind) {
		int n = B.length;
		
		for (int i = 0; i < n - 1; i++) {
			double sum = B[ind[i]];

			for (int j = i + 1; j < n; j++) {
				sum = sum - A[ind[i]][j] * sol[j];
			}
			sol[i] = sum / A[ind[i]][i];
		}

	}

	/** 3] Helper Methods ********************************************************/
	private void swap(int[] arr, int a, int b) {
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}

	private void printArray(double[][] A) {
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[i].length; j++) {
				System.out.print(A[i][j] + " ");
			}
			System.out.println();
		}
	}

	private void printArray(double[] B) {
		for (int i = 0; i < B.length; i++)
			System.out.print(B[i] + " ");
		System.out.println();
	}
}
