package calculate;

import data.User;
import data.UserGroup;

// Just count how many groups we have

public class GroupTotal implements Visitor{
	private int numOfGroup;
	
	public GroupTotal(){
		numOfGroup = 0;
	}
	
	@Override
	public void visit(User user) {
		numOfGroup++;
	}

	@Override
	public void visit(UserGroup usergroup) {	/* Not Using */	}

	public int getNumOfGroup() {
		return numOfGroup;
	}
}
