package calculate;

import java.util.ArrayList;
import java.util.List;

import data.User;
import data.UserGroup;

// Calculate for Positive Percentage of feedbacks.

public class PosPercTotal implements Visitor {

	private List<String> feedback;
	
	public PosPercTotal(){
		feedback = new ArrayList<>();
	}
	
	@Override
	public void visit(User user) {
		for(int i=0;i<user.getNewsfeeds().size();i++)
			feedback.add(user.getNewsfeeds().get(i));
	}

	@Override
	public void visit(UserGroup usergroup) {	/* Not Using*/	}
}
