package data;

import java.util.HashSet;
import java.util.Set;

public class User {
	private String uniqueID;
	private Set<User> followers;
	private Set<User> following;
	private Set<String> newsFeed;
	
	public User(String userID) {
		uniqueID = userID;
		followers = new HashSet<>();
		following = new HashSet<>();
		newsFeed = new HashSet<>();
	}
}
