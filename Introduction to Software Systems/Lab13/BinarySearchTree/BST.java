package BinarySearchTree;

import java.util.Random;

public class BST<Key extends Comparable<Key>, Value> {
	
	private Node root;
	
	/**
	 * Constructor
	 */
	public BST() {
		this.setRoot(new Node(null, null, 0));
	}
	
	/**
	 * @return the root
	 */
	public Node getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(Node root) {
		this.root = root;
	}

	//inner Node class
	//note: an outer class has direct access to values in an inner class 
	private class Node {
		
		private Key key;
		private Value value;
		private Node lChild; // left child 
		private Node rChild; // right child
		
		//number of nodes at this subtree
		//the value of N for the root will be # of nodes in entire tree 
		// the value of N for a leaf node would be 1
		private int N;
		
		public Node (Key key, Value val, int N) {
			this.setKey(key);
			this.setValue(val);
			this.setN(N);
			this.setlChild(null);
			this.setrChild(null);
		}
		
		/**
		 * @return the key
		 */
		public Key getKey() {
			return key;
		}

		/**
		 * @param key the key to set
		 */
		public void setKey(Key key) {
			this.key = key;
		}

		/**
		 * @return the value
		 */
		public Value getValue() {
			return value;
		}

		/**
		 * @param value the value to set
		 */
		public void setValue(Value value) {
			this.value = value;
		}

		/**
		 * @return the lChild
		 */
		public Node getlChild() {
			return lChild;
		}

		/**
		 * @param lChild the lChild to set
		 */
		public void setlChild(Node lChild) {
			this.lChild = lChild;
		}

		/**
		 * @return the rChild
		 */
		public Node getrChild() {
			return rChild;
		}

		/**
		 * @param rChild the rChild to set
		 */
		public void setrChild(Node rChild) {
			this.rChild = rChild;
		}

		/**
		 * @return the n
		 */
		public int getN() {
			return N;
		}

		/**
		 * @param n the n to set
		 */
		public void setN(int n) {
			N = n;
		}
		
		/**
		 * find the value associated with they key
		 * @param k : the key that needs to be find
		 * @return the value associated with they key
		 */
		public Value get(Key k) {
			if (this.getKey().equals(k))
				return this.getValue();
			
			if (k.compareTo(this.getKey()) < 0)
				if (this.getlChild() == null)
					return null;
				else
					return this.getlChild().get(k);
			else
				if (this.getrChild() == null)
					return null;
				else
					return this.getrChild().get(k);
		}
		
		/**
		 * inserts the key value pair into the tree
		 * @param v : value
		 * @param k : key
		 */
		public void put(Value v, Key k) {
			// root node
			if (this.getN() == 0) {
				this.setValue(v);
				this.setKey(k);
				this.setN(1);
			}
			// other node
			else {
				this.setN(N + 1);
				if (k.compareTo(this.getKey()) < 0)
					if (this.getlChild() == null)
						this.setlChild(new Node(k, v, 1));
					else
						this.lChild.put(v, k);
				else
					if (this.getrChild() == null)
						this.setrChild(new Node(k, v, 1));
					else
						this.rChild.put(v, k);
			}
		}
		
		/**
		 * performs an in order walk of the tree printing the values 
		 */
		public void walk() {
			if (this.getN() == 1) {
				System.out.println(this.getValue());
				return;
			}
			
			if (this.getlChild() != null)
				this.getlChild().walk();
			
			System.out.println(this.getValue());
			
			if (this.getrChild() != null)
				this.getrChild().walk();
		}
	}
	
	// returns # of nodes in the tree
	public int size() {
		return this.getRoot().getN();
	}
	
	//returns the value associated with they key 
	// returns null if the key is not in the tree 
	public Value get(Key key) {
		return this.getRoot().get(key);
	}
	
	// inserts the key value pair into the tree
	public void put(Value val, Key key) {
		this.root.put(val, key);
	} 
	
	//performs an in order walk of the tree printing the values 
	public void walk() {
		this.getRoot().walk();
	}
	
	//here are some of the test cases I performed 
	public static void main(String[] args) {
		Random rand = new Random();
		BST<Integer, Character> tree = new BST<>();
		
		for (int i = 0; i < 25; i++) {
			int key = rand.nextInt(26) + 'a';
			char val = (char)key;
			tree.put(val, key); 
		}
		
		//note: not all of these chars will end up being generated from the for loop 
		// some of these return values will be null 
		System.out.println(tree.get((int)'a'));
		System.out.println(tree.get((int)'b'));
		System.out.println(tree.get((int)'c'));
		System.out.println(tree.get((int)'f')); 
		System.out.println(tree.get((int)'k')); 
		System.out.println(tree.get((int)'x'));
		
		tree.walk();
	} 
}