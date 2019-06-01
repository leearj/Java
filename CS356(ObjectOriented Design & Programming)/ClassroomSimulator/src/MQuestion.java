import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MQuestion implements QuestionType{
	 private List <String>MQFolder;
	 private int picked;
	 private Random r;
	 
	@Override
	public void pickQuestion() {
		// Multiple-Choice Questions
			MQFolder = new ArrayList<String>();
			r = new Random();
			MQFolder.add("Q1. What year are you in?\nA: Freshman\nB: Sophomore\nC: Junior\nD: Senior");
			MQFolder.add("Q2. What is your favorite computer language?\nA: Java\nB: Python\nC: C++\nD: Pearl");
			MQFolder.add("Q3. What is your favorite pet?\nA: Dog\nB: Cat\nC: Bird\nD: Fish");
		
			picked = r.nextInt(MQFolder.size());
			showQuestion();
	}

	@Override
	public void showQuestion() {
		System.out.println("Multiple-Choice Question selected.");
		System.out.println(MQFolder.get(picked)); 
	}

}
