package data;

import java.util.Enumeration;
import java.util.TreeSet;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import calculate.Visitor;

public class UserGroup implements TwitTree{
	private String id;
	private TreeSet<TwitTree> children;
		
	public UserGroup(String groupID){
		this.id = groupID;
		children = new TreeSet<TwitTree>();
	}
	
String getID() {
	return id;
}

User getUser(User user) {
	return user;
}

public void add(TwitTree child) {
	children.add(child);
}

@Override
public void accept(Visitor visitor) {
	visitor.visit(this);
	for(TwitTree twittree: children)
		twittree.accept(visitor);
}

@Override
public TreeNode getChildAt(int childIndex) {return null;}

@Override
public int getChildCount() {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public TreeNode getParent() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public int getIndex(TreeNode node) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public boolean getAllowsChildren() {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean isLeaf() {
	// TODO Auto-generated method stub
	return false;
}

@Override
public Enumeration children() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public User getUser(String user) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public boolean contains(Visitor user) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public long showCreationTime() {
	// TODO Auto-generated method stub
	return 0;
}
}
