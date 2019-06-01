import java.util.ArrayList;

public class Node {
	ArrayList<Integer> neighbor = new ArrayList<Integer>(); //Store all the neighbor's index.
	ArrayList<Integer> distance = new ArrayList<Integer>(); //Store all distance for neighbors,access it by index
	
	
	/**Example: city1 (index 0) to city3(index 2) distance is 15.
	 * if starting city is city1(assigned by user),and target is city3,
	 * unless there are shorter path, 15 is stored to the current node's shortestDistance.
	 * so nodes[0] to nodes[2] distance is 15. so 15 gets stored in nodes[2].shortestDistance.
	 * if user ask distance from nodes[0] to nodes[0], nodes[0].shortestDistance = 0; 
	 * **/
	
	Node(){
	}
}
