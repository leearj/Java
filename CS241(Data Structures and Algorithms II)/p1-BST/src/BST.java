public class BST {
	private BSTNode root;
	private BSTNode parent;

	BST() {
		root = null;
		parent = null;
	}

	/** Function Methods **/

	public BSTNode getRoot() {
		return root;
	}

	// Find node - Assuming no duplicates
	// using root as initial value for n in the main method.
	// Additionally, storing parent of current node as well.
	public BSTNode findNode(BSTNode n, int elem) {
		// if elem we are looking for is root,return
		if (root.data == elem)
			return root;
		else {
			// if current data is greater than elem,go left
			if (n.data > elem && n.left != null){
				n.parent = parent;
				findNode(n.left, elem);
			}
			
			// else if current data is less than elem, go right
			if (n.data < elem && n.right != null){
				findNode(n.right, elem);
				n.parent = parent;
			}	
			if (n.data == elem)
				return n;
		}
		return n;
	}

	// Insert the node.
	public BSTNode insertNode(BSTNode n, int data) {
		// case: root is null.
		if (root == null) {
			root = new BSTNode(data, null, null);
			return root;
		}

		// if data is less than current data,
		if (data < n.data) {
			// recursive call - go to left until reaches null
			if (n.left != null){
				n.parent = n;
				insertNode(n.left, data);
			}
			// and then create a new node.
			else
				n.left = new BSTNode(data);
		}

		// else if data is larger than current data,
		else if (data > n.data) {
			// recursive call - go to left until reaches null
			if (n.right != null){
				n.parent = n;
				insertNode(n.right, data);
			}
			// and then create a new node.
			else
				n.right = new BSTNode(data);
		}

		// else if data already exist.
		else if (data == n.data)
			System.out.println(data + " already exists, ignore.");

		return n;
	}

	// Using recursion to perform deleteNode.
	// Using Successor to replace the data
	// Make a right first, and then go left until reaches null.
	public void deleteNode(BSTNode n, int data){
	try{
		
		// if root is null, return null.
		if (root == null)
			System.out.println(data + " doesn't exist!");
		else{
		BSTNode nodeToBeDeleted;
		BSTNode nodeToBeReplaced;
		BSTNode tmp, tmp2, tmp3;

		// find nodeToBeDeleted using recursion.
		if (n.data < data && n.left != null)
			deleteNode(n.left, data);
		else if (n.data > data && n.right != null)
			deleteNode(n.right, data);
		nodeToBeDeleted = n;

		//Use recursive method: getSuccessorNode(), to find the given node's
		//Succssor for the sake of simplicity, and efficiency.
		nodeToBeReplaced = getSuccessorNode(n);
		
		
		// case1a: leaf case(It is the parent's left-child leaf node)
		if (nodeToBeDeleted == n && nodeToBeDeleted.left != null
				&& nodeToBeDeleted.right != null)
			parent.left = null;

		// case1b: leaf case(It is the parent's right-child leaf node)
		else if (nodeToBeDeleted.left != null
				&& nodeToBeDeleted.right != null)
			parent.right = null;

		// case2a: has one left-child
		else if (nodeToBeDeleted.left != null && nodeToBeDeleted.right == null) {
			tmp = nodeToBeDeleted.left; //1
			tmp2 = nodeToBeDeleted.parent; //3
			nodeToBeDeleted.parent = null;
			tmp2.left = tmp;
		}

		// case2b: has one right-child
		else if (nodeToBeDeleted.right != null && nodeToBeDeleted.left == null) {
			tmp = nodeToBeDeleted.right;
			tmp2 = nodeToBeDeleted.parent;
			nodeToBeDeleted.parent.right = null;
			tmp2.right = tmp;
		}

		// case3a: has two children(the node is left-child of its parent.
		else if (nodeToBeDeleted.left != null
				&& nodeToBeDeleted.right != null) {
			// Faster to understand by drawing..
			tmp = nodeToBeDeleted.left;
			tmp2 = nodeToBeDeleted.parent;
			nodeToBeDeleted.parent.left = null;
			tmp2.left = nodeToBeReplaced;
			nodeToBeReplaced.left = tmp;
		}

		// case3b: has two children(the node is right-child of its parent.
		else if (nodeToBeDeleted.left != null
				&& nodeToBeDeleted.right != null) {
			// Faster to understand by drawing..
			tmp = nodeToBeDeleted.left;
			tmp2 = nodeToBeDeleted.parent;
			nodeToBeDeleted.parent.right = null;
			tmp2.right = nodeToBeReplaced;
			nodeToBeReplaced.left = tmp;

		}
		//Prints out appropriate error msg when the node not found.
		else 
			System.out.println(n.data + " doesn't exist!");
		}
	}catch(NullPointerException e){
		System.out.println("NullPointerExcpetion occured.");
		
	}
	}

	// Perform pre-order, in-order, post-order traversal
	public void printTraverse() {
		if (getRoot() != null) {
			BSTNode nodeToTraverse = getRoot();
			if (nodeToTraverse.left == null && nodeToTraverse.right == null)
				System.out.println(nodeToTraverse.data);
			else {
				System.out.print("Pre-order: ");
				inOrderTraverse(nodeToTraverse);
				System.out.println();

				System.out.print("In-order: ");
				preOrderTraverse(nodeToTraverse);
				System.out.println();

				System.out.print("Post-order: ");
				postOrderTraverse(nodeToTraverse);
				System.out.println();
			}
			
		}
	}

	

	// In-Order Traverse 1. Aim for the smallest elem first(leftmostChild in
	// BST) 2. Start at 1st left child 3. When null is reached then move up in
	// elem. leftmostChild(left tree) -> root -> right tree
	public void inOrderTraverse(BSTNode current) {
		if (current != null) {
			inOrderTraverse(current.left);
			System.out.print(current.data + " ");
			inOrderTraverse(current.right);
		}
	}

	// Pre-Order Traverse
	// root -> leftmostChild -> its parent -> right -> root->right
	public void preOrderTraverse(BSTNode current) {
		if (current != null) {
			preOrderTraverse(current.left);
			System.out.print(current.data + " ");
			preOrderTraverse(current.right);
		}
	}

	// Post-Order Traverse
	// left tree -> right tree -> root node
	public void postOrderTraverse(BSTNode current) {
		if (current != null) {
			postOrderTraverse(current.left);
			postOrderTraverse(current.right);
			System.out.print(current.data + " ");
		}
	}

	// Predecessor - Go left first from current and then go right until reaches
	// null
	public int getPredecessor(BSTNode n) {

		// First, if left-child of current node exist, move to left.
		if (n.left != null)
			n = n.left;

		// From now on, go right until reaches to null
		while (n.right != null)
			n = n.right;

		return n.data;
	}

	// Successor - Go left first from current and then go left until reaches
	// null. Return "Successor data"
	public int getSuccessor(BSTNode n) {

		// First, if right-child of current node exist, move to right.
		if (n.right != null)
			n = n.right;

		// From now on, go left until reaches to null
		while (n.left != null)
			n = n.left;

		return n.data;
	}

	// getSuccessor method - return "Successor Node"
	// Used in delete method.
	public BSTNode getSuccessorNode(BSTNode n) {

		// First, if right-child of current node exist, move to right.
		if (n.right != null)
			n = n.right;

		// From now on, go left until reaches to null
		while (n.left != null)
			n = n.left;

		return n;
	}

	/** Nested BST Node class **/
	public class BSTNode {
		public int data;
		public BSTNode left;
		public BSTNode right;
		public BSTNode parent;

		// default constructor
		public BSTNode() {
			left = null;
			right = null;
			parent = null;
		}

		// constructor with args1
		public BSTNode(int data) {
			this.data = data;
			left = null;
			right = null;
			parent = null;
		}

		// constructor with args2
		public BSTNode(int data, BSTNode left, BSTNode right) {
			this.data = data;
			this.left = left;
			this.right = right;
		}
		// constructor with args3
		public BSTNode(int data, BSTNode left, BSTNode right, BSTNode parent){
			this.data = data;
			this.left = left;
			this.right = right;
			this.parent = parent;
		}
	}
}
