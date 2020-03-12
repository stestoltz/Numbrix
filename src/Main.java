import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		int numFiles = 4;
		
		for (int i = 4; i <= numFiles; i++) {
		
			String filename = "numbrix" + i + ".txt";
			FileInputStream fin = new FileInputStream(filename);
			Scanner scn = new Scanner(fin);
			
			int size = scn.nextInt();
			
			List<Node> clues = new ArrayList<>();
			
			scn.useDelimiter(",");
			
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
						// do nothing
					}
				}
			}
			
			
			scn.close();
			
			Board numbrix = new Board(size, clues);
			
			
			System.out.println(numbrix.getBoard());
			
			
			Solver solver = new Solver(numbrix);
			
			Board solved = solver.solve();
			
			if (solved == null) {
				System.out.println("No solution");
			} else {
				System.out.println(solved.getBoard());
			}
		
		}

	}

}
