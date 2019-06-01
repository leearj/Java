// Visitor interface: implementing this interface allows the class to handle data of
// User, UserGroup class < - > Classes in calculate-package.

package calculate;

import data.User;
import data.UserGroup;

public interface Visitor {
	public void visit(User user);
	public void visit(UserGroup usergroup);
}
