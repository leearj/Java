import java.util.List;

public class iVoteService extends Classroom {
	private List<Student> stuList;
	private int SQAnswerRight;
	private int SQAnswerWrong;
	private int MQAnswerA;
	private int MQAnswerB;
	private int MQAnswerC;
	private int MQAnswerD;

	iVoteService(int numOfStudents, SQuestion sq, List<Student> stuList) {
		super(numOfStudents, sq);
		this.stuList = stuList;
	}
	
	iVoteService(int numOfStudents, MQuestion mq, List<Student> stuList) {
		super(numOfStudents, mq);
		this.stuList = stuList;
	}

	void countAnswers() {
		// SQuestion case
		if (super.getChosenQType().equals("SQuestion")) {
			SQAnswerRight = 0;
			SQAnswerWrong = 0;

			for (int i = 0; i < super.getStuList().size(); i++) {
				
				if(stuList.get(i).getNumOfSubmit() > 1)	// This handles multiple submission. 
					System.out.println(stuList.get(i).showMyID()+"student submitted multiple times.\nCounting only last submission");
				
					if (stuList.get(i).getMyAnswer().equals("Right"))
						SQAnswerRight++;
					else if (stuList.get(i).getMyAnswer().equals("Wrong"))
						SQAnswerWrong++;
			}
		}

		// MQuestion case
		if (super.getChosenQType().equals("MQuestion")) {
			MQAnswerA = 0;
			MQAnswerB = 0;
			MQAnswerC = 0;
			MQAnswerD = 0;

			for (int i = 0; i < super.getStuList().size(); i++) {
				if (stuList.get(i).getMyAnswer().equals("A"))
					MQAnswerA++;
				else if (stuList.get(i).getMyAnswer().equals("B"))
					MQAnswerB++;
				else if (stuList.get(i).getMyAnswer().equals("C"))
					MQAnswerC++;
				else if (stuList.get(i).getMyAnswer().equals("D"))
					MQAnswerD++;
			}
		}
	}

	void showResult() {
		if (super.getChosenQType().equals("SQuestion")) {
			System.out.println("# of Right: " + SQAnswerRight);
			System.out.println("# of Wrong: " + SQAnswerWrong);
		} else if (super.getChosenQType().equals("MQuestion")) {
			System.out.println("# of A: " + MQAnswerA);
			System.out.println("# of B: " + MQAnswerB);
			System.out.println("# of C: " + MQAnswerC);
			System.out.println("# of D: " + MQAnswerD);
		}
	}
}
