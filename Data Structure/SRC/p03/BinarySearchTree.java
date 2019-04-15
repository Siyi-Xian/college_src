import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiPredicate;

/**
 * TODO: This is your first major task.
 * 
 * This class implements a generic unbalanced binary search tree (BST).
 */

public class BinarySearchTree<K> implements Tree<K> {

	/**
	 * A Node is a Location, which means that it can be the return value of a
	 * search on the tree.
	 */

	class Node implements Location<K> {
		protected K data;
		protected Node left, right;
		protected Node parent; // the parent of this node
		protected int height; // the height of the subtree rooted at this node
		protected boolean dirty; // true iff the key in this node has been
									// removed

		/**
		 * Constructs a leaf node with the given key.
		 */
		public Node(K key) {
			this(key, null, null);
		}

		/**
		 * TODO
		 * 
		 * Constructs a new node with the given values for fields.
		 */
		public Node(K data, Node left, Node right) {
			this.data = data;
			this.left = left;
			this.right = right;

			if (left != null)
				this.left.parent = this;
			if (right != null)
				this.right.parent = this;
			this.parent = null;

			this.fixHeight();
			this.dirty = false;
		}

		/**
		 * Return true iff this node is a leaf in the tree.
		 */
		protected boolean isLeaf() {
			return left == null && right == null;
		}

		/**
		 * TODO
		 * 
		 * Performs a local update on the height of this node. Assumes that the
		 * heights in the child nodes are correct. This function *must* run in
		 * O(1) time.
		 */
		protected void fixHeight() {
			if (this.left == null)
				if (this.right == null)
					this.height = 1;
				else
					this.height = right.height + 1;
			else if (this.right == null)
				this.height = left.height + 1;
			else
				this.height = Math.max(left.height, right.height) + 1;
		}

		/**
		 * TODO
		 * 
		 * Returns the data in this node.
		 */
		public K get() {
			return this.data;
		}

		/**
		 * TODO
		 * 
		 * Returns the location of the node containing the inorder predecessor
		 * of this node.
		 */
		public Node getBefore() {
			List<K> keys = keys();
			try {
				return search(keys.get(keys.indexOf(get()) - 1));
			} catch (Exception e) {
				return null;
			}
		}

		/**
		 * TODO
		 * 
		 * Returns the location of the node containing the inorder successor of
		 * this node.
		 */
		public Node getAfter() {
			List<K> keys = keys();
			try {
				return search(keys.get(keys.indexOf(get()) + 1));
			} catch (Exception e) {
				return null;
			}
		}
	}

	protected Node root;
	protected int n;
	protected BiPredicate<K, K> lessThan;

	/**
	 * Constructs an empty BST, where the data is to be organized according to
	 * the lessThan relation.
	 */
	public BinarySearchTree(BiPredicate<K, K> lessThan) {
		this.lessThan = lessThan;
	}

	/**
	 * Looks up the key in this tree
	 * 
	 * @param key
	 *            the value need to be searched
	 * @param node
	 *            given tree
	 * @return location of key
	 */
	private Node searchNode(K key, Node node) {
		if (node == null)
			return null;

		if (node.data == key)
			return node;

		if (key.hashCode() < node.get().hashCode())
			return this.searchNode(key, node.left);
		else
			return this.searchNode(key, node.right);
	}

	/**
	 * TODO
	 * 
	 * Looks up the key in this tree and, if found, returns the (possibly dirty)
	 * location containing the key.
	 */
	public Node search(K key) {
		return this.searchNode(key, root);
	}

	/**
	 * TODO
	 * 
	 * Returns the height of this tree. Runs in O(1) time!
	 */
	public int height() {
		if (root == null)
			return 0;
		else
			return root.height;
	}

	/**
	 * TODO
	 * 
	 * Clears all the keys from this tree. Runs in O(1) time!
	 */
	public void clear() {
		root = null;
		n = 0;
	}

	/**
	 * Returns the number of keys in this tree.
	 */
	public int size() {
		return n;
	}

