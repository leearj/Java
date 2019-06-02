import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
	
	String[][] cityArray = new String[20][4]; // store info separately to use.
	int[][] roadArray = new int[77][3]; // There are 77 edges from text file.
										// But also need to consider additional
										// edges that user will put

	/***************** handleFile method: properly storing info after read files. **********************/
	public void handleFile() throws FileNotFoundException {
		File file1 = new File("city.dat");
		File file2 = new File("road.dat");
		Scanner scData = new Scanner(file1); // Scanner to store city.dat
		Scanner scData2 = new Scanner(file1); // secondary scanner for city.dat
		Scanner scRoad = new Scanner(file2); // third scanner for road.dat

		// cityArray set-up
		for (int i = 0; i < 20; i++) {
			if (scData.hasNextLine()) {

				// [i][0] = 4
				cityArray[i][0] = scData.next();
				// System.out.println("[" + i + "][0] :" + cityArray[i][0]);

				// [i][1] = BR
				cityArray[i][1] = scData.next();
				// System.out.println("[" + i + "][1] :" + cityArray[i][1]);

				// [i][2] = BREA CANYON (Need to extract)
				String s = scData.nextLine();
				String s2 = "";
				char c[] = new char[s.length()];
				// only store until hit the number.
				for (int k = 0; k < c.length; k++) {
					if (Character.isDigit(s.charAt(k)) == false)
						c[k] = s.charAt(k);
				}
				// store it to s2 and trim, and to the array.
				for (int l = 0; l < c.length; l++)
					s2 += c[l];
				cityArray[i][2] = s2.trim();

				// [i][3] = 4 BR BREA CANYON 5290.. 1242
				cityArray[i][3] = scData2.nextLine();
			}
		}

		// Storing road.dat into 2d array, "roadArray"
		// eg:first roadArray[i][0] = 1, roadArray[i][1] = 19, roadArray[i][2] =
		// 36
		for (int i = 0; i < roadArray.length; i++) {
			for (int j = 0; j < roadArray[i].length; j++) {
				if (scRoad.hasNextLine())
					roadArray[i][j] = scRoad.nextInt();
			}
		}
		
	}// handleFile method ends.

	/******** storeEdges method: Store edges to the graph. ***********************************************/
	public void storeEdges(Graph g) {
					// Store it to the array of nodes in the class, Graph.
			for (int i = 0; i < roadArray.length; i++) {
				
				// loop for the array of nodes in the graph class.
				int tmp1 = roadArray[i][0]; // 1, index (use it to locate nodes)
				int tmp2 = roadArray[i][1]; // 19 , target
				int tmp3 = roadArray[i][2]; // 36 , distance
				
				g.nodes.get(tmp1-1).neighbor.add(tmp2);  // store target index.
				g.nodes.get(tmp1-1).distance.add(tmp3); // store distance.
	
		}
	}
	
	/** showRoadArray method: helper method **/
	public void showRoadArray(){
		System.out.println("Showing you road array.");
		for(int i=0;i<roadArray.length;i++){
			for(int j=0;j<roadArray[i].length;j++){
				System.out.print(roadArray[i][j]+ " ");
			}
			System.out.println();
		}
	}
	
	/** showCityArray method: helper method **/
	public void showCityArray(){
		System.out.println("Showing you city array.");
		for(int i=0;i<cityArray.length;i++){
			for(int j=0;j<cityArray[i].length;j++){
				System.out.println(cityArray[i][j]+" ");
			}
			System.out.println();
		}
	}
	
}

