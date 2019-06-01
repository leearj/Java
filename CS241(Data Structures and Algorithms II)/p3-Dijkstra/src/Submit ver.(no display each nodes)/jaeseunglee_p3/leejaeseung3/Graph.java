//Each graph will only represent the graph that has ONE starting point.

import java.util.ArrayList;

public class Graph {
	ArrayList<Node> nodes = new ArrayList<Node>();
	ArrayList<Integer> visitList = new ArrayList<Integer>();
	ArrayList<Integer> bestDist = new ArrayList<Integer>(); // best Distance from starting index.
	ArrayList<ArrayList> bestPath = new ArrayList<ArrayList>(); //best path from starting index.
	ArrayList<Integer> road = new ArrayList<Integer>();
	Integer num = new Integer(0); //element for road.
	
	//total distance cost and path until get to the current index in Dijkstra method.
	ArrayList<Integer> traceDist = new ArrayList<Integer>();
	ArrayList<Integer> tracePath = new ArrayList<Integer>();
	
	//helper variables for Dijkstra method.
	int startCityIndex; //EDIT THIS LATER: assigned by user.
	int costToGetHere = 0;
	
	/** Graph constructor: mainly do initialization **/
	public Graph(int start) {
		startCityIndex = start;
		
		// create 20 nodes in the array, nodes.
		// Example : Index of nodes[0] will be 1(cityIndex)
		for (int i = 0; i < 20; i++)
			this.nodes.add(new Node());

		// Initialize visitList with 1-20.
		for (int i = 0; i < 20; i++)
			visitList.add(i+1);
		
		// Initialize bestDist with infinity.
		// But distance to itself is 0.
		for(int i=0;i<20;i++){
			if(startCityIndex == (i+1))
				bestDist.add(0);
			else
			bestDist.add(Integer.MAX_VALUE);	
		}
		
		
		
		// Initialize 2dArrayList bestPath.
		for(int i=0;i<20;i++){
			bestPath.add(new ArrayList<Character>());
			
			if(startCityIndex == (i+1)){
				bestPath.get(i).add(startCityIndex);
				bestPath.get(i).add(startCityIndex);
				}
			else
			bestPath.get(i).add(' ');
				
		}
		
		// Initialize the road with element: num.
		for(int i=0;i<20;i++)
			road.add(num);
		
		// add starting point to traceDist, tracePath
		traceDist.add(0);
		tracePath.add(startCityIndex);
		
	}
	
	
	/** Dijkstra method: Calculate the shortest distance **/
	public void Dijkstra(int currentCityIndex) {
	
		// while visitList isn't empty and visitList 
		while (visitList.size() != 0) {
			
			//temporary variables for convenience
			int currentNeighborIndex=-1;
			int currentNeighborDist;
			int nextIndex = -1; //which node is next?
			int nextDist = Integer.MAX_VALUE; //how much it cost to get to next?

			//Find the current one and remove it from visitList.
			for(int i=0;i<visitList.size();i++)
				if(visitList.get(i) == (currentCityIndex))
					visitList.remove(i);
			
			//For neighbor exist case, figure out if there is still unvisited node in visitList.
			boolean areUnvisited= false;
			if(nodes.get(currentCityIndex-1).neighbor.size() != 0){
			for(int i=0;i<nodes.get(currentCityIndex-1).neighbor.size();i++)
				if(visitList.contains(nodes.get(currentCityIndex-1).neighbor.get(i)))
					areUnvisited=true;
			}
			
			//If there are neighbors for current node and there are still unvisited neighbors.
			if(nodes.get(currentCityIndex-1).neighbor.size()!=0 && areUnvisited){
				
				//The current node's neighbor.
				for (int i = 0; i < nodes.get(currentCityIndex - 1).neighbor.size(); i++){
					currentNeighborIndex = nodes.get(currentCityIndex-1).neighbor.get(i); //19,4,2
					currentNeighborDist =  nodes.get(currentCityIndex-1).distance.get(i); //36,212,732
					
					//Find out which path has minimum cost (Where to go next?)
					//*the one that isn't in the visitList should be excluded.
					if(currentNeighborDist < nextDist && visitList.contains(currentNeighborIndex)){
						nextDist = currentNeighborDist;
						nextIndex = currentNeighborIndex;
					}
					
					//If better distance found
					if(bestDist.get(currentNeighborIndex-1) > (costToGetHere + currentNeighborDist)){
						//set it to the bestDist.
						bestDist.set(currentNeighborIndex-1, costToGetHere+currentNeighborDist);
						
						//1. wipe the original path and update it.
						bestPath.get(currentNeighborIndex-1).clear();
						
						//2. fill it with the path to get here.
						for(int k=0;k<tracePath.size();k++){
							//tracePath - the path to get here.
							bestPath.get(currentNeighborIndex-1).add(tracePath.get(k));
						}
						//3. lastly, add the currentNeighborIndex!
						bestPath.get(currentNeighborIndex-1).add(currentNeighborIndex);
					}
					
									}
			}
			
			
			//Update it for next iteration.
			if(nextDist != Integer.MAX_VALUE && visitList.contains(nextIndex) && nextIndex != -1){
				
				traceDist.add(nextDist);
				tracePath.add(nextIndex);
				costToGetHere += traceDist.get(traceDist.size()-1);
			
			//Recursively go to the next index which has shortest edge.
			Dijkstra(nextIndex);
			}
			
			//else if there is no neighbors or nextIndex isn't in visitList
			else if(nodes.get(currentCityIndex-1).neighbor.size() == 0 || !(visitList.contains(nextIndex))){
				
				
				
				
				//if it is last node case, break!
				if(visitList.size() == 0)
					break;
				
				
				//which one has lowest distance?
				int nxtIndex = -1;
				int nxtIndexDist=Integer.MAX_VALUE ;
				for(int i=0;i<visitList.size();i++){
					if(bestDist.get(visitList.get(i)-1) < nxtIndexDist){
						nxtIndexDist = bestDist.get(visitList.get(i)-1);
						nxtIndex = visitList.get(i);
					}
				}
				
				//Exception case:startCity doesn't have any neighbors!
				if(nodes.get(startCityIndex-1).neighbor.size() == 0 && nxtIndex == -1)
				break;
				
				
				
				//tracePath should be now [1,19,10], which is bestPath.get(nxtIndex-1)
				tracePath.clear();
				tracePath.addAll(0,bestPath.get(nxtIndex-1));
				
				
				
				//traceDist should be now [0,36,111]=147 for [1,19,10]
				traceDist.clear();
				traceDist.add(0);
				costToGetHere = bestDist.get(nxtIndex-1) ;
				
				//use nodes.neighbor and nodes.distance to get distances.
				for(int i=0;i<tracePath.size()-1;i++){ //tracePath.size() will be 3.
							
					int source= tracePath.get(i); //will be 1, 19
					int target= tracePath.get(i+1); //will be 19, 10.
					int shortest=-1;
					
					//Find the neighbor that has shortest. 19,4,2 
					for(int j=0;j<nodes.get(source-1).neighbor.size();j++)
						//if(nodes.get(1-1).neighbor.get(0) == 19
						if(nodes.get(source-1).neighbor.get(j) == target){
							shortest= nodes.get(source-1).distance.get(j); //36
					if(shortest != -1)
							traceDist.add(shortest);}
					
				}
				
				//which index of visitList has nextIndex?
				int found=-1;
				for(int i=0;i<visitList.size();i++)
					if(visitList.get(i) == nxtIndex)
						found = i;
				
				Dijkstra(visitList.get(found));	
			}
			
		} // while loop ends

	}// Dijkstra method ends.

}// end of Graph