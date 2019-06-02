import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Scheduler implements Algorithm {
	private List<Process> sortedList;
	private String selectedAlg;
	private final int switchCost = 3;

	private int n;
	private List<int[]> listTable;
	private int[][] table;
	private int cpuTime = 0, TAT, quantum;
	private double avgTAT = 0.0;
	private int c = 0;

	Scheduler(List<Process> sortedList, String selectedAlg) {
		this.sortedList = sortedList;
		this.selectedAlg = selectedAlg;
	}

	@Override
	public void runAlg() {
		// cpuTime: current CPU Time.
		// sBT: start burst time
		// eBT: end burst time.
		// CT: completion time.
		// TAT: turnAroundTime for each Process ; avgTAT: average TAT.

		// This will setup the table.
		// | cpuTime | pID | sBT | eBT | CT |
		switch (selectedAlg) {
		case "fcfs":
			fcfs();
			break;
		case "sjf":
			sjf();
			break;
		case "rr1":
			rr1();
			break;
		case "rr2":
			rr2();
			break;
		case "lottery":
			lottery();
			break;
		}
	}

	
	@Override
	public void fcfs() {
		runNormalScheduler();
	}

	@Override
	public void sjf() {
		runNormalScheduler();
	}

	@Override
	public void rr1() {
		runRRscheduler(20);
	}

	@Override
	public void rr2() {
		runRRscheduler(40);
	}

	@Override
	public void lottery() {
		quantum = 30;

		listTable = new ArrayList<>();
		List<Integer> running = new ArrayList<>();
		boolean delete;
		int dIndex;
		int[] row;

		// #1. Init. - Add all the list of processes INDEX in running.
		for (int i = 0; i < sortedList.size(); i++)
			running.add(i); // 0 1 2 3 4

		
		// #2. when all processes get their CT result(not 0), this loop ends.
		while (!running.isEmpty()) {
			delete = false;
			dIndex = -1;
			row = new int[5];

			int ticket = 1;
			int lotterySize = 0;
			List<Integer> indexBox = new ArrayList<>();
			List<Integer> ticketBox = new ArrayList<>();
			int winnerProcessIndex = -1;
			int winnerTicket = -1;

			// 1) Every time the lotterySize changes (because new winner is excluded)
			for (int i = 0; i < running.size(); i++)
				lotterySize += sortedList.get(running.get(i)).getPriority();


			// 2) Loop through all the processes and setup the lottery environment.
			for (int i = 0; i < running.size(); i++) {

				// ticket # = the process's priority #
				for (int j = 0; j < sortedList.get(running.get(i)).getPriority(); j++) {
					ticketBox.add(ticket++);
					indexBox.add(running.get(i));
				}
			}


			System.out.println("ticketBox: " + ticketBox); 
			System.out.println("indexBox: " + indexBox);

			// 3) We pick the winner number.
			winnerTicket = new Random().nextInt(lotterySize) + 1; // 1~sumOfAllPriority
			System.out.println("winnerTicket: " + winnerTicket);

			// 4) We record the index of that winner.
			for (int i = 0; i < ticketBox.size(); i++) {
				if (ticketBox.get(i) == winnerTicket)
					winnerProcessIndex = indexBox.get(i);
			}
			
			System.out.println("winnerProcessIndex: " + winnerProcessIndex);

			// #3. We finally perform scheduling(printout on table) of that won Process.
			int pID = sortedList.get(winnerProcessIndex).getpID();
			int sBT = sortedList.get(winnerProcessIndex).getsBT();
			int eBT = sortedList.get(winnerProcessIndex).geteBT();
			int CT = sortedList.get(winnerProcessIndex).getCT();

			if (eBT == -1 || CT == -1) {
				eBT = 0;
				CT = 0;
			}

			// 1) put Winner-Process's pID into the table. (row[1])
			row[1] = sortedList.get(winnerProcessIndex).getpID();
			row[2] = sortedList.get(winnerProcessIndex).getsBT();

			if ((sBT - quantum) <= 0) { // [3] = eBT; should never be negative number.
				eBT = 0;
			} else {
				eBT = sBT - quantum;
			}
			row[3] = eBT;

			row[0] = cpuTime;

			// if eBT = 0, the process is done, record CT.
			if (eBT == 0) {
				CT = cpuTime + sBT;
				row[4] = CT; // [4] = CT; (case1)

				// eBT != 0 means the process is not done.
			} else {
				row[4] = CT; // [4] = CT; (case2)

				// Rest of the prep for next iteration.
				if (sBT - quantum <= 0)
					sBT = 0;
				else
					sBT = (sBT - quantum);
			}
			cpuTime += (CT + quantum + switchCost);
			

			// if need to be deleted, remove from running
			if (eBT == 0) {
				delete = true;
				
				
				// size = 6 => 0 2 3 4 6 7
				for (int i = 0; i < running.size(); i++) {
					if (running.get(i) == winnerProcessIndex) {
						dIndex = i;
					}
				}
			}
			System.out.println();

			listTable.add(row);
		
			if(delete && dIndex != -1) 
			running.remove(dIndex);
			
			sortedList.get(winnerProcessIndex).setpID(pID);
			sortedList.get(winnerProcessIndex).setsBT(sBT);
			sortedList.get(winnerProcessIndex).seteBT(eBT);
			sortedList.get(winnerProcessIndex).setCT(CT);
		}

		// Show Result.
		System.out.println("listTable Size: " + listTable.size());
		for (int i = 0; i < listTable.size(); i++) {
			System.out.printf("|%7d|%7d|%7d|%7d|%7d|\n", listTable.get(i)[0], listTable.get(i)[1], listTable.get(i)[2],
					listTable.get(i)[3], listTable.get(i)[4]);
		}

		// get avgTAT
		n = sortedList.size();
		for (int i = 0; i < listTable.size(); i++)
			avgTAT += listTable.get(i)[4];
		System.out.println("[lottery" + quantum + "] Sum of all TAT: " + avgTAT);
		avgTAT = Math.round(avgTAT / n);
		System.out.println("[lottery" + quantum + "] Average TAT: " + avgTAT + "\n");
	}

	private void runNormalScheduler() {
		/** This method is used ONLY for FCFS and SJF **/

		n = sortedList.size();
		table = new int[n][5];
		int CT;

		for (int i = 0; i < n; i++) {

			// #1. put each Process's pID into the table. (table[i][1])
			table[i][1] = sortedList.get(c).getpID();

			// #2. put sBT into the table. (table[i][2])
			table[i][2] = sortedList.get(c).getsBT();

			// #3. all eBT = 0. (table[i][3])
			table[i][3] = 0;

			// #4. Calculation for current_CT, and next_cpuTime.
			table[i][0] = cpuTime;
			CT = cpuTime + sortedList.get(c).getsBT();
			table[i][4] = CT;
			cpuTime = (CT + switchCost); // setup next cpuTime

			c++; // sortedList index: (0~6)
		}

		// PrintOut the result just for visualization.
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				System.out.printf("|%5d|", table[i][j]);
			}
			System.out.println();
		}

		// get Average TAT
		for (int i = 0; i < n; i++)
			avgTAT += table[i][4];
		System.out.println("[sjf] Sum of TATs: " + avgTAT);
		avgTAT = Math.round(avgTAT / n);
		System.out.println("[sjf] Average TAT: " + avgTAT);
	}

	private void runRRscheduler(int q) {
		quantum = q;
		listTable = new ArrayList<>();
		
		List<Process> running = new ArrayList<>();
		for (int i = 0; i < sortedList.size(); i++)
			running.add(sortedList.get(i));

		// when all processes get their CT result(not 0), this loop ends.
		while (!running.isEmpty()) {

			boolean delete = false;
			int dIndex = -1;

			// Run all process that need to be still running.
			for (int i = 0; i < running.size(); i++) {

				int[] row = new int[5]; // |cpuTime|pID|sBT|eBT|CT|
				int pID = running.get(i).getpID();
				int sBT = running.get(i).getsBT();
				int eBT = running.get(i).geteBT();
				int CT = running.get(i).getCT();

				// if initial -1, set it to 0.
				if (eBT == -1 || CT == -1) {
					eBT = 0;
					CT = 0;
				}

				row[0] = cpuTime; // [0] = cpuTime
				row[1] = pID; // [1] = pID

				if (sBT < 0)
					sBT = 0;
				row[2] = sBT; // [2] = sBT

				if ((sBT - quantum) <= 0) {
					eBT = 0;
				} else {
					eBT = sBT - quantum;
				}
				row[3] = eBT; // [3] = eBT;

				// if eBT = 0, the process is done, record CT.
				if (eBT == 0) {
					dIndex = i;
					CT = cpuTime + sBT;
					row[4] = CT; // [4] = CT; (case1)

					// eBT != 0, the process is not done.
				} else {
					row[4] = CT; // [4] = CT; (case2)

					// Rest of the prep for next iteration.
					if (sBT - quantum <= 0)
						sBT = 0;
					else
						sBT = (sBT - quantum);

					cpuTime += (quantum + switchCost);
				}

				if (eBT == 0) {
					delete = true;
					dIndex = i;
				}

				listTable.add(row);
				running.get(i).setpID(pID);
				running.get(i).setsBT(sBT);
				running.get(i).seteBT(eBT);
				running.get(i).setCT(CT);
			}
			if (delete) {
				// System.out.println("Removing index" + dIndex);
				running.remove(dIndex);
				// System.out.println("running after Removed: " + running);
			}
		}

		// Show Result.
		for (int i = 0; i < listTable.size(); i++) {
			System.out.printf("|%5d|%5d|%5d|%5d|%5d|\n", listTable.get(i)[0], listTable.get(i)[1], listTable.get(i)[2],
					listTable.get(i)[3], listTable.get(i)[4]);
		}
		// get avgTAT
		n = sortedList.size();
		for (int i = 0; i < listTable.size(); i++)
			avgTAT += listTable.get(i)[4];
		System.out.println("[rr" + quantum + "] Sum of all TAT: " + avgTAT);
		avgTAT = Math.round(avgTAT / n);
		System.out.println("[rr" + quantum + "] Average TAT: " + avgTAT + "\n");
	}

	public double getAvgTAT() {
		return avgTAT;
	}

	public List<int[]> getListTable() {
		return listTable;
	}

	public void setListTable(List<int[]> listTable) {
		this.listTable = listTable;
	}

	public int[][] getTable() {
		return table;
	}

	public void setTable(int[][] table) {
		this.table = table;
	}
}