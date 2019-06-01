
/* In this class, students will decide which type of questions they want to choose,
 * and allow to show all students.
 * Also students will SUBMIT their answers in the class.
*/

import java.util.List; // * java.awt.List; won't allow List l = new ArrayList();	
import java.util.Random;
import java.util.ArrayList;

public class Classroom {
	private final int numOfStudents;
	private List<Student> stuList;
	private String chosenQType;
	private List<String> MQAnswer;
	private List<String> SQAnswer;

	Classroom(int numOfStudents, SQuestion sq) {
		this.numOfStudents = numOfStudents;
		chosenQType = "SQuestion";
		stuList = new ArrayList<Student>();
		for (int i = 0; i < numOfStudents; i++)
			stuList.add(new Student());

		initAnswers(sq);
	}

	Classroom(int numOfStudents, MQuestion mq) {
		this.numOfStudents = numOfStudents;
		chosenQType = "MQuestion";
		stuList = new ArrayList<Student>();
		for (int i = 0; i < numOfStudents; i++)
			stuList.add(new Student());

		initAnswers(mq);
	}

	void initAnswers(SQuestion sq) {
		SQAnswer = new ArrayList<String>();
		SQAnswer.add("Right");
		SQAnswer.add("Wrong");
	}

	void initAnswers(MQuestion mq) {
		MQAnswer = new ArrayList<String>();
		MQAnswer.add("A");
		MQAnswer.add("B");
		MQAnswer.add("C");
		MQAnswer.add("D");
	}

	void showAllStudents() {
		System.out.println("\n" + numOfStudents + " Students are participating in the class.");
		for (int i = 0; i < stuList.size(); i++) {
			if (i % 10 == 0 && i != 0)
				System.out.println();
			System.out.print(stuList.get(i).showMyID() + " ");
		}
		System.out.println("\n");
	}

	String getChosenQType() {
		return chosenQType;
	}

	List<Student> getStuList() {
		return stuList;
	}

	// Simulate all the students choosing random answers in the class.
	// This should set each students' myAnswer
	// This only submit once, but I also used addNumOfSubmit variable to track
	// student's # of submit.
	List<Student> randomSubmit() {
		if (chosenQType.equals("SQuestion")) {
			for (int i = 0; i < stuList.size(); i++) {
				Random r = new Random();
				stuList.get(i).setMyAnswer(SQAnswer.get(r.nextInt(SQAnswer.size())));
				stuList.get(i).addNumOfSubmit();
			}
		} else if (chosenQType.equals("MQuestion")) {
			for (int i = 0; i < stuList.size(); i++) {
				Random r = new Random();
				stuList.get(i).setMyAnswer(MQAnswer.get(r.nextInt(MQAnswer.size())));
				stuList.get(i).addNumOfSubmit();
			}
		} else {
			System.out.println("Question-Type error. Program will exit");
			System.exit(0);
		}
		return stuList;
	}
}