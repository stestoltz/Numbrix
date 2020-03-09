import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Node2 {
	
	private Set<Integer> domain;
	public final int col;
	public final int row;

	public Node2(Set<Integer> domain, int col, int row) {
		this.domain = new HashSet<>();
		this.domain.addAll(domain);
		this.col = col;
		this.row = row;
	}
	
	public Node2(int value, int col, int row) {
		this.domain = new HashSet<>();
		this.domain.add(value);
		
		this.col = col;
		this.row = row;
	}
	
	public int size() {
		return domain.size();
	}
	
	public int value() {
		List<Integer> temp = new ArrayList<>(domain);
		return temp.get(0);
	}
	
	public void setValue(int value) {
		domain = new HashSet<Integer>();
		domain.add(value);
	}
	
	public Set<Integer> getDomain() {
		Set<Integer> temp = new HashSet<>(domain);
		return temp;
	}
	
	public void limit(Set<Integer> toRemove) {
		domain.removeAll(toRemove);
	}
}
