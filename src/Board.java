import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {
	
	public Node[][] board;
	public final int size;
	
	public List<Node> clues;
	
	public Board(int size, List<Node> clues) {
		
		this.size = size;
		this.clues = clues;
		board = new Node[size][size];
		
		// setup the clue nodes in the board
		for (Node clue : clues) {
			board[clue.row][clue.col] = clue;
		}
		
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				
				// set non-clued nodes to default values of 0
				if (board[r][c] == null) {
					board[r][c] = new Node(0, c, r);
				}
			}
		}
		
	}	
	
	public Board(int size) {
		
		this.size = size;
		
		board = new Node[size][size];
		
		this.clues = new ArrayList<>();
		
		//init the board
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				
				if (board[r][c] == null) {
					board[r][c] = new Node(0, c, r);
				}
				
			}
		}
		
	}
	
	// copy constructor
	public Board(Board b) {
		this.clues = new ArrayList<>();
		
		for (Node n : b.clues) {
			clues.add(n);
		}
		
		this.size = b.size;

		board = new Node[size][size];
		
		// copy all nodes over to this board
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				board[r][c] = new Node(b.board[r][c]);
			}
		}
	}
	
	
	public Set<Node> getNeighbors(Node n) {
		Set<Node> neighbors = new HashSet<Node>();
		
		if (n.row > 0) {
			neighbors.add(board[n.row - 1][n.col]);
		}
		
		if (n.row < size - 1) {
			neighbors.add(board[n.row + 1][n.col]);
		}
		
		if (n.col > 0) {
			neighbors.add(board[n.row][n.col - 1]);
		}
		
		if (n.col < size - 1) {
			neighbors.add(board[n.row][n.col + 1]);
		}
		
		return neighbors;
	}
	
	
	public boolean isSolved() {
		
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				if (board[r][c].value == 0) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	
	public String getBoard() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("\n");
		
		for (int r = 0; r < size; r++) {
			
			sb.append(" ");
			
			for (int c = 0; c < size; c++) {
				
				Node n = board[r][c];
				
				if (n.value != 0) {
					if (n.value < 10) {
						sb.append(" ");
					}
					
					sb.append(n.value);
					
				} else {
					sb.append("  ");
				}
				
				if (c < size - 1) {
					sb.append("|");
				}
				
			}
			
			if (r < size - 1) {
				sb.append("\n ");
				for (int i = 0; i < 3 * size - 1; i++) {
					if (i % 3 == 2) {
						sb.append("+");
					} else {
						sb.append("-");
						
					}
				}
				sb.append("\n");
			}
		}
		
		return sb.toString();
	}
	
}
