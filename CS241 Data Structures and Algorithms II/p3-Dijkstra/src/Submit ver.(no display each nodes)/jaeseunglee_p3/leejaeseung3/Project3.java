//Lee Jaeseung
//CS241
//GPS using Dijkstra's Algorithm.
//Using index 1~20.


import java.io.IOException;

public class Project3{
	public static void main(String[] args) throws IOException {
		
		//Generate 20 graphs with startIndex 1-20.
		Graph[] graphs = new Graph[20]; 
		for(int i=0;i<20;i++){
			Graph g = new Graph(i+1);
			FileHandler fh = new FileHandler();
			fh.handleFile();
			fh.storeEdges(g);
			g.Dijkstra(g.startCityIndex);
			graphs[i] = g;
		}
		
		Interface start = new Interface();
		FileHandler fh = new FileHandler();
		start.prompt(graphs);
	}
}
