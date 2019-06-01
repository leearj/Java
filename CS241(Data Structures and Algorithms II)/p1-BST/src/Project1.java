import java.util.Scanner;

public class Project1 {
	public static void main(String[] args){
		BST tree = new BST();
		Scanner sc = new Scanner(System.in);
		String input, input2;
		String command;
		int givenElement=0;
		
		System.out.println("Please enter the initial sequence of values:");
		input = sc.nextLine();
		String[]s = input.split(" ");
		  for(int i=0; i < s.length; i++)
			  tree.insertNode(tree.getRoot(), Integer.parseInt(s[i]));
		 
		//Display pre-order,in-order,post-order traversals.
		tree.printTraverse();

		//infinite while-loop until user exits.
		while (true) {
			System.out.print("Command? ");
			input2 = sc.nextLine();
			String[] s2 = input2.split(" ");
			command = s2[0]; //String Alphabet command is stored
			//This only applies for "I","D" where input is more than 1.
			if(s2.length > 1)
			givenElement = Integer.parseInt(s2[1]); //Given element is stored.
				
			switch (command) {
			//Insert value
			case "I":
				tree.insertNode(tree.getRoot(),givenElement);
				System.out.print("In-order: ");
				tree.inOrderTraverse(tree.getRoot());
				System.out.println();
				break;
				
			//Delete value	
			case "D":
				tree.deleteNode(tree.getRoot(),givenElement);
				System.out.print("In-order: ");
				tree.inOrderTraverse(tree.getRoot());
				System.out.println();
				break;
				
			//Print found predecessor
			case "P":
				BST.BSTNode found1 = tree.findNode(tree.getRoot(), givenElement);
				System.out.println(tree.getPredecessor(found1));
				break;
				
			//Print found Successor
			case "S":
				BST.BSTNode found2 = tree.findNode(tree.getRoot(),givenElement);
				System.out.println(tree.getSuccessor(found2));
				break;
				
			//Exit the program	
			case "E":
				System.out.println("Thank you for using my program!");
				System.exit(0);
				
			//Display this message
			case "H":
				System.out.print("I  Insert a value\n" + "D  Delete a value\n"
						+ "P  Find predecessor\n" + "S  Find successor\n"
						+ "E  Exit the program\n" + "H  Display this message\n");
				break;
			}
		}
	}

}
