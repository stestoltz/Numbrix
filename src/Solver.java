import java.util.ArrayList;
import java.util.List;

public class Solver {

	public final int SHORTEST = 1;
	public final int CLOSEST = 2;
	public final int RANDOM = 3;
	
	private Board clueBoard;
	private final int heuristic;

	public Solver(Board clueBoard, int heuristic) throws Exception {
		this.clueBoard = clueBoard;
		
		if (heuristic == CLOSEST || heuristic == SHORTEST || heuristic == RANDOM) {
			this.heuristic = heuristic;
		} else {
			throw new Exception("Heuristic value is not valid");
		}
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
	 * @param working the board we will change
	 * @return the solved board, or null if no solution
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
	 * @return the solved board, or null if no solution
	 */
	public Board placeNumber(int n, Node current, Board working) {
		
		Node nextHint = clueBoard.getSmallestClueAfter(current.value);
		
		// if the board is no longer compatible with the original board, return null
		if (!isCompatible(working, n, current, nextHint)) {
			return null;
		}
		
		// if we have placed all numbers, return the board
		if (n > clueBoard.size * clueBoard.size) {
			return working;
		}
		
		List<Node> neighbors = new ArrayList<>();
		
		if (heuristic == SHORTEST) {
			
			// heuristic for shortest Manhattan distance to next clue
			neighbors = working.getOptimizedNeighborsShortest(current, nextHint);
			
		} else if (heuristic == CLOSEST) {
			
			// heuristic for closest Manhattan distance to actual numerical distance to next clue
			neighbors = working.getOptimizedNeighborsClosest(current, nextHint);
			
		} else if (heuristic == RANDOM) {
			
			// random heuristic
			neighbors = working.getNeighbors(current);
		}
		
		
		for (Node next : neighbors) {
			
			// if it is empty
			if (next.value == 0) {
				
				// place n at that node
				working.board[next.row][next.col].value = n;
				
				// backtracking call
				Board nextBoard = placeNumber(n + 1, next, working);

				
				// undo if placement did not result in a successful completed board
				
				if (nextBoard == null) {
					
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
	public boolean isCompatible(Board base, int n, Node current, Node nextClue) {
		
		boolean valid = true;
		
		// check if board and clues interfere with each other
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
		
		// check if it is impossible to reach the next clue
		if (valid) {
			
			if (nextClue != null) {
			
				int distance = base.manhattanDistance(current, nextClue);
				
				// if clue is impossible to reach
				if (distance > nextClue.value - current.value) {
					
					return false;
				}
			}
		}
		
		return valid;
		
	}
}
