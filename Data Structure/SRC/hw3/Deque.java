

public class Deque<T> extends DoublyLinkedList<T> implements Queue<T> {
	
	public Deque() {
	}
	
	@Override
	public void push(T x) {
		// TODO Auto-generated method stub
		Node addOne = new Node(x);
		
		addOne.next = head.next;
		addOne.prev = head.prev;
		head.next.prev = addOne;
		head.next = addOne;
		
		n++;
	}

	@Override
	public void pop() {
		// TODO Auto-generated method stub
		head.next.next.prev = head;
		head.next = head.next.next;
		n--;
	}

	@Override
	public void inject(T x) {
		// TODO Auto-generated method stub
		super.add(x);
	}

	@Override
	public void eject() {
		// TODO Auto-generated method stub
		super.remove(super.size() - 1);
	}

}

interface Queue<T> {
	void push(T x); // simple add

	void pop();

	void inject(T x);

	void eject();
}