package calculate;

import java.util.HashMap;
import java.util.Map;

import data.User;
import data.UserGroup;

public class ValidateVisitor implements Visitor{

	String word = " ";
    private boolean isValid = true;
    private Map<String, Boolean> visitedUsers;
	
    public ValidateVisitor(){
    	visitedUsers = new HashMap<>();
    }
    
	@Override
	public void visit(User user) {
		if (user.toString().contains(word) || visitedUsers.containsKey(user.toString())) 
			isValid = false;
		
		visitedUsers.put(user.toString(), isValid);
	}

	@Override
	public void visit(UserGroup usergroup) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isValid() {
        if (visitedUsers.containsValue(false))
            return false;
        
        return true;
    }
}
