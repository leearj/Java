import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class IOHandler {

	private List<Process> list; // this will store data from file. (not sorted)
	File file;
	String selectedAlg;

	IOHandler(File file, String selectedAlg) {
		this.file = file;
		this.selectedAlg = selectedAlg;
		list = new ArrayList<>();
	}
	
	public void writeData(List<int[]> listTable , double avgTAT) {
		try {
			File outputFile = new File(selectedAlg+"_"+file.getName().replace(".txt", "")+".CSV");
			FileWriter fw = new FileWriter(outputFile);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			
			for(int i=0;i<listTable.size();i++) {
				pw.println(listTable.get(i)[0]+", "+listTable.get(i)[1]+", "+listTable.get(i)[2]+", "+listTable.get(i)[3]+", "+listTable.get(i)[4]);
				
			}
			pw.println("\nAverage TAT: "+avgTAT);
			pw.close();
			outputFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeData(int[][] table , double avgTAT) {

		try {
			File outputFile = new File(selectedAlg + "_" + file.getName().replace(".txt", "") + ".CSV");
			FileWriter fw = new FileWriter(outputFile);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);

			for (int i = 0; i < table.length; i++) {
					pw.println(table[i][0] + ", " + table[i][1] + ", " + table[i][2] + ", " + table[i][3] + ", "
							+ table[i][4]);
					pw.flush();
				
			}
			pw.println("\nAverage TAT: "+avgTAT);
			pw.close();
			outputFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Process> getSortedList() {
		List<Process> temp = list;
		switch (selectedAlg) {
		
		// fcfs doesn't require sorting.
		case "fcfs": 
			return temp;

		// sjf sorts by shortest BT to longest BT.
		case "sjf":
			BTCompare btc = new BTCompare();
			Collections.sort(temp, btc);
			return temp;

		case "rr1":
			return temp; // Table_size will be extended with default-list in Scheduler class.

		case "rr2":
			return temp; // Same as rr1.

		case "lottery":
			return temp; // fixed_size table.
		}
		return null;
	}

	public void readFile() {
		
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				int pid = Integer.parseInt(sc.nextLine());
				int bt = Integer.parseInt(sc.nextLine());	// Initialize sBT with bt.
				int p = Integer.parseInt(sc.nextLine());

				list.add(new Process(pid, bt, -1, -1, p));	// Process(int pID, int sBT, int eBT, int CT, int priority)
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("List: "+list+"\n");
	}

	/** Comparator Inner Class **/
	class BTCompare implements Comparator<Process> {
		@Override
		public int compare(Process p1, Process p2) {
			if (p1.getsBT() > p2.getsBT())
				return 1;
			if (p1.getsBT() < p2.getsBT())
				return -1;
			else
				return 0;
		}
	}
}
