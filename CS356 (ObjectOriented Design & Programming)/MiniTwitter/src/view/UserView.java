package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.AbstractListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.LayoutStyle;
import javax.swing.ListModel;

import data.User;

public class UserView {
	
	private User user;
	private JFrame frame2;	// UserView
	private JTree tree; // Both use same JTree tree
	private GroupLayout layout2;
	
	// frame2 Variables declaration                    
    private JButton button_followuser;
    private JButton button_postTweet;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JScrollPane jScrollPane4;
    private JList<String> list_currentfollowing;
    private JList<String> list_newsfeed;
    private JTextArea textarea_textmessage;
    private JTextArea textarea_userid;
    
    UserView(User newUser){	// Gets User from the node we find in GUI.
    	this.user = newUser;
    	frame2 = new JFrame("UserView");
    	frame2.setExtendedState(JFrame.MAXIMIZED_BOTH);
    	frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	layout2 = new GroupLayout(frame2.getContentPane());
    	frame2.getContentPane().setLayout(layout2);
    	
    	showCreationTime();	//A3
    	initComponents();
		initPostTweet();	//A3
    	initFollowUser();	//A3
    }

	private void initComponents() {
		
		// frame2
        jScrollPane1 = new JScrollPane();
    	textarea_userid = new JTextArea();
    	button_followuser = new JButton();
    	jScrollPane2 = new JScrollPane();
    	list_currentfollowing = new JList<>();
    	jScrollPane3 = new JScrollPane();
    	textarea_textmessage = new JTextArea();
    	button_postTweet = new JButton();
    	jScrollPane4 = new JScrollPane();
    	list_newsfeed = new JList<>();
    	
    	textarea_userid.setColumns(20);
        textarea_userid.setRows(5);
        textarea_userid.setText("This is user id\n");
        jScrollPane1.setViewportView(textarea_userid);
        button_followuser.setText("Follow User");
        
        list_currentfollowing.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(list_currentfollowing);

        textarea_textmessage.setColumns(20);
        textarea_textmessage.setRows(5);
        textarea_textmessage.setText("This is tweet message\n");
        jScrollPane3.setViewportView(textarea_textmessage);

        button_postTweet = new JButton("Post Tweet");
        button_postTweet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                postTweetActionPerformed(evt);
            }
        });

        list_newsfeed.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(list_newsfeed);

        layout2.setHorizontalGroup(
            layout2.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout2.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout2.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4, GroupLayout.PREFERRED_SIZE, 530, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout2.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(GroupLayout.Alignment.LEADING, layout2.createSequentialGroup()
                            .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(button_followuser, GroupLayout.PREFERRED_SIZE, 301, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout2.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addGroup(GroupLayout.Alignment.TRAILING, layout2.createSequentialGroup()
                                .addComponent(jScrollPane3)
                                .addGap(18, 18, 18)
                                .addComponent(button_postTweet, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 530, GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        layout2.setVerticalGroup(
            layout2.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout2.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout2.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(button_followuser, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout2.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3)
                    .addComponent(button_postTweet, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
	}
	
	// A3
	
	private void showCreationTime() {
		Date date = new Date(user.showCreationTime());
		System.out.println("Creation time: " + date);
	}
	
	private void initPostTweet() {
		button_postTweet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				postTweetActionPerformed(e);
			}
		});
	}
	
	private void initFollowUser() {
		button_followuser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				followUserActionPerformed(e);				
			}			
		});
	}
	
	@SuppressWarnings("unchecked")
	protected void postTweetActionPerformed(ActionEvent e) {
		user.postTweet(textarea_textmessage.getText());
		textarea_textmessage.setText("");
		list_newsfeed.setModel((ListModel<String>) user.getNewsfeeds());
	}
	
	protected void followUserActionPerformed(ActionEvent e) {
		user.addObserver(new User(textarea_userid.getText()));
		textarea_userid.setText("");
	}
}
