import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		List<Node> clues = new ArrayList<>();
		
		clues.add(new Node(37, 0, 0));
		clues.add(new Node(31, 2, 0));
		clues.add(new Node(21, 4, 0));
		clues.add(new Node(3, 6, 0));
		clues.add(new Node(5, 8, 0));
		clues.add(new Node(35, 1, 1));
		clues.add(new Node(29, 3, 1));
		clues.add(new Node(19, 5, 1));
		clues.add(new Node(1, 7, 1));
		clues.add(new Node(39, 0, 2));
		clues.add(new Node(7, 8, 2));
		clues.add(new Node(43, 1, 3));
		clues.add(new Node(11, 7, 3));
		clues.add(new Node(41, 0, 4));
		clues.add(new Node(13, 8, 4));
		clues.add(new Node(47, 1, 5));
		clues.add(new Node(65, 7, 5));
		clues.add(new Node(49, 0, 6));
		clues.add(new Node(67, 8, 6));
		clues.add(new Node(51, 1, 7));
		clues.add(new Node(79, 3, 7));
		clues.add(new Node(81, 5, 7));
		clues.add(new Node(71, 7, 7));
		clues.add(new Node(53, 0, 8));
		clues.add(new Node(55, 2, 8));
		clues.add(new Node(77, 4, 8));
		clues.add(new Node(75, 6, 8));
		clues.add(new Node(73, 8, 8));
		
		Board numbrix = new Board(9, clues);
		
		
		System.out.println(numbrix.getBoard());

	}

}
