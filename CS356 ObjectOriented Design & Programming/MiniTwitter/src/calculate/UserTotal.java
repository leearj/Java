package calculate;

import data.User;
import data.UserGroup;

// Count total user Using User parameter.

public class UserTotal implements Visitor {
	private int numOfUser;
	
	public UserTotal(){
		numOfUser = 0;
	}
	
	@Override
	public void visit(User user) {
		numOfUser++;
	}

	@Override
	public void visit(UserGroup usergroup) {/* Not Using */}
	
	public int getNumOfUser() {
		return numOfUser;
	}
}
