public class Counter {
	private final String name; // instance field private 
	int count; // instance field
	
	// Counter constructor – Notice Constructors do not have a return type and are named the same as their class
	public Counter(String id) {
		this.name = id;
		this.count = 0;
	}
	
	// increments count
	public void increment() {
		count++;
	}
	
	// returns count
	public int tally() {
		return count;
	}
	
	// prints name and count
	public String toString() {
		return name + ": " + (count + "");
	}
}
