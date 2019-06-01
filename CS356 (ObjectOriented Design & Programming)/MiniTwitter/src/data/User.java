package data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Set;

import javax.swing.tree.TreeNode;

import calculate.Visitor;

public class User extends Observable implements Observer, TwitTree{	// Observable- this class is observed.
	private String id;
	private Set<User> followers;	// User IDs that are following this user
	private Set<User> followings;	// User IDs being followed by this user
	private List<String> newsfeeds;	// ArrayList so I can access by index.
	private long creationTime;		// A3
	private long lastUpdateTime;	// A3
	
	public User(String userID) {
		id = userID;
		followers = new HashSet<>();	// HashSet for Name&ID(No duplicate)
		followings = new HashSet<>();
		newsfeeds = new ArrayList<>();
		creationTime = System.currentTimeMillis();
		lastUpdateTime = 0;
	}

	// addObserver(): Handles adding followers, followings from both side.
	public void addObserver(User newObserver) { 
		// For this User, add newUser to followers.
		followers.add(newObserver);
		// For newObserver, he or she is following this user.
		newObserver.followings.add(this);
	}
	
	void addnewsfeed(String newMsg) {
		newsfeeds.add(newMsg);
	}	

	public String getId() {
		return id;
	}
	public Set<User> getFollowers() {
		return followers;
	}
	public Set<User> getFollowing() {
		return followings;
	}

	public List<String> getNewsfeeds(){
		return newsfeeds;
	}
	
	@Override
	public User getUser(String user) {
		if (id.equals(user))
			return this;
		
		return null;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return null;
	}

	@Override
	public int getChildCount() {
		return 0;
	}

	@Override
	public TreeNode getParent() {
		return null;
	}

	@Override
	public int getIndex(TreeNode node) {
		return 0;
	}

	@Override
	public boolean getAllowsChildren() {
		return false;
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public Enumeration<?> children() {
		return null;
	}

	@Override
	public void update(Observable o, Object arg) {
		// Notify the change of state
		if (arg instanceof String) {
			System.out.println("update called");
			newsfeeds.add(0, (String) arg);
		}
	}

	@Override
	public boolean contains(Visitor user) {
		if (user instanceof User)
			return this.toString().equals(user.toString());
		
		return false;
	}

	
	
	@Override
	public long showCreationTime() {
		return creationTime;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}
	
	public long getCreationTime() {
		return creationTime;
	}
	
	public void postTweet(String tweet) {
		lastUpdateTime = System.currentTimeMillis();
		Date date = new Date(lastUpdateTime);
		System.out.println(date + ": " + tweet);
		newsfeeds.add(tweet);
		setChanged();
		notifyObservers(tweet);
		System.out.println("Users notified.");
	}
}