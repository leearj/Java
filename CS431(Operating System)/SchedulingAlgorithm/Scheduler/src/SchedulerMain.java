import java.io.File;

import javax.swing.JOptionPane;

public class SchedulerMain {
	public static void main(String[] args) {

		while (true) {

			File file = new File(JOptionPane.showInputDialog(
					"Type one of testdata:\ntestdata1.txt\ntestdata2.txt\ntestdata3.txt\ntestdata4.txt\n\n*Type EXIT to exit"));
			if (file.getName().equalsIgnoreCase("EXIT"))
				System.exit(0);
			String selectedAlg = JOptionPane
					.showInputDialog("Select what algorithm you want to run:\nfcfs\nsjf\nrr1\nrr2\nlottery");

			IOHandler io = new IOHandler(file, selectedAlg);
			io.readFile();
			Scheduler scheduler = new Scheduler(io.getSortedList(), selectedAlg); 
			scheduler.runAlg();

			if (selectedAlg.equals("fcfs") || selectedAlg.equals("sjf"))
				io.writeData(scheduler.getTable(), scheduler.getAvgTAT()); // Create CSX file with the created-table.
			else if (selectedAlg.equals("rr1") || selectedAlg.equals("rr2") || selectedAlg.equals("lottery"))
				io.writeData(scheduler.getListTable(), scheduler.getAvgTAT());
		}
	}
}