
public class Node {

	public int value;
	public final int col;
	public final int row;
	
	public Node(int value, int col, int row) {
		this.value = value;
		this.col = col;
		this.row = row;
	}
	
	public Node(int col, int row) {
		this.value = 0;
		this.col = col;
		this.row = row;
	}
	
}
