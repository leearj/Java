package view;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.LayoutStyle;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import calculate.GroupTotal;
import calculate.PosPercTotal;
import calculate.UserTotal;
import calculate.ValidateVisitor;
import calculate.Visitor;
import data.TwitTree;
import data.User;
import data.UserGroup;

import sound.Sound;

public class MainView extends JFrame{
	
	private static MainView mvInstance;
	
	private final Sound s = new Sound();
	private GroupLayout layout1;
	
	// frame1 Variables declaration    
	private JButton button_addUser;
    private JButton button_addGroup;
    private JButton button_openUserView;
    private JButton button_showUserTotal;		// Total 1
    private JButton button_showMsgTotal;		// Total 2
    private JButton button_showGroupTotal;		// Total 3
    private JButton button_showPosPercTotal;	// Total 4
    private JButton button_validateIDs;			// A3: validateIDs
    private JButton button_lastUpdatedUser; 	// A3: lastUpdatedUser
    private JPanel contentPane;
    private JScrollPane pane1_tree;					
    private JScrollPane pane2_userID;					
    private JScrollPane pane3_groupID;	
    private JTextArea textarea_addUser;
    private JTextArea textarea_addGroup;
    private UserGroup root;
    private JTree tree;										
    private DefaultTreeModel defaultTree;
    private Set<User> users;								// Series of User ID's; Hash doesn't allow duplicate
    private Set<UserGroup> groups;								// Series of Group ID's
    
    public MainView() {
    	super("Main Control Panel");
    	users = new HashSet<>();		// All users
    	groups = new HashSet<>();		// All groups
    	s.playBGM(getClass().getResource("/resources/bgm.wav"));
    	
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setBounds(100,100,625,625);
    	contentPane = new JPanel(new GridLayout(2,2));
    	contentPane.setBorder(new EmptyBorder(6,6,6,6));
    	setContentPane(contentPane);
    	
        initComponents();
    }

