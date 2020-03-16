import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		// get user input for file, heuristic
		Scanner userInput = new Scanner(System.in);
		
		System.out.println("Numbrix - by Gabe Armstrong, Ethan Greenly, and Stephen Stoltzfus");
		
		while (true) {
			
			System.out.println();
		
			// get file name
			System.out.print("File name (see README for formatting) (type q to quit): ");
			String filename = userInput.nextLine();
			
			// allow user to quit
			if (filename.equals("q")) {
				break;
			}
			
			// validate file
			FileInputStream fin;
			try {
			
				fin = new FileInputStream(filename);
			
			} catch (FileNotFoundException ex) {
				System.out.println("File not found");
				continue;
			}
			
			// get heuristic to use
			System.out.println("Please choose a heuristic for backtracking:");
			System.out.println("1: Shortest: Visit the node with the shortest path to the next clue");
			System.out.println("2: Closest: Visit the node whose path length is closest to the difference in node values between the node and the clue node");
			System.out.println("3: Random: Visit a random node next");
			System.out.print("Please enter 1, 2, or 3: ");
			
			String line = userInput.nextLine();

			int heuristicSelected;
			
			// validate heuristic
			try {
				heuristicSelected = Integer.parseInt(line);
				
				if (!(heuristicSelected == 1 || heuristicSelected == 2 || heuristicSelected == 3)) {
					System.out.println("Invalid heuristic entered");
					continue;
				}
			} catch (NumberFormatException ex) {
				System.out.println("Invalid heuristic entered");
				continue;
			}			
			
			// build from file
			Scanner scn = new Scanner(fin);
			
			List<Node> clues = new ArrayList<>();
			
			int size;
			
			try {
			
				size = scn.nextInt();
				
				scn.useDelimiter(",");
			
				// find clues and add them as nodes to clue list
				for (int r = 0; r < size; r++) {
					for (int c = 0; c < size; c++) {
						String next = scn.next();
						
						try {
							int clue = Integer.parseInt(next.trim());
							
							if (clue >= 1 && clue <= size * size) {
								Node clueNode = new Node(clue, c, r);
								
								clues.add(clueNode);
							}
						} catch (NumberFormatException ex) {
							// empty space in between commas - not a clue
						}
					}
				}
			} catch (NoSuchElementException ex) {
				System.out.println("File incorrectly formatted");
				continue;
			} finally {
				scn.close();
			}
			
			// build the board
			Board numbrix = new Board(size, clues);
						
			// print the board in a human-readable fashion
			System.out.println(numbrix.getBoard());
			
			// create the solver
			Solver solver;
			try {
				solver = new Solver(numbrix, heuristicSelected);
			} catch (Exception ex) {
				System.out.println("Invalid heuristic entered");
				continue;
			}
			
			// attempt to solve the board
			Board solved = solver.solve();
			
			System.out.println();
			
			// print the results
			if (solved == null) {
				System.out.println("No solution");
			} else {
				System.out.println(solved.getBoard());
			}
		
		}
		
		userInput.close();

	}

}