	/**
	 * insert key to tree and fix parent value
	 * 
	 * @param key
	 *            value need to be added
	 * @param tree
	 *            given tree
	 * @param parent
	 *            parent of given tree
	 * @return a tree which already added given value
	 */
	private Node insertNode(Node key, Node tree, Node parent) {
		if (tree == null) {
			key.parent = parent;
			n++;
			key.fixHeight();
			return key;
		}
		if (key.get() != tree.get()) {

			if (key.get().hashCode() < tree.get().hashCode())
				tree.left = insertNode(key, tree.left, tree);
			else
				tree.right = insertNode(key, tree.right, tree);

			tree.fixHeight();
		} else if (tree.dirty) {
			tree.dirty = false;
			n++;
		}

		return tree;
	}

	/**
	 * TODO
	 * 
	 * Inserts the given key into this BST, as a leaf, where the path to the
	 * leaf is determined by the predicate provided to the tree at construction
	 * time. The parent pointer of the new node and the heights in all node
	 * along the path to the root are adjusted accordingly.
	 * 
	 * Note: we assume that all keys are unique. Thus, if the given key is
	 * already present in the tree, nothing happens.
	 * 
	 * Returns the location where the insert occurred (i.e., the leaf node
	 * containing the key).
	 */
	public Node insert(K key) {
		Node newNode = new Node(key);
		root = this.insertNode(newNode, root, null);
		return newNode;
	}

	/**
	 * check if the given value is in the given tree
	 * 
	 * @param key
	 *            given value
	 * @param node
	 *            given tree
	 * @return true iff given tree contains given value
	 */
	private boolean containNode(K key, Node node) {
		if (node == null)
			return false;

		if (node.get() == key)
			if (!node.dirty)
				return true;
			else
				return false;

		if (key.hashCode() < node.get().hashCode())
			return this.containNode(key, node.left);
		else
			return this.containNode(key, node.right);
	}

	/**
	 * TODO
	 * 
	 * Returns true iff the given key is in this BST.
	 */
	public boolean contains(K key) {

		return this.containNode(key, root);
	}
	
	/**
	 * remove given value in the given tree
	 * @param key given value
	 * @param node given tree
	 * @return a new tree which already remove the given value
	 */
	private Node removeNode(K key, Node node) {
		if (node.data == key) {
			node.dirty = true;
			return node;
		}

		if (key.hashCode() < node.get().hashCode())
			node.left = this.removeNode(key, node.left);
		else
			node.right = this.removeNode(key, node.right);

		return node;
	}

	/**
	 * TODO
	 * 
	 * Removes the key from this BST. If the key is not in the tree, nothing
	 * happens. Implement the removal using lazy deletion.
	 */
	public void remove(K key) {
		if (this.contains(key)) {
			root = this.removeNode(key, root);
			n--;
		}
	}

	/**
	 * TODO
	 * 
	 * Clears out all dirty nodes from this BST.
	 * 
	 * Use the following algorithm: (1) Let ks be the list of keys in this tree.
	 * (2) Clear this tree. (2) For each key in ks, insert it into this tree.
	 */
	public void rebuild() {
		List<K> keys = this.keys();
		this.clear();
		for (K i : keys)
			this.insert(i);
	}
	
	/**
	 * great a list of all element in given tree by inorder
	 * @param node given tree
	 * @return list of elements in tree
	 */
	private List<K> inorderKeys(Node node) {
		List<K> keys = new ArrayList<K>();

		if (node == null)
			return keys;

		keys.addAll(this.inorderKeys(node.left));
		if (!node.dirty)
			keys.add(node.get());
		keys.addAll(this.inorderKeys(node.right));

		return keys;
	}

	/**
	 * TODO
	 * 
	 * Returns a sorted list of all the keys in this tree.
	 */
	public List<K> keys() {
		return this.inorderKeys(root);
	}

	/**
	 * TODO
	 * 
	 * Returns a textual representation of this BST.
	 */
	public String toString() {
		return this.keys().toString();
	}
}