    private void initComponents() {
    	JFrame frame = new JFrame("Control Panel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
    	root = new UserGroup("root");	// Setup for root.
    	defaultTree = new DefaultTreeModel(root);
    	tree = new JTree(defaultTree);
    	tree.setVisible(true);
    	updateTree();
    	
    	pane1_tree = new JScrollPane(tree);
    	pane2_userID = new JScrollPane();
    	pane3_groupID = new JScrollPane();
        textarea_addUser = new JTextArea();
        textarea_addGroup = new JTextArea();
    
        GroupLayout GLayout = new GroupLayout(contentPane);	/*////////////////////////// */
        pane1_tree.setViewportView(tree);
        
   
        button_addUser = new JButton("Add User");
        button_addUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	addUserActionPerformed(evt);
            }
        });
        
        button_addGroup = new JButton("Add Group");
        button_addGroup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	addGroupActionPerformed(evt);
            }
        });

        button_openUserView = new JButton("Open User View");
        button_openUserView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	openUserViewActionPerformed(evt);
            }
        });

        button_showUserTotal  = new JButton("Show User Total");
        button_showUserTotal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	showUserTotalActionPerformed(evt);
            }
        });
        
        button_showGroupTotal = new JButton("Show Group Total");
        button_showGroupTotal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	showGroupTotalActionPerformed(evt);
            }
        });
        
        button_showMsgTotal = new JButton("Show Message Total");
        button_showMsgTotal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	showMsgTotalActionPerformed(evt);
            }
        });
        
        button_showPosPercTotal = new JButton("Show Positivepercentage");
        button_showPosPercTotal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	showPosPercTotalActionPerformed(evt);
            }
        });
        
        button_validateIDs = new JButton("Validate IDs");
        button_validateIDs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	validateIDsActionPerformed(evt);
            }
        });
        
        button_lastUpdatedUser = new JButton("Show Last Updated User");
		button_lastUpdatedUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				lastUpdatedUserActionPerformed(evt);
			}
		});
        
        textarea_addUser.setColumns(20);
        textarea_addUser.setRows(5);
        pane2_userID.setViewportView(textarea_addUser);

        textarea_addGroup.setColumns(20);
        textarea_addGroup.setRows(5);
        pane3_groupID.setViewportView(textarea_addGroup);
       
        layout1.setHorizontalGroup(
            layout1.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout1.createSequentialGroup()
                .addContainerGap()
                .addComponent(pane1_tree, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout1.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout1.createSequentialGroup()
                        .addGroup(layout1.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                            .addComponent(button_showUserTotal, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(button_showMsgTotal, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                        .addGroup(layout1.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(button_showPosPercTotal, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                            .addComponent(button_showGroupTotal, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(button_openUserView, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pane2_userID, GroupLayout.Alignment.TRAILING)
                    .addComponent(pane3_groupID))
                .addGap(18, 18, 18)
                .addGroup(layout1.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(button_addUser, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                    .addComponent(button_addGroup, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addComponent(contentPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout1.setVerticalGroup(
            layout1.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout1.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout1.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(contentPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout1.createSequentialGroup()
                        .addGroup(layout1.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(button_addUser, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                            .addComponent(pane3_groupID, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addGroup(layout1.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(button_addGroup, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                            .addComponent(pane2_userID, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(button_openUserView, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addGroup(layout1.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(button_showGroupTotal, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(button_showUserTotal, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(28, 28, 28)
                        .addGroup(layout1.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(button_showMsgTotal, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                            .addComponent(button_showPosPercTotal, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)))
                    .addComponent(pane1_tree))
                .addContainerGap())
        );
    }                      

    private void openUserViewActionPerformed(ActionEvent evt) {                                                    
    	TreeNode node = (TreeNode) tree.getLastSelectedPathComponent();
    	if(node instanceof User)
    		new UserView((User) node);
    	else 	// When User doesn't select anything and click the button.
    		JOptionPane.showMessageDialog(null, "You didn't select User.");
    }  
    
    private void validateIDsActionPerformed(ActionEvent evt) {
		ValidateVisitor validVisitor = new ValidateVisitor();
		root.accept(validVisitor);
		if (validVisitor.isValid()) 
			JOptionPane.showMessageDialog(null, "No duplicate IDs");
		else  
			JOptionPane.showMessageDialog(null, "Duplicate IDs or with spaces");
	}

    private void lastUpdatedUserActionPerformed(ActionEvent evt) {
    	long maxTime = 0;
		Map<User, Long> map = new HashMap<>();
		for (User u : users) {
			if (u.getLastUpdateTime() > maxTime) {
				map.clear();
				maxTime = u.getLastUpdateTime();
				map.put(u, u.getLastUpdateTime());
			}
		}
		
		if (map.isEmpty()) 
			JOptionPane.showMessageDialog(null, "Last update unknown");
		 else 
			// get the first User with the latest maxTime value
			JOptionPane.showMessageDialog(null, map.keySet().stream().findFirst().get());
    }
    
    
    @SuppressWarnings("unlikely-arg-type")
	private void addUserActionPerformed(ActionEvent evt) {                                                
   	 TreeNode node = (TreeNode) tree.getLastSelectedPathComponent();
   	
   	// Case1: No String found.
   	 if (textarea_addUser.getText().equals("")) 
   			JOptionPane.showMessageDialog(null, "Eenter a user ID to add");
   	 
   	 // Case2: Group not selected.
   	 else if(node == null)
   		 JOptionPane.showMessageDialog(null, "Select a group to add user.");
   	 
   	 // Case3: If the User ID exist in users(all the current existing users)
   	 else if(users.contains(textarea_addUser.getText()))
   		 JOptionPane.showMessageDialog(null, "User already exists");
   	 
   	 // Adding User
   	 else {
   		 if(node instanceof UserGroup) {
   			 User newUser = new User(textarea_addUser.getText());
   			 users.add(newUser);
   			 ((UserGroup) node).add(newUser);
   			 updateTree();
   			textarea_addUser.setText(""); // emptying text
   		 }
   		 else
   			JOptionPane.showMessageDialog(null, "Select a group to add user");
   	 }
       }
    
    private void addGroupActionPerformed(ActionEvent evt) {
    	
    	// Case1: No String found.
    	if (textarea_addGroup.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Enter a group to add");
		
		// Case2: Group not selected.
    	} else {
			TreeNode node = (TreeNode) tree.getLastSelectedPathComponent();
			if (node == null) {
				root.add(new UserGroup(textarea_addGroup.getText()));	// Create New UserGroup
		
		// Case3: If the Group ID exist in groups(all the current existing groups)		
			} else if (groups.contains(textarea_addGroup.getText())) {
				JOptionPane.showMessageDialog(null, "Group ID already exists");
			
			} else {
				((UserGroup) node).add(new UserGroup(textarea_addGroup.getText()));
				groups.add(textarea_addGroup.getText());
				updateTree();
				textarea_addGroup.setText("");	// Resets the text
			}
		}
    }
   
    private void showUserTotalActionPerformed(ActionEvent evt) {                                                
    	// Show User Total through JOptionPane.
    	
    	TreeNode cur = (TreeNode) tree.getLastSelectedPathComponent();
    	if( cur == null)
    		JOptionPane.showMessageDialog(null, "No Users found.");
    	else {
    		Visitor visitor = new UserTotal();
			((TwitTree) cur).accept(visitor);
			int uTotal = ((UserTotal) visitor).getNumOfUser();
			JOptionPane.showMessageDialog(null, uTotal);
    	}
    }                                                                                             

    private void showGroupTotalActionPerformed(ActionEvent evt) {                                                 
    	// Show User GroupTotal through JOptionPane.
    	
    	TreeNode cur = (TreeNode) tree.getLastSelectedPathComponent();
    	if( cur == null)
    		JOptionPane.showMessageDialog(null, "No Groups found.");
    	else {
    		Visitor visitor = new GroupTotal();
			((TwitTree) cur).accept(visitor);
			int gTotal = ((UserTotal) visitor).getNumOfUser();
			JOptionPane.showMessageDialog(null, gTotal);
    	}
    }        
    
    private void showMsgTotalActionPerformed(ActionEvent evt) {
    	// Show Message Total through JOptionPane.
    	
    	TreeNode cur = (TreeNode) tree.getLastSelectedPathComponent();
    	if( cur == null)
    		JOptionPane.showMessageDialog(null, "No Messages found.");
    	else {
    		Visitor visitor = new UserTotal();
			((TwitTree) cur).accept(visitor);
			int uTotal = ((UserTotal) visitor).getNumOfUser();
			JOptionPane.showMessageDialog(null, uTotal);
    	}
    }
    
    private void showPosPercTotalActionPerformed(ActionEvent evt) {
    	// Show Positive-Percentage Total through JOptionPane.
    	
    	TreeNode cur = (TreeNode) tree.getLastSelectedPathComponent();
    	if( cur == null)
    		JOptionPane.showMessageDialog(null, "No Messages found.");
    	else {
    		Visitor visitor = new PosPercTotal();
			((TwitTree) cur).accept(visitor);
			int pTotal = ((UserTotal) visitor).getNumOfUser();
			JOptionPane.showMessageDialog(null, pTotal);
    	}
    }
     
    
    private void updateTree(){
		defaultTree.reload(root);
		for(int i=0;i<tree.getRowCount();i++)
			tree.expandRow(i);
	}
 // Implementing Singleton
	public static MainView getInstance() {
		if (mvInstance == null){ //if there is no instance available... create new one
            mvInstance = new MainView();
        }
        return mvInstance;
	}
}