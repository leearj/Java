
public class SimulationDriver {
	public static void main(String[] args) {
		/*
		 * STEPS 
		 * 1. Instantiate either single or multiple question class 
		 * 2. pickQuestion() method to randomly pick a question. 
		 * 3. Decides number of students in the Classroom class.
		 * 4. Display all the participants.
		 * 5. Submit the list of students who randomly selected their answers, to iVoteService. 5.
		 *	  countAnswer(), showResult()
		 * 
		 * Note: 
		 * 1]  You can uncomment Single-Choice-Question Case, and comment Multiple-Question Case.
		 * 2]  The way I implemented doesn't require handling multiple submission,
		 *     although in the iVoteService method, I keep track students' # of submit.
		 */

		// Multiple-Question Case
		SQuestion sq = new SQuestion();
		sq.pickQuestion();
		Classroom cr = new Classroom(35, sq);
		cr.showAllStudents();
		iVoteService ivs = new iVoteService(35, sq, cr.randomSubmit());
		ivs.countAnswers();
		ivs.showResult();

		/*
		 * // Single-Choice-Question Case. MQuestion mq = new MQuestion();
		 * mq.pickQuestion(); Classroom cr = new Classroom(27,mq); cr.showAllStudents();
		 * iVoteService ivs = new iVoteService(27,mq,cr.randomSubmit());
		 * ivs.countAnswers(); ivs.showResult();
		 */
	}

}
