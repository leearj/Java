//Interface class: handle the prompting.

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Interface{
	
	
	
	public void prompt(Graph[] graphs) throws FileNotFoundException {		
		
		//default dummy graph just for Query.
		FileHandler fh = new FileHandler();
		Graph g = new Graph(1);
		fh.handleFile();
		fh.storeEdges(g);
		
		Scanner kb = new Scanner(System.in);

		while (true) {

			System.out.print("Command? ");
			String cmd = kb.nextLine();

			switch (cmd) {

			// case H: Prints out options.
			case "H":
				System.out.println(" Q  Query the city information by entering the city code.\n"
						+ " D  Find the minimum distance between two cities.\n"
						+ " I  Insert a road by entering two city codes and distance.\n"
						+ " R  Remove an existing road by entering two city codes.\n" + " H  Display this message.\n"
						+ " E  Exit.");
				break;

			// case Q: Prints out full info.
			case "Q":
				boolean wasFound = false;
				System.out.print("City code: ");
				String cityCode = kb.nextLine();
				for (int i = 0; i < 20; i++) {
					// cityArray[i][1]indicates city name(2 letters)
					if (fh.cityArray[i][1].equals(cityCode)) {
						wasFound = true;
						System.out.println(fh.cityArray[i][3]);
					}
				}
				// If wasn't found, print out error message.
				if (wasFound == false)
					System.out.println("Invalid City Code.");

				break;

			// case D: Find minimum distance. eg input: CH PM(CH to PM)
			case "D":

				System.out.println("City codes: ");
				String input = kb.nextLine();
				String[] str = input.split(" ");
				int validChk = 0; // should be 2 to be valid.
				String fName1 = "", fName2 = "";
				int cityIndex1=-1;
				int cityIndex2=-1;
				ArrayList<String> bestPath = new ArrayList<String>();
				int minDist=-1;

				// use cityArray to find the index.
				// Example: when i = 4,
				// cityArray[i][0] = 4
				// cityArray[i][1] = BR
				// cityArray[i][2] = BREA CANYON
				// cityArray[i][3] = 4 BR BREA CANYON 5290.. 1242 (full info)

				// find two letters of cityName in cityArray[i][1]
				for (int i = 0; i < 20; i++) {
					if (str[0].equals(fh.cityArray[i][1])) {
						cityIndex1 = Integer.parseInt(fh.cityArray[i][0]);
						fName1 = fh.cityArray[i][2];
						validChk++;
					} else if (str[1].equals(fh.cityArray[i][1])) {
						cityIndex2 = Integer.parseInt(fh.cityArray[i][0]);
						fName2 = fh.cityArray[i][2];
						validChk++;
					}
				}
				// invalid input case
				if (validChk != 2)
					System.out.println("Invalid City Code!!!");

				// if valid cities
				else if (validChk == 2) {

					minDist = graphs[cityIndex1 - 1].bestDist.get(cityIndex2 - 1);

					// translate the bestPath from integer to String.
					for (int i = 0; i < graphs[cityIndex1 - 1].bestPath.get(cityIndex2 - 1).size(); i++) {

						// scan cityArray[j][2] and find String for each one.
						for (int j = 0; j < 20; j++) {
							if ((Integer) graphs[cityIndex1 - 1].bestPath.get(cityIndex2 - 1).get(i) == Integer.parseInt(fh.cityArray[j][0]))
								bestPath.add(fh.cityArray[j][2]);
						}
					}
					
					//if distance is infinity, say the path doesn't exist.
					if(minDist == Integer.MAX_VALUE)
						System.out.printf("The path from %s to %s doesn't exist.\n",fName1,fName2);
					else
						System.out.printf("The minimum distance between %s and %s is %d through the route: %s.\n", fName1,
							fName2, minDist, bestPath);
				}
				break;

			// case I: insert a road if given cities are valid.
			case "I":

				// using try-catch for possible no 3rd input.
				try {
					String insert="";
					String fName01 = "", fName02 = "";
					int cityIndex01=-1, cityIndex02=-1;
					int validChecker = 0; // 0,1 =invalid, 2=valid
					String[] tmp = new String[3];
					// input example: BR CH 300
					System.out.print("City codes and distance: ");
					insert = kb.nextLine();
					tmp = new String[3];
					tmp = insert.split(" ");

					// Search two-letters cityName in cityArray ,if found, store
					// it to fName.

					// for given city1 and city2
					for (int i = 0; i < 20; i++) {
						
						if (tmp[0].equals(fh.cityArray[i][1])) {
							fName01 = fh.cityArray[i][2];
							cityIndex01 = Integer.parseInt(fh.cityArray[i][0]);
							validChecker++;
							
						} else if (tmp[1].equals(fh.cityArray[i][1])) {
							fName02 = fh.cityArray[i][2];
							cityIndex02 = Integer.parseInt(fh.cityArray[i][0]);
							validChecker++;
						}
					}

					// if invalid,prints out error message.
					if (validChecker != 2)
						System.out.println("Invalid City Code!!!");

					else if(Integer.parseInt(tmp[2]) == 0)
						throw new ArrayIndexOutOfBoundsException();
					
					else if (validChecker == 2) {

						// Replace the bestDist with user given value.
						graphs[cityIndex01 - 1].road.set(cityIndex02 - 1, Integer.parseInt(tmp[2]));

						System.out.printf("You have inserted a road from %s to %s with a distance of %d.\n", fName01,
								fName02, Integer.parseInt(tmp[2]));
					}

				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Distance cannot be 0, negative, or random word!!!\n");
				}

				break;

			// case R: Remove road from edge array if exist.
			case "R":
				// input example: BR CH
				String insert2 = "";
				String tmp2[] = new String[2];
				String fName001 = "", fName002 = "";
				int cityIndex001=-1, cityIndex002=-1;
				int bothFound = 0; // 0,1=not bothFound, 2=bothFound

				try {
					System.out.println("City codes: ");
					insert2 = kb.nextLine();
					tmp2 = insert2.split(" "); // eg: AN BK stored.
				} catch (NullPointerException e) {
					System.out.println("Invalid City Code!!!");
				}

				// Check if given city1 and city2 are found in cityArray[i][1]
				for (int i = 0; i < 20; i++) {
					if (tmp2[0].equals(fh.cityArray[i][1])) {
						fName001 = fh.cityArray[i][2];
						cityIndex001 = Integer.parseInt(fh.cityArray[i][0]);
						bothFound++;
					} else if (tmp2[1].equals(fh.cityArray[i][1])) {
						fName002 = fh.cityArray[i][2];
						cityIndex002 = Integer.parseInt(fh.cityArray[i][0]);
						bothFound++;
					}
				}

				// if bothFound 
				if (bothFound == 2) {
		
					//case1: road does exist! remove it!
					if(graphs[cityIndex001-1].road.get(cityIndex002-1) != 0){
						graphs[cityIndex001-1].road.set(cityIndex002-1,0);
						System.out.printf("Successfully removed a road between %s and %s.\n", fName001, fName002);
					}
						
					//case2: road doesn't exist.
					else if(graphs[cityIndex001-1].road.get(cityIndex002-1) == 0)
						System.out.printf("The road from %s and %s doesn't exist.\n",fName001,fName002);
				}
				// if bothFound check failed
				else
					System.out.println("Invalid City Code!!!");

				break;

			case "E":
				System.exit(0);

			

			case "ROAD":
				System.out.println("Showing you road array.");
				for (int i = 0; i < fh.roadArray.length; i++) {
					for (int j = 0; j < fh.roadArray[i].length; j++) {
						System.out.print(fh.roadArray[i][j] + " ");
					}
					System.out.println();
				}	
				
			}// switch ends

		} // while ends
	}// prompt method ends
}
