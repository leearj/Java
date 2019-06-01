package calculate;

import java.util.ArrayList;
import java.util.List;

import data.User;
import data.UserGroup;

public class MsgTotal implements Visitor {

	private List<String> numOfMsg;

	public MsgTotal() {
		numOfMsg = new ArrayList<>();
	}

	@Override
	public void visit(User user) {
		for(int i=0;i<user.getNewsfeeds().size();i++)
			numOfMsg.add(user.getNewsfeeds().get(i));
	}

	@Override
	public void visit(UserGroup usergroup) {	/* Not Using*/	}

}
