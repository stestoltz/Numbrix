
public class Solver {
	
	private Board clueBoard;

	public Solver(Board clueBoard) {
		this.clueBoard = clueBoard;
	}
	
	/**
	 * Solve a board
	 * @return a solved board, or null if there is no solution
	 */
	public Board solve() {
		
		Board working = new Board(clueBoard.size);
		
		return backtrack(working);
	}
	
	/**
	 * Start backtracking by finding the 1 clue
	 * If it does not exist, try placing 1 in every empty spot
	 * @param working
	 * @return
	 */
	public Board backtrack(Board working) {
		
		// if we find a clue which is one, make the recursive call
		for (Node n : clueBoard.clues) {
			if (n.value == 1) {
				working.board[n.row][n.col] = n; 
				return placeNumber(2, n, working);
			}
		}
		
		// we did not find a one node - assign one to all empty nodes and try from there
		
		for (int r = 0; r < clueBoard.size; r++) {
			for (int c = 0; c < clueBoard.size; c++) {
				Node n = clueBoard.board[r][c];
				
				// if node has no value
				if (n.value == 0) {
					
					// try one at that value, with a copied board
					Board newWorking = new Board(working);
					Node oneNode = newWorking.board[r][c];
					oneNode.value = 1;
					Board result = placeNumber(2, oneNode, newWorking);
					
					if (result != null) {
						return result;
					}
					
					// otherwise - keep looping, try a different one node
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Recursive backtracking call - for number n, try to place it at valid locations
	 * @param n number to place
	 * @param current last placed node
	 * @param working current board
	 * @return a board with number n placed at an empty node adjacent to current
	 */
	public Board placeNumber(int n, Node current, Board working) {
		
		//System.out.println(working.getBoard());
		
		// if the board is no longer compatible with the original board, return null
		if (!isCompatible(working, n)) {
			return null;
		}
		
		// if we have placed all numbers, return the board
		if (n > clueBoard.size * clueBoard.size) {
			return working;
		}
		
		// for each neighbor node to the current node
		for (Node next : working.getNeighbors(current)) {
			
			// if it is empty
			if (next.value == 0) {
				
				// place n at that node
				working.board[next.row][next.col].value = n;
				
				// backtracking call
				Board nextBoard = placeNumber(n + 1, next, working);

				
				// undo if placement did not result in a successful completed board
				
				if (nextBoard == null) {
				
					// make a new domain
					//Set<Integer> domain = new HashSet<>();
					
					//for (int i = 1; i <= board.size * board.size; i++) {
					//	domain.add(i);
					//}
					
					working.board[next.row][next.col].value = 0;
				} else {
					return nextBoard;
				}
				
			}
			
			
			
		}
		
		return null;
		
	}
	
	/**
	 * Checks to see if the in-progress board is still compatible with the original board
	 * @param base in-progress board
	 * @return true if the boards are compatible
	 */
	public boolean isCompatible(Board base, int n) {
		
		boolean valid = true;
		
		for (Node node : clueBoard.clues) {
			
			Node baseNode = base.board[node.row][node.col];
			
			// if the base node at the clue's location does not have the clue in its domain
			boolean clueHasOtherValue = !(baseNode.value == 0 || baseNode.value == node.value);
			
			// if the node has already been placed and its was not placed on its clue
			boolean clueNotFulfilled = node.value < n && baseNode.value != node.value;
			
			if (clueHasOtherValue || clueNotFulfilled) {
				valid = false;
				break;
			}
			
		}
		
		return valid;
		
	}
}
