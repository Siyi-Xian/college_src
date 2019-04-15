import java.util.Comparator;

/**
 * TODO: Complete the implementation of this class.
 * 
 * A HuffmanTree represents a variable-length code such that the shorter the bit
 * pattern associated with a character, the more frequently that character
 * appears in the text to be encoded.
 */

public class HuffmanTree {

	class Node {
		protected char key;
		protected int priority;
		protected Node left, right;

		public Node(int priority, char key) {
			this(priority, key, null, null);
		}

		public Node(int priority, Node left, Node right) {
			this(priority, '\0', left, right);
		}

		public Node(int priority, char key, Node left, Node right) {
			this.key = key;
			this.priority = priority;
			this.left = left;
			this.right = right;
		}

		public boolean isLeaf() {
			return left == null && right == null;
		}
	}

	protected Node root;

	/**
	 * TODO
	 * 
	 * Creates a HuffmanTree from the given frequencies of letters in the
	 * alphabet using the algorithm described in lecture.
	 */
	public HuffmanTree(FrequencyTable charFreqs) {
		Comparator<Node> comparator = (x, y) -> {
			/**
			 * TODO: x and y are Nodes x comes before y if x's priority is less
			 * than y's priority
			 */
			if (x.priority < y.priority)
				return -1;
			else if (x.priority == y.priority)
				return 0;
			else
				return 1;
		};
		PriorityQueue<Node> forest = new Heap<Node>(comparator);

		/**
		 * TODO: Complete the implementation of Huffman's Algorithm. Start by
		 * populating forest with leaves.
		 */
		for (Character ch : charFreqs.keySet()) {
			Node temp = new Node(charFreqs.get(ch), ch);
			forest.insert(temp);
		}
		while (forest.size() > 1) {
			Node min_1 = forest.delete();
			Node min_2 = forest.delete();
			Node newNode = new Node(min_1.priority + min_2.priority, min_1, min_2);
			forest.insert(newNode);
		}
		root = forest.peek();
	}

	/**
	 * TODO
	 * 
	 * Returns the character associated with the prefix of bits.
	 * 
	 * @throws DecodeException
	 *             if bits does not match a character in the tree.
	 */
	public char decodeChar(String bits) throws DecodeException {
		Node ch = root;
		for (int i = 0; i < bits.length(); i++)
			if (bits.charAt(i) == '0' && ch.left != null)
				ch = ch.left;
			else if (ch.right != null)
				ch = ch.right;
			else
				return ch.key;
		return ch.key;
	}

	private String lookup(char ch, Node node, String str) {
		if (ch == node.key)
			return str;

		try {
			return this.lookup(ch, node.left, str + "0");
		} catch (RuntimeException e) {
			return this.lookup(ch, node.right, str + "1");
		}
	}

	/**
	 * TODO
	 * 
	 * Returns the bit string associated with the given character. Must search
	 * the tree for a leaf containing the character. Every left turn corresponds
	 * to a 0 in the code. Every right turn corresponds to a 1. This function is
	 * used by CodeBook to populate the map.
	 * 
	 * @throws EncodeException
	 *             if the character does not appear in the tree.
	 */
	public String lookup(char ch) throws EncodeException {
		return this.lookup(ch, root, "");
	}
}
