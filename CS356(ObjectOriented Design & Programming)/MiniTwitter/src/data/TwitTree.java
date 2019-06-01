// Visitable interface: User, UserGroup needs to implement this.
// Composite class

package data;

import javax.swing.tree.TreeNode;

import calculate.Visitor;

public interface TwitTree extends TreeNode{
	void accept(Visitor user);
	User getUser(String user);
	boolean contains(Visitor user);
	long showCreationTime();
}
