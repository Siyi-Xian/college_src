import java.util.function.BiPredicate;

/**
 * TODO: This is your second major task.
 *
 * This class implements a generic height-balanced binary search tree, using the
 * AVL algorithm. Beyond the constructor, only the insert() method needs to be
 * implemented. All other methods are unchanged.
 */

public class AVLTree<K> extends BinarySearchTree<K> {

	/**
	 * Creates an empty AVL tree as a BST organized according to the lessThan
	 * predicate.
	 */
	public AVLTree(BiPredicate<K, K> lessThan) {
		super(lessThan);
	}

	/**
	 * Turn Left
	 * 
	 * @param node
	 *            given tree
	 * @return tree which is turned
	 */
	private Node leftTurn(Node node) {
		Node temp = node.right.left;
		node.right.left = node;
		node = node.right;
		node.left.right = temp;

		node.parent = null;
		try {
			node.left.parent = node;
		} catch (Exception e) {
		}
		try {
			node.left.right.parent = node.left;
		} catch (Exception e) {
		}

		try {
			node.left.right.fixHeight();
		} catch (Exception e) {
		}
		try {
			node.left.fixHeight();
		} catch (Exception e) {
		}
		try {
			node.right.fixHeight();
		} catch (Exception e) {
		}
		node.fixHeight();

		return node;
	}

	/**
	 * Turn Right
	 * 
	 * @param node
	 *            given tree
	 * @return tree which is turned
	 */
	private Node rightTurn(Node node) {
		Node temp = node.left.right;
		node.left.right = node;
		node = node.left;
		node.right.left = temp;

		node.parent = null;
		try {
			node.right.parent = node;
		} catch (Exception e) {
		}
		try {
			node.right.left.parent = node.right;
		} catch (Exception e) {
		}

		try {
			node.right.left.fixHeight();
		} catch (Exception e) {
		}
		try {
			node.right.fixHeight();
		} catch (Exception e) {
		}
		try {
			node.left.fixHeight();
		} catch (Exception e) {
		}
		node.fixHeight();

		return node;
	}

	/**
	 * get height avoid NullPointerError error
	 * 
	 * @param node
	 *            given tree
	 * @return height of given tree
	 */
	private int getHeight(Node node) {
		try {
			return node.height;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * helper function to check balance
	 */
	private void checkbalance() {

		int l = this.getHeight(root.left);
		int r = this.getHeight(root.right);

		int ll, lr;
		if (root.left != null) {
			ll = this.getHeight(root.left.left);
			lr = this.getHeight(root.left.right);
		} else {
			ll = lr = 0;
		}
		int rr, rl;
		if (root.right != null) {
			rr = this.getHeight(root.right.right);
			rl = this.getHeight(root.right.left);
		} else {
			rr = rl = 0;
		}

		if (Math.abs(l - r) <= 1)
			return;

		if (l > r) {
			if (ll < lr) {
				root.left = this.leftTurn(root.left);
				root.left.parent = root;
			}
			root = this.rightTurn(root);
		} else {
			if (rr < rl) {
				root.right = this.rightTurn(root.right);
				root.right.parent = root;
			}
			root = this.leftTurn(root);
		}
	}

	/**
	 * TODO
	 * 
	 * Inserts the given key into this AVL tree such that the ordering property
	 * for a BST and the balancing property for an AVL tree are maintained.
	 */

	public Node insert(K key) {
		Node q = super.insert(key);
		this.checkbalance();
		return q;
	}
}
