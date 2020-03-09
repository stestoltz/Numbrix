import java.util.HashSet;
import java.util.Set;

public class Solver {
	
	private Board board;

	public Solver(Board board) {
		this.board = board;
	}
	
	public Board solve(Node oneNode) {
		
		Board working = new Board(board.size);
		
		working.board[oneNode.row][oneNode.col] = oneNode; 
		
		return placeNumber(2, oneNode, working);
	}
	
	public Board placeNumber(int n, Node current, Board working) {
		
		//System.out.println("placeNumber: " + n);
		
		//System.out.println(working.getBoard());
		
		if (!isCompatible(working, board)) {
			return null;
		}
		
		if (n > board.size * board.size) {
			return working;
		}
		
		int row = current.row;
		int col = current.col;
		
		for (Node next : working.getNeighbors(current)) {
			
			if (next.value == 0) {
				
				// place the current one
				working.board[next.row][next.col].value = n;
				
				// backtracking call
				Board nextBoard = placeNumber(n + 1, next, working);

				
				// undo
				
				if (nextBoard == null) {
				
					// make a new domain
					Set<Integer> domain = new HashSet<>();
					
					for (int i = 1; i <= board.size * board.size; i++) {
						domain.add(i);
					}
					
					working.board[next.row][next.col].value = 0;
				} else {
					return nextBoard;
				}
				
			}
			
			
			
		}
		
		return null;
		
	}
	
	public boolean isCompatible(Board base, Board clued) {
		
		boolean valid = true;
		
		for (Node n : clued.known) {
			
			// if the base node at the clue's location has the clue in its domain
			if (base.board[n.row][n.col].value == 0 || base.board[n.row][n.col].value == n.value) {
				// good
			} else {
				valid = false;
				break;
			}
			
		}
		
		return valid;
		
	}
	
	/*public Board solve2() {
		
		Set<Node> allNodes = new HashSet<Node>();
		
		for (int i = 0; i < board.size; i++) {
			for (int j = 0; j < board.size; j++) {
				allNodes.add(board.board[i][j]);
			}
		}
		
		
		//remove neighbors from all other domains
		
		
		// while not completed
		while (board.known.size() < board.size * board.size) {
			
			System.out.println();
			System.out.println(board.getBoard());
			
			//System.out.println(board.board[0][7].getDomain());
			
			Set<Node> unknown = new HashSet<>(allNodes);
		
			unknown.removeAll(board.known);
			
			for (Node known : board.known) {
				
				Set<Node> unknownNotNeighbors = new HashSet<>(unknown);
				
				unknownNotNeighbors.removeAll(board.getNeighbors(known));
				
				for (Node n : unknownNotNeighbors) {
					
					// remove adjacent values from all domains that are not adjacent
					
					n.getDomain().remove(known.value() + 1);
					n.getDomain().remove(known.value() - 1);
					
					if (n.size() == 1) {
						board.known.add(n);
					}
				}
				
				// if value is only in one domain
				
				Node location = null;
				int value = known.value() + 1;
				
				for (Node un : unknown) {
					
					// do with n + 1
					
					if (un.getDomain().contains(value)) {
						if (location == null) {
							location = un;
						} else {
							location = null;
							break;
						}
					}
					
				}
				
				if (location != null) {
					board.known.add(location);
				}
				
				location = null;
				value = known.value() - 1;
				
				for (Node un : unknown) {
					
					// do with n + 1
					
					if (un.getDomain().contains(value)) {
						if (location == null) {
							location = un;
						} else {
							location = null;
							break;
						}
					}
					
				}
				
				if (location != null) {
					board.known.add(location);
				}
				
			}
		
		}
		
		return board;
	}
*/
}
