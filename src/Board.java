import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {
	
	private Node[][] board;
	private int size;
	
	public Board(int size, List<Node> clues) {
		
		this.size = size;
		
		board = new Node[size][size];
		
		Set<Integer> domain = new HashSet<>();
		
		for (int i = 1; i <= size * size; i++) {
			domain.add(i);
		}
		
		Set<Integer> taken = new HashSet<>();
		
		for (Node clue : clues) {
			board[clue.row][clue.col] = clue;
			
			taken.add(clue.value());
		}
		
		domain.removeAll(taken);
		
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				
				if (board[r][c] == null) {
					board[r][c] = new Node(domain, c, r);
				}
				
			}
		}
		
	}
	
	
	public List<Node> getNeighbors(Node n) {
		List<Node> neighbors = new ArrayList<Node>();
		
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
				if (board[r][c].size() != 1) {
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
				
				if (n.size() == 1) {
					if (n.value() < 10) {
						sb.append(" ");
					}
					
					sb.append(n.value());
					
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
