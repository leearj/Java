import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SQuestion implements QuestionType {
	 private List <String>SQFolder;
	 private int picked;
	 private Random r;
	 
	// Single-Choice Questions
		public void pickQuestion() {
			SQFolder = new ArrayList<String>();
			r = new Random();
			SQFolder.add("Q1. Trump is a good president.\n1. Right\n2. Wrong");
			SQFolder.add("Q2. Macbook is better than other laptops.\n1. Right\n2. Wrong");
			SQFolder.add("Q3. North Korea would always threaten US but never attack.\n1. Right\n2. Wrong");
			
			picked = r.nextInt(SQFolder.size());
			showQuestion();
		}

		@Override
		public void showQuestion() {
			System.out.println("Single-Choice Question selected.");
			System.out.println(SQFolder.get(picked));
		}
}
