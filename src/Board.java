import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
	
	public Node[][] board;
	public final int size;
	
	public List<Node> clues;
	
	/**
	 * Constructor for clue boards
	 * @param size
	 * @param clues list of clues
	 */
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
	
	/**
	 * Constructor for empty boards
	 * @param size
	 */
	public Board(int size) {
		
		this.size = size;
		
		board = new Node[size][size];
		
		this.clues = new ArrayList<>();
		
		//create the empty board
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				
				board[r][c] = new Node(0, c, r);
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
	
	/**
	 * Gets the neighbors of a node, in random order
	 * @param n
	 * @return list of neighbors of the given node
	 */
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
		
		Collections.shuffle(neighbors);
		
		return neighbors;
	}
	
	
	// implements the shortest-path-first heuristic
	public List<Node> getOptimizedNeighborsShortest(Node n, Node nextHint) {
		List<Node> randomizedNeighbors = getNeighbors(n);
		
		if (nextHint == null) {
			return randomizedNeighbors;
		}
		
		// build list of distances with matching indices to neighbors list
		List<Integer> distances = new ArrayList<>();
		
		for (Node neighbor : randomizedNeighbors) {
			
			// Manhattan distance
			int distance = manhattanDistance(neighbor, nextHint);
			
			distances.add(distance);
		}
		
		List<Node> organizedNeighbors = new ArrayList<>();
		
		// reorder list so that nodes with shortest distance are first
		while (randomizedNeighbors.size() > 0) {		

			int index = 0;
			int minDistance = distances.get(index);
			
			// find the minimum distance
			for (int i = 1; i < distances.size(); i++) {
				if (distances.get(i) < minDistance) {
					index = i;
					minDistance = distances.get(i);
				}
			}
			
			// remove that node and add it to the new list
			organizedNeighbors.add(randomizedNeighbors.remove(index));
			distances.remove(index);
		}
		
		return organizedNeighbors;
		
	}
	
	// implements the closest-path-first heuristic
	public List<Node> getOptimizedNeighborsClosest(Node n, Node nextHint) {
		List<Node> randomizedNeighbors = getNeighbors(n);
		
		if (nextHint == null) {
			return randomizedNeighbors;
		}
		
		// calculate the actual distance needed to travel
		int numericalDistance = nextHint.value - n.value;

		// build list of distances with matching indices to neighbors list
		List<Integer> distances = new ArrayList<>();
		
		for (Node neighbor : randomizedNeighbors) {
			
			// Manhattan distance
			int distance = manhattanDistance(neighbor, nextHint);
			
			distances.add(distance);
		}
		
		List<Node> organizedNeighbors = new ArrayList<>();
		
		// reorder list so that nodes with closest distance are first
		while (randomizedNeighbors.size() > 0) {

			int index = 0;
			int minDifference = distances.get(index);

			// find the minimum distance
			for (int i = 1; i < distances.size(); i++) {
				
				int distance = distances.get(i);
				
				int difference = Math.abs(numericalDistance - distance);
				
				if (difference < minDifference) {
					index = i;
					minDifference = distances.get(i);
				}
			}
			
			// remove that node and add it to the new list
			organizedNeighbors.add(randomizedNeighbors.remove(index));
			distances.remove(index);
		}
		
		return organizedNeighbors;
		
	}
	
	public int manhattanDistance(Node a, Node b) {
		return Math.abs(a.row - b.row) + Math.abs(a.col - b.col);
	}
	
	// finds the next clue to travel to
	public Node getSmallestClueAfter(int n) {
		
		Node nextSmallest = null;
		
		for (Node clue : clues) {
			
			// clue is in the future
			if (clue.value > n) {
				
				// no clue found yet or smaller than the best one found so far
				if (nextSmallest == null || clue.value < nextSmallest.value) {
					nextSmallest = clue;
				}
			}
		}
		
		return nextSmallest;		
	}
	
	// returns true if all locations in the board have values
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
	
	// returns a readable string of the board
	public String getBoard() {
		StringBuilder sb = new StringBuilder();
		
		int width = 2;
		
		if (size * size >= 100) {
			width = 3;
		}
		
		sb.append("\n");
		
		for (int r = 0; r < size; r++) {
			
			sb.append(" ");
			
			for (int c = 0; c < size; c++) {
				
				Node n = board[r][c];
				
				String s = "";
				
				if (n.value != 0) {
					s += n.value;
				}
				
				sb.append(String.format("%" + width + "s", s));
				
				if (c < size - 1) {
					sb.append("|");
				}
				
			}
			
			if (r < size - 1) {
				sb.append("\n ");
				for (int i = 0; i < (width + 1) * size - 1; i++) {
					if (i % (width + 1) == width) {
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
