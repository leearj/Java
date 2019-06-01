import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Student {
	
	private String myID;
	private List<String> MQAnswer;	// Possible answers for Multiple-Questions. 
	private List<String> SQAnswer;	// Possible answers for Single-Questions.
	private int numOfSubmit;
	private String myAnswer;
	
	Student() {
		myID = assignStudentID();
		numOfSubmit = 0;
		MQAnswer = new ArrayList<String>();
		SQAnswer = new ArrayList<String>();
		MQAnswer.add("A");
		MQAnswer.add("B");
		MQAnswer.add("C");
		MQAnswer.add("D");
		SQAnswer.add("Right");
		SQAnswer.add("Wrong");
	}
	
	List<String> getMQAns() {
		return MQAnswer;
	}
	
	List<String> getSQAns() {
		return SQAnswer;
	}
	
	String getMyAnswer() {
		return myAnswer;
	}
	
	void setMyAnswer(String str) {
		myAnswer = str;
	}
	
	void submitSQAnswer() {
		Random r = new Random();
		myAnswer=SQAnswer.get(r.nextInt(SQAnswer.size()));
		System.out.println("I set myAnswer");
		numOfSubmit++;
	}
	
	void submitMQAnswer() {
		Random r = new Random();
		myAnswer=MQAnswer.get(r.nextInt(MQAnswer.size()));
		numOfSubmit++;
	}
	
	// Randomly create 9-digits Student ID.
	String assignStudentID(){
		Random r = new Random();
		String id = "";
		
		for(int i=0;i<9;i++) {
			int n= r.nextInt(9)+48; //(ASCII)48~57 = 0~9
			id += (char)n;
		}
		return id;
	}
	String showMyID(){
		return myID;
	}

	int getNumOfSubmit() {
		return numOfSubmit;
	}
	

	public void addNumOfSubmit() {
		numOfSubmit++;
	}
}